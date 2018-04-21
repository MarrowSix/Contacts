package com.arrow.contacts.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.arrow.contacts.R;
import com.arrow.contacts.activities.ContactActivity;
import com.arrow.contacts.models.Contact;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder>  implements SectionIndexer {
    private List<Contact> mContactList;
    private Context mContext;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
//        ImageView photo;
        CircleImageView photo;
        View contactView;

        public ViewHolder(View view) {
            super(view);
            contactView = view;
            name = (TextView) view.findViewById(R.id.name_textView);
//            photo = (ImageView) view.findViewById(R.id.image_view);
            photo = (CircleImageView) view.findViewById(R.id.image_view);
        }
    }

    public SearchAdapter(List<Contact> contactList) {
        mContactList = contactList;
    }

    @NonNull
    @Override
    public  ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_contact, parent, false);
        final SearchAdapter.ViewHolder holder = new SearchAdapter.ViewHolder(view);

        if (mContext == null) {
            mContext = parent.getContext();
        }

        holder.contactView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Contact person = mContactList.get(position);
                Intent intent = new Intent("com.arrow.contacts.activities.ACTION_START");
                intent.putExtra(ContactActivity.CONTACT, person);
                mContext.startActivity(intent);

            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.ViewHolder holder, int position) {
        Contact person = mContactList.get(position);
        holder.name.setText(person.getName());
        holder.photo.setImageResource(person.getImageID());
    }

    @Override
    public int getItemCount() {
        return mContactList.size();
    }

    @Override
    public Object[] getSections() {
        return new Object[0];
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        for (int i=0; i<mContactList.size(); i++) {
            if (mContactList.get(i).getFirstLetter().charAt(0) == sectionIndex) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int getSectionForPosition(int position) {
        return mContactList.get(position).getFirstLetter().charAt(0);
    }
}
