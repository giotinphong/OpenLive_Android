package io.agora.custom.ui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.agora.custom.User;
import io.agora.openlive.R;
import io.agora.openlive.ui.MainActivity;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG ="TAG" ;
    Button btnLogin;
    EditText edName,edPass;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    //TODO: login with google,facebook
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);
        btnLogin = (Button)findViewById(R.id.acti_login_button_login);
        edName = (EditText)findViewById(R.id.acti_login_edittext_username);
        edPass = (EditText)findViewById(R.id.acti_login_edittext_pass);
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
        mAuth.addAuthStateListener(mAuthListener);

        //signing
        btnLogin.setOnClickListener((View v) ->{
                String email = edName.getText().toString().trim();;
                String password = edPass.getText().toString().trim();
                if(email==null||password==null) return;
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()) {
                                    Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());
                                        // Name, email address, and profile photo Url
                                        String UID = mAuth.getCurrentUser().getUid();
                                        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference().child("user").child(UID);
                                        mRef.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                User user1 = dataSnapshot.getValue(User.class);
                                                if (user1.getRank().equals("student")) {
                                                    Intent in = new Intent(LoginActivity.this, StudentMainActivity.class);
                                                    in.putExtra("UID", UID);
                                                    startActivity(in);
                                                } else if (user1.getRank().equals("teacher")) {
                                                    Intent in = new Intent(LoginActivity.this, TeacherMainActivity.class);
                                                    in.putExtra("UID", UID);
                                                    startActivity(in);
                                                }
                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {

                                            }
                                        });

                                        // The user's ID, unique to the Firebase project. Do NOT use this value to
                                        // authenticate with your backend server, if you have one. Use
                                        // FirebaseUser.getToken() instead.


                                    // If sign in fails, display a message to the user. If sign in succeeds
                                    // the auth state listener will be notified and logic to handle the
                                    // signed in user can be handled in the listener.
                                }
                               else if (!task.isSuccessful()) {
                                    Log.w(TAG, "signInWithEmail:failed", task.getException());
                                    Toast.makeText(LoginActivity.this, R.string.auth_failed,
                                            Toast.LENGTH_SHORT).show();
                                    //user.delete();
                                    //mAuth.removeAuthStateListener(mAuthListener);
                                    return;
                                }

                                // ...
                            }
                        });

        });

    }

}
