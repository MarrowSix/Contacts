package com.arrow.contacts.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.arrow.contacts.R;
import com.arrow.contacts.adapters.ContactAdapter;
import com.arrow.contacts.adapters.SearchAdapter;
import com.arrow.contacts.helpers.ChineseToPinYinHelper;
import com.arrow.contacts.models.Contact;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private MaterialSearchView searchView;
    private RecyclerView recyclerView;
    private ContactAdapter contactAdapter;
    private FloatingActionButton fab;

    private List<Contact> contactList = new ArrayList<>();
    // 默认头像
    private int[] imageIDArray = {

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initFragment();

        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[] { Manifest.permission.READ_CONTACTS, Manifest.permission.READ_CALL_LOG }, 1);
        } else {
            readContacts();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (searchView.isSearchOpen()){
            searchView.closeSearch();
            ContactAdapter contactAdapter = new ContactAdapter(contactList);
            recyclerView.setAdapter(contactAdapter);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        searchView.setHint("Search Contacts");
        searchView.setHintTextColor(Color.GRAY);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    readContacts();
                } else {
                    Toast.makeText(this, "You denied the permisssion", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    private void initFragment() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                Intent intent = new Intent("com.arrow.contacts.activities.EDIT_START");
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        searchView = (MaterialSearchView) findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchContacts(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!TextUtils.isEmpty(newText)) {
                    searchContacts(newText);
                }
                return true;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                fab.hide();
            }

            @Override
            public void onSearchViewClosed() {
                ContactAdapter contactAdapter = new ContactAdapter(contactList);
                recyclerView.setAdapter(contactAdapter);
                fab.show();
            }
        });
    }

    // 读取联系人信息
    private void readContacts() {
        // Cursor cursor = null;   // 定义指向查询结果的Cursor对象
            // 查询联系人信息
        Cursor cursor = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null );

        if (cursor != null) {
                // 若查询结果非空则逐个读取联系人姓名和电话号码
            while (cursor.moveToNext()) {
                Long id = cursor.
                        getLong(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone._ID));
                String contactName = cursor.
                        getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
//                String contactNumbe = cursor.
//                        getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//                List<String> contactNumber = new ArrayList<>();
//                List<Integer> numberType = new ArrayList<>();
////                contactNumber.add(contactNumbe);
//                // 根据联系人的ID获取此人的电话号码
//                String[] phoneProjection = new String[] {
//                        ContactsContract.CommonDataKinds.Phone.NUMBER,
//                        ContactsContract.CommonDataKinds.Phone.TYPE
//                };
//                Cursor phoneCursor = this.getContentResolver().query(
//                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
//                        phoneProjection,
//                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + id,
//                        null,
//                        null
//                );
//                // 因为同一联系人可能有多个电话号码，需要遍历
//                if (phoneCursor.moveToFirst()) {
//                    do {
//                        contactNumber.add(phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
//                        numberType.add(phoneCursor.getInt(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE)));
//                    } while (phoneCursor.moveToNext());
//                }
//                phoneCursor.close();
//
//                List<String> emails = new ArrayList<>();
//                List<Integer> emailsType = new ArrayList<>();
//                String[] emailProjection = new String[] {
//                        ContactsContract.CommonDataKinds.Email.ADDRESS,
//                        ContactsContract.CommonDataKinds.Email.TYPE
//                };
//                Cursor emailCursor = this.getContentResolver().query(
//                        ContactsContract.CommonDataKinds.Email.CONTENT_URI,
//                        emailProjection,
//                        ContactsContract.CommonDataKinds.Email.CONTACT_ID + "=" + id,
//                        null,
//                        null
//                );
//                if (emailCursor.moveToFirst()) {
//                    do {
//                        emails.add(emailCursor.getString(emailCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS)));
//                        emailsType.add(emailCursor.getInt(emailCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.TYPE)));
//                    } while (emailCursor.moveToNext());
//                }
//                emailCursor.close();
                // 根据联系人的名字来决定其默认图标
                int tempIndex = 0;
                for (int i=0; i<contactName.length(); i++) {
                    tempIndex += contactName.charAt(i);
                }
                // 将中文姓名转换成对应的拼音字母
                String convert = ChineseToPinYinHelper.getInstance().getPinyin(contactName).toUpperCase();

                // 根据联系人姓名首字母是否满足正则表达式"[A-Z]"来确定其属于哪一组
                String subString = convert.substring(0, 1);
                if (!subString.matches("[A-Z]")) {
                    subString = "#";
                }
                Contact person = new Contact(contactName, convert, subString, R.mipmap.timg, id);
                contactList.add(person);
            }
            cursor.close();
        }

        Collections.sort(contactList, new Comparator<Contact>() {
            @Override
            public int compare(Contact o1, Contact o2) {
                if (o1.getFirstLetter().contains("#")) {
                    return 1;
                } else if (o2.getFirstLetter().contains("#")) {
                    return -1;
                } else {
                    return o1.getFirstLetter().compareTo(o2.getFirstLetter());
                }

            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        contactAdapter = new ContactAdapter(contactList);
        recyclerView.setAdapter(contactAdapter);
    }

    private void searchContacts(String query) {
        String pinYin = ChineseToPinYinHelper.getInstance().getPinyin(query).toUpperCase();
        List<Contact> tempList = new ArrayList<>();

        for (Contact person : contactList) {
            if (person.getPinYin().contains(pinYin)) {
                tempList.add(person);
            }
        }

        SearchAdapter searchAdapter = new SearchAdapter(tempList);
        recyclerView.setAdapter(searchAdapter);
    }
}
