package com.example.android.miwok;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by RIPU on 12-Aug-16.
 */
public class WordAdapter extends ArrayAdapter<Word> {

    private int mColorId;


   //creating constructor of adapter.....see the android documentation to know what arguments it uses
    public WordAdapter(Activity context, ArrayList<Word> keywords, int colorId){
     super(context,0,keywords); //this is super class android adapter constructor
        mColorId=colorId;

    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listView = convertView;

        if(listView==null)
        {
            listView= LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        }

        Word pos = getItem(position);  //getItem() method is an array adapter method

        TextView textView= (TextView) listView.findViewById(R.id.text);
        textView.setText(pos.getMiwokTrans());

        TextView textView1=(TextView) listView.findViewById(R.id.default_text);
        textView1.setText(pos.getDefaultTrans());


            ImageView imageView = (ImageView) listView.findViewById(R.id.image_view);
        if(pos.trueImage()) {
            imageView.setImageResource(pos.getResourceId());
            imageView.setVisibility(View.VISIBLE);
        }

        else{
            imageView.setVisibility(View.GONE);
        }

        View textcolor = listView.findViewById(R.id.container1);
        int color = ContextCompat.getColor(getContext(),mColorId);
        textcolor.setBackgroundColor(color);



    return listView;
    }
}
