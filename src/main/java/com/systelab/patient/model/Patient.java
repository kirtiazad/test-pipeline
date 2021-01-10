package com.systelab.patient.model;

import com.google.common.collect.Lists;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkNotNull;

@Entity
public class Patient implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String surname;

    @ElementCollection
    private List<PhoneNumber> phones = Lists.newLinkedList();


    protected Patient() {
    }

    private Patient(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public static Patient of(String name, String surname) {
        checkNotNull(name);
        checkNotNull(surname);

        Patient patient = new Patient(name, surname);
        return patient;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public void setName(String name) {
        this.name=name;
    }

    public void setSurname(String surname) {
        this.surname=surname;
    }


    public PhoneNumbers getPhones() {
        return PhoneNumbers.of(phones);
    }

    public void addPhone(PhoneNumber phone) {
        checkNotNull(phone);
        if (!phones.contains(phone)) {
            phones.add(phone);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Patient)) {
            return false;
        }

        Patient other = (Patient) obj;

        return Objects.equals(other.name, this.name) && Objects.equals(other.surname, this.surname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name, this.surname);
    }
}