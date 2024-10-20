package mx.ipn.escom.bautistas.parking.data.incidents

import mx.ipn.escom.bautistas.parking.data.pusher.PusherManager
import mx.ipn.escom.bautistas.parking.model.IncidentsResponse
import mx.ipn.escom.bautistas.parking.model.SimpleResponse
import javax.inject.Inject

interface IncidentsRepository {

    fun onConnected(onAction: () -> Unit)

    suspend fun getIncidentsSync(): SimpleResponse

    fun getIncidentsConnect(
        channelName: String,
        eventName: String,
        onEvent: (String) -> Unit
    )

    fun disconnect()

    suspend fun getAllIncidents(): IncidentsResponse
}

class IncidentsRepositoryImpl
@Inject constructor(
    private val incidentsDataSource: IncidentsDataSource,
    private val pusherManager: PusherManager,
) : IncidentsRepository {

    override fun onConnected(onAction: () -> Unit) = pusherManager.onConnected(onAction)

    override suspend fun getIncidentsSync(): SimpleResponse = incidentsDataSource.getAllIncidentsSync()

    override fun getIncidentsConnect(
        channelName: String,
        eventName: String,
        onEvent: (String) -> Unit
    ) = pusherManager.connect(channelName, eventName, onEvent)

    override fun disconnect() = pusherManager.disconnect()
    override suspend fun getAllIncidents(): IncidentsResponse = incidentsDataSource.getAllIncidents()

}