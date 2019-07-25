package cropcert.pages.page;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

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
		
		Timestamp timestamp = new Timestamp(new Date().getTime());
		if(page.getCreatedOn() == null)
			page.setCreatedOn(timestamp);
		if(page.getModifiedOn() == null)
			page.setModifiedOn(timestamp);
		page.setIsDeleted(false);
		
		page = save(page);
		return page;
	}

	public Page upateParent(String jsonString) throws JSONException {
		JSONObject jsonObject = new JSONObject(jsonString);
        Long id = jsonObject.getLong("id");
        Page page = findById(id);
        
        Long parentId = jsonObject.getLong("parentId");
        page.setParentId(parentId);
        
        page = update(page);
		return page;
	}

	public Page upatePage(String jsonString) throws JSONException {
		JSONObject jsonObject = new JSONObject(jsonString);
        Long id = jsonObject.getLong("id");
        Page page = findById(id);
        
        Long parentId  = jsonObject.getLong("parentId");
        String title   = jsonObject.getString("title");
        String content = jsonObject.getString("content");
        
        if(parentId != null)
        	page.setParentId(parentId);
        if(title != null)
        	page.setTitle(title);
        if(content != null)
        	page.setContent(content);
        
        page = update(page);
		return page;
	}
}
