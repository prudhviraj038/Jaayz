package com.mamacgroup.jaayz;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class SubCategoryActivity extends Activity {
    RecyclerView rv;
    GridLayoutManager gl;
    ArrayList<Categories> categories=new ArrayList<>();
    EditText et_search;
    SubCategorydapter subCategorydapter;
    String id,name="";
    TextView no_sub_cat_tv,sub_head;
    ImageView back;
    LinearLayout home_ll,offers_ll,cart_ll,user_ll;
    ImageView home_img,offers_img,cart_img,user_img;
    TextView home_tv,offers_tv,cart_tv,user_tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub_category_screen);
        id=getIntent().getStringExtra("id");
        name=getIntent().getStringExtra("name");
        rv = (RecyclerView) findViewById(R.id.rv_sub_cat);

        back=(ImageView)findViewById(R.id.back_sub_cat);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


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



        et_search=(EditText) findViewById(R.id.et_search_sub);
        et_search.setHint(Session.getword(this,"Search Here"));

        no_sub_cat_tv=(TextView) findViewById(R.id.no_sub_cat_tv);
        no_sub_cat_tv.setText(Session.getword(this,"Sub categories are not available in this category"));
        sub_head=(TextView) findViewById(R.id.sub_head_tv);
        sub_head.setText(name);

        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (subCategorydapter != null) {
                    subCategorydapter.getFilter().filter(s);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


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
        getCategories();
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
    public void getCategories(){
        String url;
        categories.clear();
        url = Session.SERVERURL + "category.php";
        Log.e("url", url);
        final ProgressDialog progressDialog = new ProgressDialog(SubCategoryActivity.this);
        progressDialog.setMessage("Please Wait....");
        progressDialog.show();
        progressDialog.setCancelable(false);
        JsonArrayRequest jsObjRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray jsonArray) {
                progressDialog.dismiss();
                categories.clear();
                try {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        if(jsonArray.getJSONObject(i).getString("id").equals(id)) {
                            categories.add(new Categories(jsonArray.getJSONObject(i)));
                        }
                    }
                    if(categories.size()!=0) {
                        subCategorydapter = new SubCategorydapter(SubCategoryActivity.this, categories.get(0).subCategories);
                        gl = new GridLayoutManager(SubCategoryActivity.this, 2);
                        rv.setLayoutManager(gl);
                        rv.setItemAnimator(new DefaultItemAnimator());
                        rv.getItemAnimator().setMoveDuration(1000);
                        rv.setAdapter(subCategorydapter);
                        subCategorydapter.notifyDataSetChanged();
                        if(categories.get(0).subCategories.size()==0){
                            no_sub_cat_tv.setVisibility(View.VISIBLE);
                        }else{
                            no_sub_cat_tv.setVisibility(View.GONE);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
