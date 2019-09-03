/*******************************************************************************************************
 *
 * msi.gama.common.interfaces.IDisplayCreator.java, in plugin msi.gama.core, is part of the source code of the GAMA
 * modeling and simulation platform (v. 1.8)
 *
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package msi.gama.common.interfaces.outputs;

import java.lang.reflect.Constructor;

import msi.gama.common.interfaces.IGamlDescription;
import ummisco.gama.processor.GamlAnnotations.doc;

@FunctionalInterface
public interface IDisplayCreator {

	public static class DisplayDescription implements IDisplayCreator, IGamlDescription {

		private Constructor<IDisplaySurface> constructor;
		private final String name, plugin, documentation;

		public DisplayDescription(final Class<IDisplaySurface> clazz, final String name, final String plugin) {
			try {
				constructor = clazz.getConstructor(IDisplayOutput.Layered.class);
			} catch (NoSuchMethodException | SecurityException e) {}
			this.name = name;
			this.plugin = plugin;
			doc doc = clazz.getDeclaredAnnotation(doc.class);
			if (doc != null) {
				documentation = doc.value();
			} else {
				documentation = name + " display";
			}
		}

		@Override
		public IDisplaySurface create(final IDisplayOutput.Layered output) {
			if (constructor != null) {
				try {
					return constructor.newInstance(output);
				} catch (Exception e1) {

				}
			}
			return IDisplaySurface.NULL;
		}

		/**
		 * Method getName()
		 *
		 * @see msi.gama.common.interfaces.INamed#getName()
		 */
		@Override
		public String getName() {
			return name;
		}

		/**
		 * Method setName()
		 *
		 * @see msi.gama.common.interfaces.INamed#setName(java.lang.String)
		 */
		@Override
		public void setName(final String newName) {}

		/**
		 * Method serialize()
		 *
		 * @see msi.gama.common.interfaces.IGamlable#serialize(boolean)
		 */
		@Override
		public String serialize(final boolean includingBuiltIn) {
			return getName();
		}

		/**
		 * Method getTitle()
		 *
		 * @see msi.gama.common.interfaces.IGamlDescription#getTitle()
		 */
		@Override
		public String getTitle() {
			return "Display supported by " + getName() + "";
		}

		/**
		 * Method getDocumentation()
		 *
		 * @see msi.gama.common.interfaces.IGamlDescription#getDocumentation()
		 */
		@Override
		public String getDocumentation() {
			return documentation;
		}

		/**
		 * Method getDefiningPlugin()
		 *
		 * @see msi.gama.common.interfaces.IGamlDescription#getDefiningPlugin()
		 */
		@Override
		public String getDefiningPlugin() {
			return plugin;
		}

	}

	IDisplaySurface create(IDisplayOutput.Layered output);

}
