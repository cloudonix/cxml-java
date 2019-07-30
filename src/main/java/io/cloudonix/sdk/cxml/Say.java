package io.cloudonix.sdk.cxml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Say extends ResponseElement {

	public class SayAs extends ResponseElement {

		private Element el;

		public SayAs(ResponseElement parent, Document doc, String interpretAs, String text) {
			super(parent, doc);
			el = createSayAs(interpretAs);
			el.appendChild(doc.createTextNode(text));
		}
		
		private Element createSayAs(String interpretAs) {
			var el = doc.createElement("say-as");
			el.setAttribute("interpret-as", interpretAs);
			return el;
		}
		
		@Override
		protected void encode(Element container) {
			container.appendChild(el);
		}

	}

	public class Break extends ResponseElement {

		private Element el;

		public Break(ResponseElement parent, Document doc) {
			super(parent, doc);
			el = doc.createElement("break");
		}
		
		@Override
		protected void encode(Element container) {
			container.appendChild(el);
		}
	}
	
	private Element say;
	
	public Say(ResponseElement parent, Document doc) {
		super(parent, doc);
		say = doc.createElement("Say");
	}
	
	@Override
	protected void encode(Element container) {
		container.appendChild(say);
		super.encode(say);
	}

	public Say(ResponseElement parent, Document doc, String text) {
		this(parent, doc);
		say.appendChild(doc.createTextNode(text));
	}
	
	public Say sayAsCardinal(String text) {
		add(new SayAs(this, doc, "cardinal", text));
		return this;
	}
	
	public Say sayAsOrdinal(String text) {
		add(new SayAs(this, doc, "ordinal", text));
		return this;
	}
	
	public Say sayAsTelephone(String text) {
		add(new SayAs(this, doc, "telephone", text));
		return this;
	}
	
	public Say sayAsDigits(String text) {
		add(new SayAs(this, doc, "digits", text));
		return this;
	}
	
	public Say sayAsTime(String text) {
		add(new SayAs(this, doc, "time", text));
		return this;
	}
	
	public Say sayAsDate(String text) {
		add(new SayAs(this, doc, "date", text));
		return this;
	}

	public Say pause() {
		add(new Break(this, doc));
		return this;
	}
}
