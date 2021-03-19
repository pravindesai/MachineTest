package com.example.machinetest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.machinetest.ROOM.RoomDao;
import com.example.machinetest.ROOM.RoomDb;
import com.example.qnopytestapp.APIResponse.ApiResponse;
import com.example.qnopytestapp.APIResponse.FormData;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "*";
    String API_URL = "http://staging.qnopy.com:8080/webanguler/images/formDetails.json";

    LinearLayout layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setContentView(R.layout.activity_main);
        init();

        setContentView(layout);

        ParseJson();
    }

    private void init() {
        layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

    }

    private void ParseJson() {

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                API_URL,
                null,
                new Listner(),
                new ErrorListner());

        requestQueue.add(jsonObjectRequest);


    }

    private class Listner implements Response.Listener<JSONObject> {
        @Override
        public void onResponse(JSONObject response) {
            Gson gson = new Gson();
            ApiResponse apiResponse =   (ApiResponse)
                                        gson.fromJson(response.toString(),
                                        ApiResponse.class);

            List<FormData> formDataList = apiResponse.getFormData();

            for (FormData data : formDataList) {
                try {
                    RoomDb.getInstance(MainActivity.this).roomDao().InsertData(data);
                }catch (Exception e){
                    logg(e.getMessage());
                }
                insertView(data);
            }

            for (FormData data : RoomDb.getInstance(MainActivity.this).roomDao().getFormData()) {
                logg("-----------------");
                logg(data.getFieldParameterLabel());
            }

        logg("Done");

        }
    }

    private class ErrorListner implements Response.ErrorListener {
        @Override
        public void onErrorResponse(VolleyError error) {
            logg("Response Error" + error.getMessage());
        }
    }

    public void toast(String msg) {
        Toast.makeText(getApplicationContext(),
                msg,
                Toast.LENGTH_SHORT)
                .show();
    }

    public void logg(String msg) {
        Log.e(TAG, "logg: " + msg);
    }

    private void insertView(FormData formData){
        logg(formData.getFieldInputType());
        LinearLayout itemLayout = new LinearLayout(this);
        itemLayout.setOrientation(LinearLayout.HORIZONTAL);

        TextView textView = new TextView(this);
        EditText editText = new EditText(this);


        switch (formData.getFieldInputType()){
            case "TEXT":
                textView.setText(formData.getFieldParameterLabel());
                editText.setText(formData.getStringValue());
                editText.setInputType(InputType.TYPE_CLASS_TEXT);
                itemLayout.addView(textView);
                itemLayout.addView(editText);
                break;

            case "NUMERIC":
                textView.setText(formData.getFieldParameterLabel());
                editText.setText(formData.getStringValue());
                editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                itemLayout.addView(textView);
                itemLayout.addView(editText);
                break;

            case "TIME":
                textView.setText(formData.getFieldParameterLabel());
                editText.setText(formData.getStringValue());
                editText.setInputType(InputType.TYPE_CLASS_DATETIME);
                itemLayout.addView(textView);
                itemLayout.addView(editText);
                break;

            case "DATE":
                textView.setText(formData.getFieldParameterLabel());
                editText.setText(formData.getStringValue());
                editText.setInputType(InputType.TYPE_CLASS_DATETIME);
                itemLayout.addView(textView);
                itemLayout.addView(editText);
                break;

            case "PHOTOS":
                textView.setText(formData.getFieldParameterLabel());
                editText.setText(formData.getStringValue());
                editText.setInputType(InputType.TYPE_CLASS_DATETIME);
                itemLayout.addView(textView);
                itemLayout.addView(editText);
                break;

            case "GPS":
                textView.setText(formData.getFieldParameterLabel());
                editText.setText(formData.getStringValue());
                editText.setInputType(InputType.TYPE_CLASS_DATETIME);
                itemLayout.addView(textView);
                itemLayout.addView(editText);
                break;

        }

        layout.addView(itemLayout);
    }

}
