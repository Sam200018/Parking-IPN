<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;
use Tymon\JWTAuth\Contracts\JWTSubject;
use Illuminate\Notifications\Notifiable;
use Illuminate\Foundation\Auth\User as Authenticatable;


class Cuentas extends Authenticatable implements JWTSubject
{
    use HasFactory;
    use Notifiable;

    protected $table= 'Cuentas';

    protected $primaryKey = 'id_cuenta';

    protected $casts = [
        'activo' => 'boolean',
        'debe_cambiar_contrasena' => 'boolean'
    ];

    protected $fillable = [
        'id_cuenta',
        'id_persona',
        'id_rol',
        'id_prog_academico',
        'activo',
        'correo',
        'password',
    ];
    
    public $timestamps = false;

    protected $hidden = [
        'password',
    ];

    public function rol(){
        return $this->belongsTo(Roles::class, 'id_rol');
    }

    public function prog_academico()
    {
        return $this->belongsTo(Programas_Academicos::class,'id_prog_academico');
    }

    public function persona()
    {
        return $this->belongsTo(Personas::class,'id_persona');
    }

    public function getJWTIdentifier()
    {
        return $this->getKey();
    }

    public function getJWTCustomClaims()
    {
        return [
            'correo'=>$this->correo,
            'id_persona'=>$this->id_persona,
            'id_rol'=>$this->id_rol
        ];
    }
}
