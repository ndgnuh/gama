/*********************************************************************************************
 *
 * 'PluginsModelsFolder.java, in plugin gama.ui.base.navigator, is part of the source code of the GAMA modeling and
 * simulation platform. (c) 2007-2016 UMI 209 UMMISCO IRD/UPMC & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and developers contact.
 * 
 *
 **********************************************************************************************/
package gama.ui.navigator.navigator.contents;

import gama.ui.base.utils.WorkbenchHelper;
import gaml.statements.test.CompoundSummary;

public class TestModelsFolder extends TopLevelFolder {

	public TestModelsFolder(final NavigatorRoot root, final String name) {
		super(root, name, FOLDER_TEST, "navigator/folder.status.test", "Built-in tests", NEUTRAL,
				WorkbenchHelper.TEST_NATURE, Location.Tests);
	}

	@Override
	public void getSuffix(final StringBuilder sb) {
		final CompoundSummary<?, ?> summary = getManager().getTestsSummary();
		if (summary != null)
			sb.append(summary.getStringSummary());
		else {
			super.getSuffix(sb);
			sb.append(", not yet run");
		}

	}

}
