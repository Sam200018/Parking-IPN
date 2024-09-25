<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Tarjetas_Acceso extends Model
{
    use HasFactory;

    protected $table = 'Tarjetas_Acceso';

    protected $primaryKey = 'id_tarjeta_acceso';


    protected $fillable = [
        'id_tarjeta_acceso',
        'id_cuenta',
        'id_vehiculo',
        'token'
    ];

    public $timestamps = false;

    public function cuenta()
    {
        return $this->belongsTo(Cuentas::class,'id_cuenta');
    }

    public function vehiculo()
    {
        return $this->belongsTo(Vehiculo::class,'id_vehiculo');
    }

    public function incidentesVehiculo()
    {
        return $this->hasMany(IncidentesVehiculo::class, 'id_incidente');
    }
}
