package com.mamacgroup.jaayz;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

public class ProductsActivity extends FragmentActivity {
    TabLayout pagerSlidingTabStrip;
    public ViewPager viewPager;
    ArrayList<String> pro_id;
    ArrayList<String> pro_title;
    MyPagerAdapter adapter;
    Map<Integer, ProductFragment> frags;
    String id,name="";
    ImageView back;
    TextView sub_head;
    EditText et_search;
    ProductFragment productFragment;
    LinearLayout home_ll,offers_ll,cart_ll,user_ll;
    ImageView home_img,offers_img,cart_img,user_img;
    TextView home_tv,offers_tv,cart_tv,user_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.products_activity);
        id=getIntent().getStringExtra("id");
        name=getIntent().getStringExtra("name");
        pro_id=new ArrayList<>();
        pro_title=new ArrayList<>();


        back=(ImageView)findViewById(R.id.back_pro);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        sub_head=(TextView) findViewById(R.id.pro_head_tv);
        sub_head.setText(name);
        et_search=(EditText) findViewById(R.id.et_search_pro);
        et_search.setHint(Session.getword(this,"Search Here"));

        home_ll=(LinearLayout)findViewById(R.id.home_ll);
        offers_ll=(LinearLayout)findViewById(R.id.offers_ll);
        cart_ll=(LinearLayout)findViewById(R.id.cart_ll);
        user_ll=(LinearLayout)findViewById(R.id.user_ll);

        home_img=(ImageView)findViewById(R.id.home_img);
        offers_img=(ImageView)findViewById(R.id.offer_img);
        cart_img=(ImageView)findViewById(R.id.cart_img);
        user_img=(ImageView)findViewById(R.id.user_img);

        home_tv=(TextView) findViewById(R.id.home_tv);
        home_tv.setText(Session.getword(this,"SuperMarket"));
        offers_tv=(TextView) findViewById(R.id.offers_tv);
        offers_tv.setText(Session.getword(this,"Offers"));
        cart_tv=(TextView) findViewById(R.id.cart_tv);
        cart_tv.setText(Session.getword(this,"Cart"));
        user_tv=(TextView) findViewById(R.id.user_tv);
        user_tv.setText(Session.getword(this,"Profile"));

        viewPager = (ViewPager) findViewById(R.id.view4_pg);
        pagerSlidingTabStrip = (TabLayout) findViewById(R.id.tabLayout_pg);
        getProType();

        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                adapter.getthisfrag(viewPager.getCurrentItem()).callSearch(s.toString());



            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


//        for(int i=0; i < pagerSlidingTabStrip.getTabCount(); i++) {
//            View tab = ((ViewGroup) pagerSlidingTabStrip.getChildAt(0)).getChildAt(i);
//            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) tab.getLayoutParams();
//            p.setMargins(0, 0, 0, 0);
//            tab.requestLayout();
//        }


        home_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
                home_img.setImageResource(R.drawable.home_select);
                home_tv.setTextColor(getResources().getColor(R.color.light_red));
            }
        });
        offers_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
                offers_img.setImageResource(R.drawable.offer_select);
                offers_tv.setTextColor(getResources().getColor(R.color.light_red));
            }
        });
        cart_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
                cart_img.setImageResource(R.drawable.cart_select);
                cart_tv.setTextColor(getResources().getColor(R.color.light_red));
            }
        });
        user_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
                user_img.setImageResource(R.drawable.user_select);
                user_tv.setTextColor(getResources().getColor(R.color.light_red));
            }
        });
    }
    public class MyPagerAdapter extends FragmentPagerAdapter {
//        private final Map<Integer,CompanyListFragment>

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {

            return pro_title.get(position);
        }

        @Override
        public int getCount() {
            return pro_title.size();
        }

        @Override
        public Fragment getItem(int position) {

            if (frags == null) {
                frags = new Hashtable<>();
            }
//            frags.put(position, Shop_Fragment_Level2.newInstance(groups.get(position).group_name, groups.get(position)));
//            return frags.get(position);
            String key="";
            String from="";
           from=et_search.getText().toString();
            if(position==0){
                key=id;
//            }else if(position==2){
//                key="luxury";
//            }else if(position==3){
//                key="budget";
            }else{
                key=pro_id.get(position-1);
            }
            Log.e("search",from);
            frags.put(position,ProductFragment.newInstance(key,from));

            return frags.get(position);
        }

        public ProductFragment getthisfrag(int pos) {

            return frags.get(pos);
        }
    }
    public void reset(){
        home_img.setImageResource(R.drawable.home);
        offers_img.setImageResource(R.drawable.offer);
        cart_img.setImageResource(R.drawable.cart);
        user_img.setImageResource(R.drawable.user);
        home_tv.setTextColor(getResources().getColor(R.color.bottom_text));
        offers_tv.setTextColor(getResources().getColor(R.color.bottom_text));
        cart_tv.setTextColor(getResources().getColor(R.color.bottom_text));
        user_tv.setTextColor(getResources().getColor(R.color.bottom_text));
    }
    private void getProType(){
        String url;
        pro_id.clear();
        pro_title.clear();
        url = Session.SERVERURL + "sub-category.php?category_id="+id;
        Log.e("url", url);
        final ProgressDialog progressDialog = new ProgressDialog(ProductsActivity.this);
        progressDialog.setMessage("Please Wait....");
        progressDialog.show();
        progressDialog.setCancelable(false);
        JsonArrayRequest jsObjRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray jsonArray) {
                progressDialog.dismiss();
                pro_id.clear();
                pro_title.clear();
                try {
                    pro_title.add("   All   ");
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject tmp_json = jsonArray.getJSONObject(i);
                        pro_id.add(tmp_json.getString("id"));
                        pro_title.add(tmp_json.getString("title"+Session.get_lan(ProductsActivity.this)));
                    }
                    adapter = new MyPagerAdapter(getSupportFragmentManager());
                    pagerSlidingTabStrip.setupWithViewPager(viewPager);
                    viewPager.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                getLists();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                Log.e("response is:", error.toString());
                progressDialog.dismiss();

            }
        });

// Access the RequestQueue through your singleton class.
        AppController.getInstance().addToRequestQueue(jsObjRequest);
    }
}
