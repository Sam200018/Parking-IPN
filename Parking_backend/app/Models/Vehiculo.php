<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Vehiculo extends Model
{
    use HasFactory;

    protected $table = 'Vehiculos';
    
    protected $primaryKey = 'id_vehiculo';
    protected $casts = [
        'asignado' => 'boolean',
    ];

    protected $fillable = [
        'id_vehiculo',
        'tipo_vehiculo',
        'placa',
        'marca',
        'modelo',
        'color',
        'ruta_documento',
        'asignado',
    ];

    public $timestamps = false;

    public function tarjetaAcceso()
    {
        return $this->hasMany(Tarjetas_Acceso::class,'id_tarjeta_acceso');
    }

    public function visita()
    {
        return $this->hasMany(Visita::class,'id_visita');
    }
}
