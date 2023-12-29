package com.tulin.v8.webtools;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.tulin.v8.webtools.js.launch.ClosureCompilerLaunchUtil;
import com.tulin.v8.webtools.js.launch.JavaScriptLaunchUtil;

public class WebToolsPlugin extends AbstractUIPlugin {
	// The shared instance.
	private static WebToolsPlugin plugin;
	// Resource bundle.
	private ResourceBundle resourceBundle;
	// Color Provider
	private ColorProvider colorProvider;

	public static final String ICON_HTML = "_icon_html";
	public static final String ICON_XML = "_icon_xml";
	public static final String ICON_JSP = "_icon_jsp";
	public static final String ICON_CSS = "_icon_css";
	public static final String ICON_WEB = "_icon_web";
	public static final String ICON_FILE = "_icon_file";
	public static final String ICON_TAG = "_icon_tag";
	public static final String ICON_ATTR = "_icon_attribute";
	public static final String ICON_VALUE = "_icon_value";
	public static final String ICON_FOLDER = "_icon_folder";
	public static final String ICON_BUTTON = "_icon_button";
	public static final String ICON_TEXT = "_icon_text";
	public static final String ICON_RADIO = "_icon_radio";
	public static final String ICON_CHECK = "_icon_check";
	public static final String ICON_SELECT = "_icon_select";
	public static final String ICON_TEXTAREA = "_icon_textarea";
	public static final String ICON_TABLE = "_icon_table";
	public static final String ICON_COLUMN = "_icon_column";
	public static final String ICON_LABEL = "_icon_label";
	public static final String ICON_PASS = "_icon_pass";
	public static final String ICON_LIST = "_icon_list";
	public static final String ICON_PANEL = "_icon_panel";
	public static final String ICON_LINK = "_icon_link";
	public static final String ICON_HIDDEN = "_icon_hidden";
	public static final String ICON_OUTPUT = "_icon_output";
	public static final String ICON_CSS_RULE = "_icon_css_rule";
	public static final String ICON_CSS_PROP = "_icon_css_prop";
	public static final String ICON_PROPERTY = "_icon_property";
	public static final String ICON_FORWARD = "_icon_forward";
	public static final String ICON_BACKWARD = "_icon_backword";
	public static final String ICON_REFRESH = "_icon_refresh";
	public static final String ICON_RUN = "_icon_run";
	public static final String ICON_TAG_HTML = "_icon_html";
	public static final String ICON_TITLE = "_icon_title";
	public static final String ICON_FORM = "_icon_form";
	public static final String ICON_IMAGE = "_icon_image";
	public static final String ICON_COMMENT = "_icon_comment";
	public static final String ICON_BODY = "_icon_body";
	public static final String ICON_DOCTYPE = "_icon_doctype";
	public static final String ICON_ELEMENT = "_icon_element";
	public static final String ICON_ATTLIST = "_icon_attlist";
	public static final String ICON_NOTATE = "_icon_notate";
	public static final String ICON_ENTITY = "_icon_entity";
	public static final String ICON_FUNCTION = "_icon_function";
	public static final String ICON_VARIABLE = "_icon_variable";
	public static final String ICON_CLASS = "_icon_class";
	public static final String ICON_TEMPLATE = "_icon_template";
	public static final String ICON_JAVASCRIPT = "_icon_javascript";
	public static final String ICON_XSD = "_icon_xsd";
	public static final String ICON_DTD = "_icon_dtd";
	public static final String ICON_PALETTE = "_icon_palette";
	public static final String ICON_ERROR = "_icon_error";
	public static final String ICON_JAR = "_icon_jar";
	public static final String ICON_JAR_EXT = "_icon_jar_ext";
	public static final String ICON_INTERFACE = "_icon_interface";
	public static final String ICON_PACKAGE = "_icon_package";
	public static final String ICON_SORT = "_icon_sort";
	public static final String ICON_ANNOTATION = "_icon_annotation";

