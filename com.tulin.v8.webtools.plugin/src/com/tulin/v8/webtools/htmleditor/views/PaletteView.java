package com.tulin.v8.webtools.htmleditor.views;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.eclipse.gef.Tool;
import org.eclipse.gef.internal.ui.palette.editparts.ToolEntryEditPart;
import org.eclipse.gef.palette.PaletteDrawer;
import org.eclipse.gef.palette.PaletteEntry;
import org.eclipse.gef.palette.PaletteListener;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.palette.ToolEntry;
import org.eclipse.gef.ui.palette.PaletteViewer;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

import com.tulin.v8.webtools.htmleditor.HTMLPlugin;
import com.tulin.v8.webtools.htmleditor.HTMLUtil;
import com.tulin.v8.webtools.htmleditor.assist.AttributeInfo;
import com.tulin.v8.webtools.htmleditor.assist.TagInfo;
import com.tulin.v8.webtools.htmleditor.editors.HTMLSourceEditor;
import com.tulin.v8.webtools.jspeditor.editors.TLDParser;

import jp.aonir.fuzzyxml.FuzzyXMLDocument;
import jp.aonir.fuzzyxml.FuzzyXMLElement;
import jp.aonir.fuzzyxml.FuzzyXMLNode;
import jp.aonir.fuzzyxml.FuzzyXMLParser;

/**
 * PaletteView.
 * <p>
 * When HTMLSourceEditor or IPaletteTarget actives, inserts a tag that is
 * selected in the palette to the calet position.
 * </p>
 */
public class PaletteView extends ViewPart {

	private PaletteViewer viewer;
	private TreeMap<String, List<IPaletteItem>> items = new TreeMap<String, List<IPaletteItem>>();
	private HashMap<HTMLPaletteEntry, IPaletteItem> tools = new HashMap<HTMLPaletteEntry, IPaletteItem>();
	private String[] defaultCategories;

	/**
	 * Constructor
	 */
	public PaletteView() {
		addPaletteItem("HTML",
				new DefaultPaletteItem("form",
						HTMLPlugin.getDefault().getImageRegistry().getDescriptor(HTMLPlugin.ICON_FORM),
						"<form action=\"\" method=\"\"></form>"));
		addPaletteItem("HTML",
				new DefaultPaletteItem("text",
						HTMLPlugin.getDefault().getImageRegistry().getDescriptor(HTMLPlugin.ICON_TEXT),
						"<input type=\"text\" name=\"\" value=\"\" />"));
		addPaletteItem("HTML",
				new DefaultPaletteItem("textarea",
						HTMLPlugin.getDefault().getImageRegistry().getDescriptor(HTMLPlugin.ICON_TEXTAREA),
						"<textarea name=\"\" rows=\"\" cols=\"\"></textarea>"));
		addPaletteItem("HTML",
				new DefaultPaletteItem("password",
						HTMLPlugin.getDefault().getImageRegistry().getDescriptor(HTMLPlugin.ICON_PASS),
						"<input type=\"password\" name=\"\" value=\"\" />"));
		addPaletteItem("HTML",
				new DefaultPaletteItem("radio",
						HTMLPlugin.getDefault().getImageRegistry().getDescriptor(HTMLPlugin.ICON_RADIO),
						"<input type=\"radio\" name=\"\" value=\"\" />"));
		addPaletteItem("HTML",
				new DefaultPaletteItem("checkbox",
						HTMLPlugin.getDefault().getImageRegistry().getDescriptor(HTMLPlugin.ICON_CHECK),
						"<input type=\"checkbox\" name=\"\" value=\"\" />"));
		addPaletteItem("HTML",
				new DefaultPaletteItem("button",
						HTMLPlugin.getDefault().getImageRegistry().getDescriptor(HTMLPlugin.ICON_BUTTON),
						"<input type=\"button\" name=\"\" value=\"\" />"));
		addPaletteItem("HTML",
				new DefaultPaletteItem("submit",
						HTMLPlugin.getDefault().getImageRegistry().getDescriptor(HTMLPlugin.ICON_BUTTON),
						"<input type=\"submit\" name=\"\" value=\"\" />"));
		addPaletteItem("HTML",
				new DefaultPaletteItem("reset",
						HTMLPlugin.getDefault().getImageRegistry().getDescriptor(HTMLPlugin.ICON_BUTTON),
						"<input type=\"reset\" value=\"\" />"));

		addPaletteItem("JSP",
				new DefaultPaletteItem("jsp:useBean",
						HTMLPlugin.getDefault().getImageRegistry().getDescriptor(HTMLPlugin.ICON_TAG),
						"<jsp:useBean id=\"\" class=\"\" scope=\"\" />"));
		addPaletteItem("JSP", new DefaultPaletteItem("jsp:include",
				HTMLPlugin.getDefault().getImageRegistry().getDescriptor(HTMLPlugin.ICON_TAG), "<jsp:include />"));
		addPaletteItem("JSP", new DefaultPaletteItem("jsp:forward",
				HTMLPlugin.getDefault().getImageRegistry().getDescriptor(HTMLPlugin.ICON_TAG), "<jsp:forward />"));

		// add items contributed from other plugins
		String[] groups = HTMLPlugin.getDefault().getPaletteContributerGroups();
		for (int i = 0; i < groups.length; i++) {
			IPaletteContributer contributer = HTMLPlugin.getDefault().getPaletteContributer(groups[i]);
			IPaletteItem[] items = contributer.getPaletteItems();
			for (int j = 0; j < items.length; j++) {
				addPaletteItem(groups[i], items[j]);
			}
		}

		// save default categories
		defaultCategories = getCategories();
	}

