<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Visita extends Model
{
    use HasFactory;

    protected $table = 'Visitas';

    protected $primaryKey = 'id_visita';
    
    protected $fillable = [
        'id_visita',
        'id_persona',
        'id_vehiculo',
        'veces_ingresadas'
    ];

    public $timestamps = false;

    public function persona()
    {
        return $this->belongsTo(Personas::class, 'id_persona');
    }

   
    public function vehiculo()
    {
        return $this->belongsTo(vehiculo::class, 'id_vehiculo');
    }

    /**
     * Get all of the registros for the Visita
     *
     * @return \Illuminate\Database\Eloquent\Relations\HasMany
     */
    public function registros()
    {
        return $this->hasMany(Registros::class, 'id_registro');
    }
}
