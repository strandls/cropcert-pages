package cropcert.pages.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.Transformers;

import com.google.inject.Inject;

import cropcert.pages.model.Page;
import cropcert.pages.model.PageModel;

public class PageDao extends AbstractDao<Page, Long>{

	@Inject
	protected PageDao(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public Page findById(Long id) {
		Session session = sessionFactory.openSession();
		Page entity = null;
		try {
			entity = session.get(Page.class, id);
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
		return entity;
	}
	
	@Override
	public List<Page> findAll() {
		Session session = sessionFactory.openSession();
		Criteria cr = session.createCriteria(Page.class)
				.setProjection(Projections.projectionList()
						.add(Projections.property("id"), "id")
						.add(Projections.property("parentId"), "parentId")
						.add(Projections.property("pageIndex"), "pageIndex")
						.add(Projections.property("title"), "title")
						.add(Projections.property("pageType"), "pageType")
						.add(Projections.property("url"), "url")
						.add(Projections.property("isDeleted"), "isDeleted"))
				.setResultTransformer(Transformers.aliasToBean(PageModel.class));
		return cr.list();
	}

}
