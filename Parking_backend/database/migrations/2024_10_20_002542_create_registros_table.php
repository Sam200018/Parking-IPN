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
            $table->foreignId('id_tarjeta_acceso')->nullable()->constrained('tarjetas_acceso')->onDelete('cascade');
            $table->foreignId('id_token')->nullable()->constrained('tokens')->onDelete('cascade');
            $table->foreignId('id_visita')->nullable()->constrained('visitas')->onDelete('cascade');
            $table->foreignId('id_cuenta')->constrained('cuentas')->onDelete('cascade');
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
