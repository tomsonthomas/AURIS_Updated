package com.admin.auris_updated;
    import java.util.ArrayList;
    import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

    public class LoginPogo {

        @SerializedName("status")
        @Expose
        private Integer status;
        @SerializedName("info")
        @Expose
        private List<Info> info = new ArrayList<>();
        public class Info {

            @SerializedName("id")
            @Expose
            private String id;
            @SerializedName("name")
            @Expose
            private String name;
            @SerializedName("age")
            @Expose
            private String age;
            @SerializedName("gender")
            @Expose
            private String gender;
            @SerializedName("phn")
            @Expose
            private String phn;
            @SerializedName("uname")
            @Expose
            private String uname;
            @SerializedName("pswd")
            @Expose
            private String pswd;
            @SerializedName("type")
            @Expose
            private String type;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getAge() {
                return age;
            }

            public void setAge(String age) {
                this.age = age;
            }

            public String getGender() {
                return gender;
            }

            public void setGender(String gender) {
                this.gender = gender;
            }

            public String getPhn() {
                return phn;
            }

            public void setPhn(String phn) {
                this.phn = phn;
            }

            public String getUname() {
                return uname;
            }

            public void setUname(String uname) {
                this.uname = uname;
            }

            public String getPswd() {
                return pswd;
            }

            public void setPswd(String pswd) {
                this.pswd = pswd;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

        }
        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public List<Info> getInfo() {
            return info;
        }

        public void setInfo(List<Info> info) {
            this.info = info;
        }

    }

