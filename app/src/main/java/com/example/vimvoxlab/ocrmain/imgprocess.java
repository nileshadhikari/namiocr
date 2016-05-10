package com.example.vimvoxlab.ocrmain;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.googlecode.leptonica.android.AdaptiveMap;
import com.googlecode.leptonica.android.Binarize;
import com.googlecode.leptonica.android.Convert;
import com.googlecode.leptonica.android.Edge;
import com.googlecode.leptonica.android.GrayQuant;
import com.googlecode.leptonica.android.MorphApp;
import com.googlecode.leptonica.android.Pix;
import com.googlecode.leptonica.android.ReadFile;
import com.googlecode.leptonica.android.Rotate;
import com.googlecode.leptonica.android.Skew;
import com.googlecode.leptonica.android.WriteFile;
import com.googlecode.tesseract.android.TessBaseAPI;
import com.isseiaoki.simplecropview.CropImageView;
import com.isseiaoki.simplecropview.callback.CropCallback;
import com.isseiaoki.simplecropview.callback.LoadCallback;
import com.isseiaoki.simplecropview.callback.SaveCallback;


import java.io.ByteArrayOutputStream;

public class imgprocess extends AppCompatActivity {

    String abclink="/storage/emulated/0/tesseract/testimages/abc.jpg";
    Bitmap bmpmain = BitmapFactory.decodeFile(abclink);
    Bitmap newbmp;
    Bitmap uniquebmp;


    int i=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imgprocess);
//        picassa();


        initialize(Binarize(bmpmain));

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

                CropImageView mCropView = (CropImageView) findViewById(R.id.cropImageView);
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
                    if(i==0){
                        uniquebmp = mCropView.getCroppedBitmap();
                    }

                    initialize(mCropView.getCroppedBitmap());
                    mCropView.setVisibility(View.INVISIBLE);
                    counterfunc(uniquebmp);
//
//                TextView tv2 = (TextView)findViewById(R.id.textView2);
//                tv2.setText(detectText(Binarize(mCropView.getCroppedBitmap())));
            }
        });

        CropImageView mCropView = (CropImageView) findViewById(R.id.cropImageView);
        mCropView.setCropMode(CropImageView.CropMode.FREE);
        mCropView.setMinFrameSizeInDp(10);

        assert mCropView != null;
        mCropView.startLoad(

                getImageUri(getApplicationContext(),bmpmain),
                new LoadCallback() {
                    @Override
                    public void onSuccess() {}

                    @Override
                    public void onError() {}
                });

//        mCropView.startCrop(
//
//                Uri.parse("/storage/emulated/0/tesseract/testimages/temp.jpg"),
//
//                new CropCallback() {
//                    @Override
//                    public void onSuccess(Bitmap cropped) {}
//
//                    @Override
//                    public void onError() {}
//                },
//
//                new SaveCallback() {
//                    @Override
//                    public void onSuccess(Uri outputUri) {}
//
//                    @Override
//                    public void onError() {}
//                }
//        );


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

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
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

    public Bitmap Grayquant(Bitmap bmp){
        Pix old = ReadFile.readBitmap(bmp);
        Pix newp = GrayQuant.pixThresholdToBinary(old,60);
        Bitmap newbmp = WriteFile.writeBitmap(newp);
        return newbmp;
    }

    public Bitmap rotate(Bitmap bmp){
        Pix old = ReadFile.readBitmap(bmp);
        Pix haha = Rotate.rotate(old,40);
        Bitmap newbmp = WriteFile.writeBitmap(haha);
        return newbmp;
    }
     public Bitmap Tophat(Bitmap bmp){

         Pix old = ReadFile.readBitmap(bmp);
         Pix newp = MorphApp.pixFastTophatBlack(old);
         Bitmap newbmp = WriteFile.writeBitmap(newp);
         return newbmp;
     }

    public Bitmap croptext(Bitmap bitmap){
        Bitmap newbm = bitmap.createBitmap(bitmap, 60, bitmap.getHeight() - 250, bitmap.getWidth() - 260, 200);
        return newbm;
    }

//    public Bitmap enhance(Bitmap bmp){
//        Pix old = ReadFile.readBitmap(bmp);
//        Pix new = AdaptiveMap.backgroundNormMorph(old,)
//
//        Bitmap newbmp = WriteFile.writeBitmap(newp);
//        return newbmp;
//    };

    public void counterfunc(Bitmap newbp){
        Bitmap temp_bmp = newbp;
        FloatingActionButton btnhome = (FloatingActionButton) findViewById(R.id.home);

        TextView tv2 = (TextView)findViewById(R.id.textView2);
        CropImageView mCropView = (CropImageView) findViewById(R.id.cropImageView);
        initialize(temp_bmp);
        i = i+1;
        if(i==1){
            tv2.setText("");
            btnhome.setVisibility(View.INVISIBLE);
            initialize(grayscale(temp_bmp));
            Toast ocr1 = Toast.makeText(getApplicationContext(),"The image is now grayscaled",Toast.LENGTH_SHORT);
            ocr1.show();
        }
        if(i==2){
            initialize(Binarize(grayscale(temp_bmp)));
            Toast ocr2 = Toast.makeText(getApplicationContext(), "The image is now binarized", Toast.LENGTH_SHORT);
            ocr2.show();
        }
        if(i==3){

//            String abc = detectText(croptext(bmpmain));
            try {
                Thread.sleep(3000);
//                initialize(Binarize(newbp));

            } catch (InterruptedException e) {
                e.printStackTrace();
            }



            tv2.setText(detectText(Binarize(grayscale(temp_bmp))));
            i=0;
            initialize(rotate(bmpmain));
            btnhome.setVisibility(View.VISIBLE);

        }

    }

//    public void picassa(){
//        ImageView img = (ImageView) findViewById(R.id.ip_image);
//        Picasso.with(getApplicationContext()).load(abclink).into(img);
//        Picasso.with(getApplicationContext()).load(R.drawable.demo)
//                .transform(Bright).into((ImageView) findViewById(R.id.image));
//
//    }
    public String detectText(Bitmap bitmap) {


        TessBaseAPI tessBaseAPI = new TessBaseAPI();

        String path = "/storage/emulated/0/tesseract/tessmain/";

        tessBaseAPI.setDebug(true);
        tessBaseAPI.init(path, "ocrb"); //Init the Tess with the trained data file, with english language

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
