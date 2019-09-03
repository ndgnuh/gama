/*********************************************************************************************
 *
 * 'GamlOutlinePage.java, in plugin gama.ui.base.modeling, is part of the source code of the
 * GAMA modeling and simulation platform.
 * (c) 2007-2016 UMI 209 UMMISCO IRD/UPMC & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and developers contact.
 * 
 *
 **********************************************************************************************/
package gama.core.lang.ui.outline;

import org.eclipse.jface.action.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.*;
import org.eclipse.xtext.ui.editor.outline.impl.OutlinePage;

import gama.ui.base.views.toolbar.GamaToolbar2;
import gama.ui.base.views.toolbar.GamaToolbarFactory;
import gama.ui.base.views.toolbar.IToolbarDecoratedView;

/**
 * The class GamlOutlinePage.
 *
 * @author drogoul
 * @since 24 nov. 2014
 *
 */
public class GamlOutlinePage extends OutlinePage implements IToolbarDecoratedView {

	GamaToolbar2 toolbar;
	protected Composite intermediate;

	public GamlOutlinePage() {}

	@Override
	protected void configureActions() {
		super.configureActions();

		IToolBarManager tbm = getSite().getActionBars().getToolBarManager();
		toolbar.wipe(SWT.RIGHT, true);
		for ( IContributionItem item : tbm.getItems() ) {
			toolbar.item(item, SWT.RIGHT);
		}
		toolbar.refresh(true);
		tbm.removeAll();
		tbm.update(true);
	}

	@Override
	public Control getControl() {
		return intermediate;
	}

	@Override
	public void createControl(final Composite compo) {
		intermediate = new Composite(compo, SWT.NONE);
		Composite parent = GamaToolbarFactory.createToolbars(this, intermediate);
		super.createControl(parent);
	}

	@Override
	protected int getDefaultExpansionLevel() {
		return 2;
	}

	/**
	 * @see gama.ui.base.views.toolbar.IToolbarDecoratedView#createToolItem(int, gama.ui.base.views.toolbar.GamaToolbar2)
	 */
	@Override
	public void createToolItems(final GamaToolbar2 tb) {
		this.toolbar = tb;
	}
	//
	// @Override
	// public void setToogle(final Action toggle) {}

}
