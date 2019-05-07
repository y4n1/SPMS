/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.y20.spms.ejb;

import com.y20.spms.entity.Project;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Yan's
 */
@Stateless
public class SupervisorNotifService {
    
    @PersistenceContext
    EntityManager em;
    
    public SupervisorNotifService() {
        
    }
    
    // Get total proposed project by spv
    public List<Project> getproposebyspv(Long spvid) {
        Enum status;
        status = Project.ProjectStatus.PROPOSED;
        String query = "select p from Project p where p.projectStatus = :status and p.supervisor.id = :spvid";
        TypedQuery<Project> q = em.createQuery(query, Project.class);
        q.setParameter("status", status);
        q.setParameter("spvid", spvid);
        return q.getResultList();               
    }
    
    
}
