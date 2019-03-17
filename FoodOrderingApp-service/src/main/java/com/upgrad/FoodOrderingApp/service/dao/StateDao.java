package com.upgrad.FoodOrderingApp.service.dao;


import com.upgrad.FoodOrderingApp.service.entity.StateEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Repository
public class StateDao {

    private static final String STATE_BY_UUID = "stateByUUID";

    @PersistenceContext
    EntityManager entityManager;

    /**
     * Method used for accessing state by uuid
     *
     * @param stateUuid uuid of StateEntity
     * @return StateEntity object
     */
    public StateEntity getState(String stateUuid) {
        try {
            return entityManager.createNamedQuery(StateDao.STATE_BY_UUID, StateEntity.class).setParameter("uuid", stateUuid)
                    .getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }
}
