package be.vodelee.ultimate.ihm;

import java.awt.Font;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

public class UltimateJTable extends JTable{

	private static final long serialVersionUID = 1L;

	public UltimateJTable(AbstractTableModel abstractTableModel, int taillePolice) {
		super(abstractTableModel);
		getTableHeader().setReorderingAllowed(false);
		getTableHeader().setFont(new Font("Serif",Font.BOLD|Font.ITALIC,taillePolice));
		setDefaultRenderer(Object.class, new CenterTableCellRenderer());
	}
}
