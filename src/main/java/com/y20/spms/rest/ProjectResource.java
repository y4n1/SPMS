/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.y20.spms.rest;

import com.y20.spms.ejb.RestOperation;
import com.y20.spms.entity.Project;
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
@Path("/project/{supervisorId}")
public class ProjectResource {
    
    @EJB
    private RestOperation prjOperation;
        
    @GET
    @Produces({"application/xml", "application/json"})
    public List<Project> getProjects(@PathParam("supervisorId")  Long supervisorId) {
        if("all".equals(supervisorId)) {
            return prjOperation.findAllProjects();
        }else {
            return prjOperation.findProjectBySupervisor(supervisorId);        
        }        
    }
        
    
}
