package mx.ipn.escom.bautistas.parking.ui.main.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ScannerViewModel @Inject constructor() : ViewModel() {


    fun scannerCardToken(cardToken: String) {
        Log.i("CardToken", cardToken)
    }
}