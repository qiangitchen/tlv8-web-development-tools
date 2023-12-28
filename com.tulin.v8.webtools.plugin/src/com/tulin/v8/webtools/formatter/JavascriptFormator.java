package com.tulin.v8.webtools.formatter;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class JavascriptFormator {
	public static String format(String text) throws Exception {
		ScriptEngineManager mgr = new ScriptEngineManager();
		ScriptEngine engine = mgr.getEngineByName("JavaScript");
		String scriptText = FileUtils.FileToString("/js/format/jsformat.js");
		engine.eval(scriptText);
		Invocable inv = (Invocable) engine;
		String value = String.valueOf(
				inv.invokeFunction("js_beautify", new Object[] { text, Integer.valueOf(1), "\t", Integer.valueOf(0) }));
		return value;
	}
}
