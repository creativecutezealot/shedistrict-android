package com.appentus.shedistrict.models;

import java.util.List;

public class BlockedUserBeans {

    /**
     * status : success
     * message : blocked User
     * result_array : [{"block_id":"13","user_id":"2","block_user_id":"2","reason":"block","created":"2020-02-21 01:36:22","user_profile":"./assets/uploads/a73db02c93ee00e01367f832e9de484eimage.png","user_name":"raja1","user_email":"raja1@gmail.com","user_password":"123456","user_country_code":"","user_mobile":"","user_dob":"","user_login_type":"","user_social":"","user_device_type":"2","user_device_token":"fLUFGyz4I0golRbWtlHMWQ:APA91bH_p9jUPt7vQegqZ2HpYEETljkCyMVxPSZNstzFXrVnvY8qKdo8zDjJ7kbr8PYD0rDiD1uTcc5hIa3N1bFZndVIB-UBYtplYq7MG0LrVF8PoVzwdqvbqO_t3Gq_5k0GAZ2nwv4A","user_lat":"26.857608798916317","user_lang":"75.82470037050156","user_status":"1","role":"0","about_me":"hello test","friend_like":"{}","describe_me":"{}","hobbies":"{}","interest_info":"","personal_info":"{\"Age\":\"\",\"Religion\":\"\",\"Has_Kids?\":\"\",\"Smoking_Habits\":\"\",\"Education\":\"\",\"Sexual_Orientation\":\"\",\"Nationality\":\"Indian,San people,Australia,Brazil,Cuba,Egypt\",\"Drinking_Habits\":\"\",\"Relationship_Status\":\"\",\"Distance\":\"22,76\",\"Ethnicity\":\"Hindu,Greeks,Poles,Russian\",\"Political_Affiliation\":\"\"}","allow_msg":"0","premium_data":"","hide_profile":"0","hide_activity":"0","stop_invite":"0","hide_location":"0","hide_age":"0","user_deactive":"0","push_status":"0","push_setting":""},{"block_id":"14","user_id":"12","block_user_id":"12","reason":"","created":"2020-02-26 01:39:12","user_profile":"./assets/uploads/5815088046f5cad6ece3b7ea0d7b182eimage.png","user_name":"raja4","user_email":"raja4@gmail.com","user_password":"123456","user_country_code":"","user_mobile":"","user_dob":"","user_login_type":"","user_social":"","user_device_type":"2","user_device_token":"","user_lat":"26.857616335792233","user_lang":"75.82472467668104","user_status":"1","role":"0","about_me":"","friend_like":"","describe_me":"","hobbies":"","interest_info":"","personal_info":"{\"Age\":\"\",\"Nationality\":\"\",\"Ethnicity\":\"\",\"Religion\":\"\",\"Distance\":\"\",\"Education\":\"\",\"Relationship_Status\":\"\",\"Sexual_Orientation\":\"\",\"Has_Kids?\":\"\",\"Drinking_Habits\":\"\",\"Smoking_Habits\":\"\",\"Political_Affiliation\":\"\"}","allow_msg":"{\n\"1\":\"0\",\n\"2\":\"0\",\n\"3\":\"0\"\n} ","premium_data":"{\n\"1\":\"0\",\n\"2\":\"0\"\n}","hide_profile":"0","hide_activity":"0","stop_invite":"0","hide_location":"0","hide_age":"0","user_deactive":"0","push_status":"0","push_setting":"{\"1\":\"0\",\"2\":\"0\",\"3\":\"0\",\"4\":\"0\",\"5\":\"0\",\"6\":\"0\",\"7\":\"0\",\"8\":\"0\",\"9\":\"0\"}"}]
     */

    private String status;
    private String message;
    private List<ResultArrayBean> result_array;

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

    public List<ResultArrayBean> getResult_array() {
        return result_array;
    }

    public void setResult_array(List<ResultArrayBean> result_array) {
        this.result_array = result_array;
    }

    public static class ResultArrayBean {
        /**
         * block_id : 13
         * user_id : 2
         * block_user_id : 2
         * reason : block
         * created : 2020-02-21 01:36:22
         * user_profile : ./assets/uploads/a73db02c93ee00e01367f832e9de484eimage.png
         * user_name : raja1
         * user_email : raja1@gmail.com
         * user_password : 123456
         * user_country_code :
         * user_mobile :
         * user_dob :
         * user_login_type :
         * user_social :
         * user_device_type : 2
         * user_device_token : fLUFGyz4I0golRbWtlHMWQ:APA91bH_p9jUPt7vQegqZ2HpYEETljkCyMVxPSZNstzFXrVnvY8qKdo8zDjJ7kbr8PYD0rDiD1uTcc5hIa3N1bFZndVIB-UBYtplYq7MG0LrVF8PoVzwdqvbqO_t3Gq_5k0GAZ2nwv4A
         * user_lat : 26.857608798916317
         * user_lang : 75.82470037050156
         * user_status : 1
         * role : 0
         * about_me : hello test
         * friend_like : {}
         * describe_me : {}
         * hobbies : {}
         * interest_info :
         * personal_info : {"Age":"","Religion":"","Has_Kids?":"","Smoking_Habits":"","Education":"","Sexual_Orientation":"","Nationality":"Indian,San people,Australia,Brazil,Cuba,Egypt","Drinking_Habits":"","Relationship_Status":"","Distance":"22,76","Ethnicity":"Hindu,Greeks,Poles,Russian","Political_Affiliation":""}
         * allow_msg : 0
         * premium_data :
         * hide_profile : 0
         * hide_activity : 0
         * stop_invite : 0
         * hide_location : 0
         * hide_age : 0
         * user_deactive : 0
         * push_status : 0
         * push_setting :
         */

        private String block_id;
        private String user_id;
        private String block_user_id;
        private String reason;
        private String created;
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

        public String getBlock_id() {
            return block_id;
        }

        public void setBlock_id(String block_id) {
            this.block_id = block_id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getBlock_user_id() {
            return block_user_id;
        }

        public void setBlock_user_id(String block_user_id) {
            this.block_user_id = block_user_id;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
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
