package tn.jardindart.controllers.Blog;

import java.sql.SQLException;
import java.util.List;

public interface dislikeservice<T> {

        public void adddislike(T t) throws SQLException;
        public List<T> showdislike() throws SQLException;
        public void deletedislike(int id) throws SQLException;
        public void editdislike(T t) throws SQLException;

    }
