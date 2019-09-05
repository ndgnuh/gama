/*******************************************************************************************************
 *
 * gaml.types.ParametricType.java, in plugin gama.core, is part of the source code of the GAMA modeling and
 * simulation platform (v. 1.8)
 *
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package gaml.types;

import static com.google.common.cache.CacheBuilder.newBuilder;
import static gaml.types.Types.builtInTypes;
import static java.util.concurrent.TimeUnit.MINUTES;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.google.common.cache.Cache;

import gama.common.interfaces.IContainer;
import gama.processor.annotations.ISymbolKind;
import gama.runtime.exceptions.GamaRuntimeException;
import gama.runtime.scope.IScope;
import gaml.descriptions.IDescription;
import gaml.descriptions.SpeciesDescription;
import gaml.expressions.IExpression;
import gaml.prototypes.OperatorProto;

/**
 * Class ParametricType. A class that allows to build composed types with a content type and a key type
 *
 * @author drogoul
 * @since 19 janv. 2014
 *
 */
public class ParametricType implements IContainerType<IContainer<?, ?>> {

	static Cache<Integer, ParametricType> CACHE2 = newBuilder().expireAfterAccess(30, MINUTES).build();
	static boolean USE_CACHE = true;

	static boolean useCacheFor(final IType<?> t) {
		final boolean builtIn = builtInTypes.containsType(t.getName());
		return t.isCompoundType() ? builtIn && useCacheFor(t.getContentType()) && useCacheFor(t.getKeyType()) : builtIn;
	}

	public static ParametricType createParametricType(final IContainerType<IContainer<?, ?>> t, final IType<?> kt,
			final IType<?> ct) {
		if (USE_CACHE && useCacheFor(t) && useCacheFor(kt) && useCacheFor(ct)) {
			final Integer key = 31 * (31 * (31 + t.hashCode()) + kt.hashCode()) + ct.hashCode();
			ParametricType p = CACHE2.getIfPresent(key);
			if (p == null) {
				p = new ParametricType(t, kt, ct);
				CACHE2.put(key, p);
				// DEBUG.OUT("Size: " + CACHE2.size() + " | Saved Type = " + p);
			} else
				// DEBUG.OUT("Saved creations : " + savedTypes++);
				return p;
		}
		return new ParametricType(t, kt, ct);
	}

	private final IContainerType<IContainer<?, ?>> type;
	private final IType<?> contentsType;
	private final IType<?> keyType;

	protected ParametricType(final IContainerType<IContainer<?, ?>> t, final IType<?> kt, final IType<?> ct) {
		type = t;
		contentsType = ct;
		keyType = kt;
	}

	@Override
	public boolean isCompoundType() {
		return true;
	}

	@Override
	public int getNumberOfParameters() {
		return type.getNumberOfParameters();
	}

	@Override
	public boolean equals(final Object other) {
		if (other instanceof ParametricType)
			return type.equals(((ParametricType) other).getGamlType())
					&& keyType.equals(((ParametricType) other).getKeyType())
					&& contentsType.equals(((ParametricType) other).getContentType());
		return false;
	}

	@Override
	public int hashCode() {
		return 31 * (31 * (31 + type.hashCode()) + keyType.hashCode()) + contentsType.hashCode();
	}

	@Override
	public String getDefiningPlugin() {
		return type.getDefiningPlugin();
	}

	/**
	 * Method getType()
	 *
	 * @see gama.common.interfaces.ITyped#getGamlType()
	 */
	@Override
	public IContainerType<IContainer<?, ?>> getGamlType() {
		return type;
	}

	/**
	 * Method cast()
	 *
	 * @see gaml.types.IType#cast(gama.runtime.scope.IScope, java.lang.Object, java.lang.Object, gaml.types.IType,
	 *      gaml.types.IType)
	 */
	@Override
	public IContainer<?, ?> cast(final IScope scope, final Object obj, final Object param, final IType<?> kt,
			final IType<?> ct, final boolean copy) throws GamaRuntimeException {
		return type.cast(scope, obj, param, keyType, contentsType, copy);
	}

