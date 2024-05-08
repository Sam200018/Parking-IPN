<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Validator;
use Illuminate\Support\Facades\Log;
use Illuminate\Validation\ValidationException;
use Illuminate\Support\Str;
use Illuminate\Support\Facades\Hash;
use App\Models\Vehiculo;

class vehiculosController extends Controller
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
        // Usarlo para tarjetas de acceso
        $messages =[
        ];

        $validator = Validator::make($request->all(),[
           'tipo_vehiculo'=> 'required',
           'placa'=> 'required',
           'marca'=> 'required',
           'modelo'=> 'required',
           'color'=> 'required',
           'documento'=>'required|file|image|mimes:png,jpg,max:2048',
        ],$messages);
        
        if($validator->fails()){
            return response()->json([
                'errors'=>$validator->errors()
            ],422);
        }

        $path_document = "";

        try{
            if($request->hasFile('documento') && $request->file('documento')->isValid()){
                $image = $request->file('documento');
    
                $path_document = $image->store('images','public');
                
            }else{
                throw new \Exception("Archivo de documento no valido.");
            }

            $vehicle = Vehiculo::create([
                'tipo_vehiculo'=>$request->tipo_vehiculo,
                'placa'=> $request->placa,
                'marca'=> $request->marca,
                'modelo'=>$request->modelo,
                'color'=>$request->color,
                'ruta_documento' =>basename($path_document),
                'asignado'=> false,
            ]);

            return response()->json([
                'message'=>'Vehiculo creado exitosamente',
                'vehiculo'=> $vehicle
            ]);


        }catch(\Exception $e){
            return response()->json(['error'=> $e->getMessage()],500);
        }

    }

    /**
     * Display the specified resource.
     */
    public function show(Request $request)
    {

        $query = Vehiculo::query();

        if ($request->has('asignado')) {
            $asignado = filter_var($request->asignado, FILTER_VALIDATE_BOOLEAN);  // Convierte el string a boolean
            $query->where('asignado', $asignado);
        }

        $input = $request->input('placa');

        if($input){
            $query->where(function($query) use($input){
                $query->where('placa','like','%'.$input.'%');
            });
        }

        $vehicles = $query->get();

        return response()->json([
            'vehiculos'=>$vehicles
        ],200);
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
