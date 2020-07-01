package com.example.ebuyzone;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import com.example.ebuyzone.model.PostModel;
import com.example.ebuyzone.model.Photo;
import com.example.ebuyzone.model.Profile;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ShowProfile extends Fragment {
    TextView tv_username;
    TextView tv_email;
    TextView tv_first_name;
    TextView tv_last_name;
    String data="";

    EditText picAddress;

    public static Integer static_id;
    CircleImageView Profile_imageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_show_profile, container, false);

        tv_username = (TextView) rootView.findViewById(R.id.profile_username);
        tv_email = (TextView) rootView.findViewById(R.id.profile_email);
        tv_first_name = (TextView) rootView.findViewById(R.id.profile_first_name);
        tv_last_name = (TextView) rootView.findViewById(R.id.profile_last_name);


           String user = SharedDataGetSet.getMySavedUsername(getActivity());
            GetServerData(user);



//        }
        Profile_imageView = (CircleImageView) rootView.findViewById(R.id.profile_img);
//        ImageView imageView = (ImageView) rootView.findViewById(R.id.profile_img);
//        String url = "https://cdn.pixabay.com/photo/2020/04/19/09/16/scenery-5062632_960_720.jpg";
//        String url1 = "http://192.168.10.2:8000/media/blog/Profile/ahmad.jpg";
//        Picasso.get().load(url1).into(Profile_imageView);

        getProfilePhoto();

        getActivity().setTitle("Profile");

        return rootView;
    }

    private void GetServerData(String user) {


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PostApi.Base)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        PostApi postApi = retrofit.create(PostApi.class);

        Call<Profile> call = postApi.getProfile(user);

        call.enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {

                if (response.isSuccessful()) {

                    if (response.body() != null) {

                        Profile postValues = response.body();

                        String v_sh_str_username   =  postValues.getUsername();
                        String v_sh_str_email      =  postValues.getEmail();
                        String v_sh_str_first_name =  postValues.getFirst_name();
                        String v_sh_str_last_name  =  postValues.getLast_name();

                        tv_username.setText(v_sh_str_username);
                        tv_email.setText(v_sh_str_email);
                        tv_first_name.setText(v_sh_str_first_name);
                        tv_last_name.setText(v_sh_str_last_name);

                    }

                } else {
                    Log.d("fail", "fail");
                }

            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {
                Log.d("fail", t.getMessage() == null ? "" : t.getMessage());
            }
        });

    }

    private void getProfilePhoto() {
        String queryToken_ap = SharedDataGetSet.getMySavedToken(getActivity());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PostApi.img)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

//        String data = String.valueOf(getted_id);

        PostApi postApi = retrofit.create(PostApi.class);

        Call<Photo> call = postApi.getPhoto(queryToken_ap);

        call.enqueue(new Callback<Photo>() {
            @Override
            public void onResponse(Call<Photo> call, Response<Photo> response) {

                if (response.isSuccessful()) {

                    if (response.body() != null) {

                        Photo postValues = response.body();


                        Integer str_id = postValues.getId();
                        static_id = str_id;

                        Log.d("static_id", String.valueOf(str_id));
                        String PicUrl = postValues.getPhoto();

                        Picasso.get().load(PicUrl).into(Profile_imageView);

                    }

                } else {
                    Log.d("fail", "fail");
                }

            }

            @Override
            public void onFailure(Call<Photo> call, Throwable t) {
                Log.d("fail", t.getMessage() == null ? "" : t.getMessage());
            }
        });

    }



}
