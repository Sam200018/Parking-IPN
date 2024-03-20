<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class rol extends Model
{
    use HasFactory;
    protected $table= 'Rol';

    protected $primaryKey = 'id_rol';

    protected $fillable =[
        'nombre_rol'
    ];

    public $timestamps = false;

    public function cuenta(){
        return $this->hasMany(cuenta::class,'id_cuenta');
    }
}
