package mx.ipn.escom.bautistas.parking.data.records

import mx.ipn.escom.bautistas.parking.model.InfoRecordRequest
import mx.ipn.escom.bautistas.parking.model.InfoRecordResponse
import mx.ipn.escom.bautistas.parking.model.RecordsResponse
import mx.ipn.escom.bautistas.parking.model.RegisterMovRequest
import mx.ipn.escom.bautistas.parking.model.SimpleResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface RecordsDataSource {

    @POST("register_mov")
    suspend fun registerMov(@Body registerMovRequest: RegisterMovRequest): SimpleResponse

    @GET("get_all_records")
    suspend fun getAllRecords(): RecordsResponse

    @GET("get_all_records_sync")
    suspend fun getAllRecordsSync():SimpleResponse

    @POST("get_info_to_record")
    suspend fun getInfoToRecord(@Body infoRecordRequest: InfoRecordRequest): InfoRecordResponse

}