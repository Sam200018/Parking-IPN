<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Validator;
use Illuminate\Support\Facades\Log;
use App\Events\IncidentCreated;
use Illuminate\Validation\ValidationException;
use App\Models\IncidentesVehiculo;
use App\Models\Tarjetas_Acceso;

class IncidentesVehiculoController extends Controller
{

    /**
     * Store a newly created resource in storage.
     */
    public function postIncident(Request $request)
    {
        $messages =[
            'id_tarjeta_acceso.required' => 'El campo id de la tarjeta de acceso es obligatorio.',
            'id_tarjeta_acceso.integer' => 'El id de la tarjeta de acceso debe ser un número entero.',
            'id_tarjeta_acceso.exists' => 'La tarjeta de acceso especificada no existe.',

            'titulo.required' => 'El campo título es obligatorio.',
            'titulo.max' => 'El título no debe exceder los 100 caracteres.',

            'detalles.required' => 'El campo detalles es obligatorio.',
            'detalles.max' => 'Los detalles no deben exceder los 225 caracteres.'
        ];

        $validator = Validator::make($request->all(),[
            'id_tarjeta_acceso' => 'required|integer|exists:Tarjetas_Acceso,id_tarjeta_acceso',
            'titulo' => 'required',
            'detalles' => 'required'
        ],$messages);

        if($validator->fails()){
            return response()->json([
                'errors' => $validator->errors()
            ],422);
        }

        $incident = IncidentesVehiculo::create([
            'id_tarjeta_acceso' => $request->id_tarjeta_acceso,
            'titulo' => $request->titulo,
            'detalles' => $request->detalles,
            'cerrado' => false,
        ]);

        event(new IncidentCreated($incident));

        return response()->json([
            'message' => 'Incidente creado exitosamente',
        ], 200);
    }

    /**
     * 
     */
    public function getAllIncidentsSync()
    {
        $incidentesVeh = IncidentesVehiculo::orderBy('id_incidente', 'desc')->get();

        event(new IncidentCreated($incidentesVeh));

        return response()->json([
            'message' => 'Incidentes enviados'
        ],200);
    }

    /**
     * Update the specified resource in storage.
     */
    public function updateIncident(string $id)
    {
        $incident = IncidentesVehiculo::where('id_incidente', $id)->first();
        if ($incident) {
            $incident->cerrado = true;
            $incident->save();

            event(new IncidentCreated($incident));

            return response()->json([
                'message' => 'Incidente actualizado',
                'incident'=>$incident
            ]);
        } else {
            return response()->json([
                'error' => 'Incidente no encontrado'
            ],404);
        }
    }

    public function showIncident($id)
    {
        $incidente = IncidentesVehiculo::with([
            'tarjetaAcceso.cuenta.persona',
            'tarjetaAcceso.cuenta.rol',
            'tarjetaAcceso.vehiculo'])
                              ->where('id_incidente', $id)
                              ->first();

        if (!$incidente) {
            return response()->json(['mensaje' => 'Incidente no encontrado'], 404);
        }

        return response()->json([
            'incidente' => $incidente
        ], 200);
    }

    public function getAllIncidents(Request $request)
    {
        $query = IncidentesVehiculo::with([
            'tarjetaAcceso.cuenta.persona',
            'tarjetaAcceso.vehiculo',
        ])->orderBy('id_incidente', 'desc');
    
        if ($request->has('search')) {
            $search = $request->search;
            $query->where('titulo', 'like', "%$search%");
        }
    
        $incidentesVeh = $query->get();
    
        return response()->json([
            'incidentes' => $incidentesVeh
        ], 200);
    }

    public function getAllIncidentsByAccesCard(String $id)
    {
        $tarjetaAcceso = Tarjetas_Acceso::where('id_tarjeta_acceso',$id)->first();

        if($tarjetaAcceso){
            $idTarjetaAcceso = $tarjetaAcceso->id_tarjeta_acceso;
            
            $incidentesVeh = IncidentesVehiculo::when($idTarjetaAcceso, function($query, $idTarjetaAcceso) {
                return $query->whereHas('tarjetaAcceso', function($q) use ($idTarjetaAcceso) {
                    $q->where('id_tarjeta_acceso', $idTarjetaAcceso);
                });
            })
            ->orderBy('id_incidente', 'desc')
            ->get();
        
            return response()->json([
                'incidentes' => $incidentesVeh
            ], 200);  

        }else{
            return response()->json([
                'error'=>'Tarjeta de acceso no encontrada'
            ],404);
        }
    }
}
