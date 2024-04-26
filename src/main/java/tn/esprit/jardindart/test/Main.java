package tn.esprit.jardindart.test;

import tn.esprit.jardindart.models.Association;
import tn.esprit.jardindart.models.DonArgent;
import tn.esprit.jardindart.models.DonBienMateriel;
import tn.esprit.jardindart.services.AssociationService;
import tn.esprit.jardindart.services.DonArgentService;
import tn.esprit.jardindart.services.DonBienMaterielService;
import tn.esprit.jardindart.utils.MyDataBase;

import java.sql.SQLException;
import java.sql.*;

public class Main {
    public static void main(String[] args){

        MyDataBase db=MyDataBase.getInstance();

        //Association a = new Association(37,1234566,"Grain desp","ariana","photo"," recherche de chaise ");//lel modif wel ajout nahi menha l id
        //AssociationService as = new AssociationService();
        //Date date = new Date(124, 2, 29);
       // DonArgent da=new DonArgent(1400,date,  37, 27);
        //DonArgentService das = new DonArgentService();
        //das.ajouter(da);


        //DonBienMateriel dbm=new DonBienMateriel(57,"azizdon","taswiret aziz",false,37,27);
        //DonBienMaterielService dbs = new DonBienMaterielService();
        //dbs.ajouter(dbm);
        //dbs.modifier(dbm);
        //dbs.supprimer(55);
        //System.out.println(dbs.afficher());



        //das.modifier(da);

            //das.supprimer(43);
        //System.out.println(das.afficher());



        //as.modifier(a);


    }
}
