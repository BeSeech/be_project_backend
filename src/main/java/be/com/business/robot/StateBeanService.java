package be.com.business.robot;

import be.com.data.StateCRUDService;
import be.com.helpers.OperationResult;

import javax.annotation.PreDestroy;
import javax.ejb.*;
import java.util.HashMap;

import io.reactivex.Observable;

@Singleton
public class StateBeanService
{
    private HashMap<String, StateBean> stateBeanMap = new HashMap<String, StateBean>();

    @EJB
    StateCRUDService stateCRUDService;

    private boolean isStateBeanInCash(String id)
    {
        return (stateBeanMap.getOrDefault(id, null) != null);
    }

    public StateBean getStateBean(String uid)
    {
        if (!isStateBeanInCash(uid)) {
            StateBean stateBean = stateCRUDService.getStateBean(uid);
            if (stateBean != null) {
                stateBeanMap.put(stateBean.getUid(), stateBean);
            }
        }

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
        Observable<StateBean> obs =  Observable.fromArray(stateCRUDService.getStatesFromDB())
                .flatMapIterable( list -> list )
                .map( state -> StateCRUDService.getStateBean(state));
        return obs;
    }
}
