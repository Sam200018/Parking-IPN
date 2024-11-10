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
use App\Models\Registros;
use App\Models\Tokens;
use App\Events\AccessCardCreated;

class tarjetasAccesoController extends Controller
{
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
                'token'=> $token
            ]);

            $vehiculo->asignado = true;

            $vehiculo->save();

            $tarjetaAcceso = Tarjetas_Acceso::with([
                'cuenta.persona',
                'cuenta.rol',
                'cuenta.prog_academico',
                 'vehiculo',
                ])->get();

            event(new AccessCardCreated($tarjetaAcceso));

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
    public function getCardInfo(Request $request)
    {

        $token = $request->query('card_token');

        try {
            $tarjetaAcceso = Tarjetas_Acceso::with([
                'cuenta.persona',
                'cuenta.rol',
                'cuenta.prog_academico',
                'vehiculo'])
                ->where('token', $token)
                ->first();
                

            if (!$tarjetaAcceso) {
                return response()->json(['mensaje' => 'Tarjeta de acceso no valida'], 404);
            }
            
            $ultimoRegistro = Registros::where('id_tarjeta_acceso', $tarjetaAcceso->id_tarjeta_acceso)
            ->orderBy('id_registro', 'desc')
            ->first();
            
            if(!$ultimoRegistro){
                return response()->json([
                    'tarjeta_acceso' => $tarjetaAcceso,
                    'movimiento' => 1
                ], 200);
            }

            $isCardInUse = Tokens::where('id_token', $ultimoRegistro->id_token)->exists();
            
            if(!$isCardInUse){
                return response()->json([
                    'tarjeta_acceso' => $tarjetaAcceso,
                    'movimiento' => 1
                ], 200);
            }
            return response()->json([
                'tarjeta_acceso' => $tarjetaAcceso,
                'movimiento' => 0
            ], 200);


        } catch (ModelNotFoundException $e) {
            return response()->json(['mensaje' => 'Modelo no encontrado'], 404);
        } catch (\Exception $e) {
            return response()->json(['error' => $e->getMessage()], 500);
        }
    }

    public function getAll(Request $request)
    {

        $query = Tarjetas_Acceso::with([
            'cuenta.persona',
            'cuenta.rol',
            'cuenta.prog_academico',
             'vehiculo',
            ]);

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
    public function deleteAccessCard(string $id)
    {
        $tarjetaAccesso = Tarjetas_Acceso::where('id_tarjeta_acceso',$id)->first();

        if($tarjetaAcceso){
            $tarjetaAcceso->token = null;
            $tarjetaAcceso->save();

            return response()->json([
                'message' => "Tarjeta de acceso dada de baja correctamente",
            ],200);
        }else{
            return response()->json([
                'error'=>'Tarjeta de acceso no encontrada'
            ],404);
        }
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

    public function getAllAccessCardList(Request $request)
    {
        $query = Tarjetas_Acceso::with([
        ]);
    
        $tarjetasAcceso = $query->get();
    
        
        $data = json_encode($tarjetasAcceso);
        
        // Disparar el evento con la colección completa de tarjetas de acceso
        event(new AccessCardCreated($data));
        // AccessCardCreated::dispatch($tarjetasAcceso);
    
        return response()->json([
            'message' => 'Actualizando tarjetas de acceso...'
        ], 200);
    }

    public function getAccessCardById(String $id)
    {
        $tarjetaAccesso = Tarjetas_Acceso::with(['cuenta','vehiculo'])
        ->where('id_tarjeta_acceso',$id)->first();

        if($tarjetaAcceso){
            return response()->json([
                'accessCard' => $tarjetaAcceso,
            ],200);
        }else{
            return response()->json([
                'error'=>'Tarjeta de acceso no encontrada'
            ],404);
        }
    }
}
