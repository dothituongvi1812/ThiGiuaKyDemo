package com.example.ui_demo;


import androidx.annotation.LongDef;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ArrayList<NhanVien> nv_List = null;
    MyAdapter myAdapter = null;
    String[] dv_List;
    String donvi;
    String uri = "@drawable/";
    ImageView imageView;
    int viTriDuocChon;
    EditText edtMa;
    EditText edtHoTen;
    RadioGroup radioGioiTinh;
    Spinner spinner_DV;
    int SELECT_PICTURE = 200;
    Uri currentUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         edtMa = findViewById(R.id.edt_nhapMa);
         edtHoTen = findViewById(R.id.edt_nhapHoten);
        ListView lv_NhanVien = findViewById(R.id.listViewItem);
         radioGioiTinh = findViewById(R.id.radioGroupGT);
        RadioButton rdNu = findViewById(R.id.radioNu);
        RadioButton rdNam = findViewById(R.id.radioNam);
         imageView = findViewById(R.id.imageAvt);
         spinner_DV = findViewById(R.id.spinnerDonVi);
        Button btnThoat=findViewById(R.id.buttonThoat);
        Button btnGhiFile=findViewById(R.id.button_GhiFile);
        Button btnDocFile=findViewById(R.id.button_DocFile);
        Button btnSua=findViewById(R.id.button_Sua);
        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Button btnChonHinhAnh=findViewById(R.id.btnChonHinhAnh);
        btnChonHinhAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageChooser();
            }
        });

        dv_List = getResources().getStringArray(R.array.donvi_list);
        nv_List = new ArrayList<>();
        myAdapter = new MyAdapter(this, R.layout.custom_list_item, nv_List);
        lv_NhanVien.setAdapter(myAdapter);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dv_List);
        spinner_DV.setAdapter(adapter);
        spinner_DV.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                donvi = dv_List[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Button btnThem = findViewById(R.id.button_Them);
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int maSo = Integer.parseInt(edtMa.getText().toString());
                String hoTen = edtHoTen.getText().toString();
                String gioiTinh = ((RadioButton) findViewById(radioGioiTinh.getCheckedRadioButtonId())).getText().toString();
                //Tạo đối tượng nhân viên
                NhanVien nv = new NhanVien(maSo, hoTen, gioiTinh, donvi);

                nv.setImage(currentUri.toString());
                nv_List.add(nv);
                myAdapter.notifyDataSetChanged();
  //              writoFileTe(nv.toString());
                xoaTrang();
            }
        });

        lv_NhanVien.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                NhanVien nv = nv_List.get(i);
                edtMa.setText(nv.getMa() + "");
                edtHoTen.setText(nv.getHoten());
                Uri uri = Uri.parse(nv.getImage());
                imageView.setImageURI(uri);

                if (nv.getGioiTinh().equals("Nam")) {
                    rdNam.setChecked(true);
                } else
                    rdNu.setChecked(true);

                for (int j = 0; j < dv_List.length; j++) {
                    if (dv_List[j].equals(nv.getDonVi())) {
                        spinner_DV.setSelection(j);
                    }
                }
                viTriDuocChon=i;
            }
        });
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NhanVien nhanVien=nv_List.get(viTriDuocChon);
                nhanVien.setHoten(edtHoTen.getText().toString());
                String sex=((RadioButton) findViewById(radioGioiTinh.getCheckedRadioButtonId())).getText().toString();
                nhanVien.setGioiTinh(sex.toString());
                Log.d("sex",sex.toString());
                nhanVien.setDonVi(donvi);
                myAdapter.notifyDataSetChanged();
                xoaTrang();
            }
        });
//        ủa chổ này event đọc file mà chổ event ghi đâu
        btnDocFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BufferedReader reader = null;
                try {
                    InputStream inputStream = openFileInput("nhanvien.txt");

                    reader = new BufferedReader(
                            new InputStreamReader(inputStream));
                    // do reading, usually loop until end of file reading
                    String mLine;
                    while ((mLine = reader.readLine()) != null) {
                        //process l
                        Log.e(" reader.readLine()", mLine);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e) {
                            //log the exception
                        }
                    }

                }

            }
        });
//        btnGhiFile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                int maSo = Integer.parseInt(edtMa.getText().toString());
//                String hoTen = edtHoTen.getText().toString();
//                String gioiTinh = ((RadioButton) findViewById(radioGioiTinh.getCheckedRadioButtonId())).getText().toString();
//                //Tạo đối tượng nhân viên
//                NhanVien nv = new NhanVien(maSo, hoTen, gioiTinh, donvi);
//                nv.setImage(uri);
//
//
//            }
//
//        });

    }
    public void xoaTrang(){
        edtHoTen.setText("");
        edtMa.setText("");
        radioGioiTinh.clearCheck();
        spinner_DV.setSelection(0);

    }
    public void imageChooser() {

        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode ==SELECT_PICTURE) {
                // Get the url of the image from data
                Uri selectedImageUri = data.getData();
                Log.d("selectedImageUri", selectedImageUri.toString());
                if (null != selectedImageUri) {
                    imageView.setImageURI(selectedImageUri);
                    this.currentUri = selectedImageUri;

//                    lấy cái uri này lưu vô sinh viên

                }
            }

        }

    }
    public  ArrayList<String> readFile(){
        BufferedReader reader = null;
        ArrayList<String> mLine = new ArrayList<String>();

        try {
            InputStream inputStream = openFileInput("nhanvien.txt");

            reader = new BufferedReader(
                    new InputStreamReader(inputStream));
            // do reading, usually loop until end of file reading
            String text = "";
            while ((text= reader.readLine()) != null) {
                //process l
                mLine.add(text);
            }
            return mLine;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }

        }
        return mLine;
    }
    public void writeToFile( String content){
        String filename = "nhanvien.txt";
        FileOutputStream fout = null;
        Log.d("write", content);
//        ArrayList<String> oldDataFile = this.readFile();
        try {
            fout = openFileOutput(filename, Context.MODE_PRIVATE);
//            for ( String s : oldDataFile)
//            {
//                fout.write(oldDataFile.toString().getBytes());
//                fout.write(System.getProperty("line.separator").getBytes());
//
//            }
            fout.write(content.getBytes());
            fout.write(System.getProperty("line.separator").getBytes());
            fout.close();
            Toast.makeText(this,"Xong",Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}