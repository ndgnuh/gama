/*******************************************************************************************************
 *
 * gama.ui.experiment.commands.CopyLayout.java, in plugin gama.ui.experiment.experiment, is part of the source code of the
 * GAMA modeling and simulation platform (v. 1.8)
 *
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package gama.ui.experiment.commands;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

import gama.ui.base.utils.PerspectiveHelper;
import gama.ui.base.utils.WorkbenchHelper;
import gama.common.util.TextBuilder;
import gama.util.tree.GamaNode;
import gama.util.tree.GamaTree;

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
