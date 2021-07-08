package com.appentus.shedistrict.models;

import java.util.List;

public class CountriesBean {

    private List<Countries> countries;

    public List<Countries> getCountries() {
        return countries;
    }

    public void setCountries(List<Countries> countries) {
        this.countries = countries;
    }

    public static class Countries {
        /**
         * country_name : India
         * country_isd : +91
         * display : India (+91)
         * cities : [{"city":"Mumbai - Airoli","state":"Maharashtra"},{"city":"Mumbai - Andheri","state":"Maharashtra"},{"city":"Mumbai - Bandra","state":"Maharashtra"},{"city":"Mumbai - Belapur","state":"Maharashtra"},{"city":"Mumbai - Borivali","state":"Maharashtra"},{"city":"Mumbai - Dadar","state":"Maharashtra"},{"city":"Mumbai - Ghatkoper","state":"Maharashtra"},{"city":"Mumbai - Kandivali","state":"Maharashtra"},{"city":"Mumbai - Lower Parel","state":"Maharashtra"},{"city":"Mumbai - Thane","state":"Maharashtra"},{"city":"Mumbai - Vashi","state":"Maharashtra"},{"city":"Mumbai","state":"Maharashtra"},{"city":"Delhi - Dwarka","state":"Delhi"},{"city":"Delhi - Faridabad","state":"Delhi"},{"city":"Delhi - Ghaziabad","state":"Delhi"},{"city":"Delhi - Gurgaon","state":"Delhi"},{"city":"Delhi - Indirapuram","state":"Delhi"},{"city":"Delhi - Jankpuri","state":"Delhi"},{"city":"Delhi - Karol Bagh","state":"Delhi"},{"city":"Delhi - Lajpat Nagar","state":"Delhi"},{"city":"Delhi - Laxmi Ngar","state":"Delhi"},{"city":"Delhi - Malviya Nagar","state":"Delhi"},{"city":"Delhi - Model Town","state":"Delhi"},{"city":"Delhi - Noida","state":"Delhi"},{"city":"Delhi - Pitampura","state":"Delhi"},{"city":"Delhi - Punjabi Bagh","state":"Delhi"},{"city":"Delhi - Rajouri Garden","state":"Delhi"},{"city":"Delhi - RK Puram","state":"Delhi"},{"city":"Delhi - Rohini","state":"Delhi"},{"city":"Delhi - Uttam Nagar","state":"Delhi"},{"city":"Delhi - Vasant Kunj","state":"Delhi"},{"city":"Delhi - Vasant Vihar","state":"Delhi"},{"city":"Delhi","state":"Delhi"},{"city":"Bengaluru - Electronic city","state":"Karnataka"},{"city":"Bengaluru - Hebbal","state":"Karnataka"},{"city":"Bengaluru - HSR Layout","state":"Karnataka"},{"city":"Bengaluru - Indiranagar","state":"Karnataka"},{"city":"Bengaluru - JP Nagar","state":"Karnataka"},{"city":"Bengaluru - Kengeri","state":"Karnataka"},{"city":"Bengaluru - Koramangala","state":"Karnataka"},{"city":"Bengaluru - Malleswaram","state":"Karnataka"},{"city":"Bengaluru - MG Road","state":"Karnataka"},{"city":"Bengaluru - Rajarajeswari Nagar","state":"Karnataka"},{"city":"Bengaluru - Whitefield","state":"Karnataka"},{"city":"Bengaluru - Yelahanka","state":"Karnataka"},{"city":"Bengaluru","state":"Karnataka"},{"city":"Bangalore - Electronic city","state":"Karnataka"},{"city":"Bangalore - Hebbal","state":"Karnataka"},{"city":"Bangalore - HSR Layout","state":"Karnataka"},{"city":"Bangalore - Indiranagar","state":"Karnataka"},{"city":"Bangalore - JP Nagar","state":"Karnataka"},{"city":"Bangalore - Kengeri","state":"Karnataka"},{"city":"Bangalore - Koramangala","state":"Karnataka"},{"city":"Bangalore - Malleswaram","state":"Karnataka"},{"city":"Bangalore - MG Road","state":"Karnataka"},{"city":"Bangalore - Rajarajeswari Nagar","state":"Karnataka"},{"city":"Bangalore - Whitefield","state":"Karnataka"},{"city":"Bangalore - Yelahanka","state":"Karnataka"},{"city":"Bangalore","state":"Karnataka"},{"city":"Hyderabad","state":"Telangana"},{"city":"Ahmedabad","state":"Gujarat"},{"city":"Chennai - Adyar","state":"Tamil Nadu"},{"city":"Chennai - Annanagar","state":"Tamil Nadu"},{"city":"Chennai - Ashok Nagar","state":"Tamil Nadu"},{"city":"Chennai - Chromepet","state":"Tamil Nadu"},{"city":"Chennai - Egmore","state":"Tamil Nadu"},{"city":"Chennai - Medavakkam","state":"Tamil Nadu"},{"city":"Chennai - Mylapore","state":"Tamil Nadu"},{"city":"Chennai - OMR","state":"Tamil Nadu"},{"city":"Chennai - Parry\u2019s","state":"Tamil Nadu"},{"city":"Chennai - Porur","state":"Tamil Nadu"},{"city":"Chennai - Red Hills","state":"Tamil Nadu"},{"city":"Chennai - Shollinganallur","state":"Tamil Nadu"},{"city":"Chennai - T Nagar","state":"Tamil Nadu"},{"city":"Chennai - Thoraipakkam","state":"Tamil Nadu"},{"city":"Chennai - Vadapalani","state":"Tamil Nadu"},{"city":"Chennai - Vandalur","state":"Tamil Nadu"},{"city":"Chennai","state":"Tamil Nadu"},{"city":"Kolkata","state":"West Bengal"},{"city":"Surat","state":"Gujarat"},{"city":"Pune - Baner","state":"Maharashtra"},{"city":"Pune - Hadapsar","state":"Maharashtra"},{"city":"Pune - Kothrud","state":"Maharashtra"},{"city":"Pune - Nagar Road","state":"Maharashtra"},{"city":"Pune - Pimpri Chichwad","state":"Maharashtra"},{"city":"Pune - Satara Road","state":"Maharashtra"},{"city":"Pune - Shivaji Nagar","state":"Maharashtra"},{"city":"Pune","state":"Maharashtra"},{"city":"Jaipur","state":"Rajasthan"},{"city":"Lucknow","state":"Uttar Pradesh"},{"city":"Kanpur","state":"Uttar Pradesh"},{"city":"Nagpur","state":"Maharashtra"},{"city":"Indore","state":"Madhya Pradesh"},{"city":"Thane","state":"Maharashtra"},{"city":"Bhopal","state":"Madhya Pradesh"},{"city":"Visakhapatnam","state":"Andhra Pradesh"},{"city":"Patna","state":"Bihar"},{"city":"Vadodara","state":"Gujarat"},{"city":"Ghaziabad","state":"Uttar Pradesh"},{"city":"Ludhiana","state":"Punjab"},{"city":"Agra","state":"Uttar Pradesh"},{"city":"Nashik","state":"Maharashtra"},{"city":"Faridabad","state":"Haryana"},{"city":"Meerut","state":"Uttar Pradesh"},{"city":"Rajkot","state":"Gujarat"},{"city":"Kalyan","state":"Maharashtra"},{"city":"Vasai Virar","state":"Maharashtra"},{"city":"Varanasi","state":"Uttar Pradesh"},{"city":"Srinagar","state":"Jammu and Kashmir"},{"city":"Aurangabad","state":"Maharashtra"},{"city":"Dhanbad","state":"Jharkhand"},{"city":"Amritsar","state":"Punjab"},{"city":"Navi Mumbai","state":"Maharashtra"},{"city":"Allahabad","state":"Uttar Pradesh"},{"city":"Ranchi","state":"Jharkhand"},{"city":"Haora","state":"West Bengal"},{"city":"Coimbatore","state":"Tamil Nadu"},{"city":"Jabalpur","state":"Madhya Pradesh"},{"city":"Gwalior","state":"Madhya Pradesh"},{"city":"Vijayawada","state":"Andhra Pradesh"},{"city":"Jodhpur","state":"Rajasthan"},{"city":"Madurai","state":"Tamil Nadu"},{"city":"Raipur","state":"Chhattisgarh"},{"city":"Kota","state":"Rajasthan"},{"city":"Guwahati","state":"Assam"},{"city":"Chandigarh","state":"Chandigarh"},{"city":"Solapur","state":"Maharashtra"},{"city":"Hubli and Dharwad","state":"Karnataka"},{"city":"Bareilly","state":"Uttar Pradesh"},{"city":"Moradabad","state":"Uttar Pradesh"},{"city":"Mysore","state":"Karnataka"},{"city":"Gurgaon","state":"Haryana"},{"city":"Aligarh","state":"Uttar Pradesh"},{"city":"Jalandhar","state":"Punjab"},{"city":"Tiruchirappalli","state":"Tamil Nadu"},{"city":"Bhubaneswar","state":"Odisha (Orissa)"},{"city":"Salem","state":"Tamil Nadu"},{"city":"Mira and Bhayander","state":"Maharashtra"},{"city":"Kerala - Thiruvananthapuram","state":"Kerala"},{"city":"Kerala - Thrissur","state":"Kerala"},{"city":"Kerala - Kottayam","state":"Kerala"},{"city":"Kerala - Malappuram","state":"Kerala"},{"city":"Kerala - Kannur","state":"Kerala"},{"city":"Bhiwandi","state":"Maharashtra"},{"city":"Saharanpur","state":"Uttar Pradesh"},{"city":"Gorakhpur","state":"Uttar Pradesh"},{"city":"Guntur","state":"Andhra Pradesh"},{"city":"Bikaner","state":"Rajasthan"},{"city":"Amravati","state":"Maharashtra"},{"city":"Noida","state":"Uttar Pradesh"},{"city":"Jamshedpur","state":"Jharkhand"},{"city":"Bhilai Nagar","state":"Chhattisgarh"},{"city":"Warangal","state":"Telangana"},{"city":"Cuttack","state":"Odisha (Orissa)"},{"city":"Firozabad","state":"Uttar Pradesh"},{"city":"Kochi","state":"Kerala"},{"city":"Bhavnagar","state":"Gujarat"},{"city":"Dehradun","state":"Uttarakhand"},{"city":"Durgapur","state":"West Bengal"},{"city":"Asansol","state":"West Bengal"},{"city":"Nanded WaghalaNanded","state":"Maharashtra"},{"city":"Kolhapur","state":"Maharashtra"},{"city":"Ajmer","state":"Rajasthan"},{"city":"Gulbarga","state":"Karnataka"},{"city":"Jamnagar","state":"Gujarat"},{"city":"Ujjain","state":"Madhya Pradesh"},{"city":"Loni","state":"Uttar Pradesh"},{"city":"Siliguri","state":"West Bengal"},{"city":"Jhansi","state":"Uttar Pradesh"},{"city":"Ulhasnagar","state":"Maharashtra"},{"city":"Nellore","state":"Andhra Pradesh"},{"city":"Jammu","state":"Jammu and Kashmir"},{"city":"Sangli Miraj Kupwad","state":"Maharashtra"},{"city":"Belgaum","state":"Karnataka"},{"city":"Mangalore","state":"Karnataka"},{"city":"Ambattur","state":"Tamil Nadu"},{"city":"Tirunelveli","state":"Tamil Nadu"},{"city":"Malegaon","state":"Maharashtra"},{"city":"Gaya","state":"Bihar"},{"city":"Jalgaon","state":"Maharashtra"},{"city":"Udaipur","state":"Rajasthan"},{"city":"Maheshtala","state":"West Bengal"}]
         */

        private String country_name;
        private String country_isd;
        private String display;
        private List<CitiesBean> cities;

        public String getCountry_name() {
            return country_name;
        }

        public void setCountry_name(String country_name) {
            this.country_name = country_name;
        }

        public String getCountry_isd() {
            return country_isd;
        }

        public void setCountry_isd(String country_isd) {
            this.country_isd = country_isd;
        }

        public String getDisplay() {
            return display;
        }

        public void setDisplay(String display) {
            this.display = display;
        }

        public List<CitiesBean> getCities() {
            return cities;
        }

        public void setCities(List<CitiesBean> cities) {
            this.cities = cities;
        }

        public static class CitiesBean {
            /**
             * city : Mumbai - Airoli
             * state : Maharashtra
             */

            private String city;
            private String state;

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }
        }
    }
}
