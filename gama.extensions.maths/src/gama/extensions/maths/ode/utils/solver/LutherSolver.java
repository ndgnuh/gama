/*********************************************************************************************
 *
 * 'LutherSolver.java, in plugin gama.extensions.maths, is part of the source code of the
 * GAMA modeling and simulation platform.
 * (c) 2007-2016 UMI 209 UMMISCO IRD/UPMC & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and developers contact.
 * 
 *
 **********************************************************************************************/
package gama.extensions.maths.ode.utils.solver;

import java.util.List;


import org.apache.commons.math3.ode.nonstiff.LutherIntegrator;

import msi.gama.util.list.IList;
import msi.gama.util.map.IMap;

public class LutherSolver extends Solver {

	public LutherSolver(final double step, final IMap<String, IList<Double>> integrated_val) {
		super(step, new LutherIntegrator(step), integrated_val);
	}

}
