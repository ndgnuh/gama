package ummisco.gama.ui.commands;

import static org.eclipse.core.resources.ResourcesPlugin.getWorkspace;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.jobs.Job;

import msi.gama.common.interfaces.IGamaView;
import msi.gama.common.interfaces.IGui;
import msi.gama.common.preferences.GamaPreferences;
import msi.gama.kernel.experiment.IExperimentPlan;
import msi.gama.kernel.experiment.TestAgent;
import msi.gama.kernel.model.IModel;
import msi.gama.runtime.GAMA;
import msi.gama.runtime.IScope;
import msi.gaml.compilation.GAML;
import msi.gaml.descriptions.ModelDescription;
import msi.gaml.statements.test.CompoundSummary;
import msi.gaml.statements.test.TestExperimentSummary;
import msi.gaml.statements.test.WithTestSummary;
import one.util.streamex.StreamEx;
import ummisco.gama.ui.utils.SwtGui;
import ummisco.gama.ui.utils.WorkbenchHelper;

public class TestsRunner {

	public static CompoundSummary<TestExperimentSummary, ?> LAST_RUN;

	public static void start() {
		if (SwtGui.ALL_TESTS_RUNNING) { return; }

		LAST_RUN = new CompoundSummary<>();

		final IGui gui = GAMA.getRegularGui();
		final IScope scope = GAMA.getRuntimeScope();
		final IGamaView.Test testView = gui.openTestView(scope, true);
		final List<IFile> testFiles = StreamEx.of(getWorkspace().getRoot().getProjects())
				.filter(TestsRunner::isInteresting).flatCollection(p -> GAML.getAllGamaFilesInProject(p)).toList();
		final int size = testFiles.size();
		final int[] i = { 1 };

		Job.createSystem("All tests", (m) -> {
			for (final IFile file : testFiles) {
				if (testView != null) {
					testView.displayProgress(i[0]++, size);
				}
				final List<TestExperimentSummary> list = runTests(file);
				if (list != null) {
					LAST_RUN.addSummaries(list);
				}
			}
			gui.displayTestsResults(scope, LAST_RUN);
			SwtGui.ALL_TESTS_RUNNING = false;
			gui.endTestDisplay();
		}).schedule();

	}

	/**
	 * Run the tests declared in a model (or a file or an URI).
	 *
	 * @param object
	 * @return
	 */
	private static List<TestExperimentSummary> runTests(final Object object) {
		// final StringBuilder sb = new StringBuilder();
		final IModel model = GAML.findModelIn(object);
		if (model == null) { return null; }
		final List<String> testExpNames = ((ModelDescription) model.getDescription()).getExperimentNames().stream()
				.filter(e -> model.getExperiment(e).isTest()).collect(Collectors.toList());
		if (testExpNames.isEmpty()) { return null; }
		final List<TestExperimentSummary> result = new ArrayList<>();
		for (final String expName : testExpNames) {
			final IExperimentPlan exp = GAMA.runModel(model, expName, true);
			if (exp != null) {
				exp.setHeadless(true);
				final TestAgent agent = (TestAgent) exp.getAgent();
				exp.getController().getScheduler().paused = false;
				agent.step(agent.getScope());
				result.add(((WithTestSummary<TestExperimentSummary>) agent).getSummary());
				GAMA.closeExperiment(exp);
			}
		}
		return result;
	}

	private static boolean isInteresting(final IProject p) {
		if (p == null || !p.exists() || !p.isAccessible()) { return false; }
		// If it is contained in one of the built-in tests projects, return true
		try {
			if (p.getDescription().hasNature(WorkbenchHelper.TEST_NATURE)) { return true; }
		} catch (final CoreException e) {
			return false;
		}
		if (GamaPreferences.Runtime.USER_TESTS.getValue()) {
			// If it is not in user defined projects, return false
			try {
				if (p.getDescription().hasNature(WorkbenchHelper.BUILTIN_NATURE)) { return false; }
			} catch (final CoreException e) {
				return false;
			}
			// We try to find in the project a folder called 'tests'
			final IResource r = p.findMember("tests");
			if (r != null && r.exists() && r.isAccessible() && r.getType() == IResource.FOLDER) { return true; }
		}
		return false;
	}

}
