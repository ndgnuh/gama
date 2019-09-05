package gaml.compilation.interfaces;

import org.eclipse.emf.ecore.EObject;

import gaml.statements.Facets;

public interface ISyntacticFactory {

	/**
	 * The Constant SPECIES_VAR.
	 */
	String SPECIES_VAR = "species_var";
	/**
	 * The Constant SYNTHETIC_MODEL.
	 */
	String SYNTHETIC_MODEL = "synthetic_model";
	/**
	 * The Constant EXPERIMENT_MODEL.
	 */
	String EXPERIMENT_MODEL = "experiment_model";

	/**
	 * Creates a new Syntactic object.
	 *
	 * @param statement
	 *            the statement
	 * @return the syntactic model element
	 */
	ISyntacticElement createSyntheticModel(EObject statement);

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
	ISyntacticElement createExperimentModel(EObject root, EObject expObject, String path);

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
	ISyntacticElement create(String keyword, EObject statement, boolean withChildren, Object... data);

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
	ISyntacticElement create(String keyword, Facets facets, boolean withChildren, Object... data);

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
	ISyntacticElement create(String keyword, Facets facets, EObject statement, boolean withChildren, Object... data);

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
	ISyntacticElement createVar(String keyword, String name, EObject stm);

}