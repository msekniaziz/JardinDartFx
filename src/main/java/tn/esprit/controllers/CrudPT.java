package tn.esprit.controllers;

import tn.esprit.entities.Producttrocwith;
import tn.esprit.entities.Produittroc;

import java.sql.SQLException;
import java.util.List;
public interface CrudPT <T> {

    void addPT(T t) throws SQLException;
    void addMeth2(T t) throws SQLException;
    void modifierPT(T t) throws SQLException;

    void modifierPT1(Producttrocwith producttrocwith, int idprd, String nom, String category, String description, String image);

    void deletePTall() throws SQLException;
    void deletePT(T t) throws SQLException;
    T getProduitById(int id) throws SQLException;
    List<T> afficherListPT() throws SQLException;


}
