package com.appentus.shedistrict.models;

public class UpdateEventBean {


    /**
     * status : success
     * message : status successfully updated
     * result : {"meeting_id":"16","meeting_user_id":"26","meeting_friend_id":"12","meeting_subject":"Business","meeting_reason":"business talk","meeting_date":"2024/06/06","meeting_time":"04:23:53 PM","meeting_location":"Malviya nager jaipur","created":"2020-05-11 13:08:17","meeting_status":"2"}
     */

    private String status;
    private String message;
    private ResultBean result;

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

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * meeting_id : 16
         * meeting_user_id : 26
         * meeting_friend_id : 12
         * meeting_subject : Business
         * meeting_reason : business talk
         * meeting_date : 2024/06/06
         * meeting_time : 04:23:53 PM
         * meeting_location : Malviya nager jaipur
         * created : 2020-05-11 13:08:17
         * meeting_status : 2
         */

        private String meeting_id;
        private String meeting_user_id;
        private String meeting_friend_id;
        private String meeting_subject;
        private String meeting_reason;
        private String meeting_date;
        private String meeting_time;
        private String meeting_location;
        private String created;
        private String meeting_status;

        public String getMeeting_id() {
            return meeting_id;
        }

        public void setMeeting_id(String meeting_id) {
            this.meeting_id = meeting_id;
        }

        public String getMeeting_user_id() {
            return meeting_user_id;
        }

        public void setMeeting_user_id(String meeting_user_id) {
            this.meeting_user_id = meeting_user_id;
        }

        public String getMeeting_friend_id() {
            return meeting_friend_id;
        }

        public void setMeeting_friend_id(String meeting_friend_id) {
            this.meeting_friend_id = meeting_friend_id;
        }

        public String getMeeting_subject() {
            return meeting_subject;
        }

        public void setMeeting_subject(String meeting_subject) {
            this.meeting_subject = meeting_subject;
        }

        public String getMeeting_reason() {
            return meeting_reason;
        }

        public void setMeeting_reason(String meeting_reason) {
            this.meeting_reason = meeting_reason;
        }

        public String getMeeting_date() {
            return meeting_date;
        }

        public void setMeeting_date(String meeting_date) {
            this.meeting_date = meeting_date;
        }

        public String getMeeting_time() {
            return meeting_time;
        }

        public void setMeeting_time(String meeting_time) {
            this.meeting_time = meeting_time;
        }

        public String getMeeting_location() {
            return meeting_location;
        }

        public void setMeeting_location(String meeting_location) {
            this.meeting_location = meeting_location;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public String getMeeting_status() {
            return meeting_status;
        }

        public void setMeeting_status(String meeting_status) {
            this.meeting_status = meeting_status;
        }
    }
}
