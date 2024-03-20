<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Models\programa_academico;

class progAcademController extends Controller
{
    public function store(Request $request)
    {
        $request->validate([
            'nombre_prog_academico'=> 'required|unique:Programa_Academico'
        ]);
            
        $prog_academico = programa_academico::create([
            'nombre_prog_academico'=> $request->nombre_prog_academico,
        ]);
        return response()->json([
            'message'=> 'Programa academico creado exitosamente',
            'prog_academico'=> $prog_academico
        ],200);

    }

    public function getAllProgsAcadems()
    {
        $progs_academs = programa_academico::all();
        return response()->json([
            'programas academicos' => $progs_academs
        ],200);
    }
}
