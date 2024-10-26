package mx.ipn.escom.bautistas.parking.data.records

import mx.ipn.escom.bautistas.parking.data.pusher.PusherManager
import mx.ipn.escom.bautistas.parking.model.InfoRecordRequest
import mx.ipn.escom.bautistas.parking.model.InfoRecordResponse
import mx.ipn.escom.bautistas.parking.model.RecordsResponse
import mx.ipn.escom.bautistas.parking.model.RegisterMovRequest
import mx.ipn.escom.bautistas.parking.model.SimpleResponse
import javax.inject.Inject

interface RecordsRepository {

    suspend fun registerMov(registerMovRequest: RegisterMovRequest): SimpleResponse

    fun getRecordsConnect(channelName: String, eventName: String, onEvent: (String) -> Unit)

    fun disconnect()

    suspend fun getAllRecordsSync(): SimpleResponse

    fun onConnected(onAction: () -> Unit)

    suspend fun getInfoToRecord(infoRecordRequest: InfoRecordRequest): InfoRecordResponse

    suspend fun getAllRecords(): RecordsResponse

}

class RecordsRepositoryImpl @Inject constructor(
    private val recordsDataSource: RecordsDataSource,
    private val pusherManager: PusherManager,
) : RecordsRepository {
    override suspend fun registerMov(registerMovRequest: RegisterMovRequest) =
        recordsDataSource.registerMov(registerMovRequest)

    override fun getRecordsConnect(
        channelName: String,
        eventName: String,
        onEvent: (String) -> Unit
    ) = pusherManager.connect(channelName, eventName, onEvent)

    override fun disconnect() = pusherManager.disconnect()
    override suspend fun getAllRecordsSync(): SimpleResponse = recordsDataSource.getAllRecordsSync()
    override fun onConnected(onAction: () -> Unit) = pusherManager.onConnected(onAction)
    override suspend fun getInfoToRecord(infoRecordRequest: InfoRecordRequest): InfoRecordResponse =
        recordsDataSource.getInfoToRecord(infoRecordRequest)

    override suspend fun getAllRecords(): RecordsResponse = recordsDataSource.getAllRecords()
}