/*********************************************************************************************
 *
 * 'GamlQualifiedNameProvider.java, in plugin gama.core.lang, is part of the source code of the GAMA modeling and
 * simulation platform. (c) 2007-2016 UMI 209 UMMISCO IRD/UPMC & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and developers contact.
 *
 *
 **********************************************************************************************/
package gama.core.lang.gaml.naming;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;

import gama.common.interfaces.IKeyword;
import gama.core.lang.gaml.Access;
import gama.core.lang.gaml.ActionArguments;
import gama.core.lang.gaml.ActionDefinition;
import gama.core.lang.gaml.ActionFakeDefinition;
import gama.core.lang.gaml.ActionRef;
import gama.core.lang.gaml.ArgumentDefinition;
import gama.core.lang.gaml.Array;
import gama.core.lang.gaml.Block;
import gama.core.lang.gaml.BooleanLiteral;
import gama.core.lang.gaml.D_Species;
import gama.core.lang.gaml.DoubleLiteral;
import gama.core.lang.gaml.Entry;
import gama.core.lang.gaml.EquationDefinition;
import gama.core.lang.gaml.EquationFakeDefinition;
import gama.core.lang.gaml.EquationRef;
import gama.core.lang.gaml.Expression;
import gama.core.lang.gaml.ExpressionList;
import gama.core.lang.gaml.Facet;
import gama.core.lang.gaml.Function;
import gama.core.lang.gaml.GamlDefinition;
import gama.core.lang.gaml.HeadlessExperiment;
import gama.core.lang.gaml.If;
import gama.core.lang.gaml.Import;
import gama.core.lang.gaml.IntLiteral;
import gama.core.lang.gaml.Model;
import gama.core.lang.gaml.Parameter;
import gama.core.lang.gaml.Point;
import gama.core.lang.gaml.Pragma;
import gama.core.lang.gaml.ReservedLiteral;
import gama.core.lang.gaml.S_Action;
import gama.core.lang.gaml.S_Assignment;
import gama.core.lang.gaml.S_Definition;
import gama.core.lang.gaml.S_Display;
import gama.core.lang.gaml.S_Do;
import gama.core.lang.gaml.S_Equations;
import gama.core.lang.gaml.S_Experiment;
import gama.core.lang.gaml.S_Global;
import gama.core.lang.gaml.S_If;
import gama.core.lang.gaml.S_Loop;
import gama.core.lang.gaml.S_Other;
import gama.core.lang.gaml.S_Reflex;
import gama.core.lang.gaml.S_Solve;
import gama.core.lang.gaml.S_Species;
import gama.core.lang.gaml.S_StringDefinition;
import gama.core.lang.gaml.SkillFakeDefinition;
import gama.core.lang.gaml.SkillRef;
import gama.core.lang.gaml.Statement;
import gama.core.lang.gaml.StringEvaluator;
import gama.core.lang.gaml.StringLiteral;
import gama.core.lang.gaml.TerminalExpression;
import gama.core.lang.gaml.TypeDefinition;
import gama.core.lang.gaml.TypeFakeDefinition;
import gama.core.lang.gaml.TypeInfo;
import gama.core.lang.gaml.TypeRef;
import gama.core.lang.gaml.Unary;
import gama.core.lang.gaml.Unit;
import gama.core.lang.gaml.UnitFakeDefinition;
import gama.core.lang.gaml.UnitName;
import gama.core.lang.gaml.VarDefinition;
import gama.core.lang.gaml.VarFakeDefinition;
import gama.core.lang.gaml.VariableRef;
import gama.core.lang.gaml.util.GamlSwitch;
import gaml.descriptions.ModelDescription;

/**
 * GAML Qualified Name provider.
 *
 */
public class GamlQualifiedNameProvider extends IQualifiedNameProvider.AbstractImpl {

