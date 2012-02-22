/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.translator;

import java.util.List;

import org.ebayopensource.dsf.jsgen.shared.ids.ScopeIds;
import org.ebayopensource.dsf.jst.declaration.JstCache;
import org.ebayopensource.dsf.jst.declaration.JstFunctionRefType;
import org.ebayopensource.dsf.jst.declaration.JstMethod;
import org.ebayopensource.dsf.jst.declaration.JstModifiers;
import org.ebayopensource.dsf.jst.declaration.JstObjectLiteralType;
import org.ebayopensource.dsf.jst.declaration.JstPackage;
import org.ebayopensource.dsf.jst.declaration.JstProperty;
import org.ebayopensource.dsf.jst.declaration.JstType;
import org.ebayopensource.dsf.jst.declaration.JstTypeReference;
import org.ebayopensource.dsf.jst.expr.FuncExpr;
import org.ebayopensource.dsf.jst.term.JstLiteral;
import org.ebayopensource.dsf.jst.term.NV;
import org.ebayopensource.dsf.jst.term.ObjLiteral;
import org.ebayopensource.dsf.jst.token.IExpr;
import org.ebayopensource.dsf.jst.traversal.JstDepthFirstTraversal;
import org.ebayopensource.dsf.jst.ts.util.JstPrettyPrintVisitor;
import org.ebayopensource.dsf.jstojava.parser.comments.IJsCommentMeta;
import org.ebayopensource.dsf.jstojava.translator.robust.ast2jst.BaseAst2JstTranslator;
import org.ebayopensource.dsf.jstojava.translator.robust.ast2jst.TranslatorFactory;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.Expression;

public class DefsTranslator extends BasePropsProtosTranslator {
	public DefsTranslator(TranslateCtx ctx) {
		super(ctx);
		type = ScopeIds.DEFS;
	}

	@Override
	public void process(Expression expr, JstType jstType) {
		// TODO Auto-generated method stub
		super.process(expr, jstType);

		if (m_ctx.hasFunctionTypeRefReplacements()) {
			JstDepthFirstTraversal.accept(jstType, new FunctionTypeRefVisitor(
					m_ctx.getFunctionTypeRefReplacements()));
		}

	}

	protected void translateExpr(Expression expr, JstType jstType) {
		getCtx().enterBlock(type);
		try {
			BaseAst2JstTranslator translator;
			// if (expr instanceof ObjectLiteral) {
			// translator = new VjoOLTranslator(getCtx());
			// } else {
			// TODO - Throw error here...
			translator = TranslatorFactory.getTranslator(expr, getCtx());
			// }
			translator.setParent(jstType);
			Object node = translator.translate(expr);
			// post process Object Literal

			if (node instanceof ObjLiteral) {
				ObjLiteral literal = ((ObjLiteral) node);
				IndentedPrintStream ps = new IndentedPrintStream(System.out);
				JstDepthFirstTraversal.accept(literal,
						new JstPrettyPrintVisitor(ps));
				for (NV field : literal.getNVs()) {
					processDef(jstType, field);
					System.out.println(field);
				}
				System.out.println(node);
			}

		} finally {
			getCtx().exitBlock();
		}
	}

	private void processDef(JstType jstType, NV field) {

		if (field.getValue() != null) {

			if (field.getValue() instanceof ObjLiteral) {
				processObjLiteralDef(jstType, field.getName(),
						(ObjLiteral) field.getValue());
			}

			if (field.getValue() instanceof FuncExpr) {
				processFunctionDef(jstType,
						((FuncExpr) field.getValue()).getFunc());

			}
		}

	}

	// take the object literal and construct
	// JstObjLiteralType
	private void processObjLiteralDef(JstType jstType, String name,
			ObjLiteral value) {

		JstObjectLiteralType otype = new JstObjectLiteralType(name);
		otype.setPackage(new JstPackage(jstType.getName()));
		// TODO add source info
		JstCache.getInstance().addOType(otype);
		jstType.addProperty(new JstProperty(otype, name));
		for (NV field : value.getNVs()) {
			JstProperty prop = createPropertyFromNV(field);
			
			otype.addProperty(prop);
			if(field.isOptional()){
				otype.addOptionalField(prop);
			}
			// how do I know this is optional?
			System.out.println(prop);
			
		}
		jstType.addOType(otype);
	}

	private JstProperty createPropertyFromNV(NV nv){
		IExpr value = nv.getValue();
		if ((nv.getName() != null) && (nv.getName().length() > 0)
				&& (value instanceof JstLiteral)) {
			// create the JstProperty
			JstProperty jstProperty = new JstProperty(
					((JstLiteral) value).getResultType(), nv.getName(),
					(JstLiteral) value, new JstModifiers());
			jstProperty.setSource(nv.getSource());
			jstProperty.setComments(nv.getComments());
			return jstProperty;
		}
		return null;
	}
	
	private void processFunctionDef(JstType type, JstMethod mtd) {
		if (mtd != null) {
			type.addMethod(mtd);
			JstFunctionRefType ref = new JstFunctionRefType(mtd);
			ref.setPackage(new JstPackage(type.getName()));
			type.addOType(ref);
		}

	}

}
