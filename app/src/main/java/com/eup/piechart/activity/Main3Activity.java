package com.eup.piechart.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.eup.piechart.R;
import com.eup.piechart.adapter.ContactsAdapter;
import com.eup.piechart.model.Contact;

import java.util.ArrayList;

public class Main3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        ArrayList<Contact> contacts = new ArrayList<>();
        for (int i = 1; i < 20; i++) {
            Contact contact = new Contact();
            contact.setName("Nam");
            contact.setNumber("Number: " + i);
            if (i == 5)
                contact.setBlocked(true);
            contacts.add(contact);
        }

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_contacts);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setEnabled(false);
        recyclerView.setClickable(false);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        final ContactsAdapter contactsAdapter = new ContactsAdapter(this , contacts);
        recyclerView.setAdapter(contactsAdapter);

        Button button = (Button) findViewById(R.id.button_get);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = "";
                ArrayList<Contact> contacts = contactsAdapter.getContactList();
                for (Contact contact : contacts) {
                    if (contact.blocked == true) {
                        message += "\n" + contact.name;
                    }
                }

                Toast.makeText(Main3Activity.this, "" + message + "\n are blocked.", Toast.LENGTH_SHORT).show();
            }
        });
    }
    
}
