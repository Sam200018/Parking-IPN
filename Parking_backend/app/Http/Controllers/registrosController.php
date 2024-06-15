<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Validator;
use Illuminate\Support\Facades\Log;
use Illuminate\Validation\ValidationException;
use Illuminate\Support\Facades\Hash;
use App\Events\MoveRegistered;
use App\Models\Tarjetas_Acceso;
use App\Models\Cuentas;
use App\Models\Registros;
use App\Models\Tokens;

class registrosController extends Controller
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
        $validator = Validator::make($request->all(),[
            'id_cuenta' => 'required|integer',
        ]);

        if($validator->fails()){
            return response()->json(['errors' => 'Agregar cuenta que realiza movimiento'],422);
        }

        $idTarjetaAcceso = $request->id_tarjeta_acceso;
        $idVisita = $request->id_visita;
        $movimiento = $request->movimiento;

        if($idTarjetaAcceso){
            try{
                $cuenta = Cuentas::findOrFail($request->id_cuenta);
                $tarjetaAcceso = Tarjetas_Acceso::findOrFail($idTarjetaAcceso);
                $tokenValue = $this->createCompositeHash($cuenta->id_cuenta,$tarjetaAcceso->id_tarjeta_acceso,"");

                $token= Tokens::create([
                    'token' => $tokenValue,
                ]);
                
                $registro = Registros::create([
                    'id_tarjeta_acceso' => $tarjetaAcceso->id_tarjeta_acceso,
                    'id_token' => $token->id_token,
                    'id_cuenta' => $cuenta->id_cuenta,
                ]);

                $registros = Registros::all();
                
                event(new MoveRegistered($registros));

                return response()->json([
                    'message' => 'Registro exitoso',
                ]);
                

            }catch(ModelNotFoundException $e){
                return response()->json(['error' =>$e->getMessage()], 404);
            }
            catch(\Exception $e){
                return response()->json(['error'=>$e->getMessage()],500);
            }
        }elseif($idVisita){

        }else{
            return response()->json(['error' => 'Agregar id visita o id tarjeta de acceso'],500);
        }
    }

    private function createCompositeHash($field1, $field2, $field3)
    {
        $inputString = $field1 . '|' . $field2 . '|' . $field3;

        $hash = Hash::make($inputString);

        return $hash;
    }

    public function getAllRecordsList()
    {
        $registros = Registros::orderBy('id_registro', 'desc')->get();

        event(new MoveRegistered($registros));

        return response()->json([
            'message' => 'Registros enviados'
        ],200);
    }
}