	private final static String NULL = "";
	private final static GamlSwitch<String> SWITCH = new GamlSwitch<>() {

		@Override
		public String caseS_Reflex(final S_Reflex s) {
			if (s.getKey().equals(IKeyword.ASPECT))
				return s.getName();
			return NULL;
		}

		@Override
		public String caseD_Species(final D_Species s) {
			return NULL;
		}

		@Override
		public String caseS_StringDefinition(final S_StringDefinition s) {
			return s.getName();
		}

		@Override
		public String caseS_Definition(final S_Definition s) {
			return s.getName();
		}

		@Override
		public String caseS_Equations(final S_Equations s) {
			return s.getName();
		}

		@Override
		public String caseArgumentDefinition(final ArgumentDefinition a) {
			return a.getName();
		}

		@Override
		public String caseS_Species(final S_Species s) {
			return s.getName();
		}

		@Override
		public String caseS_Experiment(final S_Experiment s) {
			return s.getName();
		}

		@Override
		public String caseHeadlessExperiment(final HeadlessExperiment s) {
			return s.getName();
		}

		@Override
		public String caseFacet(final Facet f) {
			return f.getName();
		}

		@Override
		public String caseModel(final Model o) {
			return o.getName() + ModelDescription.MODEL_SUFFIX;
		}

		@Override
		public String caseImport(final Import i) {
			return i.getName();
		}

		@Override
		public String caseS_Display(final S_Display s) {
			return NULL;
		}

		@Override
		public String defaultCase(final EObject e) {
			return NULL;
		}

		@Override
		public String caseEntry(final Entry object) {
			return NULL;
		}

		@Override
		public String caseStringEvaluator(final StringEvaluator object) {
			return NULL;
		}

		@Override
		public String caseBlock(final Block object) {
			return NULL;
		}

		@Override
		public String casePragma(final Pragma object) {
			return NULL;
		}

		@Override
		public String caseStatement(final Statement object) {
			return NULL;
		}

		@Override
		public String caseS_Global(final S_Global object) {
			return NULL;
		}

		@Override
		public String caseS_Do(final S_Do object) {
			return NULL;
		}

		@Override
		public String caseS_Loop(final S_Loop object) {
			return object.getName();
		}

		@Override
		public String caseS_If(final S_If object) {
			return NULL;
		}

		@Override
		public String caseS_Other(final S_Other object) {
			return NULL;
		}

		@Override
		public String caseS_Assignment(final S_Assignment object) {
			return NULL;
		}

		@Override
		public String caseS_Solve(final S_Solve object) {
			return NULL;
		}

		@Override
		public String caseActionArguments(final ActionArguments object) {
			return NULL;
		}

		@Override
		public String caseExpression(final Expression object) {
			return NULL;
		}

		@Override
		public String caseFunction(final Function object) {
			return NULL;
		}

		@Override
		public String caseExpressionList(final ExpressionList object) {
			return NULL;
		}

		@Override
		public String caseVariableRef(final VariableRef object) {
			return NULL;
		}

		@Override
		public String caseTypeInfo(final TypeInfo object) {
			return NULL;
		}

		@Override
		public String caseGamlDefinition(final GamlDefinition object) {
			return object.getName();
		}

		@Override
		public String caseEquationDefinition(final EquationDefinition object) {
			return object.getName();
		}

		@Override
		public String caseTypeDefinition(final TypeDefinition object) {
			return object.getName();
		}

		@Override
		public String caseVarDefinition(final VarDefinition object) {
			return object.getName();
		}

		@Override
		public String caseActionDefinition(final ActionDefinition object) {
			return object.getName();
		}

		@Override
		public String caseUnitFakeDefinition(final UnitFakeDefinition object) {
			return object.getName();
		}

		@Override
		public String caseTypeFakeDefinition(final TypeFakeDefinition object) {
			return object.getName();
		}

		@Override
		public String caseActionFakeDefinition(final ActionFakeDefinition object) {
			return object.getName();
		}

		@Override
		public String caseSkillFakeDefinition(final SkillFakeDefinition object) {
			return object.getName();
		}

		@Override
		public String caseVarFakeDefinition(final VarFakeDefinition object) {
			return object.getName();
		}

		@Override
		public String caseEquationFakeDefinition(final EquationFakeDefinition object) {
			return object.getName();
		}

		@Override
		public String caseTerminalExpression(final TerminalExpression object) {
			return NULL;
		}

		@Override
		public String caseS_Action(final S_Action object) {
			return object.getName();
		}

		@Override
		public String caseIf(final If object) {
			return NULL;
		}

		@Override
		public String caseUnit(final Unit object) {
			return NULL;
		}

		@Override
		public String caseUnary(final Unary object) {
			return NULL;
		}

		@Override
		public String caseAccess(final Access object) {
			return NULL;
		}

		@Override
		public String caseArray(final Array object) {
			return NULL;
		}

		@Override
		public String casePoint(final Point object) {
			return NULL;
		}

		@Override
		public String caseParameter(final Parameter object) {
			return NULL;
		}

		@Override
		public String caseUnitName(final UnitName object) {
			return NULL;
		}

		@Override
		public String caseTypeRef(final TypeRef object) {
			return NULL;
		}

		@Override
		public String caseSkillRef(final SkillRef object) {
			return NULL;
		}

		@Override
		public String caseActionRef(final ActionRef object) {
			return NULL;
		}

		@Override
		public String caseEquationRef(final EquationRef object) {
			return NULL;
		}

		@Override
		public String caseIntLiteral(final IntLiteral object) {
			return NULL;
		}

		@Override
		public String caseDoubleLiteral(final DoubleLiteral object) {
			return NULL;
		}

		@Override
		public String caseStringLiteral(final StringLiteral object) {
			return NULL;
		}

		@Override
		public String caseBooleanLiteral(final BooleanLiteral object) {
			return NULL;
		}

		@Override
		public String caseReservedLiteral(final ReservedLiteral object) {
			return NULL;
		}

	};

	// @Inject private IResourceScopeCache cache;

	// @Override
	// public QualifiedName getFullyQualifiedName(final EObject obj) {
	// if (obj == null) { return null; }
	// return cache.get(obj, obj.eResource(), () -> GamlQualifiedNameProvider.this.get(obj));
	// }

	@Override
	public QualifiedName getFullyQualifiedName(final EObject obj) {
		final String string = SWITCH.doSwitch(obj);
		if (string == null || string.isEmpty())
			return null;
		return QualifiedName.create(string);

	}
	//
	// private QualifiedName get(final EObject input) {
	// final String string = SWITCH.doSwitch(input);
	// if (string == null || string.equals(NULL)) { return null; }
	// return QualifiedName.create(string);
	// }

}