package com.example.qvntruyen;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

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
import java.util.List;

public class Search extends AppCompatActivity {
    SearchView searchview;
    String url = "https://qvntruyendata.000webhostapp.com/getdata.php";
    ArrayList<DS_Truyen> list = new ArrayList<DS_Truyen>();
    ArrayList<DS_Truyen> list2 = new ArrayList<DS_Truyen>();
    Ad_Truyen listtruyen2;
    GridView gView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        gView = (GridView)findViewById(R.id.listsearch);
        GetData(url);
        listtruyen2 = new Ad_Truyen(Search.this,R.layout.gv_item,list);
        searchview = (SearchView) findViewById(R.id.searchView);
        searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                Info();
                gView.setAdapter(listtruyen2);
//                Toast.makeText(Search.this, newText, Toast.LENGTH_SHORT).show();
//                if(newText.length()==0)
//                    Toast.makeText(Search.this, "gg", Toast.LENGTH_SHORT).show();
                list2.clear();
                if(!newText.equals(""))
                    for (DS_Truyen i : list){
                        if (i.getTenTruyen().trim().toLowerCase().contains(newText.toLowerCase()))
                            list2.add(i);
                        //                        Toast.makeText(Search.this, i.getTenTruyen(), Toast.LENGTH_SHORT).show();
                    }
//                if(newText.equals(""))
//                    list2.clear();
                listtruyen2 = new Ad_Truyen(Search.this, R.layout.gv_item, list2);
                gView.setAdapter(listtruyen2);
                return false;
            }
            public void callSearch(String query) {
                Toast.makeText(Search.this, query, Toast.LENGTH_SHORT).show();
            }
        });
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.drawer_menu_1, menu);
        return true;
    }
    private void GetData(String url) {
        RequestQueue requestQueue =  Volley.newRequestQueue(Search.this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i=0; i<response.length();i++){
                            try {
                                JSONObject object = response.getJSONObject(i);
                                list.add(new DS_Truyen(
                                        object.getInt("ID"),
                                        object.getString("TenTruyen"),
                                        object.getString("TheLoai"),
                                        object.getString("Anh"),
                                        object.getString("TacGia"),
                                        object.getString("TomTat")
                                ));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        listtruyen2.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Search.this,"Lỗi!", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);

    }
    private void Info(){
        gView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Search.this, Item_click.class);
                intent.putExtra("img", list2.get(position).getAnh());
                intent.putExtra("tentruyen", list2.get(position).getTenTruyen());
                intent.putExtra("theloai", list2.get(position).getTheLoai());
                intent.putExtra("id", list2.get(position).getID());
                intent.putExtra("tomtat", list2.get(position).getTomTat());
                startActivity(intent);
            }
        });

    }
}