	/**
	 * Method id()
	 *
	 * @see gaml.types.IType#id()
	 */
	@Override
	public int id() {
		return type.id();
	}

	/**
	 * Method toClass()
	 *
	 * @see gaml.types.IType#toClass()
	 */
	@Override
	public Class<? extends IContainer<?, ?>> toClass() {
		return type.toClass();
	}

	/**
	 * Method getDefault()
	 *
	 * @see gaml.types.IType#getDefault()
	 */
	@Override
	public IContainer<?, ?> getDefault() {
		return type.getDefault();
	}

	/**
	 * Method getVarKind()
	 *
	 * @see gaml.types.IType#getVarKind()
	 */
	@Override
	public int getVarKind() {
		return ISymbolKind.Variable.CONTAINER;
	}

	/**
	 * Method getGetter()
	 *
	 * @see gaml.types.IType#getGetter(java.lang.String)
	 */
	@Override
	public OperatorProto getGetter(final String name) {
		return type.getGetter(name);
	}

	/**
	 * Method getFieldDescriptions()
	 *
	 * @see gaml.types.IType#getFieldDescriptions(gaml.descriptions.ModelDescription)
	 */
	// @Override
	// public Map getFieldDescriptions(final ModelDescription model) {
	// return type.getFieldDescriptions(model);
	// }

	/**
	 * Method isSpeciesType()
	 *
	 * @see gaml.types.IType#isSpeciesType()
	 */
	@Override
	public boolean isAgentType() {
		// Verify this
		return type.isAgentType();
	}

	/**
	 * Method isSkillType()
	 *
	 * @see gaml.types.IType#isSkillType()
	 */
	@Override
	public boolean isSkillType() {
		return false;
	}

	/**
	 * Method defaultContentType()
	 *
	 * @see gaml.types.IType#defaultContentType()
	 */
	@Override
	public IType<?> getContentType() {
		return contentsType;
	}

	/**
	 * Method defaultKeyType()
	 *
	 * @see gaml.types.IType#defaultKeyType()
	 */
	@Override
	public IType<?> getKeyType() {
		return keyType;
	}

	/**
	 * Method getSpeciesName()
	 *
	 * @see gaml.types.IType#getSpeciesName()
	 */
	@Override
	public String getSpeciesName() {
		return type.getSpeciesName();
	}

	/**
	 * Method getSpecies()
	 *
	 * @see gaml.types.IType#getSpecies()
	 */
	@Override
	public SpeciesDescription getSpecies() {
		final SpeciesDescription result = type.getSpecies();
		// if (result != null)
		return result;
		// return contentsType.getSpecies();
	}

	@Override
	public SpeciesDescription getDenotedSpecies() {
		final SpeciesDescription result = type.getSpecies();
		if (result != null)
			return result;
		return contentsType.getSpecies();
	}

	/**
	 * Method isAssignableFrom()
	 *
	 * @see gaml.types.IType#isAssignableFrom(gaml.types.IType)
	 */
	@Override
	public boolean isAssignableFrom(final IType<?> l) {
		return type.isAssignableFrom(l.getGamlType()) && contentsType.isAssignableFrom(l.getContentType())
				&& keyType.isAssignableFrom(l.getKeyType());
	}

	/**
	 * Method isTranslatableInto()
	 *
	 * @see gaml.types.IType#isTranslatableInto(gaml.types.IType)
	 */
	@Override
	public boolean isTranslatableInto(final IType<?> l) {
		return type.isTranslatableInto(l.getGamlType()) && contentsType.isTranslatableInto(l.getContentType())
				&& keyType.isTranslatableInto(l.getKeyType());
	}

	@Override
	public boolean isParametricFormOf(final IType<?> l) {
		return !l.isParametricType() && type.equals(l);
	}

	@Override
	public boolean isParametricType() {
		return true;
	}

	/**
	 * Method setParent()
	 *
	 * @see gaml.types.IType#setParent(gaml.types.IType)
	 */
	@Override
	public void setParent(final IType<? super IContainer<?, ?>> p) {}