	private void createToolBar() {
		Action customize = new Action("Configuration",
				HTMLPlugin.getDefault().getImageRegistry().getDescriptor(HTMLPlugin.ICON_PROPERTY)) {
			public void run() {
				PaletteCustomizeDialog dialog = new PaletteCustomizeDialog(getViewSite().getShell());
				dialog.open();
			}
		};
		customize.setToolTipText("Configuration");

		IToolBarManager mgr = getViewSite().getActionBars().getToolBarManager();
		mgr.add(customize);
	}

	/**
	 * create controls and apply configurations.
	 */
	public void createPartControl(Composite parent) {
		viewer = new PaletteViewer();
		viewer.createControl(parent);

		PaletteRoot root = new PaletteRoot();

		String[] category = getCategories();
		for (int i = 0; i < category.length; i++) {
			PaletteDrawer group = new PaletteDrawer(category[i]);
			IPaletteItem[] items = getPaletteItems(category[i]);
			for (int j = 0; j < items.length; j++) {
				HTMLPaletteEntry entry = new HTMLPaletteEntry(items[j].getLabel(), null, items[j].getImageDescriptor());
				tools.put(entry, items[j]);
				group.add(entry);
			}
			root.add(group);
		}

		viewer.setPaletteRoot(root);

		viewer.addPaletteListener(new PaletteListener() {
			public void activeToolChanged(PaletteViewer palette, ToolEntry tool) {
				Object obj = palette.getEditPartRegistry().get(tool);
				if (!(obj instanceof ToolEntryEditPart)) {
					return;
				}
				ToolEntryEditPart part = (ToolEntryEditPart) obj;
				if (part != null) {
					// get the active editor
					IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
					IEditorPart editorPart = page.getActiveEditor();
					// execute processing of the palette item
					if (editorPart != null) {
						if (editorPart instanceof HTMLSourceEditor) {
							IPaletteItem item = (IPaletteItem) tools.get(tool);
							item.execute((HTMLSourceEditor) editorPart);
						} else if (editorPart instanceof IPaletteTarget) {
							IPaletteItem item = (IPaletteItem) tools.get(tool);
							item.execute(((IPaletteTarget) editorPart).getPaletteTarget());
						}
					}
					// unset palette selection
					part.setToolSelected(false);
				}
			}
		});
		viewer.getControl().addMouseListener(new MouseAdapter() {
			public void mouseUp(MouseEvent e) {
				// set focus to the active editor
				IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
				IEditorPart editorPart = page.getActiveEditor();
				if (editorPart != null) {
					editorPart.setFocus();
				}
			}
		});

		// apply configuration (too long!!)
		IPreferenceStore store = HTMLPlugin.getDefault().getPreferenceStore();
		String xml = store.getString(HTMLPlugin.PREF_PALETTE_ITEMS);
		if (xml != null) {
			FuzzyXMLDocument doc = new FuzzyXMLParser().parse(xml);
			// apply visible
			FuzzyXMLNode[] groups = HTMLUtil.selectXPathNodes(doc.getDocumentElement(), "/palette/groups/group");
			for (int i = 0; i < groups.length; i++) {
				FuzzyXMLElement group = (FuzzyXMLElement) groups[i];

				String name = group.getAttributeNode("name").getValue();
				boolean visible = Boolean.valueOf(group.getAttributeNode("visible").getValue());

				@SuppressWarnings("rawtypes")
				List entries = viewer.getPaletteRoot().getChildren();
				PaletteDrawer drawer = null;

				for (int j = 0; j < entries.size(); j++) {
					drawer = (PaletteDrawer) entries.get(j);
					if (drawer.getLabel().equals(name)) {
						drawer.setVisible(visible);
						break;
					} else {
						drawer = null;
					}
				}
				if (drawer == null) {
					drawer = new PaletteDrawer(name);
					drawer.setVisible(visible);
					viewer.getPaletteRoot().add(drawer);
				}
			}
			// add user items
			FuzzyXMLNode[] items = HTMLUtil.selectXPathNodes(doc.getDocumentElement(), "/palette/items/item");
			String[] categories = getCategories();
			for (int i = 0; i < items.length; i++) {
				FuzzyXMLElement item = (FuzzyXMLElement) items[i];
				String name = item.getAttributeNode("name").getValue();
				String group = item.getAttributeNode("group").getValue();
				String text = item.getValue();
				if (Arrays.binarySearch(categories, group) < 0) {
					addPaletteItem(group, new DefaultPaletteItem(name,
							HTMLPlugin.getDefault().getImageRegistry().getDescriptor(HTMLPlugin.ICON_TAG), text));
				}
			}
		}

		@SuppressWarnings("rawtypes")
		List entries = viewer.getPaletteRoot().getChildren();
		for (int i = 0; i < entries.size(); i++) {
			PaletteDrawer group = (PaletteDrawer) entries.get(i);
			if (Arrays.binarySearch(defaultCategories, group.getLabel()) < 0) {
				IPaletteItem[] items = getPaletteItems(group.getLabel());
				List<HTMLPaletteEntry> itemList = new ArrayList<HTMLPaletteEntry>();
				for (int j = 0; j < items.length; j++) {
					HTMLPaletteEntry entry = new HTMLPaletteEntry(items[j].getLabel(), null,
							items[j].getImageDescriptor());
					tools.put(entry, items[j]);
					itemList.add(entry);
				}
				group.setChildren(itemList);
			}
		}

		// create toolbar
		createToolBar();
	}

