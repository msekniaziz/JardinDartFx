package tn.esprit.applicationgui.Controllers;

import java.sql.SQLException;
import java.util.List;

public interface likeservice<T> {
    public void addlike(T t) throws SQLException;
    public List<T> showlike(int user_id) throws SQLException;
    public void deletelike(int id) throws SQLException;
    public void editlike(T t) throws SQLException;

}
