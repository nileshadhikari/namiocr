package com.example.vimvoxlab.ocrmain;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.isseiaoki.simplecropview.CropImageView;
import com.isseiaoki.simplecropview.callback.LoadCallback;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class CamActivity extends AppCompatActivity {

    String abc="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.camera);



        final Camera mCamera = getCameraInstance();

        // Create our Preview view and set it as the content of our activity.
        CameraPreview mPreview = new CameraPreview(this, mCamera);
        FrameLayout preview = (FrameLayout) findViewById(R.id.flayout);
        preview.addView(mPreview);


        FloatingActionButton camclick = (FloatingActionButton) findViewById(R.id.camclick);
        camclick.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // get an image from the camera
                        mCamera.takePicture(null, null, mPicture);
                    }
                }
        );

        FloatingActionButton btnhome = (FloatingActionButton) findViewById(R.id.home);
        assert btnhome != null;
        btnhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeintent = new Intent(getApplicationContext(),MainActivity.class);
                homeintent.putExtra("link",abc);
                finish();
                startActivity(homeintent);


            }

        });


    }

    /** Check if this device has a camera */


    public static Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open(); // attempt to get a Camera instance
        }
        catch (Exception e){
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }


    private Camera.PictureCallback mPicture = new Camera.PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {

            Long abc;
            if (data != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);

                if(bitmap!=null){
                    Toast toast1 = Toast.makeText(getApplicationContext(), "Image has been captured", Toast.LENGTH_SHORT);

                    Toast toast2 = Toast.makeText(getApplicationContext(), "uhaha", Toast.LENGTH_SHORT);
                    toast1.show();
                    File file=new File(Environment.getExternalStorageDirectory()+"/storage/emulated/0/tesseract");
                    if(!file.isDirectory()){
                        file.mkdir();
                    }
                    abc=System.currentTimeMillis();

                    file=new File(Environment.getExternalStorageDirectory()+"/tesseract/testimages/abc.jpg");


                    try
                    {
                        FileOutputStream fileOutputStream=new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                        fileOutputStream.flush();
                        fileOutputStream.close();
                    }
                    catch(IOException e){
                        e.printStackTrace();
                    }
                    catch(Exception exception)
                    {
                        exception.printStackTrace();
                    }

//                    Bitmap bmap = BitmapFactory.decodeFile("/storage/emulated/0/tesseract/testimages"+abc+".jpg");
//                    TextView txtview = (TextView) findViewById(R.id.textView);
//                    txtview.setText(detectText(bmap));
                }
                camera.startPreview();
            }
            camera.startPreview();
            Toast finaltoast = Toast.makeText(getApplicationContext(),"Not availabe in demo version",Toast.LENGTH_SHORT);
            finaltoast.show();
        }
    };









}
