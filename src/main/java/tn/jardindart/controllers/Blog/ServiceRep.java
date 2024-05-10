package tn.jardindart.controllers.Blog;

import tn.jardindart.models.ReponseBlog;

import java.sql.SQLException;
import java.util.List;

public interface ServiceRep <T> {
    public void addrep(T t) throws SQLException;
    public List<T> showrep() throws SQLException;
    public void deleterep(int id) throws SQLException;
    public void editrep(T t) throws SQLException;

    List<ReponseBlog> readAll();
}
