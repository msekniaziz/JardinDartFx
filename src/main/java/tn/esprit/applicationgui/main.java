package tn.esprit.applicationgui;

import tn.esprit.applicationgui.Controllers.Blogservice;
import tn.esprit.applicationgui.entites.Blog;
import tn.esprit.applicationgui.utils.MyDataBase;

import java.sql.SQLException;

public class main {
    public static void main(String[] args) {
        Blogservice ps =new Blogservice();


        try {
            //ps.addblog(new Blog(1,"blog1","blog1","garden" ));
           // ps.addblog(new Blog(1,"blog2","blog2","home" ));
          //  ps.addblog(new Blog(1,"blog1","blog3","garden" ));

            System.out.println(ps.showblog());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
}
