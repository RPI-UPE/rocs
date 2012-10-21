package edu.rpi.rocs.client.ui.filters.helpers;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;

import edu.rpi.rocs.client.ui.filters.ScheduleTimeBlockFilterWidget;

public class SelectableTableWidget extends Widget {
	private boolean selected[][];
	private int m_rows;
	private int m_cols;
	private Element m_element;
	private Element m_tbody;
	protected transient ScheduleTimeBlockFilterWidget owner = null;
	
	public void setOwner(ScheduleTimeBlockFilterWidget owner) {
		this.owner = owner;
	}
	
	private native void addDocumentHandlers()/*-{
		if($wnd._rocs_init===undefined) {
			$wnd._rocs_init = true;
			$wnd.callback = function(element,func) {
				return function(e) {
					return func.call(element,element,e);
				};
			}
			$wnd.getParent = function(e) { return e.parentNode ? e.parentNode : e.parentElement; };
			$wnd.min = function(x,y) { if(x>y) return y; return x; };
			$wnd.max = function(x,y) { if(y>x) return y; return x; };
			$wnd.startDrag = function(obj,event) {
				var e = event || $wnd.event;
				if(e.target == undefined)
					e.target = e.srcElement;
				if(e.button==1 && $wnd.event != null || e.button == 0) {
					obj.mdown = true;
					obj.end_col = obj.start_col = e.target.cellIndex;
					obj.end_row = obj.start_row = $wnd.getParent(e.target).rowIndex;
					e.target.className = "rocs-table-cursor rocs-table-possible";
					obj.startValue = !obj.widget.@edu.rpi.rocs.client.ui.filters.helpers.SelectableTableWidget::isSelected(II)(obj.start_row-1,obj.start_col-1);
					if($wnd.addEventListener) return false;
				}
			}
			$wnd.stopDrag = function(obj,event) {
				var e = $wnd.event || event;
				if(e.target == undefined)
					e.target = e.srcElement;
				if(obj.mdown==true) {
					obj.mdown = false;
					var a = $wnd.max(obj.start_row,1);
					var b = $wnd.max(obj.end_row,1);
					var c = $wnd.max(obj.start_col,1);
					var d = $wnd.max(obj.end_col,1);
					obj.sr = $wnd.min(a,b);
					obj.sc = $wnd.min(c,d);
					obj.er = $wnd.max(a,b);
					obj.ec = $wnd.max(c,d);
					var widget = obj.widget;
					var row, col;
					for(row=obj.sr;row<=obj.er;row++) {
						for(col=obj.sc;col<=obj.ec;col++) {
							widget.@edu.rpi.rocs.client.ui.filters.helpers.SelectableTableWidget::setSelected(IIZ)(row-1,col-1,obj.startValue);
						}
					}
					widget.@edu.rpi.rocs.client.ui.filters.helpers.SelectableTableWidget::onChange()();
					obj.sr = obj.sc = obj.er = obj.ec = 1;
					e.cancelBubble = true;
				}
			}
			$wnd.continueDrag = function(obj,event) {
				var e = $wnd.event || event;
				if(e.target == undefined)
					e.target = e.srcElement;
				if(obj.mdown==true) {
					obj.end_col = e.target.cellIndex;
					obj.end_row = $wnd.getParent(e.target).rowIndex;
					var a = $wnd.max(obj.start_row,1);
					var b = $wnd.max(obj.end_row,1);
					var c = $wnd.max(obj.start_col,1);
					var d = $wnd.max(obj.end_col,1);
					var tsr = $wnd.min(a,b);
					var tsc = $wnd.min(c,d);
					var ter = $wnd.max(a,b);
					var tec = $wnd.max(c,d);
					var widget = this.widget;
					var i, row, col;
					if(tsr > obj.sr) {
						for(i=obj.sc;i<=obj.ec;i++) {
							var theCell = obj.rows[obj.sr].cells[i];
							if(widget.@edu.rpi.rocs.client.ui.filters.helpers.SelectableTableWidget::isSelected(II)(obj.sr-1,i-1))
								theCell.className = "rocs-table-cursor rocs-table-selected";
							else
								theCell.className = "rocs-table-cursor rocs-table-unselected";
						}
					}
					if(tsc > obj.sc) {
						for(i=obj.sr;i<=obj.er;i++) {
							var theCell = obj.rows[i].cells[obj.sc];
							if(widget.@edu.rpi.rocs.client.ui.filters.helpers.SelectableTableWidget::isSelected(II)(i-1,obj.sc-1))
								theCell.className = "rocs-table-cursor rocs-table-selected";
							else
								theCell.className = "rocs-table-cursor rocs-table-unselected";
						}
					}
					if(ter < obj.er) {
						for(i=obj.sc;i<=obj.ec;i++) {
							var theCell = obj.rows[obj.er].cells[i];
							if(widget.@edu.rpi.rocs.client.ui.filters.helpers.SelectableTableWidget::isSelected(II)(obj.er-1,i-1))
								theCell.className = "rocs-table-cursor rocs-table-selected";
							else
								theCell.className = "rocs-table-cursor rocs-table-unselected";
						}
					}
					if(tec < obj.ec) {
						for(i=obj.sr;i<=obj.er;i++) {
							var theCell = obj.rows[i].cells[obj.ec];
							if(widget.@edu.rpi.rocs.client.ui.filters.helpers.SelectableTableWidget::isSelected(II)(i-1,obj.ec-1))
								theCell.className = "rocs-table-cursor rocs-table-selected";
							else
								theCell.className = "rocs-table-cursor rocs-table-unselected";
						}
					}
					obj.sr = tsr; obj.sc = tsc; obj.er = ter; obj.ec = tec;
					for(row=obj.sr;row<=obj.er;row++) {
						for(col=obj.sc;col<=obj.ec;col++) {
							var theCell = obj.rows[row].cells[col];
							theCell.className = "rocs-table-cursor rocs-table-possible";
						}
					}
				}
			}
		}
	}-*/;
	
