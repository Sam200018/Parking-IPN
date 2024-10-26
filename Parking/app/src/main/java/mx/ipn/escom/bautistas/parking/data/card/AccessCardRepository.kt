package mx.ipn.escom.bautistas.parking.data.card

import mx.ipn.escom.bautistas.parking.data.pusher.PusherManager
import mx.ipn.escom.bautistas.parking.model.AccessCardResponse
import mx.ipn.escom.bautistas.parking.model.CardInfoTokeResponse
import mx.ipn.escom.bautistas.parking.model.GenAccessCardRequest
import mx.ipn.escom.bautistas.parking.model.SimpleResponse
import javax.inject.Inject

interface AccessCardRepository {

    suspend fun genAccessCard(genAccessCardRequest: GenAccessCardRequest): SimpleResponse

    suspend fun getAllCards(idCuenta: Long?): AccessCardResponse

    fun getCardHash(): String?

    fun saveHash(hash: String)

    suspend fun getInfoCard(cardToken: String): CardInfoTokeResponse

    fun getAccessCardsConnect(channelName: String, eventName: String, onEvent: (String) -> Unit)
    fun disconnect()

    fun onConnected(onAction: () -> Unit)

}

class AccessCardRepositoryImpl @Inject constructor(
    private val accessCardDataSource: AccessCardDataSource,
    private val cardLocalSource: CardLocalSource,
    private val pusherManager: PusherManager,
) : AccessCardRepository {

    override suspend fun genAccessCard(genAccessCardRequest: GenAccessCardRequest) =
        accessCardDataSource.genAccessCard(genAccessCardRequest)

    override suspend fun getAllCards(idCuenta: Long?) = accessCardDataSource.getAllCards(idCuenta)
    override fun getCardHash(): String? = cardLocalSource.getHash()
    override fun saveHash(hash: String) = cardLocalSource.saveHash(hash)
    override suspend fun getInfoCard(cardToken: String): CardInfoTokeResponse =
        accessCardDataSource.getCardInfo(cardToken)

    override fun getAccessCardsConnect(
        channelName: String,
        eventName: String,
        onEvent: (String) -> Unit
    ) = pusherManager.connect(
        channelName,
        eventName,
        onEvent
    )

    override fun disconnect() = pusherManager.disconnect()
    override fun onConnected(onAction: () -> Unit) = pusherManager.onConnected(onAction)


}