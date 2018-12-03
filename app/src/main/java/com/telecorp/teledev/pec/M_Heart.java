package com.telecorp.teledev.pec;

import java.util.List;

public class M_Heart {
    /**
     * output : [{"data_id":"154","category_id":"27","category_name":"อัตราการเต้นของหัวใจ","name_user":"user name","detail_user":"MacID :78:02:B7:61:0D:CE","measurement_user":"89","date_user":"อ. 23 ต.ค. 2018","time_user":"14:07:28","counts_user":"ครั้งที่ 4 ","userId_user":"id user","date":"2018-10-23"},{"data_id":"153","category_id":"27","category_name":"อัตราการเต้นของหัวใจ","name_user":"user name","detail_user":"MacID :78:02:B7:61:0D:CE","measurement_user":"86","date_user":"อ. 23 ต.ค. 2018","time_user":"14:07:22","counts_user":"ครั้งที่ 3 ","userId_user":"id user","date":"2018-10-23"},{"data_id":"152","category_id":"27","category_name":"อัตราการเต้นของหัวใจ","name_user":"user name","detail_user":"MacID :78:02:B7:61:0D:CE","measurement_user":"86","date_user":"อ. 23 ต.ค. 2018","time_user":"14:07:20","counts_user":"ครั้งที่ 2 ","userId_user":"id user","date":"2018-10-23"},{"data_id":"151","category_id":"27","category_name":"อัตราการเต้นของหัวใจ","name_user":"user name","detail_user":"MacID :78:02:B7:61:0D:CE","measurement_user":"83","date_user":"อ. 23 ต.ค. 2018","time_user":"14:07:18","counts_user":"ครั้งที่ 1 ","userId_user":"id user","date":"2018-10-23"}]
     * status : true
     */

    private boolean status;
    private List<OutputBean> output;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<OutputBean> getOutput() {
        return output;
    }

    public void setOutput(List<OutputBean> output) {
        this.output = output;
    }

    public static class OutputBean {
        /**
         * data_id : 154
         * category_id : 27
         * category_name : อัตราการเต้นของหัวใจ
         * name_user : user name
         * detail_user : MacID :78:02:B7:61:0D:CE
         * measurement_user : 89
         * date_user : อ. 23 ต.ค. 2018
         * time_user : 14:07:28
         * counts_user : ครั้งที่ 4
         * userId_user : id user
         * date : 2018-10-23
         */

        private String data_id;
        private String category_id;
        private String category_name;
        private String name_user;
        private String detail_user;
        private String measurement_user;
        private String date_user;
        private String time_user;
        private String counts_user;
        private String userId_user;
        private String date;

        public String getData_id() {
            return data_id;
        }

        public void setData_id(String data_id) {
            this.data_id = data_id;
        }

        public String getCategory_id() {
            return category_id;
        }

        public void setCategory_id(String category_id) {
            this.category_id = category_id;
        }

        public String getCategory_name() {
            return category_name;
        }

        public void setCategory_name(String category_name) {
            this.category_name = category_name;
        }

        public String getName_user() {
            return name_user;
        }

        public void setName_user(String name_user) {
            this.name_user = name_user;
        }

        public String getDetail_user() {
            return detail_user;
        }

        public void setDetail_user(String detail_user) {
            this.detail_user = detail_user;
        }

        public String getMeasurement_user() {
            return measurement_user;
        }

        public void setMeasurement_user(String measurement_user) {
            this.measurement_user = measurement_user;
        }

        public String getDate_user() {
            return date_user;
        }

        public void setDate_user(String date_user) {
            this.date_user = date_user;
        }

        public String getTime_user() {
            return time_user;
        }

        public void setTime_user(String time_user) {
            this.time_user = time_user;
        }

        public String getCounts_user() {
            return counts_user;
        }

        public void setCounts_user(String counts_user) {
            this.counts_user = counts_user;
        }

        public String getUserId_user() {
            return userId_user;
        }

        public void setUserId_user(String userId_user) {
            this.userId_user = userId_user;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }
}