	private native void setElementWidget(Element e, SelectableTableWidget w)/*-{
		e.widget = w;
		e.mdown = false;
		e.start_row = e.start_col = e.end_row = e.end_col = 0;
		e.sr = e.sc = e.er = e.ec = 1;
		e.startValue = false;
		e.onmousedown = function () { return false; };
		e.onselectstart = function() { return false; };
		if($doc.attachEvent)
			$doc.attachEvent("onmouseup",$wnd.callback(e,$wnd.stopDrag));
		else
			$doc.addEventListener("mouseup",$wnd.callback(e,$wnd.stopDrag),false);
	}-*/;
	
	private native void addEventHandlers(Element e, Element theTable)/*-{
		if($wnd.attachEvent) {
			e.attachEvent("onmousedown",$wnd.callback(theTable,$wnd.startDrag));
			e.attachEvent("onmousemove",$wnd.callback(theTable,$wnd.continueDrag));
			e.attachEvent("onmouseup",$wnd.callback(theTable,$wnd.stopDrag));
			e.attachEvent("onselectstart",function() { return false; });
		}
		else {
			e.addEventListener("mousedown",$wnd.callback(theTable,$wnd.startDrag),false);
			e.addEventListener("mousemove",$wnd.callback(theTable,$wnd.continueDrag),false);
			e.addEventListener("mouseup",$wnd.callback(theTable,$wnd.stopDrag),false);
			e.addEventListener("selectstart",function() { return false; }, false);
		}
	}-*/;
	/*e.onselectstart = function(e) { $wnd.startDrag(e); return true; };*/
	
	public SelectableTableWidget(int rows, int cols) {
		m_rows = rows;
		m_cols = cols;
		selected = new boolean[rows][cols];
		m_element = Document.get().createElement("table");
		m_element.setId("timetable");
		m_tbody = Document.get().createElement("tbody");
		m_element.appendChild(m_tbody);
		addDocumentHandlers();
		Element t_row;
		Element t_col;
		for(int row=0;row<=rows;row++) {
			t_row = Document.get().createElement("tr");
			for(int col=0;col<=cols;col++) {
				t_col = Document.get().createElement("td");
				if(row>0&&col>0) {
					t_col.setClassName("rocs-table-cursor rocs-table-unselected");
					addEventHandlers(t_col, m_element);
				}
				else {
					t_col.setClassName("rocs-table-unselected");
				}
				t_row.appendChild(t_col);
			}
			m_tbody.appendChild(t_row);
		}
		setElement(m_element);
		//setElementWidget(m_element, this);
	}
	
	@Override
	public void onAttach() {
		super.onAttach();
		setElementWidget(m_element, this);
	}
	
	private native void setInternalSelected(Element theTable, int row, int col, boolean value)/*-{
		if(value) {
			theTable.rows[row].cells[col].className = "rocs-table-cursor rocs-table-selected";
		}
		else {
			theTable.rows[row].cells[col].className = "rocs-table-cursor rocs-table-unselected";
		}
	}-*/;
	
	public void setSelected(int row, int col, boolean value) {
		selected[row][col]=value;
		setInternalSelected(m_element, row+1,col+1,value);
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
	
	protected void onChange() {
		owner.tableChanged();
	}
}
