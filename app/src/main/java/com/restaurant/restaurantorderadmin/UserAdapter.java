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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//adapter is used to present data in recylerview or in list form
public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {
    private List<UserModel> user_model;
    private Context context;
    private UserAdapter thisAdapter = this;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView username,id,email;
        public CheckBox checkBox;
        //public SimpleRatingBar comp_rating;
        // public ImageView comp_img;

        public MyViewHolder(View view) {
            super(view);

            id = (TextView) view.findViewById(R.id.txtUserId);
            username = (TextView) view.findViewById(R.id.txtUserIdTitle);
            email = (TextView) view.findViewById(R.id.txtEmail);
           // orderId = (TextView) view.findViewById(R.id.txtdelete);
           /* comp_location = (TextView) view.findViewById(R.id.comp_location);
            comp_rating = (SimpleRatingBar) view.findViewById(R.id.comp_rating);
            comp_img=(ImageView)view.findViewById(R.id.img_comp_logo);*/
        }
    }


    public UserAdapter(List<UserModel> user_model) {
        this.user_model = user_model;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lay_user_item, parent, false);
        context=parent.getContext();

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        UserModel user = user_model.get(position);
        holder.username.setText(user.getName());
        holder.id.setText(user.getId()+"-");
        holder.email.setText(user.getEmail());

    }

    @Override
    public int getItemCount() {
        return user_model.size();
    }


}