	/**
	 * Adds PaletteItem to the specified category.
	 * 
	 * @param category the category
	 * @param item     the item
	 */
	private void addPaletteItem(String category, IPaletteItem item) {
		if (items.get(category) == null) {
			List<IPaletteItem> list = new ArrayList<IPaletteItem>();
			items.put(category, list);
		}
		List<IPaletteItem> list = items.get(category);
		list.add(item);
	}

	/**
	 * Update the category information.
	 * <p>
	 * If the category already exists, overwrites the category infomation.
	 * Otherwise, creates the new category and appends it to the palette.
	 * 
	 * @param category the category
	 * @param items    the map contains items
	 */
	private void updateCategory(String category, List<Map<String, String>> items) {

		viewer.setActiveTool(null);

		// remove all items
		List<IPaletteItem> list = this.items.get(category);
		if (list != null) {
			list.clear();
		}

		@SuppressWarnings("rawtypes")
		List entries = viewer.getPaletteRoot().getChildren();
		PaletteDrawer group = null;

		for (int i = 0; i < entries.size(); i++) {
			group = (PaletteDrawer) entries.get(i);
			if (group.getLabel().equals(category)) {
				break;
			} else {
				group = null;
			}
		}

		if (group == null) {
			group = new PaletteDrawer(category);
			viewer.getPaletteRoot().add(group);
		}

		// add items
		for (int i = 0; i < items.size(); i++) {
			Map<String, String> map = items.get(i);
			addPaletteItem(category, new DefaultPaletteItem(map.get("name"),
					HTMLPlugin.getDefault().getImageRegistry().getDescriptor(HTMLPlugin.ICON_TAG), map.get("text")));
		}

		List<HTMLPaletteEntry> itemList = new ArrayList<HTMLPaletteEntry>();
		IPaletteItem[] newItems = getPaletteItems(category);
		for (int i = 0; i < newItems.length; i++) {
			HTMLPaletteEntry entry = new HTMLPaletteEntry(newItems[i].getLabel(), null,
					newItems[i].getImageDescriptor());
			tools.put(entry, newItems[i]);
			itemList.add(entry);
		}
		group.setChildren(itemList);
	}

