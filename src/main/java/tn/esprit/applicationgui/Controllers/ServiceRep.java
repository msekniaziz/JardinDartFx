package tn.esprit.applicationgui.Controllers;

import java.sql.SQLException;
import java.util.List;

public interface ServiceRep <T> {
    public void addrep(T t) throws SQLException;
    public List<T> showrep() throws SQLException;
    public void deleterep(int id) throws SQLException;
    public void editrep(T t) throws SQLException;

}
