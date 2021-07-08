package com.appentus.shedistrict.models;

import java.util.List;

public class IntrestBean {

    /**
     * status : success
     * message : interest
     * result : [{"interest_id":"1","interest":"Favorite Food","created":"2020-02-10 05:38:24","value":[{"id":"1","interest_id":"1","interest_value":"demo1 ","created":"2020-02-12 05:14:36"},{"id":"2","interest_id":"1","interest_value":"demo2","created":"2020-02-12 05:14:36"}]},{"interest_id":"2","interest":"Favorite Color","created":"2020-02-10 05:38:24","value":[]},{"interest_id":"3","interest":"Favorite Music","created":"2020-02-10 05:38:24","value":[]},{"interest_id":"4","interest":"Favorite Movie","created":"2020-02-10 05:38:24","value":[]},{"interest_id":"5","interest":"Favorite Type of Clothing","created":"2020-02-10 05:38:24","value":[]},{"interest_id":"6","interest":"Favorite Season","created":"2020-02-10 05:38:24","value":[]},{"interest_id":"7","interest":"Favorite Animal","created":"2020-02-10 05:38:24","value":[]},{"interest_id":"8","interest":"Favorite App","created":"2020-02-10 05:38:24","value":[]},{"interest_id":"9","interest":"Favorite Dog Breed","created":"2020-02-10 05:38:24","value":[]},{"interest_id":"10","interest":"Favorite Car","created":"2020-02-10 05:38:24","value":[]},{"interest_id":"11","interest":"Favorite Book","created":"2020-02-10 05:38:24","value":[]},{"interest_id":"12","interest":"Favorite Holidays","created":"2020-02-10 05:38:24","value":[]}]
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
         * interest_id : 1
         * interest : Favorite Food
         * created : 2020-02-10 05:38:24
         * value : [{"id":"1","interest_id":"1","interest_value":"demo1 ","created":"2020-02-12 05:14:36"},{"id":"2","interest_id":"1","interest_value":"demo2","created":"2020-02-12 05:14:36"}]
         */

        private String interest_id;
        private String interest;
        private String interest_key;
        private String created;
        private List<ValueBean> value;
        private String selectedValue;
        private String input_type;

        public String getInput_type() {
            return input_type;
        }

        public void setInput_type(String input_type) {
            this.input_type = input_type;
        }


        public String getSelectedValue() {
            return selectedValue;
        }

        public void setSelectedValue(String selectedValue) {
            this.selectedValue = selectedValue;
        }



        public String getInterest_key() {
            return interest_key;
        }

        public void setInterest_key(String interest_key) {
            this.interest_key = interest_key;
        }

        public String getInterest_id() {
            return interest_id;
        }

        public void setInterest_id(String interest_id) {
            this.interest_id = interest_id;
        }

        public String getInterest() {
            return interest;
        }

        public void setInterest(String interest) {
            this.interest = interest;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public List<ValueBean> getValue() {
            return value;
        }

        public void setValue(List<ValueBean> value) {
            this.value = value;
        }

        public static class ValueBean {
            /**
             * id : 1
             * interest_id : 1
             * interest_value : demo1
             * created : 2020-02-12 05:14:36
             */

            private String id;
            private String interest_id;
            private String interest_value;
            private String created;

            private  boolean isSelected;



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

            public String getInterest_id() {
                return interest_id;
            }

            public void setInterest_id(String interest_id) {
                this.interest_id = interest_id;
            }

            public String getInterest_value() {
                return interest_value;
            }

            public void setInterest_value(String interest_value) {
                this.interest_value = interest_value;
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