	/**
	 * Method getParent()
	 *
	 * @see gaml.types.IType#getParent()
	 */
	@Override
	public IType<?> getParent() {
		return type;
	}

	/**
	 * Method coerce()
	 *
	 * @see gaml.types.IType#coerce(gaml.types.IType, gaml.descriptions.IDescription)
	 */
	@Override
	public IType<?> coerce(final IType<?> expr, final IDescription context) {
		return null;
	}

	/**
	 * Method distanceTo()
	 *
	 * @see gaml.types.IType#distanceTo(gaml.types.IType)
	 */
	@Override
	public int distanceTo(final IType<?> t) {
		return t.getGamlType().distanceTo(type) + t.getContentType().distanceTo(contentsType)
				+ t.getKeyType().distanceTo(keyType);
	}

	/**
	 * Method setFieldGetters()
	 *
	 * @see gaml.types.IType#setFieldGetters(java.util.Map)
	 */
	@Override
	public void setFieldGetters(final Map<String, OperatorProto> map) {}

	/**
	 * Method canBeTypeOf()
	 *
	 * @see gaml.types.IType#canBeTypeOf(gama.runtime.scope.IScope, java.lang.Object)
	 */
	@Override
	public boolean canBeTypeOf(final IScope s, final Object c) {
		return type.canBeTypeOf(s, c);
	}

	/**
	 * Method init()
	 *
	 * @see gaml.types.IType#init(int, int, java.lang.String, java.lang.Class[])
	 */
	@Override
	public void init(final int varKind, final int id, final String name, final Class<IContainer<?, ?>> clazz) {}

	/**
	 * Method isContainer()
	 *
	 * @see gaml.types.IType#isContainer()
	 */
	@Override
	public boolean isContainer() {
		return true; // ???
	}

	/**
	 * Method isFixedLength()
	 *
	 * @see gaml.types.IType#isFixedLength()
	 */
	@Override
	public boolean isFixedLength() {
		return type.isFixedLength();
	}

	/**
	 * Method findCommonSupertypeWith()
	 *
	 * @see gaml.types.IType#findCommonSupertypeWith(gaml.types.IType)
	 */
	@SuppressWarnings ("unchecked")
	@Override
	public IType<? super IContainer<?, ?>> findCommonSupertypeWith(final IType<?> iType) {
		if (iType instanceof ParametricType) {
			final IType<?> pType = iType;
			final IType<?> cType = type.findCommonSupertypeWith(pType.getGamlType());
			if (cType.isContainer()) {
				final IType<?> kt = keyType.findCommonSupertypeWith(pType.getKeyType());
				final IType<?> ct = contentsType.findCommonSupertypeWith(pType.getContentType());
				return (IType<? super IContainer<?, ?>>) GamaType.from(cType, kt, ct);
			} else
				return (IType<? super IContainer<?, ?>>) cType;
		} else if (iType.isContainer()) {
			final IType<?> cType = type.findCommonSupertypeWith(iType);
			return (IType<? super IContainer<?, ?>>) cType;
			// dont we need to use the key and contents type here ?
		} else
			return type.findCommonSupertypeWith(iType);
	}

	/**
	 * Method isParented()
	 *
	 * @see gaml.types.IType#isParented()
	 */
	@Override
	public boolean isParented() {
		return true;
		// return type != null; // ??
	}

	@Override
	public boolean isDrawable() {
		return type.isDrawable();
	}

	/**
	 * Method setSupport()
	 *
	 * @see gaml.types.IType#setSupport(java.lang.Class)
	 */
	@Override
	public void setSupport(final Class<IContainer<?, ?>> clazz) {}

	@Override
	public IContainer<?, ?> cast(final IScope scope, final Object obj, final Object param, final boolean copy)
			throws GamaRuntimeException {
		return cast(scope, obj, param, keyType, contentsType, copy);
	}

