package com.budgetemprunt.seydou.petitbidge;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SubscribDialog extends DialogFragment{
    private View mProgressView;
    private View mLoginFormView;


    private UserLoginTask mAuthTask = null;

    private EditText Editlogin;
    private  EditText Editpass;
    private  EditText Editpass2;
    private UserData listner;

    interface UserData{
        void sendUserData(String mail,String pass);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listner = (UserData) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()+ " doit implementé SendCall");
        }
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.subscrib,null,false);

        Editlogin = (EditText)view.findViewById(R.id.mail);
        Editpass = (EditText)view.findViewById(R.id.motdepasse1);
        Editpass2 = (EditText)view.findViewById(R.id.motdepasse2);
        final Button btnIns = view.findViewById(R.id.incrire);
        final Button btnAnnul = view.findViewById(R.id.annule_ins);
        mLoginFormView = view.findViewById(R.id.login_form2);
        mProgressView = view.findViewById(R.id.progress2);

        btnIns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inscrire();
            }
        });

        btnAnnul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        builder.setTitle("Inscription");
        builder.setView(view);
        return builder.create();
    }


    private void inscrire(){
        if (mAuthTask != null) {
            return;
        };

        Editlogin.setError(null);
        Editpass.setError(null);
        Editpass2.setError(null);

        // Store values at the time of the login attempt.
        String email = Editlogin.getText().toString();
        String password = Editpass.getText().toString();
        String confirme = Editpass2.getText().toString();
        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            Editpass.setError(getString(R.string.error_invalid_password));
            focusView = Editpass;
            Log.i("passError","invalidecd");
            cancel = true;
        }

        else  if (!password.equals(confirme)) {
            Editpass2.setError(getString(R.string.error_idem_password));
            focusView = Editpass2;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            Editlogin.setError(getString(R.string.error_field_required));
            focusView = Editlogin;
            Log.i("LoginError","vide");
            cancel = true;
        } else if (!isEmailValid(email)) {
            Editlogin.setError(getString(R.string.error_invalid_email));
            focusView = Editlogin;
            Log.i("LoginError","invalidecd");
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new SubscribDialog.UserLoginTask(email, password);
            mAuthTask.execute((Void) null);
            listner.sendUserData(email,password);
            Intent intent = new Intent(getActivity().getApplicationContext(),MainActivity.class);
            startActivity(intent);
            getActivity().finish();

        }
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2 && isAdded()) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
            Log.i("progress","invalidecd");
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }


    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        final Pattern VALID_EMAIL_ADDRESS_REGEX =
                Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(email);
       return matcher.find();
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }



    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;

        Pattern pattern;
        Matcher matcher;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }
/**
 for (String credential : DUMMY_CREDENTIALS) {
 String[] pieces = credential.split(":");
 if (pieces[0].equals(mEmail)) {
 // Account exists, return true if the password matches.
 return pieces[1].equals(mPassword);
 }
 }
 **/

            // TODO: register the new account here.
            String pattern_pass= "^(?=.*[0-9]).{6}$";
            pattern = pattern.compile(pattern_pass);
            matcher = pattern.matcher(mPassword);
            return matcher.matches();
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);
            if(isAdded()){

                if (success) {
                    dismiss();
                } else {
                    Editpass.setError(getString(R.string.error_incorrect_password));
                    Editpass.requestFocus();
                }}
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }

}
