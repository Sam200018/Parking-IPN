<?php

namespace App\Http\Controllers;

use Illuminate\Support\Facades\Auth;
use Illuminate\Http\Request;
use Tymon\JWTAuth\Facades\JWTAuth;
use Illuminate\Support\Facades\Log;
use Illuminate\Support\Facades\Hash;
use App\Models\Cuentas;

class authController extends Controller
{
    public function login(Request $request)
    {
        $credentials = request(['correo','password']);

        if (! $token = auth()->attempt($credentials)) {
            return response()->json(['error' => 'Credenciales no validas'], 401);
        }        
            $cuenta = Cuentas::with(['rol','prog_academico','persona'])->where('correo',$request->correo)->first();

            if(!$cuenta->activo){
                return response()->json([
                    'message'=> 'Cuenta no activa. Acercarse al administrador para más información'
                ],401);
            }
            
            return response()->json([
                'token'=>$token,
                'cuenta'=>$cuenta
            ],200);
    }

    public function checkStatus()
    {
        try {
            $token = JWTAuth::getToken();

            $payLoad = JWTAuth::getPayLoad($token);

            $correo =$payLoad->get('correo');

            $cuenta = Cuentas::with(['rol','prog_academico','persona'])->where('correo',$correo)->first();


            $newToken = JWTAuth::refresh();
            return response()->json([
                'token' => $newToken,
                'cuenta' =>$cuenta
            ]);
        } catch (\Tymon\JWTAuth\Exceptions\TokenInvalidException $e) {
            return response()->json(['error' => 'Token no válido'], 401);
        }
    }
}
