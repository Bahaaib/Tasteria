package com.example.bahaaibrahim.tasteria;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;


public class signUpFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    ////////////////////////////////////////Fragment variables//////////////////////
    private TextView alredayMemText;
    private Button genderButtonMale, genderButtonFemale, signUpButton;
    private ArrayList ageRange;
    private ArrayAdapter spinnerArrayAdapter;
    private Spinner ageSpinner;
    private EditText userName, signMail, signPass;
    private Drawable errorIcon;
    private String spinnerItem;
    private String usernameRes, mailRes, passRes, genderRes, ageRes;
    private FirebaseAuth nUserAuth;
    private FirebaseAuth.AuthStateListener nUserAuthListener;
    private boolean successfulSignUp;


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

        //Prevent keyboard from automatic popping up once onCreate fragment..
        this.getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        //Establishing Firebase connection..
        nUserAuth = FirebaseAuth.getInstance();
        nUserAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    startActivity(new Intent(getActivity(), MainActivity.class));
                } else {
                    //Action to be taken
                }
            }
        };

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

        genderRes = getString(R.string.male);
        genderButtonMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                swapMaleColors();
                genderRes = getString(R.string.male);
            }
        });

        genderButtonFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                swapFemaleColors();
                genderRes = getString(R.string.female);
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
                successfulSignUp = false;


                if (!isValidUsername(userNameStr)) {
                    userName.setError(getString(R.string.invalidUsername), errorIcon);
                    successfulSignUp = false;
                } else {
                    userName.setError(null);
                    //send valid data
                    usernameRes = userNameStr;
                    successfulSignUp = true;
                }


                if (!isValidEmail(signMailStr)) {

                    signMail.setError(getString(R.string.invalidForm), errorIcon);
                    successfulSignUp = false;
                } else {//send valid data
                    mailRes = signMailStr;
                    successfulSignUp = true;
                }

                if (!isValidPassword(signPassStr)) {
                    signPass.setError(getString(R.string.passCharLess), errorIcon);
                    successfulSignUp = false;
                } else {//send valid data
                    passRes = signPassStr;
                    successfulSignUp = true;

                }
                if (spinnerItem == "Age") {
                    Toast.makeText(getActivity(), R.string.pickAge, Toast.LENGTH_LONG).show();
                    successfulSignUp = false;
                } else {//send valid data
                    ageRes = spinnerItem;
                    successfulSignUp = true;
                    // Process Data..

                    if (!successfulSignUp) {
                        Toast.makeText(getActivity(), R.string.signUpDataProblem, Toast.LENGTH_LONG)
                                .show();
                    } else {
                        onSignUp(mailRes, passRes);
                        //Check sent data on console
                        Log.i("Result", usernameRes + mailRes + passRes + genderRes + ageRes);
                    }

                }
            }

        });

        return v;


    }

    @Override
    public void onStart() {
        super.onStart();
        nUserAuth.addAuthStateListener(nUserAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        nUserAuth.removeAuthStateListener(nUserAuthListener);
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
        if (!TextUtils.isEmpty(userName) && userName.length() > 2) {
            return true;
        }
        return false;
    }

    // validating email id
    private boolean isValidEmail(String email) {

        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    // validating password with retype password
    private boolean isValidPassword(String pass) {
        if (!TextUtils.isEmpty(pass) && pass.length() > 7) {
            return true;
        }
        return false;
    }

    //Pass Email & Password parameters to firebase for the 1st time..

    private void onSignUp(String mailStr, String passStr) {
        nUserAuth.createUserWithEmailAndPassword(mailStr, passStr)
                .addOnCompleteListener(this.getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //Sign Up..
                        } else {
                            Toast.makeText(getActivity(), R.string.signUpFailed, Toast.LENGTH_LONG)
                                    .show();
                        }
                    }
                });
    }

}




