package com.example.sharkcatshop.Administrator;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.sharkcatshop.R;

public class Administrator extends AppCompatActivity {

    ImageView uploadImage;
    Button btnSave;
    EditText edTitle, edPrice;
    String imageURL;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrator);
//
//        uploadImage = findViewById(R.id.uploadImage);
//        btnSave = findViewById(R.id.btn_save);
//        edTitle = findViewById(R.id.ed_title);
//        edPrice = findViewById(R.id.ed_price);
//
//        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
//                new ActivityResultContracts.StartActivityForResult(),
//                new ActivityResultCallback<ActivityResult>() {
//                    @Override
//                    public void onActivityResult(ActivityResult result) {
//                        if(result.getResultCode() == Activity.RESULT_OK){
//                            Intent data = result.getData();
//                            uri = data.getData();
//                            uploadImage.setImageURI(uri);
//                        }else{
//                            Toast.makeText(Administrator.this, "NO Image Selected", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                }
//        );
//
//        uploadImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent photoPicker = new Intent(Intent.ACTION_PICK);
//                photoPicker.setType("image/*");
//                activityResultLauncher.launch(photoPicker);
//            }
//        });
//
//        btnSave.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                saveData();
//            }
//        });
    }

//    public void saveData(){
//        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("SharkCatShop Images")
//                .child(uri.getLastPathSegment());
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(Administrator.this);
//        builder.setCancelable(false);
//        builder.setView(R.layout.progress_layout);
//        AlertDialog dialog = builder.create();
//        dialog.show();
//
//        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//
//                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
//                while (!uriTask.isComplete());
//                Uri uriImage = uriTask.getResult();
//                imageURL = uriImage.toString();
//                uploadData();
//                dialog.dismiss();
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                dialog.dismiss();
//            }
//        });
//
//    }

//    public void uploadData(){
//
//        String title = edTitle.getText().toString();
//        String price = edPrice.getText().toString();
//
//        Data data = new Data(title, price, imageURL);
//
//        FirebaseDatabase.getInstance().getReference("SharkCatShop").child(title)
//                .setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if(task.isSuccessful()){
//                            Toast.makeText(Administrator.this, "Saved", Toast.LENGTH_SHORT).show();
//                            finish();
//                        }
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(Administrator.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//
//    }

}