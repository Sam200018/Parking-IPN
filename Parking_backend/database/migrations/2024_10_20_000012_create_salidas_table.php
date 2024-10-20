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
        Schema::create('Salidas', function (Blueprint $table) {
            $table->id('id_salida');
            $table->foreignId('id_registro')->constrained('Registros', 'id_registro')->onDelete('cascade');
            $table->foreignId('id_cuenta')->constrained('Cuentas', 'id_cuenta')->onDelete('cascade');
            $table->timestamp('check');
            $table->string('observaciones')->nullable();
        });
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::dropIfExists('Salidas');
    }
};
