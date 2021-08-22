package view.bundle;

import javax.swing.table.AbstractTableModel;
import model.bundle.AMilliTakim;

public class TakimTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private static String[] colNames = {"Forma Number", "First Name", "Last Name", "Ratings"};
	private AMilliTakim takim;
	
	public TakimTableModel(AMilliTakim takim) {
		this.takim = takim;
	}
	
	public void setTakim(AMilliTakim takim) {
		this.takim = takim;
	}
	
	public AMilliTakim getTakim() {
		return takim;
	}

	@Override
	public int getColumnCount() {
		return colNames.length;
	}

	@Override
	public int getRowCount() {
		return takim == null ? 0 : takim.getCurrentSize();
	}
	
	public String getColumnName(int col) {
        return colNames[col];
    }

	@Override
	public Object getValueAt(int row, int column) {
		switch(column) {
		case 0:
			return takim.get(row).getFormaNo();
		case 1:
			return takim.get(row).getFirstName();
		case 2:
			return takim.get(row).getLastName();
		case 3:
			return takim.get(row).showRating();
		default:
			return "";
		}
	}
	
	@Override
	public void setValueAt(Object value, int row, int column) {
		switch(column) {
		case 0:
			takim.get(row).setFormaNo((int) value);
			break;
		case 1:
			takim.get(row).setFirstName((String) value);
			break;
		case 2:
			takim.get(row).setLastName((String) value);
			break;
		case 3:
			if(takim.get(row) != null)
				takim.get(row).changeRating();
			break;
		}
		fireTableCellUpdated(row, column);
	}
}
