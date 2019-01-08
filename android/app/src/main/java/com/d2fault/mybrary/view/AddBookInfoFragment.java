package com.d2fault.mybrary.view;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.d2fault.mybrary.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddBookInfoFragment extends Fragment {

    private View view;
    private Activity activity;
    private Button buttonFind;
    private String isbnStr;
    private final String TAG = "AddBookInfoFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_book_info, container, false);
        initModel();
        initView();
        initListener();
        // Inflate the layout for this fragment
        return view;
    }

    private void initModel() {
        activity = getActivity();
    }

    private void initView() {
        buttonFind = view.findViewById(R.id.buttonFind);
    }

    private void initListener() {
        buttonFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrator.setPrompt("Scan");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e("onActivityResult", " 들어옴");
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Log.d(TAG, "Cancelled scan");
                Toast.makeText(activity, "Cancelled", Toast.LENGTH_SHORT).show();
            } else {
                isbnStr = result.getContents();
                if (result.getFormatName().startsWith("EAN_13")) {
                    Toast.makeText(activity, isbnStr, Toast.LENGTH_SHORT).show();
                    getBookInfo();
                } else {
                    Log.e(TAG, "ISBN ERROR: EAN_13 형식이 아닙니다.\n인식 바코드: " + isbnStr);
                    Toast.makeText(activity, "ISBN을 인식하지 못했습니다", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void getBookInfo() {
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        String url = getString(R.string.get_book_info_url) + isbnStr + "&target=isbn";

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
                headers.put("Authorization", getString(R.string.kakao_api_key));

                return headers;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }
}
