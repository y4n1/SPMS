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

/**
 *
 * @author Yan's
 */
@Stateless
public class RestOperation {

    @PersistenceContext(name = "WebappsPU")
    private EntityManager em;
            
    // For Student Operation
    public void saveStudent(Student student) {
        em.persist(student);        
    }    
    
    public List<Student> findSupervisorByStudents(Long supervisorId) {
        String query = "select s from Student s " +
                       //" inner join s.project p " +
                       "where s.project.supervisor.id = :supervisorId";
      
        TypedQuery<Student> q = em.createQuery(query, Student.class);
        q.setParameter("supervisorId", supervisorId);
        
        return q.getResultList();                
    }
    
    public List<Student> findAllStudents() {
        String query = "select s from Student s";
        TypedQuery<Student> q = em.createQuery(query, Student.class);
        return q.getResultList();
    }
    
    //For Supervisor Operation
    
    public void saveSupervisor(Supervisor spv) {
        em.persist(spv);        
    }    
    
    public List<Supervisor> findStudentsBySupervisor(Long studentId) {
        String query = "select distinct s from Project p " +
                " inner join p.supervisor s" +
                " where p.student.id = :studentId";
        TypedQuery<Supervisor> q = em.createQuery(query, Supervisor.class);
        q.setParameter("studentId", studentId);
        
        return q.getResultList();                
    }
    
    public List<Supervisor> findAllSupervisors() {
        String query = "select spv from Supervisor spv";
        TypedQuery<Supervisor> q = em.createQuery(query, Supervisor.class);
        return q.getResultList();
    }
    
    
    // Project Operation
    public void saveProject(Project prj) {
        em.persist(prj);        
    }    
    
    public List<Project> findProjectBySupervisor(Long supervisorId) {
        String query = "select p from Project AS p where p.supervisor.id = :supervisorId";
      
        TypedQuery<Project> q = em.createQuery(query, Project.class);
        q.setParameter("supervisorId", supervisorId);
        
        return q.getResultList();                
    }
    
    public List<Project> findAllProjects() {
        String query = "select p from Project p";
        TypedQuery<Project> q = em.createQuery(query, Project.class);
        return q.getResultList();
    }
    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
