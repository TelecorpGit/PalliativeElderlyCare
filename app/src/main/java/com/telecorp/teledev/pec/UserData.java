package com.telecorp.teledev.pec;

public class UserData {
    /**
     * status : S
     * message :
     * data : {"lastName":"นันทโชคณัชพงษ์","address":"138/82","gender":"M","provinceCode":"10","hashVersion":"fa3376e90be5ab3ee0ab6f7dc1717ff7","birthDate":"09/03/1993","phoneNo":"0834567891","picture":"","allergic":"ไม่มี","firstName":"ณธีนนท์","tambolCode":"100101","location":"123,321","citizenId":"1103701042486","userType":"P","email":"nateenon01@gmail.com","underlyingDisease":"ไม่มีโรค","amphurCode":"1001","username":"nut4"}
     */

    private Boolean success;
    private String status;
    private String message;
    private DataBean data;


    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * lastName : นันทโชคณัชพงษ์
         * address : 138/82
         * gender : M
         * provinceCode : 10
         * hashVersion : fa3376e90be5ab3ee0ab6f7dc1717ff7
         * birthDate : 09/03/1993
         * phoneNo : 0834567891
         * picture :
         * allergic : ไม่มี
         * firstName : ณธีนนท์
         * tambolCode : 100101
         * location : 123,321
         * citizenId : 1103701042486
         * userType : P
         * email : nateenon01@gmail.com
         * underlyingDisease : ไม่มีโรค
         * amphurCode : 1001
         * username : nut4
         */

//        private String lastName;
//        private String address;
//        private String gender;
//        private String provinceCode;
//        private String hashVersion;
//        private String birthDate;
//        private String phoneNo;
//        private String picture;
//        private String allergic;
//        private String firstName;
//        private String tambolCode;
//        private String location;
//        private String citizenId;
//        private String userType;
//        private String email;
//        private String underlyingDisease;
//        private String amphurCode;
        private String picture;
        private String role_name;
        private String role_id;
        private String login_type;
        private String location;
        private String phone_no;
        private String tambol_code;
        private String amphur_code;
        private String province_code;
        private String address;
        private String email;
        private String birth_date;
        private String allergic;
        private String underlying_disease;
        private String gender;
        private String case_owner;
        private String title_name;
        private String citizen_id;
        private String user_type;
        private String last_name;
        private String first_name;
        private String username;
        private int userid;

        public int getUserid() {
            return userid;
        }

        public void setUserid(int userid) {
            this.userid = userid;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        public String getRole_name() {
            return role_name;
        }

        public void setRole_name(String role_name) {
            this.role_name = role_name;
        }

        public String getRole_id() {
            return role_id;
        }

        public void setRole_id(String role_id) {
            this.role_id = role_id;
        }

        public String getLogin_type() {
            return login_type;
        }

        public void setLogin_type(String login_type) {
            this.login_type = login_type;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getPhone_no() {
            return phone_no;
        }

        public void setPhone_no(String phone_no) {
            this.phone_no = phone_no;
        }

        public String getTambol_code() {
            return tambol_code;
        }

        public void setTambol_code(String tambol_code) {
            this.tambol_code = tambol_code;
        }

        public String getAmphur_code() {
            return amphur_code;
        }

        public void setAmphur_code(String amphur_code) {
            this.amphur_code = amphur_code;
        }

        public String getProvince_code() {
            return province_code;
        }

        public void setProvince_code(String province_code) {
            this.province_code = province_code;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getBirth_date() {
            return birth_date;
        }

        public void setBirth_date(String birth_date) {
            this.birth_date = birth_date;
        }

        public String getAllergic() {
            return allergic;
        }

        public void setAllergic(String allergic) {
            this.allergic = allergic;
        }

        public String getUnderlying_disease() {
            return underlying_disease;
        }

        public void setUnderlying_disease(String underlying_disease) {
            this.underlying_disease = underlying_disease;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getCase_owner() {
            return case_owner;
        }

        public void setCase_owner(String case_owner) {
            this.case_owner = case_owner;
        }

        public String getTitle_name() {
            return title_name;
        }

        public void setTitle_name(String title_name) {
            this.title_name = title_name;
        }

        public String getCitizen_id() {
            return citizen_id;
        }

        public void setCitizen_id(String citizen_id) {
            this.citizen_id = citizen_id;
        }

        public String getUser_type() {
            return user_type;
        }

        public void setUser_type(String user_type) {
            this.user_type = user_type;
        }

        public String getLast_name() {
            return last_name;
        }

        public void setLast_name(String last_name) {
            this.last_name = last_name;
        }

        public String getFirst_name() {
            return first_name;
        }

        public void setFirst_name(String first_name) {
            this.first_name = first_name;
        }

        //        public String getLastName() {
//            return lastName;
//        }
//
//        public void setLastName(String lastName) {
//            this.lastName = lastName;
//        }
//
//        public String getAddress() {
//            return address;
//        }
//
//        public void setAddress(String address) {
//            this.address = address;
//        }
//
//        public String getGender() {
//            return gender;
//        }
//
//        public void setGender(String gender) {
//            this.gender = gender;
//        }
//
//        public String getProvinceCode() {
//            return provinceCode;
//        }
//
//        public void setProvinceCode(String provinceCode) {
//            this.provinceCode = provinceCode;
//        }
//
//        public String getHashVersion() {
//            return hashVersion;
//        }
//
//        public void setHashVersion(String hashVersion) {
//            this.hashVersion = hashVersion;
//        }
//
//        public String getBirthDate() {
//            return birthDate;
//        }
//
//        public void setBirthDate(String birthDate) {
//            this.birthDate = birthDate;
//        }
//
//        public String getPhoneNo() {
//            return phoneNo;
//        }
//
//        public void setPhoneNo(String phoneNo) {
//            this.phoneNo = phoneNo;
//        }
//
//        public String getPicture() {
//            return picture;
//        }
//
//        public void setPicture(String picture) {
//            this.picture = picture;
//        }
//
//        public String getAllergic() {
//            return allergic;
//        }
//
//        public void setAllergic(String allergic) {
//            this.allergic = allergic;
//        }
//
//        public String getFirstName() {
//            return firstName;
//        }
//
//        public void setFirstName(String firstName) {
//            this.firstName = firstName;
//        }
//
//        public String getTambolCode() {
//            return tambolCode;
//        }
//
//        public void setTambolCode(String tambolCode) {
//            this.tambolCode = tambolCode;
//        }
//
//        public String getLocation() {
//            return location;
//        }
//
//        public void setLocation(String location) {
//            this.location = location;
//        }
//
//        public String getCitizenId() {
//            return citizenId;
//        }
//
//        public void setCitizenId(String citizenId) {
//            this.citizenId = citizenId;
//        }
//
//        public String getUserType() {
//            return userType;
//        }
//
//        public void setUserType(String userType) {
//            this.userType = userType;
//        }
//
//        public String getEmail() {
//            return email;
//        }
//
//        public void setEmail(String email) {
//            this.email = email;
//        }
//
//        public String getUnderlyingDisease() {
//            return underlyingDisease;
//        }
//
//        public void setUnderlyingDisease(String underlyingDisease) {
//            this.underlyingDisease = underlyingDisease;
//        }
//
//        public String getAmphurCode() {
//            return amphurCode;
//        }
//
//        public void setAmphurCode(String amphurCode) {
//            this.amphurCode = amphurCode;
//        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
}
