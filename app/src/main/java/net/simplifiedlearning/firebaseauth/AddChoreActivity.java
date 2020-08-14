package net.simplifiedlearning.firebaseauth;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class AddChoreActivity extends AppCompatActivity implements View.OnClickListener {


    EditText editTextChoreId,editTextChoreName, editTextPointValue;
    Button AddChoreButtonSubmit;
    Spinner childUsernames;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_chore);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

     editTextChoreId = findViewById(R.id.editTextChoreId);
     editTextChoreName = findViewById(R.id.editTextChoreName);
     editTextPointValue = (EditText) findViewById(R.id.editTextPointValue);
     childUsernames = (Spinner) findViewById(R.id.childUsernames);
     editTextPointValue.setFilters(new InputFilter[]{new InputFilterMinMax("1", "5")});


        db.collection("profiles").whereEqualTo("parentEmail",mAuth.getCurrentUser().getEmail()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<String> lst=new ArrayList<String>();
                for(DocumentSnapshot ds:queryDocumentSnapshots.getDocuments())
                {
                    lst.add(ds.getId().toString());
                }
                Spinner s = (Spinner) findViewById(R.id.childUsernames);
                String[] st=new String[lst.size()];
                st=lst.toArray(st);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                        android.R.layout.simple_spinner_item, st);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                s.setAdapter(adapter);
            }
        });

        findViewById(R.id.AddChoreButtonSubmit).setOnClickListener(this);
        findViewById(R.id.checkpendingchores).setOnClickListener(this);
    }

    public class InputFilterMinMax implements InputFilter, net.simplifiedlearning.firebaseauth.InputFilterMinMax {
        private int min, max;

        public InputFilterMinMax(int min, int max) {
            this.min = min;
            this.max = max;
        }

        public InputFilterMinMax(String min, String max) {
            this.min = Integer.parseInt(min);
            this.max = Integer.parseInt(max);
        }

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            try {
                int input = Integer.parseInt(dest.toString() + source.toString());
                if (isInRange(min, max, input))
                    return null;
            } catch (NumberFormatException nfe) { }
            return "";
        }

        private boolean isInRange(int a, int b, int c) {
            return b > a ? c >= a && c <= b : c >= b && c <= a;
        }
    } private boolean validateInputs(String name, String id, String point_value) {
        if (name.isEmpty()) {
            editTextChoreName.setError("Name required");
            editTextChoreName.requestFocus();
            return true;
        }

        if (id.isEmpty()) {
           editTextChoreId.requestFocus(Integer.parseInt("Id Required"));
            return true;
        }

        if (point_value.isEmpty()) {
            editTextPointValue.setError("Point value required");
            editTextPointValue.requestFocus();
            return true;
        } ///add spinner validation later
        return false;
    }
    private void addData () {
        String name = editTextChoreName.getText().toString().trim();
        String username = childUsernames.getSelectedItem().toString();
        String id = editTextChoreId.getText().toString().trim();
        String point_value = editTextPointValue.getText().toString().trim();
        String email=mAuth.getCurrentUser().getEmail();
        String uniqueID = UUID.randomUUID().toString();
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("choreName",name);
        map.put("email",email);
        map.put("username",username);
        map.put("status","active");
        map.put("chorePoints",point_value);
        map.put("id",id);
        db.collection("chores").document(uniqueID).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(AddChoreActivity.this, "Chore Add Successfull", Toast.LENGTH_SHORT).show();
            }
        })

                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddChoreActivity.this, "Chore Add failed", Toast.LENGTH_SHORT).show();
                    }
                });

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.AddChoreButtonSubmit:
                addData();
                break;

            case R.id.checkpendingchores:
                startActivity(new Intent(AddChoreActivity.this, ParentChoreListActivity.class));
                break;
        }

        }

    }


