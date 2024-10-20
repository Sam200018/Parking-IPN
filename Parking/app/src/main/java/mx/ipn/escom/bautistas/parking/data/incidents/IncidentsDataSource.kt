package mx.ipn.escom.bautistas.parking.data.incidents

import mx.ipn.escom.bautistas.parking.model.IncidentsResponse
import mx.ipn.escom.bautistas.parking.model.SimpleResponse
import retrofit2.http.GET

interface IncidentsDataSource {

    @GET("get_all_incidents_sync")
    suspend fun getAllIncidentsSync(): SimpleResponse

    @GET("get_all_incidents")
    suspend fun getAllIncidents(): IncidentsResponse

}