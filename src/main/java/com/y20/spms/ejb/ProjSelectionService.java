/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.y20.spms.ejb;

import com.y20.spms.entity.Project;
import com.y20.spms.entity.Student;
import com.y20.spms.entity.Supervisor;
import java.util.Arrays;
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
import static javax.ws.rs.core.Response.status;

/**
 *
 * @author Yan's
 */

@Stateless
@TransactionAttribute(NOT_SUPPORTED)
public class ProjSelectionService {
    
    @PersistenceContext
    EntityManager em;
    
    public ProjSelectionService() {
        
    }
    
    private static final Logger LOGGER = Logger.getLogger(ProjSelectionService.class.getName());
    
    
    // Update project to proposed & fill student ID
    @TransactionAttribute(REQUIRED) 
    public void updateProject(Long id, Long stu) {
      Student student = em.getReference(Student.class, stu);
      Project proj;
      
      proj = em.find(Project.class, id);
      proj.setStudent(student);
      proj.setProjectStatus(Project.ProjectStatus.PROPOSED);
      
      LOGGER.info("Before Commit");
      em.persist(proj);
      em.flush();       
        
    }
    
    // Get all supervisor for available project
    public List<Supervisor> findAllSupervisor() {
        Enum status;
        status = Project.ProjectStatus.AVAILABLE;
        String query = "select distinct s from Project p " +
                " inner join p.supervisor s" +
                " where p.projectStatus = :status" +
                " order by p.supervisor.id ASC";
        TypedQuery<Supervisor> q = em.createQuery(query, Supervisor.class);
        q.setParameter("status", status);
        return q.getResultList();               
    }   
   
    // Get all available project by title
    public List<Project> findAllTitle() {
        Enum status;
        status = Project.ProjectStatus.AVAILABLE;
        String query = "select p from Project p where p.projectStatus = :status";
        TypedQuery<Project> q = em.createQuery(query, Project.class);
        q.setParameter("status", status);
        return q.getResultList();               
    }
    
    // Get available project by supervisor
    public List<Project> findTitlebyspv(Long spv) {
        Enum status;
        status = Project.ProjectStatus.AVAILABLE;
        String query = "select p from Project p where p.supervisor.id = :spv and p.projectStatus = :status";
        TypedQuery<Project> q = em.createQuery(query, Project.class);
        q.setParameter("spv", spv);
        q.setParameter("status", status);
        return q.getResultList();               
    }
    
    // Get project detail by ID
    public Project findDetail(Long ID) {
        String query = "select p from Project p where p.id = :ID";
        TypedQuery<Project> q = em.createQuery(query, Project.class);
        q.setParameter("ID", ID);
        return q.getSingleResult();               
    }
    
    // find supervisor by title
    public List<Project> findspvbytitle(String title) {
        String query = "select p from Project p where p.title = :title";
        TypedQuery<Project> q = em.createQuery(query, Project.class);
        q.setParameter("title", title);
        return q.getResultList();                
    }
    
    // to check if student have applied for any project
    public Integer checkrecord(Long stuID){
        Enum status1, status2, status3;
        status1 = Project.ProjectStatus.ACCEPTED;
        status2 = Project.ProjectStatus.PROPOSED;
        status3 = Project.ProjectStatus.RFC;

        //Student student = em.getReference(Student.class, stuID);
        String query = "select p from Project p where p.student.id = :stuID " +
                " and (p.projectStatus = :status1 " +
                " or p.projectStatus = :status2 " +
                " or p.projectStatus = :status3) ";
        TypedQuery<Project> q = em.createQuery(query, Project.class);
        q.setParameter("stuID", stuID);
        q.setParameter("status1", status1);
        q.setParameter("status2", status2);
        q.setParameter("status3", status3);
        System.out.println(q.getResultList().size());
        if (q.getResultList().size() > 0){
            return 1;
        }
        else{
            return 0;
        }
            
    } 
    
}
