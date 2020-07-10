package com.example.ebuyzone;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ebuyzone.model.Login;
import com.example.ebuyzone.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ProfileLogin extends Fragment  implements View.OnClickListener {
    private ProgressDialog progressDialog;

    EditText Edreg_username;
    EditText Edreg_password;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {



        View rootView = inflater.inflate(R.layout.profile_login, container, false);


        Button logBtn = (Button) rootView.findViewById(R.id.login_button);



        Edreg_username = (EditText) rootView.findViewById(R.id.reg_username);
        Edreg_password = (EditText) rootView.findViewById(R.id.reg_password);

        progressDialog = new ProgressDialog(getActivity());

        logBtn.setOnClickListener(this);


        return rootView;


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_button:
                LogButtonClick();
                break;
        }
    }



    public void replaceFragment(Fragment someFragment) {

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }




    public void LogButtonClick()
    {

        if (!IsEmptyEditTextLogin()){

            if ( InternetUtil.isInternetOnline(getActivity()) ){
                login();
            }

        }



    }


    private void login(){
        progressDialog.setMessage("Loging in Progress");
        progressDialog.show();

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(PostApi.Base)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();

        PostApi postApi = retrofit.create(PostApi.class);




        String add1      =  Edreg_username.getText().toString();
        String add2      =  Edreg_password.getText().toString();

        SharedPreferences preferences = getActivity().getSharedPreferences("myPrefs1", Context.MODE_PRIVATE);
        SharedPreferences.Editor prefLoginEdit = preferences.edit();
        prefLoginEdit.putString("username", add1);
        prefLoginEdit.commit();


        Login login = new Login(add1, add2);

        Call<User> call = postApi.login(login);
        call.enqueue(new Callback<User>() {

            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    progressDialog.dismiss();

                    if (response.body() != null) {

                        String token = response.body().getToken();


                        SharedPreferences preferences = getActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
                        SharedPreferences.Editor prefLoginEdit = preferences.edit();
                        prefLoginEdit.putBoolean("loggedin", true);
                        prefLoginEdit.putString("token", token);
                        prefLoginEdit.commit();



                        Toast.makeText(getContext(), "Login Successfull", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getActivity(),NavigationActivity.class));

//                        Fragment fragment = null;
//                        fragment = new Home();
//                        replaceFragment(fragment);

                    }

                }else {
                    Toast.makeText(getContext(), "login no correct :(", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getActivity(), "error :(", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }



    private Boolean IsEmptyEditTextLogin(){



        if(Edreg_password.getText().toString().isEmpty() || Edreg_username.getText().toString().isEmpty()){

            Toast toast = Toast.makeText(getActivity(),"Empty EditText", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();


            return true;
        }else{
            return false;
        }

    }







}