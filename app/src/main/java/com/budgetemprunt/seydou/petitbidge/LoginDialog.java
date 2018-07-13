package com.budgetemprunt.seydou.petitbidge;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class LoginDialog extends DialogFragment {

    private UserLoginTask mAuthTask = null;
    private View mProgressView;
    private View mLoginFormView;


    boolean connected = false;

    private AutoCompleteTextView Editlogin;
    private  EditText Editpass;
    private SendCall listner;
    private ArgentBD argentBD;
    private ArrayList<User> users;
    int user_id;

    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "seydou:hello", "sali:world"
    };


    interface SendCall{
        void sendInfo(int id,String login,String pass,boolean connected);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listner = (SendCall) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()+ " doit implementé SendCall");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        argentBD = new ArgentBD(getContext());
        argentBD.open();
        users = (ArrayList<User>)argentBD.getUsers();

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.login_dialog,null,false);
        Editlogin = (AutoCompleteTextView)view.findViewById(R.id.login);
        Editpass = (EditText)view.findViewById(R.id.motdepasse);
        Editlogin.setText("ali@a.fr");
        Editpass.setText("seydou1");
        final Button btnConnect = view.findViewById(R.id.dlconnect);
        final Button btnAnnul = view.findViewById(R.id.dlannule);
        mLoginFormView = view.findViewById(R.id.login_form);
        mProgressView = view.findViewById(R.id.progress1);

        btnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAuthTask != null) {
                    return;
                };

                Editlogin.setError(null);
                Editpass.setError(null);

                // Store values at the time of the login attempt.
                String email = Editlogin.getText().toString();
                String password = Editpass.getText().toString();

                boolean cancel = false;
                View focusView = null;

                // Check for a valid password, if the user entered one.
                if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
                    Editpass.setError(getString(R.string.error_invalid_password));
                    focusView = Editpass;
                    Log.i("passError","invalidecd");
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
                    mAuthTask = new LoginDialog.UserLoginTask(email, password);
                    mAuthTask.execute((Void) null);
                }
            }
        });

        btnAnnul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Connexion")
                .setView(view);

        return builder.create();
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
        return email.length()>3;
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }




    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;
        int id;




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

            if(mEmail.equals("admin@admin.com")){
                connected = false;
                return mPassword.equals("admin1");
            }else{
                for (User user : users) {

                    if (user.getMail().equals(mEmail)&&user.getPass().equals(mPassword)) {
                        // Account exists, return true if the password matches.
                        id=user.getId();
                        return true;
                    }
                }
            }

            // TODO: register the new account here.
            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);
            if(isAdded()){

            if (success) {
                user_id = id;
                listner.sendInfo(id,mEmail,mPassword,true);

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
