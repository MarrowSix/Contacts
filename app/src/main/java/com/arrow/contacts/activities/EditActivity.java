package com.arrow.contacts.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.arrow.contacts.R;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EditActivity extends AppCompatActivity {

    private  static final String[] nType = {
            "住宅",
            "手机",
            "单位",
            "单位传真",
            "住宅传真",
            "其他"
    };

    private static final String[] eType = {
            "住宅",
            "工作",
            "其他",
            "个人"
    };

    private static List<String> numberType = Arrays.asList(nType);
    private static List<String> emailType = Arrays.asList(eType);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.BLACK);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_cancel);

        MaterialSpinner nTypeSpinner = (MaterialSpinner) findViewById(R.id.person_number_type);
        nTypeSpinner.setItems(numberType);
        nTypeSpinner.setSelectedIndex(0);
        nTypeSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {

            }
        });

        MaterialSpinner eTypeSpinner = (MaterialSpinner) findViewById(R.id.person_email_type);
        eTypeSpinner.setItems(emailType);
        eTypeSpinner.setSelectedIndex(0);
        nTypeSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_contact, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.ic_action_save:
                saveContact();
                finish();
                return true;
                default:
        }

        return super.onOptionsItemSelected(item);
    }

    private void saveContact() {

    }
}
