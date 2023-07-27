package dev.a7exschin.swt.ui.example;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

import java.util.LinkedList;

public class SWTExample {
	
	static LinkedList<String> strings = new LinkedList<>();
	
	public static void main(String[] args) {
		generateStrings();
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setText("Example for UI Design with SWT");

		Menu bar = new Menu(shell, SWT.BAR);
		shell.setMenuBar(bar);

		MenuItem fileItem = new MenuItem(bar, SWT.CASCADE);
		fileItem.setText("&File");

		Menu submenu = new Menu(shell, SWT.DROP_DOWN);
		fileItem.setMenu(submenu);

		MenuItem item = new MenuItem(submenu, SWT.PUSH);
		item.addListener(SWT.Selection, e -> System.out.println("Select All"));
		item.setText("Select &All\tCtrl+A");
		item.setAccelerator(SWT.MOD1 + 'A');
		shell.setSize(500, 700);

		final TabFolder tabFolder = new TabFolder (shell, SWT.BORDER);
		tabFolder.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		TabItem tab1 = new TabItem(tabFolder, SWT.NONE);
		tab1.setText("Tab 1");
		
		final Composite c = new Composite(tabFolder, SWT.None);
		c.setLayout(new GridLayout());
		
		Group tab1Group = new Group(c,SWT.None);
		tab1Group.setLayout(new GridLayout());
		tab1Group.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		for (String string : strings) {
			Label label = new Label(tab1Group, SWT.None);
			label.setText(string);
			label.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		}
		
		tab1.setControl(c);
		
		TabItem tab2 = new TabItem(tabFolder, SWT.NONE);
		tab2.setText("Tab 2");
		
		tabFolder.pack();
		shell.setLayout(new FillLayout());

		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
	
	private static void generateStrings() {
		for (int i = 0; i < 12; i++) {
			strings.add("String " + i);
		}
	}
}
