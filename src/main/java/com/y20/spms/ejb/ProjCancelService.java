/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.y20.spms.ejb;

import com.y20.spms.entity.Project;
import com.y20.spms.entity.Student;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author Yan's
 */

@Stateless
public class ProjCancelService {
    
    @PersistenceContext
    EntityManager em;
    
    public ProjCancelService() {
        
    }
    
    public void updateProject(Long id, Long stu) {
      Student student = em.getReference(Student.class, stu);
      Enum status;
      status = Project.ProjectStatus.RFC;  
      Query query = em.createQuery("UPDATE Project p SET p.student = :student, p.projectStatus = :status " +
                                " WHERE p.id = :id");
      query.setParameter("id", id);
      //query.setParameter("spv", spv);   
      query.setParameter("student", student);
      query.setParameter("status", status);   

      int updateProj = query.executeUpdate();
        
    }
    
    public List<Project> findAllTitle(Long stu) {
        Enum status1, status2;
        
        Student student = em.getReference(Student.class, stu);
        status1 = Project.ProjectStatus.ACCEPTED;
        status2 = Project.ProjectStatus.PROPOSED;
        String query = "select p from Project p where p.student = :student " +
                       " and p.projectStatus in (:status1, :status2)";
        TypedQuery<Project> q = em.createQuery(query, Project.class);
        q.setParameter("status1", status1);
        q.setParameter("status2", status2);
        q.setParameter("student", student);
        return q.getResultList();               
    }
    
    
}
