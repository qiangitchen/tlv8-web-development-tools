package com.tulin.v8.webtools.jsp.editors;

import java.util.ArrayList;
import java.util.List;

import jp.aonir.fuzzyxml.FuzzyXMLComment;
import jp.aonir.fuzzyxml.FuzzyXMLDocType;
import jp.aonir.fuzzyxml.FuzzyXMLElement;
import jp.aonir.fuzzyxml.FuzzyXMLNode;
import jp.aonir.fuzzyxml.FuzzyXMLText;

import org.eclipse.swt.graphics.Image;

import com.tulin.v8.webtools.WebToolsPlugin;
import com.tulin.v8.webtools.html.editors.HTMLOutlinePage;

public class JSPOutlinePage extends HTMLOutlinePage {

	public JSPOutlinePage(JSPSourceEditor editor) {
		super(editor);
	}

	@Override
	protected Image getNodeImage(FuzzyXMLNode element) {
		if (element instanceof FuzzyXMLElement) {
			return super.getNodeImage(element);
		} else if (element instanceof FuzzyXMLText) {
			FuzzyXMLText t = (FuzzyXMLText) element;
			if (t.getValue().startsWith("<%--")) {
				return WebToolsPlugin.getDefault().getImageRegistry().get(WebToolsPlugin.ICON_COMMENT);
			}
			return WebToolsPlugin.getDefault().getImageRegistry().get(WebToolsPlugin.ICON_TAG);
		}
		return super.getNodeImage(element);
	}

	@Override
	protected String getNodeText(FuzzyXMLNode node) {
		if (node instanceof FuzzyXMLText) {
			String text = ((FuzzyXMLText) node).getValue();
			if (text.startsWith("<%--")) {
				return "#comment";
			} else if (text.startsWith("<%@")) {
				return "jsp:directive";
			} else if (text.startsWith("<%=")) {
				return "jsp:expression";
			} else if (text.startsWith("<%")) {
				return "jsp:scriptlet";
			}
		}
		return super.getNodeText(node);
	}

	@Override
	protected Object[] getNodeChildren(FuzzyXMLElement element) {
		List<FuzzyXMLNode> children = new ArrayList<FuzzyXMLNode>();
		FuzzyXMLNode[] nodes = element.getChildren();
		for (int i = 0; i < nodes.length; i++) {
			if (nodes[i] instanceof FuzzyXMLElement) {
				children.add(nodes[i]);
			} else if (nodes[i] instanceof FuzzyXMLText && ((FuzzyXMLText) nodes[i]).getValue().startsWith("<%")) {
				children.add(nodes[i]);
			} else if (nodes[i] instanceof FuzzyXMLDocType) {
				children.add(nodes[i]);
			} else if (nodes[i] instanceof FuzzyXMLComment) {
				children.add(nodes[i]);
			}
		}
		return children.toArray(new FuzzyXMLNode[children.size()]);
	}

}
