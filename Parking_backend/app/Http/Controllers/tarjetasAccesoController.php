<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Validator;
use Illuminate\Support\Facades\Log;
use Illuminate\Validation\ValidationException;
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

            $tarjeta = Tarjetas_Acceso::create([
                'id_cuenta'=> $cuenta->id_cuenta,
                'id_vehiculo'=> $vehiculo->id_vehiculo,
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
    public function show(string $id)
    {
        //
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
