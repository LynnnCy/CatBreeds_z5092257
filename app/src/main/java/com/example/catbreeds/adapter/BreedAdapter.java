package com.example.catbreeds.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.catbreeds.R;
import com.example.catbreeds.activities.BreedDetailActivity;
import com.example.catbreeds.model.BreedList;

import java.util.List;


// We need to give a type in angle brackets <> when we extend RecyclerView.Adapter
// It's saying that we want an adapter that adapts to ArticleViewHolder (our custom ViewHolder)
public class BreedAdapter extends RecyclerView.Adapter<BreedAdapter.BreedViewHolder>{
    // class variable that holds the data that we want to adapt
    private List<BreedList> breedToAdapt;

    public void setData(List<BreedList> breedToAdapt) {
        // This is basically a Setter that we use to give data to the adapter
        this.breedToAdapt = breedToAdapt;
    }

    @NonNull
    @Override
    public BreedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view =
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.breed, parent, false);
        if (view ==null){

        }
        // Then create an instance of your custom ViewHolder with the View you got from inflating
        // the layout.
        BreedViewHolder breedViewHolder = new BreedViewHolder(view);
        return breedViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BreedViewHolder holder, int position) {
        final BreedList breedAtPosition = breedToAdapt.get(position);

        holder.nameTextView.setText(breedAtPosition.getName());

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();

                Intent intent = new Intent(context, BreedDetailActivity.class);
                intent.putExtra("id", breedAtPosition.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(breedToAdapt.size() == 0){
            return 0;
        }
        return breedToAdapt.size();
    }

    // ViewHolder represents one item, but doesn't have data when it's first constructed.
    // We assign the data in onBindViewHolder.
    public static class BreedViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public TextView nameTextView;

        // This constructor is used in onCreateViewHolder
        public BreedViewHolder(View v) {
            super(v);  // runs the constructor for the ViewHolder superclass
            view = v;
            nameTextView = v.findViewById(R.id.breedName);


        }
    }
}