	/**
	 * Removes the category.
	 * 
	 * @param category the category
	 */
	private void removeCategory(String category) {

		viewer.setActiveTool(null);

		this.items.remove(category);

		@SuppressWarnings("rawtypes")
		List entries = viewer.getPaletteRoot().getChildren();
		PaletteDrawer group = null;

		for (int i = 0; i < entries.size(); i++) {
			group = (PaletteDrawer) entries.get(i);
			if (group.getLabel().equals(category)) {
				@SuppressWarnings("rawtypes")
				List children = group.getChildren();
				for (int j = 0; j < children.size(); j++) {
					tools.remove((PaletteEntry) children.get(j));
					group.remove((PaletteEntry) children.get(j));
				}
				viewer.getPaletteRoot().remove(group);
				break;
			}
		}
	}

	/**
	 * Returns PaletteItems which are contained by the specified category.
	 * 
	 * @param category the category
	 * @return the array of items which are contained by the category
	 */
	private IPaletteItem[] getPaletteItems(String category) {
		List<IPaletteItem> list = items.get(category);
		if (list == null) {
			return new IPaletteItem[0];
		}
		return list.toArray(new IPaletteItem[list.size()]);
	}

	/**
	 * Returns all categories.
	 * 
	 * @return the array which contains all categories
	 */
	private String[] getCategories() {
		return items.keySet().toArray(new String[0]);
	}

	public void setFocus() {
		viewer.getControl().setFocus();
	}

	/** ToolEntry for HTML tag palette */
	private class HTMLPaletteEntry extends ToolEntry {

		public HTMLPaletteEntry(String label, String shortDescription, ImageDescriptor icon) {
			super(label, shortDescription, icon, icon);
		}

		public Tool createTool() {
			return null;
		}
	}

