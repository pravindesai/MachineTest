package com.example.machinetest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
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

    Gson gson;
    RequestQueue requestQueue;
    LinearLayout viewContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        init();
        ParseJson();
    }

    private void init() {
        viewContainer = findViewById(R.id.viewContainer);


    }

    private void ParseJson() {

        requestQueue = Volley.newRequestQueue(this);

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
            gson = new Gson();
            ApiResponse apiResponse =   (ApiResponse)
                                        gson.fromJson(response.toString(),
                                        ApiResponse.class);

            List<FormData> formDataList = apiResponse.getFormData();

            formDataList = SortDataByRowOrder(formDataList);

            for (FormData data : formDataList) {
                try {
                    RoomDb.getInstance(MainActivity.this).roomDao().InsertData(data);
                }catch (Exception e){
                    logg(e.getMessage());
                }
                insertView(data);
            }

        }
    }

    private List<FormData> SortDataByRowOrder(List<FormData> formDataList) {
        int n = formDataList.size();
        for(int itr = 0; itr< n-1; itr++){
            for (int j = 0; j < n-itr-1; j++)

                if (formDataList.get(j).getRowOrder() > formDataList.get(j+1).getRowOrder())
                {
                    FormData temp = formDataList.get(j);
                    formDataList.set(j, formDataList.get(j+1));
                    formDataList.set(j+1, temp);
                }
        }

        return formDataList;
//        for (FormData data : formDataList) {
//            System.out.println(data.getRowOrder()+" : "+data.getFieldInputType());
//        }

    }


    private class ErrorListner implements Response.ErrorListener {
        @Override
        public void onErrorResponse(VolleyError error) {
            logg("Response Error" + error.getMessage());
        }
    }

    public void logg(String msg) {
        Log.e(TAG, "logg: " + msg);
    }

    private void insertView(FormData formData){
        logg("DataTypes: "+formData.getFieldInputType());

//        LinearLayout itemLayout = new LinearLayout(this);
//        itemLayout.setOrientation(LinearLayout.HORIZONTAL);
//        TextView textView = new TextView(this);
//        EditText editText = new EditText(this);

        View itemLinearLayout = (LinearLayout)LayoutInflater.from(this).inflate(R.layout.list_item, null);
        TextView tv = itemLinearLayout.findViewById(R.id.text);
        EditText edt = itemLinearLayout.findViewById(R.id.edt);

        switch (formData.getFieldInputType()){
            case "TEXT":
                tv.setText(formData.getFieldParameterLabel());
                edt.setText(formData.getStringValue());
                edt.setInputType(InputType.TYPE_CLASS_TEXT);
                break;

            case "NUMERIC":
                tv.setText(formData.getFieldParameterLabel());
                edt.setText(formData.getStringValue());
                edt.setInputType(InputType.TYPE_CLASS_NUMBER);
                break;

            case "TIME":
                tv.setText(formData.getFieldParameterLabel());
                edt.setText(formData.getStringValue());
                edt.setInputType(InputType.TYPE_CLASS_DATETIME);

                break;

            case "DATE":
                tv.setText(formData.getFieldParameterLabel());
                edt.setText(formData.getStringValue());
                edt.setInputType(InputType.TYPE_CLASS_DATETIME);

                break;

            case "PHOTOS":
                tv.setText(formData.getFieldParameterLabel());
                edt.setText(formData.getStringValue());
                edt.setInputType(InputType.TYPE_CLASS_DATETIME);

                break;

            case "GPS":
                tv.setText(formData.getFieldParameterLabel());
                edt.setText(formData.getStringValue());
                edt.setInputType(InputType.TYPE_CLASS_DATETIME);

                break;

        }

        viewContainer.addView(itemLinearLayout);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //requestQueue.stop();
    }
}
