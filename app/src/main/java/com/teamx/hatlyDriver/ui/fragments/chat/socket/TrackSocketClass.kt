package com.teamx.hatlyDriver.ui.fragments.chat.socket

import com.google.gson.Gson
import com.teamx.hatlyDriver.constants.AppConstants.ApiConfiguration.Companion.BASE_URL_CHAT
import com.teamx.hatlyDriver.data.models.socket_models.ExceptionData
import com.teamx.hatlyDriver.ui.fragments.chat.socket.model.remianingTimeSocketData.RemianingTimeSocketData
import io.socket.client.Ack
import io.socket.client.IO
import io.socket.client.Socket
import org.json.JSONObject
import timber.log.Timber

object TrackSocketClass {

    var trackSocket: Socket? = null

    fun connectRiderTrack(
        token: String,
        orderId: String
    ) {

        val options = IO.Options()
        val headers: MutableMap<String, List<String>> = HashMap()


        headers["Authorization"] =
            listOf(token)


        options.extraHeaders = headers


        val u: Boolean? = trackSocket?.connected()

        if (u == null) {
            trackSocket = IO.socket("$BASE_URL_CHAT/track", options)
        } else if (!u) {
            trackSocket = IO.socket("$BASE_URL_CHAT/track", options)


        }

        trackSocket?.connect()
        Timber.tag("MessageSocketClass").d("EVENT_CONNECT: ${trackSocket?.connected()}")


        trackSocket?.on(Socket.EVENT_CONNECT) {
            onListenerEverything()
            startRide(orderId)




        }
        trackSocket?.on(Socket.EVENT_CONNECT_ERROR) {
            Timber.tag("MessageSocketClass")
                .d("EVENT_CONNECT_ERROR22:${trackSocket?.connected()} ${it[0]}")

        }

    }


    val gson = Gson()


    private fun onListenerEverything() {

        trackSocket?.on("RIDE_STARTED") { args ->
            Timber.tag("MessageSocketClass").d("RIDE_STARTED: }${args.get(0)}")
        }

        trackSocket?.on("exception") { args ->

            try {
                val exception = gson.fromJson(args[0].toString(), ExceptionData::class.java)
                Timber.tag("MessageSocketClass").d("ExceptionData: ${exception.message}")
            } catch (e: java.lang.Exception) {
                Timber.tag("MessageSocketClass").d("ExceptionData: ${args[0]}")

            }

        }
        trackSocket?.on("RIDE_ENDED") { args ->

            Timber.tag("MessageSocketClass").d("RIDE_ENDED: }${args.get(0)}")


        }
        trackSocket?.on("PICKED_BY") { args ->
            Timber.tag("MessageSocketClass").d("PICKED_BY: }${args.get(0)}")
        }
        /*     trackSocket?.on("INCOMING_ORDERS") { args ->
                 val INCOMINGSOCKETORDER = gson.fromJson(args[0].toString(), IncomingOrderSocketData::class.java)
                 activeOrderArrayList.add(INCOMINGSOCKETORDER)
                 incomingOrderAdapter.notifyDataSetChanged()
                 Timber.tag("MessageSocketClass").d("INCOMING_ORDERS: }${args.get(0)}")

             }*/
        trackSocket?.on("CURRENT_STATUS") { args ->
            Timber.tag("MessageSocketClass").d("CURRENT_STATUS: }${args.get(0)}")

        }
        trackSocket?.on("REMANING_TIME") { args ->
            Timber.tag("MessageSocketClass").d("REMANING_TIME: }${args.get(0)}")
                val REMANING_TIME = gson.fromJson(args[0].toString(), RemianingTimeSocketData::class.java)
               /* activeOrderArrayList.add(INCOMINGSOCKETORDER)
                incomingOrderAdapter.notifyDataSetChanged()*/
                Timber.tag("MessageSocketClass").d("INCOMING_ORDERS: }${args.get(0)}")

            }

        }


        fun startRide(orderId: String) {
            val data = JSONObject().put("orderId", orderId)
            Timber.tag("MessageSocketClass").d("startRide:$data ")
            trackSocket?.emit("START_RIDE", data, object : Ack {
                override fun call(vararg args: Any?) {

                    Timber.tag("MessageSocketClass").d("START_RIDE: ")
                }
            })
        }

        fun updateRide(lat: Double, lng: Double) {
            val data = JSONObject().put("lat", lat).put("lng", lng)
            Timber.tag("MessageSocketClass").d("UPDATE_LOCATION:$data ")
            trackSocket?.emit("UPDATE_LOCATION", data, object : Ack {
                override fun call(vararg args: Any?) {

                    Timber.tag("MessageSocketClass").d("UPDATE_LOCATION: ")
                }
            })
        }


        fun disconnect() {
            trackSocket?.disconnect()
            trackSocket?.on(Socket.EVENT_DISCONNECT) {
                Timber.tag("MessageSocketClass")
                    .d("EVENT_DISCONNECT: ${trackSocket?.connected()}")
            }
        }


//Events to listen for
//MEssage to Emit
//Listener provide runtime data
//always disconnect socket when changing fragment
//always start socket when when id is available
//always disconnect socket and null it if it is not connect for the first three time otherwise it will take memory and crash


    }
/*

interface IncomingOrderCallBack {
fun onIncomingOrderRecieve(incomingOrderSocketData: IncomingOrderSocketData)
fun onIncomingParcelRecieve(incomingParcelSocketData: IncomingParcelSocketData)
}*/
