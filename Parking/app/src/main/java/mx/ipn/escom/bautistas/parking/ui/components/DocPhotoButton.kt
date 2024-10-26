package mx.ipn.escom.bautistas.parking.ui.components

import android.app.Activity.RESULT_OK
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.mlkit.vision.documentscanner.GmsDocumentScannerOptions
import com.google.mlkit.vision.documentscanner.GmsDocumentScannerOptions.RESULT_FORMAT_JPEG
import com.google.mlkit.vision.documentscanner.GmsDocumentScannerOptions.SCANNER_MODE_FULL
import com.google.mlkit.vision.documentscanner.GmsDocumentScanning
import com.google.mlkit.vision.documentscanner.GmsDocumentScanningResult
import mx.ipn.escom.bautistas.parking.R
import mx.ipn.escom.bautistas.parking.ui.main.MainActivity

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun DocPhotoButton(
    modifier: Modifier = Modifier,
    label: String,
    icon: ImageVector = Icons.Default.AddAPhoto,
    value: Bitmap? = null,
    filename: String = "",
    mainActivity: MainActivity,
    result: (Uri,Context) -> Unit
) {
    val context = LocalContext.current
    val options = GmsDocumentScannerOptions.Builder()
        .setScannerMode(SCANNER_MODE_FULL)
        .setGalleryImportAllowed(true)
        .setPageLimit(1)
        .setResultFormats(RESULT_FORMAT_JPEG)
        .build()

    val scanner = GmsDocumentScanning.getClient(options)

    val scannerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult(),
        onResult = { result ->
            if (result.resultCode == RESULT_OK) {
                val result =
                    GmsDocumentScanningResult.fromActivityResultIntent(
                        result.data
                    )
                result?.pages?.map {
                    result(it.imageUri,context)
                }
            }
        }
    )

    PhotoButton(
        modifier = modifier.height(250.dp),
        label = label,
        width = 380.dp,
        icon = icon,
        value = value,
        contentScale = ContentScale.FillWidth
    ) {
        scanner.getStartScanIntent(mainActivity).addOnSuccessListener {
            scannerLauncher.launch(IntentSenderRequest.Builder(it).build())
        }.addOnFailureListener {
            Toast.makeText(
                context,
                it.message,
                Toast.LENGTH_LONG,
            ).show()
        }.addOnCanceledListener {
            Toast.makeText(
                context,
                "Cancelado",
                Toast.LENGTH_LONG,
            ).show()
        }
    }
}