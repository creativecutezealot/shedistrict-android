package com.appentus.shedistrict.models;

public class SendMessageBean {
    /**
     * status : success
     * message : Message sent
     * result : {"chat_id":"241","chat_room_id":"39","chat_message":"nsdjkha","chat_file":"","chat_file_type":"0","created":"2020-03-02 04:08:46","room_id":"39","fri_id":"7","user_id":"15","block_status":"0"}
     * data : {"user_id":"15","user_profile":"./assets/uploads/c20af2b9c83e16fcd4ebc9447bc867d8IMG-20200224-WA0000.jpg","user_name":"arti","user_email":"arti7722@gmail.com","user_password":"123456","user_country_code":"","user_mobile":"","user_dob":"","user_login_type":"","user_social":"","user_device_type":"1","user_device_token":"cI-8H3cpxoY:APA91bEu97UvUk-4mbdgIQJKiiWK54eY7mI4y4vbg0LElxg462zkpa3r1cKTHHjFUut6b4LObYQ2vVI8RHc9weTN7cQ6MRiQhmuGqit8NX0bwlmOsGZ7wtAhI2C65yrS9Su8JROFXNPF","user_lat":"26.8570676","user_lang":"75.8245242","user_status":"1","created":"2020-02-27 23:02:17","role":"0","about_me":"","friend_like":"","describe_me":"","hobbies":"","interest_info":"","personal_info":"{\"Age\":\"\",\"Nationality\":\"\",\"Ethnicity\":\"\",\"Religion\":\"\",\"Distance\":\"\",\"Education\":\"\",\"Relationship_Status\":\"\",\"Sexual_Orientation\":\"\",\"Has_Kids?\":\"\",\"Drinking_Habits\":\"\",\"Smoking_Habits\":\"\",\"Political_Affiliation\":\"\"}","allow_msg":"{\"1\":\"0\",\"2\":\"1\",\"3\":\"0\"}","premium_data":"{\"1\":\"1\",\"2\":\"1\"}","hide_profile":"1","hide_activity":"1","stop_invite":"1","hide_location":"1","hide_age":"0","user_deactive":"1","push_status":"0","push_setting":"{\"1\":\"1\",\"2\":\"1\",\"3\":\"1\",\"4\":\"0\",\"5\":\"1\",\"6\":\"1\",\"7\":\"0\",\"8\":\"0\",\"9\":\"1\"}"}
     */

