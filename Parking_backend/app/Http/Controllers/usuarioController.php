<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Validator;
use Illuminate\Support\Facades\Log;
use Illuminate\Validation\ValidationException;
use App\Models\Personas;


class usuarioController extends Controller
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

        $messages = [
            'id_ipn.unique'=>'La boleta o No. empleado ya ha sido registrado',
            'numero_contacto.unique'=>'El numero de contacto ya ha sido registrado'
        ];

        $validator = Validator::make(
            $request->all(),
            ['nombre' => 'required',
            'a_paterno'=> 'required',
            'a_materno'=> 'required',
            'id_ipn' => 'nullable',
            'identificacion' =>'required|file|image|mimes:png,jpg,max:2048',
            'fotografia'=>'required|file|image|mimes:png,jpg,max:2048',
            'numero_contacto'=>'required']
        ,$messages);

        if($validator->fails()){
            return response()->json([
                'erros'=>$validator->errors()
            ],422);
        }


        $path_id_card= "";
        $path_photo= "";

        try{
            if($request->hasFile('identificacion') && $request->file('identificacion')->isValid()){
                $image = $request->file('identificacion');
    
                $path_id_card = $image->store('images','public');
                
            }else{
                throw new \Exception("Archivo de identificacion no valido.");
            }
            
            
            if($request->hasFile('fotografia') && $request->file('fotografia')->isValid()){
                $image = $request->file('fotografia');
                
                $path_photo = $image->store('images','public');
                
            }else{
                throw new \Exception("Archivo de fotografia no valido.");
            }

            $usuario = Personas::create([
                'nombre'=> $request->nombre,
                'a_paterno'=> $request->a_paterno,
                'a_materno'=> $request->a_materno,
                'id_ipn'=> $request->id_ipn,
                'ruta_identificacion'=> $path_id_card,
                'ruta_fotografia'=> $path_photo,
                'numero_contacto'=> $request->numero_contacto,
            ]);
            return response()->json([
                'message' => 'Usuario creado exitosamente',
                'usuario' => $usuario
            ]);

        }catch(\Exception $e){
            return response()->json(['error'=> $e->getMessage()],500);
        }
    }

    /**
     * Display the specified resource.
     */
    public function show()
    {
        $usuarios = Personas::all();
        return response()->json([
            'usuarios'=> $usuarios
        ], 200
        );
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
