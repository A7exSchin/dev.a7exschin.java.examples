package dev.a7exschin.swt.ui.example;

import java.util.LinkedList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;

public class SWTExample {

	private static LinkedList<String> installations;
	private static LinkedList<String> workspaces;

	public static void main(String[] args) {
		// Create the Display and Shell
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setText("Modern Java Application");
		shell.setSize(600, 400);
		shell.setLayout(new GridLayout(1, false)); // Single column layout

		// Initialize the installations and workspaces lists (replace this with your
		// actual data)
		installations = new LinkedList<>();
		installations.add("Installation 1");
		installations.add("Installation 2");
		installations.add("Installation 3");

		workspaces = new LinkedList<>();
		workspaces.add("Workspace 1");
		workspaces.add("Workspace 2");
		workspaces.add("Workspace 3");

		// Create the search bar and add it to the shell's top (North) position
		Text searchBar = new Text(shell, SWT.SEARCH | SWT.BORDER);
		searchBar.setFont(new Font(display, "Roboto", 16, SWT.NORMAL));
		searchBar.setMessage("Search...");
		searchBar.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		// Create a CTabFolder to hold the tabs
		TabFolder tabFolder = new TabFolder(shell, SWT.NONE);
		tabFolder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		// Create the "Installations" tab
		TabItem installationsTab = new TabItem(tabFolder, SWT.NONE);
		installationsTab.setText("Installations");

		// Create the "Workspaces" tab
		TabItem workspacesTab = new TabItem(tabFolder, SWT.NONE);
		workspacesTab.setText("Workspaces");

		// Add ModifyListener to the search bar (called whenever the text changes)
		searchBar.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				filterLists();
			}

			private void filterLists() {
				// TODO Auto-generated method stub

			}
		});

		Composite installationTabComposite = new Composite(tabFolder, SWT.NONE);
		installationTabComposite.setLayout(new GridLayout(1, false));
		installationTabComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		installationsTab.setControl(installationTabComposite);

		for (String string : installations) {
			Composite listItemComposite = new Composite(installationTabComposite, SWT.BORDER);
			listItemComposite.setLayout(new GridLayout(2, false));
			listItemComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
			Label nameLabel = new Label(listItemComposite, SWT.SHADOW_OUT | SWT.HORIZONTAL);
			nameLabel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
			nameLabel.setText(string);
		}

		// Set the default selected tab to "Workspaces"
		tabFolder.setSelection(installationsTab);

		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
}
