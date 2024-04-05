<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;
use Tymon\JWTAuth\Contracts\JWTSubject;
use Illuminate\Notifications\Notifiable;
use Illuminate\Foundation\Auth\User as Authenticatable;


class cuenta extends Authenticatable implements JWTSubject
{
    use HasFactory;
    use Notifiable;

    protected $table= 'cuenta';

    protected $primaryKey = 'id_cuenta';

    protected $casts = [
        'activo' => 'boolean',
        'debe_cambiar_contrasena' => 'boolean'
    ];

    protected $fillable = [
        'id_cuenta',
        'id_usuario',
        'id_rol',
        'id_prog_academico',
        'activo',
        'correo',
        'password',
        'debe_cambiar_contrasena'
    ];
    
    public $timestamps = false;

    protected $hidden = [
        'password',
    ];

    public function rol(){
        return $this->belongsTo(Rol::class, 'id_rol');
    }

    public function prog_academico()
    {
        return $this->belongsTo(programa_academico::class,'id_prog_academico');
    }

    public function usuario()
    {
        return $this->belongsTo(usuario::class,'id_usuario');
    }

    public function getJWTIdentifier()
    {
        return $this->getKey();
    }

    public function getJWTCustomClaims()
    {
        return [
            'correo'=>$this->correo,
            'id_usuario'=>$this->id_usuario,
            'id_rol'=>$this->id_rol
        ];
    }
}
