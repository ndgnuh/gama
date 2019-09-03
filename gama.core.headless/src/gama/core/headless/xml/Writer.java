/*********************************************************************************************
 * 
 * 
 * 'Writer.java', in plugin 'gama.core.headless', is part of the source code of the
 * GAMA modeling and simulation platform.
 * (c) 2007-2014 UMI 209 UMMISCO IRD/UPMC & Partners
 * 
 * Visit https://code.google.com/p/gama-platform/ for license information and developers contact.
 * 
 * 
 **********************************************************************************************/
package gama.core.headless.xml;

import gama.core.headless.core.*;
import gama.core.headless.job.ExperimentJob;
import gama.core.headless.job.ExperimentJob.ListenedVariable;

public interface Writer {

	public void writeSimulationHeader(ExperimentJob s);

	public void writeResultStep(long step, ListenedVariable[] vars);

	public void close();
}
