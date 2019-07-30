package io.cloudonix.sdk.cxml;

import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import io.vertx.core.json.JsonArray;

public class Dial extends ResponseElement {

	private Element dial;

	public Dial(ResponseElement parent, Document doc, String to) {
		super(parent, doc);
		dial = doc.createElement("Dial");
		dial.appendChild(doc.createTextNode(to));
	}
	
	public Dial trunks(List<Integer> trunks) {
		dial.setAttribute("trunks",
			trunks.stream().collect(JsonArray::new, JsonArray::add, JsonArray::addAll).encode());
		return this;
	}
	
	public Dial action(String uri) {
		dial.setAttribute("action", uri);
		return this;
	}
	
	@Override
	protected void encode(Element container) {
		container.appendChild(dial);
	}
	
}