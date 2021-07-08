package com.appentus.shedistrict.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class AnnouncmentBean extends BaseApiResponse implements Serializable {

    private List<ResultBean> result;

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean implements Serializable {
        /**
         * announcement_id : 28
         * announcement_user_id : 1
         * announcement_category_id : 1
         * announcement_title : demo
         * announcement_desc : demo demodmeodmeo
         * created : 2020-01-30 23:45:50
         * announcement_image : ./assets/uploads/0d07bdc28c151c317e3c644221cbfbb2email.png
         * status : 1
         * user : [{"user_id":"1","user_profile":"","user_name":"Admin","user_email":"admin@gmail.com","user_password":"123456","user_country_code":"","user_mobile":"","user_dob":"","user_login_type":"","user_social":"","user_device_type":"","user_device_token":"","user_lat":"","user_lang":"","user_status":"1","created":"2020-02-14 22:36:26","role":"1","about_me":"","friend_like":"","describe_me":"","hobbies":"","interest_info":"","":"","allow_msg":"0","premium_data":"","hide_profile":"0","hide_activity":"0","stop_invite":"0","hide_location":"0","hide_age":"0","user_deactive":"0","push_status":"1","push_setting":"{\"1\":\"0\",\"2\":\"0\",\"3\":\"0\",\"4\":\"0\",\"5\":\"0\",\"6\":\"0\",\"7\":\"0\",\"8\":\"0\",\"9\":\"0\"}"}]
         * category : [{"category_id":"1","category_name":"category 1","created":"2020-01-23 04:45:30"}]
         */

        private String announcement_id;
        private String announcement_user_id;
        private String announcement_category_id;
        private String announcement_title;
        private String announcement_desc;
        private String created;
        private String announcement_image;
        private String image_type;
        @SerializedName("status")
        private String statusX;
        private List<UserBean> user;
        private List<CategoryBean> category;

        public String getImage_type() {
            return image_type;
        }

        public void setImage_type(String image_type) {
            this.image_type = image_type;
        }

        public String getAnnouncement_id() {
            return announcement_id;
        }

        public void setAnnouncement_id(String announcement_id) {
            this.announcement_id = announcement_id;
        }

        public String getAnnouncement_user_id() {
            return announcement_user_id;
        }

        public void setAnnouncement_user_id(String announcement_user_id) {
            this.announcement_user_id = announcement_user_id;
        }

        public String getAnnouncement_category_id() {
            return announcement_category_id;
        }

        public void setAnnouncement_category_id(String announcement_category_id) {
            this.announcement_category_id = announcement_category_id;
        }

        public String getAnnouncement_title() {
            return announcement_title;
        }

        public void setAnnouncement_title(String announcement_title) {
            this.announcement_title = announcement_title;
        }

        public String getAnnouncement_desc() {
            return announcement_desc;
        }

        public void setAnnouncement_desc(String announcement_desc) {
            this.announcement_desc = announcement_desc;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public String getAnnouncement_image() {
            return announcement_image;
        }

        public void setAnnouncement_image(String announcement_image) {
            this.announcement_image = announcement_image;
        }

        public String getStatusX() {
            return statusX;
        }

        public void setStatusX(String statusX) {
            this.statusX = statusX;
        }

        public List<UserBean> getUser() {
            return user;
        }

        public void setUser(List<UserBean> user) {
            this.user = user;
        }

        public List<CategoryBean> getCategory() {
            return category;
        }

        public void setCategory(List<CategoryBean> category) {
            this.category = category;
        }

        public static class UserBean implements Serializable {
            /**
             * user_id : 1
             * user_profile :
             * user_name : Admin
             * user_email : admin@gmail.com
             * user_password : 123456
             * user_country_code :
             * user_mobile :
             * user_dob :
             * user_login_type :
             * user_social :
             * user_device_type :
             * user_device_token :
             * user_lat :
             * user_lang :
             * user_status : 1
             * created : 2020-02-14 22:36:26
             * role : 1
             * about_me :
             * friend_like :
             * describe_me :
             * hobbies :
             * interest_info :
             * personal_info :
             * allow_msg : 0
             * premium_data :
             * hide_profile : 0
             * hide_activity : 0
             * stop_invite : 0
             * hide_location : 0
             * hide_age : 0
             * user_deactive : 0
             * push_status : 1
             * push_setting : {"1":"0","2":"0","3":"0","4":"0","5":"0","6":"0","7":"0","8":"0","9":"0"}
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

        public static class CategoryBean implements Serializable{
            /**
             * category_id : 1
             * category_name : category 1
             * created : 2020-01-23 04:45:30
             */

            private String category_id;
            private String category_name;
            private String created;
            private String color;

            public String getColor() {
                return color;
            }

            public void setColor(String color) {
                this.color = color;
            }

            public String getCategory_id() {
                return category_id;
            }

            public void setCategory_id(String category_id) {
                this.category_id = category_id;
            }

            public String getCategory_name() {
                return category_name;
            }

            public void setCategory_name(String category_name) {
                this.category_name = category_name;
            }

            public String getCreated() {
                return created;
            }

            public void setCreated(String created) {
                this.created = created;
            }
        }
    }
}
