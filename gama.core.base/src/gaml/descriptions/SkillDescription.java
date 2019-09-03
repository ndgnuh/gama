/*******************************************************************************************************
 *
 * gaml.descriptions.SkillDescription.java, in plugin gama.core, is part of the source code of the GAMA modeling
 * and simulation platform (v. 1.8)
 *
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package gaml.descriptions;

import java.util.Collection;
import java.util.Collections;

import gama.common.interfaces.IKeyword;
import gama.common.interfaces.ISkill;
import gama.common.util.TextBuilder;
import gama.processor.annotations.GamlAnnotations.doc;
import gama.processor.annotations.GamlAnnotations.skill;
import gaml.architecture.IArchitecture;
import gaml.skills.Skill;

public class SkillDescription extends TypeDescription {

	Skill instance;
	final boolean isControl;
	final Class<? extends ISkill> javaBase;

	public SkillDescription(final String name, final Class<? extends ISkill> support,
			final Iterable<IDescription> children, final String plugin) {
		super(IKeyword.SKILL, support, null, null, children, null, null, plugin);
		this.name = name;
		this.javaBase = support;
		this.isControl = IArchitecture.class.isAssignableFrom(support);

	}

	@Override
	public Class getJavaBase() {
		return javaBase;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public IDescription addChild(final IDescription child) {
		child.setEnclosingDescription(this);
		if (child instanceof ActionDescription) {
			addAction((ActionDescription) child);
		} else {
			addOwnAttribute((VariableDescription) child);
		}
		return child;
	}

	@Override
	public boolean visitChildren(final DescriptionVisitor visitor) {
		return visitOwnChildren(visitor);
	}

	@Override
	public Collection<String> getActionNames() {
		return actions == null ? Collections.EMPTY_LIST : actions.keySet();
	}

	@Override
	public Collection<String> getAttributeNames() {
		return attributes == null ? Collections.EMPTY_LIST : attributes.keySet();
	}

	public Skill createInstance() {
		Skill instance = null;
		try {
			instance = (Skill) getJavaBase().newInstance();
			instance.setDescription(this);
		} catch (InstantiationException | IllegalAccessException e) {}
		return instance;
	}

	public Skill getInstance() {
		if (instance == null) {
			instance = createInstance();
		}
		return instance;
	}

	public boolean isControl() {
		return isControl;
	}

	@Override
	public String getTitle() {
		return "skill " + getName();
	}

	@Override
	public String getDocumentation() {
		final doc d = getDocAnnotation();
		try (TextBuilder sb = TextBuilder.create()) {
			if (d != null) {
				final String s = d.value();
				if (s != null && !s.isEmpty()) {
					sb.append(s);
					sb.append("<br/>");
				}
				final String deprecated = d.deprecated();
				if (deprecated != null && !deprecated.isEmpty()) {
					sb.append("<b>Deprecated</b>: ");
					sb.append("<i>");
					sb.append(deprecated);
					sb.append("</i><br/>");
				}
			}
			sb.append(getAttributeDocumentation());
			sb.append("<br/>");
			sb.append(getActionDocumentation());
			sb.append("<br/>");
			return sb.toString();
		}

	}

	public doc getDocAnnotation() {
		final skill s = javaBase.getAnnotation(skill.class);
		final doc[] docs = s.doc();
		doc d = null;
		if (docs.length == 0) {
			if (javaBase.isAnnotationPresent(doc.class)) {
				d = javaBase.getAnnotation(doc.class);
			}
		} else {
			d = docs[0];
		}
		return d;
	}

	public String getDeprecated() {
		final doc d = getDocAnnotation();
		if (d == null) { return null; }
		final String s = d.deprecated();
		if (s == null || s.isEmpty()) { return null; }
		return s;
	}

}