/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.y20.spms.ejb;

import com.y20.spms.entity.Student;
import com.y20.spms.entity.Supervisor;
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
import javax.persistence.metamodel.SingularAttribute;

/**
 *
 * @author Yan's
 */
@Stateless
public class UserService {

    @PersistenceContext
    EntityManager em;

    public UserService() {
    }

    public void registerStudent(String username, String password, String fname, String lname, String email, String course_name) {
        try {
            SystemUser sys_user;
            SystemUserGroup sys_user_group;
            Student s;

            MessageDigest md = MessageDigest.getInstance("SHA-256");
            String passwd = password;
            md.update(passwd.getBytes("UTF-8"));
            byte[] digest = md.digest();
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < digest.length; i++) {
                sb.append(Integer.toString((digest[i] & 0xff) + 0x100, 16).substring(1));
            }
            String paswdToStoreInDB = sb.toString();

            sys_user = new SystemUser(username, paswdToStoreInDB);
            sys_user_group = new SystemUserGroup(username, "Student");
           
            s = new Student();
            s.setUsername(username);
            s.setFname(fname);
            s.setLname(lname);
            s.setEmail(email);
            s.setCourseName(course_name);

            em.persist(sys_user);
            em.persist(sys_user_group);
            em.persist(s);

        } catch (UnsupportedEncodingException | NoSuchAlgorithmException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void registerSpv(String username, String password, String fname, String lname, String email, String department, String phone_number) {
        try {
            SystemUser sys_user;
            SystemUserGroup sys_user_group;
            Supervisor spv;

            MessageDigest md = MessageDigest.getInstance("SHA-256");
            String passwd = password;
            md.update(passwd.getBytes("UTF-8"));
            byte[] digest = md.digest();
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < digest.length; i++) {
                sb.append(Integer.toString((digest[i] & 0xff) + 0x100, 16).substring(1));
            }
            String paswdToStoreInDB = sb.toString();

            sys_user = new SystemUser();
            sys_user.setUsername(username);
            sys_user.setUserpassword(paswdToStoreInDB);
            
            sys_user_group = new SystemUserGroup();
            sys_user_group.setUsername(username);
            sys_user_group.setGroupname("Supervisor");
            
            spv = new Supervisor();
            spv.setUsername(username);
            spv.setFname(fname);
            spv.setLname(lname);
            spv.setEmail(email);
            spv.setDepartment(department);
            spv.setPhoneNumber(phone_number);

            em.persist(sys_user);
            em.persist(sys_user_group);
            em.persist(spv);

        } catch (UnsupportedEncodingException | NoSuchAlgorithmException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void registerStudent(SingularAttribute<SystemUserGroup, String> username, SingularAttribute<SystemUser, String> userpassword) {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void registerStudent(String id, String password) {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
