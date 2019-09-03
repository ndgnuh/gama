/*******************************************************************************************************
 *
 * gaml.compilation.IGamlAdditions.java, in plugin gama.core, is part of the source code of the GAMA modeling
 * and simulation platform (v. 1.8)
 *
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package gaml.compilation.interfaces;

import java.util.Arrays;
import java.util.Collections;

import gama.common.interfaces.IAgent;
import gama.common.interfaces.IContainer;
import gama.common.interfaces.IKeyword;
import gama.kernel.experiment.ExperimentAgent;
import gama.kernel.root.PlatformAgent;
import gama.kernel.simulation.SimulationAgent;
import gama.metamodel.agent.GamlAgent;
import gama.metamodel.shape.GamaPoint;
import gama.metamodel.shape.GamaShape;
import gama.metamodel.shape.IShape;
import gama.metamodel.topology.ITopology;
import gama.runtime.scope.IScope;
import gama.util.GamaColor;
import gama.util.GamaDate;
import gama.util.GamaPair;
import gama.util.file.IGamaFile;
import gama.util.graph.IGraph;
import gama.util.list.IList;
import gama.util.map.IMap;
import gama.util.matrix.IMatrix;
import gama.util.path.IPath;
import gaml.descriptions.IDescription;
import gaml.expressions.IExpression;
import gaml.operators.DeprecatedOperators;
import gaml.skills.GridSkill;
import gaml.skills.MovingSkill;
import gaml.species.ISpecies;

public interface IGamlAdditions {

	public static class Children {

		private final Iterable<IDescription> children;

		public Children(final IDescription... descs) {
			if (descs == null || descs.length == 0) {
				children = Collections.emptyList();
			} else {
				children = Arrays.asList(descs);
			}
		}

		public Iterable<IDescription> getChildren() {
			return children;
		}

	}

	int[] AI = new int[0];
	String[] AS = new String[0];
	boolean F = false;
	boolean T = true;
	String PRIM = IKeyword.PRIMITIVE;
	Class<?> IA = IAgent.class;
	Class<?> IT = ITopology.class;
	Class<?> SP = ISpecies.class;
	Class<?> GA = GamlAgent.class;
	Class<?> GC = GamaColor.class;
	Class<?> GP = GamaPair.class;
	Class<?> GS = GamaShape.class;
	Class<?> O = Object.class;
	Class<?> B = Boolean.class;
	Class<?> I = Integer.class;
	Class<?> D = Double.class;
	Class<?> S = String.class;
	Class<?> IE = IExpression.class;
	Class<?> IS = IShape.class;
	Class<?> GM = IMap.class;
	// public final static Class<?> GL = GamaList.class;
	Class<?> P = GamaPoint.class;
	Class<?> IC = IContainer.class;
	Class<?> IL = GamaPoint.class;
	Class<?> LI = IList.class;
	Class<?> IM = IMatrix.class;
	Class<?> GR = IGraph.class;
	Class<?> IP = IPath.class;
	Class<?> GF = IGamaFile.class;
	Class<?> MSK = MovingSkill.class;
	Class<?> GSK = GridSkill.class;
	Class<?> SC = IScope.class;
	Class<?> GD = GamaDate.class;
	Class<?> SA = SimulationAgent.class;
	Class<?> EA = ExperimentAgent.class;
	Class<?> DO = DeprecatedOperators.class;
	Class<?> PA = PlatformAgent.class;
	Class<?> i = int.class;
	Class<?> d = double.class;
	Class<?> b = boolean.class;

	void initialize() throws SecurityException, NoSuchMethodException;

}
