/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsnative;

import org.ebayopensource.dsf.jsnative.anno.Alias;
import org.ebayopensource.dsf.jsnative.anno.DOMSupport;
import org.ebayopensource.dsf.jsnative.anno.DomLevel;
import org.ebayopensource.dsf.jsnative.anno.JsMetatype;
import org.ebayopensource.dsf.jsnative.anno.Property;

/**
 * http://www.w3.org/TR/REC-html40/struct/text.html#edef-ins
 *
 */
@Alias("HTMLInsElement")
@DOMSupport(DomLevel.ONE)
@JsMetatype
public interface HtmlIns extends HtmlElement {
	
	@Property String getCite();
	@Property void setCite(String cite);

	@Property String getDateTime();
	@Property void setDateTime(String dateTime);

	/**
	 * Returns the onblur event handler code on the current element. 
	 * @see http://www.w3schools.com/jsref/jsref_onblur.asp
	 */
	@DOMSupport(DomLevel.ZERO)
	@Property(name="onblur")
	Object getOnBlur();
	
	/**
	 * Sets the onblur event handler code on the current element. 
	 * @param functionRef
	 * @see http://www.w3schools.com/jsref/jsref_onblur.asp
	 */
	@DOMSupport(DomLevel.ZERO)
	@Property(name="onblur")
	void setOnBlur(Object functionRef);
	
	/**
	 * Returns the onfocus event handler code on the current element.
	 * @see http://www.w3schools.com/jsref/jsref_onfocus.asp 
	 */
	@DOMSupport(DomLevel.ZERO)
	@Property(name="onfocus")
	Object getOnFocus();
	
	/**
	 * Sets the onfocus event handler code on the current element. 
	 * @param functionRef
	 * @see http://www.w3schools.com/jsref/jsref_onfocus.asp 
	 */
	@DOMSupport(DomLevel.ZERO)
	@Property(name="onfocus")
	void setOnFocus(Object functionRef);

}
