package com.qs.iChain.util;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/***
 * Util class for processing {@link Method}.
 *
 * @author TsingSungHu
 */
public final class MethodUtil {

    private static final Map<Method, String> methodNameMap = new ConcurrentHashMap<Method, String>();

    private static final Object LOCK = new Object();

    /**
     * Parse and resolve the method name, then cache to the map.
     *
     * @param method method instance
     * @return resolved method name
     */
    public static String resolveMethodName(Method method) {
        if (method == null) {
            throw new IllegalArgumentException("Null method");
        }
        String methodName = methodNameMap.get(method);
        if (methodName == null) {
            synchronized (LOCK) {
                methodName = methodNameMap.get(method);
                if (methodName == null) {
                    StringBuilder sb = new StringBuilder();

                    String className = method.getDeclaringClass().getName();
                    String name = method.getName();
                    Class<?>[] params = method.getParameterTypes();
                    sb.append(className).append(":").append(name);
                    sb.append("(");

                    int paramPos = 0;
                    for (Class<?> clazz : params) {
                        sb.append(clazz.getCanonicalName());
                        if (++paramPos < params.length) {
                            sb.append(",");
                        }
                    }
                    sb.append(")");
                    methodName = sb.toString();

                    methodNameMap.put(method, methodName);
                }
            }
        }
        return methodName;
    }

    /**
     * For test.
     */
    static void clearMethodMap() {
        methodNameMap.clear();
    }
}
