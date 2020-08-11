package net.simplifiedlearning.firebaseauth;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.proto.TargetGlobal;

import java.util.List;

public class ChoreAdapter extends RecyclerView.Adapter<ChoreAdapter.MyViewHolder> {
    public List<Chore> choreList;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public ChoreAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chore_details, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ChoreAdapter.MyViewHolder holder, final int position) {
        final Chore chore = choreList.get(position);
        holder.choreName.setText(chore.getChoreName());
        holder.chorePoints.setText(chore.getChorePoints());
        holder.status.setText(chore.getStatus());

        holder.redeem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.collection("chores").document(chore.getId()).update("status","inprogress"); //update chore status
                Toast.makeText(view.getContext(), "Sent chore for Verification!! Chore name: "+chore.getChoreName(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return choreList.size();
    }
    public ChoreAdapter(List<Chore> choreList) {
        this.choreList = choreList;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView choreName, chorePoints, status;
            public Button redeem;
        public MyViewHolder(View view) {
            super(view);
            choreName = (TextView) view.findViewById(R.id.choreNameText);
            chorePoints = (TextView) view.findViewById(R.id.chorePointsText);
            status = (TextView) view.findViewById(R.id.choreStatusText);
            redeem =  (Button) view.findViewById(R.id.redeem);

        }
    }
}
