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
public class StuReqStatService {
    
    @PersistenceContext
    EntityManager em;
    
    public StuReqStatService() {
        
    }
    
    // by Student
    public List<Project> getprojectstatus(Long stuid) {
        String query = "select p from Project p " +
                " where p.student.id = :stuid";
        TypedQuery<Project> q = em.createQuery(query, Project.class);
        q.setParameter("stuid", stuid);
        return q.getResultList();               
    }
    
}
