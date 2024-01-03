package com.tulin.v8.webtools.css;

import java.util.ArrayList;
import java.util.List;

/**
 * CSS样式属性值
 */
public class CSSValue {
	private String name;
	private List<String> values = new ArrayList<>();

	public CSSValue() {
	}

	public CSSValue(String name) {
		this.setName(name);
	}

	public CSSValue(String name, String[] value) {
		this.setName(name);
		for (String v : value) {
			values.add(v);
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setValues(String[] value) {
		values.clear();
		for (String v : value) {
			values.add(v);
		}
	}

	public void setValues(List<String> values) {
		this.values = values;
	}

	public void addValue(String value) {
		values.add(value);
	}

	public List<String> getValues() {
		return values;
	}

}
