package com.example.esieaboard;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.esieaboard.models.ClubModel;
import com.example.esieaboard.models.EventModel;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MixedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_CLUB = 0;
    private static final int VIEW_TYPE_EVENT = 1;

    private Context context;
    private ArrayList<EventModel> events;
    private ArrayList<ClubModel> clubs;

    MixedAdapter(Context context, ArrayList<EventModel> events, ArrayList<ClubModel> clubs) {
        this.context = context;
        this.events = events;
        this.clubs = clubs;
    }

    @Override
    public int getItemCount() {
        return clubs.size() + events.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position < clubs.size() ? VIEW_TYPE_CLUB : VIEW_TYPE_EVENT;
    }

    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if (viewType == VIEW_TYPE_CLUB) {
            View clubView = inflater.inflate(R.layout.club_row, parent, false);
            return new ClubViewHolder(clubView);
        } else {
            View eventView = inflater.inflate(R.layout.event_row, parent, false);
            return new EventViewHolder(eventView);
        }
    }

    @Override
    public void onBindViewHolder(@NotNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ClubViewHolder) {
            ClubViewHolder clubHolder = (ClubViewHolder) holder;
            ClubModel club = clubs.get(position);
            clubHolder.clubName.setText(club.getName());
            clubHolder.clubsLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ClubProfileActivity.class);
                    intent.putExtra("id", club.getId());
                    intent.putExtra("name", club.getName());
                    intent.putExtra("description", club.getDescription());
                    context.startActivity(intent);
                }
            });

        } else if (holder instanceof EventViewHolder) {
            EventViewHolder eventHolder = (EventViewHolder) holder;
            EventModel event = events.get(position - clubs.size());
            eventHolder.eventName.setText(event.getName());
            eventHolder.eventDate.setText(event.getDate());
            eventHolder.eventLocation.setText(event.getLocation());
            eventHolder.eventDescription.setText(event.getDescription());
            eventHolder.eventCapacity.setText(String.valueOf(event.getCapacity()));

            eventHolder.eventsLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, EventPageActivity.class);
                    intent.putExtra("id", event.getId());
                    intent.putExtra("club_id", event.getClubId());
                    intent.putExtra("name", event.getName());
                    intent.putExtra("date", event.getDate());
                    intent.putExtra("location", event.getLocation());
                    intent.putExtra("description", event.getDescription());
                    intent.putExtra("capacity", event.getCapacity());
                    context.startActivity(intent);
                }
            });
        }
    }


    public static class ClubViewHolder extends RecyclerView.ViewHolder {
        ImageView clubImage;
        TextView clubName;
        LinearLayout clubsLayout;

        public ClubViewHolder(View itemView) {
            super(itemView);
            clubImage = itemView.findViewById(R.id.logo_button);
            clubName = itemView.findViewById(R.id.club_name);
            clubsLayout = itemView.findViewById(R.id.clubs_Layout);
        }
    }

    public static class EventViewHolder extends RecyclerView.ViewHolder {
        ImageView clubImage;
        TextView eventName, eventDate, eventLocation, eventDescription, eventCapacity;
        LinearLayout eventsLayout;

        public EventViewHolder(View itemView) {
            super(itemView);
            // Initialize your UI elements
            // eventName = itemView.findViewById(R.id.eventName);
            clubImage = itemView.findViewById(R.id.club_image);
            eventName = itemView.findViewById(R.id.event_name);
            eventDate = itemView.findViewById(R.id.event_date);
            eventLocation = itemView.findViewById(R.id.event_location);
            eventDescription = itemView.findViewById(R.id.event_description);
            eventCapacity = itemView.findViewById(R.id.event_capacity);
            eventsLayout = itemView.findViewById(R.id.events_layout);
        }
    }
}
