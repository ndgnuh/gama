package msi.gama.headless.script;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.google.common.collect.Sets;

import msi.gama.common.interfaces.IKeyword;
import msi.gama.headless.core.GamaHeadlessException;
import msi.gama.headless.job.ExperimentJob;
import msi.gama.headless.job.IExperimentJob;
import msi.gama.headless.job.JobPlan;
import msi.gama.headless.job.Output;
import msi.gama.headless.job.Parameter;
import msi.gama.headless.util.WorkspaceManager;
import msi.gama.headless.xml.XmlTAG;
import msi.gama.kernel.experiment.ExperimentAgent;
import msi.gama.kernel.experiment.ExperimentParameter;
import msi.gama.kernel.experiment.ExperimentPlan;
import msi.gama.kernel.experiment.IExperimentPlan;
import msi.gama.kernel.experiment.IParameter;
import msi.gama.kernel.experiment.ParametersSet;
import msi.gaml.descriptions.ExperimentDescription;
import msi.gaml.descriptions.IDescription;
import msi.gaml.types.IType;
import ummisco.gama.dev.utils.DEBUG;

public class ExperimentationPlanFactory {

	public static String DEFAULT_HEADLESS_DIRECTORY_IN_WORKSPACE = ".headless";
	public static String DEFAULT_MODEL_DIRECTORY_IN_WORKSPACE = "models";
	public static long DEFAULT_SEED = 1l;
	public static long DEFAULT_FINAL_STEP = 1000;

	public static void analyseWorkspace(final String directoryPath)
			throws IOException, ParserConfigurationException, TransformerException {
		final ArrayList<String> modelFileNames =
				WorkspaceManager.readDirectory(directoryPath + "./" + DEFAULT_MODEL_DIRECTORY_IN_WORKSPACE);
		for (final String nm : modelFileNames) {
			analyseModelInWorkspace(new File(nm));
		}

	}

