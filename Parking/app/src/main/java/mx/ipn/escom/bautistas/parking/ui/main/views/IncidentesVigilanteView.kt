package mx.ipn.escom.bautistas.parking.ui.main.views

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import mx.ipn.escom.bautistas.parking.R
import mx.ipn.escom.bautistas.parking.ui.components.IncidentCard
import mx.ipn.escom.bautistas.parking.ui.components.SearchListComponent
import mx.ipn.escom.bautistas.parking.ui.main.interactions.VigilanteUiState

@Composable
fun IncidentesVigilanteView(
    modifier: Modifier = Modifier,
    vigilanteUiState: VigilanteUiState,
    navToNewIncident: () -> Unit,
    navToIncidentDetail: (String) -> Unit
) {

    SearchListComponent(
        searchLabel = stringResource(id = R.string.search_incident_by_title),
        value = "",
        emptyMsg = stringResource(id = R.string.element_not_found),
        buttonLabel = stringResource(id = R.string.gen_incident),
        items = vigilanteUiState.incidentList,
        searchAction = {

        },
        navAction = { navToNewIncident() }
    ) {
        IncidentCard(incident = it) { incidentId ->
            navToIncidentDetail(incidentId.toString())
        }
    }

}