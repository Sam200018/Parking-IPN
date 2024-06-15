<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Tokens extends Model
{
    use HasFactory;

    protected $table = 'Tokens';

    protected $primaryKey = 'id_token';

    protected $fillable = [
        'token'
    ];

    public $timestamps = false;

    public function tarjetaAcceso()
    {
        return $this->hasOne(TarjetasAcceso::class,'id_tarjeta_acceso');
    }
}