	public static final String PREF_COLOR_TAG = "_pref_color_tag";
	public static final String PREF_COLOR_ATTR = "_pref_color_attr";
	public static final String PREF_COLOR_VALUE = "_pref_color_value";
	public static final String PREF_COLOR_COMMENT = "_pref_color_comment";
	public static final String PREF_COLOR_STRING = "_pref_color_string";
	public static final String PREF_COLOR_DOCTYPE = "_pref_color_doctype";
	public static final String PREF_COLOR_SCRIPT = "_pref_color_scriptlet";
	public static final String PREF_COLOR_CSSPROP = "_pref_color_cssprop";
	public static final String PREF_COLOR_CSSCOMMENT = "_pref_color_csscomment";
	public static final String PREF_COLOR_CSSVALUE = "_pref_color_cssvalue";
	public static final String PREF_EDITOR_TYPE = "_pref_editor_type";
	public static final String PREF_DTD_URI = "_pref_dtd_uri";
	public static final String PREF_DTD_PATH = "_pref_dtd_path";
	public static final String PREF_DTD_CACHE = "_pref_dtd_cache";
	public static final String PREF_ASSIST_AUTO = "_pref_assist_auto";
	public static final String PREF_ASSIST_CHARS = "_pref_assist_chars";
	public static final String PREF_ASSIST_TIMES = "_pref_assist_times";
	public static final String PREF_ASSIST_CLOSE = "_pref_assist_close";
	public static final String PREF_PALETTE_ITEMS = "_pref_palette_items";
	public static final String PREF_USE_SOFTTAB = "_pref_use_softtab";
	public static final String PREF_SOFTTAB_WIDTH = "_pref_softtab_width";
	public static final String PREF_COLOR_BG = "AbstractTextEditor.Color.Background";
	public static final String PREF_COLOR_BG_DEF = "AbstractTextEditor.Color.Background.SystemDefault";
	public static final String PREF_COLOR_FG = "__pref_color_foreground";
	public static final String PREF_TLD_URI = "__pref_tld_uri";
	public static final String PREF_TLD_PATH = "__pref_tld_path";
	public static final String PREF_JSP_COMMENT = "__pref_jsp_comment";
	public static final String PREF_JSP_KEYWORD = "__pref_jsp_keyword";
	public static final String PREF_JSP_STRING = "__pref_jsp_string";
	public static final String PREF_JSP_FIX_PATH = "__pref_jsp_fix_path";
	public static final String PREF_PAIR_CHAR = "__pref_pair_character";
	public static final String PREF_SHOW_XML_ERRORS = "__pref_show_xml_errors";
	public static final String PREF_COLOR_JSSTRING = "__pref_color_jsstring";
	public static final String PREF_COLOR_JSKEYWORD = "__pref_color_jskeyword";
	public static final String PREF_COLOR_JSCOMMENT = "__pref_color_jscomment";
	public static final String PREF_COLOR_JSDOC = "__pref_color_jsdoc";
	public static final String PREF_CUSTOM_ATTRS = "__pref_custom_attributes";
	public static final String PREF_CUSTOM_ELEMENTS = "__pref_custom_elements";
	public static final String PREF_TASK_TAGS = "__pref_task_tags";
	public static final String PREF_ENABLE_CLASSNAME = "__pref_enable_classname";
	public static final String PREF_CLASSNAME_ATTRS = "__pref_classname_attrs";
	public static final String PREF_SCHEMA_MAPPINGS = "__pref_schema_mappings";
	public static final String PREF_AUTO_EDIT = "__pref_auto_edit";
	public static final String PREF_COLOR_TAGLIB = "_pref_color_taglib";
	public static final String PREF_COLOR_TAGLIB_ATTR = "_pref_color_taglib_attr";
	public static final String PREF_FORMATTER_INDENT = "_pref_formatter_indent";
	public static final String PREF_FORMATTER_TAB = "_pref_formatter_tab";
	public static final String PREF_FORMATTER_LINE = "_pref_formatter_line";

	public static final String GROUP_SOURCE = "_menu_source";

	public static final String[] SUPPORTED_IMAGE_TYPES = { "gif", "png", "jpg", "jpeg", "bmp" };

