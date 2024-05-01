<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Models\Programas_Academicos;

class progAcademController extends Controller
{
    public function store(Request $request)
    {
        $request->validate([
            'nombre_prog_academico'=> 'required|unique:Programas_Academicos'
        ]);
            
        $prog_academico = Programas_Academicos::create([
            'nombre_prog_academico'=> $request->nombre_prog_academico,
        ]);
        return response()->json([
            'message'=> 'Programa academico creado exitosamente',
            'prog_academico'=> $prog_academico
        ],200);

    }

    public function getAllProgsAcadems()
    {
        $progs_academs = Programas_Academicos::all();
        return response()->json([
            'programas academicos' => $progs_academs
        ],200);
    }
}
