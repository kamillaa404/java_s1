package org.example.demo;
import org.example.demo.Employe;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Projet {
    private int id;
    private String nom;
    private String description;
    private LocalDate dateDebut;
    private LocalDate dateLimite;
    private List<Employe> employes;
    private String status;

    public Projet(int id, String nom, String description, LocalDate dateDebut, LocalDate dateLimite) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.dateDebut = dateDebut;
        this.dateLimite = dateLimite;
        this.employes = new ArrayList<>();
        this.status = "En cours";
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDate getDateDebut() { return dateDebut; }
    public void setDateDebut(LocalDate dateDebut) { this.dateDebut = dateDebut; }

    public LocalDate getDateLimite() { return dateLimite; }
    public void setDateLimite(LocalDate dateLimite) { this.dateLimite = dateLimite; }

    public List<Employe> getEmployes() { return employes; }
    public void setEmployes(List<Employe> employes) { this.employes = employes; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public void addEmploye(Employe employe) {
        if (!employes.contains(employe)) {
            employes.add(employe);
        }
    }
    public void removeEmploye(Employe employe) {
        employes.remove(employe);
    }
}