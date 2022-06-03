package com.example.pro_xi;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;

public class Players {

    String FirstName;
    String LastName;
    String Club;
    Integer Value;
    Integer Salary;
    Integer Goals;
    Integer Assists;
    Integer Jersey;
    String Country;
    String PlayerImage;
    Double Height;
    String position;

    public Players()
    {

    }

    public Players(String firstName, String lastName, String club, Integer value, Integer salary, Integer goals, Integer assists, Integer jersey, String country, String playerImage, Double height, String position) {
        FirstName = firstName;
        LastName = lastName;
        Club = club;
        Value = value;
        Salary = salary;
        Goals = goals;
        Assists = assists;
        Jersey = jersey;
        Country = country;
        PlayerImage = playerImage;
        Height = height;
        this.position = position;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getClub() {
        return Club;
    }

    public void setClub(String club) {
        Club = club;
    }

    public Integer getValue() {
        return Value;
    }

    public void setValue(Integer value) {
        Value = value;
    }

    public Integer getSalary() {
        return Salary;
    }

    public void setSalary(Integer salary) {
        Salary = salary;
    }

    public Integer getGoals() {
        return Goals;
    }

    public void setGoals(Integer goals) {
        Goals = goals;
    }

    public Integer getAssists() {
        return Assists;
    }

    public void setAssists(Integer assists) {
        Assists = assists;
    }

    public Integer getJersey() {
        return Jersey;
    }

    public void setJersey(Integer jersey) {
        Jersey = jersey;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getPlayerImage() {
        return PlayerImage;
    }

    public void setPlayerImage(String playerImage) {
        PlayerImage = playerImage;
    }

    public Double getHeight() {
        return Height;
    }

    public void setHeight(Double height) {
        Height = height;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
