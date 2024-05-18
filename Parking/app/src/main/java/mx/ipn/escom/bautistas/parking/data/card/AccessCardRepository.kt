package mx.ipn.escom.bautistas.parking.data.card

import mx.ipn.escom.bautistas.parking.model.AccessCardResponse
import mx.ipn.escom.bautistas.parking.model.GenAccessCardRequest
import mx.ipn.escom.bautistas.parking.model.SimpleResponse
import javax.inject.Inject

interface AccessCardRepository {

    suspend fun genAccessCard(genAccessCardRequest: GenAccessCardRequest): SimpleResponse

    suspend fun getAllCards(idCuenta: Long?): AccessCardResponse

    fun getCardHash(): String?

    fun saveHash(hash: String)


}

class AccessCardRepositoryImpl @Inject constructor(
    private val accessCardDataSource: AccessCardDataSource,
    private val cardLocalSource: CardLocalSource
) : AccessCardRepository {

    override suspend fun genAccessCard(genAccessCardRequest: GenAccessCardRequest) =
        accessCardDataSource.genAccessCard(genAccessCardRequest)

    override suspend fun getAllCards(idCuenta: Long?) = accessCardDataSource.getAllCards(idCuenta)
    override fun getCardHash(): String? = cardLocalSource.getHash()
    override fun saveHash(hash: String) = cardLocalSource.saveHash(hash)
}