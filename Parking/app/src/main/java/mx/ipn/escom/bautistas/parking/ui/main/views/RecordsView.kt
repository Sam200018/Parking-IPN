package mx.ipn.escom.bautistas.parking.ui.main.views


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import mx.ipn.escom.bautistas.parking.R
import mx.ipn.escom.bautistas.parking.ui.components.RecordCardComponent
import mx.ipn.escom.bautistas.parking.ui.components.TextFieldComponent
import mx.ipn.escom.bautistas.parking.ui.main.interactions.VigilanteUiState

@Composable
fun RecordsView(
    modifier: Modifier = Modifier,
    vigilanteUiState: VigilanteUiState
    ) {

    Column(
        modifier
            .fillMaxSize()
            .padding(20.dp)) {
        TextFieldComponent(
            modifier.fillMaxWidth(),
            label = stringResource(id = R.string.search_label),
            icon = {
                Icon(
                    imageVector = Icons.Outlined.Search,
                    contentDescription = stringResource(id = R.string.search_label),
                    tint = colorResource(id = R.color.guinda)
                )
            }
        ) {

        }
        LazyColumn(modifier = modifier
            .fillMaxSize(),
            ) {
            items(
                count = vigilanteUiState.recordList.size
            ){
                RecordCardComponent()
                Spacer(modifier = modifier.height(10.dp))
            }
        }
    }
}

