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
import com.example.esieaboard.model.entities.Club;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ClubAdapter extends RecyclerView.Adapter<ClubAdapter.ClubViewHolder>{

    private final ClubClickListener clubClickListener;

    private final List<Club> clubs = new ArrayList<>();

    public interface ClubClickListener {
        void onClubClick(Club club);
    }

    public ClubAdapter(ClubClickListener clubClickListener) {
        this.clubClickListener = clubClickListener;
    }

    public void setClubs(List<Club> clubs) {
        ClubDiffCallback diffCallback = new ClubDiffCallback(this.clubs, clubs);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);
        this.clubs.clear();
        this.clubs.addAll(clubs);
        diffResult.dispatchUpdatesTo(this);
    }

    @NonNull
    @NotNull
    @Override
    public ClubAdapter.ClubViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.club_board, parent, false);

        return new ClubAdapter.ClubViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ClubAdapter.ClubViewHolder holder, int position) {
        Club club = clubs.get(position);
        holder.bind(club, clubClickListener);
    }

    @Override
    public int getItemCount() {
        return clubs.size();
    }

    public static class ClubViewHolder extends RecyclerView.ViewHolder {

        ImageView clubImage;
        TextView clubName;
        LinearLayout clubsLayout;

        public ClubViewHolder(View itemView) {
            super(itemView);
            clubImage = itemView.findViewById(R.id.logo_button);
            clubName = itemView.findViewById(R.id.club_name);
            clubsLayout = itemView.findViewById(R.id.clubs_layout);
        }

        public void bind(Club club, ClubClickListener listener) {
            clubName.setText(club.getName());

            clubsLayout.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onClubClick(club);
                }
            });
        }
    }

    public class ClubDiffCallback extends DiffUtil.Callback {

        private final List<Club> oldList;
        private final List<Club> newList;

        public ClubDiffCallback(List<Club> oldList, List<Club> newList) {
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
