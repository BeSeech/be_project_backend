package be.com.presentation.api.pub;

import be.com.presentation.api.pub.crud.CRUDResource;
import org.apache.log4j.Logger;
import org.hibernate.id.GUIDGenerator;

import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.UUID;

@Singleton
public class PublicResource
{
    private static final Logger logger = Logger.getLogger(PublicResource.class.getPackage().getName());

    @EJB
    be.com.presentation.api.pub.crud.CRUDResource CRUDResource;

    @Path("/info")
    @GET
    public String Test()
    {
        return this.getClass().getName();
    }

    @Path("/crud")
    public CRUDResource getCRUDResource()
    {
        return CRUDResource;
    }

    @GET
    @Path("/guid")
    @Produces(MediaType.APPLICATION_JSON)
    public String getRobot() throws Exception
    {
        return UUID.randomUUID().toString();
    }


}
