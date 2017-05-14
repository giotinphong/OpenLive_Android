package io.agora.custom.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import io.agora.custom.Room;
import io.agora.openlive.R;
import io.agora.openlive.model.ConstantApp;
import io.agora.openlive.ui.LiveRoomActivity;
import io.agora.openlive.ui.MainActivity;
import io.agora.rtc.Constants;

public class AddNewRoom extends AppCompatActivity {
    private EditText edTopic,edLevel;
    private Button btnStart;
    private DatabaseReference mRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_room);

        mRef = FirebaseDatabase.getInstance().getReference();
        edTopic = (EditText)findViewById(R.id.acti_addnewroom_ed_topicname);
        edLevel = (EditText)findViewById(R.id.acti_addnewroom_ed_level);
        btnStart = (Button)findViewById(R.id.acti_addnewroom_btn_start);
        Room room = new Room();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String UID = mAuth.getCurrentUser().getUid();
        room.setRoomID(UID);
         mRef.child("user").child(UID).child("name").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String te = dataSnapshot.toString();
                room.setTeacherName(te);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String topic = edTopic.getText().toString().trim();
                int Level = Integer.parseInt(edLevel.getText().toString().trim());
                room.setTopic(topic);
                room.setLevel(Level);
                room.setBroadcaster(UID);
                mRef.child("room").child(UID).setValue(room);
                forwardToLiveRoom(room.getBroadcaster());
            }
        });
    }

    public void forwardToLiveRoom(String broad) {
        Intent i = new Intent(AddNewRoom.this, LiveRoomActivity.class);
        i.putExtra(ConstantApp.ACTION_KEY_CROLE, Constants.CLIENT_ROLE_BROADCASTER);
        i.putExtra(ConstantApp.ACTION_KEY_ROOM_NAME, broad);

        startActivity(i);
    }
}