	@Override
	public String toString() {
		if (type.id() == IType.LIST || type.id() == IType.MATRIX
				|| type.id() == IType.CONTAINER && keyType == Types.NO_TYPE)
			return type.toString() + "<" + contentsType.toString() + ">";
		if (type.id() == IType.SPECIES)
			return type.toString() + "<" + contentsType.toString() + ">";
		return type.toString() + "<" + keyType.toString() + ", " + contentsType.toString() + ">";
	}

	@Override
	public String asPattern() {
		final boolean vowel = StringUtils.startsWithAny(type.getName(), vowels);
		return "${" + (vowel ? "an_" : "a_") + serialize(true) + "}";
	}

	@Override
	public String serialize(final boolean includingBuiltIn) {
		if (type.id() == IType.LIST || type.id() == IType.MATRIX
				|| type.id() == IType.CONTAINER && keyType == Types.NO_TYPE)
			return type.toString() + "<" + contentsType.toString() + ">";
		if (type.id() == IType.SPECIES)
			return type.toString() + "<" + contentsType.toString() + ">";
		return type.toString() + "<" + keyType.serialize(includingBuiltIn) + ", "
				+ contentsType.serialize(includingBuiltIn) + ">";
	}

	@Override
	public IContainerType<?> typeIfCasting(final IExpression exp) {
		if (contentsType == Types.NO_TYPE || keyType == Types.NO_TYPE) {
			final IType<?> genericCast = type.typeIfCasting(exp);
			final IType<?> ct = contentsType == Types.NO_TYPE ? genericCast.getContentType() : contentsType;
			final IType<?> kt = keyType == Types.NO_TYPE ? genericCast.getKeyType() : keyType;
			return createParametricType(type, kt, ct);
		}
		return this;
	}

	/**
	 * Method getTitle()
	 *
	 * @see gama.common.interfaces.IGamlDescription#getTitle()
	 */
	@Override
	public String getTitle() {
		return toString().replace('<', '[').replace('>', ']');
	}

	/**
	 * Method getDocumentation()
	 *
	 * @see gama.common.interfaces.IGamlDescription#getDocumentation()
	 */
	@Override
	public String getDocumentation() {
		return getTitle();
	}

	/**
	 * Method getName()
	 *
	 * @see gama.common.interfaces.IGamlDescription#getName()
	 */
	@Override
	public String getName() {
		return toString();
	}

	@Override
	public void setName(final String name) {
		// Nothing
	}

	@Override
	public boolean canCastToConst() {
		return type.canCastToConst() && contentsType.canCastToConst() && keyType.canCastToConst();
	}

	@Override
	public IContainerType<?> of(final IType<?> sub1) {
		IType<?> kt = getKeyType();
		IType<?> ct = sub1;
		if (ct == Types.NO_TYPE) {
			if (kt == Types.NO_TYPE)
				return this;
			ct = getContentType();
		}
		if (kt == Types.NO_TYPE) {
			kt = getKeyType();
		}
		return createParametricType(this, kt, ct);

	}

	@Override
	public IContainerType<?> of(final IType<?> sub1, final IType<?> sub2) {
		IType<?> kt = sub1;
		IType<?> ct = sub2;
		if (ct == Types.NO_TYPE) {
			if (kt == Types.NO_TYPE)
				return this;
			ct = getContentType();
		}
		if (kt == Types.NO_TYPE) {
			kt = getKeyType();
		}
		return createParametricType(this, kt, ct);

	}

	/**
	 * Method setDefiningPlugin()
	 *
	 * @see gaml.types.IType#setDefiningPlugin(java.lang.String)
	 */
	@Override
	public void setDefiningPlugin(final String plugin) {}

	// @Override
	// public void collectMetaInformation(final GamlProperties meta) {
	// type.collectMetaInformation(meta);
	// contentsType.collectMetaInformation(meta);
	// keyType.collectMetaInformation(meta);
	// }

	@Override
	public boolean isNumber() {
		return false;
	}

	@Override
	public IType<?> getWrappedType() {
		return Types.NO_TYPE;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see gaml.types.IType#getFieldGetters()
	 */
	@Override
	public Map<String, OperatorProto> getFieldGetters() {
		return type.getFieldGetters();
	}

}