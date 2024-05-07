package tn.esprit.applicationgui;

import tn.esprit.applicationgui.Controllers.like_blogservice;
import tn.esprit.applicationgui.entites.like_blog;

import java.sql.SQLException;

public class main {
    public static void main(String[] args, int user_id) {
        like_blogservice req =new like_blogservice();


        try {
            req.addlike(new like_blog(1,2,3,4 ));
           // ps.addblog(new Blog(1,"blog2","blog2","home" ));
          //  ps.addblog(new Blog(1,"blog1","blog3","garden" ));

            System.out.println(req.showlike(user_id));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
}
