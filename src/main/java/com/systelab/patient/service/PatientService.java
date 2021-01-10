package com.systelab.patient.service;

import com.systelab.patient.model.Patient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class PatientService {

    @Inject
    EntityManager em;

    protected PatientService() {
    }

    public List<Patient> toList() {
        Query query = em.createQuery("SELECT e FROM Patient e");
        return (List<Patient>) query.getResultList();
    }

    public Patient get(String id) {
        Patient patient = em.find(Patient.class, new Long(id));
        return Patient.of(patient.getName(), patient.getSurname());
    }

    @Transactional
    public Patient add(Patient patient) {
        System.out.println("Add patient");
        System.out.println(patient.getName());
        System.out.println(patient.getSurname());
        em.persist(patient);
        return patient;
    }

    @Transactional
    public Patient delete(Patient patient) {
        em.remove(patient);
        return patient;
    }
}
