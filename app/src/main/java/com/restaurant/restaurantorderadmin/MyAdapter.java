package com.restaurant.restaurantorderadmin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private List<OrderModel> order_list;
    private Context context;
    private MyAdapter thisAdapter = this;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView username,id,order_details,orderId;
        public CheckBox checkBox;
        //public SimpleRatingBar comp_rating;
       // public ImageView comp_img;

        public MyViewHolder(View view) {
            super(view);

            id = (TextView) view.findViewById(R.id.txtUserId);
            username = (TextView) view.findViewById(R.id.txtUsername);
            order_details = (TextView) view.findViewById(R.id.orderDetails);
            orderId = (TextView) view.findViewById(R.id.txtdelete);
           /* comp_location = (TextView) view.findViewById(R.id.comp_location);
            comp_rating = (SimpleRatingBar) view.findViewById(R.id.comp_rating);
            comp_img=(ImageView)view.findViewById(R.id.img_comp_logo);*/
        }
    }


    public MyAdapter(List<OrderModel> catg_list) {
        this.order_list = catg_list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lay_order_item, parent, false);
        context=parent.getContext();

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        OrderModel order = order_list.get(position);
        holder.username.setText(order.getUsername());
        holder.id.setText(order.getId()+"-");
        holder.order_details.setText(order.getOrder_detail());
        holder.orderId.setTag(order.getOrderId());
        holder.orderId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteOrder(view.getTag().toString());
                thisAdapter.notifyItemRemoved(position);
                thisAdapter.notifyDataSetChanged();

            }
        });
     /*   holder.comp_rating.setRating(5);
        holder.comp_location.setText(company.getLocation());
        holder.comp_img.setImageResource(R.drawable.ic_logo);*/
    }

    @Override
    public int getItemCount() {
        return order_list.size();
    }

    private void deleteOrder(final String orderId) {

        StringRequest stringRequest=new StringRequest(Request.Method.POST, Contants.URL_DELETE_ORDER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    //JSONArray array = obj.getJSONArray("message");
                    // orderModels.clear();

                    if (!obj.getBoolean("error")) {


                    }
                }catch (JSONException exp)
                {
                    Toast.makeText(context,exp.getMessage(),Toast.LENGTH_SHORT).show();
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param=new HashMap<>();
                param.put("orderId",orderId);
                return  param;
            }
        };
        RequestHandler.getInstance(context).addToRequestQueue(stringRequest);
    }

}