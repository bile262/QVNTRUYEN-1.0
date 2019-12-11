package com.example.qvntruyen;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class Ad_Truyen extends BaseAdapter {
    private Context context;
    private int layout;
    private List<DS_Truyen> DSList;

    public Ad_Truyen(Context context, int layout, List<DS_Truyen> DSList) {
        this.context = context;
        this.layout = layout;
        this.DSList = DSList;

    }

    @Override
    public int getCount() {
        return DSList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
    private class ViewHolder{
        TextView name;
        TextView theloai;
        TextView tacgia;
        ImageView img;
        TextView tomtat;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view==null)
        {
            holder= new ViewHolder();
            LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,null);
            holder.img=(ImageView) view.findViewById(R.id.gv_item_iv_image);
            holder.name=(TextView) view.findViewById(R.id.gv_item_tv_nameimage);
            holder.theloai=(TextView) view.findViewById(R.id.gv_item_theloai);
            holder.tacgia=(TextView) view.findViewById(R.id.gv_item_tg);
            holder.tomtat=(TextView)view.findViewById(R.id.gv_item_tt);
            view.setTag(holder);
        }else{
            holder=(ViewHolder)view.getTag();
        }
        DS_Truyen ds_truyen= this.DSList.get(i);
        holder.name.setText(ds_truyen.getTenTruyen());
        holder.theloai.setText(ds_truyen.getTheLoai());
        holder.tacgia.setText(ds_truyen.getTacGia());
        Picasso.with(context).load(ds_truyen.getAnh()).into(holder.img);
        holder.tomtat.setText(ds_truyen.getTomTat());
        //int ImgID = getMipMapResIdByName(ds_truyen.getAnh());
        //holder.img.setImageResource(ImgID);
        //Toast.makeText(context, a, Toast.LENGTH_SHORT).show();

        return view;
    }
    public int getMipMapResIdByName(String resName){
        String pgkName = context.getPackageName();
        int resID = context.getResources().getIdentifier(resName, "mipmap", pgkName);
        Log.i("CustomListView", "ResName:" + resName + "==> Res ID =" + resID);
        return resID;
    }
}
