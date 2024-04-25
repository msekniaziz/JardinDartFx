package tn.esprit.applicationgui.Controllers;

import java.sql.SQLException;
import java.util.List;

public interface Service <T>{
    public void addblog(T t) throws SQLException;
    public List<T> showblog() throws SQLException;
    public void deleteblog(int id) throws SQLException;
    public void editblog(T t) throws SQLException;




}