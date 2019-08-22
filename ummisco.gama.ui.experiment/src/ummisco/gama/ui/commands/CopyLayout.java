/*******************************************************************************************************
 *
 * ummisco.gama.ui.commands.CopyLayout.java, in plugin ummisco.gama.ui.experiment, is part of the source code of the
 * GAMA modeling and simulation platform (v. 1.8)
 *
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package ummisco.gama.ui.commands;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

import msi.gama.common.util.TextBuilder;
import msi.gama.util.tree.GamaNode;
import msi.gama.util.tree.GamaTree;
import ummisco.gama.ui.utils.PerspectiveHelper;
import ummisco.gama.ui.utils.WorkbenchHelper;

public class CopyLayout extends AbstractHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final GamaTree<String> tree =
				new LayoutTreeConverter().convertCurrentLayout(ArrangeDisplayViews.listDisplayViews());
		if (tree == null)
			return this;
		final GamaNode<String> firstSash = tree.getRoot().getChildren().get(0);
		firstSash.setWeight(null);
		try (TextBuilder sb = TextBuilder.create()) {
			final PerspectiveHelper pp = PerspectiveHelper.getInstance();
			sb.append(" layout " + firstSash);
			if (pp.keepTabs() != null) {
				sb.append(" tabs:").append(pp.keepTabs());
			}
			if (pp.keepToolbars() != null) {
				sb.append(" toolbars:").append(pp.keepToolbars());
			}
			if (pp.keepControls() != null) {
				sb.append(" controls:").append(pp.keepControls());
			}
			sb.append(" editors: ").append(WorkbenchHelper.getPage().isEditorAreaVisible()).append(";");
			WorkbenchHelper.copy(sb.toString());
			tree.dispose();
			return this;
		}
	}

}
