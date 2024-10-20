<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    /**
     * Run the migrations.
     */
    public function up(): void
    {
        Schema::create('vehiculos', function (Blueprint $table) {
            $table->id('id_vehiculo');
            $table->integer('tipo_vehiculo');
            $table->string('placa', 20);
            $table->string('marca', 15);
            $table->string('modelo', 4);
            $table->string('color', 20);
            $table->string('ruta_documento', 255);
            $table->boolean('asignado');
        });
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::dropIfExists('vehiculos');
    }
};
