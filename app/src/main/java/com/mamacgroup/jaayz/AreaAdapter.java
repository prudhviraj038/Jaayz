package com.mamacgroup.jaayz;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by info on 20-02-2018.
 */

public class AreaAdapter extends BaseAdapter implements Filterable {
    ArrayList<Areas> areas;
    ArrayList<Areas> areas_all;
    Context context;
    LayoutInflater inflater;
    PlanetFilter planetFilter;
    HomeActivity homeActivity;
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
                results.values = areas;
                results.count = areas.size();
            } else {
                List<Areas> nPlanetList = new ArrayList();
                Iterator it = areas_all.iterator();
                while (it.hasNext()) {
                    Areas p = (Areas) it.next();
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
                areas = areas_all;
                notifyDataSetChanged();
            } else {
                areas = (ArrayList) results.values;
                notifyDataSetChanged();
            }
        }
    }

    public AreaAdapter(Context context, HomeActivity homeActivity, ArrayList<Areas> areas) {
        this.context = context;
        this.homeActivity = homeActivity;
        this.inflater = LayoutInflater.from(context);
        this.areas = areas;
        this.areas_all = areas;
    }

    public int getCount() {
        return this.areas.size();
    }

    public Object getItem(int i) {
        return null;
    }

    public long getItemId(int i) {
        return 0;
    }

    public View getView(final int i, View view, ViewGroup viewGroup) {
        View item_view = this.inflater.inflate(R.layout.area_items, null);
        LinearLayout area_ll = (LinearLayout) item_view.findViewById(R.id.area_ll);
        LinearLayout area_ll2 = (LinearLayout) item_view.findViewById(R.id.area_ll2);
        final TextView area_title = (TextView) item_view.findViewById(R.id.area_title);
        final TextView area_title2 = (TextView) item_view.findViewById(R.id.area_title2);
        if (Session.get_user_language(this.context).equals("en")) {
            area_title.setText(((Areas) this.areas.get(i)).title);
            area_title2.setText(((Areas) this.areas.get(i)).title);
        } else {
            area_title.setText(((Areas) this.areas.get(i)).title_ar);
            area_title2.setText(((Areas) this.areas.get(i)).title_ar);
        }
        area_ll.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (!areas.get(i).type.equals("0")) {
                    Session.setAreaid(context,areas.get(i).id,areas.get(i).title+Session.get_lan(context));
                    homeActivity.area_home_tv.setText(areas.get(i).title+Session.get_lan(context));
                    homeActivity.area_pop_ll.setVisibility(View.GONE);
                }
            }
        });
        if (areas.get(i).type.equals("0")) {
            area_ll2.setVisibility(View.VISIBLE);area_ll.setVisibility(View.GONE);
        } else {
            area_ll2.setVisibility(View.GONE);area_ll.setVisibility(View.VISIBLE);
        }
        return item_view;
    }

    public Filter getFilter() {
        if (this.planetFilter == null) {
            this.planetFilter = new PlanetFilter();
        }
        return this.planetFilter;
    }
}

