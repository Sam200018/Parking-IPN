<?php

namespace App\Http\Controllers;

use Illuminate\Support\Facades\Auth;
use Illuminate\Http\Request;
use Tymon\JWTAuth\Facades\JWTAuth;
use Illuminate\Support\Facades\Log;
use Illuminate\Support\Facades\Hash;
use App\Models\cuenta;

class authController extends Controller
{
    public function login(Request $request)
    {
        $credentials = request(['correo','password']);

        if (! $token = auth()->attempt($credentials)) {
            return response()->json(['error' => 'Credenciales no validas'], 401);
        }        
            return response()->json([
                'token'=>$token
            ],200);
    }

    public function checkStatus()
    {
        try {
            $newToken = JWTAuth::refresh();
            return response()->json(['token' => $newToken]);
        } catch (\Tymon\JWTAuth\Exceptions\TokenInvalidException $e) {
            return response()->json(['error' => 'Token no válido'], 401);
        }
    }
}
