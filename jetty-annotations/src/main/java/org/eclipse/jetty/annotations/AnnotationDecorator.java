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

package org.eclipse.jetty.annotations;

import org.eclipse.jetty.util.DecoratedObjectFactory;
import org.eclipse.jetty.util.Decorator;
import org.eclipse.jetty.webapp.WebAppContext;

/**
 * AnnotationDecorator
 */
public class AnnotationDecorator implements Decorator
{
    protected AnnotationIntrospector _introspector;
    protected WebAppContext _context;

    public AnnotationDecorator(WebAppContext context)
    {
        _context = context;
        _introspector = new AnnotationIntrospector(_context);
        registerHandlers(_context);
    }

    private void registerHandlers(WebAppContext context)
    {
        _introspector.registerHandler(new ResourceAnnotationHandler(context));
        _introspector.registerHandler(new ResourcesAnnotationHandler(context));
        _introspector.registerHandler(new RunAsAnnotationHandler(context));
        _introspector.registerHandler(new PostConstructAnnotationHandler(context));
        _introspector.registerHandler(new PreDestroyAnnotationHandler(context));
        _introspector.registerHandler(new DeclareRolesAnnotationHandler(context));
        _introspector.registerHandler(new MultiPartConfigAnnotationHandler(context));
        _introspector.registerHandler(new ServletSecurityAnnotationHandler(context));
    }

    /**
     * Look for annotations that can be discovered with introspection:
     * <ul>
     * <li> Resource </li>
     * <li> Resources </li>
     * <li> RunAs </li>
     * <li> PostConstruct </li>
     * <li> PreDestroy </li>
     * <li> DeclareRoles </li>
     * <li> MultiPart </li>
     * <li> ServletSecurity</li>
     * </ul>
     *
     * @param o the object to introspect
     * @param metaInfo information about the object to introspect
     */
    protected void introspect(Object o, Object metaInfo)
    {
        if (o == null)
            return;
        _introspector.introspect(o, metaInfo);
    }

    @Override
    public Object decorate(Object o)
    {
        introspect(o, DecoratedObjectFactory.getAssociatedInfo());
        return o;
    }

    @Override
    public void destroy(Object o)
    {

    }
}
