package com.example.esieaboard.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.esieaboard.*;
import com.example.esieaboard.models.ClubModel;
import com.example.esieaboard.models.EventModel;
import com.example.esieaboard.models.SubscriptionModel;
import com.example.esieaboard.models.UserModel;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static android.view.View.GONE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EsieaBoardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EsieaBoardFragment extends Fragment implements MixedAdapter.ClubClickListener, MixedAdapter.EventClickListener {

    private static final int MANAGE_CLUBS_REQUEST_CODE = 4;
    private static final int LOG_OUT_REQUEST_CODE = 6;
    private static final int EVENT_EDIT_REQUEST = 8;
    private static final int CLUB_PROFILE_REQUEST_CODE = 11;
    private static final int EVENT_PAGE_REQUEST_CODE = 12;

    ImageButton userImageButton;
    Button newClubButton;
    RecyclerView clubsRecyclerView, eventsRecyclerView;
    DataBaseHelper dataBaseHelper;
    ArrayList<SubscriptionModel> subscriptions;
    ArrayList<ClubModel> clubs;
    ArrayList<EventModel> events;
    MixedAdapter clubsAdapter, eventsAdapter;
    ClubModel selectedClub;
    TextView userName;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_USER = "user";

    private UserModel user;

    public EsieaBoardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param user Parameter 1.
     * @return A new instance of fragment EsieaBoardFragment.
     */
    public static EsieaBoardFragment newInstance(UserModel user) {
        EsieaBoardFragment fragment = new EsieaBoardFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_USER, user);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            user = (UserModel) getArguments().getSerializable(ARG_USER);
            selectedClub = new ClubModel(-1, "", "", "");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_esiea_board, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        clubsRecyclerView = view.findViewById(R.id.club_profiles);
        eventsRecyclerView = view.findViewById(R.id.events_list);
        userImageButton = view.findViewById(R.id.user_profile_button);
        newClubButton = view.findViewById(R.id.new_club_button);
        userName = view.findViewById(R.id.user_name);

        userName.setText(user.getFirstName());

        userImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container_view, UserProfileFragment.newInstance(user));
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        if (user.getRights() < 2) {
            newClubButton.setVisibility(GONE);
        }
        newClubButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container_view, ClubNewFragment.newInstance());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        dataBaseHelper = new DataBaseHelper(getContext());

        subscriptions = new ArrayList<>();
        clubs = new ArrayList<>();
        events = new ArrayList<>();

        displaySubscriptions();
        displayClubs();
        displayEvents();

        clubsAdapter = new MixedAdapter(getContext(), subscriptions, new ArrayList<>(), new ArrayList<>(), clubs, user);
        clubsAdapter.setClubClickListener(this);
        clubsRecyclerView.setAdapter(clubsAdapter);
        clubsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        eventsAdapter = new MixedAdapter(getContext(), subscriptions, new ArrayList<>(), events, new ArrayList<>(), user);
        eventsAdapter.setEventClickListener(this);
        eventsRecyclerView.setAdapter(eventsAdapter);
        eventsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    void displaySubscriptions() {
        subscriptions.clear();
        Cursor cursor = dataBaseHelper.readSubscriptionData();

        if (cursor != null) {
            while (cursor.moveToNext()) {
                if (cursor.getInt(1) == user.getId()) {
                    subscriptions.add(new SubscriptionModel(cursor.getInt(0), cursor.getInt(1),
                            cursor.getInt(2), cursor.getInt(3)));
                }
            }
            cursor.close();
        }
    }

    void displayClubs() {
        clubs.clear();
        Cursor cursor = dataBaseHelper.readClubData();

        if (cursor != null) {
            while (cursor.moveToNext()) {
                clubs.add(new ClubModel(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3)));
            }
        }
    }

    void displayEvents() {
        events.clear();
        Cursor cursorEvents = dataBaseHelper.readEventData();
        Cursor cursorSubscriptions = dataBaseHelper.readSubscriptionData();

        if (cursorEvents != null && cursorSubscriptions != null) {
            while (cursorSubscriptions.moveToNext()) {
                while (cursorEvents.moveToNext()) {
                    if (cursorEvents.getInt(1) == cursorSubscriptions.getInt(2)
                            && cursorSubscriptions.getInt(1) == user.getId()) {
                        events.add(new EventModel(
                                cursorEvents.getInt(0),
                                cursorEvents.getInt(1),
                                cursorEvents.getString(2),
                                cursorEvents.getString(3),
                                cursorEvents.getString(4),
                                cursorEvents.getString(5),
                                cursorEvents.getInt(6)
                        ));
                    }
                }
                cursorEvents.moveToFirst();
            }
        }
    }

    @Override
    public void onClubClick(ClubModel club) {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_view, ClubProfileFragment.newInstance(user, club));
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onEventClick(EventModel event) {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_view, EventPageFragment.newInstance(user, event));
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}