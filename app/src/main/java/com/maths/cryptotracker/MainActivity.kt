package com.maths.cryptotracker

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.maths.cryptotracker.CurrencyModel
import com.maths.cryptotracker.R
import com.maths.cryptotracker.currencyAdapter
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.Locale

class MainActivity : AppCompatActivity() {


    private lateinit var currencyRV: RecyclerView
    private lateinit var searchEdt: EditText
    private lateinit var currencyModalArrayList: ArrayList<CurrencyModel>
    private lateinit var currencyRVAdapter: currencyAdapter
    private lateinit var loadingPB: ProgressBar
    private  lateinit var currencycount : TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN , WindowManager.LayoutParams.FLAG_FULLSCREEN)
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)
        searchEdt = findViewById(R.id.search_coin)
        loadingPB = findViewById(R.id.idPBLoading)
        currencyRV = findViewById(R.id.item_rv)
        currencycount = findViewById(R.id.Currencycount)
        currencyModalArrayList = ArrayList()
        currencyRVAdapter = currencyAdapter(currencyModalArrayList, this)

        currencyRV.layoutManager = LinearLayoutManager(this)
        currencyRV.adapter = currencyRVAdapter

        getData()

        searchEdt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                filter(s.toString())
            }
        })
    }

    private fun filter(filter: String) {
        val filteredList = ArrayList<CurrencyModel>()
        for (item in currencyModalArrayList) {
            if (item.name.lowercase(Locale.getDefault()).contains(filter.lowercase(Locale.getDefault()))) {
                filteredList.add(item)
            }
        }
        if (filteredList.isEmpty()) {
            Toast.makeText(this, "No currency found..", Toast.LENGTH_SHORT).show()
        } else {
            currencyRVAdapter.filterList(filteredList)
        }
    }

    private fun getData() {
        val url = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest"
        val queue: RequestQueue = Volley.newRequestQueue(this)

        val jsonObjectRequest = object : JsonObjectRequest(
            Method.GET, url, null,
            Response.Listener { response ->
                loadingPB.visibility = View.GONE
                try {
                    val dataArray: JSONArray = response.getJSONArray("data")

                    for (i in 0 until dataArray.length()) {
                        val dataObj: JSONObject = dataArray.getJSONObject(i)
                        val symbol = dataObj.getString("symbol")
                        val name = dataObj.getString("name")
                        val quote = dataObj.getJSONObject("quote")
                        val USD = quote.getJSONObject("USD")
                        val price = USD.getDouble("price")
                        currencyModalArrayList.add(CurrencyModel(name, symbol, price))
                    }
                    currencycount.text = "${currencyModalArrayList.count()}"
                    currencyRVAdapter.notifyDataSetChanged()
                } catch (e: JSONException) {
                    e.printStackTrace()
                    Toast.makeText(this@MainActivity, "Something went amiss. Please try again later", Toast.LENGTH_SHORT).show()
                }
            },
            Response.ErrorListener { error: VolleyError ->
                Toast.makeText(this@MainActivity, "Something went amiss. Please try again later", Toast.LENGTH_SHORT).show()
            }) {
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers["X-CMC_PRO_API_KEY"] = "097d3a01-6ce2-41e8-82c5-13caec72e8c0"
                return headers
            }
        }

        queue.add(jsonObjectRequest)
    }
}
