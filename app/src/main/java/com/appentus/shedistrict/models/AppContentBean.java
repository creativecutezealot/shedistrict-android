package com.appentus.shedistrict.models;

import java.util.List;

public class AppContentBean extends BaseApiResponse {

    private List<ResultBean> result;

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * id : 1
         * app_about : SheDistrict is a social networking environment that allows women to connect with each other professionally and socialy. We are dedicated to making sure real friendships and connections are made through a messaging system that encourages in-person interactions.
         * app_update :
         * ceo_msg : Hey, girl! Thank you for joining us.
         Nothing inspired the vision and development of SheDistrict more than the fact that I faced a huge problem that I know other women are also facing - the lack of friendships and connections being made with each other. It was February 2019 when I realized that I wanted to stop complaning about not being able to make friends in a big city and actualy solve the problem by creating something that would evetually turn out to be bigger than myself.
         I’m so excited to be in this joureny to creating long lasting friendships with all of you.
         * ceo_social_link : link
         * created : 2020-02-11 22:45:08
         */

        private String id;
        private String app_about;
        private String app_update;
        private String ceo_msg;
        private String ceo_social_link;
        private String created;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getApp_about() {
            return app_about;
        }

        public void setApp_about(String app_about) {
            this.app_about = app_about;
        }

        public String getApp_update() {
            return app_update;
        }

        public void setApp_update(String app_update) {
            this.app_update = app_update;
        }

        public String getCeo_msg() {
            return ceo_msg;
        }

        public void setCeo_msg(String ceo_msg) {
            this.ceo_msg = ceo_msg;
        }

        public String getCeo_social_link() {
            return ceo_social_link;
        }

        public void setCeo_social_link(String ceo_social_link) {
            this.ceo_social_link = ceo_social_link;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }
    }
}
