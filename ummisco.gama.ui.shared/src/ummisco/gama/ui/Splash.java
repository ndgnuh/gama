package ummisco.gama.ui;

import static org.eclipse.core.runtime.FileLocator.resolve;
import static org.eclipse.jface.resource.ImageDescriptor.createFromURL;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayDeque;
import java.util.Queue;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;

import msi.gama.common.interfaces.IStartupProgress;
import msi.gama.runtime.GAMA;

public class Splash implements IStartupProgress {

	private int worked;
	private Shell shell = null;
	private Label text = null;
	private ProgressBar bar = null;
	Queue<String> messages = new ArrayDeque<>();
	private final Job updater = new Job("Splash updater") {

		@Override
		public IStatus run(final IProgressMonitor m) {
			if (shell != null && !shell.isDisposed()) {
				shell.getDisplay().asyncExec(() -> {
					if (!shell.isDisposed() && !text.isDisposed() && !bar.isDisposed()) {
						final String lastMsg = messages.poll();
						if (lastMsg == null) { return; }
						increaseWorked();
						text.setText(lastMsg + " (" + worked + "%)");
						bar.setSelection(worked);
						shell.update();
						int safetyCounter = 0;
						while (shell.getDisplay().readAndDispatch() && safetyCounter++ < 100) {}
					}

				});
				schedule(200);
			}
			return Status.OK_STATUS;
		}
	};

	public Splash() {
		GAMA.setStartupMonitor(this);
		updater.setPriority(Job.INTERACTIVE);
	}

	public ImageDescriptor getImageDescriptor() {
		try {
			return createFromURL(resolve(new URL("platform:/plugin/ummisco.gama.ui.shared/welcome/splash.png")));
		} catch (final IOException e) {
			return null;
		}
	}

	public void open() {
		shell = new Shell(SWT.NO_TRIM | SWT.ON_TOP | SWT.MODELESS);
		final Color color = new Color(shell.getDisplay(), new RGB(22, 94, 147));
		final Image im = getImageDescriptor().createImage(shell.getDisplay());
		shell.addDisposeListener(e -> im.dispose());
		shell.setBackgroundImage(im);
		final Rectangle bounds = im.getBounds();
		text = new Label(shell, SWT.WRAP | SWT.CENTER);
		text.setForeground(color);
		text.setText(DEFAULT_MSG);
		text.setBounds(new Rectangle(20, bounds.height - 40, bounds.width - 40, 20));
		bar = new ProgressBar(shell, SWT.SMOOTH | SWT.HORIZONTAL);
		bar.setMinimum(0);
		bar.setMaximum(100);
		bar.setSelection(worked);
		bar.setForeground(color);
		bar.setBounds(new Rectangle(100, bounds.height - 24, bounds.width - 200, 14));
		shell.setSize(bounds.width, bounds.height);
		final Rectangle screen = shell.getDisplay().getPrimaryMonitor().getBounds();
		shell.setLocation(screen.x + (screen.width - bounds.width) / 2, screen.y + (screen.height - bounds.height) / 3);
		shell.open();
		shell.setVisible(true);
		shell.forceActive();
		color.dispose();
		updater.schedule();
	}

	public void close() {
		int safetyCounter = 0;
		while (shell.getDisplay().readAndDispatch() && safetyCounter++ < 100 || messages.size() > 0) {}
		shell.close();
		shell.dispose();
		GAMA.setStartupMonitor(null);
	}

	public void increaseWorked() {
		final double remaining = 100 - worked;
		int divider = Math.min(messages.size(), (int) remaining);
		if (divider == 0) {
			divider = 1;
		}
		worked += remaining / divider;
	}

	@Override
	public String toString() {
		return DEFAULT_MSG;
	}

	@Override
	public void add(final String task) {
		messages.add(task);
	}

}
