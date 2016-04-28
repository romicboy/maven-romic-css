package com.meimi.maven.main.entity;

import java.util.Map;

public class CssEntity {
	private String selector;
	private Map<String, String> attribute;
	public String getSelector() {
		return selector;
	}
	public void setSelector(String selector) {
		this.selector = selector;
	}
	public Map<String, String> getAttribute() {
		return attribute;
	}
	public void setAttribute(Map<String, String> attribute) {
		this.attribute = attribute;
	}
	@Override
	public String toString() {
		return "CssEntity [selector=" + selector + ", attribute=" + attribute + "]";
	}
}
