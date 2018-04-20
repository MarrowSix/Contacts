package com.arrow.contacts.activities;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.arrow.contacts.R;
import com.arrow.contacts.adapters.DetailAdapter;
import com.arrow.contacts.models.Contact;
import com.arrow.contacts.models.Detail;

import java.util.ArrayList;
import java.util.List;

public class ContactActivity extends AppCompatActivity {

    public static final String CONTACT = "contact";
    private List<Detail> mdetailList= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        Toolbar toolbar = (Toolbar) findViewById(R.id.contact_toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.edit_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
        Contact person = (Contact) intent.getSerializableExtra(CONTACT);

        for (int i=0; i<person.getPhoneNumber().size(); i++) {
            Detail temp = new Detail(
                    person.getPhoneNumber().get(i),
                    person.getPhoneType().get(i),
                    "0",
                    R.drawable.ic_number,
                    R.drawable.ic_message
            );
            mdetailList.add(temp);
        }

        for (int i=0; i<person.getEmails().size(); i++) {
            Detail temp = new Detail(
                    person.getEmails().get(i),
                    person.getEmailsType().get(i),
                    "@",
                    R.drawable.ic_email,
                    R.drawable.ic_message
            );
            mdetailList.add(temp);
        }

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        ImageView contactImageView = (ImageView) findViewById(R.id.contact_photo_image_view);

        collapsingToolbarLayout.setTitle(person.getName());
        contactImageView.setImageResource(person.getImageID());

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.contact_detail_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        DetailAdapter detailAdapter = new DetailAdapter(mdetailList);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(detailAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.view_contact, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
                default:
        }
        return super.onOptionsItemSelected(item);
    }
}
