/*********************************************************************************************
 *
 *
 * 'UnifyDoc.java', in plugin 'msi.gama.documentation', is part of the source code of the GAMA modeling and simulation
 * platform. (c) 2007-2014 UMI 209 UMMISCO IRD/UPMC & Partners
 *
 * Visit https://code.google.com/p/gama-platform/ for license information and developers contact.
 *
 *
 **********************************************************************************************/
package msi.gama.doc.util;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import gama.processor.annotations.IOperatorCategory;
import gama.processor.annotations.doc.Constants;
import gama.processor.annotations.doc.XMLElements;

public class UnifyDoc {
	public static final Map<String, String> categories = new HashMap<>();

	static {
		categories.put("Cast", IOperatorCategory.CASTING);
		categories.put("Colors", IOperatorCategory.COLOR);
		categories.put("DrivingOperators", IOperatorCategory.DRIVING);
		categories.put("Comparison", IOperatorCategory.COMPARISON);
		categories.put("IContainer", IOperatorCategory.CONTAINER);
		categories.put("Containers", IOperatorCategory.CONTAINER);
		categories.put("GamaMap", IOperatorCategory.CONTAINER);
		categories.put("IMap", IOperatorCategory.CONTAINER);
		categories.put("Files", IOperatorCategory.FILE);
		categories.put("GamaFileType", IOperatorCategory.FILE);
		categories.put("MessageType", IOperatorCategory.FIPA);
		categories.put("ConversationType", IOperatorCategory.FIPA);
		categories.put("Graphs", IOperatorCategory.GRAPH);
		categories.put("GraphsGraphstream", IOperatorCategory.GRAPH);
		categories.put("Logic", IOperatorCategory.LOGIC);
		categories.put("Maths", IOperatorCategory.ARITHMETIC);
		categories.put("GamaFloatMatrix", IOperatorCategory.MATRIX);
		categories.put("GamaIntMatrix", IOperatorCategory.MATRIX);
		categories.put("GamaMatrix", IOperatorCategory.MATRIX);
		categories.put("GamaObjectMatrix", IOperatorCategory.MATRIX);
		categories.put("IMatrix", IOperatorCategory.MATRIX);
		categories.put("SingleEquationStatement", IOperatorCategory.EDP);
		categories.put("Creation", IOperatorCategory.SPATIAL);
		categories.put("Operators", IOperatorCategory.SPATIAL);
		categories.put("Points", IOperatorCategory.SPATIAL);
		categories.put("Properties", IOperatorCategory.SPATIAL);
		categories.put("Punctal", IOperatorCategory.SPATIAL);
		categories.put("Queries", IOperatorCategory.SPATIAL);
		categories.put("ThreeD", IOperatorCategory.SPATIAL);
		categories.put("Statistics", IOperatorCategory.SPATIAL);
		categories.put("Transformations", IOperatorCategory.SPATIAL);
		categories.put("Relations", IOperatorCategory.SPATIAL);
		categories.put("Random", IOperatorCategory.RANDOM);
		categories.put("Stats", IOperatorCategory.STATISTICAL);
		categories.put("Strings", IOperatorCategory.STRING);
		categories.put("System", IOperatorCategory.SYSTEM);
		categories.put("Types", IOperatorCategory.TYPE);
		categories.put("WaterLevel", IOperatorCategory.WATER);
	}

