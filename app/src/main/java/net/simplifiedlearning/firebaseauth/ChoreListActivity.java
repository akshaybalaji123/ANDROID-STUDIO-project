package net.simplifiedlearning.firebaseauth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ChoreListActivity extends AppCompatActivity {
    private List<Chore> movieList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ChoreAdapter mAdapter;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    SharedPreferences sharedpreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chore_list);

        recyclerView = (RecyclerView) findViewById(R.id.choreListRecycler);

        mAdapter = new ChoreAdapter(movieList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);

        prepareChoreData();
    }

    private void prepareChoreData() {
        final String MyPREFERENCES = "MyPrefs" ;
        String parentEmail = sharedpreferences.getString("parentEmail", null);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        db.collection("chores")
                .whereEqualTo("email", parentEmail) //parentEmail is the id from database
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Chore chore = document.toObject(Chore.class);
                                chore.setId(document.getId().toString());
                                if(chore.getStatus().equals("active"))
                                    movieList.add(chore);
                                Log.d("data",document.getData().toString());
                                Log.d("data",chore.email);
                            }  mAdapter.notifyDataSetChanged();
                        } else {
                            Chore chore=new Chore("Fa","","","","0");
                            movieList.add(chore); mAdapter.notifyDataSetChanged();
                        }
                    }
                });
        /*Chore chore = new Chore("clean","2","akshaybalaji0105@gmail.com", "active");
        movieList.add(chore);
        chore = new Chore("dust","1","akshaybalaji0105@gmail.com", "active");
        movieList.add(chore);
        chore = new Chore("mop","3","akshaybalaji0105@gmail.com", "active");
        movieList.add(chore);*/

        mAdapter.notifyDataSetChanged();
    }
}
