<?php

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;
use App\Http\Controllers\usuarioController;
use App\Http\Controllers\rolController;
use App\Http\Controllers\ProgAcademController;
use App\Http\Controllers\cuentaController;
use App\Http\Controllers\authController;
use App\Http\Controllers\ImageController;
use App\Http\Controllers\vehiculosController;
use App\Http\Controllers\tarjetasAccesoController;


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
Route::post('/create_role', [rolController::class,'store']);
Route::get('/get_all_roles', [rolController::class,'getAllRoles']);
// Programa academico
Route::post('/create_prog_academico', [progAcademController::class,'store']);
Route::get('/get_all_progs_academs', [progAcademController::class,'getAllProgsAcadems']);
// Usuarios
Route::get('/get_all_users', [usuarioController::class,'show']);
Route::post('/create_user', [usuarioController::class,'store']);
Route::get('/get_persona_by_id/{id_persona}',[usuarioController::class,'index']);
// Images
Route::get('/storage/{filename}',[ImageController::class,'getImage']);
// Cuenta
Route::post('/create_account', [cuentaController::class,'store']);
Route::get('/get_account/{id_cuenta}',[cuentaController::class,'getCuenta']);
Route::get('/check_account_by_person/{id_persona}',[cuentaController::class,'indexByPersona']);
// Auth
Route::post('/login',[authController::class,'login']);
// Vehiculo
Route::post('/create_vehicle', [vehiculosController::class,'store']);
Route::get('/get_all_vehicles', [vehiculosController::class,'show']);
// Tarjetas acceso
Route::post('/gen_access_card',[tarjetasAccesoController::class,'store']);

Route::group(['middleware' => ['jwt.auth']], function () {
    Route::post('/check-status', [authController::class, 'checkStatus']);

});


