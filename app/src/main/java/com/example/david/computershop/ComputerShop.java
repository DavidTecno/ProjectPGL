package com.example.david.computershop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ContentFrameLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.BreakIterator;

public class ComputerShop extends AppCompatActivity {

    final String HP = "Hp";
    final String LENOVO = "Lenovo";
    final String ASUS = "Asus";
    TextView jsonTextView1;
    Switch switchList;
    Spinner spinnerList;
    Button buttonRequest;
    CheckBox checkBoxRequest;
    ImageView hp;
    ImageView lenovo;
    ImageView asus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_computer_shop);

        switchList = (Switch) findViewById(R.id.switchList);
        spinnerList = (Spinner) findViewById(R.id.spinnerList);
        buttonRequest = (Button) findViewById(R.id.buttonRequest);
        jsonTextView1 = (TextView) findViewById(R.id.jsonTextView1);

        checkBoxRequest = (CheckBox) findViewById(R.id.checkBox);
        hp = (ImageView) findViewById(R.id.hp);
        lenovo = (ImageView) findViewById(R.id.lenovo);
        asus = (ImageView) findViewById(R.id.asus);

        //ArraySpinner
        String[] computers = {HP, LENOVO, ASUS};
        spinnerList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, computers));


        //Visibility
        spinnerList.setVisibility(View.VISIBLE);
        buttonRequest.setVisibility(View.VISIBLE);
        hp.setVisibility(View.VISIBLE);
        lenovo.setVisibility(View.INVISIBLE);
        asus.setVisibility(View.INVISIBLE);
        checkBoxRequest.setVisibility(View.INVISIBLE);

        init();
    }

    private void init() {
        switchList.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {


                if (b == true) {
                    buttonRequest.setText("Request");
                    switchList.setText("Request Mode");
                    checkBoxRequest.setVisibility(View.VISIBLE);

                } else {
                    spinnerList.setVisibility(View.VISIBLE);
                    buttonRequest.setVisibility(View.VISIBLE);
                    checkBoxRequest.setVisibility(View.INVISIBLE);
                    buttonRequest.setText("View");
                    switchList.setText("View Mode");
                    String option = spinnerList.getSelectedItem().toString();
                    Log.i("asdf", option);
                    switch (option) {
                        case HP:
                            lenovo.setVisibility(View.INVISIBLE);
                            asus.setVisibility(View.INVISIBLE);
                            hp.setVisibility(View.VISIBLE);
                            break;
                        case LENOVO:
                            hp.setVisibility(View.INVISIBLE);
                            asus.setVisibility(View.INVISIBLE);
                            lenovo.setVisibility(View.VISIBLE);
                            break;
                        case ASUS:
                            lenovo.setVisibility(View.INVISIBLE);
                            hp.setVisibility(View.INVISIBLE);
                            asus.setVisibility(View.VISIBLE);
                            break;
                    }

                }
            }


        });

        buttonRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String button = buttonRequest.getText().toString();
                switch (button) {
                    case "View":
                        String option = spinnerList.getSelectedItem().toString();

                        switch (option) {
                            case HP:
                                lenovo.setVisibility(View.INVISIBLE);
                                asus.setVisibility(View.INVISIBLE);
                                hp.setVisibility(View.VISIBLE);
                                break;
                            case LENOVO:
                                hp.setVisibility(View.INVISIBLE);
                                asus.setVisibility(View.INVISIBLE);
                                lenovo.setVisibility(View.VISIBLE);
                                break;
                            case ASUS:
                                lenovo.setVisibility(View.INVISIBLE);
                                hp.setVisibility(View.INVISIBLE);
                                asus.setVisibility(View.VISIBLE);
                                break;
                        }
                        break;
                    case "Request":

                        buttonRequest.setText("Back");
                        switchList.setVisibility(View.INVISIBLE);
                        spinnerList.setVisibility(View.INVISIBLE);
                        lenovo.setVisibility(View.INVISIBLE);
                        hp.setVisibility(View.INVISIBLE);
                        asus.setVisibility(View.INVISIBLE);

                        option = spinnerList.getSelectedItem().toString();
                        if (checkBoxRequest.isChecked()){
                            option="";
                            String url = "http://192.168.201.98:40000/api/laptop";
                            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                                    (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                                        @Override
                                        public void onResponse(JSONArray response) {
                                            jsonTextView1.setText(response.toString());

                                            jsonTextView1.setVisibility(View.VISIBLE);
                                        }
                                    }, new Response.ErrorListener() {

                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            jsonTextView1.setText("Error to take server data, try later");

                                        }
                                    });

                            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                            requestQueue.add(jsonArrayRequest);
                        }else{
                            String url = "http://192.168.201.98:40000/api/laptop"+option;

                            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                                    (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                                        @Override
                                        public void onResponse(JSONObject response) {
                                            jsonTextView1.setText(response.toString());

                                            jsonTextView1.setVisibility(View.VISIBLE);
                                        }
                                    }, new Response.ErrorListener() {

                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            jsonTextView1.setText("Error to take server data, try later");

                                        }
                                    });

                            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                            requestQueue.add(jsonObjectRequest);
                        }

                        break;
                    case "Back":
                        jsonTextView1.setVisibility(View.INVISIBLE);
                        switchList.setVisibility(View.VISIBLE);
                        spinnerList.setVisibility(View.VISIBLE);
                        checkBoxRequest.setVisibility(View.INVISIBLE);
                        buttonRequest.setText("View");
                        switchList.setText("View Mode");
                        switchList.setChecked(false);
                        buttonRequest.callOnClick();
                }


            }
        });


    }

}
