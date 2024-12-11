<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Validator;
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
use App\Models\Personas;
use Carbon\Carbon;
use Exception;
use Illuminate\Database\Eloquent\ModelNotFoundException;
use Illuminate\Support\Facades\Log;

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
                $now = Carbon::now();

                if($movimiento == 1){
                    $tokenValue = $this->createCompositeHash($cuenta->id_cuenta,$visita->id_visita,"");

                    $token= Tokens::create([
                        'token' => $tokenValue,
                    ]);
                    $registro = Registros::create([
                        'id_visita' => $visita->id_visita,
                        'id_token' => $token->id_token,
                        'id_cuenta' => $cuenta->id_cuenta,
                    ]);
    
                    $entrada = Entrada::create([
                        'id_registro' => $registro->id_registro,
                        'id_cuenta' => null,
                        'check' => $now,
                        'observaciones' => $request->observaciones
                    ]);
                }else{
                    $ultimoRegistro = Registros::where('id_visita', $visita->id_visita)
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
                        'id_cuenta' => null,
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

    public function getAllRecordsList(Request $request)
    {   

        $entradas = Entrada::with([
            'cuenta.persona',
            'registro.cuenta.persona',
            'registro.tarjetaAcceso.cuenta.persona',
            'registro.tarjetaAcceso.vehiculo',
            'registro.visita.vehiculo',
            'registro.visita.persona',
        ])->orderBy('check', 'asc');
    
        $salidas = Salida::with([
            'cuenta.persona',
            'registro.cuenta.persona',
            'registro.tarjetaAcceso.cuenta.persona',
            'registro.tarjetaAcceso.vehiculo',
            'registro.visita.vehiculo',
            'registro.visita.persona',
        ])->orderBy('check', 'asc');
    

        if ($request->has('search')) {
            $search = $request->search;
    
            $entradas->whereHas('registro.tarjetaAcceso.cuenta.persona', function ($query) use ($search) {
                $query->where('nombre', 'like', "%$search%")
                      ->orWhere('a_paterno', 'like', "%$search%")
                      ->orWhere('a_materno', 'like', "%$search%")
                      ->orWhere('numero_contacto', 'like', "%$search%");
            })->orWhereHas('registro.tarjetaAcceso.vehiculo', function ($query) use ($search) {
                $query->where('placa', 'like', "%$search%");
            })->orWhereHas('registro.visita.vehiculo', function ($query) use ($search) {
                $query->where('placa', 'like', "%$search%");
            })->orWhereHas('registro.visita.persona', function ($query) use ($search) {
                $query->where('nombre', 'like', "%$search%");
            });
    
            $salidas->whereHas('registro.tarjetaAcceso.cuenta.persona', function ($query) use ($search) {
                $query->where('nombre', 'like', "%$search%")
                      ->orWhere('a_paterno', 'like', "%$search%")
                      ->orWhere('a_materno', 'like', "%$search%")
                      ->orWhere('numero_contacto', 'like', "%$search%");
            })->orWhereHas('registro.tarjetaAcceso.vehiculo', function ($query) use ($search) {
                $query->where('placa', 'like', "%$search%");
            })->orWhereHas('registro.visita.vehiculo', function ($query) use ($search) {
                $query->where('placa', 'like', "%$search%");
            })->orWhereHas('registro.visita.persona', function ($query) use ($search) {
                $query->where('nombre', 'like', "%$search%");
            });
        }
    
        $entradas = $entradas->get();
        $salidas = $salidas->get();
    
        $resultados = $entradas->concat($salidas);
    
        $registros = $resultados
            ->filter(function ($item) {
                return !is_null($item->registro->visita) || !is_null($item->registro->tarjetaAcceso);
            })
            ->sortBy(function ($item) {
                return is_null($item->registro->id_token) ? 1 : 0;
            })
            ->sortByDesc('check')
            ->values();
    
        return response()->json([
            'transactions' => $registros
        ], 200);
    }

    public function getAllRecordsListByAccessCard(String $id)
{
    $tarjetaAcceso = Tarjetas_Acceso::with('cuenta.persona')->where('id_tarjeta_acceso', $id)->first();

    if ($tarjetaAcceso && $tarjetaAcceso->cuenta && $tarjetaAcceso->cuenta->persona) {
        $idPersona = $tarjetaAcceso->cuenta->persona->id_persona;

        $entradas = Entrada::with([
            'registro.tarjetaAcceso',
            'registro.visita'
        ])->whereHas('registro.tarjetaAcceso.cuenta.persona', function ($query) use ($idPersona) {
            $query->where('id_persona', $idPersona);
        })->orderBy('check', 'desc')->get();

        $salidas = Salida::with([
            'registro.tarjetaAcceso',
            'registro.visita'
        ])->whereHas('registro.tarjetaAcceso.cuenta.persona', function ($query) use ($idPersona) {
            $query->where('id_persona', $idPersona);
        })->orderBy('check', 'desc')->get();

        $resultados = $entradas->concat($salidas);

        $registros = $resultados->sortByDesc('check')->values();

        return response()->json([
            'transactions' => $registros
        ], 200);
    } else {
        return response()->json([
            'error' => 'Tarjeta de acceso no encontrada o persona no asociada'
        ], 404);
    }
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

            $ultimoRegistro = Registros::where('id_tarjeta_acceso', $tarjetaAcceso->id_tarjeta_acceso)
            ->orderBy('id_registro', 'desc')
            ->first();

            $isCardInUse = Tokens::where('id_token', $ultimoRegistro->id_token)->exists();
           
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
            $lastVisit = Registros::where('id_visita', $visita->id_visita)
            ->orderBy('id_registro','desc')
            ->first();
            
            if($lastVisit){
                
                $isVisitActive = Tokens::where('id_token',$lastVisit->id_token)->first();
                
                if($isVisitActive){
                    return response()->json([
                        'visita' => $visita,
                        'movimiento' => 0,
                        'nota' => ''
                    ], 200);
                }
                
                return response()->json([
                    'visita' => $visita,
                    'movimiento' => 1,
                    'nota' => ''
                ], 200);
            }
            
            
            return response()->json([
                'visita' => $visita,
                'movimiento' => 1,
                'nota' => ''
            ], 200);
        }    

        // buscar vehiculo
        $vehicleQuery = Vehiculo::where('id_vehiculo',$vehiculo)->get()->first();
        // buscar persona
        $personQuery = Personas::where('id_persona',$persona)->get()->first();


        $visita = Visita::create([
            'id_persona' => $personQuery->id_persona,
            'id_vehiculo' => $vehicleQuery->id_vehiculo,
            'veces_ingresadas' => 0,
        ]);

        $visita = Visita::with(['persona', 'vehiculo'])
                ->where('id_visita', $visita->id_visita)
                ->first();

        if ($vehicleQuery->asignado) {
            $note = 'El vehículo ya cuenta con tarjeta de acceso';
        } else {
            if(!empty($personQuery->id_ipn) == 1){
                $note = 'Solo el vehículo es nuevo en el sistema';
            }else{
                $note = 'El vehículo y la persona son nuevos en el sistema';
            }
        }
        

        return response()->json([
            'visita' => $visita,
            'movimiento' => 1,
            'nota' => $note
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
