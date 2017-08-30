package com.example.bahaaibrahim.tasteria;


import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class signUpFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    ////////////////////////////////////////Fragment variables//////////////////////
    TextView alredayMemText, errorText;
    Button genderButtonMale, genderButtonFemale, signUpButton;
    ArrayList ageRange;
    ArrayAdapter spinnerArrayAdapter;
    Spinner ageSpinner;
    EditText userName, signMail, signPass;
    Drawable errorIcon;
    String spinnerItem;


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

        genderButtonMale = (Button) v.findViewById(R.id.genderButtonMale);
        genderButtonFemale = (Button) v.findViewById(R.id.genderButtonFemale);

        genderButtonMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                swapMaleColors();
            }
        });

        genderButtonFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                swapFemaleColors();
            }
        });


        ageRange = new ArrayList<>();
        ageRange.add("Age");
        for (int i = 8; i <= 100; i++) {
            ageRange.add(Integer.toString(i));
        }
        spinnerArrayAdapter = new ArrayAdapter<>(this.getActivity(), R.layout.custom_spinner, ageRange);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ageSpinner = (Spinner) v.findViewById(R.id.ageSpinner);
        ageSpinner.setAdapter(spinnerArrayAdapter);
        ageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinnerItem = adapterView.getItemAtPosition(i).toString();


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        userName = (EditText) v.findViewById(R.id.userName);
        signMail = (EditText) v.findViewById(R.id.signMail);
        signPass = (EditText) v.findViewById(R.id.signPass);


        signUpButton = (Button) v.findViewById(R.id.signUpButton);
        errorIcon = (Drawable) ContextCompat.getDrawable(this.getActivity(), R.drawable.ic_error);


        signUpButton.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {
                final String userNameStr = userName.getText().toString();
                final String signMailStr = signMail.getText().toString();
                final String signPassStr = signPass.getText().toString();
                errorIcon.setBounds(0, 0, errorIcon.getIntrinsicWidth(), errorIcon.getIntrinsicHeight());


                if (!isValidUsername(userNameStr)) {
                    userName.setError("Invalid Username", errorIcon);
                } else {
                    userName.setError(null);
                    //send valid data
                }


                if (!isValidEmail(signMailStr)) {

                    signMail.setError("Invalid E-Mail form", errorIcon);
                } else {//send valid data
                }

                if (!isValidPassword(signPassStr)) {
                    signPass.setError("Password must be 6 characters at least", errorIcon);
                } else {//send valid data
                    
                }
                if (spinnerItem == "Age") {
                    Toast.makeText(getActivity(), "Pick up your age", Toast.LENGTH_LONG).show();
                } else {//send valid data

                }
            }

        });

        return v;


    }


    private void swapMaleColors() {
        genderButtonFemale.setBackgroundResource(R.drawable.genderbuttonfemale);
        genderButtonFemale.setTextColor(Color.parseColor("#FFFFFF"));
        genderButtonMale.setBackgroundResource(R.drawable.genderbuttonmale);
        genderButtonMale.setTextColor(Color.parseColor("#C2185B"));
    }

    private void swapFemaleColors() {
        genderButtonMale.setBackgroundResource(R.drawable.genderbuttonmalerev);
        genderButtonMale.setTextColor(Color.parseColor("#FFFFFF"));
        genderButtonFemale.setBackgroundResource(R.drawable.genderbuttonfemalerev);
        genderButtonFemale.setTextColor(Color.parseColor("#C2185B"));
    }


    // validating Username
    private boolean isValidUsername(String userName) {
        if (userName != "" && userName.length() > 2) {
            return true;
        }
        return false;
    }

    // validating email id
    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    // validating password with retype password
    private boolean isValidPassword(String pass) {
        if (pass != null && pass.length() > 6) {
            return true;
        }
        return false;
    }


}




