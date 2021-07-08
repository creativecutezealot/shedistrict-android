package com.appentus.shedistrict.models;

import java.util.List;

public class HobbiesBean extends BaseApiResponse {

    private List<ResultBean> result;

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * hobbies_id : 1
         * hibbies_name : Hiking
         * created : 2020-02-10 05:07:55
         */

        private String hobbies_id;
        private String hobbies_name;
        private String created;
        private boolean isSelected;

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }

        public String getHobbies_id() {
            return hobbies_id;
        }

        public void setHobbies_id(String hobbies_id) {
            this.hobbies_id = hobbies_id;
        }

        public String getHobbies_name() {
            return hobbies_name;
        }

        public void setHibbies_name(String hibbies_name)
        {
            this.hobbies_name = hibbies_name;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }
    }
}
