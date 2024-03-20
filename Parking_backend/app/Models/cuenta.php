<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class cuenta extends Model
{
    use HasFactory;

    protected $table= 'cuenta';

    protected $primaryKey = 'id_cuenta';

    protected $fillable = [
        'id_cuenta',
        'id_usuario',
        'id_rol',
        'id_prog_academico',
        'activo',
        'correo',
        'contrasena',
        'debe_cambiar_contrasena'
    ];
    
    public $timestamps = false;

    protected $hidden = [
        'contrasena',
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

}
