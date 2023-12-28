package com.tulin.v8.webtools.jseditor.editors;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.text.DefaultInformationControl;
import org.eclipse.jface.text.IInformationControlCreator;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextHover;
import org.eclipse.jface.text.ITextHoverExtension;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.Region;
import org.eclipse.ui.internal.editors.text.EditorsPlugin;

import com.tulin.v8.webtools.htmleditor.HTMLUtil;
import com.tulin.v8.webtools.jseditor.editors.model.JavaScriptContext;
import com.tulin.v8.webtools.jseditor.editors.model.JavaScriptElement;
import com.tulin.v8.webtools.jseditor.editors.model.JavaScriptFunction;
import com.tulin.v8.webtools.jseditor.editors.model.JavaScriptModel;
import com.tulin.v8.webtools.jseditor.editors.model.JavaScriptPrototype;
import com.tulin.v8.webtools.jseditor.editors.model.JavaScriptVariable;
import com.tulin.v8.webtools.jseditor.editors.model.ModelManager;
import com.tulin.v8.webtools.jseditor.editors.model.JavaScriptFunction.JavaScriptArgument;

/**
 * 鼠标悬停 显示jsDoc
 * 
 * @author chenqian
 * @create 2021-7-25
 *
 */
public class JavaScriptHoverProvider implements ITextHover,ITextHoverExtension {
	private static final String[] DEFAULT_OBJECT_TYPES = new String[] { "Object", "String", "Number", "Array" };