	/** Returns palette configuration as XML. */
	private String getPreferenceXML() {
		StringBuffer sb = new StringBuffer();
		sb.append("<palette>");

		@SuppressWarnings("rawtypes")
		List entries = viewer.getPaletteRoot().getChildren();

		sb.append("<groups>");
		for (int i = 0; i < entries.size(); i++) {
			PaletteDrawer group = (PaletteDrawer) entries.get(i);
			sb.append("<group name=\"" + HTMLUtil.escapeXML(group.getLabel()) + "\"" + " visible=\""
					+ HTMLUtil.escapeXML(String.valueOf(group.isVisible())) + "\" />");
		}
		sb.append("</groups>");
		sb.append("<items>");
		String[] categories = getCategories();
		for (int i = 0; i < categories.length; i++) {
			if (Arrays.binarySearch(defaultCategories, categories[i]) < 0) {
				IPaletteItem[] items = getPaletteItems(categories[i]);
				for (int j = 0; j < items.length; j++) {
					sb.append("<item group=\"" + HTMLUtil.escapeXML(categories[i]) + "\"" + " name=\""
							+ HTMLUtil.escapeXML(items[j].getLabel()) + "\">"
							+ HTMLUtil.escapeXML(((DefaultPaletteItem) items[j]).getContent()) + "</item>");
				}
			}
		}
		sb.append("</items>");
		sb.append("</palette>");
		return sb.toString();
	}

	/** The dialog for palette customization */
	private class PaletteCustomizeDialog extends Dialog {

		private Table table;
		private Button add;
		private Button edit;
		private Button remove;
		private Map<String, Object> operations = new HashMap<String, Object>();

		public PaletteCustomizeDialog(Shell parentShell) {
			super(parentShell);
			setShellStyle(getShellStyle() | SWT.RESIZE);
		}

		protected Point getInitialSize() {
			return new Point(300, 300);
		}

