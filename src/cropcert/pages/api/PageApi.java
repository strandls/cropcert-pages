package cropcert.pages.api;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.hibernate.exception.ConstraintViolationException;
import org.json.JSONException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.inject.Inject;

import cropcert.pages.model.Page;
import cropcert.pages.service.PageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Path("page")
@Api("Page")
public class PageApi {
	
	private PageService pageService;
	
	@Inject
	public PageApi(PageService batchProductionService) {
		this.pageService = batchProductionService;
	}
	
	@Path("{id}")
	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(
			value = "Get the page by its id",
			response = Page.class)
	public Response find(@PathParam("id") Long id) {
		Page page = pageService.findById(id);
		return Response.status(Status.CREATED).entity(page).build();
	}
	
	@Path("all")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(
			value = "Get list of all the pages",
			response = List.class)
	public List<Page> findAll(
			@DefaultValue("-1") @QueryParam("limit") Integer limit,
			@DefaultValue("-1") @QueryParam("offset") Integer offset) {
		if(limit==-1 || offset ==-1)
			return pageService.findAll();
		else
			return pageService.findAll(limit, offset);
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(
			value = "Save the page",
			response = Page.class)
	public Response save(String  jsonString) {
		try {
			Page page = pageService.save(jsonString);
			return Response.status(Status.CREATED).entity(page).build();
		} catch(ConstraintViolationException e) {
			return Response.status(Status.CONFLICT).tag("Dublicate key").build();
		}
		catch (JsonParseException | JsonMappingException | JSONException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Response.status(Status.NO_CONTENT).build();
	}
	
	@PUT
	@Path("parent")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(
			value = "Update the parent of the page",
			response = Page.class)
	public Response updateParent(String jsonString) {
		Page page;
		try {
			page = pageService.upateParent(jsonString);
			return Response.status(Status.CREATED).entity(page).build();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return Response.status(Status.NO_CONTENT).build();
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(
			value = "Update the page itself",
			response = Page.class)
	public Response updatePage(String jsonString) {
		Page page;
		try {
			page = pageService.upatePage(jsonString);
			return Response.status(Status.CREATED).entity(page).build();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return Response.status(Status.NO_CONTENT).build();
	}
}
