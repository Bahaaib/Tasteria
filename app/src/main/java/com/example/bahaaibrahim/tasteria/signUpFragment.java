package com.example.bahaaibrahim.tasteria;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;


public class signUpFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    ////////////////////////////////////////Fragment variables//////////////////////
    TextView alredayMemText;
    Switch genderSwitch;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public signUpFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static signUpFragment newInstance(String param1, String param2) {
        signUpFragment fragment = new signUpFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);


        }


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_sign_up, container, false);
        alredayMemText = (TextView) v.findViewById(R.id.alreadyMem);
        alredayMemText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginFragment loginFragment = new LoginFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
                fragmentTransaction.replace(android.R.id.content, loginFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        genderSwitch = (Switch) v.findViewById(R.id.genderSwitch);
        revColorStyle(genderSwitch.getTextOn(), genderSwitch.getTextOff());


        return v;
    }


    //To reverse the switch MALE/FEMALE Text color against the switch thumb color

    public void revColorStyle(CharSequence switchTxtOn, CharSequence switchTxtOff) {

        Spannable revColorText = new SpannableString(switchTxtOn);
        StyleSpan style = new StyleSpan(Typeface.BOLD);
        revColorText.setSpan(style, 0, switchTxtOn.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        revColorText.setSpan(new ForegroundColorSpan(Color.parseColor("#FFFFFF")), 0, switchTxtOn.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        genderSwitch.setTextOn(revColorText);

        revColorText = new SpannableString(switchTxtOff);
        revColorText.setSpan(style, 0, switchTxtOff.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        revColorText.setSpan(new ForegroundColorSpan(Color.parseColor("#E91E63")), 0, switchTxtOff.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        genderSwitch.setTextOff(revColorText);

    }
}

