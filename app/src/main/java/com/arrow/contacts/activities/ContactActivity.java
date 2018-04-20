package com.arrow.contacts.activities;

import android.content.Intent;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.arrow.contacts.R;
import com.arrow.contacts.models.Contact;
import com.bumptech.glide.Glide;

public class ContactActivity extends AppCompatActivity {

    public static final String CONTACT = "contact";

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

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        ImageView contactImageView = (ImageView) findViewById(R.id.contact_photo_image_view);
        TextView contactNumberTextView = (TextView) findViewById(R.id.contact_number);

        collapsingToolbarLayout.setTitle(person.getName());
        // Glide.with(this).load(R.drawable.ic_contact_default).into(contactImageView);
        contactImageView.setImageResource(person.getImageID());
//        Drawable drawable = getDrawable(contactPhotoID);
//        Matrix matrix = new Matrix();

        contactNumberTextView.setText(person.getPhoneNumber().get(0));

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