	/**
	 * This method is called upon plug-in activation
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		colorProvider = new ColorProvider(getPreferenceStore());

//		// activate org.eclipse.update.core plugin, and enable proxy settings
//		UpdateCore.getPlugin();
//		UpdateUI.getDefault();
	}

	protected void initializeImageRegistry(ImageRegistry reg) {
		super.initializeImageRegistry(reg);
		reg.put(ICON_HTML, ImageDescriptor.createFromURL(getBundle().getEntry("/icons/html_file.gif")));
		reg.put(ICON_XML, ImageDescriptor.createFromURL(getBundle().getEntry("/icons/xml.gif")));
		reg.put(ICON_JSP, ImageDescriptor.createFromURL(getBundle().getEntry("/icons/jsp_file.gif")));
		reg.put(ICON_CSS, ImageDescriptor.createFromURL(getBundle().getEntry("/icons/css.gif")));
		reg.put(ICON_WEB, ImageDescriptor.createFromURL(getBundle().getEntry("/icons/web.gif")));
		reg.put(ICON_FILE, ImageDescriptor.createFromURL(getBundle().getEntry("/icons/file.gif")));
		reg.put(ICON_TAG, ImageDescriptor.createFromURL(getBundle().getEntry("/icons/tag.gif")));
		reg.put(ICON_ATTR, ImageDescriptor.createFromURL(getBundle().getEntry("/icons/attribute.gif")));
		reg.put(ICON_VALUE, ImageDescriptor.createFromURL(getBundle().getEntry("/icons/value.gif")));
		reg.put(ICON_FOLDER, ImageDescriptor.createFromURL(getBundle().getEntry("/icons/folder.gif")));
		reg.put(ICON_BUTTON, ImageDescriptor.createFromURL(getBundle().getEntry("/icons/button.gif")));
		reg.put(ICON_TEXT, ImageDescriptor.createFromURL(getBundle().getEntry("/icons/text.gif")));
		reg.put(ICON_RADIO, ImageDescriptor.createFromURL(getBundle().getEntry("/icons/radio.gif")));
		reg.put(ICON_CHECK, ImageDescriptor.createFromURL(getBundle().getEntry("/icons/checkbox.gif")));
		reg.put(ICON_SELECT, ImageDescriptor.createFromURL(getBundle().getEntry("/icons/select.gif")));
		reg.put(ICON_TEXTAREA, ImageDescriptor.createFromURL(getBundle().getEntry("/icons/textarea.gif")));
		reg.put(ICON_TABLE, ImageDescriptor.createFromURL(getBundle().getEntry("/icons/table.gif")));
		reg.put(ICON_COLUMN, ImageDescriptor.createFromURL(getBundle().getEntry("/icons/column.gif")));
		reg.put(ICON_LABEL, ImageDescriptor.createFromURL(getBundle().getEntry("/icons/label.gif")));
		reg.put(ICON_PASS, ImageDescriptor.createFromURL(getBundle().getEntry("/icons/password.gif")));
		reg.put(ICON_LIST, ImageDescriptor.createFromURL(getBundle().getEntry("/icons/list.gif")));
		reg.put(ICON_PANEL, ImageDescriptor.createFromURL(getBundle().getEntry("/icons/panel.gif")));
		reg.put(ICON_LINK, ImageDescriptor.createFromURL(getBundle().getEntry("/icons/link.gif")));
		reg.put(ICON_HIDDEN, ImageDescriptor.createFromURL(getBundle().getEntry("/icons/hidden.gif")));
		reg.put(ICON_OUTPUT, ImageDescriptor.createFromURL(getBundle().getEntry("/icons/output.gif")));
		reg.put(ICON_CSS_RULE, ImageDescriptor.createFromURL(getBundle().getEntry("/icons/css_rule.gif")));
		reg.put(ICON_CSS_PROP, ImageDescriptor.createFromURL(getBundle().getEntry("/icons/css_prop.gif")));
		reg.put(ICON_PROPERTY, ImageDescriptor.createFromURL(getBundle().getEntry("/icons/properties.gif")));
		reg.put(ICON_FORWARD, ImageDescriptor.createFromURL(getBundle().getEntry("/icons/forward.gif")));
		reg.put(ICON_BACKWARD, ImageDescriptor.createFromURL(getBundle().getEntry("/icons/backward.gif")));
		reg.put(ICON_REFRESH, ImageDescriptor.createFromURL(getBundle().getEntry("/icons/refresh.gif")));
		reg.put(ICON_RUN, ImageDescriptor.createFromURL(getBundle().getEntry("/icons/run.gif")));
		reg.put(ICON_BODY, ImageDescriptor.createFromURL(getBundle().getEntry("/icons/body.gif")));
		reg.put(ICON_FORM, ImageDescriptor.createFromURL(getBundle().getEntry("/icons/form.gif")));
		reg.put(ICON_TAG_HTML, ImageDescriptor.createFromURL(getBundle().getEntry("/icons/html.gif")));
		reg.put(ICON_IMAGE, ImageDescriptor.createFromURL(getBundle().getEntry("/icons/image.gif")));
		reg.put(ICON_TITLE, ImageDescriptor.createFromURL(getBundle().getEntry("/icons/title.gif")));
		reg.put(ICON_COMMENT, ImageDescriptor.createFromURL(getBundle().getEntry("/icons/comment.gif")));
		reg.put(ICON_DOCTYPE, ImageDescriptor.createFromURL(getBundle().getEntry("/icons/doctype.gif")));
		reg.put(ICON_ENTITY, ImageDescriptor.createFromURL(getBundle().getEntry("/icons/entity.gif")));
		reg.put(ICON_ATTLIST, ImageDescriptor.createFromURL(getBundle().getEntry("/icons/attlist.gif")));
		reg.put(ICON_ELEMENT, ImageDescriptor.createFromURL(getBundle().getEntry("/icons/element.gif")));
		reg.put(ICON_NOTATE, ImageDescriptor.createFromURL(getBundle().getEntry("/icons/notation.gif")));
		reg.put(ICON_FUNCTION, ImageDescriptor.createFromURL(getBundle().getEntry("/icons/function.gif")));
		reg.put(ICON_VARIABLE, ImageDescriptor.createFromURL(getBundle().getEntry("/icons/var.gif")));
		reg.put(ICON_CLASS, ImageDescriptor.createFromURL(getBundle().getEntry("/icons/class.gif")));
		reg.put(ICON_TEMPLATE, ImageDescriptor.createFromURL(getBundle().getEntry("/icons/template.gif")));
		reg.put(ICON_JAVASCRIPT, ImageDescriptor.createFromURL(getBundle().getEntry("/icons/js.gif")));
		reg.put(ICON_XSD, ImageDescriptor.createFromURL(getBundle().getEntry("/icons/xsd.gif")));
		reg.put(ICON_DTD, ImageDescriptor.createFromURL(getBundle().getEntry("/icons/dtd.gif")));
		reg.put(ICON_PALETTE, ImageDescriptor.createFromURL(getBundle().getEntry("/icons/palette.gif")));
		reg.put(ICON_ERROR, ImageDescriptor.createFromURL(getBundle().getEntry("/icons/error.gif")));
		reg.put(ICON_JAR, ImageDescriptor.createFromURL(getBundle().getEntry("/icons/jar.gif")));
		reg.put(ICON_JAR_EXT, ImageDescriptor.createFromURL(getBundle().getEntry("/icons/jar_ext.gif")));
		reg.put(ICON_INTERFACE, ImageDescriptor.createFromURL(getBundle().getEntry("/icons/interface.gif")));
		reg.put(ICON_PACKAGE, ImageDescriptor.createFromURL(getBundle().getEntry("/icons/package.gif")));
		reg.put(ICON_SORT, ImageDescriptor.createFromURL(getBundle().getEntry("/icons/sort.gif")));
		reg.put(ICON_ANNOTATION, ImageDescriptor.createFromURL(getBundle().getEntry("/icons/annotation.gif")));
	}

	/**
	 * This method is called when the plug-in is stopped
	 */
	public void stop(BundleContext context) throws Exception {
		JavaScriptLaunchUtil.removeLibraries();
		ClosureCompilerLaunchUtil.removeLibraries();
		colorProvider.dispose();
		super.stop(context);
	}

