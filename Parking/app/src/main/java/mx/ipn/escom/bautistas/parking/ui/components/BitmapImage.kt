package mx.ipn.escom.bautistas.parking.ui.components

import android.graphics.Bitmap
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import mx.ipn.escom.bautistas.parking.R

@Composable
fun BitmapImage(
    modifier: Modifier = Modifier,
    bitmap: Bitmap? = null,
    contentScale: ContentScale = ContentScale.FillHeight
) {
    AsyncImage(
        model = bitmap,
        contentDescription = "",
        placeholder = painterResource(id = R.drawable.ic_launcher_foreground),
        contentScale = contentScale,
        modifier = modifier.fillMaxSize()
    )

}