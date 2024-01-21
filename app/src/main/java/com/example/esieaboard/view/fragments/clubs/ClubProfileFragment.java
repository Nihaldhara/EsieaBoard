package com.example.esieaboard.view.fragments.clubs;

import android.content.DialogInterface;
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
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.esieaboard.*;
import com.example.esieaboard.model.entities.Club;
import com.example.esieaboard.model.entities.Event;
import com.example.esieaboard.model.entities.Subscription;
import com.example.esieaboard.model.entities.User;
import com.example.esieaboard.view.adapters.EventAdapter;
import com.example.esieaboard.view.fragments.events.EventNewFragment;
import com.example.esieaboard.view.fragments.events.EventPageFragment;
import com.example.esieaboard.viewmodel.ClubViewModel;
import com.example.esieaboard.viewmodel.EventViewModel;
import com.example.esieaboard.viewmodel.SubscriptionViewModel;
import com.example.esieaboard.viewmodel.UserViewModel;
import org.jetbrains.annotations.NotNull;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ClubProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClubProfileFragment extends Fragment implements EventAdapter.EventClickListener {

    ImageButton backButton;
    Button modifyButton, newButton, deleteButton, subscribeButton, unsubscribeButton;
    ImageView imageViewProfile;
    TextView clubName, clubEmailAddress, clubDescription;

    Subscription subscription;

    UserViewModel userViewModel;
    EventAdapter eventAdapter;
    EventViewModel eventViewModel;
    ClubViewModel clubViewModel;
    SubscriptionViewModel subscriptionViewModel;

    RecyclerView recyclerView;

    private static final String ARG_USER = "user";
    private static final String ARG_CLUB = "club";

    private User u;
    private Club c;

    public ClubProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param u Parameter 1.
     * @param c Parameter 2.
     * @return A new instance of fragment ClubProfileFragment.
     */
    public static ClubProfileFragment newInstance(User u, Club c) {
        ClubProfileFragment fragment = new ClubProfileFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_USER, u);
        args.putSerializable(ARG_CLUB, c);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            u = (User) getArguments().getSerializable(ARG_USER);
            c = (Club) getArguments().getSerializable(ARG_CLUB);
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

        userViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(UserViewModel.class);
        eventViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(EventViewModel.class);
        clubViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(ClubViewModel.class);
        subscriptionViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(SubscriptionViewModel.class);

        clubViewModel.get(c.getId()).observe(getViewLifecycleOwner(), club -> {
            clubName.setText(club.getName());
            clubEmailAddress.setText(club.getEmail());
            clubDescription.setText(club.getDescription());
        });

        userViewModel.get(u.getId()).observe(getViewLifecycleOwner(), user -> {
            if (user.getRights() < 1) {
                newButton.setVisibility(GONE);
            }
            if (user.getRights() < 1) {
                modifyButton.setVisibility(GONE);
            }
            if (user.getRights() < 2) {
                deleteButton.setVisibility(GONE);
            }
        });

        eventAdapter = new EventAdapter(event -> {
            userViewModel.get(u.getId()).observe(getViewLifecycleOwner(), user -> {
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container_view, EventPageFragment.newInstance(user, event));
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            });
        });

        recyclerView.setAdapter(eventAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        eventViewModel.getAllByClub(c.getId()).observe(getViewLifecycleOwner(), events -> {
            eventAdapter.setEvents(events);
        });

        subscriptionViewModel.getByUserId(u.getId(), c.getId()).observe(getViewLifecycleOwner(), subscription -> {
            if (subscription != null) {
                subscribeButton.setVisibility(GONE);
                unsubscribeButton.setVisibility(VISIBLE);
            } else {
                subscribeButton.setVisibility(VISIBLE);
                unsubscribeButton.setVisibility(GONE);
            }
        });

        newButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clubViewModel.get(c.getId()).observe(getViewLifecycleOwner(), club -> {
                    userViewModel.get(u.getId()).observe(getViewLifecycleOwner(), user -> {
                        FragmentManager fragmentManager = getParentFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_container_view, EventNewFragment.newInstance(club.getId()));
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    });
                });
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getParentFragmentManager();
                fragmentManager.popBackStack();
            }
        });

        modifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clubViewModel.get(c.getId()).observe(getViewLifecycleOwner(), club -> {
                    FragmentManager fragmentManager = getParentFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container_view, ClubEditFragment.newInstance(club));
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                });
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog();
            }
        });

        subscribeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                subscription = new Subscription(u.getId(), c.getId(), 1);
                subscriptionViewModel.insert(subscription);
            }
        });

        unsubscribeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                subscriptionViewModel.unsubscribeUser(u.getId(), c.getId());
            }
        });
    }

    @Override
    public void onEventClick(Event event) {
        userViewModel.get(u.getId()).observe(getViewLifecycleOwner(), user -> {
            FragmentManager fragmentManager = getParentFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container_view, EventPageFragment.newInstance(user, event));
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        });

    }

    void confirmDialog() {
        clubViewModel.get(c.getId()).observe(getViewLifecycleOwner(), club -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            builder.setTitle("Delete " + club.getName() + " ?");
            builder.setMessage("Do you really want to delete " + club.getName() + " ?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    clubViewModel.delete(club);
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
        });
    }
}