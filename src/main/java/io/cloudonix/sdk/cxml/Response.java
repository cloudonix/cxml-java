package io.cloudonix.sdk.cxml;

import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

public class Response extends ResponseElement {
	
	Response() {
		super(null, makeDoc());
		doc.appendChild(doc.createElement("Response"));
	}
	
	public String encode() {
		encode(doc.getDocumentElement());
		StringWriter writer = new StringWriter();
		try {
			TransformerFactory.newInstance().newTransformer().transform(new DOMSource(doc), new StreamResult(writer));
		} catch (Exception e) {
			return "error";
		}
		return writer.toString();
	}
	
	@Override
	public Response response() {
		return this;
	}
	
	private static Document makeDoc() {
		try {
			return DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
		} catch (ParserConfigurationException e) {
			return null;
		}
	}

}