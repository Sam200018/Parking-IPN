package mx.ipn.escom.bautistas.parking.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun <T> SearchListComponent(
    modifier: Modifier = Modifier,
    searchLabel: String,
    value: String,
    emptyMsg: String = "",
    buttonLabel: String = "",
    items: List<T>,
    searchAction: (String) -> Unit,
    navAction: () -> Unit,
    content: @Composable (item: T) -> Unit  // Ajusta la firma para recibir un item
) {

    Column(
        modifier
            .fillMaxHeight()
            .padding(20.dp)
    ) {

        TextFieldComponent(
            modifier = modifier.fillMaxWidth(),
            label = searchLabel,
            value = value
        ) {
            searchAction(it)
        }

        if (items.isNotEmpty()) {
            LazyColumn() {
                itemsIndexed(items) { index: Int, item: T ->
                    content(item)
                }
            }
        } else {
            Box(modifier = modifier.fillMaxHeight(), Alignment.Center) {
                Column {
                    Text(
                        text = emptyMsg,
                        fontSize = 22.sp
                    )
                    ButtonComponent(
                        fontSize = 24.sp,
                        label = buttonLabel
                    ) {
                        navAction()
                    }
                }
            }
        }
    }

}
