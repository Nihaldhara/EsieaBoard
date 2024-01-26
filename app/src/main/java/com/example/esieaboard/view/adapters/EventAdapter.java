package com.example.esieaboard.view.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.example.esieaboard.R;
import com.example.esieaboard.model.entities.Event;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    private final EventClickListener eventClickListener;

    private final List<Event> events = new ArrayList<>();

    public interface EventClickListener {
        void onEventClick(Event event);
    }

    public EventAdapter(EventClickListener eventClickListener) {
        this.eventClickListener = eventClickListener;
    }

    public void setEvents(List<Event> events) {
        EventDiffCallback diffCallback = new EventDiffCallback(this.events, events);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);
        this.events.clear();
        this.events.addAll(events);
        diffResult.dispatchUpdatesTo(this);
    }

    @NonNull
    @NotNull
    @Override
    public EventAdapter.EventViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.event_board, parent, false);

        return new EventAdapter.EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull EventAdapter.EventViewHolder holder, int position) {
        Event event = events.get(position);
        holder.bind(event, eventClickListener);
    }

    @Override
    public int getItemCount() {
        return events.size();
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

        public void bind(Event event, EventClickListener listener) {
            eventName.setText(event.getName());
            eventDate.setText(event.getDate());
            eventLocation.setText(event.getLocation());
            eventCapacity.setText(String.valueOf(event.getCapacity()));

            eventsLayout.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onEventClick(event);
                }
            });
        }
    }

    public class EventDiffCallback extends DiffUtil.Callback {

        private final List<Event> oldList;
        private final List<Event> newList;

        public EventDiffCallback(List<Event> oldList, List<Event> newList) {
            this.oldList = oldList;
            this.newList = newList;
        }

        @Override
        public int getOldListSize() {
            return oldList.size();
        }

        @Override
        public int getNewListSize() {
            return newList.size();
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            // Return whether the items are the same (e.g., same IDs)
            return oldList.get(oldItemPosition).getId() == newList.get(newItemPosition).getId();
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            // Return whether the contents of the items are the same
            // This might be a detailed comparison of each relevant field
            return oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
        }
    }

}
