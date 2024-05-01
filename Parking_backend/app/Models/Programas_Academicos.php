<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Programas_Academicos extends Model
{
    use HasFactory;
    protected $table = 'Programas_Academicos';

    protected $primaryKey = 'id_prog_academico';

    protected $fillable = [
        'nombre_prog_academico'
    ];

    public $timestamps = false;
    
    public function cuenta()
    {
        return $this->hasMany(Cuentas::class,"id_cuenta");
    }
}
