/*******************************************************************************************************
 *
 * msi.gama.util.file.GamaSVGFile.java, in plugin msi.gama.core, is part of the source code of the GAMA modeling and
 * simulation platform (v. 1.8)
 *
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package gama.extensions.files;

import java.awt.Shape;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;

import org.locationtech.jts.awt.ShapeReader;
import org.locationtech.jts.geom.Geometry;

import gama.core.ext.svgsalamander.SVGCache;
import gama.core.ext.svgsalamander.SVGDiagram;
import gama.core.ext.svgsalamander.SVGUniverse;
import gama.processor.annotations.IConcept;
import gama.processor.annotations.GamlAnnotations.doc;
import gama.processor.annotations.GamlAnnotations.example;
import gama.processor.annotations.GamlAnnotations.file;
import gama.common.geometry.GeometryUtils;
import gama.common.geometry.Scaling3D;
import gama.metamodel.shape.GamaPoint;
import gama.metamodel.shape.GamaShape;
import gama.metamodel.shape.IShape;
import gama.runtime.exceptions.GamaRuntimeException;
import gama.runtime.scope.IScope;
import gama.util.list.GamaListFactory;
import gama.util.list.IList;
import gaml.types.IType;
import gaml.types.Types;

/**
 * Class GamaSVGFile. Only loads vector shapes right now (and none of the associated elements: textures, colors, fonts,
 * etc.)
 *
 * @author drogoul
 * @since 30 déc. 2013
 *
 */
@file (
		name = "svg",
		extensions = "svg",
		buffer_type = IType.LIST,
		buffer_content = IType.GEOMETRY,
		buffer_index = IType.INT,
		concept = { IConcept.SVG },
		doc = @doc ("Represents 2D geometries described in a SVG file. The internal representation is a list of geometries"))
public class GamaSVGFile extends GamaGeometryFile {

	Scaling3D size;

	@doc (
			value = "This file constructor allows to read a svg file",
			examples = { @example (
					value = "file f <-svg_file(\"file.svg\");",
					isExecutable = false) })
	public GamaSVGFile(final IScope scope, final String pathName) throws GamaRuntimeException {
		super(scope, pathName);
	}

	@doc (
			value = "This file constructor allows to read a svg file, specifying the size of the bounding box",
			examples = { @example (
					value = "file f <-svg_file(\"file.svg\", {10,10});",
					isExecutable = false) })
	public GamaSVGFile(final IScope scope, final String pathName, final GamaPoint size) throws GamaRuntimeException {
		super(scope, pathName);
		this.size = Scaling3D.of(size);
	}

	@Override
	protected IShape buildGeometry(final IScope scope) {
		return getBuffer().get(0);
	}

	@Override
	public IList<String> getAttributes(final IScope scope) {
		// TODO are there attributes ?
		return GamaListFactory.create(Types.STRING);
	}

	@Override
	protected void fillBuffer(final IScope scope) throws GamaRuntimeException {
		try (BufferedReader in = new BufferedReader(new FileReader(getFile(scope)))) {
			final SVGUniverse svg = SVGCache.getSVGUniverse();
			final URI uri = svg.loadSVG(in, getPath(scope));
			final SVGDiagram diagram = svg.getDiagram(uri);
			final Shape shape = diagram.getRoot().getShape();
			final Geometry geom = ShapeReader.read(shape, 1.0, GeometryUtils.GEOMETRY_FACTORY); // flatness
			// =
			// ??
			// We center and scale the shape in the same operation
			// final Envelope env = geom.getEnvelopeInternal();
			// GamaPoint translation = new GamaPoint(-env.getWidth() / 2,
			// -env.getHeight() / 2);
			final IShape gs = new GamaShape(null, geom, null, new GamaPoint(0, 0), size, true);
			// gs.setLocation(new GamaPoint(0, 0));
			// gs.setLocation(translation);
			// if ( size != null ) {
			// gs = Spatial.Transformations.scaled_to(scope, gs, size);
			// }
			setBuffer(GamaListFactory.wrap(Types.GEOMETRY, gs));
		} catch (final IOException e) {
			throw GamaRuntimeException.create(e, scope);
			// e.printStackTrace();
		}
	}

}
