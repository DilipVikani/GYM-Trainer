package com.example.gymtrainer;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class YOGA extends AppCompatActivity {

    private FirebaseStorage storage;
    private StorageReference storageReference;
   LinearLayout bikramyoga;
   LinearLayout hathayoga;
   LinearLayout vinyasayoga;
   LinearLayout kundaliniyoga;
   LinearLayout anusarayoga;
    Toolbar toolbar;
    private Uri mImageUri = null;
    private final int CAMERA_REQ_CODE = 100;
    ActivityResultLauncher<Intent> someActivityResultLauncher;
    ActivityResultLauncher<Intent> galleryActivityResultLauncher;
    //    public static final int REQUEST_CODE = 100;
    public static final int PERMISSION_CODE = 101;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yoga);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();



        //  Camera Register Activity For Result ====================================================
        someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                            Intent intent = result.getData();
                            mImageUri = intent.getData();

                            Bundle extra = result.getData().getExtras();
                            Bitmap imageBitmap = (Bitmap) extra.get("data");

                            String image = imageBitmap.toString();
//                            final ProgressDialog p = new ProgressDialog(EXERCISE.this);
//                            p.setTitle("Uploading...");
//                            p.show();
//                            if (mImageUri != null){
//                                StorageReference reference = storageReference.child("camera/" + UUID.randomUUID().toString());
//                                reference.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                                    @Override
//                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                                        p.dismiss();
//                                        Toast.makeText(EXERCISE.this, "saved", Toast.LENGTH_SHORT).show();
//                                    }
//                                }).addOnFailureListener(new OnFailureListener() {
//                                    @Override
//                                    public void onFailure(@NonNull Exception e) {
//                                        p.dismiss();
//                                        Toast.makeText(EXERCISE.this, "ERROR"+e.getMessage(), Toast.LENGTH_LONG).show();
//                                        Log.d("tag", e.getLocalizedMessage());
//                                    }
//                                });
//                            }

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
                            final ProgressDialog p = new ProgressDialog(YOGA.this);
                            p.setTitle("Uploading...");
                            p.show();

                            if (selectedImage != null){
                                StorageReference reference = storageReference.child("pictures/" + UUID.randomUUID().toString());
                                reference.putFile(selectedImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        p.dismiss();
                                        Toast.makeText(YOGA.this, "saved", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        p.dismiss();
                                        Toast.makeText(YOGA.this, "ERROR"+e.getMessage(), Toast.LENGTH_LONG).show();
                                        Log.d("tag", e.getLocalizedMessage());
                                    }
                                });
                            }
//                            binding.imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                        }
                    }
                });


        getSupportActionBar();

        bikramyoga = findViewById(R.id.bikramyoga);
        bikramyoga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Let's do Bikramyoga.",Toast.LENGTH_LONG).show();
                Intent intent=new Intent(YOGA.this, bikramyoga.class);
                startActivity(intent);
            }
        });
        hathayoga = findViewById(R.id.hathayoga);
        hathayoga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Let's do Hathayoga.",Toast.LENGTH_LONG).show();
                Intent intent=new Intent(YOGA.this, hathayoga.class);
                startActivity(intent);
            }
        });

        vinyasayoga = findViewById(R.id.vinyasayoga);
        vinyasayoga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Let's do Vinyasayoga.",Toast.LENGTH_LONG).show();
                Intent intent=new Intent(YOGA.this, vinyasayoga.class);
                startActivity(intent);
            }
        });
        kundaliniyoga = findViewById(R.id.kundaliniyoga);
        kundaliniyoga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Let's do Kundaliniyoga.",Toast.LENGTH_LONG).show();
                Intent intent=new Intent(YOGA.this, kundaliniyoga.class);
                startActivity(intent);
            }
        });
        anusarayoga = findViewById(R.id.anusarayoga);
        anusarayoga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Let's do Anusarayoga.",Toast.LENGTH_LONG).show();
                Intent intent=new Intent(YOGA.this,anusarayoga.class);
                startActivity(intent);
            }
        });

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
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=\"+getPackageName()")));

            } catch (Exception es) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id =" + getPackageName())));
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
            String sharehub = "Gym Trainer";
            myIntent.putExtra(Intent.EXTRA_SUBJECT, sharehub);
            myIntent.putExtra(Intent.EXTRA_TEXT, sharebody);
            startActivity(Intent.createChooser(myIntent, "share using"));
            return true;
        }
        switch (item.getItemId()){
            case R.id.camera:
                if (ContextCompat.checkSelfPermission(YOGA.this, Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(YOGA.this, new String[]{Manifest.permission.CAMERA}
                            , 200);
                } else {
                    Intent tackPictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    someActivityResultLauncher.launch(tackPictureIntent);
                }
                break;
            case R.id.gallery:
                pickImageFromGallery();
                break;
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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                String[] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
                requestPermissions(permission, PERMISSION_CODE);
            } else {
                pickImageFromGallery();
            }
        } else {
            pickImageFromGallery();
        }
    }

    //  Select The Image From A Gellary ============================================================
    private void pickImageFromGallery() {

        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if (intent.resolveActivity(getPackageManager()) != null) {
            galleryActivityResultLauncher.launch(intent);
        } else {
            Toast.makeText(YOGA.this, "Not Support", Toast.LENGTH_SHORT).show();
        }
    }

    // Setting Permission ==========================================================================
    public void settingPermissionOpen() {
        Intent settingIntent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.parse("package:" + getPackageName()));
        startActivity(settingIntent);
    }
}



