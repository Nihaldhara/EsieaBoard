package com.example.esieaboard.view.fragments;

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
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.esieaboard.*;
import com.example.esieaboard.model.entities.Club;
import com.example.esieaboard.model.entities.User;
import com.example.esieaboard.view.adapters.ClubAdapter;
import com.example.esieaboard.view.adapters.EventAdapter;
import com.example.esieaboard.view.fragments.clubs.ClubNewFragment;
import com.example.esieaboard.view.fragments.clubs.ClubProfileFragment;
import com.example.esieaboard.view.fragments.events.EventPageFragment;
import com.example.esieaboard.view.fragments.users.UserProfileFragment;
import com.example.esieaboard.viewmodel.ClubViewModel;
import com.example.esieaboard.viewmodel.EventViewModel;
import com.example.esieaboard.viewmodel.UserViewModel;
import org.jetbrains.annotations.NotNull;

import static android.view.View.GONE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EsieaBoardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EsieaBoardFragment extends Fragment {

    ImageButton userImageButton;
    Button newClubButton;
    RecyclerView clubsRecyclerView, eventsRecyclerView;
    TextView userName;

    UserViewModel userViewModel;
    ClubViewModel clubViewModel;
    EventViewModel eventViewModel;

    ClubAdapter clubAdapter;
    EventAdapter eventAdapter;

    Club selectedClub;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_USER = "user";

    private User u;

    public EsieaBoardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param u Parameter 1.
     * @return A new instance of fragment EsieaBoardFragment.
     */
    public static EsieaBoardFragment newInstance(User u) {
        EsieaBoardFragment fragment = new EsieaBoardFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_USER, u);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            u = (User) getArguments().getSerializable(ARG_USER);
            selectedClub = new Club("", "", "");
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

        userViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(UserViewModel.class);

        userViewModel.get(u.getId()).observe(getViewLifecycleOwner(), user -> {
            userName.setText(user.getFirstName());

            if (user.getRights() < 2) {
                newClubButton.setVisibility(GONE);
            }
        });

        userImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userViewModel.get(u.getId()).observe(getViewLifecycleOwner(), user -> {
                    FragmentManager fragmentManager = getParentFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container_view, UserProfileFragment.newInstance(user));
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                });
            }
        });


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


        clubViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(ClubViewModel.class);
        clubAdapter = new ClubAdapter(club -> {
            userViewModel.get(u.getId()).observe(getViewLifecycleOwner(), user -> {
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container_view, ClubProfileFragment.newInstance(user, club));
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            });
        });

        clubsRecyclerView.setAdapter(clubAdapter);
        clubsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        clubViewModel.getAll().observe(getViewLifecycleOwner(), clubs -> {
            clubAdapter.setClubs(clubs);
        });

        eventViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(EventViewModel.class);
        eventAdapter = new EventAdapter(event -> {
            userViewModel.get(u.getId()).observe(getViewLifecycleOwner(), user -> {
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container_view, EventPageFragment.newInstance(user, event));
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            });
        });

        eventsRecyclerView.setAdapter(eventAdapter);
        eventsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        eventViewModel.getAllByUser(u.getId()).observe(getViewLifecycleOwner(), events -> {
            eventAdapter.setEvents(events);
        });
    }
}