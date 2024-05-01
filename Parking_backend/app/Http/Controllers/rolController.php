<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Models\Roles;

class rolController extends Controller
{
    public function store(Request $request)
    {
        
        $request->validate([
            'nombre_rol' => 'required|unique:Roles'
        ]);

        $rol = Roles::create([
            'nombre_rol'=> $request->nombre_rol,
        ]);

        return response()->json([
            'message' => 'Rol creado exitosamente',
            'rol'=> $rol
        ],200);
    }

    public function getAllRoles()
    {
        $roles = Roles::all();
        return response()->json([
            'roles'=> $roles
        ],200);
    }
}
