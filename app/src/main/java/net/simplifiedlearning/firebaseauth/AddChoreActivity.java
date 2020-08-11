package net.simplifiedlearning.firebaseauth;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class AddChoreActivity extends AppCompatActivity implements View.OnClickListener {


    EditText editTextChoreId,editTextChoreName, editTextPointValue;
    Button AddChoreButtonSubmit;
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
     editTextPointValue.setFilters(new InputFilter[]{new InputFilterMinMax("1", "5")});



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
        }
        return false;
    }
    private void addData () {
        String name = editTextChoreName.getText().toString().trim();
        String id = editTextChoreId.getText().toString().trim();
        String point_value = editTextPointValue.getText().toString().trim();
        String email=mAuth.getCurrentUser().getEmail();
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("choreName",name);
        map.put("email",email);
        map.put("status","active");
        map.put("chorePoints",point_value);
        db.collection("chores").document(id).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
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
                startActivity(new Intent(AddChoreActivity.this,ParentChoreListActivity.class));
                break;
        }

        }

    }


