package com.appentus.shedistrict.models;

import java.util.List;

public class BoostViewBean {


    /**
     * status : success
     * message : boosts_profiles
     * result : [{"user_id":"11","user_profile":"./assets/uploads/779663964568459245f7413e7be3205bimage.jpeg"},{"user_id":"12","user_profile":"./assets/uploads/aece7724b575a919c0c993475c6d7c1bIMG-20200423-WA0008.jpg"}]
     */

    private String status;
    private String message;
    private List<ResultBean> result;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * user_id : 11
         * user_profile : ./assets/uploads/779663964568459245f7413e7be3205bimage.jpeg
         */
        private String user_id;
        private String user_profile;
        private String boosts_active_time;
        private String created;

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public String getBoosts_active_time() {
            return boosts_active_time;
        }

        public void setBoosts_active_time(String boosts_active_time) {
            this.boosts_active_time = boosts_active_time;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getUser_profile() {
            return user_profile;
        }

        public void setUser_profile(String user_profile) {
            this.user_profile = user_profile;
        }
    }
}
