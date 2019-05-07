/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.y20.spms.ejb;

import com.y20.spms.entity.Project;
import com.y20.spms.entity.Student;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import static javax.ejb.TransactionAttributeType.NOT_SUPPORTED;
import static javax.ejb.TransactionAttributeType.REQUIRED;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author Yan's
 */

@Stateless
@TransactionAttribute(NOT_SUPPORTED)
public class ProjCancelService {
    
    @PersistenceContext
    EntityManager em;
    
    public ProjCancelService() {
        
    }
    
    private static final Logger LOGGER = Logger.getLogger(ProjSelectionService.class.getName());
    
    @TransactionAttribute(REQUIRED)
    public void updateProject(Long id, Long stu, String comment) {
        
      Student student = em.getReference(Student.class, stu);
      Project proj;
      
      proj = em.find(Project.class, id);
      proj.setStudent(student);
      proj.setProjectStatus(Project.ProjectStatus.RFC);
      proj.setReason(comment);
      
      LOGGER.info("Before Commit");
      em.persist(proj);
      em.flush();         
       
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
