package com.appentus.shedistrict.models;

import java.util.List;

public class BoostsListBean {

    /**
     * status : success
     * message : Boosts plan
     * result : [{"id":"1","plan_name":"1st","price":"0.99","boosts":"2","created":"2020-04-08 00:23:15"},{"id":"2","plan_name":"2nd","price":"1.99","boosts":"4","created":"2020-04-08 00:23:15"},{"id":"3","plan_name":"3rd","price":"2.99","boosts":"6","created":"2020-04-08 00:23:15"},{"id":"4","plan_name":"4th","price":"4.99","boosts":"10","created":"2020-04-08 00:23:15"}]
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
         * id : 1
         * plan_name : 1st
         * price : 0.99
         * boosts : 2
         * created : 2020-04-08 00:23:15
         */

        private String id;
        private String plan_name;
        private String price;
        private String boosts;
        private String created;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPlan_name() {
            return plan_name;
        }

        public void setPlan_name(String plan_name) {
            this.plan_name = plan_name;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getBoosts() {
            return boosts;
        }

        public void setBoosts(String boosts) {
            this.boosts = boosts;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }
    }
}
