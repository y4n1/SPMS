/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.y20.spms.ejb;

import com.y20.spms.database.Student;
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
public class StudentOperation {

    @PersistenceContext(name = "WebappsPU")
    private EntityManager em;
            
    public void saveStudent(Student student) {
        em.persist(student);        
    }    
    
    public List<Student> findStudentsBySupervisor(String supervisorId) {
        String query = "select s from Student s where s.project.supervisor.id = :supervisorId";
      
        TypedQuery<Student> q = em.createQuery(query, Student.class);
        q.setParameter("supervisorId", supervisorId);
        
        return q.getResultList();                
    }
    
    public List<Student> findAllStudents() {
        String query = "select s from Student s";
        TypedQuery<Student> q = em.createQuery(query, Student.class);
        return q.getResultList();
    }
    
    
    
    
    
    
    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
