<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class programa_academico extends Model
{
    use HasFactory;
    protected $table = 'Programa_Academico';

    protected $primaryKey = 'id_prog_academico';

    protected $fillable = [
        'nombre_prog_academico'
    ];

    public $timestamps = false;
    
    public function cuenta()
    {
        return $this->hasMany(cuenta::class,"id_cuenta");
    }
}
