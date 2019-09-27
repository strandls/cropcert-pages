package cropcert.pages.api;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.JSONException;

import com.google.inject.Inject;

import cropcert.pages.filter.Permissions;
import cropcert.pages.filter.TokenAndUserAuthenticated;
import cropcert.pages.model.Page;
import cropcert.pages.service.PageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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
	@ApiOperation(value = "Get the page by its id", response = Page.class)
	public Response find(@Context HttpServletRequest request, @PathParam("id") Long id) {
		Page page = pageService.findById(id);
		return Response.status(Status.CREATED).entity(page).build();
	}

	@Path("all")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Get list of all the pages", response = Page.class, responseContainer = "List")
	public Response findAll(@Context HttpServletRequest request) {
		List<Page> pages = pageService.findAll();
		return Response.ok().entity(pages).build();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Save the page", response = Page.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header") })
	@TokenAndUserAuthenticated(permissions = { Permissions.ADMIN })
	public Response save(@Context HttpServletRequest request, String jsonString) {
		Page page;
		try {
			page = pageService.save(jsonString);
			return Response.status(Status.CREATED).entity(page).build();
		} catch (IOException | JSONException e) {
			e.printStackTrace();
		}
		return Response.status(Status.NO_CONTENT).build();
	}
	
	@PUT
	@Path("tree")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Update the tree structure", response = Page.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header") })
	@TokenAndUserAuthenticated(permissions = { Permissions.ADMIN })
	public Response updateTreeStructure(@Context HttpServletRequest request, String jsonString) {
		try {
			pageService.updateTreeStructure(jsonString);
			return Response.ok().build();
		} catch (JSONException e) {
			e.printStackTrace();
			return Response.status(Status.BAD_REQUEST).build();
		}
	}

	@PUT
	@Path("parent")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Update the parent of the page", response = Page.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header") })
	@TokenAndUserAuthenticated(permissions = { Permissions.ADMIN })
	public Response updateParent(@Context HttpServletRequest request, String jsonString) {
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
	@ApiOperation(value = "Update the page itself", response = Page.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header") })
	@TokenAndUserAuthenticated(permissions = { Permissions.ADMIN })
	public Response updatePage(@Context HttpServletRequest request, String jsonString) {
		Page page;
		try {
			page = pageService.upatePage(jsonString);
			return Response.status(Status.CREATED).entity(page).build();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return Response.status(Status.NO_CONTENT).build();
	}
	
	@Path("{id}")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Delete the page", response = Page.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header") })
	@TokenAndUserAuthenticated(permissions = { Permissions.ADMIN }) 
	public Response markAsDeletePage(@Context HttpServletRequest request, @PathParam("id") Long id) {
		Page page = pageService.markAsDelete(id);
		return Response.ok().entity(page).build();
	}
}
