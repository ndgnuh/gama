/**
 * Created by drogoul, 31 mars 2012
 * 
 */
package msi.gaml.descriptions;

import java.util.*;
import org.eclipse.emf.ecore.EObject;

/**
 * The class EcoreBasedExpressionDescription.
 * 
 * @author drogoul
 * @since 31 mars 2012
 * 
 */
public class StringListExpressionDescription extends BasicExpressionDescription {

	final Collection<String> strings;

	public StringListExpressionDescription(final Collection<String> exp) {
		super((EObject) null);
		strings = exp;
	}

	@Override
	public IExpressionDescription cleanCopy() {
		return new StringListExpressionDescription(strings);
	}

	public StringListExpressionDescription(final String ... exp) {
		super((EObject) null);
		strings = Arrays.asList(exp);
	}

	@Override
	public String toString() {
		return strings.toString();
	}

	@Override
	public Set<String> getStrings(final IDescription context, final boolean skills) {
		return new HashSet(strings);
	}

}
