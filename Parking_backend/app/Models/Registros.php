<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Registros extends Model
{
    use HasFactory;

    protected $table = 'Registros';

    protected $primaryKey = 'id_registro';

    protected $fillable = [
        'id_registro',
        'id_tarjeta_acceso',
        'id_token',
        'id_visita',
        'id_cuenta',
    ];

    public $timestamps = false;

    public function tarjetaAcceso()
    {
        return $this->belongsTo(TarjetasAcceso::class,'id_tarjeta_acceso');
    }

    public function cuenta()
    {
        return $this->belongsTo(Cuentas::class,'id_cuenta');
    }

    public function token(Type $var = null)
    {
        return $this->belongsTo(Token::class,'id_token');
    }
}
