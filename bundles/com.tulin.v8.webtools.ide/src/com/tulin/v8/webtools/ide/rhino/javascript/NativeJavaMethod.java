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
 *   Frank Mitchell
 *   Mike Shaver
 *   Ulrike Mueller <umueller@demandware.com>
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

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * This class reflects Java methods into the JavaScript environment and
 * handles overloading of methods.
 *
 * @author Mike Shaver
 * @see NativeJavaArray
 * @see NativeJavaPackage
 * @see NativeJavaClass
 */

public class NativeJavaMethod extends BaseFunction
{
    static final long serialVersionUID = -3440381785576412928L;

    NativeJavaMethod(MemberBox[] methods)
    {
        this.functionName = methods[0].getName();
        this.methods = methods;
    }

    NativeJavaMethod(MemberBox method, String name)
    {
        this.functionName = name;
        this.methods = new MemberBox[] { method };
    }

    public NativeJavaMethod(Method method, String name)
    {
        this(new MemberBox(method), name);
    }

    @Override
    public String getFunctionName()
    {
        return functionName;
    }

    static String scriptSignature(Object[] values)
    {
        StringBuffer sig = new StringBuffer();
        for (int i = 0; i != values.length; ++i) {
            Object value = values[i];

            String s;
            if (value == null) {
                s = "null";
            } else if (value instanceof Boolean) {
                s = "boolean";
            } else if (value instanceof String) {
                s = "string";
            } else if (value instanceof Number) {
                s = "number";
            } else if (value instanceof Scriptable) {
                if (value instanceof Undefined) {
                    s = "undefined";
                } else if (value instanceof Wrapper) {
                    Object wrapped = ((Wrapper)value).unwrap();
                    s = wrapped.getClass().getName();
                } else if (value instanceof Function) {
                    s = "function";
                } else {
                    s = "object";
                }
            } else {
                s = JavaMembers.javaSignature(value.getClass());
            }

            if (i != 0) {
                sig.append(',');
            }
            sig.append(s);
        }
        return sig.toString();
    }

    @Override
    String decompile(int indent, int flags)
    {
        StringBuffer sb = new StringBuffer();
        boolean justbody = (0 != (flags & Decompiler.ONLY_BODY_FLAG));
        if (!justbody) {
            sb.append("function ");
            sb.append(getFunctionName());
            sb.append("() {");
        }
        sb.append("/*\n");
        sb.append(toString());
        sb.append(justbody ? "*/\n" : "*/}\n");
        return sb.toString();
    }

