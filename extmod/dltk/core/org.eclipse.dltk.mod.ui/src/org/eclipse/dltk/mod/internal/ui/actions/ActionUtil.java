/*******************************************************************************
 * Copyright (c) 2000-2011 IBM Corporation and others, eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     eBay Inc - modification
 *******************************************************************************/
package org.eclipse.dltk.mod.internal.ui.actions;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.dltk.mod.core.DLTKLanguageManager;
import org.eclipse.dltk.mod.core.IModelElement;
import org.eclipse.dltk.mod.core.IProjectFragment;
import org.eclipse.dltk.mod.core.IScriptFolder;
import org.eclipse.dltk.mod.core.IScriptProject;
import org.eclipse.dltk.mod.internal.core.ExternalScriptProject;
import org.eclipse.dltk.mod.internal.corext.util.Messages;
import org.eclipse.dltk.mod.internal.ui.editor.ScriptEditor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.ide.ResourceUtil;

/*
 * http://dev.eclipse.org/bugs/show_bug.cgi?id=19104
 */
public class ActionUtil {

	private ActionUtil() {
	}

	// bug 31998 we will have to disable renaming of linked packages (and cus)
	public static boolean mustDisableScriptModelAction(Shell shell,
			Object element) {
		if (!(element instanceof IScriptFolder)
				&& !(element instanceof IProjectFragment))
			return false;

		IResource resource = ResourceUtil.getResource(element);
		if ((resource == null) || (!(resource instanceof IFolder))
				|| (!resource.isLinked()))
			return false;

		MessageDialog.openInformation(shell,
				ActionMessages.ActionUtil_not_possible,
				ActionMessages.ActionUtil_no_linked);
		return true;
	}

	public static boolean isProcessable(Shell shell, ScriptEditor editor) {
		if (editor == null)
			return true;
		IModelElement input = SelectionConverter.getInput(editor);
		// if a Script editor doesn't have an input of type Script element
		// then it is for sure not on the build path
		if (input == null) {
			MessageDialog.openInformation(shell,
					ActionMessages.ActionUtil_notOnBuildPath_title,
					ActionMessages.ActionUtil_notOnBuildPath_message);
			return false;
		}
		return isProcessable(shell, input);
	}

	public static boolean isProcessable(Shell shell, Object element) {
		if (!(element instanceof IModelElement))
			return true;

		if (isOnBuildPath((IModelElement) element))
			return true;
		MessageDialog.openInformation(shell,
				ActionMessages.ActionUtil_notOnBuildPath_title,
				ActionMessages.ActionUtil_notOnBuildPath_message);
		return false;
	}

	public static boolean isOnBuildPath(IModelElement element) {
		// fix for bug http://dev.eclipse.org/bugs/show_bug.cgi?id=20051
		if (element.getElementType() == IModelElement.SCRIPT_PROJECT)
			return true;
		IScriptProject project = element.getScriptProject();
		// ebay Modify, if project is native or external. Always return true.
		if (project.getElementName().equals("Native project")) {
			return true;
		}

		if (project instanceof ExternalScriptProject) {
			return true;
		}

		if (!project.isOnBuildpath(element))
			return false;
		IProject resourceProject = project.getProject();
		if (resourceProject == null)
			return false;
		if (DLTKLanguageManager.hasScriptNature(project.getProject())) {
			return true;
		}
		return false;
	}

	public static boolean isSearchAble(IModelElement element) {
		// Jack: For native type, they are not in workspace, but it can support
		// search operation

		// disable, this feature have not been implemented, hide it
		// if (element.getElementType() == IModelElement.SCRIPT_PROJECT)
		// return true;
		// IScriptProject project = element.getScriptProject();
		// if (project != null && project.isOnBuildpath(element))
		// return true;

		return false;
	}

	public static boolean areProcessable(Shell shell, IModelElement[] elements) {
		for (int i = 0; i < elements.length; i++) {
			if (!isOnBuildPath(elements[i])) {
				MessageDialog
						.openInformation(
								shell,
								ActionMessages.ActionUtil_notOnBuildPath_title,
								Messages.format(
										ActionMessages.ActionUtil_notOnBuildPath_resource_message,
										new Object[] { elements[i].getPath() }));
				return false;
			}
		}
		return true;
	}
}
