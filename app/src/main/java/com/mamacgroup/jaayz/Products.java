package com.mamacgroup.jaayz;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by yellowsoft on 8/1/18.
 */

public class Products implements Serializable {
    String id,title,title_ar,price,old_price,quantity,about,about_ar,description,description_ar,cat_id,cat_title,cat_title_ar;
    ArrayList<String> images=new ArrayList<>();
    int qty=1;
    public Products(JSONObject jsonObject) {
            try {
                this.id = jsonObject.getString("id");
                this.title = jsonObject.getString("title");
                this.title_ar = jsonObject.getString("title_ar");
                this.price = jsonObject.getString("price");
                this.old_price = jsonObject.getString("old_price");
                this.quantity = jsonObject.getString("quantity");
                this.about = jsonObject.getString("about");
                this.about_ar = jsonObject.getString("about_ar");
                this.cat_id = jsonObject.getJSONObject("category").getString("id");
                this.cat_title = jsonObject.getJSONObject("category").getString("title");
                this.cat_title_ar = jsonObject.getJSONObject("category").getString("title_ar");
                this.description = jsonObject.getString("description");
                this.description_ar = jsonObject.getString("description_ar");
                for(int i=0;i<jsonObject.getJSONArray("images").length();i++){
                    images.add(jsonObject.getJSONArray("images").get(i).toString());
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

    }
    public String getTitle(Context context){
        if(Session.get_user_language(context).equals("en")){
             return title;
        }else{
            return title_ar;
        }
    }
    public String getCatTitle(Context context){
        if(Session.get_user_language(context).equals("en")){
            return cat_title;
        }else{
            return cat_title_ar;
        }
    }
    public String getAbout(Context context){
        if(Session.get_user_language(context).equals("en")){
            return about;
        }else{
            return about_ar;
        }
    }
    public String getDescription(Context context){
        if(Session.get_user_language(context).equals("en")){
            return description;
        }else{
            return description_ar;
        }
    }
}
