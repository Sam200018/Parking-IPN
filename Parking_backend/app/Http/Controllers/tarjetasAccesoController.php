<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Validator;
use Illuminate\Support\Facades\Log;
use Illuminate\Validation\ValidationException;
use Illuminate\Support\Facades\Hash;
use App\Models\Cuentas;
use App\Models\Vehiculo;
use App\Models\Tarjetas_Acceso;

class tarjetasAccesoController extends Controller
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
        $validator = Validator::make($request->all(), [
            'id_cuenta' => 'required|integer',
            'id_vehiculo' => 'required|integer|unique:Tarjetas_Acceso,id_vehiculo,NULL,id,id_cuenta,' . $request->id_cuenta,
        ]);
    
        if ($validator->fails()) {
            return response()->json(['errors' => "La combinacion no es posible"], 422);
        }
        try{
            $vehiculo = Vehiculo::findOrFail($request->id_vehiculo);
            $cuenta = Cuentas::findOrFail($request->id_cuenta);

            $token = $this->createCompositeHash($cuenta->id_cuenta,$vehiculo->id_vehiculo,"");

            $tarjeta = Tarjetas_Acceso::create([
                'id_cuenta'=> $cuenta->id_cuenta,
                'id_vehiculo'=> $vehiculo->id_vehiculo,
                'token'=>token
            ]);

            $vehiculo->asignado = true;

            $vehiculo->save();

            return response()->json([
                'message'=> 'Tarjeta acceso creada con exito'
            ]);
        }catch(ModelNotFoundException $e){
            return response()->json(['error' =>$e->getMessage()], 404);
        }
        catch(\Exception $e){
            return response()->json(['error'=>$e->getMessage()],500);
        }

    
    }

    /**
     * Display the specified resource.
     */
    public function show(Request $request)
    {
        
    }

    public function getAll(Request $request)
    {

        $query = Tarjetas_Acceso::with(['cuenta.persona', 'vehiculo']);

        if ($request->has('id_cuenta')) {
            $query->whereHas('cuenta', function ($query) use ($request) {
                $query->where('id_cuenta', $request->id_cuenta);
            });
        }

        $tarjetasAcceso = $query->get();

        return response()->json([
            'tarjetas_acceso'=> $tarjetasAcceso
        ],200);
        
    }

    /**
     * Remove the specified resource from storage.
     */
    public function destroy(string $id)
    {
        //
    }

    public function newHash(String $id)
    {
        $tarjetaAcceso = Tarjetas_Acceso::where('id_tarjeta_acceso',$id)->first();

        if($tarjetaAcceso){
            $token = $this->createCompositeHash($tarjetaAcceso->id_tarjeta_acceso, $tarjetaAcceso->id_cuenta, $tarjetaAcceso->id_vehiculo);

            $tarjetaAcceso->token = $token;
            $tarjetaAcceso->save();
            return response()->json([
                    'message'=>'Token actulizado correctamente',
                'tarjeta_acceso'=>$tarjetaAcceso
                ],200);
        }else{
            return response()->json([
                'error'=>'Tarjeta de acceso no encontrada'
            ],404);
        }
    }

    public function createCompositeHash($field1, $field2, $field3)
    {
        $inputString = $field1 . '|' . $field2 . '|' . $field3;

        $hash = Hash::make($inputString);

        return $hash;
    }
}
