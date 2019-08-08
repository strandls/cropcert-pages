package cropcert.pages.dao;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

public class DaoModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(PageDao.class).in(Scopes.SINGLETON);
	}
}
