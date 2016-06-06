package com.example.vimvoxlab.ocrmain;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vimvoxlab.ocrmain.MainActivity.galleryimage;

import java.util.List;

/**
 * Created by nilesh on 4/27/16.
 */
public class rvadapter extends RecyclerView.Adapter<rvadapter.PersonViewHolder>   {

    rvlistener mOnClickListener;

    public rvadapter(List<galleryimage> gimages) {
        this.gimages = gimages;
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_layout, viewGroup, false);
        v.setOnClickListener(mOnClickListener);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(PersonViewHolder personViewHolder, int position) {

        personViewHolder.filePhoto.setImageBitmap(BitmapFactory.decodeFile(gimages.get(position).glink));
//        personViewHolder.fileName.setText(gimages.get(position).gname);
//        personViewHolder.btnedit.setOnClickListener(mOnClickListener);


    }

    @Override
    public int getItemCount() {
        return gimages.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder {
        CardView card_view;
        TextView fileName;
        ImageView filePhoto;
        Button btnedit;



        PersonViewHolder(View itemView) {
            super(itemView);
            card_view = (CardView)itemView.findViewById(R.id.card_view);
//            fileName = (TextView)itemView.findViewById(R.id.textView);
            filePhoto = (ImageView)itemView.findViewById(R.id.imageView);
//            btnedit = (Button)itemView.findViewById(R.id.btn_edit);

        }
    }

    List<galleryimage> gimages;

}
