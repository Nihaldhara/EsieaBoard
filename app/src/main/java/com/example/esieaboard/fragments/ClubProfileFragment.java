package com.example.esieaboard.fragments;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
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
import static android.view.View.VISIBLE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ClubProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClubProfileFragment extends Fragment implements MixedAdapter.EventClickListener{

    private static final int CREATE_EVENT_REQUEST_CODE = 5;
    private static final int EDIT_CLUB_REQUEST_CODE = 9;
    private static final int EVENT_PAGE_REQUEST_CODE = 12;

    ImageButton backButton;
    Button modifyButton, newButton, deleteButton, subscribeButton, unsubscribeButton;
    ImageView imageViewProfile;
    TextView clubName, clubEmailAddress, clubDescription;

    SubscriptionModel subscription;
    DataBaseHelper dataBaseHelper;

    ArrayList<EventModel> events;
    MixedAdapter eventsAdapter;
    RecyclerView recyclerView;

    private static final String ARG_USER = "user";
    private static final String ARG_CLUB = "club";

    private UserModel user;
    private ClubModel club;

    public ClubProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param userArg Parameter 1.
     * @param clubArg Parameter 2.
     * @return A new instance of fragment ClubProfileFragment.
     */
    public static ClubProfileFragment newInstance(UserModel userArg, ClubModel clubArg) {
        ClubProfileFragment fragment = new ClubProfileFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_USER, userArg);
        args.putSerializable(ARG_CLUB, clubArg);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            user = (UserModel) getArguments().getSerializable(ARG_USER);
            club = (ClubModel) getArguments().getSerializable(ARG_CLUB);
            events = new ArrayList<>();
            dataBaseHelper = new DataBaseHelper(getActivity());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_club_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        backButton = view.findViewById(R.id.back_button);
        deleteButton = view.findViewById(R.id.delete_button);
        modifyButton = view.findViewById(R.id.modify_button);
        newButton = view.findViewById(R.id.button_new);
        subscribeButton = view.findViewById(R.id.subscribe_button);
        unsubscribeButton = view.findViewById(R.id.unsubscribe_button);
        imageViewProfile = view.findViewById(R.id.logo_image);
        clubName = view.findViewById(R.id.club_name);
        clubEmailAddress = view.findViewById(R.id.club_email_address);
        clubDescription = view.findViewById(R.id.club_description);
        recyclerView = view.findViewById(R.id.recycler_view);

        clubName.setText(club.getName());
        clubEmailAddress.setText(club.getEmail());
        clubDescription.setText(club.getDescription());

        displayEvents();

        eventsAdapter = new MixedAdapter(getActivity(), new ArrayList<>(), new ArrayList<>(), events, new ArrayList<>(), user);
        eventsAdapter.setEventClickListener(this);
        recyclerView.setAdapter(eventsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if (subscribed()) {
            subscribeButton.setVisibility(GONE);
            unsubscribeButton.setVisibility(VISIBLE);
        }

        if(user.getRights() < 1) { newButton.setVisibility(GONE); }
        newButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container_view, EventNewFragment.newInstance(club.getId()));
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getParentFragmentManager();
                fragmentManager.popBackStack();
            }
        });

        if(user.getRights() < 1) { modifyButton.setVisibility(GONE); }
        modifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container_view, ClubEditFragment.newInstance(club));
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        if(user.getRights() < 2) { deleteButton.setVisibility(GONE); }
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog();
            }
        });

        subscribeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                subscription = new SubscriptionModel(-1, user.getId(), club.getId(), 1);
                dataBaseHelper.addSubscription(subscription);
                subscribeButton.setVisibility(GONE);
                unsubscribeButton.setVisibility(VISIBLE);
            }
        });

        unsubscribeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cursor = dataBaseHelper.readSubscriptionData();

                if(cursor != null) {
                    while(cursor.moveToNext()) {
                        if(cursor.getInt(1) == user.getId() && cursor.getInt(2) == club.getId()) {
                            subscription = dataBaseHelper.getSubscriptionByClubAndUserId(club.getId(), user.getId());
                        }
                    }
                }
                dataBaseHelper.deleteSubscriptionRow(String.valueOf(subscription.getId()));
                subscribeButton.setVisibility(VISIBLE);
                unsubscribeButton.setVisibility(GONE);
            }
        });
    }

    void displayEvents() {
        events.clear();
        Cursor cursor = dataBaseHelper.readEventData();

        if (cursor != null) {
            while (cursor.moveToNext()) {
                if (cursor.getInt(1) == club.getId()) {
                    events.add(new EventModel(
                            cursor.getInt(0),
                            cursor.getInt(1),
                            cursor.getString(2),
                            cursor.getString(3),
                            cursor.getString(4),
                            cursor.getString(5),
                            cursor.getInt(6)
                    ));
                }
            }
            cursor.close();
        }
    }

    @Override
    public void onEventClick(EventModel event) {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_view, EventPageFragment.newInstance(user, event));
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    boolean subscribed() {
        Cursor cursor = dataBaseHelper.readSubscriptionData();

        if(cursor != null) {
            while(cursor.moveToNext()) {
                if(cursor.getInt(2) == club.getId() && cursor.getInt(1) == user.getId()) {
                    return true;
                }
            }
        }

        return false;
    }

    void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Delete " + club.getName() + " ?");
        builder.setMessage("Do you really want to delete " + club.getName() + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DataBaseHelper dataBase = new DataBaseHelper(getContext());
                dataBase.deleteClubRow(String.valueOf(club.getId()));
                FragmentManager fragmentManager = getParentFragmentManager();
                fragmentManager.popBackStackImmediate();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }
}