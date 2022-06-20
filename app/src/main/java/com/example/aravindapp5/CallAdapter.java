package com.example.aravindapp5;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class CallAdapter extends BaseAdapter {
    Context context;
    List<CallModel> callModelList=new ArrayList<>();
    LayoutInflater layoutInflater;

    public CallAdapter(Context context, List<CallModel> callModelList) {
        this.context = context;
        this.callModelList = callModelList;
        layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return callModelList.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View root=layoutInflater.inflate(R.layout.custom_call,null);
        TextView name=root.findViewById(R.id.c_name);
        TextView number=root.findViewById(R.id.c_number);
        TextView date=root.findViewById(R.id.c_timeStamp);
        TextView duration=root.findViewById(R.id.c_duration);
        TextView type=root.findViewById(R.id.c_type);


        name.setText(""+callModelList.get(i).getName());
        number.setText(""+callModelList.get(i).getNumber());
        date.setText(""+callModelList.get(i).getDate());
        duration.setText(""+callModelList.get(i).getDuration()+" Sec");
       // type.setText(""+callModelList.get(i).getType());
        /*
        INCOMING_TYPE
OUTGOING_TYPE
MISSED_TYPE
VOICEMAIL_TYPE
REJECTED_TYPE
BLOCKED_TYPE
ANSWERED_EXTERNALLY_TYPE
         */

        if(callModelList.get(i).getType().equals("1"))
        {
            type.setText("IN CALL");
        }
        else  if(callModelList.get(i).getType().equals("2"))
        {
            type.setText("OUT GOING CALL");
        }
        else  if(callModelList.get(i).getType().equals("3"))
        {
            type.setText("MISSED CALL");
        }
        else  if(callModelList.get(i).getType().equals("4"))
        {
            type.setText("VOICE CALL");
        }
        else  if(callModelList.get(i).getType().equals("5"))
        {
            type.setText("REJECTED");
        }
        else  if(callModelList.get(i).getType().equals("6"))
        {
            type.setText("BLOCKED");
        }
        else  if(callModelList.get(i).getType().equals("7"))
        {
            type.setText("ANSWERED EXTERNALLY TYPE");
        }
        else
        {
            type.setText("");
        }

        return root;
    }
}
