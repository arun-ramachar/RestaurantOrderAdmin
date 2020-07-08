package com.restaurant.restaurantorderadmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    Button login;
    EditText username, password;
    TextView txtCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toast.makeText(getApplicationContext(),SharedPrefManager.getID(getApplicationContext()),Toast.LENGTH_SHORT).show();
if(!SharedPrefManager.getID(getApplicationContext()).isEmpty())
{
    startActivity(new Intent(MainActivity.this,OrdersActivity.class));
    finish();
}


        username = (EditText)findViewById(R.id.txtuser);
        password = (EditText)findViewById(R.id.txtpass);
        login = (Button)findViewById(R.id.btnlogin);
        txtCreate=(TextView)findViewById(R.id.txtCreateAccount);
        txtCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,SignUpActivity.class));
                finish();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginButton();
            }
        });

    }

    private void getOrders() {

    }

    public void LoginButton(){

        StringRequest stringRequest=new StringRequest(Request.Method.POST, Contants.URL_LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    //  JSONArray array = obj.getJSONArray("message");

                    if (!obj.getBoolean("error")) {

                       /* for (int i = 0; i < array.length(); i++) {
                            JSONObject jsonobj = array.getJSONObject(i);
                        }*/
                       if(obj.getString("role").equalsIgnoreCase("admin"))
                       {
                           SharedPrefManager.setID(obj.getString("id"),getApplicationContext());
                           SharedPrefManager.setUsername(obj.getString("username"),getApplicationContext());
                           SharedPrefManager.setToken(obj.getString("token"),getApplicationContext());
                           //Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
                           updateToken(FirebaseInstanceId.getInstance().getToken());
                           startActivity(new Intent(MainActivity.this,OrdersActivity.class));
                           finish();
                       }
                       else
                       {
                           Toast.makeText(getApplicationContext(),"Invalid Login Credential",Toast.LENGTH_SHORT).show();
                       }

                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),obj.getString("message"),Toast.LENGTH_SHORT).show();
                    }
                }catch (JSONException exp)
                {
                    Toast.makeText(getApplicationContext(),exp.getMessage(),Toast.LENGTH_SHORT).show();
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", username.getText().toString().trim());
                params.put("password", password.getText().toString().trim());
                 //params.put("token", FirebaseInstanceId.getInstance().getToken());
                return params;

            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

    }

    private void updateToken(final String token) {
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Contants.URL_UPDATE_ADMIN_TOKEN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    //  JSONArray array = obj.getJSONArray("message");

                    if (!obj.getBoolean("error")) {

                       /* for (int i = 0; i < array.length(); i++) {
                            JSONObject jsonobj = array.getJSONObject(i);
                        }*/
                        Toast.makeText(getApplicationContext(),obj.getString("message"),Toast.LENGTH_SHORT).show();

                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),obj.getString("message"),Toast.LENGTH_SHORT).show();
                    }
                }catch (JSONException exp)
                {
                    Toast.makeText(getApplicationContext(),exp.getMessage(),Toast.LENGTH_SHORT).show();
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("token", token);
                return params;

            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }
}