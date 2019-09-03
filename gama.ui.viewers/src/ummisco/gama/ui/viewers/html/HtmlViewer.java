/*********************************************************************************************
 *
 * 'HtmlViewer.java, in plugin gama.ui.base.viewers, is part of the source code of the GAMA modeling and simulation
 * platform. (c) 2007-2016 UMI 209 UMMISCO IRD/UPMC & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and developers contact.
 *
 *
 **********************************************************************************************/
package ummisco.gama.ui.viewers.html;

import java.net.MalformedURLException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.ProgressEvent;
import org.eclipse.swt.browser.ProgressListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.ide.FileStoreEditorInput;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.ui.part.FileEditorInput;

import gama.ui.base.utils.WebHelper;
import gama.ui.base.views.toolbar.GamaToolbar2;
import gama.ui.base.views.toolbar.GamaToolbarFactory;
import gama.ui.base.views.toolbar.IToolbarDecoratedView;
import msi.gama.common.interfaces.gui.IGamaView;

/**
 * Class BrowserEditor.
 *
 * @author drogoul
 * @since 28 avr. 2014
 *
 */
public class HtmlViewer extends EditorPart implements IToolbarDecoratedView, IGamaView.Html {

	Browser browser;
	ToolItem back, forward, home;

	public HtmlViewer() {}

	@Override
	public void doSave(final IProgressMonitor monitor) {}

	@Override
	public void doSaveAs() {}

	@Override
	public void init(final IEditorSite site, final IEditorInput in) throws PartInitException {
		setSite(site);
		setInput(in);
		openInput();
	}

	private void openInput() {
		if (browser == null) { return; }
		if (getEditorInput() instanceof FileEditorInput) {
			final FileEditorInput input = (FileEditorInput) getEditorInput();
			try {
				this.setUrl(input.getURI().toURL().toString());
			} catch (final MalformedURLException e) {
				e.printStackTrace();
			}
		} else if (getEditorInput() instanceof FileStoreEditorInput) {
			final FileStoreEditorInput input = (FileStoreEditorInput) getEditorInput();
			try {
				this.setUrl(input.getURI().toURL().toString());
			} catch (final MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public boolean isDirty() {
		return false;
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	public void createPartControl(final Composite parent) {
		final Composite compo = GamaToolbarFactory.createToolbars(this, parent);
		browser = new Browser(compo, SWT.NONE);
		browser.addProgressListener(new ProgressListener() {

			@Override
			public void changed(final ProgressEvent arg0) {}

			@Override
			public void completed(final ProgressEvent event) {
				checkButtons();
			}
		});
		parent.layout();
		openInput();
	}

	@Override
	public void setUrl(final String url) {
		browser.setUrl(url);
		this.setPartName(url.substring(url.lastIndexOf('/') + 1));
		checkButtons();
	}

	/**
	 *
	 */
	void checkButtons() {
		back.setEnabled(browser.isBackEnabled());
		forward.setEnabled(browser.isForwardEnabled());
	}

	@Override
	public void setFocus() {
		browser.setFocus();
	}

	public Control getSizableFontControl() {
		return browser;
	}

	/**
	 * Method createToolItem()
	 *
	 * @see gama.ui.base.views.toolbar.IToolbarDecoratedView#createToolItem(int,
	 *      gama.ui.base.views.toolbar.GamaToolbar2)
	 */
	@Override
	public void createToolItems(final GamaToolbar2 tb) {

		back = tb.button("browser/back", "Back", "Go to previous page in history", e -> {
			browser.back();
			checkButtons();
		}, SWT.RIGHT);
		home = tb.button("browser/home", "Home", "Go back to the welcome page", e -> {
			browser.setUrl(WebHelper.getWelcomePageURL().toString());
			checkButtons();
		}, SWT.RIGHT);
		forward = tb.button("browser/forward", "Forward", "Go to next page in history", e -> {
			browser.forward();
			checkButtons();
		}, SWT.RIGHT);
		tb.sep(GamaToolbarFactory.TOOLBAR_SEP, SWT.RIGHT);
		tb.button("browser/refresh", "Refresh", "Refresh current page", e -> browser.refresh(), SWT.RIGHT);
		tb.button("browser/stop", "Stop", "Stop loading page", e -> browser.stop(), SWT.RIGHT);

	}

}
