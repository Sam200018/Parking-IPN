package mx.ipn.escom.bautistas.parking.ui.main.views

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.LinearLayout
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.BottomStart
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import mx.ipn.escom.bautistas.parking.ui.main.viewmodels.NewUserViewModel
import java.util.concurrent.Executor
import kotlin.math.min

@Composable
fun CamaraScreen(
    modifier: Modifier = Modifier,
    photo: Bitmap? = null,
    backAction: (Bitmap) -> Unit
) {
    CamaraContent(
        onPhotoCaptured = {
            backAction(it)
        },
        lastCapturedPhoto = photo
    )
}

@Composable
fun CamaraContent(
    modifier: Modifier = Modifier,
    onPhotoCaptured: (Bitmap) -> Unit,
    lastCapturedPhoto: Bitmap? = null
) {
    val context: Context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val camaraController = remember {
        LifecycleCameraController(context)
    }

    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text(text = "Take photo") },
                onClick = {
                    capturePhoto(context, camaraController, onPhotoCaptured)
                },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Camera,
                        contentDescription = "Camera capture icon"
                    )
                }
            )
        }
    ) {
        Box(modifier = modifier.padding(it)) {
            AndroidView(
                factory = { context ->
                    PreviewView(context).apply {
                        layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
//                        setBackgroundResource(Color.Black.hashCode())
                        scaleType = PreviewView.ScaleType.FILL_START
                    }.also { previewView ->
                        previewView.controller = camaraController
                        camaraController.bindToLifecycle(lifecycleOwner)

                    }
                }
            )
            CameraOverlay()
            if (lastCapturedPhoto != null) {
                LastPhotoPreview(
                    modifier = modifier.align(alignment = BottomStart),
                    lastCapturedPhoto = lastCapturedPhoto
                )
            }
        }
    }
}

fun capturePhoto(
    context: Context,
    cameraController: LifecycleCameraController,
    onPhotoCaptured: (Bitmap) -> Unit
) {
    val mainExecutor: Executor = ContextCompat.getMainExecutor(context)

    cameraController.takePicture(mainExecutor, object : ImageCapture.OnImageCapturedCallback() {
        override fun onCaptureSuccess(image: ImageProxy) {
            val correctedBitmap: Bitmap = image
                .toBitmap()


            onPhotoCaptured(correctedBitmap)
            image.close()
        }

        override fun onError(exception: ImageCaptureException) {
            Log.e("CameraContent", "Error capturing image", exception)
        }
    })
}


@Composable
private fun LastPhotoPreview(
    modifier: Modifier = Modifier,
    lastCapturedPhoto: Bitmap
) {

    val capturedPhoto: ImageBitmap =
        remember(lastCapturedPhoto.hashCode()) { lastCapturedPhoto.asImageBitmap() }

    Card(
        modifier = modifier
            .size(128.dp)
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = MaterialTheme.shapes.large
    ) {
        Image(
            bitmap = capturedPhoto,
            contentDescription = "Last captured photo",
            contentScale = androidx.compose.ui.layout.ContentScale.Crop
        )
    }
}

@Composable
fun CameraOverlay(modifier: Modifier = Modifier) {
    Box(modifier = Modifier.fillMaxSize()) {
        Canvas(modifier = modifier.matchParentSize()) {
            val canvasWidth = size.width
            val canvasHeight = size.height
            val sideLength =
                min(canvasWidth, canvasHeight) * 1f  // Ajusta el tamaño según sea necesario
            val topLeftOffsetX = (canvasWidth - sideLength) / 2
            val topLeftOffsetY = (canvasHeight - sideLength) / 2

            drawRect(
                color = Color.Black.copy(alpha = 0.5f),
                size = Size(canvasWidth, canvasHeight)
            )
            drawRect(
                color = Color.Transparent,
                topLeft = Offset(topLeftOffsetX, topLeftOffsetY),
                size = Size(sideLength, sideLength),
                blendMode = BlendMode.Clear
            )
        }
    }
}


