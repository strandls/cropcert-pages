package cropcert.pages.page;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

public class PageModule extends AbstractModule{

	@Override
	protected void configure() {
		bind(PageDao.class).in(Scopes.SINGLETON);
		bind(PageService.class).in(Scopes.SINGLETON);
		bind(PageEndPoint.class).in(Scopes.SINGLETON);
	}
}
