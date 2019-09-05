/*********************************************************************************************
 *
 * 'MonitorView.java, in plugin gama.ui.experiment.experiment, is part of the source code of the GAMA modeling and
 * simulation platform. (c) 2007-2016 UMI 209 UMMISCO IRD/UPMC & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and developers contact.
 *
 *
 **********************************************************************************************/
package gama.ui.experiment.views.inspectors;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import gama.core.outputs.MonitorOutput;
import gama.core.outputs.ValuedDisplayOutputFactory;
import gama.ui.base.parameters.EditorFactory;
import gama.ui.base.resources.GamaColors;
import gama.ui.base.resources.IGamaColors;
import gama.ui.base.resources.IGamaIcons;
import gama.ui.base.utils.WorkbenchHelper;
import gama.ui.base.views.ExpandableItemsView;
import gama.ui.base.views.toolbar.GamaToolbar2;
import gama.ui.base.views.toolbar.GamaToolbarFactory;
import gama.ui.base.views.toolbar.IToolbarDecoratedView;
import gama.common.interfaces.IAgent;
import gama.common.interfaces.IValue;
import gama.common.interfaces.ItemList;
import gama.common.interfaces.outputs.IDisplayOutput;
import gama.common.util.TextBuilder;
import gama.runtime.scope.IScope;
import gama.util.GamaColor;
import gaml.GAML;
import gaml.expressions.IExpression;
import gaml.expressions.IExpressionFactory;
import gaml.types.IType;
import gaml.types.Types;

/**
 * @author Alexis Drogoul
 */
@SuppressWarnings ({ "rawtypes", "unchecked" })
public class MonitorView extends ExpandableItemsView<MonitorOutput> implements IToolbarDecoratedView.Pausable {

	private static int count = 0;

	@Override
	public void ownCreatePartControl(final Composite parent) {
		displayItems();
	}

	@Override
	protected boolean needsOutput() {
		return false;
	}

	@Override
	public void addOutput(final IDisplayOutput output) {
		super.addOutput(output);
		addItem((MonitorOutput) output);
	}

	@Override
	public boolean addItem(final MonitorOutput output) {
		if (output != null) {
			createItem(getParentComposite(), output, output.getValue() == null,
					output.getColor() == null ? null : GamaColors.get(output.getColor()));
			// getViewer().setSize(getViewer().computeSize(SWT.DEFAULT,
			// SWT.DEFAULT, true));
			return true;
		}
		return false;

	}

