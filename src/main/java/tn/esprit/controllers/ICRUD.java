package tn.esprit.controllers;

import java.sql.SQLException;
import java.util.List;

public interface ICRUD<T> {

     void add(T t) throws SQLException;
     void addMeth2(T t) throws SQLException;
     void modifier(T t) throws SQLException;

     void delete(T t) throws SQLException;

     List<T> afficherList() throws SQLException;

}
