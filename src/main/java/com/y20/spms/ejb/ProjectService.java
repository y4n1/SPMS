/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.y20.spms.ejb;

import com.y20.spms.entity.Project;
import com.y20.spms.entity.ProjectTopic;
import com.y20.spms.entity.Student;
import com.y20.spms.entity.Supervisor;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
public class ProjectService {
    
    @PersistenceContext
    EntityManager em;
    
    public ProjectService() {
    }
    
    // Register Project 
    public void registerProject(String title, String description, String requiredSkills, Long spv,  Long topic) {
        //long x = 4;
        Project proj;
        ProjectTopic pt = em.getReference(ProjectTopic.class, topic);
        
        Supervisor supervisor = em.getReference(Supervisor.class, spv);
        
        
        
        proj = new Project();
        proj.setTitle(title);
        proj.setDescription(description);
        proj.setRequiredSkills(requiredSkills);
        proj.setProjectStatus(Project.ProjectStatus.AVAILABLE);
        //Set<ProjectTopic> projectTopics = new HashSet<>();
       // projectTopics.add(topic);
        proj.addTopic(pt);
        //Set<Supervisor> supervisors = new HashSet<>();
        proj.setSupervisor(supervisor);
        em.persist(proj);
               
    }
    
    // Propose Project by student
    public void proposeProject(String title, String description, String requiredSkills, Long spv,  Long topic, Long stu) {
        //long x = 4;
        Project proj;
        ProjectTopic pt = em.getReference(ProjectTopic.class, topic);
        
        Supervisor supervisor = em.getReference(Supervisor.class, spv);
        Student student = em.getReference(Student.class, stu);
        
        
        proj = new Project();
        proj.setTitle(title);
        proj.setDescription(description);
        proj.setRequiredSkills(requiredSkills);
        proj.setProjectStatus(Project.ProjectStatus.PROPOSED);
        proj.addTopic(pt);
        proj.setSupervisor(supervisor);
        proj.setStudent(student);
        em.persist(proj);
        
             
        
        
    }
   
    
    public List<Supervisor> findSupervisor() {
        String query = "select spv from Supervisor spv";
        TypedQuery<Supervisor> q = em.createQuery(query, Supervisor.class);
        return q.getResultList();               
    }
    
    public List<ProjectTopic> findTopic() {
        String query = "select pt from ProjectTopic pt";
        TypedQuery<ProjectTopic> q = em.createQuery(query, ProjectTopic.class);
        return q.getResultList();               
    }
    
    
    public Student getstuId(String stu){
        
        String query = "select s from Student s where s.username = :stu";
        TypedQuery<Student> q = em.createQuery(query, Student.class);
        q.setParameter("stu", stu);
        return q.getSingleResult();
        
    }
        
}
