package com.example.vimvoxlab.ocrmain;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
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
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    rvlistener newlistener;
    List<galleryimage> gimages;
    String main_folder=Environment.getExternalStorageDirectory().toString();

    class galleryimage{
        String glink;
        String gname;

        galleryimage(String glink, String gname){
            this.glink=glink;
            this.gname=gname;
        }

    }



        public boolean isExternalStorageWritable() {
            String state = Environment.getExternalStorageState();
            if (Environment.MEDIA_MOUNTED.equals(state)) {
                return true;
            }
            return false;
        }

        /* Checks if external storage is available to at least read */
        public boolean isExternalStorageReadable() {
            String state = Environment.getExternalStorageState();
            if (Environment.MEDIA_MOUNTED.equals(state) ||
                    Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
                return true;
            }
            return false;
        }
        public void checkstorage(){
            if((isExternalStorageReadable()==true)&&(isExternalStorageWritable()==true)){

                    File abc = new File(Environment.getExternalStorageDirectory()+"/tesseract/tessmain/tessdata");
                    abc.mkdirs();
                    File abc1 = new File(Environment.getExternalStorageDirectory()+"/tesseract/testimages");
                    abc1.mkdirs();
            }
           if((isExternalStorageWritable()==false)||(isExternalStorageReadable()==false)){
                    File abc = new File(Environment.getDataDirectory()+"/tesseract/tessmain/tessdata");
                    abc.mkdirs();
                    File abc1 = new File(Environment.getExternalStorageDirectory()+"/tesseract/testimages");
                    abc1.mkdirs();

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
        checkstorage();
//        checksharedpref();
        trained_datafile();
    }

    public void checksharedpref(){

        SharedPreferences prefs = this.getSharedPreferences(
                "com.example.vimvoxlab.ocrmain", Context.MODE_APPEND);
        if(prefs.getBoolean("access_allowed",false)==false){ sharedpref(); trained_datafile();
        Toast.makeText(getApplicationContext(),"first run",Toast.LENGTH_SHORT).show();}
        else {
            Toast.makeText(getApplicationContext(),"not first run",Toast.LENGTH_SHORT).show();
        }
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

        rv.setLayoutManager(llayout);
        rvadapter adapter = new rvadapter(gimages);
        rv.setAdapter(adapter);

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

    public void sharedpref(){
        SharedPreferences prefs = this.getSharedPreferences(
                "com.example.vimvoxlab.ocrmain", Context.MODE_APPEND);
        SharedPreferences.Editor newedit = prefs.edit();
        Boolean access_allowed= true;
        newedit.putBoolean("access_allowed",access_allowed);
        newedit.apply();
    }

    public void trained_datafile(){


        if (!(new File(main_folder + "tesseract/tessmain/tessdata/" + "ocrb" + ".traineddata")).exists()) {
            try {
                Toast.makeText(getApplicationContext(),"file pani chaina",Toast.LENGTH_SHORT).show();
                AssetManager assetManager = getAssets();
                InputStream in = assetManager.open("tessdata/" + "ocrb" + ".traineddata");
                OutputStream out = new FileOutputStream(main_folder
                        + "tesseract/tessmain/tessdata/" + "ocrb" + ".traineddata");
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }

                Toast.makeText(getApplicationContext(),in.toString(),Toast.LENGTH_SHORT).show();

                Toast.makeText(getApplicationContext(),out.toString(),Toast.LENGTH_SHORT).show();
                in.close();
                out.close();
                Toast.makeText(getApplicationContext(),"sucessful loading trained data",Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(),"unsucessful loading trained data",Toast.LENGTH_SHORT).show();
            }
        }

    }


}