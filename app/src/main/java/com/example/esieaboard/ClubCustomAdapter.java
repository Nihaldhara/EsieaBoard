package com.example.esieaboard;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ClubCustomAdapter extends RecyclerView.Adapter<ClubCustomAdapter.ClubHolder> {

    private Context context;
    private ArrayList club_id, club_name, club_description;
    private ArrayList event_id, event_club_id, event_name, event_description, event_date, event_location, event_capacity;

    ClubCustomAdapter(Context context, ArrayList club_id, ArrayList club_name, ArrayList club_description) {
        this.context = context;
        this.club_id = club_id;
        this.club_name = club_name;
        this.club_description = club_description;
    }

    @NonNull
    @NotNull
    @Override
    public ClubHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.club_row, parent, false);
        return new ClubHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ClubHolder holder, int position) {
        holder.clubName.setText(String.valueOf(club_name.get(position)));
        holder.clubsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ClubProfileActivity.class);
                intent.putExtra("id", Integer.parseInt(String.valueOf(club_id.get(holder.getAdapterPosition()))));
                intent.putExtra("name", String.valueOf(club_name.get(holder.getAdapterPosition())));
                intent.putExtra("description", String.valueOf(club_description.get(holder.getAdapterPosition())));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return club_id.size();
    }

    public class ClubHolder extends RecyclerView.ViewHolder {

        ImageView clubImage;
        TextView clubName;
        LinearLayout clubsLayout;

        public ClubHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            clubImage = itemView.findViewById(R.id.logo_button);
            clubName = itemView.findViewById(R.id.club_name);
            clubsLayout = itemView.findViewById(R.id.clubs_Layout);
        }
    }

    public class EventHolder extends RecyclerView.ViewHolder {

        public EventHolder(@NonNull @NotNull View itemView) {
            super(itemView);
        }
    }
}
