package mx.ipn.escom.bautistas.parking.data.records

import mx.ipn.escom.bautistas.parking.data.pusher.PusherManager
import mx.ipn.escom.bautistas.parking.model.RegisterMovRequest
import mx.ipn.escom.bautistas.parking.model.SimpleResponse
import javax.inject.Inject

interface RecordsRepository {

    suspend fun registerMov(registerMovRequest: RegisterMovRequest): SimpleResponse

    fun getRecordsConnect(channelName: String, eventName: String, onEvent: (String) -> Unit)

    fun disconnect()

    suspend fun getAllRecords(): SimpleResponse

    fun onConnected(onAction: () -> Unit)

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
    override suspend fun getAllRecords(): SimpleResponse = recordsDataSource.getAllRecords()
    override fun onConnected(onAction: () -> Unit) = pusherManager.onConnected(onAction)
}