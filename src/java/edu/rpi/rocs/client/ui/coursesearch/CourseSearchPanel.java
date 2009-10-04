package edu.rpi.rocs.client.ui.coursesearch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeSet;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.InlineHyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.rpi.rocs.client.SemesterManager;
import edu.rpi.rocs.client.SemesterManager.SemesterManagerCallback;
import edu.rpi.rocs.client.objectmodel.Course;
import edu.rpi.rocs.client.objectmodel.Semester;
import edu.rpi.rocs.client.objectmodel.Course.CourseComparator;
import edu.rpi.rocs.client.ui.ListBoxHTML;

public class CourseSearchPanel extends VerticalPanel {

	FlexTable layout = new FlexTable();
	Label deptLabel = new Label("Department:");
	ListBox deptList = new ListBox();
	Label levelLabel = new Label("Level:");
	ListBox levelList = new ListBox();
	Label numberLabel = new Label("Number:");
	TextBox numberTextbox = new TextBox();
	Label nameLabel = new Label("Name:");
	TextBox nameTextbox = new TextBox();
	Anchor searchButton = new Anchor("Search");
	ListBoxHTML resultsListBox = new ListBoxHTML(true);
	InlineHyperlink resultAdd = new InlineHyperlink("Add", "rocs-add");
	SimplePanel resultsWrapper = new SimplePanel();
	private SemesterManagerCallback semesterChangeCallback =
		new SemesterManagerCallback() {

			public void semesterLoaded(Semester semester) {
				// TODO Auto-generated method stub
				//Log.trace("In CourseSearchPanel.semesterChangeCallback.semesterLoaded");
				deptList.clear();
				deptList.addItem("Any");
				List<Course> courses = semester.getCourses();
				TreeSet<String> depts = new TreeSet<String>();
				for(Course course : courses) {
					String a = course.getDept();
					//Log.trace("Found department " + a);
					depts.add(a);
				}
				for(String str : depts) {
					deptList.addItem(str);
				}
			}
		
	};
	
	private static CourseSearchPanel instance = null;
	public static CourseSearchPanel getInstance() {
		if (instance == null) {
			instance = new CourseSearchPanel();
		}
		return instance;
	}
	
	private CourseSearchPanel() {
		SemesterManager.getInstance().addSemesterChangeListener(semesterChangeCallback);
		deptList.addItem("Any");
		levelList.addItem("Any");
		levelList.addItem("1000");
		levelList.addItem("2000");
		levelList.addItem("4000");
		levelList.addItem("6000");
		levelList.addItem(">= 2000");
		levelList.addItem(">= 4000");
		layout.setWidget(0, 0, deptLabel);
		layout.setWidget(0, 1, deptList);
		layout.setWidget(1, 0, levelLabel);
		layout.setWidget(1, 1, levelList);
		layout.setWidget(2, 0, numberLabel);
		layout.setWidget(2, 1, numberTextbox);
		layout.setWidget(3, 0, nameLabel);
		layout.setWidget(3, 1, nameTextbox);
		layout.setWidget(4, 0, searchButton);
		layout.getFlexCellFormatter().setColSpan(4, 0, 2);
		
		searchButton.addStyleName("greybutton");
		searchButton.addStyleName("linkbutton");
		searchButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				CourseSearchPanel.getInstance().search();
			}
			
		});
		
		resultsListBox.addStyleName("search_results");
		
		resultAdd.addStyleName("greenbutton");
		resultAdd.addStyleName("linkbutton");
		
		resultsWrapper.addStyleName("search_results");
		resultsWrapper.add(resultsListBox);
		
		this.add(layout);
		this.add(resultsWrapper);
		this.add(resultAdd);
		/*
		CourseFilterEnum filters[] = CourseFilterEnum.values();
		for(int i = 0; i < filters.length; i++) {
			this.add(filters[i].getFilter().getFilterWidget());
		}
		*/
	}

	public void search() {
		Semester semester = SemesterManager.getInstance().getCurrentSemester();
		List<Course> courses = semester.getCourses();
		Log.debug("There are " + courses.size() + " courses");
		String dept = deptList.getValue(deptList.getSelectedIndex());
		if(dept == "Any") dept = null;
		
		String level = getSearchLevel();
		
		String name = nameTextbox.getValue();
		if(name == null) name = null;
		else if(name.equals("")) name = null;
		
		String num = numberTextbox.getValue();
		if(num == null) num = null;
		else if(num.equals("")) num = null;
		
		int userCourseNum = -1;
		if(num != null){
			userCourseNum = Integer.parseInt(num);
		}
		
		ArrayList<Course> theResults = new ArrayList<Course>();
		for(Course course : courses) {
			if(dept==null || course.getDept()==dept) {
				if(getCorrectCourseLevel(level, course.getNum()) == true){
					if(userCourseNum == -1 || userCourseNum == course.getNum()){
						if(name == null || course.getName().toLowerCase().indexOf(name.toLowerCase()) != -1){
							theResults.add(course);
						}
					}
				}
			}
		}
		Collections.sort(theResults, new CourseComparator());
		
		resultsListBox.clear();
		for(Course course : theResults) {
			resultsListBox.addHTML(course.getListDescription(), course.getDept()+course.getNum());
		}
	}
	
	private String getSearchLevel() {
		String level = levelList.getValue(levelList.getSelectedIndex());
		if(level.indexOf("Any") != -1) {
			return "0";
		}
		else if(level.indexOf("1000") != -1){
			return "1";
		}
		else if(level.indexOf(">=") != -1 && level.indexOf("2000") != -1){
			return "2?";
		}
		else if(level.indexOf("2000") != -1){
			return "2";
		}
		else if(level.indexOf(">=") != -1 && level.indexOf("4000") != -1){
			return "4?";
		}
		else if(level.indexOf("4000") != -1){
			return "4";
		}
		else{
			return "6";
		}
	}
	
	private boolean getCorrectCourseLevel(String level, int courseNum){
		if(level.equals("0")){
				return true;
		}
		else if(level.equals("1")){
			if(courseNum >= 1000 && courseNum < 2000){
				return true;
			}
		}
		else if(level.equals("2")){
			if(courseNum >= 2000 && courseNum < 4000){
				return true;
			}
		}
		else if(level.equals("2?")){
			if(courseNum >= 2000){
				return true;
			}
		}
		else if(level.equals("4")){
			if(courseNum >= 4000 && courseNum < 6000){
				return true;
			}
		}
		else if(level.equals("4?")){
			if(courseNum >= 4000){
				return true;
			}
		}
		else if(level.equals("6")){
			if(courseNum >= 6000){
				return true;
			}
		}
		return false;
	}
}
