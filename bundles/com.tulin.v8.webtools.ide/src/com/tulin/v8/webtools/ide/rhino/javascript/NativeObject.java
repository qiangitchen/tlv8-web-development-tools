/* -*- Mode: java; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * ***** BEGIN LICENSE BLOCK *****
 * Version: MPL 1.1/GPL 2.0
 *
 * The contents of this file are subject to the Mozilla Public License Version
 * 1.1 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.
 *
 * The Original Code is Rhino code, released
 * May 6, 1999.
 *
 * The Initial Developer of the Original Code is
 * Netscape Communications Corporation.
 * Portions created by the Initial Developer are Copyright (C) 1997-1999
 * the Initial Developer. All Rights Reserved.
 *
 * Contributor(s):
 *   Norris Boyd
 *   Igor Bukanov
 *   Bob Jervis
 *   Mike McCabe
 *
 * Alternatively, the contents of this file may be used under the terms of
 * the GNU General Public License Version 2 or later (the "GPL"), in which
 * case the provisions of the GPL are applicable instead of those above. If
 * you wish to allow use of your version of this file only under the terms of
 * the GPL and not to allow others to use your version of this file under the
 * MPL, indicate your decision by deleting the provisions above and replacing
 * them with the notice and other provisions required by the GPL. If you do
 * not delete the provisions above, a recipient may use your version of this
 * file under either the MPL or the GPL.
 *
 * ***** END LICENSE BLOCK ***** */

package com.tulin.v8.webtools.ide.rhino.javascript;

/**
 * This class implements the Object native object. See ECMA 15.2.
 */
public class NativeObject extends IdScriptableObject {
	static final long serialVersionUID = -6345305608474346996L;

	private static final Object OBJECT_TAG = "Object";

	static void init(Scriptable scope, boolean sealed) {
		NativeObject obj = new NativeObject();
		obj.exportAsJSClass(MAX_PROTOTYPE_ID, scope, sealed);
	}

	@Override
	public String getClassName() {
		return "Object";
	}

	@Override
	public String toString() {
		return ScriptRuntime.defaultObjectToString(this);
	}

	@Override
	protected void initPrototypeId(int id) {
		String s;
		int arity;
		switch (id) {
		case Id_constructor:
			arity = 1;
			s = "constructor";
			break;
		case Id_toString:
			arity = 0;
			s = "toString";
			break;
		case Id_toLocaleString:
			arity = 0;
			s = "toLocaleString";
			break;
		case Id_valueOf:
			arity = 0;
			s = "valueOf";
			break;
		case Id_hasOwnProperty:
			arity = 1;
			s = "hasOwnProperty";
			break;
		case Id_propertyIsEnumerable:
			arity = 1;
			s = "propertyIsEnumerable";
			break;
		case Id_isPrototypeOf:
			arity = 1;
			s = "isPrototypeOf";
			break;
		case Id_toSource:
			arity = 0;
			s = "toSource";
			break;
		case Id___defineGetter__:
			arity = 2;
			s = "__defineGetter__";
			break;
		case Id___defineSetter__:
			arity = 2;
			s = "__defineSetter__";
			break;
		case Id___lookupGetter__:
			arity = 1;
			s = "__lookupGetter__";
			break;
		case Id___lookupSetter__:
			arity = 1;
			s = "__lookupSetter__";
			break;
		default:
			throw new IllegalArgumentException(String.valueOf(id));
		}
		initPrototypeMethod(OBJECT_TAG, id, s, arity);
	}

