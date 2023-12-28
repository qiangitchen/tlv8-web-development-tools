package com.tulin.v8.webtools.jseditor.editors.additional;

import java.util.ArrayList;
import java.util.List;

import com.tulin.v8.webtools.htmleditor.HTMLPlugin;
import com.tulin.v8.webtools.jseditor.editors.model.JavaScriptModel;

/**
 * <code>IAdditionalJavaScriptCompleter</code> implementation for <strong>ECMA
 * Script</strong>.
 * 
 * @author shinsuke
 */
public class ECMAScriptCompleter extends AbstractCompleter {
	public List<JavaScriptModel> loadModel(List<JavaScriptModel> libModelList) {
		try {
			JsFileCache jsFileCache = getJsFileCache("js/ecma/ecmascript-5.js");
			String source = new String(jsFileCache.getBytes(), "UTF-8");
			JavaScriptModel model = new JavaScriptModel(jsFileCache.getFile(),
					source, libModelList);
			if (model != null) {
				libModelList.add(model);
				List<JavaScriptModel> list = new ArrayList<JavaScriptModel>();
				list.add(model);
				return list;
			}
		} catch (Exception e) {
			HTMLPlugin.logException(e);
		}
		return null;
	}
}
