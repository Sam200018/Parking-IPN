package mx.ipn.escom.bautistas.parking.ui.main.views


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import mx.ipn.escom.bautistas.parking.R
import mx.ipn.escom.bautistas.parking.ui.components.RecordCardComponent
import mx.ipn.escom.bautistas.parking.ui.components.SearchListComponent
import mx.ipn.escom.bautistas.parking.ui.main.interactions.VigilanteUiState

@Composable
fun RecordsView(
    modifier: Modifier = Modifier,
    vigilanteUiState: VigilanteUiState
) {
    SearchListComponent(
        modifier = modifier.fillMaxWidth(),
        searchLabel = stringResource(id = R.string.search_label),
        value = "",
        emptyMsg = stringResource(id = R.string.element_not_found),
        items = vigilanteUiState.recordList,
        searchAction = {
//                manualRegistrationViewModel.onSearchVehicleInputChanged(it)
        },
    ) {


        RecordCardComponent( transaction = it)
    }
}

