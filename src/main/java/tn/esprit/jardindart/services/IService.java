package tn.esprit.jardindart.services;

import tn.esprit.jardindart.models.DonBienMateriel;

import java.util.List;

public interface IService<T> {
    void ajouter(T t);
    void modifier(T t);
    void supprimer(int id);
    List<T> afficher();
    Object getById(int id);


}
