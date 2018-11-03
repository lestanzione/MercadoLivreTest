package br.com.stanzione.mercadolivretest.data;

import com.google.gson.annotations.SerializedName;

public class CardIssuer {

    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("secure_thumbnail")
    private String secureThumbnail;
    @SerializedName("thumbnail")
    private String thumbnail;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSecureThumbnail() {
        return secureThumbnail;
    }

    public void setSecureThumbnail(String secureThumbnail) {
        this.secureThumbnail = secureThumbnail;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
