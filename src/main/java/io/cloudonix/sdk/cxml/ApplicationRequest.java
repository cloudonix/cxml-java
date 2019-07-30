package io.cloudonix.sdk.cxml;

import static java.util.AbstractMap.SimpleEntry;

import java.util.*;
import java.util.Map.Entry;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import io.vertx.core.json.DecodeException;
import io.vertx.core.json.JsonObject;

public class ApplicationRequest {
	
	static Logger log = Logger.getLogger(ApplicationRequest.class.getName());
	
	private Map<String, String> params;
	private Map<String, String> headers;
	
	public ApplicationRequest(List<Entry<String, String>> params, List<Entry<String, String>> headers) {
		this.params = params.stream().collect(Collectors.toMap(Entry::getKey, Entry::getValue));
		this.headers = headers.stream().collect(Collectors.toMap(Entry::getKey, Entry::getValue));
		
		this.headers.entrySet().stream().filter(e -> e.getKey().startsWith("X-Cx-"))
			.forEach(e -> {
				this.params.put(e.getKey().substring(5), e.getValue());
			});
		
	}
	
	public static ApplicationRequest from(String method, 
			Map<String, String> headers, Map<String, String> query, Map<String, String> form, 
			String body) {
		log.info("Parsing CPaaS application request: method: " + method + 
				", query: " + query + ", form: " + form + ", body: " + body);
		List<Entry<String, String>> requestParams = new ArrayList<>();
		requestParams.addAll(query.entrySet());
		requestParams.addAll(form.entrySet());
		requestParams.addAll(readRequestBody(method, body));
		return new ApplicationRequest(requestParams, headers.entrySet().stream().collect(Collectors.toList()));
	}
	
	private static List<Entry<String,String>> readRequestBody(String method, String body) {
		try {
			if ("post".equalsIgnoreCase(method))
				return new JsonObject(body).stream()
						.map(e -> new SimpleEntry<String,String>(e.getKey(), String.valueOf(e.getValue())))
						.collect(Collectors.toList());
		} catch (DecodeException e) {
		}
		return Collections.emptyList();
	}

	public String to() {
		String to = params.get("To");
		if (Objects.isNull(to))
			to = "";
		if (to.startsWith("+"))
			to = to.substring(1);
		return to;
	}
	
	public Response response() {
		return new Response();
	}

	public DialStatus dialStatus() {
		String dialStatus = params.get("DialCallStatus");
		if(Objects.isNull(dialStatus))
			throw new RuntimeException("Dial status can't be null!!");
		return DialStatus.parse(dialStatus);
	}
	
	@Override
	public String toString() {
		return "{CXML request: Parameters: " + params + "; Headers: " + headers + "}";
	}
}
