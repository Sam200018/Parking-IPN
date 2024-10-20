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
        Schema::create('Registros', function (Blueprint $table) {
            $table->id('id_registro');
            $table->foreignId('id_tarjeta_acceso')->nullable()->constrained('Tarjetas_Acceso', 'id_tarjeta_acceso')->onDelete('cascade');
            $table->foreignId('id_token')->nullable()->constrained('Tokens', 'id_token')->onDelete('cascade');
            $table->foreignId('id_visita')->nullable()->constrained('Visitas', 'id_visita')->onDelete('cascade');
            $table->foreignId('id_cuenta')->constrained('Cuentas', 'id_cuenta')->onDelete('cascade');
        });
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::dropIfExists('Registros');
    }
};
