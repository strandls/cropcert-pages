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
import javax.ws.rs.core.StreamingOutput;

import com.google.common.io.Files;
import com.google.inject.Inject;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

import cropcert.pages.service.ImageService;
import static cropcert.pages.service.ImageService.rootPath;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.ws.rs.WebApplicationException;

@Path("image")
@Api("Image")
public class ImageApi {

    @Inject
    private ImageService imageService;

    @GET
    @Path("{image}")
    @Consumes(MediaType.TEXT_PLAIN)
    @ApiOperation(value = "Get the image by url", response = StreamingOutput.class)
    public Response getImage(@Context HttpServletRequest request, @PathParam("image") String image) throws FileNotFoundException {
        String fileLocation = rootPath + File.separatorChar + image;
        InputStream in = new FileInputStream(new File(fileLocation));
        StreamingOutput sout;
        sout = new StreamingOutput() {
            @Override
            public void write(OutputStream out) throws IOException, WebApplicationException {
                byte[] buf = new byte[8192];
                int c;
                while ((c = in.read(buf, 0, buf.length)) > 0) {
                    out.write(buf, 0, c);
                    out.flush();
                }
                out.close();
            }
        };
        return Response.ok(sout).type("image/" + Files.getFileExtension(image)).build();
    }

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Save the image from inputStream", response = Map.class)
    public Response addImage(@Context HttpServletRequest request, @FormDataParam("upload") InputStream inputStream,
            @FormDataParam("upload") FormDataContentDisposition fileDetails) {
        Map<String, Object> result = imageService.addImage(inputStream, fileDetails, request);
        return Response.ok(result).build();
    }
}
