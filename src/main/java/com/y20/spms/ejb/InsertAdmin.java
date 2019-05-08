/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.y20.spms.ejb;

import com.y20.spms.entity.SystemUser;
import com.y20.spms.entity.SystemUserGroup;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import static java.util.Objects.isNull;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Yan's
 */

@Singleton
@Startup 
public class InsertAdmin {
    
    @PersistenceContext(name = "WebappsPU")
    public EntityManager em;
    
    @PostConstruct 
    public void createSomeDataInDatabase (){
        
        if(isNull(checkExist().getUsername())){
            
            Logger.getLogger(UserService.class.getName()).info("User exist");
           
        }else{
            try {
                 SystemUser sys_user;
                 SystemUserGroup sys_user_group;

                  MessageDigest md = MessageDigest.getInstance("SHA-256");
                   String passwd = "admin1";
                   md.update(passwd.getBytes("UTF-8"));
                    byte[] digest = md.digest();
                    StringBuffer sb = new StringBuffer();
                   for (int i = 0; i < digest.length; i++) {
                        sb.append(Integer.toString((digest[i] & 0xff) + 0x100, 16).substring(1));
                    }
                   String paswdToStoreInDB = sb.toString();

                   sys_user = new SystemUser("admin1", paswdToStoreInDB);
                   sys_user_group = new SystemUserGroup("admin1", "Admin");

                    em.persist(sys_user);
                   em.persist(sys_user_group);

                } catch (UnsupportedEncodingException | NoSuchAlgorithmException ex) {
                    Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
             }
        }
    }    
    
    public SystemUser checkExist(){
        
        String user = "admin1";
        String query = "select s from SystemUser s where s.username = :user";
        TypedQuery<SystemUser> q = em.createQuery(query, SystemUser.class);
        q.setParameter("user", user);
        return q.getSingleResult();   
    }
            
    
}
