package mx.ipn.escom.bautistas.parking.ui.components

import android.Manifest
import android.graphics.Bitmap
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import mx.ipn.escom.bautistas.parking.R

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PhotoButton(
    modifier: Modifier = Modifier,
    label: String,
    icon: ImageVector,
    width: Dp = 217.dp,
    value: Bitmap? = null,
    filename: String = "",
    contentScale: ContentScale = ContentScale.FillHeight,
    goCamera: () -> Unit,
) {

    val cameraPermission: PermissionState =
        rememberPermissionState(permission = Manifest.permission.CAMERA)


    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(280.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier.fillMaxWidth()
        ) {
            Text(text = label, fontSize = 24.sp)
            Box(
                modifier = modifier
                    .size(width, 274.dp)
                    .background(color = colorResource(id = R.color.white)),
                contentAlignment = Alignment.Center,
            ) {
                BitmapImage(bitmap = value, contentScale = contentScale, filename = filename)
                IconButton(onClick = {
                    if (cameraPermission.status.isGranted) {
                        goCamera()
                    } else {
                        cameraPermission.launchPermissionRequest()
                    }
                }, modifier.size(80.dp)) {
                    Icon(
                        imageVector = icon,
                        contentDescription = "",
                        tint = colorResource(id = R.color.guinda),
                        modifier = modifier.size(60.dp)
                    )
                }
            }
        }
    }
}
