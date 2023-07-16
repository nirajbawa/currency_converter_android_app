package com.example.currencyconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    Spinner spinner1, spinner2;
    EditText EditText1, EditText2;

    private RequestQueue requestQueue;
    private JsonObjectRequest lJsonObjectRequest;

    private String apiURL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        spinner1 = findViewById(R.id.spinner1);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(
                this,
                R.layout.custom_spinner_item,
                getResources().getStringArray(R.array.spinner_items)
        );

        // Optionally, set a dropdown layout for the spinner items
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner1.setAdapter(adapter1);

        spinner2 = findViewById(R.id.spinner2);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(
                this,
                R.layout.custom_spinner_item,
                getResources().getStringArray(R.array.spinner_items)
        );

        // Optionally, set a dropdown layout for the spinner items
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);

        EditText1 = findViewById(R.id.editTextText1);
        EditText2 = findViewById(R.id.editTextText2);


//        set event

        Button converter = findViewById(R.id.converter);
        converter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                converter();
            }
        });


        requestQueue = Volley.newRequestQueue(this);

    }

    public void converter()
    {
        String country1 = spinner1.getSelectedItem().toString();
        String country2 = spinner2.getSelectedItem().toString();
        String amount1 = EditText1.getText().toString();



        if(amount1.matches("^[0-9]+(\\.[0-9]*)?$")){

            apiURL = "https://api.api-ninjas.com/v1/convertcurrency?want="+country2+"&have="+country1+"&amount="+Double.parseDouble(amount1);

            JsonObjectRequest JsonObjectRequest = new JsonObjectRequest(Request.Method.GET, apiURL, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                Double amount = response.getDouble("new_amount");
                                EditText2.setText(Double.toString(amount));
                                Toast.makeText(MainActivity.this, "Conversion Successful", Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
//                            EditText2.setText();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Log.d("myapp", error.toString());
                            int statusCode = error.networkResponse.statusCode;
                            Log.d("myapp",  Integer.toString(statusCode));
                            error.printStackTrace();
                            Toast.makeText(MainActivity.this, "please Try Again", Toast.LENGTH_SHORT).show();
                        }
                    }
            )

            {
                // Override getHeaders() to add custom headers
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    String key1 = "v3izIwxA4qPIm46Qa8Vymw==wGJwwcRxor0KEN0R";
                    headers.put("X-Api-Key'", key1);
                    return headers;
                }
            };

//            {
//                @Override
//                public java.util.Map<String, String> getHeaders() {
//                    java.util.Map<String, String> headers = new java.util.HashMap<>();
//                    String key1 = "2264f53d11bmshac449c10ba82bdep1cbb65jsn5fa905d65c9c";
//                    String key2 = "currency-converter-by-api-ninjas.p.rapidapi.com";
//                    headers.put("X-RapidAPI-Key'", key1);
//                    headers.put("X-RapidAPI-Host", key2);
//                    return headers;
//                }
//            };

            requestQueue.add(JsonObjectRequest);
        }
        else{
            Toast.makeText(this, "Please Enter Valid Input", Toast.LENGTH_LONG).show();
        }


    }


}