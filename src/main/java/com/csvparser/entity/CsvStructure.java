package com.csvparser.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "csvstructure")
public class CsvStructure {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "experience_date")
    public Timestamp experienceDate;

    @Column(name = "campaign_id")
    public String campaignId;

    @Column(name = "campaign_name")
    public String campaignName;

    @Column(name = "user_session_id")
    public String userSessionId;

    @Column(name = "source")
    public String source;

    @Column(name = "opt_in")
    public Boolean optIn;

    @Column(name = "first_name")
    public String firstName;

    @Column(name = "last_name")
    public String lastName;

    @Column(name = "address")
    public String address;

    @Column(name = "phone_number")
    public String phone;

    @Column(name = "city")
    public String city;

    @Column(name = "state")
    public String state;

    @Column(name = "postal_code")
    public String zipCode;

    @Column(name = "favorite_color")
    public String color;

    public CsvStructure() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Timestamp getExperienceDate() {
        return experienceDate;
    }

    public void setExperienceDate(Timestamp experienceDate) {
        this.experienceDate = experienceDate;
    }

    public String getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(String campaignId) {
        this.campaignId = campaignId;
    }

    public String getCampaignName() {
        return campaignName;
    }

    public void setCampaignName(String campaignName) {
        this.campaignName = campaignName;
    }

    public String getUserSessionId() {
        return userSessionId;
    }

    public void setUserSessionId(String userSessionId) {
        this.userSessionId = userSessionId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Boolean getOptIn() {
        return optIn;
    }

    public void setOptIn(Boolean optIn) {
        this.optIn = optIn;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

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

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
//
//    @Override
//    public String toString() {
//        return "CsvStructure{" +
//                "id=" + id +
//                ", experienceDate=" + experienceDate +
//                ", campaignId='" + campaignId + '\'' +
//                ", campaignName='" + campaignName + '\'' +
//                ", userSessionId='" + userSessionId + '\'' +
//                ", source='" + source + '\'' +
//                ", optIn=" + optIn +
//                ", firstName='" + firstName + '\'' +
//                ", lastName='" + lastName + '\'' +
//                ", address='" + address + '\'' +
//                ", phone='" + phone + '\'' +
//                ", city='" + city + '\'' +
//                ", state='" + state + '\'' +
//                ", zipCode='" + zipCode + '\'' +
//                ", color='" + color + '\'' +
//                '}';
//    }
}
