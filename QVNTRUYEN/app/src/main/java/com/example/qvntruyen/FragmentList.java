package com.example.qvntruyen;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

public class FragmentList extends Fragment {
    TextView htlist;
    GridView gView;
    Ad_Truyen HTTR;
    ArrayList<DS_Truyen> HT;
    String urlGetData="https://qvntruyendata.000webhostapp.com/getdata.php";
    int id;
    int k;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_list,container,false);
        HT=new ArrayList<>();
        gView=(GridView)view.findViewById(R.id.fragment_list_gv_item) ;
        HTTR = new Ad_Truyen(getContext(),R.layout.gv_list,HT);
        gView.setAdapter(HTTR);
        GetData(urlGetData);
//        if(HT.size()==0)
//        {
//            htlist=(TextView)view.findViewById(R.id.tb);
//            htlist.setText("List Yêu Thích Rỗng");
//        }
        Info();
        return view;

    }
    private void GetData(String url) {
        RequestQueue requestQueue =  Volley.newRequestQueue(getContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i=0; i<response.length();i++){
                            try {
                                JSONObject object = response.getJSONObject(i);
                                Cursor data= HomeActivity.database.GetData("SELECT * FROM List");
                                while (data.moveToNext()) {
                                    id = data.getInt(0);
                                    String is = String.valueOf(id);
                                    if (object.getInt("ID") == id) {
                                        HT.add(new DS_Truyen(
                                                object.getInt("ID"),
                                                object.getString("TenTruyen"),
                                                object.getString("TheLoai"),
                                                object.getString("Anh"),
                                                object.getString("TacGia"),
                                                object.getString("TomTat")
                                        ));
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        HTTR.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(),"Lỗi!", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);


    }
    private void Info(){
        gView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), Item_List.class);
                intent.putExtra("img", HT.get(position).getAnh());
                intent.putExtra("tentruyen", HT.get(position).getTenTruyen());
                intent.putExtra("theloai", HT.get(position).getTheLoai());
                intent.putExtra("id", HT.get(position).getID());
                intent.putExtra("tomtat", HT.get(position).getTomTat());
                startActivity(intent);
            }
        });

    }
}