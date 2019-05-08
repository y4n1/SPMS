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
public class AdminReportService {
    
    @PersistenceContext
    EntityManager em;
    
    public AdminReportService() {
        
    }
    
    
    // by supervisor
    public List<Project> getprojectbyspv(Long spvid) {
        String query = "select p from Project p " +
                " join p.supervisor s" +
                " where p.supervisor.id = :spvid" +
                " order by p.supervisor.id ASC";
        TypedQuery<Project> q = em.createQuery(query, Project.class);
        q.setParameter("spvid", spvid);
        return q.getResultList();               
    }
    
    // all supervisor
    public List<Project> getallspv() {
        String query = "select p from Project p " +
                " join p.supervisor s" +
                " order by p.supervisor.id ASC";
        TypedQuery<Project> q = em.createQuery(query, Project.class);
        return q.getResultList();               
    }
    
    // by student
    public List<Project> getprojectbystudent(Long stuid) {
        String query = "select p from Project p " +
                " join p.student s" +
                " where p.student.id = :stuid" +
                " order by p.student.id ASC";
        TypedQuery<Project> q = em.createQuery(query, Project.class);
        q.setParameter("stuid", stuid);
        return q.getResultList();               
    }
    
    // all student
    public List<Project> getallstudent() {
        String query = "select p from Project p " +
                " join p.student s" +
                " order by p.student.id ASC";
        TypedQuery<Project> q = em.createQuery(query, Project.class);
        return q.getResultList();               
    }
    
    //fillup all supervisor list
    public List<Supervisor> getspvall() {
        String query = "select s from Supervisor s ";
        TypedQuery<Supervisor> q = em.createQuery(query, Supervisor.class);
        return q.getResultList();               
    }
    
    // all student
    public List<Student> getstuall() {
        String query = "select s from Student s ";
        TypedQuery<Student> q = em.createQuery(query, Student.class);
        return q.getResultList();               
    }
    
}
