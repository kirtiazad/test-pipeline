package com.systelab.patient.controller;


import com.systelab.patient.model.Patient;
import com.systelab.patient.service.PatientService;
import org.eclipse.microprofile.jwt.JsonWebToken;

import javax.annotation.security.PermitAll;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@RequestScoped
@Path("/patients")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PatientController {

    @Inject
    PatientService service;

    @Inject
    JsonWebToken jwt;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public List<Patient> list() {
        return this.service.toList();
    }

    @GET
    @Path("/{id}")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public Patient get(@PathParam("id") String id) {
        return this.service.get(id);
    }

    @POST
    @Path("/patient")
    public Patient add(Patient patient) {
        return this.service.add(patient);
    }

    @DELETE
    @Path("/patient")
    public Patient delete(Patient patient) {
        return this.service.delete(patient);
    }
}