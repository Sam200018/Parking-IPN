package mx.ipn.escom.bautistas.parking.data.vehicle


import mx.ipn.escom.bautistas.parking.model.CreateVehicleResponse
import mx.ipn.escom.bautistas.parking.model.GetVehiclesResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query


interface VehicleDataSource {
    @GET("get_all_vehicles")
    suspend fun getUsers(
        @Query("asignado") asignado: Boolean? = null,
        @Query("placa") placa: String? = null,
    ): GetVehiclesResponse

    @Multipart
    @POST("create_vehicle")
    suspend fun createVehicle(
        @Part("tipo_vehiculo") vehicleType: RequestBody,
        @Part("marca") brand: RequestBody,
        @Part("modelo") model: RequestBody,
        @Part("plate") placa: RequestBody,
        @Part("color") color: RequestBody,
        @Part documento: MultipartBody.Part,
    ):CreateVehicleResponse


}