	@Override
	public String getHoverInfo(ITextViewer viewer, IRegion hoverRegion) {
		StringBuffer res = new StringBuffer();
		int offset = hoverRegion.getOffset();
		String source = viewer.getDocument().get();
		String[] words = getLastWord(source, offset);
		String last = words[1];
		String word = words[2];
		String lowerWord = word.toLowerCase();
		Set<String> objTypeSet = new HashSet<String>();
		IFile iFile = HTMLUtil.getActiveFile();
		if (iFile == null)
			return null;
		if (!lowerWord.endsWith("(") && !lowerWord.endsWith(".") && !lowerWord.endsWith("=")) {
			String tsr = source.substring(offset);
			if (tsr.indexOf("\n") > 0) {
				tsr = tsr.substring(0, tsr.indexOf("\n"));
			}
			if (tsr.indexOf("=") > 0) {
				tsr = tsr.substring(0, tsr.indexOf("="));
			}
			if (tsr.indexOf("(") > 0) {
				tsr = tsr.substring(0, tsr.indexOf("("));
			}
			if (tsr.indexOf(".") > 0) {
				tsr = tsr.substring(0, tsr.indexOf("."));
			}
			lowerWord = lowerWord + tsr;
			lowerWord = lowerWord.trim();
		}
		List<String> addedStrings = new ArrayList<String>();
		if (last.endsWith(".")) {
			String objName = last.substring(0, last.length() - 1);

			if (objName.length() > 0) {
				char c = objName.charAt(0);
				if (c == '\"' || c == '\'') {
					objTypeSet.add("String");
				} else if (c >= '0' && c <= '9') {
					objTypeSet.add("Number");
				} else if (c == '/') {
					objTypeSet.add("RegExp");
				}
			}

			String targetSource;
			int pos = offset - 1;
			if (pos >= 0 && pos < source.length()) {
				char[] carray = source.toCharArray();
				carray[pos] = ' ';
				targetSource = String.valueOf(carray);
			} else {
				targetSource = source;
			}
			JavaScriptModel model = ModelManager.getInstance().getCachedModel(iFile, targetSource);
			JavaScriptContext context = model.getContextFromOffset(offset);

			JavaScriptContext clsContext = model.getObjectTypeContext(objName);
			if (clsContext != null && clsContext.getTarget() != null) {
				// static
				for (JavaScriptElement jsElement : clsContext.getElements()) {
					if (jsElement.isStatic() && isTargetElement(jsElement, lowerWord)) {
						// 属性没有jsDoc但是有指向函数时，属性、方法的jsDoc取对应函数的jsDoc 2021-7-24 陈乾
						if (jsElement.getDescription() == null && jsElement.getFunction() != null) {
							jsElement.setDescription(jsElement.getFunction().getDescription());
						}
						if (jsElement.getDescription() != null) {
							res.append(jsElement.getDescription());
						}
					}
				}
			} else {
				// instance properties and functions
				if (objTypeSet.isEmpty() && context != null) {
					JavaScriptElement jsElement = context.getElementByName(objName, false);
					if (jsElement != null) {
						for (String returnType : jsElement.getReturnTypes()) {
							if (!"*".equals(returnType)) {
								objTypeSet.add(returnType);
							}
						}
						// instance elements
						if (jsElement.getContext() != null) {
							JavaScriptElement[] jsElements = jsElement.getContext().getClassObjectElements();
							for (JavaScriptElement jsChildElement : jsElements) {
								if (isTargetElement(jsChildElement, lowerWord)) {
									// 属性没有jsDoc但是有指向函数时，属性、方法的jsDoc取对应函数的jsDoc 2021-7-24 陈乾
									if (jsChildElement.getDescription() == null
											&& jsChildElement.getFunction() != null) {
										jsChildElement.setDescription(jsChildElement.getFunction().getDescription());
									}
									//System.out.println("1:" + jsChildElement.getDescription());
									if (jsChildElement.getDescription() != null) {
//										res.append("<b>"+jsElement.getDisplayString()+"."+jsChildElement.getDisplayString()+"</b><br><hr><br>");
										res.append(jsChildElement.getDescription().replace("\n", "<br>"));
//										res.append("<br>");
//										if(jsChildElement.getFunction()!=null && jsChildElement.getFunction().getArguments().length>0) {
//											res.append("<br><b>参数</b><br>");
//											for(JavaScriptArgument a:jsChildElement.getFunction().getArguments()) {
//												res.append(a.toString()+"<br>");
//											}
//										}
//										if(jsChildElement.getFunction()!=null && jsChildElement.getFunction().getReturnTypes().length>0) {
//											res.append("<br><b>返回</b><br>");
//											for(String r:jsChildElement.getFunction().getReturnTypes()) {
//												res.append(r+"<br>");
//											}
//										}
//										if(jsChildElement.getFromSource()!=null) {
//											res.append("<br><b>起源</b><br>");
//											res.append("<a>"+jsChildElement.getFromSource()+"</a><br>");
//										}
									}
								}
							}
							if (objTypeSet.isEmpty() && jsElements.length != 0) {
								objTypeSet.add("*");
							}
						}
					}
				}

				// instance
				boolean typeFound = false;
				if (!objTypeSet.isEmpty()) {
					for (String objType : objTypeSet) {
						JavaScriptContext jsBlock = model.getObjectTypeContext(objType);
						if (jsBlock != null) {
							for (JavaScriptElement jsElement : jsBlock.getClassObjectElements()) {
								if (!jsElement.isStatic() && isTargetElement(jsElement, lowerWord)) {
									// 属性没有jsDoc但是有指向函数时，属性、方法的jsDoc取对应函数的jsDoc 2021-7-24 陈乾
									if (jsElement.getDescription() == null && jsElement.getFunction() != null) {
										jsElement.setDescription(jsElement.getFunction().getDescription());
									}
//									System.out.println("2:"+jsElement.getDescription());
//									if (jsElement.getDescription() != null) {
//										res.append(jsElement.getDescription());
//									}
								}
							}
							typeFound = true;
						}
					}
				}

				if (!typeFound) {
					// unknown type
					for (String type : DEFAULT_OBJECT_TYPES) {
						JavaScriptContext jsContext = model.getObjectTypeContext(type);
						if (jsContext != null) {
							for (JavaScriptElement jsElement : jsContext.getClassObjectElements()) {
								if (!jsElement.isStatic() && isTargetElement(jsElement, lowerWord)
										&& !(jsElement instanceof JavaScriptPrototype)) {
									// 属性没有jsDoc但是有指向函数时，属性、方法的jsDoc取对应函数的jsDoc 2021-7-24 陈乾
									if (jsElement.getDescription() == null && jsElement.getFunction() != null) {
										jsElement.setDescription(jsElement.getFunction().getDescription());
									}
									// System.out.println("3:" + jsElement.getDescription());
									if (jsElement.getDescription() != null) {
//										res.append("<b>"+jsElement.getDisplayString()+"</b><br><hr><br>");
										res.append(jsElement.getDescription().replace("\n", "<br>"));
//										res.append("<br>");
//										if(jsElement.getFunction()!=null && jsElement.getFunction().getArguments().length>0) {
//											res.append("<br><b>参数</b><br>");
//											for(JavaScriptArgument a:jsElement.getFunction().getArguments()) {
//												res.append(a.toString()+"<br>");
//											}
//										}
//										if(jsElement.getFunction()!=null && jsElement.getFunction().getReturnTypes().length>0) {
//											res.append("<br><b>返回</b><br>");
//											for(String r:jsElement.getFunction().getReturnTypes()) {
//												res.append(r+"<br>");
//											}
//										}
//										if(jsElement.getFromSource()!=null) {
//											res.append("<br><b>起源</b><br>");
//											res.append("<a>"+jsElement.getFromSource()+"</a><br>");
//										}
									}
								}
							}
						}
					}
				}
			}
		} else {
			JavaScriptModel model = ModelManager.getInstance().getCachedModel(iFile, source);
			JavaScriptContext context = model.getContextFromOffset(offset);

			if (context != null) {
				String subSource = source.substring(0, offset);

				boolean funcFlag = false;
				int count = 0;
				int paramPos = 0;
				StringBuilder funcBuf = new StringBuilder();
				boolean isCompleted = false;
				for (int i = subSource.length() - 1; i >= 0 && !isCompleted; i--) {
					char c = subSource.charAt(i);
					switch (c) {
					case ',':
						paramPos++;
						break;
					case '(':
						count--;
						if (count < 0) {
							funcFlag = true;
						}
						break;
					case ')':
						count++;
						break;
					default:
						if (funcFlag) {
							if ((c >= '0' && c <= '9') || //
									(c >= 'a' && c <= 'z') || //
									(c >= 'A' && c <= 'Z') || //
									(c == '_') || //
									(c == '$') //
							) {
								funcBuf.insert(0, c);
							} else if (funcBuf.length() != 0) {
								isCompleted = true;
							}
						}
						break;
					}
				}

				String targetFunc;
				if (funcBuf.length() == 0) {
					targetFunc = null;
				} else {
					targetFunc = funcBuf.toString();
				}

				if (targetFunc != null) {
					JavaScriptElement jsElement = context.getElementByName(targetFunc, false);
					if (jsElement != null && jsElement.getFunction() != null) {
						JavaScriptArgument[] jsArgs = jsElement.getFunction().getArguments();
						if (paramPos >= 0 && paramPos < jsArgs.length) {
							String[] types = jsArgs[paramPos].getTypes();
							for (String t : types) {
								objTypeSet.add(t);
							}
						}
					}
					objTypeSet.remove("*");
				}

				JavaScriptElement[] children = context.getVisibleElements();
				for (JavaScriptElement element : children) {
					if (element.getName().toLowerCase().equals(lowerWord)) {
						JavaScriptFunction func = element.getFunction();
						if (func != null && !func.isAnonymous() && element == func) {
							String replace = func.getReplaceString();
							if (!addedStrings.contains(func.getReplaceString())) {
								// System.out.println("4:" + func.getDescription());
								if (func.getDescription() != null) {
//									res.append("<b>"+func.getDisplayString()+"</b><br><hr><br>");
									res.append(func.getDescription().replace("\n", "<br>"));
//									res.append("<br>");
//									if(func.getArguments().length>0) {
//										res.append("<br><b>参数</b><br>");
//										for(JavaScriptArgument a:func.getArguments()) {
//											res.append(a.toString()+"<br>");
//										}
//									}
//									if(func.getReturnTypes().length>0) {
//										res.append("<br><b>返回</b><br>");
//										for(String r:func.getReturnTypes()) {
//											res.append(r+"<br>");
//										}
//									}
//									if(func.getFromSource()!=null) {
//										res.append("<br><b>起源</b><br>");
//										res.append("<a>"+func.getFromSource()+"</a><br>");
//									}
								}
								addedStrings.add(replace);
							}

						} else if (element instanceof JavaScriptVariable) {
							String replace = element.getReplaceString();
							if (!addedStrings.contains(replace)) {
								// 对象没有jsDoc但是有指向函数时，jsDoc取对应函数的jsDoc 2021-7-24 陈乾
								if (element.getDescription() == null && element.getFunction() != null) {
									element.setDescription(element.getFunction().getDescription());
								}
								// System.out.println("5:" + func.getDescription());
								if (element.getDescription() != null) {
//									res.append("<b>"+element.getDisplayString()+"</b><br><hr><br>");
									res.append(element.getDescription().replace("\n", "<br>"));
//									res.append("<br>");
//									if(element.getFunction()!=null && element.getFunction().getArguments().length>0) {
//										res.append("<br><b>参数</b><br>");
//										for(JavaScriptArgument a:element.getFunction().getArguments()) {
//											res.append(a.toString()+"<br>");
//										}
//									}
//									if(element.getFunction()!=null && element.getFunction().getReturnTypes().length>0) {
//										res.append("<br><b>返回</b><br>");
//										for(String r:element.getFunction().getReturnTypes()) {
//											res.append(r+"<br>");
//										}
//									}
//									if(element.getFromSource()!=null) {
//										res.append("<br><b>起源</b><br>");
//										res.append("<a>"+element.getFromSource()+"</a><br>");
//									}
								}
								addedStrings.add(replace);
							}
						}
					}
				}
			}
		}
//		return res.toString();
		if(res.length()<1) {
			return null;
		}
		return res.toString();
	}

