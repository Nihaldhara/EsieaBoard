package com.example.esieaboard;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.esieaboard.models.ClubModel;
import com.example.esieaboard.models.EventModel;
import com.example.esieaboard.models.SubscriptionModel;
import com.example.esieaboard.models.UserModel;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MixedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_CLUB = 0;
    private static final int VIEW_TYPE_EVENT = 1;
    private static final int VIEW_TYPE_USER = 2;
    private static final int EVENT_EDIT_REQUEST = 8;

    private Context context;
    private ArrayList<SubscriptionModel> subscriptions;
    private ArrayList<UserModel> users;
    private ArrayList<EventModel> events;
    private ArrayList<ClubModel> clubs;
    private UserModel user;
    public interface ClubClickListener {
        void onClubClick(ClubModel club);
    }
    private ClubClickListener clubClickListener;
    public interface EventClickListener {
        void onEventClick(EventModel event);
    }
    private EventClickListener eventClickListener;

    public MixedAdapter(Context context, ArrayList<SubscriptionModel> subscriptions, ArrayList<UserModel> users, ArrayList<EventModel> events, ArrayList<ClubModel> clubs, UserModel user) {
        this.context = context;
        this.subscriptions = subscriptions;
        this.users = users;
        this.events = events;
        this.clubs = clubs;
        this.user = user;
    }

    public void setClubClickListener(ClubClickListener listener) {
        this.clubClickListener = listener;
    }

    public void setEventClickListener(EventClickListener listener) {
        this.eventClickListener = listener;
    }

    @Override
    public int getItemCount() {
        return clubs.size() + events.size() + users.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position < clubs.size()) {
            return VIEW_TYPE_CLUB;
        } else if (position < clubs.size() + events.size()) {
            return VIEW_TYPE_EVENT;
        } else {
            return VIEW_TYPE_USER;
        }
    }

    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if (viewType == VIEW_TYPE_CLUB) {
            View clubView = inflater.inflate(R.layout.club_board, parent, false);
            return new ClubViewHolder(clubView);
        } else if (viewType == VIEW_TYPE_EVENT) {
            View eventView = inflater.inflate(R.layout.event_board, parent, false);
            return new EventViewHolder(eventView);
        } else {
            View userView = inflater.inflate(R.layout.admin_row, parent, false);
            return new UserViewHolder(userView);
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
                    if (clubClickListener != null) {
                        clubClickListener.onClubClick(club);
                    }
                }
            });

        } else if (holder instanceof EventViewHolder) {
            EventViewHolder eventHolder = (EventViewHolder) holder;
            EventModel event = events.get(position - clubs.size());
            eventHolder.eventName.setText(event.getName());
            eventHolder.eventDate.setText(event.getDate());
            eventHolder.eventLocation.setText(event.getLocation());
            eventHolder.eventCapacity.setText(String.valueOf(event.getCapacity()));

            eventHolder.eventsLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (eventClickListener != null) {
                        eventClickListener.onEventClick(event);
                    }
                }
            });

        } else if (holder instanceof UserViewHolder) {
            DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
            UserViewHolder userHolder = (UserViewHolder) holder;
            UserModel userSelect = users.get(position - (clubs.size() + events.size()));
            userHolder.userName.setText(userSelect.getFirstName());

            userHolder.adminButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    userSelect.setRights(2);
                    dataBaseHelper.updateUser(userSelect);
                }
            });

            userHolder.memberButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    userSelect.setRights(1);
                    dataBaseHelper.updateUser(userSelect);
                }
            });

            userHolder.userButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    userSelect.setRights(0);
                    dataBaseHelper.updateUser(userSelect);
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
        TextView eventName, eventDate, eventLocation, eventCapacity;
        LinearLayout eventsLayout;

        public EventViewHolder(View itemView) {
            super(itemView);
            clubImage = itemView.findViewById(R.id.club_image);
            eventName = itemView.findViewById(R.id.event_name);
            eventDate = itemView.findViewById(R.id.event_date);
            eventLocation = itemView.findViewById(R.id.event_location);
            eventCapacity = itemView.findViewById(R.id.event_capacity);
            eventsLayout = itemView.findViewById(R.id.events_layout);
        }
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView userName;
        Button adminButton, memberButton, userButton;

        public UserViewHolder(View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.user_name);
            adminButton = itemView.findViewById(R.id.button_admin);
            memberButton = itemView.findViewById(R.id.button_member);
            userButton = itemView.findViewById(R.id.button_user);
        }
    }
}
