package cropcert.pages.image;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.sun.jersey.core.header.FormDataContentDisposition;

public class ImageService {
	
	public final static String rootPath = System.getProperty("user.home") + 
			File.separatorChar + "cropcert-image";

	public Map<String, Object> addImage(InputStream inputStream, FormDataContentDisposition fileDetails,
			HttpServletRequest request) {
		String fileName = fileDetails.getFileName();

		String fileLocation = rootPath + File.separatorChar + fileName;

		boolean uploaded = writeToFile(inputStream, fileLocation);

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("uploaded", uploaded);
		
		if (uploaded) {
			String uri = request.getRequestURI() + "/" + fileName;
			result.put("uri", uri);
		} else {
			result.put("error", "enable to upload image");
		}
		return result;
	}

	private boolean writeToFile(InputStream inputStream, String fileLocation) {
		try {
			OutputStream out = new FileOutputStream(new File(fileLocation));
			int read = 0;
			byte[] bytes = new byte[1024];

			out = new FileOutputStream(new File(fileLocation));
			while ((read = inputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			out.flush();
			out.close();

			return true;
		} catch (IOException e) {
			e.printStackTrace();

		}
		return false;
	}

	public InputStream getImage(String image) throws FileNotFoundException {
		
		String fileLocation = rootPath + File.separatorChar + image;
		InputStream in = new FileInputStream(new File(fileLocation));
        return in;
	}
}
