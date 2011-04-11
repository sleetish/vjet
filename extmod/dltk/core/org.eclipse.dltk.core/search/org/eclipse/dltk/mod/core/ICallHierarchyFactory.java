/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
package org.eclipse.dltk.mod.core;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.dltk.mod.core.search.IDLTKSearchScope;

public interface ICallHierarchyFactory {
	ICalleeProcessor createCalleeProcessor(IMethod method, IProgressMonitor monitor, IDLTKSearchScope scope); //to ext point
	ICallProcessor createCallProcessor(); // to ext point
}
