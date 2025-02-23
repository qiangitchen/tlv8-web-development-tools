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
 *   Roger Lawrence
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
 * The base class for Function objects
 * See ECMA 15.3.
 */
public class BaseFunction extends IdScriptableObject implements Function
{

    static final long serialVersionUID = 5311394446546053859L;

    private static final Object FUNCTION_TAG = "Function";

    static void init(Scriptable scope, boolean sealed)
    {
        BaseFunction obj = new BaseFunction();
        // Function.prototype attributes: see ECMA 15.3.3.1 
        obj.prototypePropertyAttributes = DONTENUM | READONLY | PERMANENT;
        obj.exportAsJSClass(MAX_PROTOTYPE_ID, scope, sealed);
    }

    public BaseFunction()
    {
    }

    public BaseFunction(Scriptable scope, Scriptable prototype)
    {
        super(scope, prototype);
    }

    @Override
    public String getClassName() {
        return "Function";
    }

    /**
     * Implements the instanceof operator for JavaScript Function objects.
     * <p>
     * <code>
     * foo = new Foo();<br>
     * foo instanceof Foo;  // true<br>
     * </code>
     *
     * @param instance The value that appeared on the LHS of the instanceof
     *              operator
     * @return true if the "prototype" property of "this" appears in
     *              value's prototype chain
     *
     */
    @Override
    public boolean hasInstance(Scriptable instance)
    {
        Object protoProp = ScriptableObject.getProperty(this, "prototype");
        if (protoProp instanceof Scriptable) {
            return ScriptRuntime.jsDelegatesTo(instance, (Scriptable)protoProp);
        }
        throw ScriptRuntime.typeError1("msg.instanceof.bad.prototype",
                                       getFunctionName());
    }

// #string_id_map#

    private static final int
        Id_length       = 1,
        Id_arity        = 2,
        Id_name         = 3,
        Id_prototype    = 4,
        Id_arguments    = 5,

        MAX_INSTANCE_ID = 5;

    @Override
    protected int getMaxInstanceId()
    {
        return MAX_INSTANCE_ID;
    }

    @Override
    protected int findInstanceIdInfo(String s)
    {
        int id;
// #generated# Last update: 2007-05-09 08:15:15 EDT
        L0: { id = 0; String X = null; int c;
            L: switch (s.length()) {
            case 4: X="name";id=Id_name; break L;
            case 5: X="arity";id=Id_arity; break L;
            case 6: X="length";id=Id_length; break L;
            case 9: c=s.charAt(0);
                if (c=='a') { X="arguments";id=Id_arguments; }
                else if (c=='p') { X="prototype";id=Id_prototype; }
                break L;
            }
            if (X!=null && X!=s && !X.equals(s)) id = 0;
            break L0;
        }
// #/generated#
// #/string_id_map#

        if (id == 0) return super.findInstanceIdInfo(s);

        int attr;
        switch (id) {
          case Id_length:
          case Id_arity:
          case Id_name:
            attr = DONTENUM | READONLY | PERMANENT;
            break;
          case Id_prototype:
            attr = prototypePropertyAttributes;
            break;
          case Id_arguments:
            attr = DONTENUM | PERMANENT;
            break;
          default: throw new IllegalStateException();
        }
        return instanceIdInfo(attr, id);
    }

    @Override
    protected String getInstanceIdName(int id)
    {
        switch (id) {
            case Id_length:       return "length";
            case Id_arity:        return "arity";
            case Id_name:         return "name";
            case Id_prototype:    return "prototype";
            case Id_arguments:    return "arguments";
        }
        return super.getInstanceIdName(id);
    }

    @Override
    protected Object getInstanceIdValue(int id)
    {
        switch (id) {
          case Id_length:    return ScriptRuntime.wrapInt(getLength());
          case Id_arity:     return ScriptRuntime.wrapInt(getArity());
          case Id_name:      return getFunctionName();
          case Id_prototype: return getPrototypeProperty();
          case Id_arguments: return getArguments();
        }
        return super.getInstanceIdValue(id);
    }

