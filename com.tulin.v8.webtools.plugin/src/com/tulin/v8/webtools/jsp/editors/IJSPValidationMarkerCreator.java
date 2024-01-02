package com.tulin.v8.webtools.jsp.editors;

public interface IJSPValidationMarkerCreator {

	public void addMarker(int severity, int offset, int length, String message);
}