	@Override
	public Object execIdCall(IdFunctionObject f, Context cx, Scriptable scope, Scriptable thisObj, Object[] args) {
		if (!f.hasTag(OBJECT_TAG)) {
			return super.execIdCall(f, cx, scope, thisObj, args);
		}
		int id = f.methodId();
		switch (id) {
		case Id_constructor: {
			if (thisObj != null) {
				// BaseFunction.construct will set up parent, proto
				return f.construct(cx, scope, args);
			}
			if (args.length == 0 || args[0] == null || args[0] == Undefined.instance) {
				return new NativeObject();
			}
			return ScriptRuntime.toObject(cx, scope, args[0]);
		}

		case Id_toLocaleString: // For now just alias toString
		case Id_toString: {
			if (cx.hasFeature(Context.FEATURE_TO_STRING_AS_SOURCE)) {
				String s = ScriptRuntime.defaultObjectToSource(cx, scope, thisObj, args);
				int L = s.length();
				if (L != 0 && s.charAt(0) == '(' && s.charAt(L - 1) == ')') {
					// Strip () that surrounds toSource
					s = s.substring(1, L - 1);
				}
				return s;
			}
			return ScriptRuntime.defaultObjectToString(thisObj);
		}

		case Id_valueOf:
			return thisObj;

		case Id_hasOwnProperty: {
			boolean result;
			if (args.length == 0) {
				result = false;
			} else {
				String s = ScriptRuntime.toStringIdOrIndex(cx, args[0]);
				if (s == null) {
					int index = ScriptRuntime.lastIndexResult(cx);
					result = thisObj.has(index, thisObj);
				} else {
					result = thisObj.has(s, thisObj);
				}
			}
			return ScriptRuntime.wrapBoolean(result);
		}

		case Id_propertyIsEnumerable: {
			boolean result;
			if (args.length == 0) {
				result = false;
			} else {
				String s = ScriptRuntime.toStringIdOrIndex(cx, args[0]);
				if (s == null) {
					int index = ScriptRuntime.lastIndexResult(cx);
					result = thisObj.has(index, thisObj);
					if (result && thisObj instanceof ScriptableObject) {
						ScriptableObject so = (ScriptableObject) thisObj;
						int attrs = so.getAttributes(index);
						result = ((attrs & ScriptableObject.DONTENUM) == 0);
					}
				} else {
					result = thisObj.has(s, thisObj);
					if (result && thisObj instanceof ScriptableObject) {
						ScriptableObject so = (ScriptableObject) thisObj;
						int attrs = so.getAttributes(s);
						result = ((attrs & ScriptableObject.DONTENUM) == 0);
					}
				}
			}
			return ScriptRuntime.wrapBoolean(result);
		}

		case Id_isPrototypeOf: {
			boolean result = false;
			if (args.length != 0 && args[0] instanceof Scriptable) {
				Scriptable v = (Scriptable) args[0];
				do {
					v = v.getPrototype();
					if (v == thisObj) {
						result = true;
						break;
					}
				} while (v != null);
			}
			return ScriptRuntime.wrapBoolean(result);
		}

		case Id_toSource:
			return ScriptRuntime.defaultObjectToSource(cx, scope, thisObj, args);
		case Id___defineGetter__:
		case Id___defineSetter__: {
			if (args.length < 2 || !(args[1] instanceof Callable)) {
				Object badArg = (args.length >= 2 ? args[1] : Undefined.instance);
				throw ScriptRuntime.notFunctionError(badArg);
			}
			if (!(thisObj instanceof ScriptableObject)) {
				throw Context.reportRuntimeError2("msg.extend.scriptable", thisObj.getClass().getName(),
						String.valueOf(args[0]));
			}
			ScriptableObject so = (ScriptableObject) thisObj;
			String name = ScriptRuntime.toStringIdOrIndex(cx, args[0]);
			int index = (name != null ? 0 : ScriptRuntime.lastIndexResult(cx));
			Callable getterOrSetter = (Callable) args[1];
			boolean isSetter = (id == Id___defineSetter__);
			so.setGetterOrSetter(name, index, getterOrSetter, isSetter);
			if (so instanceof NativeArray)
				((NativeArray) so).setDenseOnly(false);
		}
			return Undefined.instance;

		case Id___lookupGetter__:
		case Id___lookupSetter__: {
			if (args.length < 1 || !(thisObj instanceof ScriptableObject))
				return Undefined.instance;

			ScriptableObject so = (ScriptableObject) thisObj;
			String name = ScriptRuntime.toStringIdOrIndex(cx, args[0]);
			int index = (name != null ? 0 : ScriptRuntime.lastIndexResult(cx));
			boolean isSetter = (id == Id___lookupSetter__);
			Object gs;
			for (;;) {
				gs = so.getGetterOrSetter(name, index, isSetter);
				if (gs != null)
					break;
				// If there is no getter or setter for the object itself,
				// how about the prototype?
				Scriptable v = so.getPrototype();
				if (v == null)
					break;
				if (v instanceof ScriptableObject)
					so = (ScriptableObject) v;
				else
					break;
			}
			if (gs != null)
				return gs;
		}
			return Undefined.instance;

		default:
			throw new IllegalArgumentException(String.valueOf(id));
		}
	}

// #string_id_map#

	@Override
	protected int findPrototypeId(String s) {
		int id;
// #generated# Last update: 2007-05-09 08:15:55 EDT
		L0: {
			id = 0;
			String X = null;
			int c;
			L: switch (s.length()) {
			case 7:
				X = "valueOf";
				id = Id_valueOf;
				break L;
			case 8:
				c = s.charAt(3);
				if (c == 'o') {
					X = "toSource";
					id = Id_toSource;
				} else if (c == 't') {
					X = "toString";
					id = Id_toString;
				}
				break L;
			case 11:
				X = "constructor";
				id = Id_constructor;
				break L;
			case 13:
				X = "isPrototypeOf";
				id = Id_isPrototypeOf;
				break L;
			case 14:
				c = s.charAt(0);
				if (c == 'h') {
					X = "hasOwnProperty";
					id = Id_hasOwnProperty;
				} else if (c == 't') {
					X = "toLocaleString";
					id = Id_toLocaleString;
				}
				break L;
			case 16:
				c = s.charAt(2);
				if (c == 'd') {
					c = s.charAt(8);
					if (c == 'G') {
						X = "__defineGetter__";
						id = Id___defineGetter__;
					} else if (c == 'S') {
						X = "__defineSetter__";
						id = Id___defineSetter__;
					}
				} else if (c == 'l') {
					c = s.charAt(8);
					if (c == 'G') {
						X = "__lookupGetter__";
						id = Id___lookupGetter__;
					} else if (c == 'S') {
						X = "__lookupSetter__";
						id = Id___lookupSetter__;
					}
				}
				break L;
			case 20:
				X = "propertyIsEnumerable";
				id = Id_propertyIsEnumerable;
				break L;
			}
			if (X != null && X != s && !X.equals(s))
				id = 0;
			break L0;
		}
// #/generated#
		return id;
	}

	private static final int Id_constructor = 1, Id_toString = 2, Id_toLocaleString = 3, Id_valueOf = 4,
			Id_hasOwnProperty = 5, Id_propertyIsEnumerable = 6, Id_isPrototypeOf = 7, Id_toSource = 8,
			Id___defineGetter__ = 9, Id___defineSetter__ = 10, Id___lookupGetter__ = 11, Id___lookupSetter__ = 12,
			MAX_PROTOTYPE_ID = 12;

// #/string_id_map#
}
