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
        Schema::create('Tarjetas_Acceso', function (Blueprint $table) {
            $table->id('id_tarjeta_acceso');
            $table->foreignId('id_cuenta')->nullable()->constrained('Cuentas', 'id_cuenta')->onDelete('cascade');
            $table->foreignId('id_vehiculo')->constrained('Vehiculos', 'id_vehiculo')->onDelete('cascade');
            $table->string('token', 255)->nullable();
        });
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::dropIfExists('Tarjetas_Acceso');
    }
};