	/**
	 * The constructor.
	 */
	public WebToolsPlugin() {
		super();
		plugin = this;
		try {
			resourceBundle = ResourceBundle.getBundle("com.tulin.v8.webtools.PluginResources.properties");
		} catch (MissingResourceException x) {
			resourceBundle = null;
		}
	}

	/**
	 * Returns the shared instance.
	 */
	public static WebToolsPlugin getDefault() {
		return plugin;
	}

	public static String getPluginId() {
		return getDefault().getBundle().getSymbolicName();
	}

	public Image getImage(String path) {
		Image image = getImageRegistry().get(path);
		if (image == null) {
			ImageDescriptor descriptor = getImageDescriptor(path);
			if (descriptor != null) {
				getImageRegistry().put(path, image = descriptor.createImage());
			}
		}
		return image;
	}

	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(getPluginId(), path);
	}

	public static Image getIcon(String imgname) {
		return getDefault().getImage("/icons/" + imgname);
	}

	public ColorProvider getColorProvider() {
		return this.colorProvider;
	}

	/**
	 * Returns the string from the plugin's resource bundle, or 'key' if not found.
	 */
	public static String getResourceString(String key) {
		ResourceBundle bundle = getDefault().getResourceBundle();
		try {
			return (bundle != null) ? bundle.getString(key) : key;
		} catch (MissingResourceException e) {
			return key;
		}
	}

	/**
	 * Returns the plugin's resource bundle,
	 */
	public ResourceBundle getResourceBundle() {
		return resourceBundle;
	}

	/**
	 * Open the alert dialog.
	 * 
	 * @param message message
	 */
	public static void openAlertDialog(String message) {
		MessageBox box = new MessageBox(Display.getCurrent().getActiveShell(), SWT.NULL | SWT.ICON_ERROR);
		box.setMessage(message);
		box.setText(getResourceString("ErrorDialog.Caption"));
		box.open();
	}

	/**
	 * Generates a message from a template and parameters. Replace template {0}{1}..
	 * with parameters
	 *
	 * @param message message
	 * @param params  parameterd
	 * @return generated message
	 */
	public static String createMessage(String message, String[] params) {
		for (int i = 0; i < params.length; i++) {
			message = message.replaceAll("\\{" + i + "\\}", params[i]);
		}
		return message;
	}

	/**
	 * Logging debug information.
	 *
	 * @param message message
	 */
	public static void logDebug(String message) {
		ILog log = getDefault().getLog();
		IStatus status = new Status(IStatus.INFO, getPluginId(), 0, message, null);
		log.log(status);
	}

	/**
	 * Logging error information.
	 *
	 * @param message message
	 */
	public static void logError(String message) {
		ILog log = getDefault().getLog();
		IStatus status = new Status(IStatus.ERROR, getPluginId(), 0, message, null);
		log.log(status);
	}

	public static void logError(Exception e) {
		ILog log = getDefault().getLog();
		IStatus status = new Status(IStatus.ERROR, getPluginId(), 0, e.getMessage(), null);
		log.log(status);
	}

	public static void logError(Throwable e) {
		ILog log = getDefault().getLog();
		IStatus status = new Status(IStatus.ERROR, getPluginId(), 0, e.getMessage(), null);
		log.log(status);
	}

	/**
	 * Logging exception information.
	 *
	 * @param ex exception
	 */
	public static void logException(Throwable ex) {
		ILog log = getDefault().getLog();
		IStatus status = null;
		if (ex instanceof CoreException) {
			status = ((CoreException) ex).getStatus();
		} else {
			status = new Status(IStatus.ERROR, getPluginId(), 0, ex.toString(), ex);
		}
		log.log(status);

		// TODO debug
		ex.printStackTrace();
	}
}
