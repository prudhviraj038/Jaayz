package com.mamacgroup.jaayz;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by yellowsoft on 8/1/18.
 */

public class Categories implements Serializable {
    String id,title,title_ar,image;
    ArrayList<SubCategories> subCategories=new ArrayList<>();
    public Categories(JSONObject jsonObject) {
            try {
                this.id = jsonObject.getString("id");
                this.title = jsonObject.getString("title");
                this.title_ar = jsonObject.getString("title_ar");
                this.image = jsonObject.getString("image");
                for(int i=0;i<jsonObject.getJSONArray("subcategory").length();i++){
                    subCategories.add(new SubCategories(jsonObject.getJSONArray("subcategory").getJSONObject(i)));
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
    public class SubCategories implements Serializable {
        String id, title, title_ar, image;
        Context context;
        public SubCategories(JSONObject jsonObject) {
            try {
                this.id = jsonObject.getString("id");
                this.title = jsonObject.getString("title");
                this.title_ar = jsonObject.getString("title_ar");
                this.image = jsonObject.getString("image");
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        public String getTitle(Context context) {
            if (Session.get_user_language(context).equals("en")) {
                return title;
            } else {
                return title_ar;
            }
        }
    }
}
