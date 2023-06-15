package wipb.jsfcruddemo.web.apiResources;

import wipb.jsfcruddemo.web.model.Product;
import wipb.jsfcruddemo.web.service.ProductService;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;

@Path("products")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class ProductResource {

    @EJB
    private ProductService productService;

    @Context
    private UriInfo uriInfo;

    @GET
    public List<Product> findAll() {
        return productService.findAll();
    }

    @GET
    @Path("{id}")
    public Product findById(@PathParam("id") Long id){
        return productService.findById(id);
    }

    @POST
    public Response save(Product b){
        productService.save(b);
        URI productUri = uriInfo.getAbsolutePathBuilder().path(b.getId().toString()).build();
        return Response.created(productUri).build();
    }

    @DELETE @Path("{id}")
    public Response delete(@PathParam("id") Long id) {
        productService.delete(id);
        return Response.noContent().build();
    }
}
