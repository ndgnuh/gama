/*********************************************************************************************
 * 
 *
 * 'PointEditor.java', in plugin 'msi.gama.application', is part of the source code of the 
 * GAMA modeling and simulation platform.
 * (c) 2007-2014 UMI 209 UMMISCO IRD/UPMC & Partners
 * 
 * Visit https://code.google.com/p/gama-platform/ for license information and developers contact.
 * 
 * 
 **********************************************************************************************/
package msi.gama.gui.parameters;

import msi.gama.common.interfaces.EditorListener;
import msi.gama.common.util.StringUtils;
import msi.gama.kernel.experiment.IParameter;
import msi.gama.metamodel.agent.IAgent;
import msi.gama.metamodel.shape.GamaPoint;
import msi.gama.runtime.*;
import msi.gama.runtime.GAMA.InScope;
import msi.gaml.operators.Cast;
import msi.gaml.types.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class PointEditor extends AbstractEditor implements VerifyListener {

	private Text xText, yText, zText;
	private Composite pointEditor;

	PointEditor(final IParameter param) {
		super(param);
	}

	PointEditor(final IAgent agent, final IParameter param) {
		this(agent, param, null);
	}

	PointEditor(final IAgent agent, final IParameter param, final EditorListener l) {
		super(agent, param, l);
	}

	PointEditor(final Composite parent, final String title, final Object value,
		final EditorListener<GamaPoint> whenModified) {
		// Convenience method
		super(new InputParameter(title, value), whenModified);
		this.createComposite(parent);
	}

	@Override
	public Control createCustomParameterControl(final Composite comp) {
		pointEditor = new Composite(comp, SWT.NONE);
		final GridData pointEditorGridData = new GridData(GridData.FILL, GridData.CENTER, true, false);
		pointEditorGridData.widthHint = 100;
		pointEditor.setLayoutData(pointEditorGridData);
		final GridLayout pointEditorLayout = new GridLayout(3, true);
		pointEditorLayout.horizontalSpacing = 10;
		pointEditorLayout.verticalSpacing = 0;
		pointEditorLayout.marginHeight = 0;
		pointEditorLayout.marginWidth = 0;
		pointEditor.setLayout(pointEditorLayout);
		// x
		final Composite xComposite = new Composite(pointEditor, SWT.NONE);
		final GridLayout subCompositeLayout = new GridLayout(2, false);
		subCompositeLayout.marginHeight = 0;
		subCompositeLayout.marginWidth = 0;
		xComposite.setLayout(subCompositeLayout);
		final GridData subCompositeGridData = new GridData(GridData.FILL, GridData.CENTER, true, false);
		xComposite.setLayoutData(subCompositeGridData);
		final Label xLabel = new Label(xComposite, SWT.NONE);
		xLabel.setText("x");
		xText = new Text(xComposite, SWT.BORDER);
		final GridData textGridData = new GridData(GridData.FILL, GridData.CENTER, true, false);
		textGridData.widthHint = 30;
		xText.setLayoutData(textGridData);
		xText.setBackground(normal_bg);
		// y
		final Composite yComposite = new Composite(pointEditor, SWT.NONE);
		yComposite.setLayout(subCompositeLayout);
		yComposite.setLayoutData(subCompositeGridData);
		final Label yLabel = new Label(yComposite, SWT.NONE);
		yLabel.setText("y");
		yText = new Text(yComposite, SWT.BORDER);
		yText.setLayoutData(textGridData);
		yText.setBackground(normal_bg);
		// z
		final Composite zComposite = new Composite(pointEditor, SWT.NONE);
		zComposite.setLayout(subCompositeLayout);
		zComposite.setLayoutData(subCompositeGridData);
		final Label zLabel = new Label(zComposite, SWT.NONE);
		zLabel.setText("z");
		zText = new Text(zComposite, SWT.BORDER);
		zText.setLayoutData(textGridData);
		zText.setBackground(normal_bg);
		displayParameterValue();
		// all
		displayParameterValue();
		// xText.addMouseTrackListener(this);
		// yText.addMouseTrackListener(this);
		xText.addModifyListener(this);
		xText.addVerifyListener(this);
		yText.addModifyListener(this);
		yText.addVerifyListener(this);
		zText.addModifyListener(this);
		zText.addVerifyListener(this);
		return pointEditor;
	}

	@Override
	public void verifyText(final VerifyEvent event) {
		if ( internalModification ) { return; }
		char myChar = event.character;
		// Assume we don't allow it
		event.doit = Character.isDigit(myChar) || myChar == '\b' || myChar == '.';
	}

	@Override
	protected void displayParameterValue() {
		GamaPoint p = (GamaPoint) currentValue;
		xText.setText(currentValue == null ? "0" : StringUtils.toGaml(p.getX()));
		yText.setText(currentValue == null ? "0" : StringUtils.toGaml(p.getY()));
		zText.setText(currentValue == null ? "0" : StringUtils.toGaml(p.getZ()));
	}

	@Override
	public void modifyText(final ModifyEvent me) {
		if ( internalModification ) { return; }
		GAMA.run(new InScope.Void() {

			@Override
			public void process(final IScope scope) {
				modifyValue(new GamaPoint(Cast.asFloat(scope, xText.getText()), Cast.asFloat(scope, yText.getText()),
					Cast.asFloat(scope, zText.getText())));
			}
		});

	}

	@Override
	public Control getEditorControl() {
		return pointEditor;
	}

	@Override
	public IType getExpectedType() {
		return Types.get(IType.POINT);
	}

}
