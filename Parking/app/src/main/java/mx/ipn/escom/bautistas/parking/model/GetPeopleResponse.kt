package mx.ipn.escom.bautistas.parking.model

import kotlinx.serialization.Serializable

@Serializable
data class GetPeopleResponse(var usuarios: List<Persona>)