	@Override
	protected Composite createItemContentsFor(final MonitorOutput output) {
		final Composite compo = new Composite(getViewer(), SWT.NONE);
		compo.setBackground(IGamaColors.WHITE.color());
		final GridLayout layout = new GridLayout(2, false);
		// GridData firstColData = new GridData(SWT.FILL, SWT.FILL, true,
		// false);
		// firstColData.widthHint = 60;
		// GridData secondColData = new GridData(SWT.FILL, SWT.FILL, true,
		// false);
		// secondColData.widthHint = 200;
		layout.verticalSpacing = 5;
		compo.setLayout(layout);
		final Text titleEditor =
				(Text) EditorFactory.create(output.getScope(), compo, "Title:", output.getName(), true, newValue -> {
					output.setName(newValue);
					update(output);
				}).getEditor();

		final IExpression expr =
				GAML.compileExpression(output.getExpressionText(), output.getScope().getSimulation(), true);

		final Text c = (Text) EditorFactory.createExpression(output.getScope(), compo, "Expression:",
				output.getValue() == null ? IExpressionFactory.NIL_EXPR : expr, newValue -> {
					output.setNewExpression(newValue);
					update(output);
				}, Types.NO_TYPE).getEditor();

		c.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(final SelectionEvent e) {}

			@Override
			public void widgetDefaultSelected(final SelectionEvent e) {
				getViewer().collapseItemWithData(output);
			}

		});
		titleEditor.addModifyListener(evt -> {
			output.setName(titleEditor.getText());
			update(output);
		});
		// outputs.add(output);
		// update(output);
		return compo;
	}

	@Override
	public void removeItem(final MonitorOutput o) {
		o.close();
		removeOutput(o);
	}

	@Override
	public void resumeItem(final MonitorOutput o) {
		if (o.isPaused()) {
			o.setPaused(false);
		}
		update(o);
	}

	@Override
	public void pauseItem(final MonitorOutput o) {
		o.setPaused(true);
		update(o);
	}

	@Override
	public String getItemDisplayName(final MonitorOutput o, final String previousName) {
		try (TextBuilder sb = TextBuilder.create()) {
			sb.append(o.getName()).append(ItemList.SEPARATION_CODE).append(getValueAsString(o));
			if (o.isPaused()) {
				sb.append(" (paused)");
			}
			return sb.toString();
		}
	}

	public String getValueAsString(final MonitorOutput o) {
		final Object v = o.getLastValue();
		return v == null ? "nil" : v instanceof IValue ? ((IValue) v).serialize(true) : v.toString();
	}

	@Override
	public GamaColor getItemDisplayColor(final MonitorOutput o) {
		return o.getColor();
	}

	@SuppressWarnings ("unused")
	public static void createNewMonitor(final IScope scope) {
		// TODO ADD the possibility to do it in several simulations
		new MonitorOutput(scope, "monitor" + count++, "");
	}

	// @Override
	// public void dispose() {
	// reset();
	// super.dispose();
	// }

	@Override
	public void reset() {
		disposeViewer();
		outputs.clear();
	}

	//
	// @Override
	// public void update(final IDisplayOutput displayOutput) {
	// final MonitorOutput output = (MonitorOutput) displayOutput;
	// if ( !outputs.contains(output) ) { return; }
	// super.update(output);
	// }

	@Override
	public void focusItem(final MonitorOutput data) {
		outputs.remove(data);
		outputs.add(0, data);
	}

	@Override
	protected boolean areItemsClosable() {
		return true;
	}

	@Override
	protected boolean areItemsPausable() {
		return true;
	}

	@Override
	public List getItems() {
		return outputs;
	}

	@Override
	public void updateItemValues() {}

	@Override
	public void createToolItems(final GamaToolbar2 tb) {
		super.createToolItems(tb);
		tb.sep(GamaToolbarFactory.TOOLBAR_SEP, SWT.RIGHT);
		tb.button(IGamaIcons.MENU_ADD_MONITOR, "Add new monitor", "Add new monitor",
				e -> createNewMonitor(getOutput().getScope()), SWT.RIGHT);
	}

	// @Override
	// public void outputReloaded(final IDisplayOutput output) {
	//
	// }

	/**
	 * Method pauseChanged()
	 *
	 * @see gama.ui.experiment.views.toolbar.IToolbarDecoratedView.Pausable#pauseChanged()
	 */
	@Override
	public void pauseChanged() {}

	/**
	 * Method synchronizeChanged()
	 *
	 * @see gama.ui.experiment.views.toolbar.IToolbarDecoratedView.Pausable#synchronizeChanged()
	 */
	@Override
	public void synchronizeChanged() {}

	/**
	 * Method handleMenu()
	 *
	 * @see msi.gama.common.interfaces.ItemList#handleMenu(java.lang.Object, int, int)
	 */
	@Override
	public Map<String, Runnable> handleMenu(final MonitorOutput data, final int x, final int y) {
		final Map<String, Runnable> menu = new HashMap();
		final IExpression exp = data.getValue();
		if (exp == null)
			return null;
		final IType<?> type = exp.getGamlType();
		menu.put("Copy to clipboard", () -> {
			WorkbenchHelper.copy(getValueAsString(data));
		});
		if (type.isNumber() || type.isContainer() && type.getContentType().isNumber()) {
			// menu.put("Open chart", () -> {});
			menu.put("Save as CSV", () -> {
				data.saveHistory();
			});
		} else if (type.isAgentType()) {
			menu.put("Inspect", () -> {
				data.getScope().getGui().setSelectedAgent((IAgent) data.getLastValue());
			});
		} else if (type.isContainer() && type.getContentType().isAgentType()) {
			menu.put("Browse", () -> {
				ValuedDisplayOutputFactory.browse((Collection<? extends IAgent>) data.getLastValue());
			});
		}
		return menu;
	}

}
