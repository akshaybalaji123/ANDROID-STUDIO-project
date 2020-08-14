package net.simplifiedlearning.firebaseauth;


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class ParentChoreAdapter extends RecyclerView.Adapter<ParentChoreAdapter.MyViewHolder> {
    public List<Chore> choreList;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    final String MyPREFERENCES = "MyPrefs" ;

    public ParentChoreAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.parent_chore_details, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ParentChoreAdapter.MyViewHolder holder, final int position) {
        final Chore chore = choreList.get(position);
        holder.choreName.setText(chore.getChoreName());
        holder.chorePoints.setText(chore.getChorePoints());
        holder.status.setText(chore.getStatus());

        holder.redeem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.collection("chores").document(chore.getId()).update("status","reedeemed"); //update chore status
                db.collection("chores").document(chore.getId()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                String username=document.getData().get("username").toString();
                                db.collection("profiles").document(username).update("totalPoints", FieldValue.increment(Integer.parseInt(chore.getChorePoints())));
                            } else {

                            }
                        } else {
                            Log.d("error", "get failed with ", task.getException());
                        }
                    }
                });

                Toast.makeText(view.getContext(), "Reedemed", Toast.LENGTH_SHORT).show();
            }
        });
        holder.active.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.collection("chores").document(chore.getId()).update("status","active"); //update chore status
                Toast.makeText(view.getContext(), "Rejected", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return choreList.size();
    }
    public ParentChoreAdapter(List<Chore> choreList) {
        this.choreList = choreList;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView choreName, chorePoints, status;
            public Button redeem,active;
        public MyViewHolder(View view) {
            super(view);
            choreName = (TextView) view.findViewById(R.id.chorePNameText);
            chorePoints = (TextView) view.findViewById(R.id.chorePPointsText);
            status = (TextView) view.findViewById(R.id.chorePStatusText);
            redeem =  (Button) view.findViewById(R.id.Predeem);
            active =  (Button) view.findViewById(R.id.Pactive);

        }
    }
}
