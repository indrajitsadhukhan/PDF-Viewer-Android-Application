package com.example.pdfreader;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView l;
    public static ArrayList<File> filelist=new ArrayList<>();
    PDFAdapter obj_adapter;
    public static int REQUEST_PERMISSION=1;
    boolean boolean_permission;
    File dir;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        l=(ListView)findViewById(R.id.listview);
        dir=new File(Environment.getExternalStorageDirectory().toString());
        permission();
        l.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent =new Intent(getApplicationContext(),viewPDFFiles.class);
                intent.putExtra("position",position);
                startActivity(intent);
            }
        });
    }
    void permission()
    {
        if((ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)){
            if((ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)))
            {

            }
            else
            {
                ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_PERMISSION);
            }
        }
        else
        {
            boolean_permission=true;
            getfile(dir);
            obj_adapter=new PDFAdapter(getApplicationContext(),filelist);
            l.setAdapter(obj_adapter);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,  int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==REQUEST_PERMISSION)
        {
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                boolean_permission=true;
                getfile(dir);
                obj_adapter=new PDFAdapter(getApplicationContext(),filelist);
                l.setAdapter(obj_adapter);
            }
            else
            {
                Toast.makeText(this,"Please allow the permissions.",Toast.LENGTH_LONG).show();
            }
        }
    }
    public ArrayList<File> getfile(File dir)
    {
        File listFlie[]=dir.listFiles();
        if(listFlie!=null && listFlie.length>0)
        {
            for(int i=0;i<listFlie.length;i++)
            {
                if(listFlie[i].isDirectory())
                {
                    getfile(listFlie[i]);
                }
                else
                {
                    boolean booleanpdf=false;
                    if(listFlie[i].getName().endsWith(".pdf"))
                    {
                        for(int j=0;j<filelist.size();j++)
                        {
                            if(filelist.get(j).getName().equals(listFlie[i].getName()))
                            {
                                booleanpdf=true;
                            }
                            else
                            {

                            }
                        }
                        if(booleanpdf)
                            booleanpdf=false;
                        else
                        {
                            filelist.add(listFlie[i]);
                        }
                    }
                }
            }

        }
        return filelist;
    }
}