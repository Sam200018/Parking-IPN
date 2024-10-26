package mx.ipn.escom.bautistas.parking.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import mx.ipn.escom.bautistas.parking.ui.main.interactions.AuthState

@Composable
fun ProfileCardComponent(modifier: Modifier = Modifier, authState: AuthState) {
    Box(
        modifier = Modifier
            .size(150.dp)
            .padding(10.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = modifier
                    .fillMaxSize(0.5f)
            ) {
                BitmapImage(
                    filename = authState.account?.persona?.rutaFotografia?: "",
                )
            }
            Text(
                "${authState.account?.persona?.nombre} ${authState.account?.persona?.aPaterno} ${authState.account?.persona?.aMaterno}",
                textAlign = TextAlign.Center,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

