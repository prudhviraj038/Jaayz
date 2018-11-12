package com.mamacgroup.jaayz;

/**
 * Created by mac on 3/20/17.
 */


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

//import com.google.android.gms.ads.formats.NativeAd;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.MyViewHolder> {
    private Context context;
    ArrayList<Products> products;
    ArrayList<Products> products_all=new ArrayList<>();
    PlanetFilter planetFilter;
    int qty=1;
    private class PlanetFilter extends Filter {
        Boolean clear_all;

        private PlanetFilter() {
            this.clear_all = Boolean.valueOf(false);
        }

        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            this.clear_all = Boolean.valueOf(false);
            if (constraint == null || constraint.length() == 0) {
                this.clear_all = Boolean.valueOf(true);
                results.values = products;
                results.count = products.size();
            } else {
                List<Products> nPlanetList = new ArrayList();
                Iterator it = products_all.iterator();
                while (it.hasNext()) {
                    Products p = (Products) it.next();
                    if (Pattern.compile(Pattern.quote(constraint.toString()),2).matcher(p.title).find()) {
                        nPlanetList.add(p);
                    }
                }
                results.values = nPlanetList;
                results.count = nPlanetList.size();
            }
            return results;
        }

        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results.count == 0) {
                notifyDataSetChanged();
            } else if (this.clear_all.booleanValue()) {
                products = products_all;
                notifyDataSetChanged();
            } else {
                products = (ArrayList) results.values;
                notifyDataSetChanged();
            }
        }
    }
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener,View.OnClickListener{
        public TextView pro_name, price,qty_tv,sta_qty,buy_tv;
        public ImageView img,like,plus,minus;
        LinearLayout buy_ll;
        CardView cardView;
        public MyViewHolder(View view) {
            super(view);
            pro_name=(TextView) view.findViewById(R.id.pro_name);
            price=(TextView) view.findViewById(R.id.product_price);
            qty_tv=(TextView) view.findViewById(R.id.qty_tv);
            sta_qty=(TextView) view.findViewById(R.id.sta_qty);
            buy_tv=(TextView) view.findViewById(R.id.buy_now_tv);
            cardView=(CardView) view.findViewById(R.id.card_view);

            img=(ImageView) view.findViewById(R.id.pro_imgg);
            like=(ImageView) view.findViewById(R.id.like_img);
            plus=(ImageView) view.findViewById(R.id.plus_img);
            minus=(ImageView) view.findViewById(R.id.minus_img);

            buy_ll=(LinearLayout)view.findViewById(R.id.buy_now_ll);
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

        }
    }


    public ProductsAdapter(Context context, ArrayList<Products> products) {
        this.context = context;
        this.products = products;
        this.products_all = products;
    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.pro_name.setText(products.get(position).getTitle(context));
        holder.price.setText(products.get(position).price+" KD");
        Picasso.with(context).load(products.get(position).images.get(0)).into(holder.img);

        if(position==0){
            ViewGroup.MarginLayoutParams layoutParams =
                    (ViewGroup.MarginLayoutParams) holder.cardView.getLayoutParams();
            layoutParams.setMargins(0, 0, 8, 0);
            holder.cardView.requestLayout();
        }else if(position==1){
            ViewGroup.MarginLayoutParams layoutParams =
                    (ViewGroup.MarginLayoutParams) holder.cardView.getLayoutParams();
            layoutParams.setMargins(8, 0, 0, 0);
            holder.cardView.requestLayout();
        }else if(position%2==0){
            ViewGroup.MarginLayoutParams layoutParams =
                    (ViewGroup.MarginLayoutParams) holder.cardView.getLayoutParams();
            layoutParams.setMargins(0, 0, 8, 0);
            holder.cardView.requestLayout();
        }else{
            ViewGroup.MarginLayoutParams layoutParams =
                    (ViewGroup.MarginLayoutParams) holder.cardView.getLayoutParams();
            layoutParams.setMargins(8, 0, 0, 0);
            holder.cardView.requestLayout();
        }



        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int temp=products.get(position).qty;
                temp++;
                Log.e("qtyyyyy",String.valueOf(temp));
                products.get(position).qty=temp;
               holder.qty_tv.setText(String.valueOf(products.get(position).qty));
            }
        });
        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(products.get(position).qty>1){
                    int temp=products.get(position).qty;
                    temp--;
                    Log.e("qtyyyyy_m",String.valueOf(temp));
                    products.get(position).qty=temp;
                    holder.qty_tv.setText(String.valueOf(products.get(position).qty));
                }
            }
        });

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
        return products.size();

    }
    public Filter getFilter() {
        if (this.planetFilter == null) {
            this.planetFilter = new ProductsAdapter.PlanetFilter();
        }
        return this.planetFilter;
    }
}