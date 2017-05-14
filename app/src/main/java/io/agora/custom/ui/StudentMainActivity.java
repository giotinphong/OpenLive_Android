package io.agora.custom.ui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import io.agora.custom.ListRoomAdapter;
import io.agora.custom.Room;
import io.agora.openlive.R;
import io.agora.openlive.model.ConstantApp;
import io.agora.openlive.ui.BaseActivity;
import io.agora.openlive.ui.LiveRoomActivity;
import io.agora.openlive.ui.MainActivity;
import io.agora.rtc.Constants;

public class StudentMainActivity extends BaseActivity {
    ArrayList<Room>roomArrayList;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_main);
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    //Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                   // Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
        ListView listView  = (ListView)findViewById(R.id.acti_student_main_listview);
        String UID = getIntent().getExtras().getString("UID");
        roomArrayList = new ArrayList<>();
        ListRoomAdapter listRoomAdapter = new ListRoomAdapter(StudentMainActivity.this,R.layout.item_card_room,roomArrayList);
        listView.setAdapter(listRoomAdapter);
        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference().child("room");
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot datason : dataSnapshot.getChildren()) {
                    Room room = datason.getValue(Room.class);
                    room.setRoomID(datason.getKey());
                    roomArrayList.add(room);
                    listRoomAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Room room = roomArrayList.get(position);
                int crole = Constants.CLIENT_ROLE_AUDIENCE;
                String broad = room.getBroadcaster();
                StudentMainActivity.this.forwardToLiveRoom(crole,broad);
            }
        });
    }

    @Override
    protected void initUIandEvent() {

    }

    @Override
    protected void deInitUIandEvent() {

    }

    public void forwardToLiveRoom(int cRole,String room) {
        Intent i = new Intent(StudentMainActivity.this, LiveRoomActivity.class);
        i.putExtra(ConstantApp.ACTION_KEY_CROLE, cRole);
        i.putExtra(ConstantApp.ACTION_KEY_ROOM_NAME, room);

        startActivity(i);
    }
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
