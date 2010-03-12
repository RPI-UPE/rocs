package edu.rpi.rocs.client.ui.filters.helpers;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;

public class SelectableTableWidget extends Widget {
	private boolean selected[][];
	private int m_rows;
	private int m_cols;
	private Element m_element;
	private Element m_tbody;
	
	private native void addDocumentHandlers()/*-{
		if(window.rocs_mdown===undefined) {
			window.rocs_mdown = false;
			window.rocs_start_row = 0;
			window.rocs_start_col = 0;
			window.rocs_end_row = 0;
			window.rocs_end_col = 0;
			window.rocs_sc = 0;
			window.rocs_sr = 0;
			window.rocs_ec = 0;
			window.rocs_er = 0;
			window.min = function(x,y) { if(x>y) return y; return x; };
			window.max = function(x,y) { if(y>x) return y; return x; };
			window.startDrag = function(event) {
				if(event.button==0) {
					rocs_mdown=true;
					rocs_end_col = rocs_start_col = event.target.cellIndex;
					rocs_end_row = rocs_start_row = event.target.parentNode.rowIndex;
					event.target.className = "rocs-table-cursor rocs-table-possible";
					return false;
				}
			}
			window.stopDrag = function(event) {
				if(event.button==0 && rocs_mdown == true) {
					rocs_mdown = false;
					rocs_sr = min(max(rocs_start_row,1), max(rocs_end_row,1));
					rocs_sc = min(max(rocs_start_col,1), max(rocs_end_col,1));
					rocs_er = max(max(rocs_start_row,1), max(rocs_end_row,1));
					rocs_ec = max(max(rocs_start_col,1), max(rocs_end_col,1));
					widget = theTable.widget;
					for(row=rocs_sr;row<=rocs_er;row++) {
						for(col=rocs_sc;col<=rocs_ec;col++) {
							test = widget.@edu.rpi.rocs.client.ui.filters.helpers.SelectableTableWidget::isSelected(II)(row-1,col-1);
							widget.@edu.rpi.rocs.client.ui.filters.helpers.SelectableTableWidget::setSelected(IIZ)(row-1,col-1,!test);
						}
					}
					rocs_end_row = rocs_start_row = 0;
					rocs_end_col = rocs_start_col = 0;
					event.cancelBubble = true;
				}
			}
			window.continueDrag = function(event) {
				if(rocs_mdown==true) {
					rocs_end_col = event.target.cellIndex;
					rocs_end_row = event.target.parentNode.rowIndex;
					tsr = min(max(rocs_start_row,1),max(rocs_end_row,1));
					tsc = min(max(rocs_start_col,1),max(rocs_end_col,1));
					ter = max(max(rocs_start_row,1),max(rocs_end_row,1));
					tec = max(max(rocs_start_col,1),max(rocs_end_col,1));
					widget = theTable.widget;
					if(tsr > rocs_sr) {
						for(i=rocs_sc;i<=rocs_ec;i++) {
							theCell = theTable.rows[rocs_sr].cells[i];
							if(widget.@edu.rpi.rocs.client.ui.filters.helpers.SelectableTableWidget::isSelected(II)(rocs_sr-1,i-1))
								theCell.className = "rocs-table-cursor rocs-table-selected";
							else
								theCell.className = "rocs-table-cursor rocs-table-unselected";
						}
					}
					if(tsc > rocs_sc) {
						for(i=rocs_sr;i<=rocs_er;i++) {
							theCell = theTable.rows[i].cells[rocs_sc];
							if(widget.@edu.rpi.rocs.client.ui.filters.helpers.SelectableTableWidget::isSelected(II)(i-1,rocs_sc-1))
								theCell.className = "rocs-table-cursor rocs-table-selected";
							else
								theCell.className = "rocs-table-cursor rocs-table-unselected";
						}
					}
					if(ter < rocs_er) {
						for(i=rocs_sc;i<=rocs_ec;i++) {
							theCell = theTable.rows[rocs_er].cells[i];
							if(widget.@edu.rpi.rocs.client.ui.filters.helpers.SelectableTableWidget::isSelected(II)(rocs_er-1,i-1))
								theCell.className = "rocs-table-cursor rocs-table-selected";
							else
								theCell.className = "rocs-table-cursor rocs-table-unselected";
						}
					}
					if(tec < rocs_ec) {
						for(i=rocs_sr;i<=rocs_er;i++) {
							theCell = theTable.rows[i].cells[rocs_ec];
							if(widget.@edu.rpi.rocs.client.ui.filters.helpers.SelectableTableWidget::isSelected(II)(i-1,rocs_ec-1))
								theCell.className = "rocs-table-cursor rocs-table-selected";
							else
								theCell.className = "rocs-table-cursor rocs-table-unselected";
						}
					}
					rocs_sr = tsr; rocs_sc = tsc; rocs_er = ter; rocs_ec = tec;
					for(row=rocs_sr;row<=rocs_er;row++) {
						for(col=rocs_sc;col<=rocs_ec;col++) {
							theCell = theTable.rows[row].cells[col];
							theCell.className = "rocs-table-cursor rocs-table-possible";
						}
					}
				}
			}
			if(document.addEventListener)
				document.addEventListener('mouseup', stopDrag, false);
			else if(document.attachEvent)
				document.attachEvent("onmouseup", stopDrag);
		}
	}-*/;
	
