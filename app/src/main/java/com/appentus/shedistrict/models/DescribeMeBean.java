package com.appentus.shedistrict.models;

import java.util.List;

public class DescribeMeBean {

    /**
     * status : success
     * message : describe data
     * result : [{"id":"1","value":"option 1","created":"2020-02-14 05:52:49"},{"id":"2","value":"option 2","created":"2020-02-14 05:52:49"},{"id":"3","value":"option 3","created":"2020-02-14 05:52:49"}]
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
         * value : option 1
         * created : 2020-02-14 05:52:49
         */

        private String id;
        private String value;
        private String created;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }
    }
}
