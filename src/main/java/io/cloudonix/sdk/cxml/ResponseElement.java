package io.cloudonix.sdk.cxml;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public abstract class ResponseElement {
	protected ResponseElement parent;
	protected List<ResponseElement> elements = new ArrayList<>(); 
	protected Document doc;

	protected ResponseElement(ResponseElement parent, Document doc) {
		this.parent = parent;
		this.doc = doc;
		if (Objects.nonNull(parent)) // I'm not the parent response
			parent.add(this);
	}
	
	public ResponseElement container() {
		return parent;
	}
	
	public Response response() {
		return parent.response();
	}
	
	public ResponseElement add(ResponseElement child) {
		elements.add(child);
		return this;
	}
	
	protected void encode(Element container) {
		elements.forEach(el -> el.encode(container));
	}
	
	/* Element helpers */
	
	public Dial dial(String to) {
		return new Dial(this, doc, to);
	}
	
	public Response reject() {
		new Reject(this, doc);
		return response();
	}

	public Response reject(String reason) {
		new Reject(this, doc, reason);
		return response();
	}

	public Say say(String text) {
		return new Say(this, doc, text);
	}

	public Say say() {
		return new Say(this, doc);
	}

}