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
use App\Models\Vehiculo;
use App\Models\Visita;
use App\Models\Entrada;
use App\Models\Salida;
use Carbon\Carbon;

class registrosController extends Controller
{

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
                
                $now = Carbon::now();
                
                if($movimiento == 0){
                    $ultimoRegistro = Registros::where('id_tarjeta_acceso', $tarjetaAcceso->id_tarjeta_acceso)
                    ->orderBy('id_registro', 'desc')
                    ->first();
                    
                    if(!$ultimoRegistro){
                        throw new Exception("Movimiento no valido");
                    }
                    
                    
                    $token = Tokens::where('id_token', $ultimoRegistro->id_token)->first();
                    
                    $ultimoRegistro->id_token = null; 
                    $ultimoRegistro->save();
                    
                    if ($token) {
                    } else {
                        throw new Exception("Token no encontrado y no pudo ser eliminado.");
                    }
                    
                    $token->delete();

                    $salida = Salida::create([
                        'id_registro' => $ultimoRegistro->id_registro,
                        'id_cuenta' => $tarjetaAcceso->cuenta->id_cuenta,
                        'check' => $now,
                        'observaciones' => $request->observaciones
                    ]);

                }else{
                    $tokenValue = $this->createCompositeHash($cuenta->id_cuenta,$tarjetaAcceso->id_tarjeta_acceso,"");
                    $token= Tokens::create([
                        'token' => $tokenValue,
                    ]);

                    $registro = Registros::create([
                        'id_tarjeta_acceso' => $tarjetaAcceso->id_tarjeta_acceso,
                        'id_token' => $token->id_token,
                        'id_cuenta' => $cuenta->id_cuenta,
                    ]);

                    $entrada = Entrada::create([
                        'id_registro' => $registro->id_registro,
                        'id_cuenta' => $tarjetaAcceso->cuenta->id_cuenta,
                        'check' => $now,
                        'observaciones' => $request->observaciones
                    ]);
                }
                
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
            try{
                $cuenta = Cuentas::findOrFail($request->id_cuenta);
                $visita = Visita::findOrFail($idVisita);
                $tokenValue = $this->createCompositeHash($cuenta->id_cuenta,$visita->id_visita,"");

                $token= Tokens::create([
                    'token' => $tokenValue,
                ]);

                $registro = Registros::create([
                    'id_visita' => $visita->id_vista,
                    'id_token' => $token->id_token,
                    'id_cuenta' => $cuenta->id_cuenta,
                ]);

                $registros = Registros::all();

                $now = Carbon::now();

                if($movimiento == 0){
                    $entrada = Entrada::create([
                        'id_registro' => $registro->id_registro,
                        'id_cuenta' => $visita->persona->id_persona,
                        'check_in' => $now,
                        'observaciones' => $request->observaciones
                    ]);
                }else{
                    $salida = Salida::create([
                        'id_registro' => $registro->id_registro,
                        'id_cuenta' => $visita->persona->id_persona,
                        'check_out' => $now,
                        'observaciones' => $request->observaciones
                    ]);
                }

                event(new MoveRegistered($registros));

                return response()->json([
                    'message' => 'Registro exitoso',
                ]);

            }catch(ModelNotFoundException $e){
                return response()->json(['error' =>$e->getMessage()], 404);
            }
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

        $entradas = Entrada::with([
            'cuenta.persona',
            'registro.cuenta.persona',
            'registro.tarjetaAcceso.cuenta.persona',
            'registro.tarjetaAcceso.vehiculo',
            'registro.visita.vehiculo',
            ])->orderBy('check','desc')->get();

        $salidas = Salida::with([
            'cuenta.persona',
            'registro.cuenta.persona',
            'registro.tarjetaAcceso.cuenta.persona',
            'registro.tarjetaAcceso.vehiculo',
            'registro.visita.vehiculo',
            ])->orderBy('check','desc')->get();

        $resultados = $entradas->concat($salidas);

        $registros = $resultados->sortBy('check');

        return response()->json([
            'transactions' => $registros
        ],200);
    }


    public function getInfoCardToRegistration(Request $request)
    {
        $messages = [
            'id_persona.required' => 'El campo id de la persona es obligatorio',
            'id_persona.exists' => 'La persona que busca no existe',

            'id_vehiculo.required' => 'El campo id del vehiculo es obligatorio',
            'id_vehiculo.exists' => 'El vehiculo que busca no existe',
        ];

        $validator = Validator::make($request->all(),[
            'id_persona' => 'required|integer|exists:Personas,id_persona',
            'id_vehiculo' => 'required|integer|exists:Vehiculos,id_vehiculo',
        ],$messages);
        
        if($validator->fails()){
            return response()->json(['errors' => $validator->errors()],422);
        }

        $note = 'El usuario y el vehículo cuentan con tarjeta de acceso';
        $persona = $request->id_persona;
        $vehiculo = $request->id_vehiculo;
        
        $tarjetaAcceso = Tarjetas_Acceso::with([
            'cuenta.persona',
            'cuenta.rol',
            'cuenta.prog_academico',
            'vehiculo',
            ])
        ->whereHas('cuenta', function($query) use ($persona) {
            $query->where('id_persona', $persona);
        })
        ->whereHas('vehiculo', function($query) use ($vehiculo) {
            $query->where('id_vehiculo', $vehiculo);
        })
            ->first();
        
        if($tarjetaAcceso){
            $isCardInUse = Registros::where('id_tarjeta_acceso', $tarjetaAcceso->id_tarjeta_acceso)
                                    ->where('id_token', 1)
                                    ->exists();
            if(!$isCardInUse){
                return response()->json([
                    'tarjeta_acceso' => $tarjetaAcceso,
                    'movimiento' => 1,
                    'nota' => $note
                ], 200);
            }

            return response()->json([
                'tarjeta_acceso' => $tarjetaAcceso,
                'movimiento' => 0,
                'nota' => $note
            ], 200);
        }


        $visita = Visita::with(['persona', 'vehiculo'])
                            ->where([
                                ['id_persona', '=', $persona],
                                ['id_vehiculo', '=', $vehiculo]
                            ])
                            ->first();

        if($visita){
            $isVisitActive = Registros::where('id_visita', $visita->id_visita)
                                    ->where('id_token', 1)
                                    ->exists();

            // Falta obtener veces ingresadas

            if(!$isVisitActive){
                return response()->json([
                    'visita' => $visita,
                    'movimiento' => 1,
                    'nota' => ''
                ], 200);
            }
            
            return response()->json([
                'visita' => $visita,
                'movimiento' => 0,
                'nota' => ''
            ], 200);
        }    

        // buscar vehiculo
        $vehicleQuery = Vehiculo::where('id_vehiculo',$vehiculo)->get();
        // buscar persona
        $pesonQuery = Personas::where('id_persona',$persona)->get();

        $visita = Visita::create([
            'id_persona' => $pesonQuery->id_persona,
            'id_vehiculo' => $vehicleQuery->id_vehiculo,
            'veces_ingresadas' => 0,
        ]);

        $visita = Visita::with(['persona', 'vehiculo'])
                ->where('id', $visita->id)
                ->first();

        if($vehicleQuery->asignado){
            $note = 'El vehículo ya cuenta con tarjeta de acceso';
        }else{
            $note = 'Solo el vehículo es nuevo en el sistema';
        }

        return response()->json([
            'visita' => $visita,
            'movimiento' => 0,
            'note' => $note
        ], 200);
    }

    public function getAllRecordsListSync()
    {
        event(new MoveRegistered('hola'));

        return response()->json([
            'message' => "Sincronizando..."
        ],200);
    }
}
