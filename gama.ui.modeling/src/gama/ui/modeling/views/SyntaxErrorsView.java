/*********************************************************************************************
 *
 * 'SyntaxErrorsView.java, in plugin gama.ui.base.modeling, is part of the source code of the GAMA modeling and
 * simulation platform. (c) 2007-2016 UMI 209 UMMISCO IRD/UPMC & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and developers contact.
 *
 *
 **********************************************************************************************/
package gama.ui.modeling.views;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.expressions.EvaluationContext;
import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.ISources;
import org.eclipse.ui.internal.views.markers.ConfigureContentsDialogHandler;
import org.eclipse.ui.views.markers.MarkerSupportView;

import gama.ui.base.commands.TestsRunner;
import gama.ui.base.resources.IGamaColors;
import gama.ui.base.utils.WorkbenchHelper;
import gama.ui.base.views.toolbar.GamaToolbar2;
import gama.ui.base.views.toolbar.GamaToolbarFactory;
import gama.ui.base.views.toolbar.IToolbarDecoratedView;
import gama.common.interfaces.IPreferenceChangeListener.IPreferenceAfterChangeListener;
import gama.common.preferences.GamaPreferences;

public class SyntaxErrorsView extends MarkerSupportView
		implements IToolbarDecoratedView, IPreferenceAfterChangeListener<Boolean> {

	protected Composite parent;
	protected GamaToolbar2 toolbar;

	ToolItem warningAction, infoAction;

	public SyntaxErrorsView() {
		super("gama.core.gaml.ui.error.generator");
		GamaPreferences.Modeling.WARNINGS_ENABLED.addChangeListener(this);
		GamaPreferences.Modeling.INFO_ENABLED.addChangeListener(this);
	}

	@Override
	public void createPartControl(final Composite compo) {
		this.parent = GamaToolbarFactory.createToolbars(this, compo);
		super.createPartControl(parent);
	}

	@Override
	public void dispose() {
		super.dispose();
		GamaPreferences.Modeling.WARNINGS_ENABLED.removeChangeListener(this);
		GamaPreferences.Modeling.INFO_ENABLED.removeChangeListener(this);
	}

	/**
	 * @see msi.gama.common.interfaces.IPreferenceChangeListener#afterValueChange(java.lang.Object)
	 */
	@Override
	public void afterValueChange(final Boolean newValue) {
		build();
		checkActions();
	}

	void checkActions() {
		if (warningAction != null) {
			warningAction.setSelection(GamaPreferences.Modeling.WARNINGS_ENABLED.getValue());
		}
		if (infoAction != null) {
			infoAction.setSelection(GamaPreferences.Modeling.INFO_ENABLED.getValue());
		}
	}

	@Override
	protected void setContentDescription(final String description) {
		toolbar.status((Image) null, description, e -> openFilterDialog(), IGamaColors.BLUE, SWT.LEFT);
	}

	@Override
	public void createToolItems(final GamaToolbar2 tb) {
		this.toolbar = tb;

		warningAction = tb.check("build.warnings2", "", "Toggle display of warning markers", e -> {
			final boolean b = ((ToolItem) e.widget).getSelection();
			GamaPreferences.Modeling.WARNINGS_ENABLED.set(b).save();
		}, SWT.RIGHT);
		warningAction.setSelection(GamaPreferences.Modeling.WARNINGS_ENABLED.getValue());

		infoAction = tb.check("build.infos2", "", "Toggle display of information markers", e -> {
			final boolean b = ((ToolItem) e.widget).getSelection();
			GamaPreferences.Modeling.INFO_ENABLED.set(b).save();
		}, SWT.RIGHT);
		infoAction.setSelection(GamaPreferences.Modeling.INFO_ENABLED.getValue());

		tb.sep(GamaToolbarFactory.TOOLBAR_SEP, SWT.RIGHT);
		tb.button("build.all2", "", "Clean and validate all projects", e -> {
			build();
		}, SWT.RIGHT);

		tb.button("test.run2", "", "Run all tests", e -> TestsRunner.start(), SWT.RIGHT);

	}

	void openFilterDialog() {
		final IEvaluationContext ec = new EvaluationContext(null, this);
		ec.addVariable(ISources.ACTIVE_PART_NAME, this);
		final ExecutionEvent ev = new ExecutionEvent(null, new HashMap<>(), this, ec);
		new ConfigureContentsDialogHandler().execute(ev);
	}

	static void build() {
		final ProgressMonitorDialog dialog = new ProgressMonitorDialog(WorkbenchHelper.getShell());
		dialog.setBlockOnOpen(false);
		dialog.setCancelable(false);
		dialog.setOpenOnRun(true);
		try {
			dialog.run(true, false, monitor -> {
				try {
					ResourcesPlugin.getWorkspace().build(IncrementalProjectBuilder.CLEAN_BUILD, monitor);
				} catch (CoreException e) {
					e.printStackTrace();
				}
			});
		} catch (InvocationTargetException | InterruptedException e1) {
			e1.printStackTrace();
		}
	}
}
