/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.y20.spms.ejb;

import com.y20.spms.entity.Logging;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import static javax.ejb.TransactionAttributeType.NOT_SUPPORTED;
import static javax.ejb.TransactionAttributeType.REQUIRED;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Yan's
 */
@Stateless
@TransactionAttribute(NOT_SUPPORTED)
public class ProjLoggingService {
    
    @PersistenceContext
    EntityManager em;
    
    public ProjLoggingService() {
        
    }
    
    @TransactionAttribute(REQUIRED) 
    public void insertLog(String username, String date, String form, String activity) {

        Logging log;
       
        log = new Logging();
        log.setUsername(username);
        log.setDatetime(date);
        log.setForm(form);
        log.setActivity(activity);
        
        em.persist(log);
        em.flush();         
        
    }
    
    public List<Logging> extractLog() {

        String query = "select l from Logging l";
        TypedQuery<Logging> q = em.createQuery(query, Logging.class);
        return q.getResultList();               
    }
    
}