	@Override
	public IRegion getHoverRegion(ITextViewer viewer, int offset) {
		return new Region(offset, 0);
	}

	/**
	 * Cuts out the last word of caret position.
	 * 
	 * @param viewer  ITextViewer
	 * @param current the caret offset
	 * @return the last word of caret position
	 */
	public static String[] getLastWord(String source, int offset) {
		StringBuilder buf = new StringBuilder();
		int current = offset - 1;
		int b1 = 0; // ()
		int b2 = 0; // []
		while (current >= 0) {
			char c = source.charAt(current);
			if (c >= 'a' && c <= 'z') {
				if (b1 == 0 && b2 == 0) {
					buf.insert(0, c);
				}
			} else if (c >= 'A' && c <= 'Z') {
				if (b1 == 0 && b2 == 0) {
					buf.insert(0, c);
				}
			} else if (c >= '0' && c <= '9') {
				if (b1 == 0 && b2 == 0) {
					buf.insert(0, c);
				}
			} else if (c == '_' || c == '$' || c == '.') {
				if (b1 == 0 && b2 == 0) {
					buf.insert(0, c);
				}
			} else if (c == '(' && b1 > 0) {
				b1--;
			} else if (c == ')') {
				b1++;
			} else if (c == '[' && b2 > 0) {
				b2--;
			} else if (c == ']') {
				b2++;
			} else if (c == ' ' && buf.length() > 0 && b1 == 0 && b2 == 0) {
				break;
			} else if (b1 == 0 && b2 == 0) {
				break;
			}
			current--;
		}

		String target = source.substring(current + 1, offset);

		String word = buf.toString().trim();
		int pos = word.lastIndexOf('.');
		if (pos >= 0) {
			return new String[] { target, word.substring(0, pos + 1), word.substring(pos + 1), "" };
		} else {
			StringBuilder requirePathBuf = new StringBuilder();
			int i = offset - 1;
			while (i >= 0) {
				char c = source.charAt(i);
				if (c >= 'a' && c <= 'z') {
					requirePathBuf.insert(0, c);
				} else if (c >= 'A' && c <= 'Z') {
					requirePathBuf.insert(0, c);
				} else if (c >= '0' && c <= '9') {
					requirePathBuf.insert(0, c);
				} else if (c == '.' || c == '/' || c == '(' || c == '"' || c == '\'' || c == '_' || c == '$') {
					requirePathBuf.insert(0, c);
				} else {
					break;
				}
				i--;
			}
			return new String[] { target, "", word, requirePathBuf.toString() };
		}
	}

	private boolean isTargetElement(JavaScriptElement jsElement, String lowerWord) {
		if (jsElement.isPrivate()) {
			return false;
		}
		if (jsElement instanceof JavaScriptFunction && ((JavaScriptFunction) jsElement).isAnonymous()) {
			return false;
		}
		if (lowerWord.length() == 0) {
			return true;
		}
		return jsElement.getName().toLowerCase().startsWith(lowerWord);
	}

	@Override
	public IInformationControlCreator getHoverControlCreator() {
		return parent -> new DefaultInformationControl(parent, EditorsPlugin.getAdditionalInfoAffordanceString());
	}

}
