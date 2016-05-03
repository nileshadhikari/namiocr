package com.example.vimvoxlab.ocrmain;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.googlecode.leptonica.android.Binarize;
import com.googlecode.leptonica.android.Convert;
import com.googlecode.leptonica.android.Edge;
import com.googlecode.leptonica.android.Pix;
import com.googlecode.leptonica.android.ReadFile;
import com.googlecode.leptonica.android.WriteFile;
import com.googlecode.tesseract.android.TessBaseAPI;

public class imgprocess extends AppCompatActivity {

    Bitmap bmpmain = BitmapFactory.decodeFile("/storage/emulated/0/tesseract/testimages/abc.jpg");
    Bitmap newbmp = BitmapFactory.decodeFile("/storage/emulated/0/tesseract/testimages/abc.jpg");
    int i=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imgprocess);

        initialize(bmpmain);

//        grayscale();
//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        Binarize();
//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        initialize(newbmp);
//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        croptext(newbmp);

        FloatingActionButton btnocr = (FloatingActionButton) findViewById(R.id.btn_ocr);
        btnocr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ProgressBar newprogress = (ProgressBar)findViewById(R.id.progressBar);
//                try {
//                    Thread.sleep(3000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                grayscale(bmpmain);
//                try {
//                    Thread.sleep(3000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                Binarize(bmpmain);

//                initialize(croptext(bmpmain));
//                TextView newtr = (TextView)findViewById(R.id.textView);
//                newtr.setText(detectText(croptext(bmpmain)));
                    if(i==3){
                      newprogress.setVisibility(View.VISIBLE);
                        Toast ocr3 = Toast.makeText(getApplicationContext(),"Starting Detection",Toast.LENGTH_SHORT);
                        ocr3.show();
                    }
                    counterfunc();

            }
        });

        FloatingActionButton btnhome = (FloatingActionButton) findViewById(R.id.home);
        assert btnhome != null;
        btnhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeintent = new Intent(getApplicationContext(),MainActivity.class);
                finish();
                startActivity(homeintent);


            }

        });
        assert btnhome != null;
        btnhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeintent = new Intent(getApplicationContext(),MainActivity.class);
                finish();
                startActivity(homeintent);


            }

    });

    }

    public void initialize(Bitmap bmp){
        ImageView img = (ImageView) findViewById(R.id.ip_image);
        img.setImageBitmap(bmp);


    }


    public Bitmap Binarize(Bitmap bmp){
        Pix oldpix = ReadFile.readBitmap(bmp);
        Pix newpix = Binarize.otsuAdaptiveThreshold(oldpix);
        Bitmap newbmp = WriteFile.writeBitmap(newpix);
        return newbmp;
    }

    public Bitmap grayscale(Bitmap bmp){
        Pix old = ReadFile.readBitmap(bmp);
        Pix newp = Convert.convertTo8(old);
        Bitmap newbmp = WriteFile.writeBitmap(newp);
        return newbmp;

    }

    public void edgedetection(){
        Pix old = ReadFile.readBitmap(newbmp);
        Pix newp = Edge.pixSobelEdgeFilter(old, Edge.L_ALL_EDGES);
        Bitmap newbmp = WriteFile.writeBitmap(newp);
        initialize(newbmp);

    }

    public Bitmap croptext(Bitmap bitmap){
        Bitmap newbm = bitmap.createBitmap(bitmap, 60, bitmap.getHeight() - 250, bitmap.getWidth() - 260, 200);
        return newbm;
    }

    public void counterfunc(){
        FloatingActionButton btnhome = (FloatingActionButton) findViewById(R.id.home);
        TextView tv2 = (TextView)findViewById(R.id.textView2);
        ProgressBar newprogress = (ProgressBar)findViewById(R.id.progressBar);
        i = i+1;
        if(i==1){
            tv2.setText("");
            btnhome.setVisibility(View.INVISIBLE);
            initialize(grayscale(bmpmain));
            Toast ocr1 = Toast.makeText(getApplicationContext(),"The image is now grayscaled",Toast.LENGTH_SHORT);
            ocr1.show();
        }
        if(i==2){
            initialize(Binarize(bmpmain));

            Toast ocr2 = Toast.makeText(getApplicationContext(), "The image is now binarized", Toast.LENGTH_SHORT);
            ocr2.show();
        }
        if(i==3){

//            String abc = detectText(croptext(bmpmain));

            newprogress.setVisibility(View.VISIBLE);




            try {
                Thread.sleep(3000);
                initialize(Binarize(croptext(bmpmain)));

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Toast ocr4 = Toast.makeText(getApplicationContext(),"Detection Complete",Toast.LENGTH_SHORT);
            ocr4.show();


            tv2.setText(detectText(croptext(bmpmain)));
            i=0;
            btnhome.setVisibility(View.VISIBLE);
            newprogress.setVisibility(View.INVISIBLE);
        }

    }
    public String detectText(Bitmap bitmap) {


        TessBaseAPI tessBaseAPI = new TessBaseAPI();

        String path = "/storage/emulated/0/tesseract/tessmain/";

        tessBaseAPI.setDebug(true);
        tessBaseAPI.init(path, "eng"); //Init the Tess with the trained data file, with english language

        //For example if we want to only detect numbers
//        tessBaseAPI.setVariable(TessBaseAPI.VAR_CHAR_BLACKLIST, "1234567890");
        tessBaseAPI.setVariable(TessBaseAPI.VAR_CHAR_WHITELIST, "1234567890!@#$%^&*()_+=-qwertyuiop[]}{POIU" +
                "YTREWQasdASDfghFGHjklJKLl;L:'\"\\|~`xcvXCVbnmBNM,./<>?");


        tessBaseAPI.setImage(bitmap);

        String text = tessBaseAPI.getUTF8Text();
        tessBaseAPI.end();
        return text;
    }
}
