package be.com.presentation.api.pub.crud.state;

import be.com.business.robot.StateBean;
import be.com.business.robot.StateBeanService;
import be.com.helpers.OperationResult;
import io.reactivex.Observable;
import org.apache.log4j.Logger;

import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.List;

@Singleton
public class CRUDStateResource
{
    @EJB
    private StateBeanService stateBeanService;

    private static final Logger logger = Logger.getLogger(CRUDStateResource.class.getPackage().getName());

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postState(StateBean newStateBean) throws Exception
    {
        logger.info("Post request: " + newStateBean.toString());
        OperationResult or = stateBeanService.addStateBean(newStateBean);
        if (or.isOk()) {
            return Response.noContent().build();
        }
        return Response.status(Response.Status.BAD_REQUEST.getStatusCode(), or.getErrorMessage()).build();
    }

    @PUT
    @Path("/{uid}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response putState(@PathParam("uid") String uid, StateBean stateBean) throws Exception
    {
        if (stateBean == null) {
            throw new WebApplicationException(404);
        }
        stateBean.setUid(uid);
        OperationResult or = stateBeanService.updateStateBean(stateBean);
        if (or.isOk()) {
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND.getStatusCode(), or.getErrorMessage()).build();
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<StateBean> getRobot(@Context SecurityContext securityContext) throws Exception
    {
        Observable<StateBean> obs = stateBeanService.getStateBeans();
        return obs.toList().blockingGet();
    }

    @GET
    @Path("/{uid}")
    @Produces(MediaType.APPLICATION_JSON)
    public StateBean getRobot(@PathParam("uid") String uid, @Context SecurityContext securityContext) throws Exception
    {
/*        if (!securityContext.isSecure()) {
            throw new WebApplicationException(401);
        }*/
        logger.debug("Get state request with uid: " + uid);
        StateBean stateBean = stateBeanService.getStateBean(uid);

        if (stateBean == null) {
            throw new WebApplicationException(404);
        }
        return stateBean;
    }

    @DELETE
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/{uid}")
    public Response deleteRobot(@PathParam("uid") String uid)
    {
        OperationResult or = stateBeanService.deleteState(uid);
        if (or.isOk()) {
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND.getStatusCode(), or.getErrorMessage()).build();
    }
}
