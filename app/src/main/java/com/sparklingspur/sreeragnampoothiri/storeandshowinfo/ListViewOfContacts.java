package com.sparklingspur.sreeragnampoothiri.storeandshowinfo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.ArrayRes;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;

/**
 * Created by sreerag.nampoothiri on 11-May-15.
 */
public class ListViewOfContacts extends Activity implements ActionMode.Callback {

    protected Object mActionMode;
    public Object personDlt;
    List<Contact> dltPersonsList = new ArrayList<>();

    ListAdapter listAdapter;
    public int selectedItem = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_contacts_listview);
        final ListView listView = (ListView) findViewById(R.id.listview);

        DatabaseHandler db = new DatabaseHandler(getApplicationContext());
        List<Contact> contactList = db.getAllContacts();
        listAdapter = new ListAdapter(this, R.layout.contact_view_item,contactList);
        listView.setAdapter(listAdapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);

        listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                // Capture total checked items
                final int checkedCount = listView.getCheckedItemCount();

                // Set the CAB title according to total checked items
                mode.setTitle(checkedCount + " Selected");
                personDlt = listView.getItemAtPosition(position);
                if(checked) {

                    Contact prsntoArray = (Contact) personDlt;
                    dltPersonsList.add(prsntoArray);
                }
                else {
                    dltPersonsList.remove(personDlt);
                }
                // Calls toggleSelection method from ListViewAdapter Class
                listAdapter.toggleSelection(position);
            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                mode.getMenuInflater().inflate(R.menu.menu_main, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch (item.getItemId()){
                    case R.id.delete:
                        SparseBooleanArray selected = listAdapter.getSelectedIds();
                        for (int i = (selected.size() - 1); i >= 0; i--) {
                            if (selected.valueAt(i)) {
                                Contact selecteditem = listAdapter
                                        .getItem(selected.keyAt(i));
                                // Remove selected items following the ids
                                listAdapter.remove(selecteditem);
                            }
                        }
                        Iterator<Contact> dltIter = dltPersonsList.iterator();
                        do{

                            Contact dltContct = dltIter.next();
                            DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                            db.deleteContact(dltContct);

                        }while(dltIter.hasNext());


                        // Close CAB
                        mode.finish();
                        return true;
                    default:
                        return false;
                }

            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                listAdapter.removeSelection();

            }
        });


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
                Contact addPersonDlt = (Contact)personDlt;
                dltPersonsList.add(addPersonDlt);

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
        /*MenuInflater inflater = mode.getMenuInflater();
        // Assumes that you have "contexual.xml" menu resources
        inflater.inflate(R.menu.menu_main, menu);*/
        return false;
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
        /*    case R.id.action_delete:
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
                return true;*/
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




