package com.mamacgroup.jaayz;

import android.content.Context;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by yellowsoft on 8/1/18.
 */

public class Areas implements Serializable {
    String area_id;
    public String id;
    public String latitude;
    public String longitude;
    public String title;
    public String title_ar;
    public String type;
    public String address_id;
//    public Areas(JSONObject jsonObject, Context context) {
//        try {
//            this.id = jsonObject.getString("id");
//            this.title = jsonObject.getString("title");
//            this.title_ar = jsonObject.getString("title_ar");
////            this.address_id = jsonObject.get("address_id").getAsString();
////            this.latitude = jsonObject.get("latitude").getAsString();
////            this.longitude = jsonObject.get(Session.longitude).getAsString();
//            this.type = "0";
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//    }

    public Areas(JSONObject jsonObject, Context context, String type) {
        this.type = type;
            try {
                if (type.equals("0")) {
                    this.id = jsonObject.getString("id");
                    this.title = jsonObject.getString("title");
                    this.title_ar = jsonObject.getString("title_ar");
                    return;
                }else{
                    this.id = jsonObject.getString("id");
                    this.title = jsonObject.getString("title");
                    this.title_ar = jsonObject.getString("title_ar");
                    this.address_id = jsonObject.getString("address_id");
                    this.latitude = jsonObject.getString("latitude");
                    this.longitude = jsonObject.getString("longitude");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

    }
}
