package br.com.gabrielkulzer.conversordemoedas.repository

import android.content.Context
import android.util.Log
import br.com.gabrielkulzer.conversordemoedas.model.IObserver
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class RateAPI {

    fun getCurrency(context: Context,observer: IObserver){
        val url = "https://api.hgbrasil.com/finance?key=ce3131bb"
        val queue = Volley.newRequestQueue(context)
        val strReq = StringRequest(Request.Method.GET,
            url,
            {response -> val jsonObject = JSONObject(response)
            val results = jsonObject.getJSONObject("results")
            val currencies = results.getJSONObject("currencies")
            val dollar = currencies.getJSONObject("USD").getDouble("buy")
            val euro = currencies.getJSONObject("EUR").getDouble("buy")
            val map = mutableMapOf<String,Double>()
                map["dollar"] = dollar
                map["euro"] = euro

                observer.updateUI(map)
            },
            { error -> Log.e("APPDEBUG","Erro:${error.message}")})
        queue.add(strReq)
    }
}