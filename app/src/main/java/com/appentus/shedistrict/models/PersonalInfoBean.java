package com.appentus.shedistrict.models;

import java.util.List;

public class PersonalInfoBean {

    /**
     * status : success
     * message : personal info data
     * result : [{"id":"1","info_key":"Age","info_value":"{\"1\":\"18\",\"2\":\"19\"}","created":"2020-02-14 06:19:58"},{"id":"2","info_key":"Nationality","info_value":"{\"1\":\"India\",\"2\":\"Albanian\"}","created":"2020-02-14 06:19:58"},{"id":"3","info_key":"Religion","info_value":"{\"1\":\"Hindu\",\"2\":\"Muslim\"}","created":"2020-02-14 06:21:22"},{"id":"4","info_key":"Education","info_value":"{\"1\":\"Uni\",\"2\":\"12th\"}","created":"2020-02-14 06:21:22"},{"id":"5","info_key":"Ethnicity","info_value":"{}","created":"2020-02-14 23:00:33"},{"id":"6","info_key":"Distance","info_value":"{}","created":"2020-02-14 23:00:33"},{"id":"8","info_key":"Relationship Status","info_value":"{}","created":"2020-02-14 23:00:33"},{"id":"9","info_key":"Sexual Orientation","info_value":"{}","created":"2020-02-14 23:00:33"},{"id":"10","info_key":"Has Kids?","info_value":"{}","created":"2020-02-14 23:00:33"},{"id":"11","info_key":"Drinking Habits","info_value":"{}","created":"2020-02-14 23:00:33"},{"id":"12","info_key":"Smoking Habits","info_value":"{}","created":"2020-02-14 23:00:33"},{"id":"13","info_key":"Political Affiliation","info_value":"{}","created":"2020-02-14 23:00:33"}]
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
         * info_key : Age
         * info_value : {"1":"18","2":"19"}
         * created : 2020-02-14 06:19:58
         */

        private String id;
        private String info_key;
        private String info_value;
        private String created;
        private String selectedValue;
        private  boolean isSelected;

        public String getSelectedValue() {
            return selectedValue;
        }

        public void setSelectedValue(String selectedValue) {
            this.selectedValue = selectedValue;
        }

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getInfo_key() {
            return info_key;
        }

        public void setInfo_key(String info_key) {
            this.info_key = info_key;
        }

        public String getInfo_value() {
            return info_value;
        }

        public void setInfo_value(String info_value) {
            this.info_value = info_value;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }
    }
}
