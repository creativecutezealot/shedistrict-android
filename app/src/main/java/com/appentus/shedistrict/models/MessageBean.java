package com.appentus.shedistrict.models;

import java.util.List;

public class MessageBean extends BaseApiResponse {
    /**
     * meeting_status : 0
     * result : [{"chat_id":"2","chat_room_id":"2","chat_message":"hiii","chat_file":"","chat_file_type":"0","created":"2020-05-08 12:29:35","user_id":"12","fri_id":"27","room_id":"2","block_status":"0","first_received":"27","first_send":"12","userprofile":"./assets/uploads/aece7724b575a919c0c993475c6d7c1bIMG-20200423-WA0008.jpg","friendprofile":"./assets/uploads/5cc6701bb521f72d4b4bc4f243cab41a20200420_182914.jpg"},{"chat_id":"3","chat_room_id":"2","chat_message":"hiii","chat_file":"","chat_file_type":"0","created":"2020-05-08 12:29:36","user_id":"12","fri_id":"27","room_id":"2","block_status":"0","first_received":"27","first_send":"12","userprofile":"./assets/uploads/aece7724b575a919c0c993475c6d7c1bIMG-20200423-WA0008.jpg","friendprofile":"./assets/uploads/5cc6701bb521f72d4b4bc4f243cab41a20200420_182914.jpg"},{"chat_id":"4","chat_room_id":"2","chat_message":"hiii","chat_file":"","chat_file_type":"0","created":"2020-05-08 12:29:54","user_id":"12","fri_id":"27","room_id":"2","block_status":"0","first_received":"27","first_send":"12","userprofile":"./assets/uploads/aece7724b575a919c0c993475c6d7c1bIMG-20200423-WA0008.jpg","friendprofile":"./assets/uploads/5cc6701bb521f72d4b4bc4f243cab41a20200420_182914.jpg"}]
     */

    private String meeting_status;
    private List<ResultBean> result;

    public String getMeeting_status() {
        return meeting_status;
    }

    public void setMeeting_status(String meeting_status) {
        this.meeting_status = meeting_status;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * chat_id : 2
         * chat_room_id : 2
         * chat_message : hiii
         * chat_file :
         * chat_file_type : 0
         * created : 2020-05-08 12:29:35
         * user_id : 12
         * fri_id : 27
         * room_id : 2
         * block_status : 0
         * first_received : 27
         * first_send : 12
         * userprofile : ./assets/uploads/aece7724b575a919c0c993475c6d7c1bIMG-20200423-WA0008.jpg
         * friendprofile : ./assets/uploads/5cc6701bb521f72d4b4bc4f243cab41a20200420_182914.jpg
         */

        private String role;
        private String chat_id;
        private String chat_room_id;
        private String chat_message;
        private String chat_file;
        private String chat_file_type;
        private String created;
        private String user_id;
        private String fri_id;
        private String room_id;
        private String block_status;
        private String first_received;
        private String first_send;
        private String userprofile;
        private String friendprofile;

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getChat_id() {
            return chat_id;
        }

        public void setChat_id(String chat_id) {
            this.chat_id = chat_id;
        }

        public String getChat_room_id() {
            return chat_room_id;
        }

        public void setChat_room_id(String chat_room_id) {
            this.chat_room_id = chat_room_id;
        }

        public String getChat_message() {
            return chat_message;
        }

        public void setChat_message(String chat_message) {
            this.chat_message = chat_message;
        }

        public String getChat_file() {
            return chat_file;
        }

        public void setChat_file(String chat_file) {
            this.chat_file = chat_file;
        }

        public String getChat_file_type() {
            return chat_file_type;
        }

        public void setChat_file_type(String chat_file_type) {
            this.chat_file_type = chat_file_type;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getFri_id() {
            return fri_id;
        }

        public void setFri_id(String fri_id) {
            this.fri_id = fri_id;
        }

        public String getRoom_id() {
            return room_id;
        }

        public void setRoom_id(String room_id) {
            this.room_id = room_id;
        }

        public String getBlock_status() {
            return block_status;
        }

        public void setBlock_status(String block_status) {
            this.block_status = block_status;
        }

        public String getFirst_received() {
            return first_received;
        }

        public void setFirst_received(String first_received) {
            this.first_received = first_received;
        }

        public String getFirst_send() {
            return first_send;
        }

        public void setFirst_send(String first_send) {
            this.first_send = first_send;
        }

        public String getUserprofile() {
            return userprofile;
        }

        public void setUserprofile(String userprofile) {
            this.userprofile = userprofile;
        }

        public String getFriendprofile() {
            return friendprofile;
        }

        public void setFriendprofile(String friendprofile) {
            this.friendprofile = friendprofile;
        }
    }



  /*  private List<ResultBean> result;

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        *//**
         * chat_id : 14
         * chat_room_id : 3
         * chat_message : Hi
         * chat_file :
         * chat_file_type : 0
         * created : 2020-03-06 05:10:07
         * user_id :
         * fri_id :
         * room_id : 3
         * block_status : 0
         * userprofile : null
         * friendprofile :
         *//*
        private String chat_id;
        private String chat_room_id;
        private String chat_message;
        private String chat_file;
        private String chat_file_type;
        private String created;
        private String user_id;
        private String fri_id;
        private String room_id;
        private String block_status;
        private Object userprofile;
        private String friendprofile;
        private String first_received;
        private String first_send;

        public String getFirst_received() {
            return first_received;
        }

        public void setFirst_received(String first_received) {
            this.first_received = first_received;
        }

        public String getFirst_send() {
            return first_send;
        }

        public void setFirst_send(String first_send) {
            this.first_send = first_send;
        }

        public String getChat_id() {
            return chat_id;
        }

        public void setChat_id(String chat_id) {
            this.chat_id = chat_id;
        }

        public String getChat_room_id() {
            return chat_room_id;
        }

        public void setChat_room_id(String chat_room_id) {
            this.chat_room_id = chat_room_id;
        }

        public String getChat_message() {
            return chat_message;
        }

        public void setChat_message(String chat_message) {
            this.chat_message = chat_message;
        }

        public String getChat_file() {
            return chat_file;
        }

        public void setChat_file(String chat_file) {
            this.chat_file = chat_file;
        }

        public String getChat_file_type() {
            return chat_file_type;
        }

        public void setChat_file_type(String chat_file_type) {
            this.chat_file_type = chat_file_type;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getFri_id() {
            return fri_id;
        }

        public void setFri_id(String fri_id) {
            this.fri_id = fri_id;
        }

        public String getRoom_id() {
            return room_id;
        }

        public void setRoom_id(String room_id) {
            this.room_id = room_id;
        }

        public String getBlock_status() {
            return block_status;
        }

        public void setBlock_status(String block_status) {
            this.block_status = block_status;
        }

        public Object getUserprofile() {
            return userprofile;
        }

        public void setUserprofile(Object userprofile) {
            this.userprofile = userprofile;
        }

        public String getFriendprofile() {
            return friendprofile;
        }

        public void setFriendprofile(String friendprofile) {
            this.friendprofile = friendprofile;
        }
    }*/
}
