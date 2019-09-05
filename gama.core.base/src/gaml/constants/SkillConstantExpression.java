/*******************************************************************************************************
 *
 * gaml.expressions.SkillConstantExpression.java, in plugin gama.core, is part of the source code of the GAMA
 * modeling and simulation platform (v. 1.8)
 *
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package gaml.constants;

import gama.common.interfaces.ISkill;
import gaml.compilation.kernel.GamaSkillRegistry;
import gaml.expressions.IExpression;
import gaml.types.IType;

public class SkillConstantExpression extends ConstantExpression<ISkill> {

	public SkillConstantExpression(final String val, final IType<ISkill> t) {
		super(GamaSkillRegistry.INSTANCE.getSkillInstanceFor(val), t);
	}

	/**
	 * @see gaml.expressions.IExpression#getDocumentation()
	 */
	@Override
	public String getDocumentation() {
		return value.getDocumentation();
	}

	@Override
	public String getTitle() {
		return value.getTitle();
	}

	@Override
	public String literalValue() {
		return value.getName();
	}

}
