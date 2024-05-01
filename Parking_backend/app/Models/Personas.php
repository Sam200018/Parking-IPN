<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Personas extends Model
{
    use HasFactory;

    protected $table= 'Personas';

    protected $primaryKey= 'id_persona';

    protected $fillable = [
        'id_persona',
        'nombre',
        'a_paterno',
        'a_materno',
        'id_ipn',
        'ruta_identificacion',
        'ruta_fotografia',
        'numero_contacto'
    ]; 

    public $timestamps = false;

    public function cuenta()
    {
        return $this->hasMany(Cuentas::class, 'id_cuenta');
    }

    public function visita()
    {
        return $this->hasMany(Visita::class, 'id_visita');
    }
}
