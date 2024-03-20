<?php

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;
use App\Http\Controllers\usuarioController;
use App\Http\Controllers\rolController;
use App\Http\Controllers\ProgAcademController;
use App\Http\Controllers\cuentaController;


/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider and all of them will
| be assigned to the "api" middleware group. Make something great!
|
*/

// Rol
Route::post('create_role', [rolController::class,'store']);
Route::get('get_all_roles', [rolController::class,'getAllRoles']);
// Programa academico
Route::post('create_prog_academico', [progAcademController::class,'store']);
Route::get('get_all_progs_academs', [progAcademController::class,'getAllProgsAcadems']);
// Usuarios
Route::get('get_all_users', [usuarioController::class,'show']);
Route::post('create_user', [usuarioController::class,'store']);
// Cuenta
Route::post('create_account', [cuentaController::class,'store']);
Route::get('get_account/{id_cuenta}',[cuentaController::class,'getCuenta']);




// Route::middleware('auth:sanctum')->get('/user', function (Request $request) {
//     return $request->user();
// });

