package com.example.bahaaibrahim.tasteria;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginFragment extends Fragment {


    private TextView signUpText;
    private EditText loginMail, loginPassword;
    private Button loginButton;
    private Drawable errorIcon;
    private FirebaseAuth userAuth;
    private FirebaseAuth.AuthStateListener userAuthListener;
    private String mailRes, passwordRes;
    private boolean successfulLogin;


    private signUpFragment mListener;

    public LoginFragment() {
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
        View v = inflater.inflate(R.layout.fragment_login, container, false);

        //Prevent keyboard from automatic popping up once onCreate fragment..
        this.getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        //Establishing Connection with firebase..
        userAuth = FirebaseAuth.getInstance();

        userAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    startActivity(new Intent(getActivity(), MainActivity.class));
                } else {// Action to be taken..
                }
            }
        };


        //Go back to log in fragment on Textview click..
        signUpText = (TextView) v.findViewById(R.id.signUpText);
        signUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUpFragment signUpFragment = new signUpFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.slide_in_right, R.anim.slide_out_left);
                fragmentTransaction.replace(android.R.id.content, signUpFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();


            }
        });
        loginMail = (EditText) v.findViewById(R.id.loginMail);
        loginPassword = (EditText) v.findViewById(R.id.loginPassword);


        loginButton = (Button) v.findViewById(R.id.login);
        errorIcon = (Drawable) ContextCompat.getDrawable(this.getActivity(), R.drawable.ic_error);

        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                successfulLogin = false;
                final String loginMailStr = loginMail.getText().toString();
                final String loginPasswordStr = loginPassword.getText().toString();
                errorIcon.setBounds(0, 0, errorIcon.getIntrinsicWidth(), errorIcon.getIntrinsicHeight());


                if (!isValidEmail(loginMailStr)) {
                    loginMail.setError(getString(R.string.invalidMail), errorIcon);
                    successfulLogin = false;

                } else {//send valid data
                    successfulLogin = true;
                    mailRes = loginMailStr;
                }

                if (!isValidPassword(loginPasswordStr)) {
                    successfulLogin = false;
                    loginPassword.setError(getString(R.string.passCharLess), errorIcon);
                } else {//Send valid Data
                    successfulLogin = true;
                    passwordRes = loginPasswordStr;


                }
                if (!successfulLogin) {
                    Toast.makeText(getActivity(), R.string.loginDataProblem, Toast.LENGTH_LONG).show();
                } else {
                    onLogIn(mailRes, passwordRes);
                }


            }
        });


        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        userAuth.addAuthStateListener(userAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        userAuth.removeAuthStateListener(userAuthListener);
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

    private void onLogIn(String mailStr, String passwordStr) {
        userAuth.signInWithEmailAndPassword(mailStr, passwordStr)
                .addOnCompleteListener(this.getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser = userAuth.getCurrentUser();

                            //Log in..
                        } else {
                            Toast.makeText(getActivity(), R.string.logInFailed, Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}


