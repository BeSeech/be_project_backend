package be.com.presentation.api;

import be.com.presentation.api.priv.PrivateResource;
import be.com.presentation.api.pub.PublicResource;
import org.apache.log4j.Logger;

import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ws.rs.*;

@Path("/")
@Singleton
public class ApiResource
{
    private static final Logger logger = Logger.getLogger(ApiResource.class.getPackage().getName());

    @EJB
    PublicResource publicResource;

    @EJB
    PrivateResource privateResource;

    @Path("/public")
    public PublicResource getPublicResource()
    {
        return publicResource;
    }

    @Path("/private")
    public PrivateResource getPrivateResource()
    {
        return privateResource;
    }
}