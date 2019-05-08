/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.y20.spms.rest;

import com.y20.spms.ejb.RestOperation;
import com.y20.spms.entity.Project;
import com.y20.spms.entity.Supervisor;
import java.util.HashMap;
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
@Path("project")
public class ProjectResource {
    
    @EJB
    private RestOperation prjOperation;
        
    // by supervisor ID
    @GET
    @Path("/{supervisorId}")
    @Produces({"application/json"})
    public List<Project> getProjects(@PathParam("supervisorId")  Long supervisorId) {
        return prjOperation.findProjectBySupervisor(supervisorId);  
    }
    
    // All
    @GET
    @Path("/all")
    @Produces({"application/json"})
    public List<Project> getAllProjects() {
        return prjOperation.findAllProjects();
    }
}
