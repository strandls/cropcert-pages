package cropcert.pages.api;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.inject.Inject;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

import cropcert.pages.service.ImageService;
import io.swagger.annotations.Api;

@Path("image")
@Api("Image upload")
public class ImageApi {
	
	@Inject
	private ImageService imageService;
	
	@GET
	@Path("{image}")
	@Consumes(MediaType.TEXT_PLAIN)
	public Response getImage(@PathParam("image") String image) {        
        InputStream fileStream;
		try {
			fileStream = imageService.getImage(image);
			return Response
					.ok(fileStream, MediaType.APPLICATION_OCTET_STREAM)
					.header("content-disposition","attachment; filename = "+image)
					.build();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addImage(@Context HttpServletRequest request,
			@FormDataParam("file") InputStream inputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetails) {
		Map<String, Object> result = imageService.addImage(inputStream, fileDetails, request);
		return Response.ok(result).build();
	}
}
