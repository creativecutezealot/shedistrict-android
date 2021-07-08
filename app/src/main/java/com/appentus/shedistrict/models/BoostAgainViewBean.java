package com.appentus.shedistrict.models;

import java.util.List;

public class BoostAgainViewBean {


    /**
     * status : success
     * message : Profile Boosts Again
     * result : [{"user_id":"11","user_profile":"./assets/uploads/779663964568459245f7413e7be3205bimage.jpeg","boosts_active_time":"1590502559","created":"2020-05-29 13:04:57","id":"1"}]
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
         * boosts_active_time : 1590502559
         * created : 2020-05-29 13:04:57
         * id : 1
         */

        private String user_id;
        private String user_profile;
        private String boosts_active_time;
        private String created;
        private String id;

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

        public String getBoosts_active_time() {
            return boosts_active_time;
        }

        public void setBoosts_active_time(String boosts_active_time) {
            this.boosts_active_time = boosts_active_time;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
