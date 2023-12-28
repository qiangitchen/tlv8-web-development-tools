package com.tulin.v8.webtools.formatter;

import java.io.StringWriter;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.io.HTMLWriter;
import org.dom4j.io.OutputFormat;

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
		Document document = null;
		document = DocumentHelper.parseText(str);

		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("utf-8");
		StringWriter writer = new StringWriter();

		HTMLWriter htmlWriter = new HTMLWriter(writer, format);

		htmlWriter.write(document);
		htmlWriter.close();
		return writer.toString();
	}
}
