package model;

import java.util.ArrayList;

public class Table extends ArrayList<Row> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HeaderRow headerRow;
	public Table() {
		
	}
	
	public void setHeaderRow(HeaderRow header) {
		this.headerRow = header;
	}

	public HeaderRow getHeaderRow() {
		return headerRow;
	}
	
	public Column getColumn(int index) {
		Column col = new Column();
		col.add(headerRow.get(index));
		for(int i = 0; i < this.size(); i++) {
			col.add(this.get(i).get(index));
		}
		return col;
	}
	
}
