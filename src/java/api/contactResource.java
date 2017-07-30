package api;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import db.DAL;
import db.DataBase;
import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import model.contact;

/**
 * REST Web Service
 *
 * @author chen
 */
@Path("contact")
public class contactResource {

    @Context
    private ServletContext servlet_context;
    @Context
    private UriInfo context;

    /**
     * Retrieves representation of an instance of api.contactResource
     *
     * @return Json with all the contact
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllContacts() {
        JsonObject result = new JsonObject();
        JsonArray list = DAL.allContactList();
        result.add("data", list);
        result.addProperty("status", 0);
        result.addProperty("msg", "");
        return Response.status(200).entity(result.toString()).build();
    }

    /**
     *
     * @param content
     * @return
     */
    @DELETE
    @Path("deleteContact/{idValue}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteContact(@PathParam("idValue") String idValue) {
        JsonObject result = new JsonObject();
        String res = DAL.deleteContactById(idValue);
        if (!res.equals("-1")) {
            result.addProperty("data", res);
            result.addProperty("status", 0);
            result.addProperty("msg", "");
        } else {
            result.addProperty("msg", "Unable to delete contact");
            result.addProperty("status", -1);
        }
        return Response.status(200).entity(result.toString()).build();
    }

    @PUT
    @Path("editContact")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(String content) {
        JsonObject o = (JsonObject) new JsonParser().parse(content);
        JsonObject result = new JsonObject();
        JsonObject tmp = DAL.getcontactById(o.get("id").getAsString());
        contact c = new contact(tmp.get("id").getAsString(), o.get("name").getAsString(), o.get("job").getAsString(), o.get("img").getAsString());
        String res = DAL.updateContact(c);
        if (!res.equals("-1")) {
            result.addProperty("data", res);
            result.addProperty("status", 0);
            result.addProperty("msg", "");
        } else {
            result.addProperty("msg", "Unable to update contact");
            result.addProperty("status", -1);
        }
        return Response.status(200).entity(result.toString()).build();
    }

    @POST
    @Path("addContact")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response insertToList(String content) {
        JsonObject o = (JsonObject) new JsonParser().parse(content);
        JsonObject result = new JsonObject();
        String res = DAL.insert(new contact("", o.get("name").getAsString(), o.get("job").getAsString(), o.get("img").getAsString()));
        if (!res.equals("-1")) {
            result.addProperty("data", res);
            result.addProperty("status", 0);
            result.addProperty("msg", "");
        } else {
            result.addProperty("msg", "Unable to insert contact");
            result.addProperty("status", -1);
        }
        return Response.status(200).entity(result.toString()).build();
    }

}
