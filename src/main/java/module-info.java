module tn.jardindart{
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;
    requires org.controlsfx.controls;
    requires org.apache.commons.codec;
    requires javafx.web;
    requires twilio;
    requires slf4j.simple;
    requires jakarta.mail;
    requires com.google.common;
    requires bcrypt;
    opens tn.jardindart.controllers;
    opens tn.jardindart.utils;
    opens tn.jardindart.entites;
}