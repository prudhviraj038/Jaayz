package com.mamacgroup.jaayz;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
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
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeActivity extends Activity {
    RecyclerView rv;
    HomeAdapter homeAdapter;
    LinearLayoutManager gl;
    ArrayList<Areas> areas;
    ArrayList<Categories> categories=new ArrayList<>();
    AreaAdapter areaAdapter;
    LinearLayout area_pop_ll,area_home_ll;
    ImageView close;
    TextView sta_area,area_home_tv;
    ListView lv;
    EditText et_area;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);
        areas=new ArrayList<>();
        rv = (RecyclerView) findViewById(R.id.rv_home);

        lv=(ListView)findViewById(R.id.area_listt);

        area_pop_ll=(LinearLayout)findViewById(R.id.area_pop_ll);
        area_home_ll=(LinearLayout)findViewById(R.id.area_home_ll);

        sta_area=(TextView)findViewById(R.id.sta_area_tv);
        sta_area.setText(Session.getword(this,"Select Area"));
        area_home_tv=(TextView)findViewById(R.id.area_home_tv);
        area_home_tv.setText(Session.getAreaName(this));

        et_area=(EditText) findViewById(R.id.et_area);
        et_area.setHint(Session.getword(this,"Search Here"));

        close=(ImageView) findViewById(R.id.close_img_area);

        et_area.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (areaAdapter != null) {
                    areaAdapter.getFilter().filter(s);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        area_home_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                area_pop_ll.setVisibility(View.VISIBLE);
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                area_pop_ll.setVisibility(View.GONE);
            }
        });

        getAreas();
        getCategories();
    }
    public void getAreas(){
        String url;
        areas.clear();
        url = Session.SERVERURL + "areas.php?";
        Log.e("url", url);
        final ProgressDialog progressDialog = new ProgressDialog(HomeActivity.this);
        progressDialog.setMessage("Please Wait....");
        progressDialog.show();
        progressDialog.setCancelable(false);
        JsonArrayRequest jsObjRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray jsonArray) {
                progressDialog.dismiss();
                areas.clear();
                try {
                    for (int i = 0; i < jsonArray.length(); i++) {
                       areas.add(new Areas(jsonArray.getJSONObject(i), HomeActivity.this, "0"));
                        for (int j = 0; j < jsonArray.getJSONObject(i).getJSONArray("areas").length(); j++) {
                            areas.add(new Areas(jsonArray.getJSONObject(i).getJSONArray("areas").getJSONObject(j), HomeActivity.this, "1"));
                        }
                    }
                    areaAdapter=new AreaAdapter(HomeActivity.this,HomeActivity.this,areas);
                    lv.setAdapter(areaAdapter);
                    areaAdapter.notifyDataSetChanged();
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
    public void getCategories(){
        String url;
        categories.clear();
        url = Session.SERVERURL + "category.php";
        Log.e("url", url);
        final ProgressDialog progressDialog = new ProgressDialog(HomeActivity.this);
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
                        categories.add(new Categories(jsonArray.getJSONObject(i)));
                    }
                    homeAdapter = new HomeAdapter(HomeActivity.this,categories);
                    gl = new LinearLayoutManager(HomeActivity.this);
                    rv.setLayoutManager(gl);
                    rv.setItemAnimator(new DefaultItemAnimator());
                    rv.getItemAnimator().setMoveDuration(1000);
                    rv.setAdapter(homeAdapter);
                    homeAdapter.notifyDataSetChanged();
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
