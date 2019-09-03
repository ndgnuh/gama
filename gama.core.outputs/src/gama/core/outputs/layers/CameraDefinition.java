/*******************************************************************************************************
 *
 * gama.core.outputs.layers.CameraDefinition.java, in plugin msi.gama.core, is part of the source code of the GAMA
 * modeling and simulation platform (v. 1.8)
 *
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package gama.core.outputs.layers;

import gama.processor.annotations.IConcept;
import gama.processor.annotations.ISymbolKind;
import gama.processor.annotations.GamlAnnotations.doc;
import gama.processor.annotations.GamlAnnotations.facet;
import gama.processor.annotations.GamlAnnotations.facets;
import gama.processor.annotations.GamlAnnotations.inside;
import gama.processor.annotations.GamlAnnotations.symbol;
import gama.common.interfaces.IKeyword;
import gama.metamodel.shape.GamaPoint;
import gama.runtime.exceptions.GamaRuntimeException;
import gama.runtime.scope.IScope;
import gaml.descriptions.IDescription;
import gaml.expressions.IExpression;
import gaml.operators.Cast;
import gaml.types.IType;

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
