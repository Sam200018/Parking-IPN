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
        Schema::create('Cuentas', function (Blueprint $table) {
            $table->id('id_cuenta');
            $table->foreignId('id_persona')->nullable()->constrained('Personas','id_personas')->onDelete('cascade');
            $table->foreignId('id_rol')->constrained('Roles','id_rol')->onDelete('cascade');
            $table->foreignId('id_prog_academico')->nullable()->constrained('Programas_Academicos','id_prog_academico')->onDelete('cascade');
            $table->boolean('activo');
            $table->string('correo')->unique();
            $table->string('password');
        });
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::dropIfExists('Cuentas');
    }
};
