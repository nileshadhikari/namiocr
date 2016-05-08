package com.example.vimvoxlab.ocrmain;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    List<galleryimage> gimages;

    class galleryimage{
        String glink;
        String gname;

        galleryimage(String glink, String gname){
            this.glink=glink;
            this.gname=gname;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FloatingActionButton abc = (FloatingActionButton) findViewById(R.id.fab);
        assert abc != null;
        abc.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent camintent = new Intent(getApplicationContext(), CamActivity.class);
                finish();
                startActivity(camintent);
            }
        });

        FloatingActionButton bbc = (FloatingActionButton) findViewById(R.id.ippo);
        assert bbc != null;
        bbc.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent editorintent = new Intent(getApplicationContext(),imgprocess.class);
                editorintent.putExtra("link",getIntent().getStringExtra("link"));
                finish();
                startActivity(editorintent);
            }
        });



        imagefinalize();
        recycleview();


    }

//    public void findimages(){
//
//        ArrayList<File> m_list = new ArrayList<File>();
//
//        String folderpath = Environment.getExternalStorageDirectory()
//                + File.separator + "tesseract/testimages/";
//        File dir = new File(folderpath);
//        m_list.clear();
//        if (dir.isDirectory()) {
//            File[] files = dir.listFiles();
//            for (File file : files) {
//                if (!file.getPath().contains("Not_Found"))
//                    m_list.add(file);
//            }
//        }
//    }


    public void recycleview(){
        RecyclerView rv =(RecyclerView) findViewById(R.id.rv);
        rv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        GridLayoutManager llayout = new GridLayoutManager(getApplicationContext(),2);

        rv.setLayoutManager(llm);
        rvadapter adapter = new rvadapter(gimages);
        rv.setAdapter(adapter);

    }

    public void externalstorage(){



    }

    public void imagefinalize(){

            gimages = new ArrayList<>();


            String folderpath = Environment.getExternalStorageDirectory()
                    + File.separator + "tesseract/testimages/";
            File dir = new File(folderpath);
//            m_list.clear();
            if (dir.isDirectory()) {
                File[] files = dir.listFiles();
                for (File file : files) {
//                    if (!file.getPath().contains("Not_Found"))
//                        m_list.add(file);
//                    file.getName();
                    if((file.isFile()==true)){
                        gimages.add(new galleryimage((folderpath + file.getName().toString()),file.getName()));
                    }
                }
            }



    }




}




