package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.StateDao;
import com.upgrad.FoodOrderingApp.service.entity.StateEntity;
import com.upgrad.FoodOrderingApp.service.exception.AddressNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;

@Service
public class StateService {


    @Autowired
    StateDao stateDao;

    /**
     * Method used for getting StateEntity object by uuid od state
     *
     * @param stateUuid uuid of the state
     * @return StateEntity object
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public StateEntity getStateByUUID(@NotNull String stateUuid) throws AddressNotFoundException {
        StateEntity stateEntity = stateDao.getState(stateUuid);
        if (stateEntity == null) {
            throw new AddressNotFoundException("ANF-002", "No stateEntity by this id");
        }
        return stateEntity;
    }
}
