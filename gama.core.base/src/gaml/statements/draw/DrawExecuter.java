/*******************************************************************************************************
 *
 * gaml.statements.draw.DrawExecuter.java, in plugin gama.core,
 * is part of the source code of the GAMA modeling and simulation platform (v. 1.8)
 * 
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 * 
 ********************************************************************************************************/
package gaml.statements.draw;

import java.awt.geom.Rectangle2D;

import gama.common.interfaces.outputs.IGraphics;
import gama.runtime.exceptions.GamaRuntimeException;
import gama.runtime.scope.IScope;
import gaml.expressions.IExpression;

abstract class DrawExecuter {

	final IExpression item;

	DrawExecuter(final IExpression item) {
		this.item = item.isConst() ? null : item;
	}

	abstract Rectangle2D executeOn(IScope agent, IGraphics g, DrawingData data) throws GamaRuntimeException;

}