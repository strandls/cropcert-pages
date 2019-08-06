package cropcert.pages.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import cropcert.pages.model.Page;

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

}
