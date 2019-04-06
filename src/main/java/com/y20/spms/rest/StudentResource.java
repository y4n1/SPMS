/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.y20.spms.rest;

import com.y20.spms.database.Student;
import com.y20.spms.ejb.StudentOperation;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 *
 * @author Yan's
 */
@Path("/student/{supervisorId}")
public class StudentResource {
    
    @EJB
    private StudentOperation studentOperation;
        
    @GET
    @Produces({"application/xml", "application/json"})
    public List<Student> getStudents(@PathParam("supervisorId")  String supervisorId) {
        if("all".equals(supervisorId)) {
            return studentOperation.findAllStudents();
        }else {
            return studentOperation.findStudentsBySupervisor(supervisorId);        
        }        
    }
    
    
}
