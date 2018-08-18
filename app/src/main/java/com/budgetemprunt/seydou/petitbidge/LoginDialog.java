package com.budgetemprunt.seydou.petitbidge;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class LoginDialog extends Fragment implements MyKeyboard.KeyValue{

    private UserLoginTask mAuthTask = null;
    private View mProgressView;
    private View mLoginFormView;

    OnButtonClickedListener mListener;

    SessionManager session;

    boolean connected = false;

    private AutoCompleteTextView Editlogin;
    private  EditText Editpass;
    private ArgentBD argentBD;
    private ArrayList<User> users;
    String pass1;


    public LoginDialog(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.login_dialog,container,false);

        argentBD = new ArgentBD(getActivity());
        argentBD.open();
        users = (ArrayList<User>) argentBD.getUsers();

        session = new SessionManager(getActivity());

        Editlogin = (AutoCompleteTextView) v.findViewById(R.id.login);
        Editpass = (EditText)v.findViewById(R.id.motdepasse);
        Editlogin.setText("ali@a.fr");
        Editpass.setText("seydou1");
        final Button btnConnect = (Button)v.findViewById(R.id.dlconnect);
        final Button btnAnnul = (Button) v.findViewById(R.id.dlannule);
        btnAnnul.setText("Inscrire");
        mLoginFormView =  v.findViewById(R.id.login_form);
        mProgressView = v.findViewById(R.id.progress1);

        Editpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyKeyboard myKeyboard = new MyKeyboard();
                FragmentManager fm = getFragmentManager();
                myKeyboard.setTargetFragment(LoginDialog.this,300);
                myKeyboard.setCancelable(true);

                myKeyboard.show(fm,"Prêt");
            }
        });

        btnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkInput();
            }
        });

        btnAnnul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onButtonClicked();
            }
        });

        return v;
    }



    private void checkInput(){
        if (mAuthTask != null) {
            return;
        }

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
            Log.i("passError", "invalidecd");
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            Editlogin.setError(getString(R.string.error_field_required));
            focusView = Editlogin;
            Log.i("LoginError", "vide");
            cancel = true;
        } else if (!isEmailValid(email)) {
            Editlogin.setError(getString(R.string.error_invalid_email));
            focusView = Editlogin;
            Log.i("LoginError", "invalidecd");
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
            mAuthTask = new UserLoginTask(email, password);
            mAuthTask.execute((Void) null);
        }
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2 ) {
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

    @Override
    public void SendKeyValue(String keyValue) {
        if(pass1!=null)
        {
            if(keyValue.equals("x")){
                pass1=null;
                Editpass.setText(null);
            }else if(keyValue.equals("C")&&pass1.length()!=0){
                pass1 = pass1.substring(0,pass1.length()-1);
            }else {
                pass1 +=keyValue;
            }
        }

        else {
            if(keyValue.equals("x")){
                pass1=null;
            }else if(keyValue.equals("C")){
                pass1=null;
            }
            else {
                pass1 = keyValue;
            }
        }

        Editpass.setText(pass1);
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

            if (success) {
                session.createLoginSession(mEmail,id);
                Intent i = new Intent(getActivity(),MainActivity.class);
                startActivity(i);
                getActivity().finish();
            } else {
                Editpass.setError(getString(R.string.error_incorrect_password));
                Editpass.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }public interface OnButtonClickedListener{
        public void onButtonClicked();
    }


    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        try{
            mListener = (OnButtonClickedListener)activity;
        }catch(ClassCastException e){
            throw new ClassCastException(activity.toString()+"must implement OnButtonClickListener");
        }
    }


}
