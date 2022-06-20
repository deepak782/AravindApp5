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
import android.provider.CallLog;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {

    ListView callLogList;

    CallModel callModel;
    CallAdapter callAdapter;
    List<CallModel> callModelList=new ArrayList<>();
    ContentResolver contentResolver;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        callLogList=findViewById(R.id.calllog_list);

        findViewById(R.id.btn_calllog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                permissionMethod();
            }
        });
    }

    private void permissionMethod() {

        if(ContextCompat.checkSelfPermission(MainActivity2.this, Manifest.permission.READ_CALL_LOG)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(MainActivity2.this,new String[]{Manifest.permission.READ_CALL_LOG},201);
        }
        else
        {
            readAllCallLog();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode)
        {
            case 201:
                if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED)
                {
                    readAllCallLog();
                }
                else
                {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void readAllCallLog() {

        callModelList.clear();
        contentResolver=getContentResolver();

        Uri uri= CallLog.Calls.CONTENT_URI;//Call log Path
        String[] projections={CallLog.Calls.CACHED_NAME, CallLog.Calls.NUMBER, CallLog.Calls.DATE, CallLog.Calls.DURATION, CallLog.Calls.TYPE};
        String selection=null;
        String[] args=null;
        String sortOrder=""+ CallLog.Calls.DATE+" DESC";

        cursor= contentResolver.query(uri,projections,selection,args,sortOrder);

        if(cursor.getCount()>0&cursor!=null)
        {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(CallLog.Calls.CACHED_NAME));
                @SuppressLint("Range") String number = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
                @SuppressLint("Range") String date = cursor.getString(cursor.getColumnIndex(CallLog.Calls.DATE));
                @SuppressLint("Range") String duration = cursor.getString(cursor.getColumnIndex(CallLog.Calls.DURATION));
                @SuppressLint("Range") String type = cursor.getString(cursor.getColumnIndex(CallLog.Calls.TYPE));

                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy == HH:mm");
                String dateString=simpleDateFormat.format(new Date(Long.parseLong(date)));


                callModel = new CallModel(name, number, dateString, duration, type);
                callModelList.add(callModel);
                callAdapter = new CallAdapter(this, callModelList);
                callLogList.setAdapter(callAdapter);
            }

        }
        else
        {
            Toast.makeText(this, "No CallLog Found", Toast.LENGTH_SHORT).show();
        }
    }
}