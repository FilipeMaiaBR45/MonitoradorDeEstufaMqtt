package com.example.monitoradordeestufamqtt.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.monitoradordeestufamqtt.MqttHandler
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.IMqttActionListener
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.IMqttToken
import org.eclipse.paho.client.mqttv3.MqttCallback
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended
import org.eclipse.paho.client.mqttv3.MqttClient
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.eclipse.paho.client.mqttv3.MqttException
import org.eclipse.paho.client.mqttv3.MqttMessage



class MqttRepository(context: Context) {
    var mqttClient: MqttAndroidClient
    private val serverURI = "tcp://broker.emqx.io:1883"


    val messageLiveData = MutableLiveData<String>()
    val valueLuz = MutableLiveData<String>()

    init {
        mqttClient = MqttAndroidClient(context, serverURI, "kotlin_client")
        mqttClient?.setCallback(object : MqttCallbackExtended {
            override fun connectComplete(reconnect: Boolean, serverURI: String?) {
                if (!reconnect) {
                    subscribe("/temperatura")
                    subscribe("/luminosidade")
                }
            }

            override fun connectionLost(cause: Throwable?) {
            }

            override fun messageArrived(topic: String?, message: MqttMessage?) {
                if (topic!! == "/temperatura"){
                    messageLiveData.postValue(message.toString())
                }
                if (topic == "/luminosidade"){
                    valueLuz.postValue(message.toString())
                }

                Log.d(MqttHandler.TAG, "Receive message: ${message.toString()} from topic: $topic")
            }

            override fun deliveryComplete(token: IMqttDeliveryToken?) {
            }
        })
    }

    fun connect() {
        val options = MqttConnectOptions()
        try {
            mqttClient.connect(options, null, object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken?) {
                    Log.d(MqttHandler.TAG, "Connection success")
                }

                override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                    Log.d(MqttHandler.TAG, "Connection failure")                }
            })
        } catch (e: MqttException) {
            e.printStackTrace()
        }
    }

    fun subscribe(topic: String) {
        try {
            mqttClient.subscribe(topic, 1)
        } catch (e: MqttException) {
            e.printStackTrace()
        }
    }

    fun disconnect() {
        mqttClient.disconnect()
    }

}