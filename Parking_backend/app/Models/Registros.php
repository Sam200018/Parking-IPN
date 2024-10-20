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
        return $this->belongsTo(Tarjetas_Acceso::class,'id_tarjeta_acceso');
    }

    public function cuenta()
    {
        return $this->belongsTo(Cuentas::class,'id_cuenta');
    }

    public function token()
    {
        return $this->belongsTo(Token::class,'id_token');
    }

    /**
     * Get the visita that owns the Registros
     *
     * @return \Illuminate\Database\Eloquent\Relations\BelongsTo
     */
    public function visita()
    {
        return $this->belongsTo(Visita::class, 'id_visita');
    }

    /**
     * Get the Entrada associated with the Registros
     *
     * @return \Illuminate\Database\Eloquent\Relations\HasOne
     */
    public function Entrada(){
        return $this->hasOne(Entrada::class,'id_entrada');
    }

    /**
     * Get the Salida associated with the Registros
     *
     * @return \Illuminate\Database\Eloquent\Relations\HasOne
     */
    public function Salida()
    {
        return $this->hasOne(Salida::class, 'id_salida');
    }

}
