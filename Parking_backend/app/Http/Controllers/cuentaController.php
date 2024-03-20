<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Validator;
use Illuminate\Support\Facades\Log;
use Illuminate\Validation\ValidationException;
use Illuminate\Support\Str;
use Illuminate\Support\Facades\Hash;
use App\Models\cuenta;

class cuentaController extends Controller
{
    /**
     * Display a listing of the resource.
     */
    public function index()
    {
        //
    }

    /**
     * Store a newly created resource in storage.
     */
    public function store(Request $request)
    {
        $messages =[
            'id_usuario.unique'=>'Este usuario ya tiene cuenta registrada',
            'id_usuario.exists'=>'Este usuario no existe',
            'id_role.exists'=>'El rol seleccionado no existe',
            'correo.unique'=>'El correo ya ha sido registrado'
        ];

        $validator = Validator::make($request->all(),[
            'id_usuario'=>'required|unique:cuenta,id_usuario|exists:usuario,id_usuario',
            'id_rol'=>'required|exists:Rol,id_rol',
            'id_prog_academico'=>'nullable|exists:Programa_Academico,id_prog_academico',
            'correo'=>'required|email|unique:cuenta,correo',
        ],$messages);
        
        if($validator->fails()){
            return response()->json([
                'errors'=>$validator->errors()
            ],422);
        }

        $tempPassword = Str::random(12);
        $passwordHash = Hash::make($tempPassword);

        Log::info("hola");

        $cuenta = cuenta::create([
            'id_usuario'=>$request->id_usuario,
            'id_rol'=>$request->id_rol,
            'id_prog_academico'=>$request->id_prog_academico,
            'correo'=>$request->correo,
            'activo'=>true,
            'contrasena'=> $passwordHash,
            'debe_cambiar_contrasena'=> true,
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
            $cuenta = cuenta::with(['rol','prog_academico','usuario'])->findOrFail($id);
            return response()->json([
                'cuenta'=>$cuenta
            ],200); 
        } catch (ModelNotFoundException $e) {
            return response()->json(['mensaje' => 'Modelo no encontrado'], 404);
        } catch (\Exception $e) {
            return response()->json(['error' => $e->getMessage()], 500);
        }
    }


    /**
     * Display the specified resource.
     */
    public function show(string $id)
    {
        
    }

    /**
     * Update the specified resource in storage.
     */
    public function update(Request $request, string $id)
    {
        //
    }

    /**
     * Remove the specified resource from storage.
     */
    public function destroy(string $id)
    {
        //
    }
}
