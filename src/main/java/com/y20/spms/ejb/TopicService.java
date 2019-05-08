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
import javax.ejb.TransactionAttribute;
import static javax.ejb.TransactionAttributeType.NOT_SUPPORTED;
import static javax.ejb.TransactionAttributeType.REQUIRED;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Yan's
 */
@Stateless
@TransactionAttribute(NOT_SUPPORTED)
public class TopicService {
    
    @PersistenceContext
    EntityManager em;
    
    private static final Logger LOGGER = Logger.getLogger(TopicService.class.getName());
    
    public TopicService() {
    }
    
@TransactionAttribute(REQUIRED)    
public void registerTopic(String title, String description) {

        ProjectTopic topic;


        topic = new ProjectTopic();
        topic.setTopicTitle(title);
        topic.setTopicDescription(description);
        em.persist(topic);
        em.flush();  
        
        LOGGER.info("New Topic " + title + "has been added");
    }
}
