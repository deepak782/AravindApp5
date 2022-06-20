package com.example.aravindapp5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView contactList;
    ContentResolver contentResolver;
    Cursor cursor;
    ArrayList<String> arrayList=new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contactList=findViewById(R.id.ContactsList);

        findViewById(R.id.btn_contacts).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                permissionMethod();
            }
        });

        contactList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Toast.makeText(MainActivity.this, ""+arrayList.get(i), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void permissionMethod() {

        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_CONTACTS)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.READ_CONTACTS},101);
        }
        else
        {
            readAllContacts();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case 101:
                if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED)
                {
                    readAllContacts();
                }
                else
                {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void readAllContacts() {

        arrayList.clear();

        contentResolver=getContentResolver();

        Uri uri= ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String[] projections={ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER};
        String selection=null;//row wise
        String[] args=null;
        String sortOrder=""+ ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME+" ASC";

        cursor=contentResolver.query(uri,projections,selection,args,sortOrder);

        if(cursor.getCount()>0&&cursor!=null)
        {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                @SuppressLint("Range") String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                arrayList.add("" + name + "\n" + number);
                arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayList);
                contactList.setAdapter(arrayAdapter);
            }
        }
        else
        {
            Toast.makeText(this, "No Contacts Found", Toast.LENGTH_SHORT).show();
        }

    }
}