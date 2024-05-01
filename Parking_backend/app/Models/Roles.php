<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Roles extends Model
{
    use HasFactory;
    protected $table= 'Roles';

    protected $primaryKey = 'id_rol';

    protected $fillable =[
        'id_rol',
        'nombre_rol'
    ];

    public $timestamps = false;

    public function cuenta(){
        return $this->hasMany(Cuentas::class,'id_cuenta');
    }
}

