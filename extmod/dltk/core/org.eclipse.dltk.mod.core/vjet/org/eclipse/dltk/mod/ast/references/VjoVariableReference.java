package org.eclipse.dltk.mod.ast.references;

import org.eclipse.dltk.mod.ast.references.VariableKind;
import org.eclipse.dltk.mod.ast.references.VariableReference;

public class VjoVariableReference extends VariableReference {

	private String declaringTypeName;
	
	public VjoVariableReference(int start, int end, String name,
			VariableKind kind, String declaringTypeName) {
		super(start, end, name, kind);
		this.declaringTypeName = declaringTypeName;
	}

	public String getDeclaringTypeName() {
		return declaringTypeName;
	}
}
