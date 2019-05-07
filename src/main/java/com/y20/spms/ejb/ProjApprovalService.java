/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.y20.spms.ejb;

import com.y20.spms.entity.Project;
import java.util.List;
import java.util.logging.Logger;
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
public class ProjApprovalService {
    
    @PersistenceContext
    EntityManager em;
    
    private static final Logger LOGGER = Logger.getLogger(ProjApprovalService.class.getName());
    
    public ProjApprovalService() {
        
    }
    
    // get all propose project by supervisor
    public List<Project> findTitlebySpv(Long spvid) {
        Enum status;
        status = Project.ProjectStatus.PROPOSED;
        String query = "select p from Project p where p.projectStatus = :status and p.supervisor.id = :spvid";
        TypedQuery<Project> q = em.createQuery(query, Project.class);
        q.setParameter("status", status);
        q.setParameter("spvid", spvid);
        return q.getResultList();               
    }
    
    // Get Project description
    public Project findprojdecr(Long projID) {
        String query = "select p from Project p where p.id = :projID "; 
        TypedQuery<Project> q = em.createQuery(query, Project.class);
        q.setParameter("projID", projID);
        return q.getSingleResult();
    }
    
    // Update Record
    @TransactionAttribute(REQUIRED)
    public void updateProject(Long id, String status, String comment) {
      
      Project proj1;
      
      // Update
      proj1 = em.find(Project.class, id);
      
      System.out.println(status);
      if ("ACCEPTED".equals(status)){ 
            proj1.setProjectStatus(Project.ProjectStatus.ACCEPTED);
            proj1.setReason(comment);
      }else {
            proj1.setProjectStatus(Project.ProjectStatus.REJECTED);
            proj1.setReason(comment);
      }
    
      em.persist(proj1);
      em.flush();
      LOGGER.info("Project" + id + "is updated");
    }
    
}
