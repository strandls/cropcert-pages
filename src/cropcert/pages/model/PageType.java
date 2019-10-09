package cropcert.pages.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "pageType")
@XmlEnum
public enum PageType {

	@XmlEnumValue("Content")
	CONTENT("CONTENT"),
	@XmlEnumValue("Redirect")
	REDIRECT("REDIRECT");
	
	private String value;
	
	PageType(String value) {
		this.value = value;
	}
	
	public static PageType fromValue(String value) {
		for(PageType pageType : PageType.values()) {
			if(pageType.value.equals(value))
				return pageType;
		}
		throw new IllegalArgumentException(value);
	}
}
