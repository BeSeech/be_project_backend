package be.com.presentation.api.priv;

import be.com.presentation.api.pub.crud.CRUDResource;
import org.apache.log4j.Logger;

import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ws.rs.Path;

@Singleton
public class PrivateResource
{
    private static final Logger logger = Logger.getLogger(PrivateResource.class.getPackage().getName());

    @EJB
    CRUDResource CRUDResource;

    @Path("/crud")
    public CRUDResource getCRUDResource()
    {
        return CRUDResource;
    }

}
