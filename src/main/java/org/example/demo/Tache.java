package org.example.demo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Tache {
    private int id;
    private String titre;
    private String description;
    private LocalDate dateCreation;
    private LocalDate dateLimite;
    private String priorite;
    private String categorie;
    private String status;
    private List<Employe> employesAssignes;
    private Projet projetParent;

    public Tache(int id, String titre, String description, LocalDate dateLimite, Projet projetParent) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.dateCreation = LocalDate.now();
        this.dateLimite = dateLimite;
        this.priorite = "MOYENNE";
        this.categorie = "AUTRE";
        this.status = "À FAIRE";
        this.employesAssignes = new ArrayList<>();
        this.projetParent = projetParent;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDate getDateCreation() { return dateCreation; }
    public void setDateCreation(LocalDate dateCreation) { this.dateCreation = dateCreation; }

    public LocalDate getDateLimite() { return dateLimite; }
    public void setDateLimite(LocalDate dateLimite) { this.dateLimite = dateLimite; }

    public String getPriorite() { return priorite; }
    public void setPriorite(String priorite) { this.priorite = priorite; }

    public String getCategorie() { return categorie; }
    public void setCategorie(String categorie) { this.categorie = categorie; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public List<Employe> getEmployesAssignes() { return employesAssignes; }
    public void setEmployesAssignes(List<Employe> employesAssignes) { this.employesAssignes = employesAssignes; }

    public Projet getProjetParent() { return projetParent; }
    public void setProjetParent(Projet projetParent) { this.projetParent = projetParent; }

    public void addEmploye(Employe employe) {
        if (!employesAssignes.contains(employe)) {
            employesAssignes.add(employe);
        }
    }

    public void removeEmploye(Employe employe) {
        employesAssignes.remove(employe);
    }
}
