package io.cloudonix.sdk.cxml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Reject extends ResponseElement {

	private Element reject;

	protected Reject(ResponseElement parent, Document doc) {
		super(parent, doc);
		reject = doc.createElement("Reject");
	}
	
	protected Reject(ResponseElement parent, Document doc, String reason) {
		super(parent, doc);
		reject = doc.createElement("Reject");
		reject.setAttribute("reason", reason);
	}

	@Override
	protected void encode(Element container) {
		container.appendChild(reject);
	}

}
