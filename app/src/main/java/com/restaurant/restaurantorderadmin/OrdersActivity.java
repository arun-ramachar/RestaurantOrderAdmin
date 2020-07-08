package com.restaurant.restaurantorderadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OrdersActivity extends AppCompatActivity {
    ArrayList<OrderModel> orderModels;
    private RecyclerView recyclerView;
    private MyAdapter mAdapter;
    Button btnRefresh,btn_contact_list;
    String orderId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        btnRefresh=(Button)findViewById(R.id.btn_refresh);
        btn_contact_list=(Button)findViewById(R.id.btn_contact_list);
        btn_contact_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OrdersActivity.this,ListUser.class));
            }
        });
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getOrders();
            }
        });
        orderModels=new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mAdapter = new MyAdapter(orderModels);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        getOrders();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.more, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.menu_logout){
            SharedPrefManager.setID(null,getApplicationContext());
            SharedPrefManager.setUsername(null,getApplicationContext());
            SharedPrefManager.setToken(null,getApplicationContext());
            startActivity(new Intent(OrdersActivity.this,MainActivity.class));
            finish();
            // do something
        }
        return super.onOptionsItemSelected(item);
    }

    private void getOrders() {

        StringRequest stringRequest=new StringRequest(Request.Method.GET, Contants.URL_GET_ORDERS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                      JSONArray array = obj.getJSONArray("message");
                   orderModels.clear();

                    if (!obj.getBoolean("error")) {

                        for (int i = 0; i < array.length(); i++) {
                            JSONObject jsonobj = array.getJSONObject(i);
                            orderModels.add(new OrderModel(jsonobj.getString("orderId"),jsonobj.getString("userId"),jsonobj.getString("username"),jsonobj.getString("orderDetails")));
                        }
                        mAdapter.notifyDataSetChanged();
                       /* if (obj.getString("role").equalsIgnoreCase("admin")) {
                            M.setID(obj.getString("id"), getApplicationContext());
                            M.setUsername(obj.getString("username"), getApplicationContext());
                            M.setToken(obj.getString("token"), getApplicationContext());
                            Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                            //startActivity(new Intent(OrdersActivity.this,OrdersActivity.class));
                            //finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Invalid Login Credential", Toast.LENGTH_SHORT).show();
                        }*/

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
        });
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }
    private void deleteOrder() {

        StringRequest stringRequest=new StringRequest(Request.Method.POST, Contants.URL_DELETE_ORDER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    //JSONArray array = obj.getJSONArray("message");
                   // orderModels.clear();

                    if (!obj.getBoolean("error")) {
                        getOrders();



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
                Map<String,String> param=new HashMap<>();
                param.put("orderId",orderId);
                return  param;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }
}