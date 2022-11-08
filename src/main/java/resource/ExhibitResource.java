package resource;
import io.quarkus.security.Authenticated;
import model.Exhibit;
import service.ExhibitService;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("api")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class ExhibitResource {

  @Inject
  ExhibitService service;

  @GET
  @Path("/")
  @Authenticated
  public Response displayAllExhibits(){
    return Response.ok(service.displayAllItems()).build();
  }

  @POST
  @Path("/")
  @Transactional
  @RolesAllowed("admin")
  public Response insertNewItem(Exhibit exhibit){
    service.create(exhibit);
    return Response.ok(exhibit).status(Response.Status.CREATED).build();
  }




}
