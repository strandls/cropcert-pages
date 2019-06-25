package cropcert.pages.image;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

public class ImageModule  extends AbstractModule {

	@Override
	protected void configure() {
		bind(ImageService.class).in(Scopes.SINGLETON);
		bind(ImageEndPoint.class).in(Scopes.SINGLETON);
	}
	
}
