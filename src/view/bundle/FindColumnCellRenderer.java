package view.bundle;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import model.bundle.Found;

public class FindColumnCellRenderer extends DefaultTableCellRenderer{

	private static final long serialVersionUID = 1L;
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
		Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
		Color bgColor = ((TakimTableModel) table.getModel()).getTakim().get(row).getFoundStatus() == Found.FOUND ? Color.RED : table.getBackground();
		component.setBackground(bgColor);
		return component;
	}
}
