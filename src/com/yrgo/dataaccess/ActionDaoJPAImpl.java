package com.yrgo.dataaccess;

import com.yrgo.domain.Action;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class ActionDaoJPAImpl implements ActionDao {


    @PersistenceContext
    private EntityManager em;

    public void create(Action newAction) {
        em.persist(newAction);
    }

    public List<Action> getIncompleteActions(String userId) {
        return em.createQuery("select action from Action as action where action.complete=false and action.owningUser=:userId").setParameter("userId", userId).getResultList();
    }

    public void update(Action actionToUpdate) throws RecordNotFoundException {
        Action action = em.find(Action.class, actionToUpdate.getActionId());
        if (action == null) {
            throw new RecordNotFoundException();
        }
        action.setDetails(actionToUpdate.getDetails());
        action.setRequiredBy(actionToUpdate.getRequiredBy());
        action.setOwningUser(actionToUpdate.getOwningUser());
        action.setComplete(actionToUpdate.isComplete());
        em.merge(action);
    }

    public void delete(Action oldAction) throws RecordNotFoundException {
        Action action = em.find(Action.class, oldAction.getActionId());
        if (action == null) {
            throw new RecordNotFoundException();
        }
        em.remove(action);
    }
}


