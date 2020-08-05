package com.eup.piechart.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.eup.piechart.R;
import com.eup.piechart.model.Contact;

import java.util.ArrayList;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder> {
    Context context;

    ArrayList<Contact> contacts;

    public ContactsAdapter(Context context, ArrayList<Contact> contacts) {
        this.context = context;
        this.contacts = contacts;
    }

    @Override
    public ContactsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false);
        return new ContactsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ContactsViewHolder holder, final int position) {
        holder.textName.setText(contacts.get(position).name);
        holder.textNumber.setText(contacts.get(position).number);
        if (contacts.get(position).isBlocked()) {
            holder.buttonBlock.setText("Enable");
        } else {
            holder.buttonBlock.setText("Block");
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "" + position, Toast.LENGTH_SHORT).show();
                holder.itemView.setBackgroundColor(0x00FF00);
            }
        });

        holder.buttonBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("test", "" + position + " : " + contacts.get(position).isBlocked());
                if (contacts.get(position).isBlocked()) {
                    contacts.get(position).setBlocked(false);
                    holder.buttonBlock.setText("block");
                } else {
                    contacts.get(position).setBlocked(true);
                    holder.buttonBlock.setText("enable");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    class ContactsViewHolder extends RecyclerView.ViewHolder {
        TextView textName, textNumber;
        Button buttonBlock;

        public ContactsViewHolder(View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.text_name);
            textNumber = itemView.findViewById(R.id.text_number);
            buttonBlock = itemView.findViewById(R.id.button_block);
        }
    }

    public ArrayList<Contact> getContactList() {
        return contacts;
    }
}
