package com.appentus.shedistrict.models;

import java.util.List;

public class EventBean extends BaseApiResponse {


    private List<PendingBean> pending;
    private List<AttendingBean> attending;

    public List<PendingBean> getPending() {
        return pending;
    }

    public void setPending(List<PendingBean> pending) {
        this.pending = pending;
    }

    public List<AttendingBean> getAttending() {
        return attending;
    }

    public void setAttending(List<AttendingBean> attending) {
        this.attending = attending;
    }

    public static class PendingBean {
        /**
         * meeting_id : 18
         * meeting_user_id : 51
         * meeting_friend_id : 1
         * meeting_subject : tsgdg
         * meeting_reason : xggdgd
         * meeting_date : 2014/Jun/6
         * meeting_time : 8:00PM
         * meeting_location : test
         * created : 2020-02-12 06:30:44
         * meeting_status : 0
         * user_name : Admin
         * user_profile :
         * text : you have invited Admin
         * type : send
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
        private String user_name;
        private String user_profile;
        private String fri_name;
        private String text;
        private String type;

        public String getFri_name() {
            return fri_name;
        }

        public void setFri_name(String fri_name) {
            this.fri_name = fri_name;
        }

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

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getUser_profile() {
            return user_profile;
        }

        public void setUser_profile(String user_profile) {
            this.user_profile = user_profile;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    public static class AttendingBean {
        /**
         * meeting_id : 17
         * meeting_user_id : 51
         * meeting_friend_id : 1
         * meeting_subject : jjdjjshs
         * meeting_reason : hahhshs
         * meeting_date : 1903/04/02
         * meeting_time : 04:00 PM
         * meeting_location : ffshsjjsjsjsjsjjshshshshsh
         * created : 2020-02-11 22:28:25
         * meeting_status : 1
         * user_name : Admin
         * user_profile :
         * text : New Friends Meetup With You
         * type : send
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
        private String user_name;
        private String user_profile;
        private String text;
        private String type;

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

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getUser_profile() {
            return user_profile;
        }

        public void setUser_profile(String user_profile) {
            this.user_profile = user_profile;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
