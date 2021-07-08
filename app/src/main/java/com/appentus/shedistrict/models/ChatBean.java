package com.appentus.shedistrict.models;

import java.util.List;

public class ChatBean extends BaseApiResponse {

    private List<ResultBean> result;

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * room_id : 11
         * new_fri_id : 5
         * fri_name : Admin
         * fri_image :
         * last_message : Hi ti
         * last_message_created : 2020-02-04 05:34:48
         */

        private String room_id;
        private String new_fri_id;
        private String fri_name;
        private String fri_role;
        private String fri_image;
        private String last_message;
        private String sender_id;
        private String receiver_id;
        private String last_message_created;

        public String getFri_role() {
            return fri_role;
        }

        public void setFri_role(String fri_role) {
            this.fri_role = fri_role;
        }

        public String getSender_id() {
            return sender_id;
        }

        public void setSender_id(String sender_id) {
            this.sender_id = sender_id;
        }

        public String getReceiver_id() {
            return receiver_id;
        }

        public void setReceiver_id(String receiver_id) {
            this.receiver_id = receiver_id;
        }

        public String getRoom_id() {
            return room_id;
        }

        public void setRoom_id(String room_id) {
            this.room_id = room_id;
        }

        public String getNew_fri_id() {
            return new_fri_id;
        }

        public void setNew_fri_id(String new_fri_id) {
            this.new_fri_id = new_fri_id;
        }

        public String getFri_name() {
            return fri_name;
        }

        public void setFri_name(String fri_name) {
            this.fri_name = fri_name;
        }

        public String getFri_image() {
            return fri_image;
        }

        public void setFri_image(String fri_image) {
            this.fri_image = fri_image;
        }

        public String getLast_message() {
            return last_message;
        }

        public void setLast_message(String last_message) {
            this.last_message = last_message;
        }

        public String getLast_message_created() {
            return last_message_created;
        }

        public void setLast_message_created(String last_message_created) {
            this.last_message_created = last_message_created;
        }
    }
}
