/*******************************************************************************************************
 *
 * gaml.compilation.ast.SyntacticFactory.java, in plugin gama.core, is part of the source code of the GAMA modeling and
 * simulation platform (v. 1.8)
 *
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package gama.core.lang.gaml.ast;

import static gama.common.interfaces.IKeyword.EXPERIMENT;
import static gama.common.interfaces.IKeyword.GRID;
import static gama.common.interfaces.IKeyword.MODEL;
import static gama.common.interfaces.IKeyword.SPECIES;

import org.eclipse.emf.ecore.EObject;

import gama.core.lang.gaml.ast.SyntacticModelElement.SyntacticExperimentModelElement;
import gaml.compilation.interfaces.ISyntacticElement;
import gaml.compilation.interfaces.ISyntacticFactory;
import gaml.statements.Facets;

/**
 * Class SyntacticFactory.
 *
 * @author drogoul
 * @since 9 sept. 2013
 *
 */
public class SyntacticFactory implements ISyntacticFactory {

	/**
	 * Creates a new Syntactic object.
	 *
	 * @param statement
	 *            the statement
	 * @return the syntactic model element
	 */
	@Override
	public SyntacticModelElement createSyntheticModel(final EObject statement) {
		return new SyntacticModelElement(SYNTHETIC_MODEL, null, statement, null);
	}

	/**
	 * Creates a new Syntactic object.
	 *
	 * @param root
	 *            the root
	 * @param expObject
	 *            the exp object
	 * @param path
	 *            the path
	 * @return the syntactic experiment model element
	 */
	@Override
	public SyntacticExperimentModelElement createExperimentModel(final EObject root, final EObject expObject,
			final String path) {
		final SyntacticExperimentModelElement model = new SyntacticExperimentModelElement(EXPERIMENT_MODEL, root, path);
		final SyntacticExperimentElement exp = new SyntacticExperimentElement("experiment", null, expObject);
		model.addChild(exp);
		return model;
	}

	/**
	 * Creates the.
	 *
	 * @param keyword
	 *            the keyword
	 * @param statement
	 *            the statement
	 * @param withChildren
	 *            the with children
	 * @param data
	 *            the data
	 * @return the i syntactic element
	 */
	@Override
	public ISyntacticElement create(final String keyword, final EObject statement, final boolean withChildren,
			final Object... data) {
		return create(keyword, null, statement, withChildren, data);
	}

	/**
	 * Creates the.
	 *
	 * @param keyword
	 *            the keyword
	 * @param facets
	 *            the facets
	 * @param withChildren
	 *            the with children
	 * @param data
	 *            the data
	 * @return the i syntactic element
	 */
	@Override
	public ISyntacticElement create(final String keyword, final Facets facets, final boolean withChildren,
			final Object... data) {
		return create(keyword, facets, null, withChildren, data);
	}

	/**
	 * Creates the.
	 *
	 * @param keyword
	 *            the keyword
	 * @param facets
	 *            the facets
	 * @param statement
	 *            the statement
	 * @param withChildren
	 *            the with children
	 * @param data
	 *            the data
	 * @return the i syntactic element
	 */
	@Override
	public ISyntacticElement create(final String keyword, final Facets facets, final EObject statement,
			final boolean withChildren, final Object... data) {
		if (keyword.equals(MODEL)) {
			if (data.length > 0)
				return new SyntacticModelElement(keyword, facets, statement, (String) data[0], data);
			else
				return new SyntacticModelElement(keyword, facets, statement, null);
		} else if (keyword.equals(SPECIES) || keyword.equals(GRID))
			return new SyntacticSpeciesElement(keyword, facets, statement);
		else if (keyword.equals(EXPERIMENT))
			return new SyntacticExperimentElement(keyword, facets, statement);
		if (!withChildren)
			return new SyntacticSingleElement(keyword, facets, statement);
		return new SyntacticComposedElement(keyword, facets, statement);
	}

	/**
	 * Creates a new Syntactic object.
	 *
	 * @param keyword
	 *            the keyword
	 * @param name
	 *            the name
	 * @param stm
	 *            the stm
	 * @return the i syntactic element
	 */
	@Override
	public ISyntacticElement createVar(final String keyword, final String name, final EObject stm) {
		return new SyntacticAttributeElement(keyword, name, stm);
	}
}

// TODO for content assist
// Build a scope accessible by EObjects that contain variables and actions names
// in the syntactic structure
// A global scope can also be built for built-in elements (and attached to the
// local scopes if we can detect things like
// skills, etc.)
// The scope could be attached to resources (like the syntactic elements) and
// become accessible from content assist to
// return possible candidates