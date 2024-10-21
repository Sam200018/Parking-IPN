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
        Schema::create('Sustitutos', function (Blueprint $table) {
            $table->id('id_sustitucion');
            $table->foreignId('id_tarjeta_acceso')->constrained('tarjetas_acceso','id_tarjeta_acceso')->onDelete('cascade');
            $table->foreignId('id_cuenta')->constrained('cuentas')->onDelete('cascade');
            $table->timestamps();
        });
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::dropIfExists('Sustitutos');
    }
};
