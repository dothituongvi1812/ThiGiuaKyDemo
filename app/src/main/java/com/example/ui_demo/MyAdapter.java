package com.example.ui_demo;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MyAdapter extends ArrayAdapter<NhanVien> {
    Activity context=null;
    ArrayList<NhanVien> myArray=null;
    int layoutId;

    public MyAdapter(Activity context, int textViewResourceId, ArrayList<NhanVien> objects) {
        super(context, textViewResourceId, objects);
        this.context=context;
        this.layoutId=textViewResourceId;
        this.myArray=objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        convertView=inflater.inflate(layoutId,null);
        if(myArray.size()>0 && position>=0){
            final TextView tvMaSo=convertView.findViewById(R.id.tv_MaSo);
            final TextView tvHoTen=convertView.findViewById(R.id.tv_HoTen);
            final TextView tvGioiTinh=convertView.findViewById(R.id.tv_Gioitinh);
            final TextView tvDonVi=convertView.findViewById(R.id.tv_DonVi);
            final  NhanVien nv=myArray.get(position);
            Log.d("nhanVien",nv.toString());
            tvMaSo.setText(nv.getMa()+"");
            tvHoTen.setText(nv.getHoten());
            tvGioiTinh.setText(nv.getGioiTinh());
            tvDonVi.setText(nv.getDonVi());
            final ImageView imgitem=(ImageView) convertView.findViewById(R.id.image);
            imgitem.setImageURI(Uri.parse(nv.getImage()));
//

        }
        return  convertView;
    }
}
