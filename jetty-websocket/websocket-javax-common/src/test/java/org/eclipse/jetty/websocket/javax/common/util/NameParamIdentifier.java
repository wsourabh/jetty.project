//
// ========================================================================
// Copyright (c) 1995-2020 Mort Bay Consulting Pty Ltd and others.
//
// This program and the accompanying materials are made available under
// the terms of the Eclipse Public License 2.0 which is available at
// https://www.eclipse.org/legal/epl-2.0
//
// This Source Code may also be made available under the following
// Secondary Licenses when the conditions for such availability set
// forth in the Eclipse Public License, v. 2.0 are satisfied:
// the Apache License v2.0 which is available at
// https://www.apache.org/licenses/LICENSE-2.0
//
// SPDX-License-Identifier: EPL-2.0 OR Apache-2.0
// ========================================================================
//

package org.eclipse.jetty.websocket.javax.common.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.eclipse.jetty.util.annotation.Name;

/**
 * Simple {@link InvokerUtils.ParamIdentifier}
 * that observes {@link Name} tagged method parameters.
 */
public class NameParamIdentifier implements InvokerUtils.ParamIdentifier
{
    @Override
    public InvokerUtils.Arg getParamArg(Method method, Class<?> paramType, int idx)
    {
        Annotation[] annos = method.getParameterAnnotations()[idx];
        if (annos != null || (annos.length > 0))
        {
            for (Annotation anno : annos)
            {
                if (anno.annotationType().equals(Name.class))
                {
                    Name name = (Name)anno;
                    return new InvokerUtils.Arg(paramType, name.value());
                }
            }
        }

        return new InvokerUtils.Arg(paramType);
    }
}
