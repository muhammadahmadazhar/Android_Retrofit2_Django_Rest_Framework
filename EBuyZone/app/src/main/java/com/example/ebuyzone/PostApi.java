package com.example.ebuyzone;


import com.example.ebuyzone.model.Login;
import com.example.ebuyzone.model.Photo;
import com.example.ebuyzone.model.PostModel;
import com.example.ebuyzone.model.Profile;
import com.example.ebuyzone.model.User;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface PostApi {


    String root = "http://192.168.10.2:8000/";
//    String root = "http://127.0.0.1:8000/";


    String base_local = root + "api/v1/";
    String BASE_URL = base_local + "account/";
    String API_URL = root + "api/v1/";




    @POST("api-token-auth/")
    Call<User> login(@Body Login login);

//    @GET("post/list/")
//    Call<List<PostModel>> getListPost();
//
//    @GET("post/{order_id}/")
//    Call<PostModel> getPost(@Path(value = "order_id", encoded = true) String id);
//
//    @DELETE("post/delete/{order_id}/")
//    Call<List<PostModel>> deletePost(@Header("Authorization")  String authToken, @Path(value = "order_id", encoded = true) String id);


    String Base = root + "android/";
    String img = root + "image/";

    @GET("orders/{order_id}/")
    Call<PostModel> getPost(@Path(value = "order_id", encoded = true) String id);

    @GET("orders/")
    Call<List<PostModel>> getListPost();

    @POST("order_status/")
    Call<PostModel> addPost(@Header("Authorization")  String authToken, @Body PostModel postModel);

    @DELETE("orders/delete/{order_id}/")
    Call<List<PostModel>> deletePost(@Header("Authorization")  String authToken, @Path(value = "order_id", encoded = true) String id);

    @GET("profile/{username}/")
    Call<Profile> getProfile(@Path(value = "username", encoded = true) String name);

    @GET("showPhoto/")
    Call<Photo> getPhoto(@Header("Authorization")  String authToken);

    @Multipart
    @PUT("editPhoto/")
    Call<RequestBody>  uploadFile(@Part MultipartBody.Part file);
}