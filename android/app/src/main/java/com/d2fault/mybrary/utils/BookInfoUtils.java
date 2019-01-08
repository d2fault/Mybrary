package com.d2fault.mybrary.utils;

import android.app.Activity;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.d2fault.mybrary.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class BookInfoUtils {
    private static final String TAG = "AddBookInfoFragment";

    public static void getBookInfo(final Activity activity, String isbnStr) {
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        String url = activity.getString(R.string.get_book_info_url) + isbnStr + "&target=isbn";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray documentsJSONArray = response.getJSONArray("documents");
                            // meta에 totalcount가 있는데 아마 사용하지 않을 것 같긴 하다
                            JSONObject metaJSONObj = response.getJSONObject("meta");

                            Log.d(TAG, "authors: " + documentsJSONArray.getJSONObject(0).getJSONArray("authors").get(0));
                            Log.d(TAG, "contents: " + documentsJSONArray.getJSONObject(0).getString("contents"));
                            Log.d(TAG, "datetime: " + documentsJSONArray.getJSONObject(0).getString("datetime"));
                            Log.d(TAG, "isbn: " + documentsJSONArray.getJSONObject(0).getString("isbn"));
                            Log.d(TAG, "price: " + documentsJSONArray.getJSONObject(0).getInt("price"));
                            Log.d(TAG, "publisher: " + documentsJSONArray.getJSONObject(0).getString("publisher"));
                            Log.d(TAG, "sale_price: " + documentsJSONArray.getJSONObject(0).getInt("sale_price"));
                            Log.d(TAG, "status: " + documentsJSONArray.getJSONObject(0).getString("status"));
                            Log.d(TAG, "thumbnail: " + documentsJSONArray.getJSONObject(0).getString("thumbnail"));
                            Log.d(TAG, "title: " + documentsJSONArray.getJSONObject(0).getString("title"));
                            Log.d(TAG, "translators: " + documentsJSONArray.getJSONObject(0).getJSONArray("translators").get(0));
                            Log.d(TAG, "url: " + documentsJSONArray.getJSONObject(0).getString("url"));


                            //documentsJSONArray.getJSONObject(0).getJSONObject("authors");
                            Log.d(TAG, "jsonarray: " + documentsJSONArray.toString());
                            Log.d(TAG, "jsonobject: " + metaJSONObj.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, error.toString());
                    }
                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  headers = new HashMap<String, String>();
                headers.put("Authorization", activity.getString(R.string.kakao_api_key));
                return headers;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }
}
