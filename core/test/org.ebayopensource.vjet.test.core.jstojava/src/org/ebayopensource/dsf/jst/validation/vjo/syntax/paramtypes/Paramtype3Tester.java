/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/* 
 * $Id: Paramtype1Tester.java, May 18, 2009, 9:51:16 PM, liama. Exp$
 *
 * Copyright (c) 2006-2009 Ebay Technologies. All Rights Reserved.
 * This software program and documentation are copyrighted by Ebay 
 * Technologies.
 */
package org.ebayopensource.dsf.jst.validation.vjo.syntax.paramtypes;




import java.util.List;

import org.ebayopensource.dsf.jsgen.shared.ids.MethodProbIds;
import org.ebayopensource.dsf.jsgen.shared.ids.TypeProbIds;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoSemanticProblem;
import org.ebayopensource.dsf.jst.validation.vjo.VjoValidationBaseTester;
import org.junit.Before;
import org.junit.Test;




/**
 * Indetify the parameter match test cases
 * 
 * @author <a href="mailto:liama@ebay.com">liama</a>
 * @since JDK 1.5
 */
//@ModuleInfo(value="DsfPrebuild",subModuleId="JsToJava")
//@Category( { P3, FAST, UNIT })
public class Paramtype3Tester extends VjoValidationBaseTester {

    @Before
    public void setUp() {
        expectProblems.clear();
        expectProblems.add(createNewProblem(TypeProbIds.TypeMismatch, 15, 0));
        expectProblems.add(createNewProblem(MethodProbIds.ParameterMismatch,
                20, 0));
        expectProblems.add(createNewProblem(MethodProbIds.ParameterMismatch,
                27, 0));
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test parameter type mismatch")
    public void testIfstatement1() {
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "syntax.paramtypes/", "Employee4.js", this.getClass());
        assertProblemEquals(expectProblems, problems);
    }
}
