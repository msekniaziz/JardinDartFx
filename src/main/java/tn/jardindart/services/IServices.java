package tn.jardindart.services;

import tn.jardindart.models.Categories;

import java.sql.SQLException;
import java.util.ArrayList;




public interface IServices<T> {
    void add (T t,int a) throws SQLException;
    ArrayList<T> getAll();
    void update(T t,int a);
    boolean delete(T t);
    boolean deleteAll();

    void update(Categories categories);
}