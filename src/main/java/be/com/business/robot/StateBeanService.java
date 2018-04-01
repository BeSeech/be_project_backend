package be.com.business.robot;

import be.com.data.StateCRUDService;
import be.com.helpers.OperationResult;

import javax.annotation.PreDestroy;
import javax.ejb.*;
import java.util.HashMap;

import be.com.presentation.api.pub.crud.state.CRUDStateResource;
import io.reactivex.Observable;
import org.apache.log4j.Logger;

@Singleton
public class StateBeanService
{
    private HashMap<String, StateBean> stateBeanMap = new HashMap<String, StateBean>();

    private static final Logger logger = Logger.getLogger(StateBeanService.class.getPackage().getName());

    @EJB
    StateCRUDService stateCRUDService;

    private boolean isStateBeanInCash(String uid)
    {
        return (stateBeanMap.getOrDefault(uid, null) != null);
    }

    public StateBean getStateBean(String uid)
    {
        logger.debug("StateBeanService.getStateBean(" + uid + ")");
        if (!isStateBeanInCash(uid)) {
            StateBean stateBean = stateCRUDService.getStateBean(uid);
            if (stateBean != null) {
                stateBeanMap.put(stateBean.getUid(), stateBean);
            }
        }
        logger.debug("StateBeanService.getStateBean return: " + stateBeanMap.getOrDefault(uid, null));
        return stateBeanMap.getOrDefault(uid, null);
    }

    public OperationResult deleteState(String uid)
    {
        OperationResult or = stateCRUDService.deleteState(uid);

        if (!or.isOk()) {
            return or;
        }

        StateBean result = stateBeanMap.remove(uid);
        if (result == null) {
            return OperationResult.error("State is not found");
        }
        return OperationResult.ok();
    }

    public OperationResult addStateBean(StateBean stateBean)
    {
        if (isStateBeanInCash(stateBean.getUid())) {
            return OperationResult.error("State with this uid already exists");
        }

        OperationResult or = stateCRUDService.insertStateBean(stateBean);

        if (!or.isOk()) {
            return or;
        }

        stateBeanMap.put(stateBean.getUid(), stateBean);

        return OperationResult.ok();
    }

    public OperationResult updateStateBean(StateBean stateBean) throws Exception
    {
        OperationResult or = stateCRUDService.updateStateBean(stateBean);
        if (!or.isOk()) {
            return or;
        }
        stateBeanMap.put(stateBean.getUid(), stateBean);
        return OperationResult.ok();
    }

    @PreDestroy
    public void destroy()
    {
        stateCRUDService.finished();
    }

    @Remove
    public void finished()
    {

    }

    public Observable<StateBean> getStateBeans()
    {
        Observable<StateBean> obs = Observable.fromArray(stateCRUDService.getStatesFromDB())
                .flatMapIterable(list -> list)
                .map(state -> StateCRUDService.getStateBean(state));
        return obs;
    }
}
