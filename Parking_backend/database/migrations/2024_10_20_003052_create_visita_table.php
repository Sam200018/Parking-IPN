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
        Schema::create('Visitas', function (Blueprint $table) {
            $table->id('id_visita');
            $table->foreignId('id_persona')->constrained('personas')->onDelete('cascade');
            $table->foreignId('id_vehiculo')->constrained('vehiculos')->onDelete('cascade');
            $table->integer('veces_ingresadas');
        });
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::dropIfExists('Visitas');
    }
};