    @Override
    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        for (int i = 0, N = methods.length; i != N; ++i) {
            Method method = methods[i].method();
            sb.append(JavaMembers.javaSignature(method.getReturnType()));
            sb.append(' ');
            sb.append(method.getName());
            sb.append(JavaMembers.liveConnectSignature(methods[i].argTypes));
            sb.append('\n');
        }
        return sb.toString();
    }

    @Override
    public Object call(Context cx, Scriptable scope, Scriptable thisObj,
                       Object[] args)
    {
        // Find a method that matches the types given.
        if (methods.length == 0) {
            throw new RuntimeException("No methods defined for call");
        }

        int index = findFunction(cx, methods, args);
        if (index < 0) {
            Class<?> c = methods[0].method().getDeclaringClass();
            String sig = c.getName() + '.' + getFunctionName() + '(' +
                         scriptSignature(args) + ')';
            throw Context.reportRuntimeError1("msg.java.no_such_method", sig);
        }

        MemberBox meth = methods[index];
        Class<?>[] argTypes = meth.argTypes;
      
        if (meth.vararg) {
            // marshall the explicit parameters
            Object[] newArgs = new Object[argTypes.length];
            for (int i = 0; i < argTypes.length-1; i++) {
                newArgs[i] = Context.jsToJava(args[i], argTypes[i]);
            }
            
            Object varArgs;
            
            // Handle special situation where a single variable parameter
            // is given and it is a Java or ECMA array or is null.
            if (args.length == argTypes.length &&
                (args[args.length-1] == null ||
                 args[args.length-1] instanceof NativeArray ||
                 args[args.length-1] instanceof NativeJavaArray))
            {
                // convert the ECMA array into a native array
                varArgs = Context.jsToJava(args[args.length-1], 
                                           argTypes[argTypes.length - 1]);
            } else {            
                // marshall the variable parameters
                Class<?> componentType = argTypes[argTypes.length - 1].
                                         getComponentType();
                varArgs = Array.newInstance(componentType, 
                                            args.length - argTypes.length + 1);            
                for (int i = 0; i < Array.getLength(varArgs); i++) {
                    Object value = Context.jsToJava(args[argTypes.length-1 + i], 
                                                    componentType);
                    Array.set(varArgs, i, value);
                }
            }
            
            // add varargs
            newArgs[argTypes.length-1] = varArgs;
            // replace the original args with the new one
            args = newArgs;
        } else {  
            // First, we marshall the args.
            Object[] origArgs = args;
            for (int i = 0; i < args.length; i++) {
                Object arg = args[i];
                Object coerced = Context.jsToJava(arg, argTypes[i]);
                if (coerced != arg) {
                    if (origArgs == args) {
                        args = args.clone();
                    }
                    args[i] = coerced;
                }
            }
        }
        Object javaObject;
        if (meth.isStatic()) {
            javaObject = null;  // don't need an object
        } else {
            Scriptable o = thisObj;
            Class<?> c = meth.getDeclaringClass();
            for (;;) {
                if (o == null) {
                    throw Context.reportRuntimeError3(
                        "msg.nonjava.method", getFunctionName(),
                        ScriptRuntime.toString(thisObj), c.getName());
                }
                if (o instanceof Wrapper) {
                    javaObject = ((Wrapper)o).unwrap();
                    if (c.isInstance(javaObject)) {
                        break;
                    }
                }
                o = o.getPrototype();
            }
        }
        if (debug) {
            printDebug("Calling ", meth, args);
        }

        Object retval = meth.invoke(javaObject, args);
        Class<?> staticType = meth.method().getReturnType();

        if (debug) {
            Class<?> actualType = (retval == null) ? null
                                                : retval.getClass();
            System.err.println(" ----- Returned " + retval +
                               " actual = " + actualType +
                               " expect = " + staticType);
        }

        Object wrapped = cx.getWrapFactory().wrap(cx, scope,
                                                  retval, staticType);
        if (debug) {
            Class<?> actualType = (wrapped == null) ? null
                                                 : wrapped.getClass();
            System.err.println(" ----- Wrapped as " + wrapped +
                               " class = " + actualType);
        }

        if (wrapped == null && staticType == Void.TYPE) {
            wrapped = Undefined.instance;
        }
        return wrapped;
    }

    /**
     * Find the index of the correct function to call given the set of methods
     * or constructors and the arguments.
     * If no function can be found to call, return -1.
     */
    static int findFunction(Context cx,
                            MemberBox[] methodsOrCtors, Object[] args)
    {
        if (methodsOrCtors.length == 0) {
            return -1;
        } else if (methodsOrCtors.length == 1) {
            MemberBox member = methodsOrCtors[0];
            Class<?>[] argTypes = member.argTypes;
            int alength = argTypes.length;
            
            if (member.vararg) {
                alength--;
                if ( alength > args.length) {
                    return -1;
                }
            } else {
                if (alength != args.length) {
                    return -1;
                }
            }
            for (int j = 0; j != alength; ++j) {
                if (!NativeJavaObject.canConvert(args[j], argTypes[j])) {
                    if (debug) printDebug("Rejecting (args can't convert) ",
                                          member, args);
                    return -1;
                }
            }
            if (debug) printDebug("Found ", member, args);
            return 0;
        }

        int firstBestFit = -1;
        int[] extraBestFits = null;
        int extraBestFitsCount = 0;

      search:
        for (int i = 0; i < methodsOrCtors.length; i++) {
            MemberBox member = methodsOrCtors[i];
            Class<?>[] argTypes = member.argTypes;
            int alength = argTypes.length;
            if (member.vararg) {
                alength--;
                if ( alength > args.length) {
                    continue search;
                }
            } else {
                if (alength != args.length) {
                    continue search;
                }
            }
            for (int j = 0; j < alength; j++) {
                if (!NativeJavaObject.canConvert(args[j], argTypes[j])) {
                    if (debug) printDebug("Rejecting (args can't convert) ",
                                          member, args);
                    continue search;
                }
            }
            if (firstBestFit < 0) {
                if (debug) printDebug("Found first applicable ", member, args);
                firstBestFit = i;
            } else {
                // Compare with all currently fit methods.
                // The loop starts from -1 denoting firstBestFit and proceed
                // until extraBestFitsCount to avoid extraBestFits allocation
                // in the most common case of no ambiguity
                int betterCount = 0; // number of times member was prefered over
                                     // best fits
                int worseCount = 0;  // number of times best fits were prefered
                                     // over member
                for (int j = -1; j != extraBestFitsCount; ++j) {
                    int bestFitIndex;
                    if (j == -1) {
                        bestFitIndex = firstBestFit;
                    } else {
                        bestFitIndex = extraBestFits[j];
                    }
                    MemberBox bestFit = methodsOrCtors[bestFitIndex];
                    if (cx.hasFeature(Context.FEATURE_ENHANCED_JAVA_ACCESS) &&
                        (bestFit.member().getModifiers() & Modifier.PUBLIC) !=
                            (member.member().getModifiers() & Modifier.PUBLIC))
                    {
                        // When FEATURE_ENHANCED_JAVA_ACCESS gives us access
                        // to non-public members, continue to prefer public
                        // methods in overloading
                        if ((bestFit.member().getModifiers() & Modifier.PUBLIC) == 0)
                            ++betterCount;
                        else
                            ++worseCount;
                    } else {
                        int preference = preferSignature(args, argTypes,
                                                         member.vararg,
                                                         bestFit.argTypes,
                                                         bestFit.vararg );
                        if (preference == PREFERENCE_AMBIGUOUS) {
                            break;
                        } else if (preference == PREFERENCE_FIRST_ARG) {
                            ++betterCount;
                        } else if (preference == PREFERENCE_SECOND_ARG) {
                            ++worseCount;
                        } else {
                            if (preference != PREFERENCE_EQUAL) Kit.codeBug();
                            // This should not happen in theory
                            // but on some JVMs, Class.getMethods will return all
                            // static methods of the class hierarchy, even if
                            // a derived class's parameters match exactly.
                            // We want to call the derived class's method.
                            if (bestFit.isStatic() &&
                                bestFit.getDeclaringClass().isAssignableFrom(
                                       member.getDeclaringClass()))
                            {
                                // On some JVMs, Class.getMethods will return all
                                // static methods of the class hierarchy, even if
                                // a derived class's parameters match exactly.
                                // We want to call the derived class's method.
                                if (debug) printDebug(
                                    "Substituting (overridden static)",
                                    member, args);
                                if (j == -1) {
                                    firstBestFit = i;
                                } else {
                                    extraBestFits[j] = i;
                                }
                            } else {
                                if (debug) printDebug(
                                    "Ignoring same signature member ",
                                    member, args);
                            }
                            continue search;
                        }
                    }
                }
                if (betterCount == 1 + extraBestFitsCount) {
                    // member was prefered over all best fits
                    if (debug) printDebug(
                        "New first applicable ", member, args);
                    firstBestFit = i;
                    extraBestFitsCount = 0;
                } else if (worseCount == 1 + extraBestFitsCount) {
                    // all best fits were prefered over member, ignore it
                    if (debug) printDebug(
                        "Rejecting (all current bests better) ", member, args);
                } else {
                    // some ambiguity was present, add member to best fit set
                    if (debug) printDebug(
                        "Added to best fit set ", member, args);
                    if (extraBestFits == null) {
                        // Allocate maximum possible array
                        extraBestFits = new int[methodsOrCtors.length - 1];
                    }
                    extraBestFits[extraBestFitsCount] = i;
                    ++extraBestFitsCount;
                }
            }
        }

        if (firstBestFit < 0) {
            // Nothing was found
            return -1;
        } else if (extraBestFitsCount == 0) {
            // single best fit
            return firstBestFit;
        }

        // report remaining ambiguity
        StringBuffer buf = new StringBuffer();
        for (int j = -1; j != extraBestFitsCount; ++j) {
            int bestFitIndex;
            if (j == -1) {
                bestFitIndex = firstBestFit;
            } else {
                bestFitIndex = extraBestFits[j];
            }
            buf.append("\n    ");
            buf.append(methodsOrCtors[bestFitIndex].toJavaDeclaration());
        }

        MemberBox firstFitMember = methodsOrCtors[firstBestFit];
        String memberName = firstFitMember.getName();
        String memberClass = firstFitMember.getDeclaringClass().getName();

        if (methodsOrCtors[0].isMethod()) {
            throw Context.reportRuntimeError3(
                "msg.constructor.ambiguous",
                memberName, scriptSignature(args), buf.toString());
        } else {
            throw Context.reportRuntimeError4(
                "msg.method.ambiguous", memberClass,
                memberName, scriptSignature(args), buf.toString());
        }
    }

    /** Types are equal */
    private static final int PREFERENCE_EQUAL      = 0;
    private static final int PREFERENCE_FIRST_ARG  = 1;
    private static final int PREFERENCE_SECOND_ARG = 2;
    /** No clear "easy" conversion */
    private static final int PREFERENCE_AMBIGUOUS  = 3;

    /**
     * Determine which of two signatures is the closer fit.
     * Returns one of PREFERENCE_EQUAL, PREFERENCE_FIRST_ARG,
     * PREFERENCE_SECOND_ARG, or PREFERENCE_AMBIGUOUS.
     */
    private static int preferSignature(Object[] args, 
                                       Class<?>[] sig1,
                                       boolean vararg1,
                                       Class<?>[] sig2,
                                       boolean vararg2 )
    {
        // TODO: This test is pretty primitive. It basically prefers
        // a matching no vararg method over a vararg method independent
        // of the type conversion cost. This can lead to unexpected results.
        int alength = args.length;
        if (!vararg1 && vararg2) {
            // prefer the no vararg signature
            return PREFERENCE_FIRST_ARG;
        } else if (vararg1 && !vararg2) {
            // prefer the no vararg signature
            return PREFERENCE_SECOND_ARG;
        } else if (vararg1 && vararg2) {
            if (sig1.length < sig2.length) {
                // prefer the signature with more explicit types
                return PREFERENCE_SECOND_ARG;                
            } else if (sig1.length > sig2.length) {
                // prefer the signature with more explicit types
                return PREFERENCE_FIRST_ARG;                
            } else {
                // Both are varargs and have the same length, so make the
                // decision with the explicit args. 
                alength = Math.min(args.length, sig1.length-1);
            }
        }
        
        int totalPreference = 0;
        for (int j = 0; j < alength; j++) {
            Class<?> type1 = sig1[j];
            Class<?> type2 = sig2[j];
            if (type1 == type2) {
                continue;
            }
            Object arg = args[j];

            // Determine which of type1, type2 is easier to convert from arg.

            int rank1 = NativeJavaObject.getConversionWeight(arg, type1);
            int rank2 = NativeJavaObject.getConversionWeight(arg, type2);

            int preference;
            if (rank1 < rank2) {
                preference = PREFERENCE_FIRST_ARG;
            } else if (rank1 > rank2) {
                preference = PREFERENCE_SECOND_ARG;
            } else {
                // Equal ranks
                if (rank1 == NativeJavaObject.CONVERSION_NONTRIVIAL) {
                    if (type1.isAssignableFrom(type2)) {
                        preference = PREFERENCE_SECOND_ARG;
                    } else if (type2.isAssignableFrom(type1)) {
                        preference = PREFERENCE_FIRST_ARG;
                    } else {
                        preference = PREFERENCE_AMBIGUOUS;
                    }
                } else {
                    preference = PREFERENCE_AMBIGUOUS;
                }
            }

            totalPreference |= preference;

            if (totalPreference == PREFERENCE_AMBIGUOUS) {
                break;
            }
        }
        return totalPreference;
    }


    private static final boolean debug = false;

    private static void printDebug(String msg, MemberBox member,
                                   Object[] args)
    {
        if (debug) {
            StringBuffer sb = new StringBuffer();
            sb.append(" ----- ");
            sb.append(msg);
            sb.append(member.getDeclaringClass().getName());
            sb.append('.');
            if (member.isMethod()) {
                sb.append(member.getName());
            }
            sb.append(JavaMembers.liveConnectSignature(member.argTypes));
            sb.append(" for arguments (");
            sb.append(scriptSignature(args));
            sb.append(')');
            System.out.println(sb);
        }
    }

    MemberBox[] methods;
    private String functionName;
}

