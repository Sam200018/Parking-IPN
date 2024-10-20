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
        Schema::create('Incidentes_Vehiculo', function (Blueprint $table) {
            $table->id('id_incidente');
            $table->foreignId('id_tarjeta_acceso')->constrained('tarjetas_acceso')->onDelete('cascade');
            $table->string('titulo', 100);
            $table->string('detalles', 255);
            $table->boolean('cerrado');
        });
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::dropIfExists('Incidentes_Vehiculo');
    }
};
