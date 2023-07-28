package dev.a7exschin.swt.ui.example;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

import java.util.HashMap;
import java.util.LinkedList;

public class ModernJavaAppUI {
    private Display display;
    private Shell shell;
    private LinkedList<String> installations;
    private LinkedList<String> workspaces;
    private List installationsList;
    private List workspacesList;
    private Text searchBar;

    private HashMap<String, Composite> installationsWidgets;
    private HashMap<String, Composite> workspacesWidgets;

    public ModernJavaAppUI() {
        // Create the Display and Shell
        display = new Display();
        shell = new Shell(display);
        shell.setText("Modern Java Application");
        shell.setSize(600, 400);
        shell.setLayout(new GridLayout(1, false)); // Single column layout

        // Initialize the installations and workspaces lists (replace this with your actual data)
        installations = new LinkedList<>();
        installations.add("Installation 1");
        installations.add("Installation 2");
        installations.add("Installation 3");

        workspaces = new LinkedList<>();
        workspaces.add("Workspace 1");
        workspaces.add("Workspace 2");
        workspaces.add("Workspace 3");

        // Create the search bar and add it to the shell's top (North) position
        searchBar = new Text(shell, SWT.SEARCH | SWT.BORDER);
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

        installationsWidgets = new HashMap<>();
        workspacesWidgets = new HashMap<>();

        // Add ModifyListener to the search bar (called whenever the text changes)
        searchBar.addModifyListener(new ModifyListener() {
            @Override
            public void modifyText(ModifyEvent e) {
                filterLists();
            }
        });

        // Initialize the lists with items
        createLists(tabFolder, installationsTab, workspacesTab);
        
        // Set the default selected tab to "Workspaces"
        tabFolder.setSelection(installationsTab);

        shell.open();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
        display.dispose();
    }

    private void createLists(TabFolder tabFolder, TabItem installationsTab, TabItem workspacesTab) {
        // Create a Composite to hold the installations
        Composite installationsComposite = new Composite(tabFolder, SWT.NONE);
        installationsComposite.setLayout(new GridLayout(1, false));
        installationsComposite.setLayoutData(new GridData(GridData.FILL_VERTICAL));
        installationsTab.setControl(installationsComposite);

        // Create a List to display the installations
        installationsList = new List(installationsComposite, SWT.FILL);
        installationsList.setFont(new Font(display, "Roboto", 18, SWT.NORMAL));
        installationsList.setLayoutData(new GridData(SWT.END, SWT.END, false, false));

        // Create a Composite to hold the workspaces
        Composite workspacesComposite = new Composite(tabFolder, SWT.NONE);
        workspacesComposite.setLayout(new GridLayout(1, false));
        workspacesComposite.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, true));
        workspacesTab.setControl(workspacesComposite);

        // Create a List to display the workspaces
        workspacesList = new List(workspacesComposite, SWT.FILL);
        workspacesList.setFont(new Font(display, "Roboto", 18, SWT.NORMAL));
        workspacesList.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, true));

        updateLists(); // Initialize the lists with items
    }

    private void updateLists() {
        updateList(installations, installationsWidgets, installationsList);
        updateList(workspaces, workspacesWidgets, workspacesList);
    }

    private void updateList(LinkedList<String> itemList, HashMap<String, Composite> widgetMap, List listWidget) {
        for (String item : itemList) {
            if (widgetMap.containsKey(item)) {
                widgetMap.get(item).dispose(); // Dispose the existing widget if it exists
            }

            // Create a custom composite for each list item
            Composite listItemComposite = new Composite(listWidget.getParent(), SWT.NONE);
            listItemComposite.setLayout(new GridLayout(2, false));
            listItemComposite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

            // Create a label to display the item text
            Label itemLabel = new Label(listItemComposite, SWT.NONE);
            itemLabel.setText(item);
            itemLabel.setFont(new Font(display, "Roboto", 18, SWT.NORMAL));
            itemLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

            // Create a delete button
            Button deleteButton = new Button(listItemComposite, SWT.PUSH);
            deleteButton.setText("Delete");
            deleteButton.setFont(new Font(display, "Roboto", 12, SWT.NORMAL));
            deleteButton.setLayoutData(new GridData(SWT.END, SWT.CENTER, false, false));
            deleteButton.addListener(SWT.Selection, e -> {
                itemList.remove(item);
                updateLists(); // Update the lists after deletion
            });

            widgetMap.put(item, listItemComposite);
        }
    }

    private void filterLists() {
        String searchQuery = searchBar.getText().toLowerCase();
        filterList(installations, installationsWidgets, installationsList, searchQuery.isEmpty() ? null : searchQuery);
        filterList(workspaces, workspacesWidgets, workspacesList, searchQuery.isEmpty() ? null : searchQuery);
    }

    private void filterList(LinkedList<String> originalList, HashMap<String, Composite> widgetMap, List listWidget, String searchQuery) {
        LinkedList<String> filteredItems = new LinkedList<>();
        if (searchQuery == null) {
            filteredItems.addAll(originalList); // Show all items
        } else {
            for (String item : originalList) {
                if (item.toLowerCase().contains(searchQuery)) {
                    filteredItems.add(item);
                }
            }
        }

        // Dispose all widgets in the list
        for (Composite composite : widgetMap.values()) {
            composite.dispose();
        }

        for (String item : filteredItems) {
            // Create a custom composite for each filtered list item
            Composite listItemComposite = new Composite(listWidget.getParent(), SWT.NONE);
            Label itemLabel = new Label(listItemComposite, SWT.NONE);
            itemLabel.setText(item);
            itemLabel.setFont(new Font(display, "Roboto", 18, SWT.NORMAL));
            itemLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

            // Create a delete button
            Button deleteButton = new Button(listItemComposite, SWT.PUSH);
            deleteButton.setText("Delete");
            deleteButton.setFont(new Font(display, "Roboto", 12, SWT.NORMAL));
            deleteButton.setLayoutData(new GridData(SWT.END, SWT.CENTER, false, false));
            deleteButton.addListener(SWT.Selection, e -> {
                originalList.remove(item);
                updateLists(); // Update the lists after deletion
            });

            widgetMap.put(item, listItemComposite);
        }

        listWidget.getParent().layout(true, true); // Trigger re-layout of the parent composite
    }

    public static void main(String[] args) {
        new ModernJavaAppUI();
    }
}

