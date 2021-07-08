package com.appentus.shedistrict.models;

public class CurrentPlanBean {


    /**
     * status : success
     * message : Plan successfully purchased
     * result : {"id":"6","user_id":"12","plan_type":"product","boosts":"4","amount":"5.99","plan_id":"0","duration":"6","is_active":"1","created":"2020-05-04 15:39:12","start_date":"2020-05-04","end_date":"2020-10-31"}
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
         * id : 6
         * user_id : 12
         * plan_type : product
         * boosts : 4
         * amount : 5.99
         * plan_id : 0
         * duration : 6
         * is_active : 1
         * created : 2020-05-04 15:39:12
         * start_date : 2020-05-04
         * end_date : 2020-10-31
         */

        private String id;
        private String user_id;
        private String plan_type;
        private String boosts;
        private String amount;
        private String plan_id;
        private String duration;
        private String is_active;
        private String created;
        private String start_date;
        private String end_date;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getPlan_type() {
            return plan_type;
        }

        public void setPlan_type(String plan_type) {
            this.plan_type = plan_type;
        }

        public String getBoosts() {
            return boosts;
        }

        public void setBoosts(String boosts) {
            this.boosts = boosts;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getPlan_id() {
            return plan_id;
        }

        public void setPlan_id(String plan_id) {
            this.plan_id = plan_id;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public String getIs_active() {
            return is_active;
        }

        public void setIs_active(String is_active) {
            this.is_active = is_active;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public String getStart_date() {
            return start_date;
        }

        public void setStart_date(String start_date) {
            this.start_date = start_date;
        }

        public String getEnd_date() {
            return end_date;
        }

        public void setEnd_date(String end_date) {
            this.end_date = end_date;
        }
    }
}
