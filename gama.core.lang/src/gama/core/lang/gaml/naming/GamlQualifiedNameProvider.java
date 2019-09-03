/*********************************************************************************************
 *
 * 'GamlQualifiedNameProvider.java, in plugin gama.core.gaml, is part of the source code of the GAMA modeling and
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

import msi.gama.common.interfaces.IKeyword;
import msi.gaml.descriptions.ModelDescription;
import ummisco.gama.gaml.Access;
import ummisco.gama.gaml.ActionArguments;
import ummisco.gama.gaml.ActionDefinition;
import ummisco.gama.gaml.ActionFakeDefinition;
import ummisco.gama.gaml.ActionRef;
import ummisco.gama.gaml.ArgumentDefinition;
import ummisco.gama.gaml.ArgumentPair;
import ummisco.gama.gaml.Array;
import ummisco.gama.gaml.Block;
import ummisco.gama.gaml.BooleanLiteral;
import ummisco.gama.gaml.DoubleLiteral;
import ummisco.gama.gaml.Entry;
import ummisco.gama.gaml.EquationDefinition;
import ummisco.gama.gaml.EquationFakeDefinition;
import ummisco.gama.gaml.EquationRef;
import ummisco.gama.gaml.Expression;
import ummisco.gama.gaml.ExpressionList;
import ummisco.gama.gaml.Facet;
import ummisco.gama.gaml.Function;
import ummisco.gama.gaml.GamlDefinition;
import ummisco.gama.gaml.HeadlessExperiment;
import ummisco.gama.gaml.If;
import ummisco.gama.gaml.Import;
import ummisco.gama.gaml.IntLiteral;
import ummisco.gama.gaml.Model;
import ummisco.gama.gaml.Parameter;
import ummisco.gama.gaml.Point;
import ummisco.gama.gaml.Pragma;
import ummisco.gama.gaml.ReservedLiteral;
import ummisco.gama.gaml.S_Action;
import ummisco.gama.gaml.S_Assignment;
import ummisco.gama.gaml.S_Declaration;
import ummisco.gama.gaml.S_Definition;
import ummisco.gama.gaml.S_DirectAssignment;
import ummisco.gama.gaml.S_Display;
import ummisco.gama.gaml.S_Do;
import ummisco.gama.gaml.S_Equations;
import ummisco.gama.gaml.S_Experiment;
import ummisco.gama.gaml.S_Global;
import ummisco.gama.gaml.S_If;
import ummisco.gama.gaml.S_Loop;
import ummisco.gama.gaml.S_Other;
import ummisco.gama.gaml.S_Reflex;
import ummisco.gama.gaml.S_Return;
import ummisco.gama.gaml.S_Set;
import ummisco.gama.gaml.S_Solve;
import ummisco.gama.gaml.S_Species;
import ummisco.gama.gaml.S_Var;
import ummisco.gama.gaml.SkillFakeDefinition;
import ummisco.gama.gaml.SkillRef;
import ummisco.gama.gaml.Statement;
import ummisco.gama.gaml.StringEvaluator;
import ummisco.gama.gaml.StringLiteral;
import ummisco.gama.gaml.TerminalExpression;
import ummisco.gama.gaml.TypeDefinition;
import ummisco.gama.gaml.TypeFakeDefinition;
import ummisco.gama.gaml.TypeInfo;
import ummisco.gama.gaml.TypeRef;
import ummisco.gama.gaml.Unary;
import ummisco.gama.gaml.Unit;
import ummisco.gama.gaml.UnitFakeDefinition;
import ummisco.gama.gaml.UnitName;
import ummisco.gama.gaml.VarDefinition;
import ummisco.gama.gaml.VarFakeDefinition;
import ummisco.gama.gaml.VariableRef;
import ummisco.gama.gaml.speciesOrGridDisplayStatement;
import ummisco.gama.gaml.util.GamlSwitch;

/**
 * GAML Qualified Name provider.
 *
 */
public class GamlQualifiedNameProvider extends IQualifiedNameProvider.AbstractImpl {

	private final static String NULL = "";
	private final static GamlSwitch<String> SWITCH = new GamlSwitch<String>() {

		@Override
		public String caseS_Reflex(final S_Reflex s) {
			if (s.getKey().equals(IKeyword.ASPECT)) { return s.getName(); }
			return NULL;
		}

		@Override
		public String casespeciesOrGridDisplayStatement(final speciesOrGridDisplayStatement s) {
			return NULL;
		}

		@Override
		public String caseS_Declaration(final S_Declaration s) {
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
		public String caseS_Return(final S_Return object) {
			return NULL;
		}

		@Override
		public String caseS_Assignment(final S_Assignment object) {
			return NULL;
		}

		@Override
		public String caseS_DirectAssignment(final S_DirectAssignment object) {
			return NULL;
		}

		@Override
		public String caseS_Set(final S_Set object) {
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
		public String caseArgumentPair(final ArgumentPair object) {
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
		public String caseS_Var(final S_Var object) {
			return object.getName();
		}

		@Override
		public String caseIf(final If object) {
			return NULL;
		}
		//
		// @Override
		// public String caseCast(final Cast object) {
		// return NULL;
		// }

		// @Override
		// public String caseBinary(final Binary object) {
		// return NULL;
		// }

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
		//
		// @Override
		// public String caseColorLiteral(final ColorLiteral object) {
		// return NULL;
		// }

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
		if (string == null || string.isEmpty()) { return null; }
		return QualifiedName.create(string);

	}
	//
	// private QualifiedName get(final EObject input) {
	// final String string = SWITCH.doSwitch(input);
	// if (string == null || string.equals(NULL)) { return null; }
	// return QualifiedName.create(string);
	// }

}