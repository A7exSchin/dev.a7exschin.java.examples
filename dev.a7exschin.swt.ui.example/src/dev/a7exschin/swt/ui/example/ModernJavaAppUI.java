package dev.a7exschin.swt.ui.example;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

public class ModernJavaAppUI {

    public static void main(String[] args) {
        Display display = new Display();
        Shell shell = new Shell(display);
        shell.setText("Tree List with Buttons");
        shell.setSize(400, 400);
        shell.setLayout(new FillLayout());

        Tree tree = new Tree(shell, SWT.BORDER | SWT.V_SCROLL);
        tree.setHeaderVisible(true);

        Font boldFont = new Font(display, "Arial", 12, SWT.BOLD);

        // Create columns in the tree
        TreeColumn column1 = new TreeColumn(tree, SWT.LEFT);
        column1.setText("Items");
        column1.setWidth(200);

        TreeColumn column2 = new TreeColumn(tree, SWT.LEFT);
        column2.setText("Actions");
        column2.setWidth(150);

        // Create root item
        TreeItem rootItem = new TreeItem(tree, SWT.NONE);
        rootItem.setText("Root Item");
        rootItem.setFont(boldFont);

        // Create sub-items under the root item
        TreeItem subItem1 = new TreeItem(rootItem, SWT.NONE);
        subItem1.setText("Sub Item 1");
        createButtonComposite(subItem1);

        TreeItem subItem2 = new TreeItem(rootItem, SWT.NONE);
        subItem2.setText("Sub Item 2");
        createButtonComposite(subItem2);

        // Create another root item
        TreeItem rootItem2 = new TreeItem(tree, SWT.NONE);
        rootItem2.setText("Another Root Item");
        rootItem2.setFont(boldFont);

        // Create sub-items under the second root item
        TreeItem subItem3 = new TreeItem(rootItem2, SWT.NONE);
        subItem3.setText("Sub Item 3");
        createButtonComposite(subItem3);

        TreeItem subItem4 = new TreeItem(rootItem2, SWT.NONE);
        subItem4.setText("Sub Item 4");
        createButtonComposite(subItem4);

        shell.open();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
        display.dispose();
    }

    // Helper method to create a button composite for each tree item
    private static void createButtonComposite(TreeItem treeItem) {
        Composite buttonComposite = new Composite(treeItem.getParent(), SWT.NONE);
        GridLayout layout = new GridLayout(1, false);
        layout.marginWidth = layout.marginHeight = 0;
        buttonComposite.setLayout(layout);

        Button button = new Button(buttonComposite, SWT.PUSH);
        button.setText("Action");
        button.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false));
        button.addListener(SWT.Selection, e -> {
            // Action to be performed when the button is clicked
            System.out.println("Button clicked for item: " + treeItem.getText());
        });

        treeItem.setData(buttonComposite);
    }
}



