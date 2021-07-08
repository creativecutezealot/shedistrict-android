package com.appentus.shedistrict.models;

import java.util.List;

public class UserInfoByEmailBean {

    /**
     * status : success
     * message : profile successfully get
     * result : {"user_id":"159","user_profile":"./assets/uploads/3023290b840119df9416f76a75373a88IMG-20200416-WA0009.jpg","user_name":"arru","user_email":"arru@gmail.com","user_password":"123456","user_country_code":"","user_mobile":"7221999733","user_dob":"09/06/1994","user_login_type":"","user_social":"","user_device_type":"1","user_device_token":"","user_lat":"26.8511824","user_lang":"75.8198026","user_status":"1","created":"2020-04-23 11:01:01","role":"0","about_me":"","friend_like":"","describe_me":"","hobbies":"","interest_info":"{\"Favorite_Food\":\"\",\"Favorite_Color\":\"\",\"Favorite_Music\":\"\",\"Favorite_Movie\":\"\",\"Favorite_Type_of_Clothing\":\"\",\"Favorite_Season\":\"\",\"Favorite_Animal\":\"\",\"Favorite_App\":\"\",\"Favorite_Dog_Breed\":\"\",\"Favorite_Car\":\"\",\"Favorite_Book\":\"\",\"Favorite_Holidays\":\"\"}","personal_info":"{\"Age\":\"\",\"Ethnicity\":\"\",\"Nationality\":\"\",\"Religion\":\"\",\"Distance\":\"\",\"Education\":\"\",\"Relationship_Status\":\"\",\"Sexual_Orientation\":\"\",\"Has_Kids?\":\"\",\"Drinking_Habits\":\"\",\"Smoking_Habits\":\"\",\"Political_Affiliated\":\"\"}","allow_msg":"{\"1\":\"0\",\"2\":\"0\",\"3\":\"0\"}","premium_data":"{\"1\":\"0\",\"2\":\"0}","hide_profile":"0","hide_activity":"0","stop_invite":"0","hide_location":"0","hide_age":"0","user_deactive":"0","push_status":"0","push_setting":"{\"1\":\"0\",\"2\":\"0\",\"3\":\"0\",\"4\":\"0\",\"5\":\"0\",\"6\":\"0\",\"7\":\"0\",\"8\":\"0\",\"9\":\"0\"}","question":"My first dog name","answer":"coco","she_protect":"0","user_details":[{"detail_id":"159","detail_user_id":"159","user_bio":"","user_bio_image":"","user_bio_video":"./assets/uploads/ecd5f5577ec252a2b2e8fe56b22e7aee1587639787428.mp4","user_intro_video":"./assets/uploads/a34409f36904de89167d01100d9f906f1587639787428.mp4","created":"2020-04-23 11:01:01"}],"user_photos":[{"photo_id":"116","user_photos":"./assets/uploads/aa02c888c5025ee32be2ce66bd1f062bScreenshot_20200420-182448.png","photos_user_id":"159","created":"2020-04-23 11:05:22"}]}
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
         * user_id : 159
         * user_profile : ./assets/uploads/3023290b840119df9416f76a75373a88IMG-20200416-WA0009.jpg
         * user_name : arru
         * user_email : arru@gmail.com
         * user_password : 123456
         * user_country_code :
         * user_mobile : 7221999733
         * user_dob : 09/06/1994
         * user_login_type :
         * user_social :
         * user_device_type : 1
         * user_device_token :
         * user_lat : 26.8511824
         * user_lang : 75.8198026
         * user_status : 1
         * created : 2020-04-23 11:01:01
         * role : 0
         * about_me :
         * friend_like :
         * describe_me :
         * hobbies :
         * interest_info : {"Favorite_Food":"","Favorite_Color":"","Favorite_Music":"","Favorite_Movie":"","Favorite_Type_of_Clothing":"","Favorite_Season":"","Favorite_Animal":"","Favorite_App":"","Favorite_Dog_Breed":"","Favorite_Car":"","Favorite_Book":"","Favorite_Holidays":""}
         * personal_info : {"Age":"","Ethnicity":"","Nationality":"","Religion":"","Distance":"","Education":"","Relationship_Status":"","Sexual_Orientation":"","Has_Kids?":"","Drinking_Habits":"","Smoking_Habits":"","Political_Affiliated":""}
         * allow_msg : {"1":"0","2":"0","3":"0"}
         * premium_data : {"1":"0","2":"0}
         * hide_profile : 0
         * hide_activity : 0
         * stop_invite : 0
         * hide_location : 0
         * hide_age : 0
         * user_deactive : 0
         * push_status : 0
         * push_setting : {"1":"0","2":"0","3":"0","4":"0","5":"0","6":"0","7":"0","8":"0","9":"0"}
         * question : My first dog name
         * answer : coco
         * she_protect : 0
         * user_details : [{"detail_id":"159","detail_user_id":"159","user_bio":"","user_bio_image":"","user_bio_video":"./assets/uploads/ecd5f5577ec252a2b2e8fe56b22e7aee1587639787428.mp4","user_intro_video":"./assets/uploads/a34409f36904de89167d01100d9f906f1587639787428.mp4","created":"2020-04-23 11:01:01"}]
         * user_photos : [{"photo_id":"116","user_photos":"./assets/uploads/aa02c888c5025ee32be2ce66bd1f062bScreenshot_20200420-182448.png","photos_user_id":"159","created":"2020-04-23 11:05:22"}]
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
        private String question;
        private String answer;
        private String she_protect;
        private List<UserDetailsBean> user_details;
        private List<UserPhotosBean> user_photos;

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

        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }

        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }

        public String getShe_protect() {
            return she_protect;
        }

        public void setShe_protect(String she_protect) {
            this.she_protect = she_protect;
        }

        public List<UserDetailsBean> getUser_details() {
            return user_details;
        }

        public void setUser_details(List<UserDetailsBean> user_details) {
            this.user_details = user_details;
        }

        public List<UserPhotosBean> getUser_photos() {
            return user_photos;
        }

        public void setUser_photos(List<UserPhotosBean> user_photos) {
            this.user_photos = user_photos;
        }

        public static class UserDetailsBean {
            /**
             * detail_id : 159
             * detail_user_id : 159
             * user_bio :
             * user_bio_image :
             * user_bio_video : ./assets/uploads/ecd5f5577ec252a2b2e8fe56b22e7aee1587639787428.mp4
             * user_intro_video : ./assets/uploads/a34409f36904de89167d01100d9f906f1587639787428.mp4
             * created : 2020-04-23 11:01:01
             */

