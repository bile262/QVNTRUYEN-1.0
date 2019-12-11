package com.example.qvntruyen;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class LoaiTruyen extends AppCompatActivity {
    String url = "https://qvntruyendata.000webhostapp.com/getdata.php";
    //    Toolbar toolbar1;
    ArrayList<DS_Truyen> mangtr = new ArrayList<>();
    Ad_Truyen ad_truyen;
    GridView gridView;
    String text;
    ActionBar actionBar;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loai_truyen);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("LOẠI TRUYỆN");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        gridView = (GridView) findViewById(R.id.gv_id_loaitr) ;
        Intent  intent = getIntent();
        text =intent.getStringExtra("loaitruyen");


        ad_truyen = new Ad_Truyen(getApplicationContext(),R.layout.gv_item,mangtr);
        gridView.setAdapter(ad_truyen);
        GetData(url);
        Info();
    }



    private void GetData(String url) {
        RequestQueue requestQueue =  Volley.newRequestQueue(LoaiTruyen.this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i=0; i<response.length();i++){
                            try {

                                JSONObject object = response.getJSONObject(i);
                                String s1 = object.getString("TheLoai").trim().toLowerCase();
                                String s2 = text.trim().toLowerCase();
                                if( s2.equals(s1)) {
                                    System.out.println("Đúng");
                                }else {   System.out.println("Sai");
                                    System.out.println(object.getString("TheLoai").trim().toLowerCase());
                                    System.out.println(text.trim().toLowerCase());
                                }
                                if(s2.equals(s1)){
                                    mangtr.add(new DS_Truyen(
                                            object.getInt("ID"),
                                            object.getString("TenTruyen"),
                                            object.getString("TheLoai"),
                                            object.getString("Anh"),
                                            object.getString("TacGia")
                                    ));
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        ad_truyen.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoaiTruyen.this,"Lỗi!", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);

    }
    private void Info(){
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(LoaiTruyen.this, Item_click.class);
                intent.putExtra("img", mangtr.get(position).getAnh());
                intent.putExtra("tentruyen", mangtr.get(position).getTenTruyen());
                intent.putExtra("theloai", mangtr.get(position).getTheLoai());
                intent.putExtra("id", mangtr.get(position).getID());
                startActivity(intent);
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:break;
        }

        return super.onOptionsItemSelected(item);
    }
}