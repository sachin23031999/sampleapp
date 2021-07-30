package com.example.sample.viewModels

import android.app.Application
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.android.volley.Request
import com.android.volley.Response
import com.example.sample.AUTH_KEY
import com.example.sample.BASE_URL
import com.example.sample.data.Base
import com.example.sample.models.Details
import com.example.sample.networking.CustomJsonObjectRequest
import com.example.sample.networking.VolleySingleton
import com.example.sample.utils.ErrorListener
import com.google.gson.GsonBuilder
import com.sachin.milkdistributor.room.RoomDB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception
import kotlin.coroutines.coroutineContext

@Suppress("RedundantSamConstructor")
class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val volley = VolleySingleton.getInstance(application)

    // var errorListener: ErrorListener? = null
    private val detailsDao = RoomDB.getDatabase(application).detailsDao()

    fun getDetails(): LiveData<List<Details>> {

        var listDetails = ArrayList<Details>()

        val request = CustomJsonObjectRequest(
            Request.Method.GET,
            BASE_URL,
            null,
            Response.Listener {
                try {
                    Log.d("response", it.toString())
                    val fetched = GsonBuilder().create().fromJson(it.toString(), Base::class.java)

                    fetched.videos.forEach {
                        val detail = Details(
                            it.id,
                            it.duration,
                            it.image,
                            it.user.name,
                            it.user.url,
                            it.video_files[0].link
                        )
                        listDetails.add(detail)
                    }


                } catch (e: Exception) {
                    Log.d("errorss", e.printStackTrace().toString())

                }

            }, Response.ErrorListener {
                Log.d("errorss", it.toString())
//                errorListener?.errorOccurred(it.toString())
            }, AUTH_KEY
        )
        volley.addToRequestQueue(request)

        Log.d("datata", listDetails.toString())
        viewModelScope.launch(Dispatchers.IO) {
            detailsDao.insertDetail(listDetails)
        }
        return detailsDao.getDetailList()
    }

}