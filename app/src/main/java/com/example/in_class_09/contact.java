package com.example.in_class_09;

public class contact {

    String fname,email,phone,url,id,lname;

    public contact() {
    }

    public contact(String fname, String email, String phone, String url, String id, String lname) {
        this.fname = fname;
        this.email = email;
        this.phone = phone;
        this.url = url;
        this.id = id;
        this.lname = lname;

    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    @Override
    public String toString() {
        return "contact{" +
                "fname='" + fname + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", url='" + url + '\'' +
                ", id='" + id + '\'' +
                ", lname='" + lname + '\'' +
                '}';
    }
}
