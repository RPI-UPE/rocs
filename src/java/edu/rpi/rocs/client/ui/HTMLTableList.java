package edu.rpi.rocs.client.ui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasMouseOutHandlers;
import com.google.gwt.event.dom.client.HasMouseOverHandlers;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Widget;

public class HTMLTableList extends Widget implements List<HTMLTableList.HTMLTableListRow>, 
	HasClickHandlers, HasMouseOutHandlers, HasMouseOverHandlers {
	protected Element m_divwrap;
	protected Element m_table;
	protected Element m_tbody;
	protected ArrayList<HTMLTableListRow> m_rows=new ArrayList<HTMLTableList.HTMLTableListRow>();
	
	public class HTMLTableListCell extends Widget implements HasClickHandlers, HasMouseOverHandlers, HasMouseOutHandlers {
		public Element m_td=null;
		
		public HTMLTableListCell() {
			this(false);
		}
		
		public HTMLTableListCell(boolean t) {
			m_td = (t? DOM.createTH() : DOM.createTD());
			setElement(m_td);
		}
		
		public void setText(String text) {
			m_td.setInnerText(text);
		}
		
		public void setHTML(String html) {
			m_td.setInnerHTML(html);
		}
		
		public void setWidget(Widget w) {
			m_td.appendChild(w.getElement());
		}

		public HandlerRegistration addClickHandler(ClickHandler arg0) {
			return addDomHandler(arg0, ClickEvent.getType());
		}

		public HandlerRegistration addMouseOverHandler(MouseOverHandler arg0) {
			return addDomHandler(arg0, MouseOverEvent.getType());
		}

		public HandlerRegistration addMouseOutHandler(MouseOutHandler arg0) {
			return addDomHandler(arg0, MouseOutEvent.getType());
		}
		
		public void attach() {
			onAttach();
		}
		
		public void detach() {
			onDetach();
		}
	}
	
	public class HTMLTableListRow extends Widget implements List<HTMLTableListCell>, HasClickHandlers, HasMouseOverHandlers, HasMouseOutHandlers {
		public Element m_tr=null;
		public ArrayList<HTMLTableListCell> m_cells=new ArrayList<HTMLTableListCell>();
		
		public HTMLTableListRow() {
			m_tr = DOM.createTR();
			setElement(m_tr);
		}
		
		public boolean add(HTMLTableListCell e) {
			m_tr.appendChild(e.getElement());
			return m_cells.add(e);
		}

		public void add(int index, HTMLTableListCell element) {
			Node n = m_tr.getChild(index);
			m_tr.insertBefore(element.getElement(), n);
			m_cells.add(index, element);
		}

		public boolean addAll(Collection<? extends HTMLTableListCell> c) {
			return m_cells.addAll(c);
		}

		public boolean addAll(int index,
				Collection<? extends HTMLTableListCell> c) {
			return m_cells.addAll(index, c);
		}

		public void clear() {
			m_cells.clear();
		}

		public boolean contains(Object o) {
			return m_cells.contains(o);
		}

		public boolean containsAll(Collection<?> c) {
			return m_cells.containsAll(c);
		}

		public HTMLTableListCell get(int index) {
			return m_cells.get(index);
		}

		public int indexOf(Object o) {
			return m_cells.indexOf(o);
		}

		public boolean isEmpty() {
			return m_cells.isEmpty();
		}

		public Iterator<HTMLTableListCell> iterator() {
			return m_cells.iterator();
		}

		public int lastIndexOf(Object o) {
			return m_cells.lastIndexOf(o);
		}

		public ListIterator<HTMLTableListCell> listIterator() {
			return m_cells.listIterator();
		}

		public ListIterator<HTMLTableListCell> listIterator(int index) {
			return m_cells.listIterator(index);
		}

		public boolean remove(Object o) {
			return m_cells.remove(o);
		}

		public HTMLTableListCell remove(int index) {
			return m_cells.remove(index);
		}

		public boolean removeAll(Collection<?> c) {
			return m_cells.removeAll(c);
		}

		public boolean retainAll(Collection<?> c) {
			return m_cells.retainAll(c);
		}

		public HTMLTableListCell set(int index, HTMLTableListCell element) {
			return m_cells.set(index, element);
		}

		public int size() {
			return m_cells.size();
		}

		public List<HTMLTableListCell> subList(int fromIndex, int toIndex) {
			return m_cells.subList(fromIndex, toIndex);
		}

		public Object[] toArray() {
			return m_cells.toArray();
		}

		public <T> T[] toArray(T[] a) {
			return m_cells.toArray(a);
		}
		
		public HTMLTableListCell cell(int index) {
			return m_cells.get(index);
		}

		public HandlerRegistration addClickHandler(ClickHandler arg0) {
			return addDomHandler(arg0, ClickEvent.getType());
		}

		public HandlerRegistration addMouseOverHandler(MouseOverHandler arg0) {
			return addDomHandler(arg0, MouseOverEvent.getType());
		}

		public HandlerRegistration addMouseOutHandler(MouseOutHandler arg0) {
			return addDomHandler(arg0, MouseOutEvent.getType());
		}
		
		@Override
		protected void doAttachChildren() {
			for(HTMLTableListCell c : m_cells) {
				c.attach();
			}
		}
		
		@Override
		protected void doDetachChildren() {
			for(HTMLTableListCell c : m_cells) {
				c.detach();
			}
		}

		public void attach() {
			onAttach();
		}
		
		public void detach() {
			onDetach();
		}
	}
	
	public HTMLTableList() {
		m_rows = new ArrayList<HTMLTableListRow>();
		m_divwrap = DOM.createDiv();
		setElement(m_divwrap);
		m_table = DOM.createTable();
		m_tbody = DOM.createTBody();
		m_table.appendChild(m_tbody);
		m_divwrap.appendChild(m_table);
		m_divwrap.setClassName("htmlTableList");
	}
	
	public boolean add(HTMLTableListRow e) {
		m_tbody.appendChild(e.getElement());
		if(isAttached()) e.attach();
		return m_rows.add(e);
	}

	public void add(int index, HTMLTableListRow element) {
		Node n = m_tbody.getChild(index);
		m_tbody.insertBefore(element.getElement(), n);
		m_rows.add(index, element);
	}

	public boolean addAll(Collection<? extends HTMLTableListRow> c) {
		return m_rows.addAll(c);
	}

	public boolean addAll(int index, Collection<? extends HTMLTableListRow> c) {
		return m_rows.addAll(index, c);
	}

	public void clear() {
		for(HTMLTableListRow row : m_rows) {
			row.detach();
			m_tbody.removeChild(row.getElement());
		}
		m_rows.clear();
	}

	public boolean contains(Object o) {
		return m_rows.contains(o);
	}

	public boolean containsAll(Collection<?> c) {
		return m_rows.containsAll(c);
	}

	public HTMLTableListRow get(int index) {
		return m_rows.get(index);
	}

	public int indexOf(Object o) {
		return m_rows.indexOf(o);
	}

	public boolean isEmpty() {
		return m_rows.isEmpty();
	}

	public Iterator<HTMLTableListRow> iterator() {
		return m_rows.iterator();
	}

	public int lastIndexOf(Object o) {
		return m_rows.lastIndexOf(o);
	}

	public ListIterator<HTMLTableListRow> listIterator() {
		return m_rows.listIterator();
	}

	public ListIterator<HTMLTableListRow> listIterator(int index) {
		return m_rows.listIterator(index);
	}

	public boolean remove(Object o) {
		return m_rows.remove(o);
	}

	public HTMLTableListRow remove(int index) {
		return m_rows.remove(index);
	}

	public boolean removeAll(Collection<?> c) {
		return m_rows.removeAll(c);
	}

	public boolean retainAll(Collection<?> c) {
		return m_rows.retainAll(c);
	}

	public HTMLTableListRow set(int index, HTMLTableListRow element) {
		return m_rows.set(index, element);
	}

	public int size() {
		return m_rows.size();
	}

	public List<HTMLTableListRow> subList(int fromIndex, int toIndex) {
		return m_rows.subList(fromIndex, toIndex);
	}

	public Object[] toArray() {
		return m_rows.toArray();
	}

	public <T> T[] toArray(T[] a) {
		return m_rows.toArray(a);
	}

	public HandlerRegistration addMouseOutHandler(MouseOutHandler arg0) {
		return addDomHandler(arg0, MouseOutEvent.getType());
	}

	public HandlerRegistration addMouseOverHandler(MouseOverHandler arg0) {
		return addDomHandler(arg0, MouseOverEvent.getType());
	}

	public HandlerRegistration addClickHandler(ClickHandler arg0) {
		return addDomHandler(arg0, ClickEvent.getType());
	}
	
	@Override
	protected void doAttachChildren() {
		for(HTMLTableListRow r : m_rows) {
			r.attach();
		}
	}
	
	@Override
	protected void doDetachChildren() {
		for(HTMLTableListRow r : m_rows) {
			r.detach();
		}
	}
	
	public void attach() {
		onAttach();
	}
	
	public void detach() {
		onDetach();
	}

	public void clearEverythingButHeader() {
		HTMLTableListRow r = m_rows.get(0);
		for(HTMLTableListRow row : m_rows) {
			if(r == row) continue;
			row.detach();
			m_tbody.removeChild(row.getElement());
		}
		m_rows.clear();
		m_rows.add(r);
	}

	public int indexOfElement(Element e) {
		// TODO Auto-generated method stub
		for(int i=0;i<m_rows.size();i++) {
			if(m_rows.get(i).getElement()==e) {
				return i;
			}
		}
		return -1;
	}
}
