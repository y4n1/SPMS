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
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Yan's
 */
//@Singleton
//@Startup 
public class PopulateDatabase {
       
  /*  @PersistenceContext(name = "WebappsPU")
    public EntityManager em;
        
    @PostConstruct 
    public void createSomeDataInDatabase() {
           
        System.err.println("loaded");
        
        Supervisor spv = new Supervisor();
        spv.setId("mn125");
        spv.setFname("my");
        spv.setLname("name");
        spv.setDepartment("P and A");
        spv.setEmail("mn@yahoo.com");
        spv.setPhoneNumber("123456789");
        
        
        em.persist(spv);
        
        ProjectTopic topic = new ProjectTopic();
        topic.setTopicTitle("topic title");
        topic.setTopicDescription("topic description");
        
        em.persist(topic);
        
        Project p = new Project();
        p.setTitle("abcdefg");
        p.setDescription("Project Description");
        p.setRequiredSkills("Java, Python");
        p.setProjectStatus(Project.ProjectStatus.PROPOSED);
        Set<ProjectTopic> projectTopics = new HashSet<>();
        projectTopics.add(topic);
        p.setTopic(projectTopics);
        p.setSupervisor(spv);
        
        em.persist(p);
        
             
        Student s = new Student();
        s.setUsername("fn5548");
        s.setFname("Franta");
        s.setLname("Novak");
        s.setEmail("abcd@gmail.com");
        s.setCourseName("abcdef");
        s.setProject(p);
        em.persist(s); 
        
        
        */
        
        
    //}
    

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
