/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.y20.spms.ejb;

import com.y20.spms.entity.Student;
import com.y20.spms.entity.Supervisor;
import com.y20.spms.entity.SystemUser;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Yan's
 */
@Stateless
public class RetrieveID {

    @PersistenceContext
    EntityManager em;
    
    public RetrieveID() {
    }
    
  //Student
    public Student getstudentID(String stu){
       
        String query = "select s from Student s where s.username = :stu";
        TypedQuery<Student> q = em.createQuery(query, Student.class);
        q.setParameter("stu", stu);
        return q.getSingleResult();
        
    }
    
    //Supervisor
    public Supervisor getspvId(String spv){
        
        String query = "select s from Supervisor s where s.username = :spv";
        TypedQuery<Supervisor> q = em.createQuery(query, Supervisor.class);
        q.setParameter("spv", spv);
        return q.getSingleResult();
        
    }
    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
