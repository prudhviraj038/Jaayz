package com.mamacgroup.jaayz;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class FirstTimeAreaSelectionActivity extends Activity {
    TextView sta_area;
    ListView lv;
    EditText et_area;
    AreaAdapter2 areaAdapter;
    ArrayList<Areas> areas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_time_area_selection_screen);

        areas=new ArrayList<>();
        sta_area=(TextView)findViewById(R.id.sta_area_tv);
        sta_area.setText(Session.getword(this,"Select Area"));
        et_area=(EditText) findViewById(R.id.et_area);
        et_area.setHint(Session.getword(this,"Search Here"));

        lv=(ListView)findViewById(R.id.area_listt);

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
        getAreas();
    }
    public void getAreas(){
        String url;
        areas.clear();
        url = Session.SERVERURL + "areas.php?";
        Log.e("url", url);
        final ProgressDialog progressDialog = new ProgressDialog(FirstTimeAreaSelectionActivity.this);
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
                        areas.add(new Areas(jsonArray.getJSONObject(i), FirstTimeAreaSelectionActivity.this, "0"));
                        for (int j = 0; j < jsonArray.getJSONObject(i).getJSONArray("areas").length(); j++) {
                            areas.add(new Areas(jsonArray.getJSONObject(i).getJSONArray("areas").getJSONObject(j), FirstTimeAreaSelectionActivity.this, "1"));
                        }
                    }
                    areaAdapter=new AreaAdapter2(FirstTimeAreaSelectionActivity.this,FirstTimeAreaSelectionActivity.this,areas);
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
}
