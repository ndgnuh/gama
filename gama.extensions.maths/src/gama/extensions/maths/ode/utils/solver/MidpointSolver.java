/*********************************************************************************************
 *
 * 'MidpointSolver.java, in plugin gama.extensions.maths, is part of the source code of the
 * GAMA modeling and simulation platform.
 * (c) 2007-2016 UMI 209 UMMISCO IRD/UPMC & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and developers contact.
 * 
 *
 **********************************************************************************************/
package gama.extensions.maths.ode.utils.solver;

import org.apache.commons.math3.ode.nonstiff.MidpointIntegrator;

import msi.gama.util.list.IList;
import msi.gama.util.map.IMap;

public class MidpointSolver extends Solver {

	public MidpointSolver(final double step, final IMap<String, IList<Double>> integrated_val) {
		super(step, new MidpointIntegrator(step), integrated_val);
	}

}
