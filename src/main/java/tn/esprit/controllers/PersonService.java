package tn.esprit.controllers;

import tn.esprit.entities.Person;
import tn.esprit.utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonService implements ICRUD<Person>{

    private Connection conx;
    private Statement stm;
    private PreparedStatement pstm;
    public PersonService(){
        conx = MyDatabase.getInstance().getConx();
    }

    @Override
    public void add(Person person) throws SQLException {

        String req = "INSERT INTO `person`(`name`, `lastname`) VALUES ('"+person.getFirstName()+"','"+person.getLastName()+"')";
        stm = conx.createStatement();
        stm.executeUpdate(req);
        System.out.println("Personne ajoutée");
    }

    @Override
    public void addMeth2(Person person) throws SQLException {
        String req = "INSERT INTO `person`(`name`, `lastname`) VALUES (?,?)";
        pstm = conx.prepareStatement(req);
        pstm.setString(1, person.getFirstName());
        pstm.setString(2, person.getLastName());

        pstm.executeUpdate();
        System.out.println("Personne ajoutée meth 2");


    }

    @Override
    public void modifier(Person person) throws SQLException {

    }

    @Override
    public void delete(Person person) throws SQLException {

    }

    @Override
    public List<Person> afficherList() throws SQLException {
        String req = "SELECT * FROM `person`";

        stm  = conx.createStatement();
        ResultSet res = stm.executeQuery(req);

        List<Person> people = new ArrayList<>();

        while (res.next()){
            people.add(new Person(res.getInt(1),res.getString(2),res.getString(3)));
        }


        return people;
    }
}