    @Override
    protected void setInstanceIdValue(int id, Object value)
    {
        if (id == Id_prototype) {
            if ((prototypePropertyAttributes & READONLY) == 0) {
                prototypeProperty = (value != null)
                                    ? value : UniqueTag.NULL_VALUE;
            }
            return;
        } else if (id == Id_arguments) {
            if (value == NOT_FOUND) {
                // This should not be called since "arguments" is PERMANENT
                Kit.codeBug();
            }
            defaultPut("arguments", value);
        }
        super.setInstanceIdValue(id, value);
    }

    @Override
    protected void fillConstructorProperties(IdFunctionObject ctor)
    {
        // Fix up bootstrapping problem: getPrototype of the IdFunctionObject
        // can not return Function.prototype because Function object is not
        // yet defined.
        ctor.setPrototype(this);
        super.fillConstructorProperties(ctor);
    }

    @Override
    protected void initPrototypeId(int id)
    {
        String s;
        int arity;
        switch (id) {
          case Id_constructor: arity=1; s="constructor"; break;
          case Id_toString:    arity=1; s="toString";    break;
          case Id_toSource:    arity=1; s="toSource";    break;
          case Id_apply:       arity=2; s="apply";       break;
          case Id_call:        arity=1; s="call";        break;
          default: throw new IllegalArgumentException(String.valueOf(id));
        }
        initPrototypeMethod(FUNCTION_TAG, id, s, arity);
    }

    static boolean isApply(IdFunctionObject f) {
        return f.hasTag(FUNCTION_TAG) && f.methodId() == Id_apply;
    }
    
    static boolean isApplyOrCall(IdFunctionObject f) {
        if(f.hasTag(FUNCTION_TAG)) {
            switch(f.methodId()) {
                case Id_apply:
                case Id_call:
                    return true;
            }
        }
        return false;
    }

    @Override
    public Object execIdCall(IdFunctionObject f, Context cx, Scriptable scope,
                             Scriptable thisObj, Object[] args)
    {
        if (!f.hasTag(FUNCTION_TAG)) {
            return super.execIdCall(f, cx, scope, thisObj, args);
        }
        int id = f.methodId();
        switch (id) {
          case Id_constructor:
            return jsConstructor(cx, scope, args);

          case Id_toString: {
            BaseFunction realf = realFunction(thisObj, f);
            int indent = ScriptRuntime.toInt32(args, 0);
            return realf.decompile(indent, 0);
          }

          case Id_toSource: {
            BaseFunction realf = realFunction(thisObj, f);
            int indent = 0;
            int flags = Decompiler.TO_SOURCE_FLAG;
            if (args.length != 0) {
                indent = ScriptRuntime.toInt32(args[0]);
                if (indent >= 0) {
                    flags = 0;
                } else {
                    indent = 0;
                }
            }
            return realf.decompile(indent, flags);
          }

          case Id_apply:
          case Id_call:
            return ScriptRuntime.applyOrCall(id == Id_apply,
                                             cx, scope, thisObj, args);
        }
        throw new IllegalArgumentException(String.valueOf(id));
    }

    private BaseFunction realFunction(Scriptable thisObj, IdFunctionObject f)
    {
        Object x = thisObj.getDefaultValue(ScriptRuntime.FunctionClass);
        if (x instanceof BaseFunction) {
            return (BaseFunction)x;
        }
        throw ScriptRuntime.typeError1("msg.incompat.call",
                                       f.getFunctionName());
    }

    /**
     * Make value as DontEnum, DontDelete, ReadOnly
     * prototype property of this Function object
     */
    public void setImmunePrototypeProperty(Object value)
    {
        if ((prototypePropertyAttributes & READONLY) != 0) {
            throw new IllegalStateException();
        }
        prototypeProperty = (value != null) ? value : UniqueTag.NULL_VALUE;
        prototypePropertyAttributes = DONTENUM | PERMANENT | READONLY;
    }

