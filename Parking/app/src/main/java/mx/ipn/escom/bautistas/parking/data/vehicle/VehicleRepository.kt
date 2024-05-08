package mx.ipn.escom.bautistas.parking.data.vehicle

import mx.ipn.escom.bautistas.parking.model.CreateVehicleResponse
import mx.ipn.escom.bautistas.parking.model.GetVehiclesResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Part
import javax.inject.Inject

interface VehicleRepository {

    suspend fun getAllVehicles(
        asignado: Boolean? = null,
        placa: String? = null
    ): GetVehiclesResponse

    suspend fun createVehicle(
        @Part("tipo_vehiculo") vehicleType: RequestBody,
        @Part("marca") brand: RequestBody,
        @Part("modelo") model: RequestBody,
        @Part("plate") placa: RequestBody,
        @Part("color") color: RequestBody,
        @Part documento: MultipartBody.Part,
    ): CreateVehicleResponse

}

class VehicleRepositoryImpl @Inject constructor(
    private val vehicleDataSource: VehicleDataSource
) : VehicleRepository {
    override suspend fun getAllVehicles(asignado: Boolean?, placa: String?): GetVehiclesResponse =
        vehicleDataSource.getUsers(asignado, placa)

    override suspend fun createVehicle(
        vehicleType: RequestBody,
        brand: RequestBody,
        model: RequestBody,
        placa: RequestBody,
        color: RequestBody,
        documento: MultipartBody.Part
    ): CreateVehicleResponse = vehicleDataSource.createVehicle(
        vehicleType,
        brand,
        model,
        placa,
        color,
        documento,
    )
}