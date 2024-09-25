<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class IncidentesVehiculo extends Model
{
    use HasFactory;

    protected $table = 'Incidentes_Vehiculo';

    protected $primaryKey = 'id_incidente';

    protected $casts = [
        'cerrado' => 'boolean',
    ];

    protected $fillable = [
        'id_incidente',
        'id_tarjeta_acceso',
        'titulo',
        'detalles',
        'cerrado'
    ];

    public $timestamp = false;

    public function tarjetaAcceso()
    {
        return $this->belongsTo(Tarjetas_Acceso::class, 'id_tarjeta_acceso');
    }
}
