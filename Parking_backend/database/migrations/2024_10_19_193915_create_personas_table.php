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
        Schema::create('Personas', function (Blueprint $table) {
            $table->id('id_persona');
            $table->string('nombre', 45);
            $table->string('a_paterno', 45);
            $table->string('a_materno', 45);
            $table->string('id_ipn', 12)->nullable();
            $table->string('ruta_identificacion', 255);
            $table->string('ruta_fotografia', 255);
            $table->string('numero_contacto', 15);
        });
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::dropIfExists('Personas');
    }
};
