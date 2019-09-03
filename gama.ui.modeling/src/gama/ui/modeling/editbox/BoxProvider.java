/*********************************************************************************************
 *
 * 'BoxProviderImpl.java, in plugin gama.ui.base.modeling, is part of the source code of the GAMA modeling and
 * simulation platform. (c) 2007-2016 UMI 209 UMMISCO IRD/UPMC & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and developers contact.
 *
 *
 **********************************************************************************************/
package gama.ui.modeling.editbox;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbenchPart;

import gama.ui.modeling.editor.GamlEditor;

@SuppressWarnings ({ "rawtypes" })
public class BoxProvider {

	protected String id;
	protected String name;
	protected IBoxSettings editorsSettings;
	protected BoxSettingsStore settingsStore;
	protected Map<String, Class> builders;
	protected Collection<String> defaultSettingsCatalog;
	private ArrayList<Matcher> matchers;

	public BoxSettingsStore getSettingsStore() {
		if (settingsStore == null) {
			settingsStore = createSettingsStore();
			settingsStore.setProviderId(id);
		}
		return settingsStore;
	}

	public IBoxSettings getEditorsBoxSettings() {
		if (editorsSettings == null) {
			editorsSettings = createSettings0();
			getSettingsStore().loadDefaults(editorsSettings);
			editorsSettings.addPropertyChangeListener(event -> {
				final String p = event.getProperty();
				if (p != null && (p.equals(IBoxSettings.PropertiesKeys.FileNames.name())
						|| p.equals(IBoxSettings.PropertiesKeys.ALL.name()))) {
					matchers = null;
				}
			});
		}
		return editorsSettings;
	}

	public BoxDecorator decorate(final IWorkbenchPart editorPart) {
		if (!(editorPart instanceof GamlEditor))
			return null;
		final IBoxSettings settings = getEditorsBoxSettings();
		if (!settings.getEnabled())
			return null;
		((GamlEditor) editorPart).createDecorator();
		return ((GamlEditor) editorPart).getDecorator();
	}

	public boolean supports(final IWorkbenchPart editorPart) {
		return editorPart.getAdapter(Control.class) instanceof StyledText
				&& (supportsFile(editorPart.getTitle()) || supportsFile(editorPart.getTitleToolTip()));
	}

	protected boolean supportsFile(final String fileName) {
		if (fileName != null) {
			for (final Matcher matcher : getMatchers()) {
				if (matcher.matches(fileName))
					return true;
			}
		}
		return false;
	}

	protected Collection<Matcher> getMatchers() {
		if (matchers == null) {
			matchers = new ArrayList<>();
			final Collection<String> fileNames = getEditorsBoxSettings().getFileNames();
			if (fileNames != null) {
				for (final String pattern : fileNames) {
					matchers.add(new Matcher(pattern));
				}
			}
		}
		return matchers;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setId(final String newId) {
		id = newId;
	}

	public void setName(final String newName) {
		name = newName;
	}

	protected BoxSettingsStore createSettingsStore() {
		final BoxSettingsStore result = new BoxSettingsStore();
		result.setDefaultSettingsCatalog(defaultSettingsCatalog);
		return result;
	}

	public void setDefaultSettingsCatalog(final Collection<String> cat) {
		defaultSettingsCatalog = cat;
	}

	public IBoxSettings createSettings() {
		final BoxSettingsImpl result = createSettings0();
		result.copyFrom(getEditorsBoxSettings());
		return result;
	}

	protected BoxSettingsImpl createSettings0() {
		return new BoxSettingsImpl();
	}

	public BoxDecorator createDecorator() {
		final BoxDecorator result = new BoxDecorator();
		result.setProvider(this);
		return result;
	}

	public Collection<String> getBuilders() {
		return builders != null ? builders.keySet() : null;
	}

	public void setBuilders(final Map<String, Class> newBuilders) {
		builders = newBuilders;
	}

	public BoxBuilder createBoxBuilder(final String name) {
		Class<?> c = null;
		if (name != null && builders != null) {
			c = builders.get(name);
		}
		if (c == null)
			return new BoxBuilder();
		try {
			return (BoxBuilder) c.getConstructor().newInstance();
		} catch (final Exception e) {
			// EditBox.logError(this, "Cannot create box builder: " + name, e);
		}
		return null;
	}

	class Matcher {

		org.eclipse.ui.internal.misc.StringMatcher m;

		Matcher(final String pattern) {
			m = new org.eclipse.ui.internal.misc.StringMatcher(pattern, true, false);
		}

		boolean matches(final String text) {
			return m.match(text);
		}
	}
}
