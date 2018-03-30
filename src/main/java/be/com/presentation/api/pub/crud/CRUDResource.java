package be.com.presentation.api.pub.crud;

import be.com.presentation.api.pub.crud.state.CRUDStateResource;
import org.apache.log4j.Logger;

import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ws.rs.Path;

@Singleton
public class CRUDResource
{
    private static final Logger logger = Logger.getLogger(CRUDResource.class.getPackage().getName());

    @EJB
    CRUDStateResource CRUDStateResource;

    @Path("/states")
    public CRUDStateResource getCRUDStateResource()
    {
        return CRUDStateResource;
    }

}
