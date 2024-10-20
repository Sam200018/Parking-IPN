package mx.ipn.escom.bautistas.parking.model

import kotlinx.serialization.Serializable

@Serializable
data class IncidentsResponse(
    val incidentes: List<Incident>
)
