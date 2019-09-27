package cropcert.pages.service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

import cropcert.pages.dao.PageDao;
import cropcert.pages.model.Page;

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
		
		page.setPageIndex(page.getId());
		
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
        
        String title   = jsonObject.getString("title");
        String heading = jsonObject.getString("heading");
        String content = jsonObject.getString("content");
        
        if(title != null)
            page.setTitle(title);
        if(content != null)
            page.setContent(content);
        if(heading != null)
            page.setHeading(heading);
        
        page = update(page);
		return page;
	}

	public void updateTreeStructure(String jsonString) throws JSONException {
		JSONArray jsonArray = new JSONArray(jsonString);
		for(int i = 0; i < jsonArray.length(); i++) {
			JSONObject object = (JSONObject) jsonArray.get(i);
			
			Long id        = object.getLong("id");
			Long parentId  = object.getLong("parentId");
			Long pageIndex = object.getLong("pageIndex");
			
			Page page = findById(id);
			page.setParentId(parentId);
			page.setPageIndex(pageIndex);
			update(page);
		}
	}

	public Page markAsDelete(Long id) {
		Page page = findById(id);
		page.setIsDeleted(true);
		update(page);
		return page;
	}
}
