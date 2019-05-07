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
import java.util.Arrays;
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
        em.flush();         
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
        em.flush();  
             
        
        
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
