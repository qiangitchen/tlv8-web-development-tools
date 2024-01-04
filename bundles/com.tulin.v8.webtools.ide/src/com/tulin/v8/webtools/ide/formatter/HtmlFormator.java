package com.tulin.v8.webtools.ide.formatter;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class HtmlFormator {
	public static String format(String text) throws Exception {
		String value;
		try {
			return formatHtml(text);
		} catch (Exception e) {
			ScriptEngineManager mgr = new ScriptEngineManager();
			ScriptEngine engine = mgr.getEngineByName("JavaScript");
			String scriptText = FileUtils.FileToString("/js/format/jsformat.js") + "\n"
					+ FileUtils.FileToString("/js/format/htmlformat.js");
			engine.eval(scriptText);
			Invocable inv = (Invocable) engine;
			value = String.valueOf(inv.invokeFunction("style_html",
					new Object[] { text, Integer.valueOf(1), "\t", Integer.valueOf(80) }));
		}
		return value;
	}

	public static String formatHtml(String str) throws Exception {
		Document doc = Jsoup.parse(str);
		doc.outputSettings().indentAmount(4);
		doc.outputSettings().prettyPrint(true);
		return doc.html();
	}
}
