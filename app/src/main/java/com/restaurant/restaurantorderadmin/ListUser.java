package com.restaurant.restaurantorderadmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListUser extends AppCompatActivity {

    ArrayList<UserModel> userModels;
    private RecyclerView recyclerView;
    private UserAdapter mAdapter;
    Button btnRefresh;
    String orderId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_user);
//intializing views
        btnRefresh = (Button) findViewById(R.id.btn_refresh);
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getUsers();
            }
        });
        userModels = new ArrayList<>();
        //setting recyclerview to prsent data in list
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mAdapter = new UserAdapter(userModels);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        //getting all users who had requested
        getUsers();

    }

    private void getUsers() {
//request for getting user list
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Contants.URL_GET_USERS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    JSONArray array = obj.getJSONArray("message");
                    userModels.clear();

                    if (!obj.getBoolean("error")) {
                 for (int i = 0; i < array.length(); i++) {
                            JSONObject jsonobj = array.getJSONObject(i);
                            //supplying data to model to then binding to adapter to present in recylerview
                            userModels.add(new UserModel( jsonobj.getString("Id"), jsonobj.getString("username"), jsonobj.getString("email")));
                        }
                        //Toast.makeText(getApplicationContext(),String.valueOf( userModels.size()), Toast.LENGTH_SHORT).show();

                        //notify adapter that data is changed update the list for user
                        mAdapter.notifyDataSetChanged();

                    }
                } catch (JSONException exp) {
                    Toast.makeText(getApplicationContext(), exp.getMessage(), Toast.LENGTH_SHORT).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

}