	public static void analyseModelInWorkspace(final File modelFile)
			throws IOException, ParserConfigurationException, TransformerException {
		final String headlessDirectory = modelFile.getParentFile().getParentFile().getAbsolutePath() + "/"
				+ DEFAULT_HEADLESS_DIRECTORY_IN_WORKSPACE;
		final String outFileName = modelFile.getName().substring(0, modelFile.getName().lastIndexOf('.'));
		final File storeDirectory = new File(headlessDirectory);
		if (!storeDirectory.exists()) {
			storeDirectory.mkdirs();
		}
		final File outFile = new File(headlessDirectory + "/" + outFileName + ".xml");
		final File outerrFile = new File(headlessDirectory + "/err_" + outFileName + ".log");

		try (final OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(outFile));
				final OutputStreamWriter outErr = new OutputStreamWriter(new FileOutputStream(outerrFile));) {
			analyseModel(modelFile.getAbsolutePath(), out, outErr);

			/*
			 * JobPlan jb = new JobPlan(); List<IExperimentJob> generatedExperiment; try {
			 * jb.loadModelAndCompileJob(modelFile.getAbsolutePath()); long[] seed = {DEFAULT_FINAL_STEP};
			 * generatedExperiment = jb.constructAllJobs(seed,DEFAULT_FINAL_STEP);
			 * 
			 * 
			 * Document dd =ExperimentationPlanFactory.buildXmlDocument(generatedExperiment); TransformerFactory
			 * transformerFactory = TransformerFactory.newInstance(); Transformer transformer =
			 * transformerFactory.newTransformer(); DOMSource source = new DOMSource(dd); StreamResult result = new
			 * StreamResult(new File("/tmp/file.xml")); transformer.transform(source, result);
			 * 
			 * //generatedExperiment = jb.loadModelAndCompileJob(modelFile.getAbsolutePath()); }catch(Exception e) {
			 * outErr.write("Error in file : " + modelFile.getAbsolutePath()); }
			 */

		}

	}
	//
	// public static void analyseModelsDirectoryForValidation(final String modelFileName, final OutputStreamWriter
	// output,
	// final OutputStreamWriter err) throws IOException, TransformerException {
	// final List<String> allFiles = ScriptFactory.getModelsInDirectory(modelFileName);
	// for (final String fPath : allFiles) {
	// analyseModelsDirectoryForValidation(modelFileName, output, err);
	// }
	//
	// }

	public static void analyseModel(final String modelFileName, final OutputStreamWriter output,
			final OutputStreamWriter err) throws IOException, TransformerException {
		final JobPlan jb = new JobPlan();
		try {
			jb.loadModelAndCompileJob(modelFileName);
		} catch (final Exception e) {
			err.write("Error building plan: " + modelFileName);
		}
		final long[] seeds = { DEFAULT_SEED };
		final List<IExperimentJob> jobs = jb.constructAllJobs(seeds, DEFAULT_FINAL_STEP);
		Document dd;
		try {
			dd = buildXmlDocument(jobs);
		} catch (final ParserConfigurationException e) {
			err.write("Error building xml: " + modelFileName);
			return;
		}
		final TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer;
		transformer = transformerFactory.newTransformer();
		final DOMSource source = new DOMSource(dd);
		final StreamResult result = new StreamResult(output);
		transformer.transform(source, result);

	}

	public static List<IExperimentJob> buildExperiment(final String modelFileName)
			throws IOException, GamaHeadlessException {
		final JobPlan jb = new JobPlan();
		jb.loadModelAndCompileJob(modelFileName);
		final long[] seeds = { DEFAULT_SEED };
		final List<IExperimentJob> jobs = jb.constructAllJobs(seeds, DEFAULT_FINAL_STEP);
		return jobs;
	}

	public static Document buildXmlDocument(final List<IExperimentJob> jobs) throws ParserConfigurationException {
		
		final DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		final DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		final Document doc = docBuilder.newDocument();
		final Element rootElement = doc.createElement(XmlTAG.EXPERIMENT_PLAN_TAG);
		doc.appendChild(rootElement);

		for (final IExperimentJob job : jobs) {
			final Element jb = job.asXMLDocument(doc);
			rootElement.appendChild(jb);
		}

		return doc;
	}

	public static Document buildXmlDocumentForModelLibrary(final List<IExperimentJob> jobs)
			throws ParserConfigurationException {
		// this class will be executed if "buildModelLibrary" is turn to true. (automatic generation for the website)
		final DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		final DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		final Document doc = docBuilder.newDocument();
		final Element rootElement = doc.createElement(XmlTAG.EXPERIMENT_PLAN_TAG);
		doc.appendChild(rootElement);

		for (int i = 0; i < jobs.size(); i++) {
			final IExperimentJob job = jobs.get(i);

			final Element jb = job.asXMLDocument(doc);
			// make sure the pathSeparator is correct
			final String modelPath = jb.getAttribute(XmlTAG.SOURCE_PATH_TAG).replace("\\", "/");
			// add the character pathSeparator at the beginning of the string.
			jb.setAttribute(XmlTAG.SOURCE_PATH_TAG, "/" + jb.getAttribute(XmlTAG.SOURCE_PATH_TAG));
			// set the final step to 11
			jb.setAttribute(XmlTAG.FINAL_STEP_TAG, "11");

			final Node outputRoot = jb.getElementsByTagName("Outputs").item(0);
			final NodeList outputs = outputRoot.getChildNodes();
			for (int outputId = 0; outputId < outputs.getLength(); outputId++) {
				// add the attribute "output_path" with the path : path + name_of_display
				final Element output = (Element) outputs.item(outputId);
				final String outputName = output.getAttribute(XmlTAG.NAME_TAG);
				output.setAttribute(XmlTAG.OUTPUT_PATH,
						modelPath.substring(0, modelPath.length() - 5) + "/" + outputName);
				// set the framerate to 10
				output.setAttribute(XmlTAG.FRAMERATE_TAG, "10");
			}
			rootElement.appendChild(jb);
		}

		return doc;
	}
	
	/**
	 * Build XML headless representation of an {@link IExperimentPlan}. It is a biased representation of
	 * experiment plan, because it does not take into account the exploration method, but only keep the
	 * exhaustive form of parameter space: it means there is one headless simulation per point in the 
	 * parameter space defined by the {@link IParameter.Batch}
	 * 
	 * @param plan
	 * @return An XML document that describe the experiment
	 * @throws ParserConfigurationException
	 */
	public static Document buildRichXmlDocument(final IExperimentPlan plan) throws ParserConfigurationException {
		
		final DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		final DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		final Document doc = docBuilder.newDocument();
		final Element rootElement = doc.createElement(XmlTAG.EXPERIMENT_PLAN_TAG);
		doc.appendChild(rootElement);
		
		ExperimentDescription expD = plan.getDescription();
		
		ExperimentJob ej = ExperimentJob.loadAndBuildJob(expD, plan.getModel().getFilePath());
		
		if(expD.getFacet(IKeyword.TYPE).getExpression().literalValue().equals(IKeyword.BATCH)) {
			
			List<IParameter> params = new ArrayList<>();
			final Iterable<IDescription> parameters = expD.getChildrenWithKeyword(IKeyword.PARAMETER);
			for (final IDescription para : parameters) { params.add(new ExperimentParameter(para)); }
			
			Set<List<Object>> parameterSpace = Sets.cartesianProduct(params.stream()
						.map(bParam -> parameterValueVector(bParam, plan))
						.collect(Collectors.toList())
					);
			
			int xpID = 1;
			for (List<Object> pSpacePoint : parameterSpace) {
				
				Map<IParameter,Object> ps = params.stream().collect(Collectors.toMap(
												Function.identity(), 
												p -> pSpacePoint.get(params.indexOf(p))));
				
				rootElement.appendChild(writeXMLSimulationElement(doc, plan, 
						xpID++, ps, ej.getOutputs(), ej.finalStep, ej.getSeed()));
			}
		} else {
			rootElement.appendChild(ej.asXMLDocument(doc));
		}
		
		return doc;
	}
	
	@SuppressWarnings("unchecked")
	private static Set<Object> parameterValueVector(IParameter parameter, IExperimentPlan exp) {
		Set<Object> vector = new HashSet<>();
		if (parameter.getAmongValue(exp.getExperimentScope()) != null) {
			vector.addAll(parameter.getAmongValue(exp.getExperimentScope()));
		} else {
			double varValue = parameter.getMinValue(exp.getExperimentScope()).doubleValue();
			while (varValue <= parameter.getMaxValue(exp.getExperimentScope()).doubleValue()) {
				if (parameter.getType().id() == IType.INT) {
					vector.add((int)varValue);
				} else if (parameter.getType().id() == IType.FLOAT) {
					vector.add(varValue);
				} else {
					continue;
				}
				varValue += parameter.getStepValue(exp.getExperimentScope()).doubleValue();
			}
		}
		DEBUG.LOG("["+ExperimentationPlanFactory.class.getCanonicalName()+"] "
				+ "Parameter "+parameter.getTitle()+" = "+vector.stream().map(Object::toString).collect(Collectors.joining(";")));
		return vector;
	}
	
	/**
	 * Write an XML Element that correspond to one {@link ParametersSet} (i.e. a simulation input parameter set) of an {@link IExperimentPlan}
	 * 
	 * @param doc
	 * @param experiment
	 * @param xpID
	 * @param params
	 * @param path
	 * @param finalStep
	 * @param seed
	 * @return
	 */
	private static Element writeXMLSimulationElement(Document doc, IExperimentPlan experiment,
			int xpID, Map<IParameter,Object> params, List<Output> outputs, double finalStep, double seed) {
		final Element simulation = doc.createElement(XmlTAG.SIMULATION_TAG);

		final Attr attr = doc.createAttribute(XmlTAG.ID_TAG);
		attr.setValue(Integer.toString(xpID));
		simulation.setAttributeNode(attr);

		final Attr attr3 = doc.createAttribute(XmlTAG.SOURCE_PATH_TAG);
		attr3.setValue(experiment.getModel().getFilePath());
		simulation.setAttributeNode(attr3);

		final Attr attr2 = doc.createAttribute(XmlTAG.FINAL_STEP_TAG);
		attr2.setValue(Double.toString(finalStep));
		simulation.setAttributeNode(attr2);

		final Attr attr5 = doc.createAttribute(XmlTAG.SEED_TAG);
		attr5.setValue(Double.toString(seed));
		simulation.setAttributeNode(attr5);

		final Attr attr4 = doc.createAttribute(XmlTAG.EXPERIMENT_NAME_TAG);
		attr4.setValue(experiment.getName());
		simulation.setAttributeNode(attr4);

		final Element parameters = doc.createElement(XmlTAG.PARAMETERS_TAG);
		simulation.appendChild(parameters);

		for (IParameter para : params.keySet()) {
			
			final Element aparameter = doc.createElement(XmlTAG.PARAMETER_TAG);
			parameters.appendChild(aparameter);

			final Attr ap1 = doc.createAttribute(XmlTAG.NAME_TAG);
			ap1.setValue(para.getTitle());
			aparameter.setAttributeNode(ap1);

			final Attr ap2 = doc.createAttribute(XmlTAG.VAR_TAG);
			ap2.setValue(para.getName());
			aparameter.setAttributeNode(ap2);

			final Attr ap3 = doc.createAttribute(XmlTAG.TYPE_TAG);
			
			ap3.setValue(para.getType().toString());
			aparameter.setAttributeNode(ap3);

			final Attr ap4 = doc.createAttribute(XmlTAG.VALUE_TAG);
			ap4.setValue(params.get(para).toString());
			aparameter.setAttributeNode(ap4);
		}

		final Element eOutputs = doc.createElement(XmlTAG.OUTPUTS_TAG);
		simulation.appendChild(eOutputs);

		for (final Output o : outputs) {
			final Element aOutput = doc.createElement(XmlTAG.OUTPUT_TAG);
			eOutputs.appendChild(aOutput);

			final Attr o3 = doc.createAttribute(XmlTAG.ID_TAG);
			o3.setValue(o.getId());
			aOutput.setAttributeNode(o3);

			final Attr o1 = doc.createAttribute(XmlTAG.NAME_TAG);
			o1.setValue(o.getName());
			aOutput.setAttributeNode(o1);

			final Attr o2 = doc.createAttribute(XmlTAG.FRAMERATE_TAG);
			o2.setValue(new Integer(o.getFrameRate()).toString());
			aOutput.setAttributeNode(o2);
		}
		return simulation;
	}

}
