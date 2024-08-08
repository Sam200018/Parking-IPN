package mx.ipn.escom.bautistas.parking.data.card

import mx.ipn.escom.bautistas.parking.model.AccessCard
import mx.ipn.escom.bautistas.parking.model.AccessCardResponse
import mx.ipn.escom.bautistas.parking.model.CardInfoTokeResponse
import mx.ipn.escom.bautistas.parking.model.GenAccessCardRequest
import mx.ipn.escom.bautistas.parking.model.SimpleResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AccessCardDataSource {

    @POST("gen_access_card")
    suspend fun genAccessCard(@Body genAccessCardRequest: GenAccessCardRequest): SimpleResponse

    @GET("get_access_cards")
    suspend fun getAllCards(
        @Query("id_cuenta") idCuenta: Long? = null,
    ): AccessCardResponse


    @GET("get_info_card")
    suspend fun getCardInfo(
        @Query("card_token") cardToken: String? = null
    ): CardInfoTokeResponse
}