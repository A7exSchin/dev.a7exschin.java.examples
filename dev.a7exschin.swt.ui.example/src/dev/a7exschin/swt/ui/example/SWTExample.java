package dev.a7exschin.swt.ui.example;

import java.util.Collections;
import java.util.LinkedList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

public class SWTExample {
	
	private static Display display = new Display();
	private static Shell shell = new Shell(display);
	private static Text searchBar;
	private static ScrolledComposite scrolled;
	
	private static LinkedList<ListComplexClass> installations;
	private static LinkedList<ListComplexClass> workspaces;
	private static LinkedList<ListComplexClass> shownInstallations;
	private static Color lightBlue = new Color(new RGB(158, 180, 240));
	private static Color white = new Color(new RGB(255, 255, 255));
	private static Color lightGray = new Color(new RGB(240, 240, 240));

	public static void main(String[] args) {
		// Create the Display and Shell
		shell.setText("Modern Java Application");
		shell.setSize(600, 400);
		shell.setLayout(new GridLayout(1, false)); // Single column layout

		// Initialize the installations and workspaces lists (replace this with your
		// actual data)
		installations = new LinkedList<>();
		for (int i = 1; i < 10; i++) {
			LinkedList<String> strings = new LinkedList<>();
			Collections.addAll(strings, "Subitem 1", "Subitem 2", "Subitem 3");
			installations.add(new ListComplexClass("List Item " + i,
					"Very/very/very/very/very/very/long/location/path " + i, strings));
		}

		workspaces = new LinkedList<>();
		for (int i = 1; i < 4; i++) {
			LinkedList<String> strings = new LinkedList<>();
			Collections.addAll(strings, "Subitem 1", "Subitem 2", "Subitem 3");
			workspaces.add(new ListComplexClass("Second Tab Item " + i, "Second Tab Item Location " + i, strings));
		}
		
		shownInstallations = new LinkedList<>();
		shownInstallations.addAll(installations);
		
		// Create the search bar and add it to the shell's top (North) position
		searchBar = new Text(shell, SWT.SEARCH | SWT.BORDER);
		searchBar.setFont(new Font(display, "Roboto", 16, SWT.NORMAL));
		searchBar.setMessage("Search...");
		searchBar.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		// Create a CTabFolder to hold the tabs
		TabFolder tabFolder = new TabFolder(shell, SWT.NONE);
		tabFolder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		// Create the "Installations" tab
		TabItem tab1 = new TabItem(tabFolder, SWT.NONE);
		tab1.setText("First Tab");

		// Create the "Workspaces" tab
		TabItem tab2 = new TabItem(tabFolder, SWT.NONE);
		tab2.setText("Second Tab");

		// Add ModifyListener to the search bar (called whenever the text changes)
		searchBar.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				filterLists();
			}
		});
		scrolled = new ScrolledComposite(tabFolder, SWT.V_SCROLL);
		scrolled.setExpandHorizontal(true);
		scrolled.setExpandVertical(true);
		scrolled.setAlwaysShowScrollBars(true);
		scrolled.setMinSize(500, 700);
		tab1.setControl(scrolled);
		
		generateTabContents();
		
		scrolled.addListener(SWT.Resize, event -> {
			final int width = scrolled.getClientArea().width;
			scrolled.setMinSize(scrolled.getContent().computeSize(width, SWT.DEFAULT));
		});
		

		// Set the default selected tab to "Workspaces"
		tabFolder.setSelection(tab1);

		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
	
	private static void filterLists() {
        String searchQuery = searchBar.getText().toLowerCase();
        filterList(installations, searchQuery.isEmpty() ? null : searchQuery);
        //filterList(workspaces, searchQuery.isEmpty() ? null : searchQuery);
    }
	
	private static void filterList(LinkedList<ListComplexClass> originalList, String searchQuery) {
		LinkedList<ListComplexClass> filteredList = new LinkedList<>();
		if(searchQuery == null ) {
			filteredList.addAll(originalList);
		} else {
			for (ListComplexClass listComplexClass : originalList) {
				if(listComplexClass.getName().toLowerCase().contains(searchQuery)) {
					filteredList.add(listComplexClass);
				}
			}
		}
		
		// Get rid of old Composite
		scrolled.getContent().dispose();
		
		//Update externalList with filtered Items
		shownInstallations = filteredList;
		
		generateTabContents();
		
		int width = scrolled.getClientArea().width;
		scrolled.setMinSize(scrolled.getContent().computeSize(width, SWT.DEFAULT));
		scrolled.layout(true, true);
		scrolled.requestLayout();
	}
	
	private static void generateTabContents() {
		Composite installationTabComposite = new Composite(scrolled, SWT.NONE);
		installationTabComposite.setLayout(new GridLayout(1, false));
		installationTabComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		installationTabComposite.setBackground(new Color(new RGB(240, 240, 240)));
		
		installationTabComposite.requestLayout();
		
		scrolled.setContent(installationTabComposite);
		
		for (ListComplexClass entry : shownInstallations) {
			Composite listItemComposite = new Composite(installationTabComposite, SWT.NONE);
			GridLayout listItemLayout = new GridLayout(2, false); // Set GridLayout with 2 columns
			listItemLayout.marginWidth = 5; // Remove the default margin
			listItemLayout.horizontalSpacing = 10; // Add spacing between the items
			listItemComposite.setLayout(listItemLayout);
			GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
			gridData.grabExcessHorizontalSpace = true;
			listItemComposite.setLayoutData(gridData); // Set to fill horizontally

			Composite labelComposite = new Composite(listItemComposite, SWT.NONE);
			labelComposite.setLayout(new GridLayout(1, false));
			labelComposite.setLayoutData(new GridData(SWT.LEFT, SWT.BEGINNING, true, false));
			labelComposite.setBackground(white);

			Label nameLabel = new Label(labelComposite, SWT.NULL);
			nameLabel.setFont(new Font(display, "Roboto", 16, SWT.NORMAL));
			nameLabel.setText(entry.getName());
			nameLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

			// ToolBar for the Buttons on the right
			ToolBar tools = new ToolBar(listItemComposite, SWT.FLAT);
			tools.setLayoutData(new GridData(SWT.END, SWT.CENTER, false, false));
			tools.setBackground(white);

			ToolItem deleteItem = new ToolItem(tools, SWT.PUSH);
			deleteItem.setImage(new Image(display, "icons/trashCan.png"));

			Label descrLabel = new Label(labelComposite, SWT.NULL);
			descrLabel.setFont(new Font(display, "Roboto", 10, SWT.NORMAL));
			descrLabel.setText(entry.getDescription());
			descrLabel.setLayoutData(new GridData(SWT.LEFT, SWT.BOTTOM, true, false));
			descrLabel.setForeground(new Color(new RGB(112, 115, 125)));

			listItemComposite.setBackground(white);
			nameLabel.setBackground(white);
			descrLabel.setBackground(white);
			Listener changeColorEnterListener = new Listener() {

				@Override
				public void handleEvent(Event event) {
					listItemComposite.setBackground(lightBlue);
					labelComposite.setBackground(lightBlue);
					nameLabel.setBackground(lightBlue);
					tools.setBackground(lightBlue);
					deleteItem.setImage(new Image(display, "icons/trashCanLightBlue.png"));
					descrLabel.setBackground(lightBlue);
					listItemComposite.setCursor(new Cursor(display, SWT.CURSOR_HAND));
					// nameLabel.setCursor(new Cursor(display, SWT.CURSOR_HAND));
				}
			};

			Listener changeColorExitListener = new Listener() {

				@Override
				public void handleEvent(Event event) {
					listItemComposite.setBackground(white);
					labelComposite.setBackground(white);
					tools.setBackground(white);
					deleteItem.setImage(new Image(display, "icons/trashCan.png"));
					nameLabel.setBackground(white);
					descrLabel.setBackground(white);
				}
			};
			Listener changeColorDeleteEnterListener = new Listener() {

				@Override
				public void handleEvent(Event event) {
					listItemComposite.setBackground(lightBlue);
					labelComposite.setBackground(lightBlue);
					nameLabel.setBackground(lightBlue);
					tools.setBackground(lightBlue);
					deleteItem.setImage(new Image(display, "icons/trashCan.png"));
					descrLabel.setBackground(lightBlue);
					listItemComposite.setCursor(new Cursor(display, SWT.CURSOR_HAND));
				}
			};

			listItemComposite.addListener(SWT.MouseEnter, changeColorEnterListener);
			listItemComposite.addListener(SWT.MouseExit, changeColorExitListener);
			labelComposite.addListener(SWT.MouseEnter, changeColorEnterListener);
			labelComposite.addListener(SWT.MouseExit, changeColorExitListener);
			nameLabel.addListener(SWT.MouseEnter, changeColorEnterListener);
			nameLabel.addListener(SWT.MouseExit, changeColorExitListener);
			descrLabel.addListener(SWT.MouseEnter, changeColorEnterListener);
			descrLabel.addListener(SWT.MouseExit, changeColorExitListener);
			tools.addListener(SWT.MouseEnter, changeColorDeleteEnterListener);

			// Begin Content
			Composite content = new Composite(listItemComposite, SWT.BORDER | SWT.CENTER);
			content.setLayout(new GridLayout(1, false));
			GridData data = new GridData(SWT.FILL, SWT.FILL, false, false);
			content.setLayoutData(data);
			content.setBackground(lightGray);

			listItemComposite.addMouseListener(new MouseListener() {
				private boolean doubleClick;

				@Override
				public void mouseUp(MouseEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mouseDown(MouseEvent e) {
					doubleClick = false;

					// Set doubleclick time to 150ms
					int time = Display.getDefault().getDoubleClickTime() - 350;
					Display.getDefault().timerExec(time, new Runnable() {
						public void run() {
							if (!doubleClick) {
								if (e.button == 3) {
									System.out.println("Single Right Click Event wubwub!");
								} else {
									System.out.println("Single Left Click Event woooooo!");
									data.exclude = !data.exclude;
									content.setVisible(!data.exclude);
									content.getParent().pack();
									content.layout();
									content.getParent().requestLayout();
									int width = scrolled.getClientArea().width;
									scrolled.setMinSize(scrolled.getContent().computeSize(width, SWT.DEFAULT));
									scrolled.layout(true, true);
									scrolled.requestLayout();
								}

							}
						}
					});

				}

				@Override
				public void mouseDoubleClick(MouseEvent e) {
					doubleClick = true;
					System.out.println("DOUBLE CLICK YEAH!");

				}
			});

			for (String string : entry.getAssignedStrings()) {
				Composite contentItemComposite = new Composite(content, SWT.NONE);
				GridLayout contentItemLayout = new GridLayout(2, false); // Set GridLayout with 2 columns
				contentItemLayout.marginWidth = 5; // Remove the default margin
				contentItemLayout.horizontalSpacing = 10; // Add spacing between the items
				contentItemComposite.setLayout(contentItemLayout);
				GridData contentGridData = new GridData(GridData.FILL_HORIZONTAL | GridData.HORIZONTAL_ALIGN_FILL);
				gridData.grabExcessHorizontalSpace = true;
				contentItemComposite.setLayoutData(contentGridData); // Set to fill horizontally
				contentItemComposite.setBackground(lightGray);

				Composite contentLabelComposite = new Composite(contentItemComposite, SWT.NONE);
				contentLabelComposite.setLayout(new GridLayout(1, false));
				contentLabelComposite.setLayoutData(new GridData(SWT.LEFT, SWT.BEGINNING, true, false));
				contentLabelComposite.setBackground(lightGray);

				Label contentNameLabel = new Label(contentLabelComposite, SWT.NULL);
				contentNameLabel.setFont(new Font(display, "Roboto", 16, SWT.NORMAL));
				contentNameLabel.setText(string);
				contentNameLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
				contentNameLabel.setBackground(lightGray);

				// ToolBar for the Buttons on the right
				ToolBar contentTools = new ToolBar(contentItemComposite, SWT.FLAT);
				contentTools.setLayoutData(new GridData(SWT.END, SWT.CENTER, false, false));
				contentTools.setBackground(lightGray);

				ToolItem contentDeleteItem = new ToolItem(contentTools, SWT.PUSH);
				contentDeleteItem.setImage(new Image(display, "icons/trashCanLightGray.png"));

				Label contentdescriptionLabel = new Label(contentLabelComposite, SWT.NULL);
				contentdescriptionLabel.setFont(new Font(display, "Roboto", 10, SWT.NORMAL));
				contentdescriptionLabel.setText(entry.getDescription());
				contentdescriptionLabel.setLayoutData(new GridData(SWT.LEFT, SWT.BOTTOM, true, false));
				contentdescriptionLabel.setForeground(new Color(new RGB(112, 115, 125)));
				contentdescriptionLabel.setBackground(lightGray);

			}
			data.exclude = !data.exclude;
			content.setVisible(!data.exclude);
			content.getParent().pack();
			content.layout();
			content.getParent().requestLayout();
		}
	}

}
