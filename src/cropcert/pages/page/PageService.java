package cropcert.pages.page;

import java.io.IOException;

import org.json.JSONException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

import cropcert.pages.common.AbstractService;

public class PageService extends AbstractService<Page> {

	@Inject
	private ObjectMapper objectMappper;

	@Inject
	public PageService(PageDao dao) {
		super(dao);
	}

	public Page save(String jsonString)
			throws JsonParseException, JsonMappingException, IOException, JSONException {
		Page page = objectMappper.readValue(jsonString, Page.class);
		page = save(page);
		return page;
	}
}
