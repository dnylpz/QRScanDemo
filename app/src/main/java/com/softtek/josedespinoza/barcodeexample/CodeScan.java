package com.softtek.josedespinoza.barcodeexample;


import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

public class CodeScan extends AppCompatActivity  {

    private Button fixedCodeBtn, camScanBtn;
    private TextView txtView;
    private CameraManager camMan;
    private CameraDevice camDev;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_scan);
        //INITIAL CODE ^

        //GET UI HANDLERS
        fixedCodeBtn = (Button) findViewById(R.id.button);
        txtView = (TextView) findViewById(R.id.txtContent);
        camScanBtn = (Button) findViewById(R.id.camScan);

        //EVENT HANDRLERS
        fixedCodeBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                ImageView myIV = (ImageView) findViewById(R.id.imgview);
                Bitmap myBM = BitmapFactory.decodeResource(
                        getApplicationContext().getResources(), R.drawable.puppy);
                if (myBM != null) myIV.setImageBitmap(myBM);
                BarcodeDetector detector = new BarcodeDetector.Builder(getApplicationContext())
                        .setBarcodeFormats(Barcode.DATA_MATRIX | Barcode.QR_CODE)
                        .build();
                if(!detector.isOperational()){
                    txtView.setText(R.string.detectorError);
                    return;
                }
                Frame frame = new Frame.Builder().setBitmap(myBM).build();
                SparseArray<Barcode> barcodes = detector.detect(frame);

                Barcode thisCode = barcodes.valueAt(0);
                txtView.setText(thisCode.rawValue);
            }
        });

        //SECOND HANDLER
        camScanBtn.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                camMan = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
                try {
                    camMan.getCameraIdList();
                } catch (CameraAccessException e) {
                    e.printStackTrace();
                }
            }
        });

    }

}
