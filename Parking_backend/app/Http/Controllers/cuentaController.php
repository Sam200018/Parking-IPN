<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Validator;
use Illuminate\Support\Facades\Log;
use Illuminate\Validation\ValidationException;
use Illuminate\Support\Str;
use Illuminate\Support\Facades\Hash;
use App\Models\Cuentas;
use App\Models\Tarjetas_Acceso;

class cuentaController extends Controller
{
    /**
     * Display a listing of the resource.
     */
    public function isVerifiedPersona(String $id)
    {
        $cuenta = Cuentas::where('id_persona', $id)->first();
        if ($cuenta) {
            return response()->json([
                'verified' => true,
                'message' => 'Persona verificada',
                'account'=>$cuenta
            ]);
        } else {
            return response()->json(['verified' => false, 'message' => 'Persona no verificada, cree una cuenta']);
        }
    }

    /**
     * Store a newly created resource in storage.
     */
    public function store(Request $request)
    {
        Log::info($request->all());
        
        $messages =[
            'id_persona.unique'=>'Este usuario ya tiene cuenta registrada',
            'id_persona.exists'=>'Este usuario no existe',
            'id_role.exists'=>'El rol seleccionado no existe',
            'correo.unique'=>'El correo ya ha sido registrado'
        ];

        $validator = Validator::make($request->all(),[
            'id_persona'=>'required|unique:Cuentas,id_persona|exists:Personas,id_persona',
            'id_rol'=>'required|exists:Roles,id_rol',
            'id_prog_academico'=>'nullable|exists:Programas_Academicos,id_prog_academico',
            'correo'=>'required|email|unique:Cuentas,correo',
        ],$messages);
        
        if($validator->fails()){
            return response()->json([
                'errors'=>$validator->errors()
            ],422);
        }

        $tempPassword = Str::random(12);
        $passwordHash = Hash::make($tempPassword);


        $cuenta = Cuentas::create([
            'id_persona'=>$request->id_persona,
            'id_rol'=>$request->id_rol,
            'id_prog_academico'=>$request->id_prog_academico,
            'correo'=>$request->correo,
            'activo'=>true,
            'password'=> $passwordHash,
        ]);

        return response()->json([
            'message'=> 'Cuenta creada exitosamente',
            'cuenta'=>$cuenta,
            'temp_password'=>$tempPassword
        ]);
    }

    public function getCuenta(String $id)
    {
        try{
            $cuenta = Cuentas::with(['rol','prog_academico','persona'])->findOrFail($id);
            return response()->json([
                'cuenta'=>$cuenta
            ],200); 
        } catch (ModelNotFoundException $e) {
            return response()->json(['mensaje' => 'Modelo no encontrado'], 404);
        } catch (\Exception $e) {
            return response()->json(['error' => $e->getMessage()], 500);
        }
    }

    public function getAllAccounts(Request $request)
    {
        $cuentas = Cuentas::with([
            'rol',
            'prog_academico',
            'persona',// Asegúrate de cargar las tarjetas de acceso
        ])->get();
    
        // Agregar la cuenta de tarjetas de acceso a cada cuenta
        $cuentas->each(function($cuenta) {
            $cuenta->no_vehiculos = Tarjetas_Acceso::where('id_cuenta', $cuenta->id_cuenta)->count();
        });

        if ($request->has('search')) {
            $search = $request->search;
        
            $cuentas = Cuentas::with(['rol', 'prog_academico', 'persona'])
                ->whereHas('persona', function ($query) use ($search) {
                    $query->where('nombre', 'like', "%$search%")
                          ->orWhere('a_paterno', 'like', "%$search%")
                          ->orWhere('a_materno', 'like', "%$search%")
                          ->orWhere('numero_contacto', 'like', "%$search%");
                })
                ->get();
        
            // Agregar la cuenta de tarjetas de acceso nuevamente
            $cuentas->each(function($cuenta) {
                $cuenta->no_vehiculos = Tarjetas_Acceso::where('id_cuenta', $cuenta->id_cuenta)->count();
            });
        }
    
        return response()->json([
            'cuentas' => $cuentas
        ], 200);
    }

    public function changeAccountState(String $id)
    {
        $cuenta = Cuentas::where('id_cuenta',$id)->first();
        
        if($cuenta){
            $cuenta->activo = !$cuenta->activo;
            $cuenta->save();

            return response()->json([
                'message' => "Cuenta actualizada",
            ],200);
        }else{
            return response()->json([
                'error'=>'Cuenta de acceso no encontrada'
            ],404);
        }
    }

}
