package io.agora.custom;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import io.agora.openlive.R;

/**
 * Created by sonnguyen on 5/14/17.
 */

public class ListRoomAdapter extends ArrayAdapter<Room> {
    ArrayList<Room> roomArrayList;
    Context context;
    TextView txtTopic,txtTeacherName,txtLevel;
    public ListRoomAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<Room> objects) {
        super(context, resource, objects);
        this.roomArrayList = objects;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_card_room,parent,false);
        Room room = roomArrayList.get(position);
        txtLevel = (TextView)v.findViewById(R.id.item_txt_level);
        txtTopic = (TextView)v.findViewById(R.id.item_txt_topic);
        txtTeacherName = (TextView)v.findViewById(R.id.item_txt_teachername);
        txtLevel.setText("Level : "+room.getLevel());
        txtTopic.setText("Topic : "+ room.getTopic());
        txtTeacherName.setText("Teacher Name : " + room.getTeacherName());
        return v;
    }
}
