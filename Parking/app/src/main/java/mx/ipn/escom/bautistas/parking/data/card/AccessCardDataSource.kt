package mx.ipn.escom.bautistas.parking.data.card

import mx.ipn.escom.bautistas.parking.model.GenAccessCardRequest
import mx.ipn.escom.bautistas.parking.model.SimpleResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AccessCardDataSource {

    @POST("gen_access_card")
    suspend fun genAccessCard(@Body genAccessCardRequest: GenAccessCardRequest): SimpleResponse
}