            private String detail_id;
            private String detail_user_id;
            private String user_bio;
            private String user_bio_image;
            private String user_bio_video;
            private String user_intro_video;
            private String created;

            public String getDetail_id() {
                return detail_id;
            }

            public void setDetail_id(String detail_id) {
                this.detail_id = detail_id;
            }

            public String getDetail_user_id() {
                return detail_user_id;
            }

            public void setDetail_user_id(String detail_user_id) {
                this.detail_user_id = detail_user_id;
            }

            public String getUser_bio() {
                return user_bio;
            }

            public void setUser_bio(String user_bio) {
                this.user_bio = user_bio;
            }

            public String getUser_bio_image() {
                return user_bio_image;
            }

            public void setUser_bio_image(String user_bio_image) {
                this.user_bio_image = user_bio_image;
            }

            public String getUser_bio_video() {
                return user_bio_video;
            }

            public void setUser_bio_video(String user_bio_video) {
                this.user_bio_video = user_bio_video;
            }

            public String getUser_intro_video() {
                return user_intro_video;
            }

            public void setUser_intro_video(String user_intro_video) {
                this.user_intro_video = user_intro_video;
            }

            public String getCreated() {
                return created;
            }

            public void setCreated(String created) {
                this.created = created;
            }
        }

        public static class UserPhotosBean {
            /**
             * photo_id : 116
             * user_photos : ./assets/uploads/aa02c888c5025ee32be2ce66bd1f062bScreenshot_20200420-182448.png
             * photos_user_id : 159
             * created : 2020-04-23 11:05:22
             */

            private String photo_id;
            private String user_photos;
            private String photos_user_id;
            private String created;

            public String getPhoto_id() {
                return photo_id;
            }

            public void setPhoto_id(String photo_id) {
                this.photo_id = photo_id;
            }

            public String getUser_photos() {
                return user_photos;
            }

            public void setUser_photos(String user_photos) {
                this.user_photos = user_photos;
            }

            public String getPhotos_user_id() {
                return photos_user_id;
            }

            public void setPhotos_user_id(String photos_user_id) {
                this.photos_user_id = photos_user_id;
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
