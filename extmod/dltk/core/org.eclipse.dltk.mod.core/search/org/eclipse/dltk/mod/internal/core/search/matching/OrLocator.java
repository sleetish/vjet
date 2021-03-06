/*******************************************************************************
 * Copyright (c) 2000, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
package org.eclipse.dltk.mod.internal.core.search.matching;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.mod.ast.ASTNode;
import org.eclipse.dltk.mod.ast.declarations.FieldDeclaration;
import org.eclipse.dltk.mod.ast.declarations.MethodDeclaration;
import org.eclipse.dltk.mod.ast.declarations.TypeDeclaration;
import org.eclipse.dltk.mod.ast.expressions.Expression;
import org.eclipse.dltk.mod.ast.references.Reference;
import org.eclipse.dltk.mod.ast.references.TypeReference;
import org.eclipse.dltk.mod.compiler.env.lookup.Scope;
import org.eclipse.dltk.mod.core.IModelElement;
import org.eclipse.dltk.mod.core.search.SearchMatch;
import org.eclipse.dltk.mod.core.search.SearchPattern;
import org.eclipse.dltk.mod.core.search.matching.MatchLocator;
import org.eclipse.dltk.mod.core.search.matching.PatternLocator;

public class OrLocator extends PatternLocator {

	protected PatternLocator[] patternLocators;

	public OrLocator(OrPattern pattern) {
		super(pattern);

		SearchPattern[] patterns = pattern.patterns;
		int length = patterns.length;
		this.patternLocators = new PatternLocator[length];
		for (int i = 0; i < length; i++)
			this.patternLocators[i] = PatternLocator
					.patternLocator(patterns[i], null);
	}

	public void initializePolymorphicSearch(MatchLocator locator) {
		for (int i = 0, length = this.patternLocators.length; i < length; i++)
			this.patternLocators[i].initializePolymorphicSearch(locator);
	}

	public int match(ASTNode node, MatchingNodeSet nodeSet) {
		int level = IMPOSSIBLE_MATCH;
		for (int i = 0, length = this.patternLocators.length; i < length; i++) {
			int newLevel = this.patternLocators[i].match(node, nodeSet);
			if (newLevel > level) {
				if (newLevel == ACCURATE_MATCH)
					return ACCURATE_MATCH;
				level = newLevel;
			}
		}
		return level;
	}

	public int match(Expression node, MatchingNodeSet nodeSet) {
		int level = IMPOSSIBLE_MATCH;
		for (int i = 0, length = this.patternLocators.length; i < length; i++) {
			int newLevel = this.patternLocators[i].match(node, nodeSet);
			if (newLevel > level) {
				if (newLevel == ACCURATE_MATCH)
					return ACCURATE_MATCH;
				level = newLevel;
			}
		}
		return level;
	}

	public int match(FieldDeclaration node, MatchingNodeSet nodeSet) {
		int level = IMPOSSIBLE_MATCH;
		for (int i = 0, length = this.patternLocators.length; i < length; i++) {
			int newLevel = this.patternLocators[i].match(node, nodeSet);
			if (newLevel > level) {
				if (newLevel == ACCURATE_MATCH)
					return ACCURATE_MATCH;
				level = newLevel;
			}
		}
		return level;
	}

	public int match(MethodDeclaration node, MatchingNodeSet nodeSet) {
		int level = IMPOSSIBLE_MATCH;
		for (int i = 0, length = this.patternLocators.length; i < length; i++) {
			int newLevel = this.patternLocators[i].match(node, nodeSet);
			if (newLevel > level) {
				if (newLevel == ACCURATE_MATCH)
					return ACCURATE_MATCH;
				level = newLevel;
			}
		}
		return level;
	}

	public int match(Reference node, MatchingNodeSet nodeSet) {
		int level = IMPOSSIBLE_MATCH;
		for (int i = 0, length = this.patternLocators.length; i < length; i++) {
			int newLevel = this.patternLocators[i].match(node, nodeSet);
			if (newLevel > level) {
				if (newLevel == ACCURATE_MATCH)
					return ACCURATE_MATCH;
				level = newLevel;
			}
		}
		return level;
	}

	public int match(TypeDeclaration node, MatchingNodeSet nodeSet) {
		int level = IMPOSSIBLE_MATCH;
		for (int i = 0, length = this.patternLocators.length; i < length; i++) {
			int newLevel = this.patternLocators[i].match(node, nodeSet);
			if (newLevel > level) {
				if (newLevel == ACCURATE_MATCH)
					return ACCURATE_MATCH;
				level = newLevel;
			}
		}
		return level;
	}

	public int match(TypeReference node, MatchingNodeSet nodeSet) {
		int level = IMPOSSIBLE_MATCH;
		for (int i = 0, length = this.patternLocators.length; i < length; i++) {
			int newLevel = this.patternLocators[i].match(node, nodeSet);
			if (newLevel > level) {
				if (newLevel == ACCURATE_MATCH)
					return ACCURATE_MATCH;
				level = newLevel;
			}
		}
		return level;
	}
	
	public void matchReportReference(ASTNode reference, IModelElement element,
			Scope scope, int accuracy, MatchLocator locator)
			throws CoreException {
		for (int i = 0, length = this.patternLocators.length; i < length; i++) {
			this.patternLocators[i].matchReportReference(reference, element,
					scope, accuracy, locator);
		}
	}

	public int matchContainer() {
		int result = 0;
		for (int i = 0, length = this.patternLocators.length; i < length; i++)
			result |= this.patternLocators[i].matchContainer();
		return result;
	}


	public SearchMatch newDeclarationMatch(ASTNode reference,
			IModelElement element, int accuracy,
			MatchLocator locator) {
		PatternLocator closestPattern = null;
		int level = ACCURATE_MATCH;
		for (int i = 0, pl = this.patternLocators.length; i < pl; i++) {
			PatternLocator patternLocator = this.patternLocators[i];
			int newLevel = patternLocator.resolveLevel(reference);
			if (newLevel > level) {
				closestPattern = patternLocator;
				if (newLevel == ACCURATE_MATCH)
					break;
				level = newLevel;
			}
		}
		if (closestPattern != null) {
			return closestPattern.newDeclarationMatch(reference, element,
					accuracy, locator);
		}
		// super implementation...
		return locator.newDeclarationMatch(element, accuracy,
				reference.matchStart(), reference.matchLength());
	}

	public int resolveLevel(ASTNode node) {
		int level = IMPOSSIBLE_MATCH;
		for (int i = 0, length = this.patternLocators.length; i < length; i++) {
			int newLevel = this.patternLocators[i].resolveLevel(node);
			if (newLevel > level) {
				if (newLevel == ACCURATE_MATCH)
					return ACCURATE_MATCH;
				level = newLevel; // want to answer the stronger match
			}
		}
		return level;
	}
}
