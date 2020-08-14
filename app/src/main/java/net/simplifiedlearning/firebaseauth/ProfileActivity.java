package net.simplifiedlearning.firebaseauth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    final String MyPREFERENCES = "MyPrefs";


    Button AddChoreButton;
    TextView totalPoints;
    SharedPreferences sharedpreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        AddChoreButton = (Button) findViewById(R.id.AddChoreButton);
        totalPoints = (TextView) findViewById(R.id.totalPoints);


        findViewById(R.id.AddChoreButton).setOnClickListener(this);

        preparepointdata();

    }

    private void preparepointdata() {
        final String MyPREFERENCES = "MyPrefs";

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String parentEmail = sharedpreferences.getString("parentEmail", null);
        String username = sharedpreferences.getString("username", null);
        findViewById()

        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        DocumentReference docIdRef = rootRef.collection("profiles").document(username);
        db.collection("profiles").document(username)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) // if username is present
                    {
                        findViewById().setText(document.getData().get("totalPoints").toString());//id of textView
                    } else {

                        //add code (user not found/registered)
                    }
                        } else {
                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.AddChoreButton:
                startActivity(new Intent(ProfileActivity.this, AddChoreActivity.class));
        }
    }
}
