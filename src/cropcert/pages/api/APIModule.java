package cropcert.pages.api;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

public class APIModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(ImageApi.class).in(Scopes.SINGLETON);
		bind(PageApi.class).in(Scopes.SINGLETON);
	}
}
