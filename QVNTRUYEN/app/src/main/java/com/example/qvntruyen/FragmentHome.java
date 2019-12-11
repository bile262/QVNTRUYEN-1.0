package com.example.qvntruyen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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

public class FragmentHome extends Fragment {
    String urlGetData="https://qvntruyendata.000webhostapp.com/getdata.php";
    TextView mTvName;
    ArrayList<DS_Truyen> img_detail;
    GridView gView;
    Ad_Truyen listtruyen;
    FrameLayout frameLayout;
    //FragmentManager fm = getFragmentManager();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        gView = (GridView) view.findViewById(R.id.fragment_home_gv_item);
        img_detail = new ArrayList<>();
        listtruyen = new Ad_Truyen(getContext(),R.layout.gv_item,img_detail);

        gView.setAdapter(listtruyen);
        GetData(urlGetData);
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
                                img_detail.add(new DS_Truyen(
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
                        listtruyen.notifyDataSetChanged();
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
                Intent intent = new Intent(getContext(), Item_click.class);
                intent.putExtra("img", img_detail.get(position).getAnh());
                intent.putExtra("tentruyen", img_detail.get(position).getTenTruyen());
                intent.putExtra("theloai", img_detail.get(position).getTheLoai());
                intent.putExtra("id", img_detail.get(position).getID());
                intent.putExtra("tomtat", img_detail.get(position).getTomTat());
                startActivity(intent);
            }
        });

    }

}