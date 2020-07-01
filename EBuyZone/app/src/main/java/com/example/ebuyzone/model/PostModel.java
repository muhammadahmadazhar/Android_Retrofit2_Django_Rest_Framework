package com.example.ebuyzone.model;


import com.google.gson.annotations.SerializedName;

public class PostModel {

    @SerializedName("order_id")
    private Integer id;
    @SerializedName("address")
    private String title;
    @SerializedName("name")
    private String text;
    @SerializedName("items_details")
    private String details;

    private String update_desc;

    public PostModel(Integer id, String update_desc) {
        this.id = id;
        this.update_desc = update_desc;
    }

    public int getId(){
        return id;
    }
    public String getTitle(){
        return title;
    }

    public String getText(){
        return text;
    }
    public String getDetails() {
        return details;
    }

}