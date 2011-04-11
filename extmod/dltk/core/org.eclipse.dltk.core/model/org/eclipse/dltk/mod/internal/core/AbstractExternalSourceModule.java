package org.eclipse.dltk.mod.internal.core;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.dltk.mod.compiler.problem.IProblemReporter;
import org.eclipse.dltk.mod.core.CompletionRequestor;
import org.eclipse.dltk.mod.core.DLTKContentTypeManager;
import org.eclipse.dltk.mod.core.DLTKLanguageManager;
import org.eclipse.dltk.mod.core.IDLTKLanguageToolkit;
import org.eclipse.dltk.mod.core.IExternalSourceModule;
import org.eclipse.dltk.mod.core.IModelElement;
import org.eclipse.dltk.mod.core.IModelStatus;
import org.eclipse.dltk.mod.core.IModelStatusConstants;
import org.eclipse.dltk.mod.core.IProblemRequestor;
import org.eclipse.dltk.mod.core.ISourceModule;
import org.eclipse.dltk.mod.core.ModelException;
import org.eclipse.dltk.mod.core.WorkingCopyOwner;

/**
 * Base class for all external source module representations.
 */
public abstract class AbstractExternalSourceModule extends AbstractSourceModule
		implements IExternalSourceModule, IStorage {

	protected AbstractExternalSourceModule(ModelElement parent, String name,
			WorkingCopyOwner owner) {
		this(parent, name, owner, true);
	}

	protected AbstractExternalSourceModule(ModelElement parent, String name,
			WorkingCopyOwner owner, boolean readOnly) {
		super(parent, name, owner, true);
	}

	/*
	 * @see org.eclipse.dltk.mod.core.ISourceModule#becomeWorkingCopy(org.eclipse.dltk.mod.core.IProblemRequestor,
	 *      org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void becomeWorkingCopy(IProblemRequestor problemRequestor,
			IProgressMonitor monitor) {
		// external, do nothing
	}

	/*
	 * @see org.eclipse.dltk.mod.core.ICodeAssist#codeComplete(int,
	 *      org.eclipse.dltk.mod.core.CompletionRequestor)
	 */
	public void codeComplete(int offset, CompletionRequestor requestor) {
		// external, do nothing
	}

	/*
	 * @see org.eclipse.dltk.mod.core.ICodeAssist#codeComplete(int,
	 *      org.eclipse.dltk.mod.core.CompletionRequestor,
	 *      org.eclipse.dltk.mod.core.WorkingCopyOwner)
	 */
	public void codeComplete(int offset, CompletionRequestor requestor,
			WorkingCopyOwner owner) {
		// external, do nothing
	}

	/*
	 * @see org.eclipse.dltk.mod.core.ISourceModule#commitWorkingCopy(boolean,
	 *      org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void commitWorkingCopy(boolean force, IProgressMonitor monitor)
			throws ModelException {
		throw new ModelException(new ModelStatus(
				IModelStatusConstants.INVALID_ELEMENT_TYPES, this));
	}

	/*
	 * @see org.eclipse.dltk.mod.core.ISourceManipulation#delete(boolean,
	 *      org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void delete(boolean force, IProgressMonitor monitor) {
		// external, do nothing
	}

	/*
	 * @see org.eclipse.dltk.mod.core.ISourceModule#discardWorkingCopy()
	 */
	public void discardWorkingCopy() {
		// external, do nothing
	}

	/*
	 * @see org.eclipse.core.runtime.PlatformObject#getAdapter(java.lang.Class)
	 */
	public Object getAdapter(Class adapter) {
		if (adapter == IStorage.class) {
			return this;
		}

		return super.getAdapter(adapter);
	}

	/*
	 * @see org.eclipse.dltk.mod.core.IModelElement#getResource()
	 */
	public IResource getResource() {
		return null;
	}

	/*
	 * @see org.eclipse.dltk.mod.core.ISourceModule#getWorkingCopy(org.eclipse.dltk.mod.core.WorkingCopyOwner,
	 *      org.eclipse.dltk.mod.core.IProblemRequestor,
	 *      org.eclipse.core.runtime.IProgressMonitor)
	 */
	public ISourceModule getWorkingCopy(WorkingCopyOwner workingCopyOwner,
			IProblemRequestor problemRequestor, IProgressMonitor monitor) {
		return this;
	}

	/*
	 * @see org.eclipse.dltk.mod.core.ISourceModule#isWorkingCopy()
	 */
	public boolean isWorkingCopy() {
		return true;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.dltk.mod.internal.core.AbstractSourceModule#hasBuffer()
	 */
	protected boolean hasBuffer() {
		return false;
	}

	/*
	 * @see org.eclipse.dltk.mod.internal.core.Openable#makeConsistent(org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void makeConsistent(IProgressMonitor monitor) throws ModelException {
		// makeConsistent(false/*don't create AST*/, 0, monitor);
		openWhenClosed(createElementInfo(), monitor);
	}

	/*
	 * @see org.eclipse.dltk.mod.core.ISourceManipulation#move(org.eclipse.dltk.mod.core.IModelElement,
	 *      org.eclipse.dltk.mod.core.IModelElement, java.lang.String, boolean,
	 *      org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void move(IModelElement container, IModelElement sibling,
			String rename, boolean replace, IProgressMonitor monitor)
			throws ModelException {
		// this may be slightly misleading to the user...
		copy(container, sibling, rename, replace, monitor);
	}

	/*
	 * @see org.eclipse.dltk.mod.core.ISourceModule#reconcile(boolean,
	 *      org.eclipse.dltk.mod.core.WorkingCopyOwner,
	 *      org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void reconcile(boolean forceProblemDetection,
			WorkingCopyOwner workingCopyOwner, IProgressMonitor monitor) {
		// external, do nothing
	}

	/*
	 * @see org.eclipse.dltk.mod.core.ISourceManipulation#rename(java.lang.String,
	 *      boolean, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void rename(String name, boolean replace, IProgressMonitor monitor) {
		// external, do nothing
	}

	/*
	 * @see org.eclipse.dltk.mod.internal.core.Openable#closing(java.lang.Object)
	 */
	protected void closing(Object info) {
		// lifetime of the working copy
	}

	/*
	 * @see org.eclipse.dltk.mod.internal.core.AbstractSourceModule#getProblemReporter(java.lang.String)
	 */
	protected IProblemReporter getProblemReporter(String natureId) {
		// external, no reporter required
		return null;
	}

	/*
	 * @see org.eclipse.dltk.mod.internal.core.AbstractSourceModule#validateSorceModule(org.eclipse.dltk.mod.core.IDLTKLanguageToolkit,
	 *      org.eclipse.core.resources.IResource)
	 */
	protected IStatus validateSorceModule(IDLTKLanguageToolkit toolkit,
			IResource resource) {
		// external, resource will always be null
		IPath path = getFullPath();
		if (toolkit == null) {
			toolkit = DLTKLanguageManager.findToolkit(path);
		}

		if (toolkit != null) {
			if (DLTKContentTypeManager.isValidFileNameForContentType(toolkit,
					path)) {
				return IModelStatus.VERIFIED_OK;
			}
		}

		return null;
	}
}
