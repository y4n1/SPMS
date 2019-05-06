/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.y20.spms.ejb;

import com.y20.spms.entity.ProjectTopic;
import com.y20.spms.entity.Student;
import com.y20.spms.entity.SystemUser;
import com.y20.spms.entity.SystemUserGroup;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Yan's
 */
@Stateless
public class TopicService {
    
    @PersistenceContext
    EntityManager em;
    
    public TopicService() {
    }
    
    
public void registerTopic(String title, String description) {

        ProjectTopic topic;


        topic = new ProjectTopic();
        topic.setTopicTitle(title);
        topic.setTopicDescription(description);
        em.persist(topic);
        em.flush();  
    }
}
