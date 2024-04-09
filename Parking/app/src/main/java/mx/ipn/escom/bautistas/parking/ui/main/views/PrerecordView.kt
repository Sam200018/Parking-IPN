package mx.ipn.escom.bautistas.parking.ui.main.views


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import mx.ipn.escom.bautistas.parking.R
import mx.ipn.escom.bautistas.parking.ui.components.FloatingActionButtonComponent
import mx.ipn.escom.bautistas.parking.ui.components.TagCarousel
import mx.ipn.escom.bautistas.parking.ui.components.TextFieldComponent
import mx.ipn.escom.bautistas.parking.ui.components.TopBarComponent

@Composable
fun PrerecordView(
    modifier: Modifier = Modifier,
    navSelectUser: () -> Unit,

    ) {
    Scaffold(
        topBar = {

            TopBarComponent(title = stringResource(id = R.string.pre_record_label))
        },
        floatingActionButton = {
            FloatingActionButtonComponent {
                navSelectUser()
            }
        }
    ) {
        Box(modifier.padding(it)) {
            Column(
                modifier
                    .fillMaxSize()
                    .padding(28.dp)
            ) {
                TextFieldComponent(
                    modifier.fillMaxWidth(),
                    label = stringResource(id = R.string.search_label),
                    icon = Icons.Outlined.Search
                ) {

                }
                TagCarousel()

            }
        }
    }
}