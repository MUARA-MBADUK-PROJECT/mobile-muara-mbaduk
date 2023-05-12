package com.example.muara_mbaduk.data.model.response;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDateTime;

public class Tiket {
    @SerializedName("code")
    private int code;

    @SerializedName("status")
    private String status;

    @SerializedName("data")
    private Data data;

    public int getCode() {
        return code;
    }

    public String getStatus() {
        return status;
    }

    public Data getData() {
        return data;
    }

    public class Data {
        @SerializedName("id")
        private String id;

        @SerializedName("title")
        private String title;

        @SerializedName("category")
        private String category;

        @SerializedName("normal_day")
        private int normalDay;

        @SerializedName("weekend_day")
        private int weekendDay;

        @SerializedName("created_at")
        private String createdAt;

        @SerializedName("updated_at")
        private String updatedAt;

        public String getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public String getCategory() {
            return category;
        }

        public int getNormalDay() {
            return normalDay;
        }

        public int getWeekendDay() {
            return weekendDay;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }
    }
}