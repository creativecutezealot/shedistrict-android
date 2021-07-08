package com.appentus.shedistrict.models;

import java.util.List;

public class PlansBean {


    /**
     * status : success
     * message : All plans
     * result : [{"plan_id":"3","plan_title":"Plan First","plan_duration":"","plan_price":"5.99","plan_description":"<ul>\r\n\t<li><strong>Two free Boosts&nbsp;<\/strong><\/li>\r\n\t<li><strong>Ads are removed<\/strong><\/li>\r\n\t<li><strong>Two week message limit removed<\/strong><\/li>\r\n\t<li><strong>See up to 10 of your most recent viewers<\/strong><\/li>\r\n\t<li><strong>Post up to 6&nbsp;announcements<\/strong><\/li>\r\n<\/ul>\r\n","plan_boosts":"2","plan_announcement":"6","plan_remove_msg_limit":"1","plan_view_ads":"1","plan_recent_viewers":"10","created":"2020-04-03 00:58:54"},{"plan_id":"4","plan_title":"Plan second","plan_duration":"","plan_price":"9.99","plan_description":"<ul>\r\n\t<li><strong>Two free Boosts&nbsp;<\/strong><\/li>\r\n\t<li><strong>Ads are removed<\/strong><\/li>\r\n\t<li><strong>Two week message limit removed<\/strong><\/li>\r\n\t<li><strong>See up to 10 of your most recent viewers<\/strong><\/li>\r\n\t<li><strong>Post up to 10&nbsp;announcements<\/strong><\/li>\r\n\t<li>Hide loaction<\/li>\r\n\t<li><strong>Hide age<\/strong><\/li>\r\n<\/ul>\r\n","plan_boosts":"2","plan_announcement":"10","plan_remove_msg_limit":"1","plan_view_ads":"1","plan_recent_viewers":"20","created":"2020-04-03 01:05:54"},{"plan_id":"5","plan_title":"Life time plan  ","plan_duration":"","plan_price":"79.99","plan_description":"<p><strong>For&nbsp; a one time&nbsp; payment of $ 79.99:<\/strong><\/p>\r\n\r\n<ul>\r\n\t<li>Unlimited boosts.<\/li>\r\n\t<li>Ads are removed.<\/li>\r\n\t<li>Two weeks message limit removed.<\/li>\r\n\t<li>See every one who has viewed your profile<\/li>\r\n\t<li>Post an unlimited amount of announcements.<\/li>\r\n\t<li>Personal invitations to exlusive events, meets ups, and more.<\/li>\r\n<\/ul>\r\n","plan_boosts":"unlimited","plan_announcement":"unlimited","plan_remove_msg_limit":"1","plan_view_ads":"1","plan_recent_viewers":"unlimited","created":"2020-04-03 01:22:11"}]
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
         * plan_id : 3
         * plan_title : Plan First
         * plan_duration :
         * plan_price : 5.99
         * plan_description : <ul>
         <li><strong>Two free Boosts&nbsp;</strong></li>
         <li><strong>Ads are removed</strong></li>
         <li><strong>Two week message limit removed</strong></li>
         <li><strong>See up to 10 of your most recent viewers</strong></li>
         <li><strong>Post up to 6&nbsp;announcements</strong></li>
         </ul>
         * plan_boosts : 2
         * plan_announcement : 6
         * plan_remove_msg_limit : 1
         * plan_view_ads : 1
         * plan_recent_viewers : 10
         * created : 2020-04-03 00:58:54
         */

        private String plan_id;
        private String plan_title;
        private String plan_duration;
        private String plan_price;
        private String plan_description;
        private String plan_boosts;
        private String plan_announcement;
        private String plan_remove_msg_limit;
        private String plan_view_ads;
        private String plan_recent_viewers;
        private String created;

        public String getPlan_id() {
            return plan_id;
        }

        public void setPlan_id(String plan_id) {
            this.plan_id = plan_id;
        }

        public String getPlan_title() {
            return plan_title;
        }

        public void setPlan_title(String plan_title) {
            this.plan_title = plan_title;
        }

        public String getPlan_duration() {
            return plan_duration;
        }

        public void setPlan_duration(String plan_duration) {
            this.plan_duration = plan_duration;
        }

        public String getPlan_price() {
            return plan_price;
        }

        public void setPlan_price(String plan_price) {
            this.plan_price = plan_price;
        }

        public String getPlan_description() {
            return plan_description;
        }

        public void setPlan_description(String plan_description) {
            this.plan_description = plan_description;
        }

        public String getPlan_boosts() {
            return plan_boosts;
        }

        public void setPlan_boosts(String plan_boosts) {
            this.plan_boosts = plan_boosts;
        }

        public String getPlan_announcement() {
            return plan_announcement;
        }

        public void setPlan_announcement(String plan_announcement) {
            this.plan_announcement = plan_announcement;
        }

        public String getPlan_remove_msg_limit() {
            return plan_remove_msg_limit;
        }

        public void setPlan_remove_msg_limit(String plan_remove_msg_limit) {
            this.plan_remove_msg_limit = plan_remove_msg_limit;
        }

        public String getPlan_view_ads() {
            return plan_view_ads;
        }

        public void setPlan_view_ads(String plan_view_ads) {
            this.plan_view_ads = plan_view_ads;
        }

        public String getPlan_recent_viewers() {
            return plan_recent_viewers;
        }

        public void setPlan_recent_viewers(String plan_recent_viewers) {
            this.plan_recent_viewers = plan_recent_viewers;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }
    }
}
