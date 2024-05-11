package mx.ipn.escom.bautistas.parking.data.card

import mx.ipn.escom.bautistas.parking.model.GenAccessCardRequest
import mx.ipn.escom.bautistas.parking.model.SimpleResponse
import javax.inject.Inject

interface AccessCardRepository {

    suspend fun genAccessCard(genAccessCardRequest: GenAccessCardRequest): SimpleResponse

}

class AccessCardRepositoryImpl @Inject constructor(
    private val accessCardDataSource: AccessCardDataSource
) : AccessCardRepository {

    override suspend fun genAccessCard(genAccessCardRequest: GenAccessCardRequest) =
        accessCardDataSource.genAccessCard(genAccessCardRequest)
}