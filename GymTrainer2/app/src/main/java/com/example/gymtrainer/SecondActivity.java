package com.example.gymtrainer;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.appbar.MaterialToolbar;

public class SecondActivity extends AppCompatActivity {

    int[] newArray;
    MaterialToolbar toolbar;
    private final int CAMERA_REQ_CODE = 100;
    ActivityResultLauncher<Intent> someActivityResultLauncher;
    ActivityResultLauncher<Intent> galleryActivityResultLauncher;
    //    public static final int REQUEST_CODE = 100;
    public static final int PERMISSION_CODE = 101;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        toolbar=findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        //  Camera Register Activity For Result ====================================================
        someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                            Bundle extra = result.getData().getExtras();
                            Bitmap imageBitmap = (Bitmap) extra.get("data");

                        }
                    }
                });

        //  Gallery Register Activity For Result ===================================================
        galleryActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                            // There are no request codes

                            Intent data = result.getData();

                            Uri selectedImage = data.getData();
                            String[] filePathColumn = {MediaStore.Images.Media.DATA};
                            Cursor cursor = getContentResolver().query(selectedImage,
                                    filePathColumn, null, null, null);
                            cursor.moveToFirst();

                            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                            String picturePath = cursor.getString(columnIndex);
                            cursor.close();
//                            binding.imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                        }
                    }
                });

        getSupportActionBar();


        newArray=new int[]{
          R.id.boat_pos,R.id.boat_pos2,R.id.boat_pos3,R.id.boat_pos4,R.id.boat_pos5,R.id.boat_pos6,R.id.boat_pos7,
          R.id.boat_pos8,R.id.boat_pos9,R.id.boat_pos10,R.id.boat_pos11,R.id.boat_pos12,R.id.boat_pos13,R.id.boat_pos14,R.id.boat_pos15,
        };
   }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.excersice_menu,menu);
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.id_privacy) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://iotexpert1.blogspot.com/2020/10/weight-loss-privacy-ploicy-page.html"));
            startActivity(intent);
            return true;
        }
        if (id == R.id.id_term) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://iotexpert1.blogspot.com/2020/10/weight-loss-terms-and-conditions-page.html"));
            startActivity(intent);
            return true;
        }
        if (id == R.id.rate) {
            try{
                startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("market://details?id=\"+getPackageName()")));

            }catch(Exception es){
                startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://play.google.com/store/apps/details?id =" + getPackageName())));
            }

            return true;
        }
        if (id == R.id.More) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/developer?id=Leap+Fitness+Group&hl=en_US&gl=US"));
            startActivity(intent);
            return true;
        }
        if (id == R.id.Share) {
            Intent myIntent = new Intent(Intent.ACTION_SEND);
            myIntent.setType("text/plain");
            String sharebody = "This is the best for gym \n By this app you streach your body. \n This is health conscious app.\n" + "https://play.google.com/store/apps/details?id=com.example.gymtrainer&hl=en";
            String sharehub ="Gym Trainer";
            myIntent.putExtra(Intent.EXTRA_SUBJECT,sharehub);
            myIntent.putExtra(Intent.EXTRA_TEXT,sharebody);
            startActivity(Intent.createChooser(myIntent,"share using"));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    //    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode==RESULT_OK){
//
//            if(requestCode==CAMERA_REQ_CODE){
//                //for camera
//                Bitmap img = (Bitmap)data.getExtras().get("data");
//                imgCamera.setImageBitmap(img);
//            }
//        }
//    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // Gallery Request Permission ==============================================================
        switch (requestCode) {
            case PERMISSION_CODE: {

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickImageFromGallery();
                } else {
                    Toast.makeText(getApplicationContext(), "Permission Denied..", Toast.LENGTH_SHORT).show();
                    settingPermissionOpen();
                }
            }

            // Camera Request Permission ===============================================================
            if (requestCode == 200) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent tackPictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    someActivityResultLauncher.launch(tackPictureIntent);
                    Toast.makeText(this, "Camera permission granted.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "Camera permission denied.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    //  Select The Image From A Gallary ============================================================
    private void pickImageFromGallery() {

        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if (intent.resolveActivity(getPackageManager()) != null) {
            galleryActivityResultLauncher.launch(intent);
        } else {
            Toast.makeText(SecondActivity.this, "Not Support", Toast.LENGTH_SHORT).show();
        }
    }

    // Setting Permission ==========================================================================
    public void settingPermissionOpen() {
        Intent settingIntent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.parse("package:" + getPackageName()));
        startActivity(settingIntent);
    }
    public void ImgBtnClick(View view) {

        for (int i=0; i< newArray.length; i++){

            if (view.getId()==newArray[i]){

                int value=i+1;
                Log.i("FIRST",String.valueOf(value));
                Intent intent=new Intent(SecondActivity.this,ThirdActivity.class);
                intent.putExtra("value",String.valueOf(value));
                startActivity(intent);
            }
        }

          }
}