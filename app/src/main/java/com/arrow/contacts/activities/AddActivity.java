package com.arrow.contacts.activities;

import android.content.ContentProviderOperation;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.arrow.contacts.R;
import com.arrow.contacts.models.Temp;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddActivity extends AppCompatActivity {

    public static final String EDIT = "edit";

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
    private MaterialEditText lastName;
    private MaterialEditText firstName;
    private MaterialEditText domain;
    private MaterialEditText number;
    private MaterialEditText email;
    private MaterialSpinner nTypeSpinner;
    private MaterialSpinner eTypeSpinner;

    private Temp temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.BLACK);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_cancel);

        lastName = (MaterialEditText) findViewById(R.id.person_last_name);
        firstName = (MaterialEditText) findViewById(R.id.person_first_name);
        domain = (MaterialEditText) findViewById(R.id.person_company_name);
        number = (MaterialEditText) findViewById(R.id.person_number);
        email = (MaterialEditText) findViewById(R.id.person_email);

        nTypeSpinner = (MaterialSpinner) findViewById(R.id.person_number_type);
        nTypeSpinner.setItems(numberType);
        eTypeSpinner = (MaterialSpinner) findViewById(R.id.person_email_type);
        eTypeSpinner.setItems(emailType);

        boolean showType = getIntent().getBooleanExtra("show_type", true);
        if (showType == true) {
            nTypeSpinner.setSelectedIndex(0);
            eTypeSpinner.setSelectedIndex(0);
        } else {
            getSupportActionBar().setTitle("修改联系人");
            temp = (Temp) getIntent().getSerializableExtra(EDIT);
            nTypeSpinner.setSelectedIndex(temp.getNumType());
            eTypeSpinner.setSelectedIndex(temp.getEmaType());
            lastName.setText(temp.getName());
            lastName.setHint("姓名");
            firstName.setVisibility(View.GONE);
            number.setText(temp.getpNumber());
            email.setText(temp.getpEmail());
        }


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
                getUIData();
                saveContact();
                finish();
                return true;
                default:
        }

        return super.onOptionsItemSelected(item);
    }

    private void getUIData() {
        temp = new Temp(
                lastName.getText().toString()+firstName.getText().toString(),
                domain.getText().toString(),
                number.getText().toString(),
                nTypeSpinner.getSelectedIndex(),
                email.getText().toString(),
                eTypeSpinner.getSelectedIndex()
        );
    }

    private void saveContact() {
        ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
        ops.add(
                ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)  // 这里先传入空值，用于添加一个raw_contact空项
                .build()
        );
        ops.add(
                ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, temp.getName())  // 插入姓名
                .build()
        );
        ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, temp.getpNumber())  //插入号码
                .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, temp.getSNumType())    //插入号码类型
                .build()
        );
        ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Email.ADDRESS, temp.getpEmail())  //插入电子邮件
                .withValue(ContactsContract.CommonDataKinds.Email.TYPE, temp.getSEmaType())    //插入电子邮件类型
                .build()
        );

        try {
            getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
