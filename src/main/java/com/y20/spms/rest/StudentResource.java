/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.y20.spms.rest;

import com.y20.spms.entity.Student;
import com.y20.spms.ejb.RestOperation;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 *
 * @author Yan's
 */

@Singleton
@Path("student")
public class StudentResource {
    
    @EJB
    private RestOperation studentOperation;
        

    // by supervisor ID
    @GET
    @Path("/{supervisorId}")
    @Produces({"application/json", "application/xml"})
    public List<Student> getStudents(@PathParam("supervisorId")  Long supervisorId) {
        return studentOperation.findSupervisorByStudents(supervisorId);   
    }
    
    // All
    @GET
    @Path("/all")
    @Produces({"application/json", "application/xml"})
    public List<Student> getAllProjects() {
        return studentOperation.findAllStudents();
    }
    
    
    
}
