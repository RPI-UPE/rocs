package edu.rpi.rocs.client.ui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Widget;

public class HTMLTableList extends Widget {
	protected Element m_divwrap;
	protected Element m_table;
	protected Element m_tbody;
	protected HTMLTableListHeader m_header;
	protected ArrayList<HTMLTableListRow> m_rows;
	
	public interface HTMLTableListSortColumnCallback {
		public ArrayList<HTMLTableListRow> sortTableData(HTMLTableList table, String column, SortMethod method);
	}
	
	public static enum SortMethod {
		Ascending,
		Descending,
		Unknown
	}
	
	public class HTMLTableListHeader implements Iterable<String> {
		private class HeaderObject implements Comparable<HeaderObject> {
			public String name="";
			@SuppressWarnings("unused")
			public boolean sortable=false;
			@SuppressWarnings("unused")
			public SortMethod current_sort=SortMethod.Unknown;
			@SuppressWarnings("unused")
			public HTMLTableListSortColumnCallback callback=null;
			public boolean asHTML=false;
			public int compareTo(HeaderObject o) {
				if(o == null) throw new NullPointerException();
				return this.name.compareTo(o.name);
			}
			@SuppressWarnings("unused")
			public boolean equals(HeaderObject o) {
				if(o==null) return false;
				return name.equals(o.name);
			}
		}
		
		private class HeaderIterator implements Iterator<String> {
			Iterator<HeaderObject> x;
			
			public HeaderIterator(Iterator<HeaderObject> parent) {
				x = parent;
			}

			public boolean hasNext() {
				return x.hasNext();
			}

			public String next() {
				return x.next().name;
			}

			public void remove() {
				
			}
			
		}
		
		protected ArrayList<HeaderObject> m_columns;
		
		public HTMLTableListHeader(boolean asHTML) {
			m_columns = new ArrayList<HeaderObject>();
		}
		
		public Element generateHeaderRow() {
			Element row = DOM.createTR();
			for(HeaderObject o : m_columns) {
				Element col = DOM.createTH();
				if(o.asHTML) {
					col.setInnerHTML(o.name);
				}
				else {
					col.setInnerText(o.name);
				}
				row.appendChild(col);
			}
			return row;
		}
		
		public void addColumn(String str) {
			addColumn(str, false, null);
		}
		
		public void addColumn(String str, boolean asHTML) {
			addColumn(str, asHTML, null);
		}
		
		public void addColumn(String str, HTMLTableListSortColumnCallback sorter) {
			addColumn(str, false, sorter);
		}
		
		public void addColumn(String str, boolean asHTML, HTMLTableListSortColumnCallback sorter) {
			HeaderObject temp = new HeaderObject();
			temp.name = str;
			if(sorter!=null) {
				temp.sortable = true;
				temp.callback = sorter;
			}
			temp.asHTML = asHTML;
			if(m_columns.contains(temp)) return;
			m_columns.add(temp);
		}
		
		public void removeColumn(String str) {
			HeaderObject temp = new HeaderObject();
			temp.name = str;
			m_columns.remove(temp);
		}

		public Iterator<String> iterator() {
			return new HeaderIterator(m_columns.iterator());
		}
	}
	
	public class HTMLTableListRow {
		private class Data {
			public String text;
			public boolean asHTML;
		}
		
		private HashMap<String, Data> m_data;
		
		public HTMLTableListRow() {
			m_data = new HashMap<String, Data>();
		}
		
		public void put(String column, String value) {
			put(column, value, false);
		}
		
		public void put(String column, String value, boolean asHTML) {
			Data newData = new Data();
			newData.text = value;
			newData.asHTML = asHTML;
			m_data.put(column, newData);
		}
		
		public String getText(String column) {
			if(m_data.containsKey(column)) {
				return m_data.get(column).text;
			}
			else return "";
		}
		
		public boolean isHTML(String column) {
			if(m_data.containsKey(column)) {
				return m_data.get(column).asHTML;
			}
			else return false;
		}
	}
	
	public HTMLTableList() {
		m_rows = new ArrayList<HTMLTableListRow>();
		m_divwrap = DOM.createDiv();
		setElement(m_divwrap);
		m_table = DOM.createTable();
		m_tbody = DOM.createTBody();
		m_table.appendChild(m_tbody);
		m_divwrap.appendChild(m_divwrap);
		Element temp = DOM.createTR();
		m_tbody.appendChild(temp);
	}
	
	public void setHeader(HTMLTableListHeader header) {
		if(header==null) return;
		m_header = header;
		Element newHeader = header.generateHeaderRow();
		m_tbody.insertBefore(newHeader, m_tbody.getFirstChild());
		m_tbody.removeChild(m_tbody.getFirstChild().getNextSibling());
	}
	
	public void addRowData(HTMLTableListRow row) {
		insertRow(row, m_rows.size());
	}
	
	public void addMultipleRows(Collection<? extends HTMLTableListRow> rows) {
		insertRows(rows, m_rows.size());
	}
	
	private Element trForRow(HTMLTableListRow row) {
		Element newElement = DOM.createTR();
		for(String column : m_header) {
			Element cell = DOM.createTD();
			String text = row.getText(column);
			boolean isHTML = row.isHTML(column);
			if(isHTML) {
				cell.setInnerHTML(text);
			}
			else {
				cell.setInnerText(text);
			}
			newElement.appendChild(cell);
		}
		return newElement;
	}
	
	public void insertRow(HTMLTableListRow row, int beforeIndex) {
		m_rows.add(beforeIndex, row);
		Element newElement = trForRow(row);
		
		// Begin insert algorithm
		Element index = m_tbody.getFirstChildElement();
		// Skip the first child element since it should be the header
		if(index != null) index = index.getNextSiblingElement();
		while(index != null && beforeIndex>0) {
			index = index.getNextSiblingElement();
			beforeIndex--;
		}
		if(index == null) {
			m_tbody.appendChild(newElement);
		}
		else {
			m_tbody.insertBefore(newElement, index);
		}
	}
	
	public void insertRows(Collection<? extends HTMLTableListRow> rows, int beforeIndex) {
		m_rows.addAll(beforeIndex, rows);
		Element index = m_tbody.getFirstChildElement();
		if(index != null) index = index.getNextSiblingElement();
		while(index != null && beforeIndex>0) {
			index = index.getNextSiblingElement();
			beforeIndex--;
		}
		if(index == null) {
			for(HTMLTableListRow row : rows) {
				m_tbody.appendChild(trForRow(row));
			}
		}
		else {
			for(HTMLTableListRow row : rows) {
				m_tbody.insertBefore(trForRow(row), index);
			}
		}
	}
	
	public void setRowData(ArrayList<HTMLTableListRow> rows) {
		m_rows = new ArrayList<HTMLTableListRow>(rows);
		while(m_tbody.hasChildNodes()) {
			m_tbody.removeChild(m_tbody.getFirstChild());
		}
		m_tbody.appendChild(m_header.generateHeaderRow());
		for(HTMLTableListRow row : m_rows) {
			m_tbody.appendChild(trForRow(row));
		}
	}
	
	public void removeRowData(HTMLTableListRow row) {
		
	}
	
	public void clear() {
		m_rows.clear();
		while(m_tbody.hasChildNodes()) {
			m_tbody.removeChild(m_tbody.getFirstChild());
		}
		m_tbody.appendChild(m_header.generateHeaderRow());
	}
}
