package com.tulin.v8.webtools.js.additional;

import java.util.ArrayList;
import java.util.List;

import com.tulin.v8.webtools.WebToolsPlugin;
import com.tulin.v8.webtools.js.model.JavaScriptModel;

/**
 * <code>IAdditionalJavaScriptCompleter</code> implementation for
 * <strong>jQuery</strong>.
 * 
 * @Deprecated 使用tern提供js代码辅助
 */
@Deprecated
public class JQueryCompleter extends AbstractCompleter {

	public List<JavaScriptModel> loadModel(List<JavaScriptModel> libModelList) {
		try {
			JsFileCache jsFileCache = getJsFileCache("js/jquery/1.7.1/jquery-1.7.1.js");
			String source = new String(jsFileCache.getBytes(), "UTF-8");
			JavaScriptModel model = new JavaScriptModel(jsFileCache.getFile(), source, libModelList);
			if (model != null) {
				libModelList.add(model);
				List<JavaScriptModel> list = new ArrayList<JavaScriptModel>();
				list.add(model);
				return list;
			}
		} catch (Exception e) {
			WebToolsPlugin.logException(e);
		}
		return null;
	}
}
