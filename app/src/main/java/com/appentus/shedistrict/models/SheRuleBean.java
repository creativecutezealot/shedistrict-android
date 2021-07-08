package com.appentus.shedistrict.models;



import java.util.List;

public class SheRuleBean extends BaseApiResponse {

    private List<ResultBean> result;


    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * rule_id : 1
         * rule_title : Be Unapologteic
         * rule_discription : Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source.
         * created : 2020-01-23 04:33:33
         */

        private String rule_id;
        private String rule_title;
        private String rule_discription;
        private String created;

        public String getRule_id() {
            return rule_id;
        }

        public void setRule_id(String rule_id) {
            this.rule_id = rule_id;
        }

        public String getRule_title() {
            return rule_title;
        }

        public void setRule_title(String rule_title) {
            this.rule_title = rule_title;
        }

        public String getRule_discription() {
            return rule_discription;
        }

        public void setRule_discription(String rule_discription) {
            this.rule_discription = rule_discription;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;

        }




    }
}
