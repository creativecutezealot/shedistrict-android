package com.appentus.shedistrict.models;

import java.util.List;

public class SecurityQuestionBean {


    /**
     * status : success
     * message : Security question
     * result : [{"id":"1","questions":"What was you first dog's name?","created":"2020-03-30 22:44:46"},{"id":"2","questions":"What is your current dog name?","created":"2020-03-30 22:44:46"},{"id":"3","questions":"What is favorite food?","created":"2020-03-30 22:44:46"},{"id":"4","questions":"Are you an only child?","created":"2020-03-30 22:44:46"},{"id":"5","questions":"What's your favorite color?","created":"2020-03-30 22:44:46"}]
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
         * questions : What was you first dog's name?
         * created : 2020-03-30 22:44:46
         */

        public String id;
        public String question;
        public String created;
        private boolean isSelect=false;

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }


        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }
    }
}
