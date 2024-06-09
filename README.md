# CryptoTracker Android App

## Overview

CryptoTracker is an Android application designed to track the latest cryptocurrency prices. It features a dynamic splash screen, a structured data model for handling currency data, a custom adapter for displaying the data in a RecyclerView, and a main activity that fetches and displays cryptocurrency information using the CoinMarketCap API.

## Features

1. **Dynamic Splash Screen**
2. **Data Models**
3. **CurrencyAdapter**
4. **MainActivity**
5. **CoinMarketCap API Integration**

## Components

### 1. Dynamic Splash Screen

The splash screen is the entry point of the application, providing a loading screen while the necessary background tasks are completed.

- **File:** `splashscreen.kt`
- **Functionality:** Displays a full-screen splash screen for a set duration before launching the main activity.

Sample code snippet:

```kotlin
class splashscreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        supportActionBar?.hide()
        setContentView(R.layout.activity_splashscreen)
        HeavyTask()
    }

    private fun HeavyTask() {
        LongOperation().execute()
    }

    private open inner class LongOperation : AsyncTask<String?, Void?, String?>() {
        // Background task implementation
    }
}
```

### 2. Data Models

Data models are used to represent the cryptocurrency data fetched from the API.

- **File:** `CurrencyModel.kt`
- **Functionality:** Defines a simple data class for storing cryptocurrency information.

Sample code snippet:

```kotlin
data class CurrencyModel(
    var name: String,
    var symbol: String,
    var price: Double
)
```

### 3. CurrencyAdapter

The `CurrencyAdapter` is a custom adapter for displaying the list of cryptocurrencies in a RecyclerView.

- **File:** `currencyAdapter.kt`
- **Functionality:** Binds the cryptocurrency data to the views in each item of the RecyclerView.

Sample code snippet:

```kotlin
class currencyAdapter(
    private var currencyModals: ArrayList<CurrencyModel>,
    private val context: Context
) : RecyclerView.Adapter<currencyAdapter.CurrencyViewHolder>() {

    inner class CurrencyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nametv: TextView = itemView.findViewById(R.id.name)
        val pricetv: TextView = itemView.findViewById(R.id.price)
        val symboltv: TextView = itemView.findViewById(R.id.symbol)
    }
}
```

### 4. MainActivity

The `MainActivity` handles the main functionality of the application, including fetching data from the API and displaying it in a RecyclerView.

- **File:** `MainActivity.kt`
- **Functionality:** Initializes the RecyclerView and its adapter, fetches cryptocurrency data from the CoinMarketCap API, and implements search functionality to filter the displayed data.

Sample code snippet:

```kotlin
class MainActivity : AppCompatActivity() {

    private lateinit var currencyRV: RecyclerView
    private lateinit var searchEdt: EditText
    private lateinit var currencyModalArrayList: ArrayList<CurrencyModel>
    private lateinit var currencyRVAdapter: currencyAdapter
    private lateinit var loadingPB: ProgressBar
    private lateinit var currencycount: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialization and setup code

        getData()

        searchEdt.addTextChangedListener(object : TextWatcher {
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
        // Update adapter with filtered list
    }

    private fun getData() {
        val url = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest"
        val queue: RequestQueue = Volley.newRequestQueue(this)

        val jsonObjectRequest = object : JsonObjectRequest(Method.GET, url, null,
            Response.Listener { response ->
                // Parse response and update RecyclerView
            },
            Response.ErrorListener { error: VolleyError ->
                // Handle error
            }) {
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers["X-CMC_PRO_API_KEY"] = "YOUR_API_KEY"
                return headers
            }
        }

        queue.add(jsonObjectRequest)
    }
}
```

### 5. CoinMarketCap API Integration

The CoinMarketCap API is used to fetch the latest cryptocurrency listings. The application uses the `/v1/cryptocurrency/listings/latest` endpoint to get real-time data.

#### CoinMarketCap API Endpoint

- **URL:** `https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest`
- **Method:** `GET`
- **Headers:**
  - `X-CMC_PRO_API_KEY`: Your API Key from CoinMarketCap

## Installation

1. Clone the repository to your local machine.
2. Open the project in Android Studio.
3. Sync the project with Gradle files.
4. Run the application on an emulator or a physical device.

## Dependencies

Add the following dependencies to your `build.gradle` file:

```gradle
dependencies {
    implementation 'com.android.volley:volley:1.2.1'
}
```

## Features

- **Dynamic Splash Screen:** Provides a full-screen loading screen at startup.
- **Data Models:** Represents cryptocurrency data using a structured data class.
- **RecyclerView with Custom Adapter:** Displays the list of cryptocurrencies with a custom adapter.
- **API Integration:** Fetches real-time cryptocurrency data from CoinMarketCap.
- **Search Functionality:** Allows users to filter cryptocurrencies by name.
