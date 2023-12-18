/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.microsphere.util;

import static io.microsphere.text.FormatUtils.format;
import static io.microsphere.util.ClassUtils.isAssignableFrom;
import static io.microsphere.util.ClassUtils.newInstance;

/**
 * {@link Exception} Utilities class
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy</a>
 * @since 1.0.0
 */
public abstract class ExceptionUtils extends BaseUtils {

    public static <T extends Throwable, TT extends Throwable> TT wrap(T source, Class<TT> thrownType) {
        if (isAssignableFrom(thrownType, source.getClass())) {
            return (TT) source;
        }
        Object[] args = resolveArguments(source);
        return ClassUtils.newInstance(thrownType, args);
    }

    private static <T extends Throwable> Object[] resolveArguments(T source) {
        String message = source.getMessage();
        Throwable cause = source.getCause() == null ? source : source.getCause();
        return message == null ? new Object[]{cause} : new Object[]{message, cause};
    }

    public static <T extends Throwable> T create(Class<T> throwableClass, Throwable cause, String messagePattern, Object... args) {
        String message = format(messagePattern, args);
        return create(throwableClass, message, cause);
    }

    public static <T extends Throwable> T create(Class<T> throwableClass, String message, Throwable cause) {
        return newInstance(throwableClass, message, cause);
    }

    public static <T extends Throwable> T create(Class<T> throwableClass, Throwable cause) {
        return newInstance(throwableClass, cause);
    }

    public static <T extends Throwable> T create(Class<T> throwableClass, String message) {
        return newInstance(throwableClass, message);
    }

    public static <T extends Throwable> T create(Class<T> throwableClass) {
        return newInstance(throwableClass);
    }

    public static <T extends Throwable> T create(Class<T> throwableClass, Object... args) {
        return newInstance(throwableClass, args);
    }

    public static <T extends Throwable, TT extends Throwable> TT throwTarget(T source, Class<TT> thrownType) throws TT {
        throw wrap(source, thrownType);
    }
}
