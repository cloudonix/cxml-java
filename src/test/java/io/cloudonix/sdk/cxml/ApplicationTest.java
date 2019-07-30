package io.cloudonix.sdk.cxml;

import static org.junit.Assert.*;

import java.util.Collections;

import org.junit.Test;

public class ApplicationTest {
	
	@Test
	public void test() {
		ApplicationRequest req = new ApplicationRequest(Collections.emptyList(), Collections.emptyList());
		String code = "1234";
		var res = req.response();
		res.say("Your verification code is").pause().sayAsDigits(code);
		assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?><Response><Say>Your verification code is<break/><say-as interpret-as=\"digits\">1234</say-as></Say></Response>",res.encode());
	}
	
}
