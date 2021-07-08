package com.appentus.shedistrict.models;

import java.util.List;

public class MeetContentBean extends BaseApiResponse {

    private List<ResultBean> result;

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * content_id : 1
         * how_its_work : Both of you are receiving this message at the same time.
         If both of you say yes, you will bre taken to a page to schedule a girl date.
         If both of you say no, your messages will dissapear, but you will still see this person in the browse section if you would like to connect with them again.
         If you say yes and she says no, your messages will be paused and you won’t be able to contact each other until you are ready to meet.
         * why : This is how we encourage meeting in person. We understand that people get busy, but it is important to be able to communicate offline as well. We think that two weeks is more than enough time to make yourselves comfortable with meeting offline.
         Disagree? Tell us why here
         * rule_tips : 1. Before meeting, make sure you have a good sense of who the person you’re meeting is: * Confirm that their pictures are real *Exchange numbers and have conversation or video chat before meeting. 2. Do not give away any personal information ( i.e., your address). 3. Let close friends and family members know where you are going to meet this person. *Optional: Activate SheProtects to be able to access your friends, family, pr police department discreetly. 4. Do not meet in areas you’re unfamiliar with. 5. Call the venue you’re meeting at ahead of time to let them know you are visiting to meet with someone 6. Most importantly, have fun.
         * created : 2020-01-27 03:06:07
         */

        private String content_id;
        private String how_its_work;
        private String why;
        private String rule_tips;
        private String created;

        public String getContent_id() {
            return content_id;
        }

        public void setContent_id(String content_id) {
            this.content_id = content_id;
        }

        public String getHow_its_work() {
            return how_its_work;
        }

        public void setHow_its_work(String how_its_work) {
            this.how_its_work = how_its_work;
        }

        public String getWhy() {
            return why;
        }

        public void setWhy(String why) {
            this.why = why;
        }

        public String getRule_tips() {
            return rule_tips;
        }

        public void setRule_tips(String rule_tips) {
            this.rule_tips = rule_tips;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }
    }
}
