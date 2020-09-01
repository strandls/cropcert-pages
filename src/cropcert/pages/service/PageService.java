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
import cropcert.pages.model.PageType;

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
        
        String title = null;
        String heading = null;
        String content = null;
        String url = null;
        String bannerUrl = null;
        String pageType = null; 
        String description = null;
        
        if(!jsonObject.isNull("title"))
        	title   = jsonObject.getString("title");
        if(!jsonObject.isNull("heading"))
        	heading = jsonObject.getString("heading");
        if(!jsonObject.isNull("content"))
        	content = jsonObject.getString("content");
        if(!jsonObject.isNull("url"))
        	url = jsonObject.getString("url");
        if(!jsonObject.isNull("bannerUrl"))
        	bannerUrl = jsonObject.getString("bannerUrl");
        if(!jsonObject.isNull("pageType"))
        	pageType = jsonObject.getString("pageType");
        if(!jsonObject.isNull("description"))
        	description = jsonObject.getString("description");
        
        page.setTitle(title);
        page.setContent(content);
        page.setHeading(heading);
        page.setUrl(url);
        if(pageType != null)
        	page.setPageType(PageType.fromValue(pageType));
        else
        	page.setPageType(null);
        page.setBannerUrl(bannerUrl);
        page.setDescription(description);
        
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
