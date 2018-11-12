package com.mamacgroup.jaayz;

/**
 * Created by mac on 3/20/17.
 */


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

//import com.google.android.gms.ads.formats.NativeAd;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {
    private Context context;
    ArrayList<Categories> categories;
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener,View.OnClickListener{
        public TextView orga, pro_list, date,summary;
        public ImageView img,selected_tick;
        LinearLayout dummy_ll,dummy_ll2;
        public MyViewHolder(View view) {
            super(view);
            pro_list=(TextView) view.findViewById(R.id.home_cat_tv);
            img=(ImageView) view.findViewById(R.id.home_pro_img);
            dummy_ll=(LinearLayout)view.findViewById(R.id.dummy_ll);
            dummy_ll2=(LinearLayout)view.findViewById(R.id.dummy_ll2);
            view.setOnClickListener(this);

        }

        @Override
        public boolean onLongClick(View view) {

//            title.setBackgroundColor(Color.parseColor("black"));

            return false;
        }

        @Override
        public void onClick(View view) {
//            mCallBack.to_reg_trades(getAdapterPosition());
            Intent intent = new Intent(context, SubCategoryActivity.class);
            intent.putExtra("id",categories.get(getAdapterPosition()).id);
            intent.putExtra("name",categories.get(getAdapterPosition()).getTitle(context));
            context.startActivity(intent);
        }
    }


    public HomeAdapter(Context context, ArrayList<Categories> categories) {
        this.context = context;
        this.categories = categories;
    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.pro_list.setText(categories.get(position).getTitle(context));
        Picasso.with(context).load(categories.get(position).image).into(holder.img);
//        if(position==9){
//            holder.dummy_ll.setVisibility(View.VISIBLE);
//        }else{
//            holder.dummy_ll.setVisibility(View.GONE);
//        }
        if(position==0){
            holder.dummy_ll2.setVisibility(View.VISIBLE);
        }else{
            holder.dummy_ll2.setVisibility(View.GONE);
        }
    }
    public class POJO {
        String activityValue;
        String activityValueText;
        String activityEmptyText;
        boolean activityCardEmptyViewVisibility;
        boolean activityCardViewVisibility;
    }
    @Override
    public int getItemCount() {
        return categories.size();

    }

}