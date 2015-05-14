package com.sparklingspur.sreeragnampoothiri.storeandshowinfo;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by sreerag.nampoothiri on 11-May-15.
 */
public class ListViewOfContacts extends Activity implements ActionMode.Callback {

    protected Object mActionMode;
    public Object personDlt;

    public int selectedItem = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_contacts_list);
        final ListView listView = (ListView) findViewById(R.id.listview);

        DatabaseHandler db = new DatabaseHandler(getApplicationContext());
        List<Contact> contactList = db.getAllContacts();

       /* Button goBackBtn = (Button) findViewById(R.id.goBackBtn);

        goBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });*/

        Context context = getApplicationContext();
       /* ArrayAdapter<Contact> adapter = new ArrayAdapter<Contact>(this,R.layout.contact_view,R.id.showContactInsideListview,contactList);
        listView.setAdapter(adapter);*/

        ListAdapter customAdapter = new ListAdapter(context,contactList);
        listView.setAdapter(customAdapter);

       /* ListIterator<Contact> listIterator = contactList.listIterator();
        while(listIterator.hasNext()){

        }*/

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object clickedItem = listView.getItemAtPosition(position);
                Contact clickedContact = (Contact) clickedItem;
                Toast toast = Toast.makeText(getApplicationContext(), clickedContact.getName().toString() + " is selected", Toast.LENGTH_SHORT);
                toast.show();
                Intent showSelectedContact = new Intent(ListViewOfContacts.this, ContactInfo.class);
                // for this the class should implement serializable
                showSelectedContact.putExtra("SelectedPerson", clickedContact);
                startActivity(showSelectedContact);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (mActionMode != null) {
                    return false;
                }
                selectedItem = position;
                personDlt = listView.getItemAtPosition(position);



                mActionMode = ListViewOfContacts.this.startActionMode(ListViewOfContacts.this);
                view.setSelected(true);
                return true;
            }
        });

    }

    private void show(){
        Toast.makeText(ListViewOfContacts.this, String.valueOf(selectedItem), Toast.LENGTH_LONG).show();
    }

    // Called when the action mode is created; startActionMode() was called
    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        // Inflate a menu resource providing context menu items
        MenuInflater inflater = mode.getMenuInflater();
        // Assumes that you have "contexual.xml" menu resources
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    // Called each time the action mode is shown. Always called after
    // onCreateActionMode, but
    // may be called multiple times if the mode is invalidated.
    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false; // Return false if nothing is done
    }

    // Called when the user selects a contextual menu item
    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                show();
                Contact dltContct = (Contact)personDlt;
                DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                db.deleteContact(dltContct);
                Toast toast = Toast.makeText(getApplicationContext(), "Contact Deleted", Toast.LENGTH_SHORT);
                toast.show();

                // Action picked, so close the CAB
                mode.finish();
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                return true;
            default:
                return false;
        }
    }

    // Called when the user exits the action mode
    @Override
    public void onDestroyActionMode(ActionMode mode) {
        mActionMode = null;
        selectedItem = -1;
    }

}




