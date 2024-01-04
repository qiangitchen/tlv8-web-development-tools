package com.tulin.v8.webtools.ide.formatter;

import java.io.StringReader;

import org.w3c.css.sac.InputSource;
import org.w3c.dom.css.CSSRuleList;
import org.w3c.dom.css.CSSStyleRule;
import org.w3c.dom.css.CSSStyleSheet;
import org.w3c.dom.css.CSSValue;

import com.steadystate.css.parser.CSSOMParser;

/**
 * CSS格式化
 * 
 * @author chenqian
 */
public class CSSFormator {

	public static String format(String text) throws Exception {
		try {
			StringBuilder fcss = new StringBuilder();
			// 使用cssparser库解析CSS
			CSSOMParser parser = new CSSOMParser();
			InputSource source = new InputSource(new StringReader(text));
			CSSStyleSheet stylesheet = parser.parseStyleSheet(source, null, null);

			// 遍历所有的CSS规则
			CSSRuleList ruleList = stylesheet.getCssRules();
			for (int i = 0; i < ruleList.getLength(); i++) {
				if (ruleList.item(i) instanceof CSSStyleRule) {
					CSSStyleRule rule = (CSSStyleRule) ruleList.item(i);
					String selector = rule.getSelectorText();
					String properties = "";

					// 遍历所有的CSS属性
					for (int j = 0; j < rule.getStyle().getLength(); j++) {
						String property = rule.getStyle().item(j);
						CSSValue value = rule.getStyle().getPropertyCSSValue(property);
						properties += "    " + property + ": " + value.getCssText() + ";\n";
					}

					// 输出格式化后的CSS规则
					fcss.append("\n" + selector + " {\n" + properties + "}");
				}
			}
			return fcss.toString().replaceFirst("\n", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return text;
	}

}
