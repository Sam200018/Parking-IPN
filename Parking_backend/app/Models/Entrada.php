<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Entrada extends Model
{
    use HasFactory;

    protected $table = 'Entradas';

    protected $primaryKey = 'id_entrada';

    protected $dates = ['check']; 

    protected $fillable = [
        'id_entrada',
        'id_registro',
        'id_cuenta',
        'check',
        'observaciones',
    ];

    public $timestamps = false;

    
    public function registro()
    {
        return $this->belongsTo(Registros::class, 'id_registro');
    }

    public function cuenta()
    {
        return $this->belongsTo(Cuentas::class, 'id_cuenta');
    }
}
