package io.cloudonix.sdk.cxml;

public enum DialStatus {
	ringing, // not expected to get this as a dial status, it is interim
	completed,
	answered,
	busy,
	noanswer,
	failed,
	canceled;
	
	public static DialStatus parse(String dialStatus) {
		dialStatus = dialStatus.replaceAll("[^a-zA-Z]+", "").toLowerCase();
		for (DialStatus status : DialStatus.values()) {
			if (status.name().equals(dialStatus))
				return status;
		}
		return failed;
	}
}
