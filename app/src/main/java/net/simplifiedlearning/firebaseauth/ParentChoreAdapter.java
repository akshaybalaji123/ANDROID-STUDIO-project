package net.simplifiedlearning.firebaseauth;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class ParentChoreAdapter extends RecyclerView.Adapter<ParentChoreAdapter.MyViewHolder> {
    public List<Chore> choreList;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    public ParentChoreAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chore_details, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ParentChoreAdapter.MyViewHolder holder, final int position) {
        Chore chore = choreList.get(position);
        holder.choreName.setText(chore.getChoreName());
        holder.chorePoints.setText(chore.getChorePoints());
        holder.status.setText(chore.getStatus());

        holder.redeem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("actiontest", position+ " works; ");
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
