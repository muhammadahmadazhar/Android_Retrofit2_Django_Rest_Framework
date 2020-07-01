package com.example.ebuyzone;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ebuyzone.model.PostModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ShowPost extends Fragment {

    TextView v_sh_title;
    TextView v_sh_text;


    Integer v_sh_str_id;

    PostModel addDeliveredStatus;
    PostModel addCancelledStatus;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_show_post, container, false);

        v_sh_title = (TextView) rootView.findViewById(R.id.vshow_title);
        v_sh_text = (TextView) rootView.findViewById(R.id.vshow_text);

        Button orderDeliver = (Button) rootView.findViewById(R.id.order_deliver);
        orderDeliver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( InternetUtil.isInternetOnline(getActivity()) ){
                    AddPostServer(addDeliveredStatus);
                }

//                if ( InternetUtil.isInternetOnline(getActivity()) ){
//                    deletePost();
//                }
            }
        });

        Button orderCancel = (Button) rootView.findViewById(R.id.order_cancelled);
        orderCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( InternetUtil.isInternetOnline(getActivity()) ){
                    AddPostServer(addCancelledStatus);
                }

//                if ( InternetUtil.isInternetOnline(getActivity()) ){
//                    deletePost();
//                }
            }
        });

        String statusDelivered = "Order Delivered";
        String statusCancelled = "Order Cancelled";

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            Integer bundle_id = bundle.getInt("post_id");
            GetServerData(bundle_id);
            addDeliveredStatus = new PostModel(bundle_id, statusDelivered);
            addCancelledStatus = new PostModel(bundle_id, statusCancelled);

        }


        getActivity().setTitle("Order Details");


        return rootView;

    }


    private void GetServerData(Integer getted_id) {


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PostApi.Base)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        String data = String.valueOf(getted_id);

        PostApi postApi = retrofit.create(PostApi.class);

        Call<PostModel> call = postApi.getPost(data);

        call.enqueue(new Callback<PostModel>() {
            @Override
            public void onResponse(Call<PostModel> call, Response<PostModel> response) {

                if (response.isSuccessful()) {

                    if (response.body() != null) {

                        PostModel postValues = response.body();

                        String v_sh_str_title = postValues.getDetails();
                        String v_sh_str_text = postValues.getText();
                         v_sh_str_id = postValues.getId();

                        String temp= String.valueOf(v_sh_str_id);
                        String idName= temp +": " + v_sh_str_text;

                        v_sh_title.setText(v_sh_str_title);
                        v_sh_text.setText(idName);



                    }

                } else {
                    Log.d("fail", "fail");
                }

            }

            @Override
            public void onFailure(Call<PostModel> call, Throwable t) {
                Log.d("fail", t.getMessage() == null ? "" : t.getMessage());
            }
        });

    }


    public void AddPostServer(PostModel addDeliveredStatus) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PostApi.Base)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        String queryToken_ap = SharedDataGetSet.getMySavedToken(getActivity());


        PostApi postApi= retrofit.create(PostApi.class);
        Call<PostModel> call = postApi.addPost(queryToken_ap, addDeliveredStatus);

        call.enqueue(new Callback<PostModel>() {
            @Override
            public void onResponse(Call<PostModel> call, Response<PostModel> response) {
                Log.d("Order", "Delivered");
            }
            @Override
            public void onFailure(Call<PostModel> call, Throwable t) {
                Log.d("fail", "fail");
            }
        });

    }

    private void deletePost() {

        String queryToken_ap = SharedDataGetSet.getMySavedToken(getActivity());


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PostApi.Base)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        PostApi postApi = retrofit.create(PostApi.class);

        String vstr_id_posta = String.valueOf(v_sh_str_id);

        Call<List<PostModel>> call = postApi.deletePost(queryToken_ap, vstr_id_posta);


        call.enqueue(new Callback<List<PostModel>>() {
            @Override
            public void onResponse(Call<List<PostModel>> call, Response<List<PostModel>> response) {
                Log.d("good", "good");
            }

            @Override
            public void onFailure(Call<List<PostModel>> call, Throwable t) {
                Log.d("fail", t.getMessage() == null ? "" : t.getMessage());
            }
        });

    }
}
