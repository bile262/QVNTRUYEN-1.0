package com.example.qvntruyen;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.io.StringReader;
import java.util.ArrayList;

public class Item_click extends AppCompatActivity  implements Serializable {
    String urlGetData="https://qvntruyendata.000webhostapp.com/getdatachap.php";
    ActionBar actionBar;
    private DrawerLayout drawer;
    public static ArrayList<ChitietTruyen> img_detail;
    public static int[] listyt= new int[100] ;
    ListView listVieư;
    ChitietTruyenAdapter listchap;
    Context context;
    ImageView img;
    Button doctruyen;
    Button yeuthich;
    TextView tentruyen;
    TextView theloai;
    TextView tomtat;
    int id;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_click);
        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        doctruyen=(Button)findViewById(R.id.doctruyenbut);
        tomtat=(TextView)findViewById(R.id.nd);
        yeuthich=(Button)findViewById(R.id.yeuthichbut);
        img = (ImageView) findViewById(R.id.single_lap_img);
        tentruyen = (TextView) findViewById(R.id.chitiet_tentruyen);
        theloai=(TextView)findViewById(R.id.chitiet_theoai);
        String data = getIntent().getExtras().getString("img");
        String data2 = getIntent().getExtras().getString("tentruyen");
        String data3 = getIntent().getExtras().getString("tomtat");
        String tl= getIntent().getExtras().getString("theloai");
        id= getIntent().getExtras().getInt("id");
        tomtat.setText(data3);
        Picasso.with(context).load(Uri.parse(data)).into(img);
        tentruyen.setText(data2);
        theloai.setText("Thể loại: "+tl);
        listVieư=(ListView)findViewById(R.id.listchap);
        img_detail=new ArrayList<>();
        listchap =new ChitietTruyenAdapter(this,R.layout.dongchap,img_detail);
        listVieư.setAdapter(listchap);
        GetData(urlGetData,id);
        int kt=0;
        Cursor data1= HomeActivity.database.GetData("SELECT * FROM List");
        while (data1.moveToNext())
        {
            int iddt=data1.getInt(0);
            String is=String.valueOf(id);
            if(iddt==id)
                kt=1;


        }
        if(kt==0){
            Info3();
        }
        else {
            yeuthich.setEnabled(false);
        }


        Info1();
        Info2();




    }
    private void GetData(String url, final Integer id) {
        RequestQueue requestQueue =  Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i=0; i<response.length();i++){
                            try {
                                JSONObject object = response.getJSONObject(i);
                                if(object.getInt("ID")==id){
                                    img_detail.add(new ChitietTruyen(
                                            object.getInt("IDTruyen"),
                                            object.getString("Chap"),
                                            object.getString("NoiDung"),
                                            object.getInt("ID")
                                    ));}
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        listchap.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Item_click.this,"Lỗi!", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:break;
        }

        return super.onOptionsItemSelected(item);
    }
    private void  Info1() {
        listVieư.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                Intent intent = new Intent(Item_click.this, DocTruyenActivity.class);
                intent.putExtra("chap", img_detail.get(position).getChap());
                intent.putExtra("noidungchap", img_detail.get(position).getNoiDung());
                intent.putExtra("tentruyen", tentruyen.getText());
                startActivity(intent);
            }
        });

    }
    private void Info2(){
        doctruyen.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent intent = new Intent(Item_click.this, DocTruyenActivity.class);
                intent.putExtra("chap", img_detail.get(0).getChap());
                intent.putExtra("tentruyen", tentruyen.getText());
                startActivity(intent);


            }
        });

    }
    private  void Info3(){
        yeuthich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeActivity.database.QueryData("INSERT INTO List VALUES('"+ id +"')");
                Toast.makeText(Item_click.this,"Đã thêm vào List",Toast.LENGTH_SHORT).show();
            }
        });
    }
}