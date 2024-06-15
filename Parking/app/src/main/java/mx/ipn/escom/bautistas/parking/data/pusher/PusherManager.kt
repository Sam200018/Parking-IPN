package mx.ipn.escom.bautistas.parking.data.pusher

import android.util.Log
import com.pusher.client.Pusher
import com.pusher.client.PusherOptions
import com.pusher.client.channel.Channel
import com.pusher.client.connection.ConnectionEventListener
import com.pusher.client.connection.ConnectionState
import com.pusher.client.connection.ConnectionStateChange
import javax.inject.Inject

class PusherManager @Inject constructor(
    pusherKey: String,
    pusherCluster: String
) {

    private var pusher: Pusher
    private val channels = mutableMapOf<String, Channel>()

    init {
        val options = PusherOptions().setCluster(pusherCluster)
        pusher = Pusher(pusherKey, options)
    }

    fun connect(channelName: String, eventName: String, onEvent: (String) -> Unit) {
        if (!channels.containsKey(channelName)) {
            val channel = pusher.subscribe(channelName)
            channels[channelName] = channel
            pusher.connect(object : ConnectionEventListener {
                override fun onConnectionStateChange(change: ConnectionStateChange) {
                    Log.i(
                        "Pusher",
                        "State changed from ${change.previousState} to ${change.currentState}"
                    )
                }

                override fun onError(
                    message: String,
                    code: String,
                    e: Exception
                ) {
                    Log.i(
                        "Pusher",
                        "There was a problem connecting! code ($code), message ($message), exception($e)"
                    )
                }
            }, ConnectionState.ALL)



            channel.bind(eventName) { event ->
                onEvent(event.data)
            }
        }else{
            if (pusher.connection.state != ConnectionState.CONNECTED) {
                pusher.connect()
            }
        }
    }

    fun disconnect() {
        pusher.disconnect()
    }

    fun onConnected(action: () -> Unit) {
        pusher.connect(object : ConnectionEventListener {
            override fun onConnectionStateChange(change: ConnectionStateChange) {
                if (change.currentState === ConnectionState.CONNECTED) {
                    action()
                }
            }

            override fun onError(message: String, code: String?, e: Exception?) {
                Log.e("Pusher", "Error al conectar: $message")
            }
        }, ConnectionState.ALL)
    }
}