package org.eclipse.dltk.mod.core.search.matching;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.dltk.mod.core.search.matching.messages"; //$NON-NLS-1$
	public static String MatchLocator_languageToolkitNotFoundForProject;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
