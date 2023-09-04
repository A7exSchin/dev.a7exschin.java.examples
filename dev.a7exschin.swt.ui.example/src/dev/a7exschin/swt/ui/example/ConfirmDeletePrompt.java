package dev.a7exschin.swt.ui.example;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class ConfirmDeletePrompt extends Dialog {
	private Shell shell;
	private String message = "";
	private static Display display = Display.getDefault();

	public ConfirmDeletePrompt(Shell parent) {
		super(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.OK | SWT.CANCEL);
		shell = parent;
	}

	public ConfirmDeletePrompt(Shell parent, int style) {
		// Let users override the default styles
		super(parent, style);
		shell = parent;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void open() {
		shell.setText(getText());
		createContents(shell);
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	private void createContents(Shell shell) {
		shell.setLayout(new GridLayout(2, true));

		// Show the message
		Label label = new Label(shell, SWT.NONE);
		label.setText(message);
		label.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 2, 1));
		Text pathText = new Text(shell, SWT.BORDER);
		pathText.setEditable(false);
		pathText.setEnabled(false);
		pathText.setText("C://very/very/very/long/path");
		pathText.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 2, 1));
		Button checkboxParentFolder = new Button(shell, SWT.CHECK);
		checkboxParentFolder.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 2, 1));
		checkboxParentFolder.setText("Delete parent folder");

		Composite parentFolderComposite = new Composite(shell, SWT.BORDER);
		parentFolderComposite.setLayout(new GridLayout(2, false));
		GridData contentData = new GridData(SWT.CENTER, SWT.CENTER, true, true, 2, 5);
		parentFolderComposite.setLayoutData(contentData);

		Path testPath = Paths.get("D:\\eclipseInstallations\\A7exSchin-EclipseInstallationManager-main");
		LinkedList<Path> parentFolderContents = getFolderContents(testPath);
		
		Image warning = display.getSystemImage(SWT.ICON_WARNING);
		Label warningImage = new Label(parentFolderComposite, SWT.NONE);
		warningImage.setImage(warning);
		warningImage.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false, 1, 1));
		Label contentsLabel = new Label(parentFolderComposite, SWT.NONE);
		if (parentFolderContents.size() == 0) {
			contentsLabel.setText("The root folder is empty!");
		} else {
			contentsLabel.setText("The root folder contains the following directories and files:");
		}

		contentsLabel.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, true, false, 1, 1));

		for (Path path : parentFolderContents) {
			Label pathLabel = new Label(parentFolderComposite, SWT.NONE);
			pathLabel.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false, 2, 1));
			if (path.toFile().isFile()) {
				pathLabel.setText("File: " + path.getFileName().toString());
			} else if (path.toFile().isDirectory()) {
				pathLabel.setText("Directory: " + path.getFileName().toString());
			}

		}
		
		parentFolderComposite.setVisible(false);
		contentData.exclude = true;

		checkboxParentFolder.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				Button btn = (Button) e.getSource();
				boolean checked = btn.getSelection();

				if (checked) {
					parentFolderComposite.setVisible(true);
					contentData.exclude = false;
				} else {
					parentFolderComposite.setVisible(false);
					contentData.exclude = true;
				}
				parentFolderComposite.layout();
				parentFolderComposite.pack();
				shell.layout();
				shell.pack();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub

			}

		});

		Button cancelButton = new Button(shell, SWT.FLAT);
		cancelButton.setLayoutData(new GridData(SWT.END, SWT.END, true, false, 1, 1));
		Font arial = new Font(display, "Arial", 10, SWT.BOLD);
		cancelButton.setFont(arial);
		cancelButton.setText("Cancel");

		Button okButton = new Button(shell, SWT.FLAT);
		okButton.setLayoutData(new GridData(SWT.BEGINNING, SWT.END, true, false, 1, 1));
		okButton.setText("Delete");
		shell.pack();
	}

	public static void main(String[] args) {
		Shell shell = new Shell(display);
		shell.setSize(400, 200);
		ConfirmDeletePrompt prompt = new ConfirmDeletePrompt(shell);
		prompt.setText("Confirm Deletion");
		prompt.setMessage("Do you really want to delete this path?:");
		prompt.open();
	}

	public LinkedList<Path> getFolderContents(Path path) {
		LinkedList<Path> folderContents = new LinkedList<>();

		try {
			Stream<Path> paths = Files.walk(path, 1);

			paths.filter(p -> (!p.equals(path))).collect(Collectors.toCollection(() -> folderContents));

			paths.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		folderContents.sort(new Comparator<Path>() {

			@Override
			public int compare(Path p1, Path p2) {
				File f1 = p1.toFile();
				File f2 = p2.toFile();
				if (f1.isDirectory() && f2.isFile()) {
					return -1;
				} else if (f1.isDirectory() && f2.isDirectory()) {
					return 0;
				} else if (f1.isFile() && f2.isFile()) {
					return 0;
				}
				return 1;
			}
		});

		return folderContents;
	}
}
