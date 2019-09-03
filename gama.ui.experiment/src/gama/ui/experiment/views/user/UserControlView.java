/*********************************************************************************************
 *
 * 'UserControlView.java, in plugin gama.ui.experiment.experiment, is part of the source code of the GAMA modeling and
 * simulation platform. (c) 2007-2016 UMI 209 UMMISCO IRD/UPMC & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and developers contact.
 *
 *
 **********************************************************************************************/
package gama.ui.experiment.views.user;

import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.ToolItem;

import gama.ui.base.controls.FlatButton;
import gama.ui.base.parameters.EditorFactory;
import gama.ui.base.resources.GamaColors;
import gama.ui.base.resources.GamaIcons;
import gama.ui.base.resources.IGamaColors;
import gama.ui.base.resources.IGamaIcons;
import gama.ui.base.resources.GamaColors.GamaUIColor;
import gama.ui.base.utils.WorkbenchHelper;
import gama.ui.base.views.GamaViewPart;
import gama.ui.base.views.toolbar.GamaToolbar2;
import gama.common.interfaces.IStatement;
import gama.common.interfaces.gui.IGamaView;
import gama.runtime.GAMA;
import gama.runtime.scope.IScope;
import gaml.architecture.user.UserInputStatement;
import gaml.architecture.user.UserPanelStatement;
import gaml.statements.UserCommandStatement;

public class UserControlView extends GamaViewPart implements IGamaView.User {

	IScope scope;
	UserPanelStatement panel;
	private Composite body;
	ToolItem inspectItem, continueItem;

	@Override
	public void initFor(final IScope scope, final UserPanelStatement panel) {
		this.panel = panel;
		this.scope = scope;

		if (body != null && !body.isDisposed()) {
			body.dispose();
			body = null;
		}

		ownCreatePartControl(getParentComposite());
		getParentComposite().layout();
	}

	private void deactivate(final Composite parent) {
		for (final Control c : parent.getChildren()) {
			if (c instanceof Composite) {
				deactivate((Composite) c);
			} else {
				c.setEnabled(false);
			}
		}
	}

	@Override
	public void ownCreatePartControl(final Composite parent) {
		parent.setBackground(IGamaColors.WHITE.color());
		if (scope == null)
			return;
		inspectItem.setEnabled(true);
		continueItem.setEnabled(true);
		setPartName(
				"[" + scope.getAgent().getName() + " in " + scope.getSimulation().getName() + "] " + panel.getName());
		parent.setLayout(new FillLayout());
		parent.setBackground(IGamaColors.WHITE.color());
		toolbar.status((Image) null,
				"User control, agent " + scope.getAgent().getName() + ", cycle " + scope.getClock().getCycle(),
				IGamaColors.NEUTRAL, SWT.LEFT);
		body = new Composite(parent, SWT.None);
		GridLayout layout = new GridLayout(3, false);
		body.setLayout(layout);
		body.setBackground(IGamaColors.WHITE.color());
		for (final IStatement statement : panel.getUserCommands()) {
			if (statement instanceof UserCommandStatement) {
				final UserCommandStatement c = (UserCommandStatement) statement;
				final Composite commandComposite = new Composite(body, SWT.BORDER);
				final GridData data = new GridData(SWT.FILL, SWT.FILL, true, false, 3, 1);
				commandComposite.setLayoutData(data);
				layout = new GridLayout(3, false);
				commandComposite.setLayout(layout);
				commandComposite.setBackground(IGamaColors.WHITE.color());
				final List<UserInputStatement> inputs = c.getInputs();
				final int nbLines = inputs.size() > 1 ? inputs.size() : 1;
				final int nbCol = inputs.size() > 0 ? 1 : 3;
				GamaUIColor color = GamaColors.get(c.getColor(scope));
				if (color == null) {
					color = IGamaColors.BLUE;
				}
				final Image image = GamaIcons.create(c.isContinue(scope) ? "small.continue" : "small.run").image();
				final FlatButton b = FlatButton.button(commandComposite, color, c.getName(), image);
				b.setEnabled(c.isEnabled(scope));
				final GridData gd = new GridData(SWT.LEFT, SWT.CENTER, true, true, nbCol, nbLines);
				b.setLayoutData(gd);
				b.addSelectionListener(new SelectionAdapter() {

					@Override
					public void widgetSelected(final SelectionEvent e) {
						scope.execute(c);
						GAMA.getExperiment().refreshAllOutputs();
						if (c.isContinue(scope)) {
							doContinue();
						}
					}

				});
				for (final UserInputStatement i : inputs) {
					scope.addVarWithValue(i.getTempVarName(), i.value(scope));
					EditorFactory.create(scope, commandComposite, i, newValue -> {
						i.setValue(scope, newValue);
						scope.execute(i);
					}, false, false);
				}

			}
		}

	}

	protected void doContinue() {
		scope.setOnUserHold(false);
		deactivate(getParentComposite());
		WorkbenchHelper.hideView(this);
	}

	@Override
	public void widgetDisposed(final DisposeEvent e) {
		scope.setOnUserHold(false);
		super.widgetDisposed(e);
	}

	@Override
	protected GamaUIJob createUpdateJob() {
		return new GamaUIJob() {

			@Override
			protected UpdatePriority jobPriority() {
				return UpdatePriority.HIGH;
			}

			@Override
			public IStatus runInUIThread(final IProgressMonitor monitor) {
				initFor(scope, panel);
				return Status.OK_STATUS;
			}
		};
	}

	/**
	 * Method createToolItem()
	 *
	 * @see gama.ui.experiment.views.toolbar.IToolbarDecoratedView#createToolItem(int,
	 *      gama.ui.experiment.views.toolbar.GamaToolbar2)
	 */
	@Override
	public void createToolItems(final GamaToolbar2 tb) {
		super.createToolItems(tb);

		inspectItem = tb.button(IGamaIcons.PANEL_INSPECT, "Inspect", "Inspect",
				e -> scope.getGui().setSelectedAgent(scope.getAgent()), SWT.RIGHT);
		inspectItem.setEnabled(false);
		continueItem = tb.button(IGamaIcons.PANEL_CONTINUE, "Continue", "Continue", e -> doContinue(), SWT.RIGHT);
		continueItem.setEnabled(false);

	}

	@Override
	protected boolean needsOutput() {
		return false;
	}

}
