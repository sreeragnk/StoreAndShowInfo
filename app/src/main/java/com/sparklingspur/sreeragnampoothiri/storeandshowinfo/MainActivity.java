package com.sparklingspur.sreeragnampoothiri.storeandshowinfo;

import android.content.Context;
import android.content.Intent;
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
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

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
                if(personAlreadyExists){
                    Toast toast = Toast.makeText(context,"Person Already Exist", Toast.LENGTH_SHORT);
                    toast.show();
                }

                else {

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


}
