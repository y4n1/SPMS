/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.y20.spms.rest;

import com.y20.spms.ejb.RestOperation;
import com.y20.spms.entity.Supervisor;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 *
 * @author Yan's
 */

@Singleton
@Path("supervisor")
public class SupervisorResource {
    
    @EJB
    private RestOperation spvOperation;
        
   //by studentid
    @GET
    @Path("/{studentId}")
    @Produces({"application/json"})
    public List<Supervisor> getSupervisors(@PathParam("studentId")  Long studentId) {
        return spvOperation.findStudentsBySupervisor(studentId);        
    }
    
    // All
    @GET
    @Path("/all")
    @Produces({"application/json"})
    public List<Supervisor> getAllProjects() {
        return spvOperation.findAllSupervisors();
    }
    
}
