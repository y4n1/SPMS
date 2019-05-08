
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.y20.spms.entity;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author Yan's
 */
@Entity
public class Project implements Serializable {

    private static final long serialVersionUID = 1L;
    
    public static enum ProjectStatus {
        ACCEPTED, PROPOSED, AVAILABLE, CANCELLED, RFC, REJECTED;
    }        

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(name = "title", nullable = false, length = 200)
    private String title;
    
    @Column(name = "description", nullable = false, length = 1000)
    private String description;
    
    @Column(name = "required_skills", nullable = false, length = 500)
    private String requiredSkills;
   
    // comment for cancellation / rejection
    @Column(name = "reason", nullable = true, length = 500)
    private String reason;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "project_status", nullable = false, length = 20)
    private ProjectStatus projectStatus;
    
    @ManyToOne(cascade = CascadeType.PERSIST, optional = false)
    @JoinColumn(name = "project_supervisor", nullable = true)
    private Supervisor supervisor;
   
    @OneToOne(optional = false)
    @JoinColumn(name = "student", nullable = true)
    private Student student;
    
   // @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
   // private Set<ProjectTopic> topic;
    
    @ManyToMany(
        cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
        }
    )
    @JoinTable(name = "project_projecttopic",
        joinColumns = @JoinColumn(name = "project_id"),
        inverseJoinColumns = @JoinColumn(name = "topic_id")
    )
        
    private Set<ProjectTopic> topics = new LinkedHashSet<>();
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRequiredSkills() {
        return requiredSkills;
    }

    public void setRequiredSkills(String requiredSkills) {
        this.requiredSkills = requiredSkills;
    }

    public ProjectStatus getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(ProjectStatus projectStatus) {
        this.projectStatus = projectStatus;
    }

    public Supervisor getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(Supervisor supervisor) {
        this.supervisor = supervisor;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Set<ProjectTopic> getTopics() {
        return topics;
    }

    public void setTopics(Set<ProjectTopic> topics) {
        this.topics = topics;
    }



    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public void addTopic(ProjectTopic topic) {
        topics.add(topic);
        topic.getProjects().add(this);
    }
 
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Project)) {
            return false;
        }
        Project other = (Project) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.y20.spms.database.Project[ id=" + id + " ]";
    }
    
}
