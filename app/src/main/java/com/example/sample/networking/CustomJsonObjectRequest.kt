package com.example.sample.networking

import android.util.Base64
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONObject
import java.util.*
import javax.xml.transform.ErrorListener
import kotlin.collections.HashMap

class CustomJsonObjectRequest(
    method: Int, url: String,
    jsonObject: JSONObject?,
    listener: Response.Listener<JSONObject>,
    errorListener: Response.ErrorListener,
    credentials: String
) : JsonObjectRequest(method, url, jsonObject, listener, errorListener) {

    private var mCredentials: String = credentials

    @Throws(AuthFailureError::class)
    override fun getHeaders(): MutableMap<String, String> {
        val headers = HashMap<String, String>()
        //headers["Content-Type"] = "application/json"
        val auth = "Basic " + Base64.encodeToString(mCredentials.toByteArray(),
            Base64.NO_WRAP)
        headers["Authorization"] = auth

        return headers
    }
}