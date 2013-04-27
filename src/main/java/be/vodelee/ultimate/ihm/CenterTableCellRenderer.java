package be.vodelee.ultimate.ihm;

import javax.swing.table.DefaultTableCellRenderer;

public class CenterTableCellRenderer extends DefaultTableCellRenderer{

	private static final long serialVersionUID = 1L;

	public CenterTableCellRenderer() {
		setHorizontalAlignment(CENTER);
		setVerticalAlignment(CENTER);
	}
	
}
