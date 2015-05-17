package com.sparklingspur.sreeragnampoothiri.storeandshowinfo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;


public class MainActivity extends Activity {

    final int REQUEST_CAMERA = 1888;
    final int SELECT_FILE = 1213;
    private Bitmap contactImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        final EditText name = (EditText) findViewById(R.id.storeName);
        final EditText phoneNumber = (EditText) findViewById(R.id.storephoneNumber);
        final Button addBtn = (Button) findViewById(R.id.storeContact);
        Button showBtn = (Button) findViewById(R.id.showContact);
        final DatabaseHandler db = new DatabaseHandler(this);
        final Context context = getApplicationContext();

        final Button addPhoto = (Button)findViewById(R.id.btnSelectPhoto);
        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                addBtn.setEnabled(!name.getText().toString().trim().isEmpty());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //db.deleteAllContacts();
                boolean personAlreadyExists = false;
                //Check for already exist contact
                Contact contact = new Contact();
                contact.setName(name.getText().toString());
                contact.setPhoneNumber(phoneNumber.getText().toString());

                personAlreadyExists = db.checkContact(contact);
                if (personAlreadyExists) {
                    Toast toast = Toast.makeText(context, "Person Already Exist", Toast.LENGTH_SHORT);
                    toast.show();
                } else {

                    //Add to Database
                    db.addContact(new Contact(name.getText().toString(), phoneNumber.getText().toString()));
                    Toast toast = Toast.makeText(context, "Added", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        showBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Button Clicked");
                Intent i = new Intent(context, ListViewOfContacts.class);
                startActivity(i);
            }
        });

        addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return true;
    }

    private void selectImage(){



        final CharSequence[] items = {"Take Photo", "Choose From Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        builder.setTitle("Add Photo");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (items[which].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);
                } else if (items[which].equals("Choose From Gallery")) {
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
                } else if (items[which].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        ImageView imageView = (ImageView) findViewById(R.id.ivImage);
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode == REQUEST_CAMERA){
                contactImage = (Bitmap)data.getExtras().get("data");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                contactImage.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

                File destination = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");
                FileOutputStream fo;

                try{
                    destination.createNewFile();
                    fo = new FileOutputStream(destination);

                }catch (FileNotFoundException e)
                {
                    e.printStackTrace();
                }catch (IOException e){
                    e.printStackTrace();
                }
                imageView.setImageBitmap(contactImage);
            }
        else if(requestCode == SELECT_FILE){
                Uri selectedImageUri = data.getData();
                String[] projection = {MediaStore.MediaColumns.DATA};
                Cursor cursor = managedQuery(selectedImageUri, projection, null, null,
                        null);
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
                cursor.moveToFirst();

                String selectedImagePath = cursor.getString(column_index);


                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(selectedImagePath, options);
                final int REQUIRED_SIZE = 200;
                int scale = 1;
                while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                        && options.outHeight / scale / 2 >= REQUIRED_SIZE)
                    scale *= 2;
                options.inSampleSize = scale;
                options.inJustDecodeBounds = false;
                contactImage = BitmapFactory.decodeFile(selectedImagePath, options);

                imageView.setImageBitmap(contactImage);
            }

        }
    }

}
