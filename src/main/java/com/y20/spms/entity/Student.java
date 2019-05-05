/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.y20.spms.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Yan's
 */
@Entity
public class Student implements Serializable {

   // private static final long serialVersionUID = 1L;
    
  //  @Id
 //   private String id;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(nullable = false, name = "username", length = 15, unique=true)
    private String username;
    
    @Column(nullable = false, name = "fname", length = 15)
    private String fname;
    
    @Column(nullable = false, name = "lname", length = 15)
    private String lname;
    
    @Column(nullable = false, name = "email", length = 100)
    private String email;
    
    @Column(nullable = false, name = "course_name", length = 100)
    private String courseName;
    
//    @ManyToOne(optional = true)
//    @JoinColumn(nullable = true, name = "project_id")
//    private Project project;
    
    /*@OneToOne(optional = false)
    @JoinColumn(nullable = false, name = "id" , referencedColumnName="username")
    private Project SystemUser; */

    /*public Student(String username, String fname, String lname, String email, String course_name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }*/

    public Student() {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   /* public Student(String username, String fname, String lname, String email, String course_name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }*/

  //  public Student() {
  //      throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  //  } 
        
  //  public String getId() {
  //      return id;
  //  }

 //   public void setId(String id) {
 //      this.id = id;
  //  }

    public String getFname() {
        return fname;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

//    public Project getProject() {
//        return project;
//    }
//
//    public void setProject(Project project) {
//        this.project = project;
//    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Student)) {
            return false;
        }
        Student other = (Student) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    /*@Override
    public String toString() {
        return "com.y20.spms.database.Student[ id=" + id + " ]";
    }
    */
}
