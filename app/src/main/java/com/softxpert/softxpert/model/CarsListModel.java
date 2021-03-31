package com.softxpert.softxpert.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CarsListModel {

    @SerializedName("status")
    @Expose
    private Integer status;

    @SerializedName("data")
    @Expose
    private List<Datum> data = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }
    public static class Datum {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("brand")
        @Expose
        private String brand;
        @SerializedName("constractionYear")
        @Expose
        private String constractionYear;
        @SerializedName("isUsed")
        @Expose
        private Boolean isUsed;
        @SerializedName("imageUrl")
        @Expose
        private String imageUrl;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public String getConstractionYear() {
            return constractionYear;
        }

        public void setConstractionYear(String constractionYear) {
            this.constractionYear = constractionYear;
        }

        public Boolean getIsUsed() {
            return isUsed;
        }

        public void setIsUsed(Boolean isUsed) {
            this.isUsed = isUsed;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }
    }
}