/*********************************************************************************************
 *
 * 'WorkaroundForIssue1353.java, in plugin gama.ui.base.experiment, is part of the source code of the GAMA modeling
 * and simulation platform. (c) 2007-2016 UMI 209 UMMISCO IRD/UPMC & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and developers contact.
 *
 *
 **********************************************************************************************/
package gama.ui.base.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.IWorkbenchPartReference;

import gama.dev.utils.DEBUG;
import gama.ui.base.resources.IGamaColors;
import gama.ui.base.utils.WorkbenchHelper;
import gama.common.util.PlatformUtils;

/**
 * Class WorkaroundForIssue1353. Only for MacOS X, Eclipse Mars and Java 1.7
 *
 * @author drogoul
 * @since 28 déc. 2015
 *
 */
public class WorkaroundForIssue1353 {

	static {
		DEBUG.OFF();
	}

	public static class PartListener implements IPartListener2 {

		@Override
		public void partActivated(final IWorkbenchPartReference partRef) {
			showShell();
		}

		@Override
		public void partClosed(final IWorkbenchPartReference partRef) {}

		@Override
		public void partDeactivated(final IWorkbenchPartReference partRef) {}

		@Override
		public void partOpened(final IWorkbenchPartReference partRef) {}

		@Override
		public void partBroughtToTop(final IWorkbenchPartReference part) {}

		@Override
		public void partHidden(final IWorkbenchPartReference partRef) {}

		@Override
		public void partVisible(final IWorkbenchPartReference partRef) {}

		@Override
		public void partInputChanged(final IWorkbenchPartReference partRef) {}
	}

	private static Shell shell;
	private static final PartListener listener = new PartListener();

	public static void showShell() {
		if (shell != null) {
			WorkbenchHelper.asyncRun(() -> {
				DEBUG.OUT("Showing shell");
				getShell().open();
				getShell().setVisible(false);

			});
		}

	}

	private static Shell getShell() {
		if (shell == null || shell.isDisposed() || shell.getShell() == null || shell.getShell().isDisposed()) {
			createShell();
		}
		return shell;
	}

	private static void createShell() {
		DEBUG.OUT("Shell created");
		shell = new Shell(WorkbenchHelper.getShell(), SWT.APPLICATION_MODAL);
		shell.setSize(5, 5);
		shell.setAlpha(0);
		shell.setBackground(IGamaColors.BLACK.color());
	}

	public static void install() {
		if (!PlatformUtils.isMac()) { return; }
		if (shell != null) { return; }
		DEBUG.OUT(WorkaroundForIssue1353.class.getSimpleName() + " installed");
		WorkbenchHelper.run(() -> {
			createShell();
			WorkbenchHelper.getPage().addPartListener(listener);
		});

	}

	public static boolean isInstalled() {
		return shell != null;
	}

	public static void remove() { // NO_UCD (unused code)

		if (shell == null) { return; }
		WorkbenchHelper.run(() -> {
			shell.dispose();
			shell = null;
			WorkbenchHelper.getPage().removePartListener(listener);
		});
		DEBUG.OUT(WorkaroundForIssue1353.class.getSimpleName() + " removed");
	}

}
