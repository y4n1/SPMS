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
import javax.persistence.TypedQuery;

/**
 *
 * @author Yan's
 */

@Stateless
public class SpvReportService {
    
    @PersistenceContext
    EntityManager em;
    
    public SpvReportService() {
        
    }
    
    // Select detail project by supervisor and student
    public List<Project> getproposebystudent(Long spvid, Long stuID) {
        String query = "select p from Project p where p.supervisor.id = :spvid and p.student.id = :stuID";
        TypedQuery<Project> q = em.createQuery(query, Project.class);
        q.setParameter("spvid", spvid);
        q.setParameter("stuID", stuID);
        return q.getResultList();               
    }
    
    // all student
    public List<Project> getproposeallstudent(Long spvid) {
        String query = "select p from Project p " +
                " inner join p.supervisor s" +
                " where p.supervisor.id = :spvid" +
                " order by p.supervisor.id ASC";
        TypedQuery<Project> q = em.createQuery(query, Project.class);
        q.setParameter("spvid", spvid);
        return q.getResultList();               
    }
    
    
    // all student
    public List<Student> findAllStudent(Long spvid) {
        String query = "select distinct s from Project p " +
                " inner join p.student s" +
                " where p.supervisor.id = :spvid" +
                " order by p.student.id ASC";
        TypedQuery<Student> q = em.createQuery(query, Student.class);
        q.setParameter("spvid", spvid);
        return q.getResultList();               
    } 
    
}