	private native void setElementWidget(Element e, SelectableTableWidget w)/*-{
		e.widget = w;
		window.theTable = e;
	}-*/;
	
	private native void addEventHandlers(Element e)/*-{
		e.onmousedown = startDrag;
		e.onmouseup = stopDrag;
		e.onmouseover = continueDrag;
	}-*/;
	
	public SelectableTableWidget(int rows, int cols) {
		m_rows = rows;
		m_cols = cols;
		selected = new boolean[rows][cols];
		m_element = Document.get().createElement("table");
		m_element.setId("timetable");
		m_tbody = Document.get().createElement("tbody");
		m_element.appendChild(m_tbody);
		setElement(m_element);
		setElementWidget(m_element, this);
		addDocumentHandlers();
		Element t_row;
		Element t_col;
		for(int row=0;row<=rows;row++) {
			t_row = Document.get().createElement("tr");
			for(int col=0;col<=cols;col++) {
				t_col = Document.get().createElement("td");
				if(row>0&&col>0) {
					t_col.setClassName("rocs-table-cursor rocs-table-unselected");
					addEventHandlers(t_col);
				}
				else {
					t_col.setClassName("rocs-table-unselected");
				}
				t_row.appendChild(t_col);
			}
			m_tbody.appendChild(t_row);
		}
	}
	
	private native void setInternalSelected(int row, int col, boolean value)/*-{
		if(value) {
			window.theTable.rows[row].cells[col].className = "rocs-table-cursor rocs-table-selected";
		}
		else {
			window.theTable.rows[row].cells[col].className = "rocs-table-cursor rocs-table-unselected";
		}
	}-*/;
	
	public void setSelected(int row, int col, boolean value) {
		selected[row][col]=value;
		setInternalSelected(row+1,col+1,value);
	}
	
	public boolean isSelected(int row, int col) {
		if(row<0 || col<0) return false;
		if(row>=m_rows || col>=m_cols) return false;
		return selected[row][col];
	}
	
	public void setColumnHeader(int col, String text) {
		if(col<0 || col>=m_cols) return;
		Element e = m_tbody.getFirstChildElement(); // e is a <tr>
		e = e.getFirstChildElement();  // e is a <td>
		e = e.getNextSiblingElement();  // e is the first <td> that should be a header
		for(;e.getNextSiblingElement()!=null;e=e.getNextSiblingElement()) {
			if(col==0) {
				e.setInnerHTML(text);
				return;
			}
			else col--;
		}
		e.setInnerHTML(text);
	}
	
	public void setRowHeader(int row, String text) {
		if(row<0 || row>=m_rows) return;
		Element e= m_tbody.getFirstChildElement();
		e = e.getNextSiblingElement();
		for(;e.getNextSiblingElement()!=null;e=e.getNextSiblingElement()) {
			if(row==0) {
				e.getFirstChildElement().setInnerHTML(text);
				return;
			}
			else row--;
		}
		e.getFirstChildElement().setInnerHTML(text);
	}
}
