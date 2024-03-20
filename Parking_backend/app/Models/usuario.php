<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class usuario extends Model
{
    use HasFactory;

    protected $table= 'usuario';

    protected $primaryKey= 'id_usuario';

    protected $fillable = [
        'id_usuario',
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
        return $this->hasMany(cuenta::class, 'id_cuenta');
    }

    public function visita()
    {
        return $this->hasMany(Visita::class, 'id_visita');
    }
}
