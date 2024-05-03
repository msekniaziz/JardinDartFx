module tn.jardindart{
    exports tn.jardindart;
    exports tn.jardindart.controllers.Prdtroc;
    opens tn.jardindart.controllers.Prdtroc to javafx.fxml;
    opens tn.jardindart.controllers.Ads to javafx.fxml ;
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
   // requires flying.saucer.pdf;
    requires java.desktop;
    requires com.jfoenix;
  //  requires org.apache.pdfbox;
    requires bcrypt;
    requires jdk.jsobject;
    requires stripe.java;
    requires kernel;
    requires io;
    requires layout;
    requires com.google.zxing;
    requires javafx.swing;
    requires org.apache.poi.poi;
    requires org.apache.pdfbox;
    requires itextpdf;
    requires java.net.http;
    requires org.apache.httpcomponents.httpclient;
    requires org.apache.httpcomponents.httpcore;
    requires facebook4j.core;
    //  requires java.mail;
    opens tn.jardindart.controllers;
    opens tn.jardindart.utils;
    opens tn.jardindart.models;
    opens tn.jardindart.controllers.user;
    opens tn.jardindart.controllers.ptCollect;
    opens tn.jardindart.controllers.prodR;
    opens tn.jardindart.controllers.Association;
    opens tn.jardindart.controllers.DA;
    opens tn.jardindart.controllers.DBM;
    exports tn.jardindart.test; // Exporter le package tn.jardindart.test
    exports tn.jardindart.controllers.Blog;
    opens tn.jardindart.controllers.Blog to javafx.fxml;


}