package tn.esprit.pifx.models;
import tn.esprit.pifx.utils.MyDataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate ;

public class User {

    private int id;
    private String nom;
    private String prenom;
    private String mail;
    private String tel;
    private String gender;
    private String password;
    private int nb_points;
    private String age;
    private LocalDate date_birthday;
    private String confirmpassword;
    private String status = "inactive";
    private String role = "USER";

    // Default constructor
    public User() {
    }

    public User(int id, String nom, String prenom, String mail, String tel, String gender, int nb_points, LocalDate date_birthday , String status) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.tel = tel;
        this.gender = gender;
        this.nb_points = nb_points;
        this.date_birthday = date_birthday;
        this.status = status ;
    }

    public User(int id, String nom, String prenom, String mail, String tel, String gender,
                String password, int nb_points, String age, LocalDate date_birthday, String confirmpassword) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.tel = tel;
        this.gender = gender;
        this.password = password;
        this.nb_points = nb_points;
        this.age = age;
        this.date_birthday = date_birthday;
        this.confirmpassword = confirmpassword;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getNb_points() {
        return nb_points;
    }

    public void setNb_points(int nb_points) {
        this.nb_points = nb_points;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public LocalDate getDate_birthday() {
        return date_birthday;
    }

    public void setDate_birthday(LocalDate date_birthday) {
        this.date_birthday = date_birthday;
    }

    public String getConfirmpassword() {
        return confirmpassword;
    }

    public void setConfirmpassword(String confirmpassword) {
        this.confirmpassword = confirmpassword;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean addUser(String nom, String prenom, String mail, String tel, String gender, String password, int nb_points, String age, LocalDate date_birthday, String confirmpassword)
    {
        MyDataBase db=MyDataBase.getInstance();
        Connection connection = db.getCnx();
        if(connection != null)
        {
            String sql = "INSERT INTO user (nom, prenom, mail, tel, gender, password, nb_points, age, date_birthday, confirmpassword, status, role) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, nom);
                stmt.setString(2, prenom);
                stmt.setString(3, mail);
                stmt.setString(4, tel);
                stmt.setString(5, gender);
                stmt.setString(6, password);
                stmt.setInt(7, nb_points);
                stmt.setString(8, age);
                stmt.setObject(9, date_birthday);
                stmt.setString(10, confirmpassword);
                stmt.setString(11, "inactive");
                stmt.setString(12, "USER");
                int rowsInserted = stmt.executeUpdate();
                return rowsInserted > 0;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }
}