package com.mamacgroup.jaayz;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class ProductFragment extends Fragment {
    ProductsActivity productsActivity;
    ArrayList<Products> products=new ArrayList<>();
    String id,search="";
    RecyclerView rv;
    TextView no_products;
    ProductsAdapter productsAdapter;
    int page=0;
    int lastItem=0;
    int tem=0;
    GridLayoutManager gv;
    int firstVisibleItem, visibleItemCount, totalItemCount;
    private int preLast;
    public static ProductFragment newInstance(String s,String search) {
        ProductFragment companyListFragment = new ProductFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id",s);
        bundle.putString("from",search);
        companyListFragment.setArguments(bundle);
        return companyListFragment;
    }
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.product_fragment, container, false);

        productsActivity=(ProductsActivity) getActivity();
        id=getArguments().getString("id");
        search=getArguments().getString("from");

        rv = (RecyclerView)view.findViewById(R.id.rv_products);
        no_products=(TextView)view.findViewById(R.id.no_products);
        no_products.setText(Session.getword(getActivity(),"Products Are Not Available in This Category"));
        getProducts();
        rv.setOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(products.size()>3) {
                    visibleItemCount = rv.getChildCount();
                    totalItemCount = gv.getItemCount();
                    firstVisibleItem = gv.findFirstVisibleItemPosition();
                    lastItem = firstVisibleItem + rv.getChildCount();
                    tem = totalItemCount;
//                    Log.e("last  total", String.valueOf(lastItem) + "   " + String.valueOf(totalItemCount) + "   " + tem);
                    if (lastItem == totalItemCount) {
                        if (preLast != lastItem) {
                            //to avoid multiple calls for last item
//                            Log.d("Last", "Last");
                            preLast = lastItem;
                            page = page + 1;
                            getProducts();
                        }
                    }
                }
            }
        });
        return view;
    }
    public void getProducts(){
        String url;
//        products.clear();
        url = Session.SERVERURL + "products.php?category_id="+id+"&page="+page+"&search="+search;
        Log.e("url", url);
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please Wait....");
        progressDialog.show();
        progressDialog.setCancelable(false);
        JsonArrayRequest jsObjRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray jsonArray) {
                progressDialog.dismiss();
                if(page==0) {
                    products.clear();
                }
                try {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        products.add(new Products(jsonArray.getJSONObject(i)));
                    }
                    Log.e("products",jsonArray.toString());
                    productsAdapter = new ProductsAdapter(getActivity(),products);
                    gv=new GridLayoutManager(getActivity(),2);
                    rv.setLayoutManager(gv);
                    rv.setItemAnimator(new DefaultItemAnimator());
                    rv.getItemAnimator().setMoveDuration(1000);
                    rv.setAdapter(productsAdapter);
                    productsAdapter.notifyDataSetChanged();
                    if(products.size()==0){
                        no_products.setVisibility(View.VISIBLE);
                    }else{
                        no_products.setVisibility(View.GONE);
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


    public void callSearch(String string){

        Log.e(id,string);


    }
}