    protected Scriptable getClassPrototype()
    {
        Object protoVal = getPrototypeProperty();
        if (protoVal instanceof Scriptable) {
            return (Scriptable) protoVal;
        }
        return getClassPrototype(this, "Object");
    }

    /**
     * Should be overridden.
     */
    public Object call(Context cx, Scriptable scope, Scriptable thisObj,
                       Object[] args)
    {
        return Undefined.instance;
    }

    public Scriptable construct(Context cx, Scriptable scope, Object[] args)
    {
        Scriptable result = createObject(cx, scope);
        if (result != null) {
            Object val = call(cx, scope, result, args);
            if (val instanceof Scriptable) {
                result = (Scriptable)val;
            }
        } else {
            Object val = call(cx, scope, null, args);
            if (!(val instanceof Scriptable)) {
                // It is program error not to return Scriptable from
                // the call method if createObject returns null.
                throw new IllegalStateException(
                    "Bad implementaion of call as constructor, name="
                    +getFunctionName()+" in "+getClass().getName());
            }
            result = (Scriptable)val;
            if (result.getPrototype() == null) {
                result.setPrototype(getClassPrototype());
            }
            if (result.getParentScope() == null) {
                Scriptable parent = getParentScope();
                if (result != parent) {
                    result.setParentScope(parent);
                }
            }
        }
        return result;
    }

    /**
     * Creates new script object.
     * The default implementation of {@link #construct} uses the method to
     * to get the value for <tt>thisObj</tt> argument when invoking
     * {@link #call}.
     * The methos is allowed to return <tt>null</tt> to indicate that
     * {@link #call} will create a new object itself. In this case
     * {@link #construct} will set scope and prototype on the result
     * {@link #call} unless they are already set.
     */
    public Scriptable createObject(Context cx, Scriptable scope)
    {
        Scriptable newInstance = new NativeObject();
        newInstance.setPrototype(getClassPrototype());
        newInstance.setParentScope(getParentScope());
        return newInstance;
    }

    /**
     * Decompile the source information associated with this js
     * function/script back into a string.
     *
     * @param indent How much to indent the decompiled result.
     *
     * @param flags Flags specifying format of decompilation output.
     */
    String decompile(int indent, int flags)
    {
        StringBuffer sb = new StringBuffer();
        boolean justbody = (0 != (flags & Decompiler.ONLY_BODY_FLAG));
        if (!justbody) {
            sb.append("function ");
            sb.append(getFunctionName());
            sb.append("() {\n\t");
        }
        sb.append("[native code, arity=");
        sb.append(getArity());
        sb.append("]\n");
        if (!justbody) {
            sb.append("}\n");
        }
        return sb.toString();
    }

    public int getArity() { return 0; }

    public int getLength() { return 0; }

    public String getFunctionName()
    {
        return "";
    }

    final Object getPrototypeProperty() {
        Object result = prototypeProperty;
        if (result == null) {
            synchronized (this) {
                result = prototypeProperty;
                if (result == null) {
                    setupDefaultPrototype();
                    result = prototypeProperty;
                }
            }
        }
        else if (result == UniqueTag.NULL_VALUE) { result = null; }
        return result;
    }

    private void setupDefaultPrototype()
    {
        NativeObject obj = new NativeObject();
        final int attr = ScriptableObject.DONTENUM;
        obj.defineProperty("constructor", this, attr);
        // put the prototype property into the object now, then in the
        // wacky case of a user defining a function Object(), we don't
        // get an infinite loop trying to find the prototype.
        prototypeProperty = obj;
        Scriptable proto = getObjectPrototype(this);
        if (proto != obj) {
            // not the one we just made, it must remain grounded
            obj.setPrototype(proto);
        }
    }

