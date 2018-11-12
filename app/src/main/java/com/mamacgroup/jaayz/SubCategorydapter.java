package com.mamacgroup.jaayz;

/**
 * Created by mac on 3/20/17.
 */


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
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

public class SubCategorydapter extends RecyclerView.Adapter<SubCategorydapter.MyViewHolder> {
    private Context context;
    PlanetFilter planetFilter;
    ArrayList<Categories.SubCategories> categories;
    ArrayList<Categories.SubCategories> categories_all;
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
                results.values = categories;
                results.count = categories.size();
            } else {
                List<Categories.SubCategories> nPlanetList = new ArrayList();
                Iterator it = categories_all.iterator();
                while (it.hasNext()) {
                    Categories.SubCategories p = (Categories.SubCategories) it.next();
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
                categories = categories_all;
                notifyDataSetChanged();
            } else {
                categories = (ArrayList) results.values;
                notifyDataSetChanged();
            }
        }
    }
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener,View.OnClickListener{
        public TextView orga, pro_list, date,summary;
        public ImageView img,selected_tick;
        LinearLayout dummy_ll,dummy_ll2;
        public MyViewHolder(View view) {
            super(view);
            pro_list=(TextView) view.findViewById(R.id.sub_cat_tvv);
            img=(ImageView) view.findViewById(R.id.sub_cat_item_img);
//            dummy_ll=(LinearLayout)view.findViewById(R.id.dummy_ll);
//            dummy_ll2=(LinearLayout)view.findViewById(R.id.dummy_ll2);
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
            Intent intent = new Intent(context, ProductsActivity.class);
            intent.putExtra("id",categories.get(getAdapterPosition()).id);
            intent.putExtra("name",categories.get(getAdapterPosition()).getTitle(context));
            context.startActivity(intent);
        }
    }


    public SubCategorydapter(Context context, ArrayList<Categories.SubCategories> categories) {
        this.context = context;
        this.categories = categories;
        this.categories_all = categories;
    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.subcatgory_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.pro_list.setText(categories.get(position).getTitle(context));
        Picasso.with(context).load(categories.get(position).image).into(holder.img);
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
    public Filter getFilter() {
        if (this.planetFilter == null) {
            this.planetFilter = new PlanetFilter();
        }
        return this.planetFilter;
    }
}