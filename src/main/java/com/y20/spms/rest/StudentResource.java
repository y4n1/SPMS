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
@Path("/student/{supervisorId}")
public class StudentResource {
    
    @EJB
    private RestOperation studentOperation;
        
    @GET
    @Produces({"application/xml", "application/json"})
    public List<Student> getStudents(@PathParam("supervisorId")  Long supervisorId) {
        if("all".equals(supervisorId)) {
            return studentOperation.findAllStudents();
        }else {
            return studentOperation.findSupervisorByStudents(supervisorId);        
        }        
    }
    
    
    
    
}
