package com.appentus.shedistrict.models;

import java.util.List;

public class PrefrenceBean extends BaseApiResponse {


    private List<ResultBean> result;

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {     /**
         * preference_id : 1
         * preference : Age
         * input_type : range
         * created : 2020-01-24 02:20:39
         * values : [{"preference_value_id":"5","preference_id":"1","value_name":"16","created":"2020-03-16 01:31:50"},{"preference_value_id":"7","preference_id":"1","value_name":"80","created":"2020-03-16 01:32:26"}]
         * unit : mi
         */

        private String preference_id;
        private String preference;
        private String input_type;
        private String created;
        private String unit;
        private List<ValuesBean> values;
        private String selectedValue;


        public String getSelectedValue() {
            return selectedValue;
        }

        public void setSelectedValue(String selectedValue) {
            this.selectedValue = selectedValue;
        }

        public String getPreference_id() {
            return preference_id;
        }

        public void setPreference_id(String preference_id) {
            this.preference_id = preference_id;
        }

        public String getPreference() {
            return preference;
        }

        public void setPreference(String preference) {
            this.preference = preference;
        }

        public String getInput_type() {
            return input_type;
        }

        public void setInput_type(String input_type) {
            this.input_type = input_type;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public List<ValuesBean> getValues() {
            return values;
        }

        public void setValues(List<ValuesBean> values) {
            this.values = values;
        }

        public static class ValuesBean {
            /**
             * preference_value_id : 5
             * preference_id : 1
             * value_name : 16
             * created : 2020-03-16 01:31:50
             */

            private String preference_value_id;
            private String preference_id;
            private String value_name;
            private String created;

            private boolean isSelect;

            public boolean isSelect() {
                return isSelect;
            }

            public void setSelect(boolean select) {
                isSelect = select;
            }

            public String getPreference_value_id() {
                return preference_value_id;
            }

            public void setPreference_value_id(String preference_value_id) {
                this.preference_value_id = preference_value_id;
            }

            public String getPreference_id() {
                return preference_id;
            }

            public void setPreference_id(String preference_id) {
                this.preference_id = preference_id;
            }

            public String getValue_name() {
                return value_name;
            }

            public void setValue_name(String value_name) {
                this.value_name = value_name;
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