    private String status;
    private String message;
    private ResultBean result;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class ResultBean {
        /**
         * chat_id : 241
         * chat_room_id : 39
         * chat_message : nsdjkha
         * chat_file :
         * chat_file_type : 0
         * created : 2020-03-02 04:08:46
         * room_id : 39
         * fri_id : 7
         * user_id : 15
         * block_status : 0
         */

        private String chat_id;
        private String chat_room_id;
        private String chat_message;
        private String chat_file;
        private String chat_file_type;
        private String created;
        private String room_id;
        private String fri_id;
        private String user_id;
        private String block_status;
        private String friendprofile;
        private String sender_id;
        private String received_id;

        public String getSender_id() {
            return sender_id;
        }

        public void setSender_id(String sender_id) {
            this.sender_id = sender_id;
        }

        public String getReceived_id() {
            return received_id;
        }

        public void setReceived_id(String received_id) {
            this.received_id = received_id;
        }

        public String getFriendprofile() {
            return friendprofile;
        }

        public void setFriendprofile(String friendprofile) {
            this.friendprofile = friendprofile;
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

        public String getRoom_id() {
            return room_id;
        }

        public void setRoom_id(String room_id) {
            this.room_id = room_id;
        }

        public String getFri_id() {
            return fri_id;
        }

        public void setFri_id(String fri_id) {
            this.fri_id = fri_id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getBlock_status() {
            return block_status;
        }

        public void setBlock_status(String block_status) {
            this.block_status = block_status;
        }
    }

    public static class DataBean {
        /**
         * user_id : 15
         * user_profile : ./assets/uploads/c20af2b9c83e16fcd4ebc9447bc867d8IMG-20200224-WA0000.jpg
         * user_name : arti
         * user_email : arti7722@gmail.com
         * user_password : 123456
         * user_country_code :
         * user_mobile :
         * user_dob :
         * user_login_type :
         * user_social :
         * user_device_type : 1
         * user_device_token : cI-8H3cpxoY:APA91bEu97UvUk-4mbdgIQJKiiWK54eY7mI4y4vbg0LElxg462zkpa3r1cKTHHjFUut6b4LObYQ2vVI8RHc9weTN7cQ6MRiQhmuGqit8NX0bwlmOsGZ7wtAhI2C65yrS9Su8JROFXNPF
         * user_lat : 26.8570676
         * user_lang : 75.8245242
         * user_status : 1
         * created : 2020-02-27 23:02:17
         * role : 0
         * about_me :
         * friend_like :
         * describe_me :
         * hobbies :
         * interest_info :
         * personal_info : {"Age":"","Nationality":"","Ethnicity":"","Religion":"","Distance":"","Education":"","Relationship_Status":"","Sexual_Orientation":"","Has_Kids?":"","Drinking_Habits":"","Smoking_Habits":"","Political_Affiliation":""}
         * allow_msg : {"1":"0","2":"1","3":"0"}
         * premium_data : {"1":"1","2":"1"}
         * hide_profile : 1
         * hide_activity : 1
         * stop_invite : 1
         * hide_location : 1
         * hide_age : 0
         * user_deactive : 1
         * push_status : 0
         * push_setting : {"1":"1","2":"1","3":"1","4":"0","5":"1","6":"1","7":"0","8":"0","9":"1"}
         */

        private String user_id;
        private String user_profile;
        private String user_name;
        private String user_email;
        private String user_password;
        private String user_country_code;
        private String user_mobile;
        private String user_dob;
        private String user_login_type;
        private String user_social;
        private String user_device_type;
        private String user_device_token;
        private String user_lat;
        private String user_lang;
        private String user_status;
        private String created;
        private String role;
        private String about_me;
        private String friend_like;
        private String describe_me;
        private String hobbies;
        private String interest_info;
        private String personal_info;
        private String allow_msg;
        private String premium_data;
        private String hide_profile;
        private String hide_activity;
        private String stop_invite;
        private String hide_location;
        private String hide_age;
        private String user_deactive;
        private String push_status;
        private String push_setting;

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

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getUser_email() {
            return user_email;
        }

        public void setUser_email(String user_email) {
            this.user_email = user_email;
        }

        public String getUser_password() {
            return user_password;
        }

        public void setUser_password(String user_password) {
            this.user_password = user_password;
        }

        public String getUser_country_code() {
            return user_country_code;
        }

        public void setUser_country_code(String user_country_code) {
            this.user_country_code = user_country_code;
        }

        public String getUser_mobile() {
            return user_mobile;
        }

        public void setUser_mobile(String user_mobile) {
            this.user_mobile = user_mobile;
        }

        public String getUser_dob() {
            return user_dob;
        }

        public void setUser_dob(String user_dob) {
            this.user_dob = user_dob;
        }

        public String getUser_login_type() {
            return user_login_type;
        }

        public void setUser_login_type(String user_login_type) {
            this.user_login_type = user_login_type;
        }

        public String getUser_social() {
            return user_social;
        }

        public void setUser_social(String user_social) {
            this.user_social = user_social;
        }

        public String getUser_device_type() {
            return user_device_type;
        }

        public void setUser_device_type(String user_device_type) {
            this.user_device_type = user_device_type;
        }

        public String getUser_device_token() {
            return user_device_token;
        }

        public void setUser_device_token(String user_device_token) {
            this.user_device_token = user_device_token;
        }

        public String getUser_lat() {
            return user_lat;
        }

        public void setUser_lat(String user_lat) {
            this.user_lat = user_lat;
        }

        public String getUser_lang() {
            return user_lang;
        }

        public void setUser_lang(String user_lang) {
            this.user_lang = user_lang;
        }

        public String getUser_status() {
            return user_status;
        }

        public void setUser_status(String user_status) {
            this.user_status = user_status;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getAbout_me() {
            return about_me;
        }

        public void setAbout_me(String about_me) {
            this.about_me = about_me;
        }

        public String getFriend_like() {
            return friend_like;
        }

        public void setFriend_like(String friend_like) {
            this.friend_like = friend_like;
        }

        public String getDescribe_me() {
            return describe_me;
        }

        public void setDescribe_me(String describe_me) {
            this.describe_me = describe_me;
        }

        public String getHobbies() {
            return hobbies;
        }

        public void setHobbies(String hobbies) {
            this.hobbies = hobbies;
        }

        public String getInterest_info() {
            return interest_info;
        }

        public void setInterest_info(String interest_info) {
            this.interest_info = interest_info;
        }

        public String getPersonal_info() {
            return personal_info;
        }

        public void setPersonal_info(String personal_info) {
            this.personal_info = personal_info;
        }

        public String getAllow_msg() {
            return allow_msg;
        }

        public void setAllow_msg(String allow_msg) {
            this.allow_msg = allow_msg;
        }

        public String getPremium_data() {
            return premium_data;
        }

        public void setPremium_data(String premium_data) {
            this.premium_data = premium_data;
        }

        public String getHide_profile() {
            return hide_profile;
        }

        public void setHide_profile(String hide_profile) {
            this.hide_profile = hide_profile;
        }

        public String getHide_activity() {
            return hide_activity;
        }

        public void setHide_activity(String hide_activity) {
            this.hide_activity = hide_activity;
        }

        public String getStop_invite() {
            return stop_invite;
        }

        public void setStop_invite(String stop_invite) {
            this.stop_invite = stop_invite;
        }

        public String getHide_location() {
            return hide_location;
        }

        public void setHide_location(String hide_location) {
            this.hide_location = hide_location;
        }

        public String getHide_age() {
            return hide_age;
        }

        public void setHide_age(String hide_age) {
            this.hide_age = hide_age;
        }

        public String getUser_deactive() {
            return user_deactive;
        }

        public void setUser_deactive(String user_deactive) {
            this.user_deactive = user_deactive;
        }

        public String getPush_status() {
            return push_status;
        }

        public void setPush_status(String push_status) {
            this.push_status = push_status;
        }

        public String getPush_setting() {
            return push_setting;
        }

        public void setPush_setting(String push_setting) {
            this.push_setting = push_setting;
        }
    }
}
