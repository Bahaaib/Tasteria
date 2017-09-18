package com.example.bahaaibrahim.tasteria;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class ProfileFragment extends Fragment {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    public RecyclerView placesRecycler;
    private PlacesAdapter placesAdapter;


    private DatabaseReference ref;


    ArrayList<PlaceModel> cardContent = new ArrayList<>();

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        mAuth = FirebaseAuth.getInstance();
        //Initialize Database..
        ref = FirebaseDatabase.getInstance().getReference();

        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                fetchData(dataSnapshot);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        //Initialize RecyclerView
        placesRecycler = (RecyclerView) v.findViewById(R.id.placesRecycler);

        //Adapter
        placesAdapter = new PlacesAdapter(this.getActivity(), cardContent);
        placesRecycler.setAdapter(placesAdapter);
        placesRecycler.setLayoutManager(new LinearLayoutManager(this.getActivity()));


        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() == null) {
            startActivity(new Intent(getActivity(), LoginActivity.class));
        }
    }

    public void updateUI() {

        if (mAuth.getCurrentUser() == null) {
            startActivity(new Intent(getActivity(), LoginActivity.class));
        }

    }

    public void fetchData(DataSnapshot dataSnapshot) {
        cardContent.clear(); //clear card content from last usage

        for (DataSnapshot ds : dataSnapshot.getChildren()) {

            PlaceModel placeModel = ds.getValue(PlaceModel.class);

            cardContent.add(placeModel);
            placesAdapter.notifyDataSetChanged();

        }

    }


}
