package be.com.data;

import be.com.business.robot.StateBean;
import be.com.helpers.OperationResult;
import org.apache.log4j.Logger;

import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.*;
import java.util.List;

@Stateless
public class StateCRUDService
{
    @PersistenceContext(unitName = "MAIN_PU")
    private EntityManager entityManager;

    private static final Logger logger = Logger.getLogger(StateCRUDService.class.getPackage().getName());

    public static State getState(StateBean stateBean)
    {
        State state = new State();
        merge(stateBean, state);
        return state;
    }

    public static StateBean getStateBean(State state)
    {
        StateBean stateBean = new StateBean();
        if (merge(state, stateBean)) {
            return stateBean;
        }
        return null;
    }

    public static boolean merge(StateBean fromStateBean, State toState)
    {
        toState.setUid(fromStateBean.getUid());
        toState.setName(fromStateBean.getName());
        toState.setColor(fromStateBean.getColor());
        return true;
    }

    public static boolean merge(State fromState, StateBean toStateBean)
    {
        try {
            toStateBean.setUid(fromState.getUid());
            toStateBean.setName(fromState.getName());
            toStateBean.setColor(fromState.getColor());
        }
        catch (Exception ex) {
            return false;
        }
        return true;
    }

    public OperationResult insertStateBean(StateBean stateBean)
    {
        if (getStateBean(stateBean.getUid()) != null) {
            return OperationResult.error("State with this uid already exists");
        }
        try {
            State state = getState(stateBean);
            entityManager.persist(state);
            entityManager.flush();
        }
        catch (Exception ex) {
            return OperationResult.error("Error in insertStateBean(): " + ex.getMessage());
        }
        return OperationResult.ok();
    }

    private State getStateFromDB(String uid)
    {
        State state = null;
        try {
            state = entityManager.createNamedQuery("State.findByUid", State.class).setParameter("uid", uid).getSingleResult();
        }
        catch (Exception ex) {
            return null;
        }
        return state;
    }

    public List<State> getStatesFromDB()
    {
        List<State> stateList = null;
        try {
            stateList = entityManager.createNamedQuery("State.findAllStates", State.class).getResultList();
        }
        catch (Exception ex) {
            return null;
        }
        return stateList;
    }


    public StateBean getStateBean(String uid)
    {
        State state = getStateFromDB(uid);
        if (state == null) {
            return null;
        }
        return getStateBean(state);
    }

    @Transactional
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public OperationResult updateStateBean(StateBean stateBean)
    {
        State state = getStateFromDB(stateBean.getUid());

        if (state == null) {
            return OperationResult.error("State with this uid doesn't exists");
        }

        merge(stateBean, state);
        entityManager.merge(state);
        entityManager.flush();

        return OperationResult.ok();
    }

    @Transactional
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public OperationResult deleteState(String uid)
    {
        State state = getStateFromDB(uid);
        if (state == null) {
            return OperationResult.error("State with this uid doesn't exist");
        }

        entityManager.remove(state);
        entityManager.flush();

        return OperationResult.ok();
    }

    @Remove
    public void finished()
    {
    }
}
