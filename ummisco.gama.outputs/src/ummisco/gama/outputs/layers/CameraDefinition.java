/*******************************************************************************************************
 *
 * ummisco.gama.outputs.layers.CameraDefinition.java, in plugin msi.gama.core, is part of the source code of the GAMA
 * modeling and simulation platform (v. 1.8)
 *
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package ummisco.gama.outputs.layers;

import msi.gama.common.interfaces.IKeyword;
import msi.gama.metamodel.shape.GamaPoint;
import msi.gama.runtime.exceptions.GamaRuntimeException;
import msi.gama.runtime.scope.IScope;
import msi.gaml.descriptions.IDescription;
import msi.gaml.expressions.IExpression;
import msi.gaml.operators.Cast;
import msi.gaml.types.IType;
import ummisco.gama.processor.IConcept;
import ummisco.gama.processor.ISymbolKind;
import ummisco.gama.processor.GamlAnnotations.doc;
import ummisco.gama.processor.GamlAnnotations.facet;
import ummisco.gama.processor.GamlAnnotations.facets;
import ummisco.gama.processor.GamlAnnotations.inside;
import ummisco.gama.processor.GamlAnnotations.symbol;

@symbol (
		name = IKeyword.CAMERA,
		kind = ISymbolKind.LAYER,
		with_sequence = false,
		unique_in_context = false,
		concept = { IConcept.CAMERA, IConcept.DISPLAY, IConcept.THREED })
@inside (
		symbols = IKeyword.DISPLAY)
@facets (
		value = { @facet (
				name = IKeyword.NAME,
				type = IType.STRING,
				optional = false,
				doc = @doc ("The name of the camera")),
				@facet (
						name = IKeyword.LOCATION,
						type = IType.POINT,
						optional = true,
						doc = @doc ("The location of the camera in the world")),
				@facet (
						name = IKeyword.LOOK_AT,
						type = IType.POINT,
						optional = true,
						doc = @doc ("The location that the camera is looking")),
				@facet (
						name = IKeyword.UP_VECTOR,
						type = IType.POINT,
						optional = true,
						doc = @doc ("The up-vector of the camera.")) },
		omissible = IKeyword.NAME)
@doc (
		value = "`" + IKeyword.CAMERA
				+ "` allows the modeler to define a camera. The display will then be able to choose among the camera defined (either within this statement or globally in GAMA) in a dynamic way. ",
		see = { IKeyword.DISPLAY, IKeyword.AGENTS, IKeyword.CHART, IKeyword.EVENT, "graphics", IKeyword.GRID_POPULATION,
				IKeyword.IMAGE, IKeyword.POPULATION, })
public class CameraDefinition extends AbstractLayerStatement {

	final IExpression locationExpr, lookAtExpr, upVectorExpr;
	GamaPoint location, lookAt, upVector;

	public CameraDefinition(final IDescription desc) throws GamaRuntimeException {
		super(desc);
		locationExpr = getFacet(IKeyword.LOCATION);
		lookAtExpr = getFacet(IKeyword.LOOK_AT);
		upVectorExpr = getFacet(IKeyword.UP_VECTOR);
	}

	@Override
	public LayerType getType(final boolean output) {
		return LayerType.OVERLAY;
	}

	@Override
	protected boolean _init(final IScope scope) {
		return true;
	}

	@Override
	protected boolean _step(final IScope scope) {
		location = locationExpr == null ? null : Cast.asPoint(scope, locationExpr.value(scope));
		lookAt = lookAtExpr == null ? null : Cast.asPoint(scope, lookAtExpr.value(scope));
		upVector = upVectorExpr == null ? null : Cast.asPoint(scope, upVectorExpr.value(scope));
		return true;
	}

}