		protected Control createDialogArea(Composite parent) {
			getShell().setText(HTMLPlugin.getResourceString("Dialog.PaletteConfig"));

			Composite container = new Composite(parent, SWT.NULL);
			container.setLayout(new GridLayout(2, false));
			container.setLayoutData(new GridData(GridData.FILL_BOTH));

			table = new Table(container, SWT.BORDER | SWT.CHECK);
			table.setLayoutData(new GridData(GridData.FILL_BOTH));
			@SuppressWarnings("rawtypes")
			List entries = viewer.getPaletteRoot().getChildren();

			for (int i = 0; i < entries.size(); i++) {
				TableItem item = new TableItem(table, SWT.LEFT);
				item.setText(((PaletteDrawer) entries.get(i)).getLabel());
				item.setChecked(((PaletteDrawer) entries.get(i)).isVisible());
			}

			table.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent evt) {
					// Can't modify default categories
					TableItem[] items = table.getSelection();
					if (items.length == 0 || Arrays.binarySearch(defaultCategories, items[0].getText()) >= 0) {
						edit.setEnabled(false);
						remove.setEnabled(false);
					} else {
						edit.setEnabled(true);
						remove.setEnabled(true);
					}
				}
			});

			Composite buttons = new Composite(container, SWT.NULL);
			buttons.setLayout(new GridLayout());
			buttons.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));

			add = new Button(buttons, SWT.PUSH);
			add.setText(HTMLPlugin.getResourceString("Button.AddGroup"));
			add.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			add.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent evt) {
					PaletteGroupDialog dialog = new PaletteGroupDialog(getShell());
					if (dialog.open() == Dialog.OK) {
						TableItem item = new TableItem(table, SWT.NULL);
						item.setText(dialog.getGroupName());
						item.setChecked(true);
						operations.put(dialog.getGroupName(), dialog.getPaletteItems());
					}
				}
			});

			edit = new Button(buttons, SWT.PUSH);
			edit.setText(HTMLPlugin.getResourceString("Button.EditGroup"));
			edit.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			edit.setEnabled(false);
			edit.addSelectionListener(new SelectionAdapter() {
				@SuppressWarnings("unchecked")
				public void widgetSelected(SelectionEvent evt) {
					TableItem[] items = table.getSelection();

					List<Map<String, String>> initItems = new ArrayList<Map<String, String>>();
					Object obj = operations.get(items[0].getText());
					if (obj != null && obj instanceof List) {
						initItems = (List<Map<String, String>>) obj;
					} else {
						IPaletteItem[] paletteItems = getPaletteItems(items[0].getText());
						for (int i = 0; i < paletteItems.length; i++) {
							Map<String, String> map = new HashMap<String, String>();
							map.put("name", paletteItems[i].getLabel());
							map.put("text", ((DefaultPaletteItem) paletteItems[i]).getContent());
							initItems.add(map);
						}
					}
					PaletteGroupDialog dialog = new PaletteGroupDialog(getShell(), items[0].getText(), initItems);
					if (dialog.open() == Dialog.OK) {
						items[0].setText(dialog.getGroupName());
						operations.put(items[0].getText(), dialog.getPaletteItems());
					}
				}
			});

			remove = new Button(buttons, SWT.PUSH);
			remove.setText(HTMLPlugin.getResourceString("Button.RemoveGroup"));
			remove.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			remove.setEnabled(false);
			remove.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent evt) {
					TableItem[] items = table.getSelection();
					operations.put(items[0].getText(), "remove");
					table.remove(table.getSelectionIndex());
				}
			});

			return container;
		}

		@SuppressWarnings({ "rawtypes", "unchecked" })
		protected void okPressed() {
			Iterator<String> ite = operations.keySet().iterator();
			while (ite.hasNext()) {
				String key = ite.next();
				Object obj = operations.get(key);
				if (obj.equals("remove")) {
					removeCategory(key);
				} else {
					updateCategory(key, (List) obj);
				}
			}
//			String[] groups = getCategories();
			List entries = viewer.getPaletteRoot().getChildren();
			for (int i = 0; i < entries.size(); i++) {
				((PaletteDrawer) entries.get(i)).setVisible(table.getItem(i).getChecked());
			}
			IPreferenceStore store = HTMLPlugin.getDefault().getPreferenceStore();
			store.setValue(HTMLPlugin.PREF_PALETTE_ITEMS, getPreferenceXML());

			super.okPressed();
		}
	}

	/** The dialog to edit a palette group */
	private class PaletteGroupDialog extends Dialog {

		private Text name;
		private Table table;
		private Button add;
		private Button addFromTLD;
		private Button edit;
		private Button remove;

		private String initialName = null;
		private List<Map<String, String>> initialItems = null;
		private String inputedName = null;
		private List<Map<String, String>> inputedItems = null;

		public PaletteGroupDialog(Shell parentShell) {
			this(parentShell, null, new ArrayList<Map<String, String>>());
		}

		public PaletteGroupDialog(Shell parentShell, String name, List<Map<String, String>> items) {
			super(parentShell);
			setShellStyle(getShellStyle() | SWT.RESIZE);
			initialName = name;
			initialItems = items;
		}

		protected Point getInitialSize() {
			return new Point(450, 350);
		}

		protected Control createDialogArea(Composite parent) {
			if (initialName == null) {
				getShell().setText(HTMLPlugin.getResourceString("Dialog.AddPaletteGroup"));
			} else {
				getShell().setText(HTMLPlugin.getResourceString("Dialog.EditPaletteGroup"));
			}

			Composite container = new Composite(parent, SWT.NULL);
			container.setLayout(new GridLayout(3, false));
			container.setLayoutData(new GridData(GridData.FILL_BOTH));

			Label label = new Label(container, SWT.NULL);
			label.setText(HTMLPlugin.getResourceString("Label.GroupName"));

			name = new Text(container, SWT.BORDER);
			if (initialName != null) {
				name.setText(initialName);
				name.setEditable(false);
			}
//			GridData gd = new GridData(GridData.FILL_HORIZONTAL);
			name.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

			// fill GridLayout
			label = new Label(container, SWT.NULL);

			label = new Label(container, SWT.NULL);
			label.setText(HTMLPlugin.getResourceString("Label.Items"));

			table = new Table(container, SWT.BORDER | SWT.FULL_SELECTION);
			table.setLayoutData(new GridData(GridData.FILL_BOTH));
			table.setHeaderVisible(true);
			table.setLinesVisible(true);
			table.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent evt) {
					TableItem[] items = table.getSelection();
					if (items.length == 0) {
						edit.setEnabled(false);
						remove.setEnabled(false);
					} else {
						edit.setEnabled(true);
						remove.setEnabled(true);
					}
				}
			});

			TableColumn col1 = new TableColumn(table, SWT.LEFT);
			col1.setText(HTMLPlugin.getResourceString("Message.ItemName"));
			col1.setWidth(100);

			TableColumn col2 = new TableColumn(table, SWT.LEFT);
			col2.setText(HTMLPlugin.getResourceString("Message.InsertText"));
			col2.setWidth(250);

			if (initialName != null) {
				for (int i = 0; i < initialItems.size(); i++) {
					Map<String, String> map = initialItems.get(i);
					TableItem item = new TableItem(table, SWT.NULL);
					item.setText(new String[] { map.get("name"), map.get("text") });
				}
			}

			Composite buttons = new Composite(container, SWT.NULL);
			buttons.setLayout(new GridLayout());
			buttons.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));

			add = new Button(buttons, SWT.PUSH);
			add.setText(HTMLPlugin.getResourceString("Button.AddItem"));
			add.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			add.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent evt) {
					PaletteItemDialog dialog = new PaletteItemDialog(getShell());
					if (dialog.open() == Dialog.OK) {
						String name = dialog.getItemName();
						String text = dialog.getInsertText();
						TableItem item = new TableItem(table, SWT.NULL);
						item.setText(new String[] { name, text });
					}
				}
			});

			addFromTLD = new Button(buttons, SWT.PUSH);
			addFromTLD.setText(HTMLPlugin.getResourceString("Button.AddFromTLD"));
			addFromTLD.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			addFromTLD.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent evt) {
					FileDialog openDialog = new FileDialog(getShell(), SWT.OPEN);
					openDialog.setFilterExtensions(new String[] { "*.tld" });
					String openFile = openDialog.open();
					if (openFile != null) {
						try {
							TLDParser parser = new TLDParser(null);
							parser.parse(new FileInputStream(new File(openFile)));
							List<TagInfo> tagInfoList = parser.getTags();
							for (int i = 0; i < tagInfoList.size(); i++) {
								TagInfo info = tagInfoList.get(i);
								TableItem item = new TableItem(table, SWT.NULL);
								StringBuffer sb = new StringBuffer();
								sb.append("<").append(info.getTagName());
								AttributeInfo[] attrs = info.getRequiredAttributeInfo();
								for (int j = 0; j < attrs.length; j++) {
									sb.append(" ").append(attrs[j].getAttributeName()).append("=\"\"");
								}
								if (info.hasBody()) {
									sb.append("></").append(info.getTagName()).append(">");
								} else {
									sb.append("/>");
								}
								item.setText(new String[] { info.getTagName(), sb.toString() });
							}
						} catch (Exception ex) {
							HTMLPlugin.openAlertDialog(ex.getMessage());
						}
					}
				}
			});

			edit = new Button(buttons, SWT.PUSH);
			edit.setText(HTMLPlugin.getResourceString("Button.EditItem"));
			edit.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			edit.setEnabled(false);
			edit.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent evt) {
					TableItem[] item = table.getSelection();
					PaletteItemDialog dialog = new PaletteItemDialog(getShell(), item[0].getText(0),
							item[0].getText(1));
					if (dialog.open() == Dialog.OK) {
						String name = dialog.getItemName();
						String text = dialog.getInsertText();
						item[0].setText(new String[] { name, text });
					}
				}
			});

			remove = new Button(buttons, SWT.PUSH);
			remove.setText(HTMLPlugin.getResourceString("Button.RemoveItem"));
			remove.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			remove.setEnabled(false);
			remove.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent evt) {
					table.remove(table.getSelectionIndices());
				}
			});

			return container;
		}

		protected void okPressed() {
			if (name.getText().equals("")) {
				HTMLPlugin.openAlertDialog(HTMLPlugin.createMessage(HTMLPlugin.getResourceString("Error.Required"),
						new String[] { HTMLPlugin.getResourceString("Message.GroupName") }));
				return;
			}

			if (initialName == null) {
				String[] categories = getCategories();
				for (int i = 0; i < categories.length; i++) {
					if (categories[i].equals(name.getText())) {
						HTMLPlugin.openAlertDialog(HTMLPlugin.createMessage(
								HTMLPlugin.getResourceString("Error.AlreadyExists"), new String[] { name.getText() }));
						return;
					}
				}
			}

			inputedName = name.getText();
			inputedItems = new ArrayList<Map<String, String>>();

			TableItem[] items = table.getItems();
			for (int i = 0; i < items.length; i++) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("name", items[i].getText(0));
				map.put("text", items[i].getText(1));
				inputedItems.add(map);
			}

			super.okPressed();
		}

		public String getGroupName() {
			return inputedName;
		}

		public List<Map<String, String>> getPaletteItems() {
			return inputedItems;
		}
	}

	/** The dialog yo edit a palette item */
	private class PaletteItemDialog extends Dialog {

		private Text itemName;
		private Text insertText;
		private String inputedName;
		private String inputedText;
		private String initialName;
		private String initialText;

		public PaletteItemDialog(Shell parentShell) {
			this(parentShell, null, null);
		}

		public PaletteItemDialog(Shell parentShell, String name, String text) {
			super(parentShell);
			setShellStyle(getShellStyle() | SWT.RESIZE);
			initialName = name;
			initialText = text;
		}

		protected Point getInitialSize() {
			return new Point(400, 180);
		}

		protected Control createDialogArea(Composite parent) {
			if (initialName == null) {
				getShell().setText(HTMLPlugin.getResourceString("Dialog.AddPaletteItem"));
			} else {
				getShell().setText(HTMLPlugin.getResourceString("Dialog.EditPaletteItem"));
			}

			Composite container = new Composite(parent, SWT.NULL);
			container.setLayout(new GridLayout(2, false));
			container.setLayoutData(new GridData(GridData.FILL_BOTH));

			Label label = new Label(container, SWT.NULL);
			label.setText(HTMLPlugin.getResourceString("Label.ItemName"));

			itemName = new Text(container, SWT.BORDER);
			itemName.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			if (initialName != null) {
				itemName.setText(initialName);
			}

			label = new Label(container, SWT.NULL);
			label.setText(HTMLPlugin.getResourceString("Label.InsertText"));

			insertText = new Text(container, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
			insertText.setLayoutData(new GridData(GridData.FILL_BOTH));
			if (initialText != null) {
				insertText.setText(initialText);
			}

			return container;
		}

		protected void okPressed() {
			if (itemName.getText().equals("")) {
				HTMLPlugin.openAlertDialog(HTMLPlugin.createMessage(HTMLPlugin.getResourceString("Error.Required"),
						new String[] { HTMLPlugin.getResourceString("Message.ItemName") }));
				return;
			}
			if (insertText.getText().equals("")) {
				HTMLPlugin.openAlertDialog(HTMLPlugin.createMessage(HTMLPlugin.getResourceString("Error.Required"),
						new String[] { HTMLPlugin.getResourceString("Message.InsertText") }));
				return;
			}
			inputedName = itemName.getText();
			inputedText = insertText.getText();
			super.okPressed();
		}

		public String getItemName() {
			return inputedName;
		}

		public String getInsertText() {
			return inputedText;
		}
	}

}