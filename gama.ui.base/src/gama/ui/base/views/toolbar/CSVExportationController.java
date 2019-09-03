/*********************************************************************************************
 *
 * 'CSVExportationController.java, in plugin gama.ui.base.shared, is part of the source code of the GAMA modeling and
 * simulation platform. (c) 2007-2016 UMI 209 UMMISCO IRD/UPMC & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and developers contact.
 * 
 *
 **********************************************************************************************/
package gama.ui.base.views.toolbar;

import org.eclipse.swt.SWT;

import gama.ui.base.resources.GamaIcons;
import gama.ui.base.resources.IGamaIcons;

/**
 * Class ZoomController.
 *
 * @author drogoul
 * @since 9 févr. 2015
 *
 */
public class CSVExportationController {

	private final IToolbarDecoratedView.CSVExportable view;

	/**
	 * @param view
	 */
	public CSVExportationController(final IToolbarDecoratedView.CSVExportable view) {
		this.view = view;
	}

	/**
	 * @param tb
	 */
	public void install(final GamaToolbar2 tb) {
		tb.button(GamaIcons.create(IGamaIcons.DISPLAY_TOOLBAR_CSVEXPORT).getCode(), "CSV Export", "CSV Export",
				e -> view.saveAsCSV(), SWT.RIGHT);

	}

}
