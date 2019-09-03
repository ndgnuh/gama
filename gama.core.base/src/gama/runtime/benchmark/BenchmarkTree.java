/*******************************************************************************************************
 *
 * gama.runtime.benchmark.BenchmarkTree.java, in plugin gama.core,
 * is part of the source code of the GAMA modeling and simulation platform (v. 1.8)
 * 
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 * 
 ********************************************************************************************************/
package gama.runtime.benchmark;

import gama.common.interfaces.IBenchmarkable;
import gama.util.tree.GamaNode;
import gama.util.tree.GamaTree;
import gaml.descriptions.ExperimentDescription;
import gaml.descriptions.IDescription;
import gaml.expressions.IExpression;
import gaml.expressions.IOperator;

public class BenchmarkTree extends GamaTree<IBenchmarkable> {

	public BenchmarkTree(final IDescription model, final ExperimentDescription focusedExperiment) {
		setRoot(new GamaNode<>(model, 0));
		build(model, focusedExperiment, getRoot(), 1);
	}

	private void build(final IDescription desc, final ExperimentDescription focusedExperiment,
			final GamaNode<IBenchmarkable> node, final int level) {
		desc.visitFacets((name, exp) -> {
			final IExpression expr = exp.getExpression();
			if (expr instanceof IOperator) {
				final IOperator op = (IOperator) expr;
				final GamaNode<IBenchmarkable> newNode = node.addChild(new GamaNode<>(op, level));
				build(op, newNode, level + 1);
			}
			return true;
		});
		desc.visitOwnChildren((d) -> {
			if (d instanceof ExperimentDescription && !d.equals(focusedExperiment)) { return true; }
			final GamaNode<IBenchmarkable> newNode = node.addChild(new GamaNode<>(d, level));
			build(d, focusedExperiment, newNode, level + 1);
			return true;
		});
	}

	private void build(final IOperator op, final GamaNode<IBenchmarkable> currentNode, final int level) {
		op.visitSuboperators((o) -> {
			final GamaNode<IBenchmarkable> node = currentNode.addChild(new GamaNode<>(o, level));
			build(o, node, level + 1);
		});

	}

}
