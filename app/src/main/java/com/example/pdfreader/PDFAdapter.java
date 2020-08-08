package com.example.pdfreader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class PDFAdapter  extends ArrayAdapter<File> {
    Context c;
    ViewHolder v;
    ArrayList<File> arr;

    public PDFAdapter(@NonNull Context context, ArrayList<File> arr) {
       super(context,R.layout.adapter_pdf,arr);
       this.c=context;
        this.arr = arr;
    }
    @Override
    public int getItemViewType(int position)
    {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        if(arr.size()>0)
        {
            return arr.size();
        }
        else
        {
            return 1;
        }
    }


    @Override
    public View getView(final int position,View convertView,ViewGroup parent) {
        if (convertView==null)
        {
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.adapter_pdf,parent,false);
            v=new ViewHolder();
            v.t=(TextView)convertView.findViewById(R.id.file_name);
            convertView.setTag(v);

        }
        else
        {
            v=(ViewHolder)convertView.getTag();
        }
        v.t.setText(arr.get(position).getName());
        return convertView;
    }

    public class ViewHolder
    {
        TextView t;
    }
}