	private static String[] tabEltXML = { XMLElements.CONCEPT_LIST, XMLElements.OPERATORS_CATEGORIES,
			XMLElements.OPERATORS, XMLElements.SKILLS, XMLElements.ARCHITECTURES, XMLElements.SPECIESS,
			XMLElements.STATEMENTS, XMLElements.CONSTANTS_CATEGORIES, XMLElements.CONSTANTS,
			XMLElements.INSIDE_STAT_KINDS, XMLElements.INSIDE_STAT_SYMBOLS, XMLElements.STATEMENT_KINDS,
			XMLElements.TYPES, XMLElements.FILES };
	// among tebEltXML, categories do not need to have an additional projectName
	// attribute
	// private static String[] tabCategoriesEltXML = { XMLElements.OPERATORS_CATEGORIES,
	// XMLElements.CONSTANTS_CATEGORIES,
	// XMLElements.INSIDE_STAT_KINDS, XMLElements.INSIDE_STAT_SYMBOLS, XMLElements.STATEMENT_KINDS,
	// XMLElements.CONCEPT_LIST };

	public static void unify(final boolean local) {
		try {

			final WorkspaceManager ws = new WorkspaceManager(".", local);
			final HashMap<String, File> hmFiles = ws.getProductDocFiles();

			final Document doc = mergeFiles(hmFiles);

			System.out.println("" + hmFiles);

			final XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
			sortie.output(doc, new FileOutputStream(Constants.DOCGAMA_GLOBAL_FILE));
		} catch (final Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void unifyAllProjects(final boolean local) {
		try {

			final WorkspaceManager ws = new WorkspaceManager(".", local);
			final HashMap<String, File> hmFiles = local ? ws.getAllDocFilesLocal() : ws.getAllDocFiles();

			final Document doc = mergeFiles(hmFiles);

			System.out.println("" + hmFiles);

			final XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
			sortie.output(doc, new FileOutputStream(Constants.DOCGAMA_GLOBAL_FILE));
		} catch (final Exception ex) {
			ex.printStackTrace();
		}
	}

	private static Document mergeFiles(final HashMap<String, File> hmFilesPackages) {
		try {

			final SAXBuilder builder = new SAXBuilder();
			Document doc = null;

			doc = new Document(new Element(XMLElements.DOC));
			for (final String elt : tabEltXML) {
				doc.getRootElement().addContent(new Element(elt));
			}

			for (final Entry<String, File> fileDoc : hmFilesPackages.entrySet()) {
				final Document docTemp = builder.build(fileDoc.getValue());

				for (final String catXML : tabEltXML) {
					if (docTemp.getRootElement().getChild(catXML) != null) {

						final List<Element> existingElt = doc.getRootElement().getChild(catXML).getChildren();

						for (final Element e : docTemp.getRootElement().getChild(catXML).getChildren()) {
							// Do not add the projectName for every kinds of
							// categories
							// if (!Arrays.asList(tabCategoriesEltXML).contains(catXML)) {
							e.setAttribute("projectName", fileDoc.getKey());
							// }

							// Test whether the element is already in the merged
							// doc
							boolean found = false;
							for (final Element exElt : existingElt) {
								boolean equals = exElt.getName().equals(e.getName());
								for (final Attribute att : exElt.getAttributes()) {
									final String valueExElt = exElt.getAttribute(att.getName()) != null
											? exElt.getAttributeValue(att.getName()) : "";
									final String valueE = e.getAttribute(att.getName()) != null
											? e.getAttributeValue(att.getName()) : "";
									equals = equals && valueExElt.equals(valueE);
								}
								found = found || equals;
							}
							// Add if it is not already in the merged doc
							if (!found) {
								doc.getRootElement().getChild(catXML).addContent(e.clone());
							}
						}
					}
				}
			}

			// Add an element for the generated types
			doc.getRootElement().getChild(XMLElements.OPERATORS_CATEGORIES).addContent(
					new Element(XMLElements.CATEGORY).setAttribute(XMLElements.ATT_CAT_ID, getProperCategory("Types")));

			return doc;
		} catch (final Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public static String getProperCategory(final String rawName) {
		if (categories.containsKey(rawName))
			return categories.get(rawName);
		else
			return rawName;
	}

	public static void main(final String[] args) {
		try {
			UnifyDoc.unify(true);
		} catch (final Exception ex) {
			ex.printStackTrace();
		}

	}
}
