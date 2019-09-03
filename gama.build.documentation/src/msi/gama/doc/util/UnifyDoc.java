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
import java.util.Map.Entry;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import gama.processor.annotations.doc.Constants;
import gama.processor.annotations.doc.TypeConverter;
import gama.processor.annotations.doc.XMLElements;

public class UnifyDoc {

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
			doc.getRootElement().getChild(XMLElements.OPERATORS_CATEGORIES).addContent(new Element(XMLElements.CATEGORY)
					.setAttribute(XMLElements.ATT_CAT_ID, new TypeConverter().getProperCategory("Types")));

			return doc;
		} catch (final Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public static void main(final String[] args) {
		try {
			UnifyDoc.unify(true);
		} catch (final Exception ex) {
			ex.printStackTrace();
		}

	}
}
