/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.y20.spms.ejb;

import com.y20.spms.entity.Project;
import com.y20.spms.entity.Student;
import com.y20.spms.entity.Supervisor;
import java.util.List;
import javax.ejb.Stateless;
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
public class ProjSelectionService {
    
    @PersistenceContext
    EntityManager em;
    
    public ProjSelectionService() {
        
    }
    
    public void updateProject(Long id, Long stu) {
      Student student = em.getReference(Student.class, stu);
      Enum status;
      status = Project.ProjectStatus.PROPOSED;    
      Query query = em.createQuery("UPDATE Project p SET p.student = :student, p.projectStatus = :status " +
                                " WHERE p.id = :id");
      query.setParameter("id", id);
      //query.setParameter("spv", spv);   
      query.setParameter("student", student);
      query.setParameter("status", status);   

      int updateProj = query.executeUpdate();
        
    }
    
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
   
    public List<Project> findAllTitle() {
        Enum status;
        status = Project.ProjectStatus.AVAILABLE;
        String query = "select p from Project p where p.projectStatus = :status";
        TypedQuery<Project> q = em.createQuery(query, Project.class);
        q.setParameter("status", status);
        return q.getResultList();               
    }
    
    public List<Project> findTitlebyspv(Long spv) {
        Enum status;
        status = Project.ProjectStatus.AVAILABLE;
        String query = "select p from Project p where p.supervisor.id = :spv and p.projectStatus = :status";
        TypedQuery<Project> q = em.createQuery(query, Project.class);
        q.setParameter("spv", spv);
        q.setParameter("status", status);
        return q.getResultList();               
    }
    
    public Project findDetail(Long ID) {
        String query = "select p from Project p where p.id = :ID";
        TypedQuery<Project> q = em.createQuery(query, Project.class);
        q.setParameter("ID", ID);
        return q.getSingleResult();               
    }
    
    public List<Project> findspvbytitle(String title) {
        String query = "select p from Project p where p.title = :title";
        TypedQuery<Project> q = em.createQuery(query, Project.class);
        q.setParameter("title", title);
        return q.getResultList();                
    }
    
}