    private Object getArguments()
    {
      // <Function name>.arguments is deprecated, so we use a slow
      // way of getting it that doesn't add to the invocation cost.
      // TODO: add warning, error based on version
      Object value = defaultGet("arguments");
      if (value != NOT_FOUND) {
          // Should after changing <Function name>.arguments its
          // activation still be available during Function call?
          // This code assumes it should not:
          // defaultGet("arguments") != NOT_FOUND
          // means assigned arguments
          return value;
      }
      Context cx = Context.getContext();
      NativeCall activation = ScriptRuntime.findFunctionActivation(cx, this);
      return (activation == null)
             ? null
             : activation.get("arguments", activation);
    }

    private static Object jsConstructor(Context cx, Scriptable scope,
                                        Object[] args)
    {
        int arglen = args.length;
        StringBuffer sourceBuf = new StringBuffer();

        sourceBuf.append("function ");
        /* version != 1.2 Function constructor behavior -
         * print 'anonymous' as the function name if the
         * version (under which the function was compiled) is
         * less than 1.2... or if it's greater than 1.2, because
         * we need to be closer to ECMA.
         */
        if (cx.getLanguageVersion() != Context.VERSION_1_2) {
            sourceBuf.append("anonymous");
        }
        sourceBuf.append('(');

        // Append arguments as coma separated strings
        for (int i = 0; i < arglen - 1; i++) {
            if (i > 0) {
                sourceBuf.append(',');
            }
            sourceBuf.append(ScriptRuntime.toString(args[i]));
        }
        sourceBuf.append(") {");
        if (arglen != 0) {
            // append function body
            String funBody = ScriptRuntime.toString(args[arglen - 1]);
            sourceBuf.append(funBody);
        }
        sourceBuf.append('}');
        String source = sourceBuf.toString();

        int[] linep = new int[1];
        String filename = Context.getSourcePositionFromStack(linep);
        if (filename == null) {
            filename = "<eval'ed string>";
            linep[0] = 1;
        }

        String sourceURI = ScriptRuntime.
            makeUrlForGeneratedScript(false, filename, linep[0]);

        Scriptable global = ScriptableObject.getTopLevelScope(scope);

        ErrorReporter reporter;
        reporter = DefaultErrorReporter.forEval(cx.getErrorReporter());

        Evaluator evaluator = Context.createInterpreter();
        if (evaluator == null) {
            throw new JavaScriptException("Interpreter not present",
                    filename, linep[0]);            
        }

        // Compile with explicit interpreter instance to force interpreter
        // mode.
        return cx.compileFunction(global, source, evaluator, reporter,
                                  sourceURI, 1, null);
    }

    @Override
    protected int findPrototypeId(String s)
    {
        int id;
// #string_id_map#
// #generated# Last update: 2007-05-09 08:15:15 EDT
        L0: { id = 0; String X = null; int c;
            L: switch (s.length()) {
            case 4: X="call";id=Id_call; break L;
            case 5: X="apply";id=Id_apply; break L;
            case 8: c=s.charAt(3);
                if (c=='o') { X="toSource";id=Id_toSource; }
                else if (c=='t') { X="toString";id=Id_toString; }
                break L;
            case 11: X="constructor";id=Id_constructor; break L;
            }
            if (X!=null && X!=s && !X.equals(s)) id = 0;
            break L0;
        }
// #/generated#
        return id;
    }

    private static final int
        Id_constructor    = 1,
        Id_toString       = 2,
        Id_toSource       = 3,
        Id_apply          = 4,
        Id_call           = 5,

        MAX_PROTOTYPE_ID  = 5;

// #/string_id_map#

    private Object prototypeProperty;
    // For function object instances, attribute is PERMANENT; see ECMA 15.3.5.2
    private int prototypePropertyAttributes = PERMANENT;
}

