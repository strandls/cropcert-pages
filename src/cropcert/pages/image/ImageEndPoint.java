package cropcert.pages.image;

import com.google.common.io.Files;
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
import java.io.IOException;
import java.io.OutputStream;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.StreamingOutput;

@Path("image")
public class ImageEndPoint {

    @Inject
    private ImageService imageService;

    @GET
    @Path("{image}")
    @Consumes(MediaType.TEXT_PLAIN)
    public Response getImage(@PathParam("image") String image) {
        try {
            InputStream is = imageService.getImage(image);
            return Response.ok(new StreamingOutput() {
                @Override
                public void write(OutputStream out) throws IOException, WebApplicationException {
                    byte[] buf = new byte[8192];
                    int c;
                    while ((c = is.read(buf, 0, buf.length)) > 0) {
                        out.write(buf, 0, c);
                        out.flush();
                    }
                    out.close();
                }
            }).type("image/" + Files.getFileExtension(image)).build();
        } catch (IOException ex) {
        }
        return null;
    }

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addImage(@Context HttpServletRequest request,
            @FormDataParam("upload") InputStream inputStream,
            @FormDataParam("upload") FormDataContentDisposition fileDetails) {
        Map<String, Object> result = imageService.addImage(inputStream, fileDetails, request);
        return Response.ok(result).build();
    }
}
