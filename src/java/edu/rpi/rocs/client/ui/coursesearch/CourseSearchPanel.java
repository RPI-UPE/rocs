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
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.rpi.rocs.client.filters.schedule.ScheduleFilter;
import edu.rpi.rocs.client.objectmodel.Course;
import edu.rpi.rocs.client.objectmodel.Period;
import edu.rpi.rocs.client.objectmodel.Schedule;
import edu.rpi.rocs.client.objectmodel.SchedulerManager;
import edu.rpi.rocs.client.objectmodel.SemesterManager;
import edu.rpi.rocs.client.objectmodel.CourseStatusObject;
import edu.rpi.rocs.client.objectmodel.SemesterManager.SemesterManagerCallback;
import edu.rpi.rocs.client.objectmodel.Section;
import edu.rpi.rocs.client.objectmodel.Semester;
import edu.rpi.rocs.client.objectmodel.Course.CourseComparator;
import edu.rpi.rocs.client.ui.HTMLTableList;
import edu.rpi.rocs.client.ui.scheduler.SchedulerFilterDisplayPanel;

public class CourseSearchPanel extends VerticalPanel {

	/*
	private static final String CHECK = IMG("check.png"),
										 BADCHECK = IMG("badcheck.png"),
										 CONFLICT = IMG("cross.png"),
										 CLOSED = IMG("closed.png"),
										 SPACE = IMG("space.png");
										 
	private static final String IMG(String name) {
		return "<img src=\"" + ImageManager.getPathForImage(name) + "\" width=12 heigth=12>";
	}
*/
	// 1 = selected, 2 = closed, 4 = conflicts
	/*
	private static final String[] LIST_HEAD =
	{
		SPACE+SPACE+SPACE+"&nbsp;&nbsp;&nbsp;&nbsp;",
		CHECK+SPACE+SPACE+"&nbsp;&nbsp;&nbsp;&nbsp;",
		SPACE+CLOSED+SPACE+"&nbsp;&nbsp;&nbsp;&nbsp;",
		BADCHECK+CLOSED+SPACE+"&nbsp;&nbsp;&nbsp;&nbsp;",
		SPACE+SPACE+CONFLICT+"&nbsp;&nbsp;&nbsp;&nbsp;",
		BADCHECK+SPACE+CONFLICT+"&nbsp;&nbsp;&nbsp;&nbsp;",
		SPACE+CLOSED+CONFLICT+"&nbsp;&nbsp;&nbsp;&nbsp;",
		BADCHECK+CLOSED+CONFLICT+"&nbsp;&nbsp;&nbsp;&nbsp;"
	};
	private static final String[] LIST_TAIL =
	{
		"",
		"</b></font>",
		"",
		"</font></b>",
		"",
		"</font></b>",
		"",
		"</font></b>",
	};
	*/
	public static class State {
		public static final int CHOSEN=1;
		public static final int CLOSED=2;
		public static final int CONFLICT=4;
	};

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
	/*
	ListBoxHTML resultsListBox = new ListBoxHTML(true);
	Anchor resultAdd = new Anchor("Add");
	SimplePanel resultsWrapper = new SimplePanel();
	*/
	HTMLTableList resultsList = CourseResultList.getInstance().getTable();
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

		/*
		resultsListBox.addStyleName("search_results");

		resultAdd.addStyleName("greenbutton");
		resultAdd.addStyleName("linkbutton");
		resultAdd.addClickHandler(new ClickHandler() {
		

			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				for(int i=0;i<resultsListBox.getItemCount();i++) {
					if(resultsListBox.isItemSelected(i)) {
						Course c = theResults.get(i);
						SchedulerManager.getInstance().addCourse(c);
						if(!ROCSInterface.getInstance().isDisplaying(ClassViewPanel.getInstance()))
							ROCSInterface.getInstance().show(ClassViewPanel.getInstance(), true);
						CourseSearchPanel.getInstance().search();
					}
				}
			}

		});

		resultAdd.addStyleName("right-padded");

		resultsWrapper.addStyleName("search_results");
		resultsWrapper.add(resultsListBox);
*/
		this.add(layout);
		//this.add(resultsWrapper);
		this.add(resultsList);
		//this.add(resultAdd);
		/*
		CourseFilterEnum filters[] = CourseFilterEnum.values();
		for(int i = 0; i < filters.length; i++) {
			this.add(filters[i].getFilter().getFilterWidget());
		}
		*/
	}

	private ArrayList<Course> theResults=null;

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

		List<CourseStatusObject> CSOlist = SchedulerManager.getInstance().getSelectedCourses();
		ArrayList<Course> ReqList = new ArrayList<Course>(), OptList = new ArrayList<Course>();
		for (CourseStatusObject CSO : CSOlist) if (CSO.getRequired()) ReqList.add(CSO.getCourse());
															else OptList.add(CSO.getCourse());
		ArrayList<ScheduleFilter> filter = new ArrayList<ScheduleFilter>();
		filter.add(SchedulerFilterDisplayPanel.getInstance().getCurrentFilters().get(0));
		ArrayList<Schedule> posSchedules = Schedule.buildAllSchedulesGivenCoursesAndFilters(ReqList,
																  new ArrayList<Course>(), filter);

		theResults = new ArrayList<Course>();
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

		//resultsListBox.clear();
		CourseResultList.getInstance().clear();
		for(Course course : theResults) {
			boolean chosen = false;
			for (Course C : ReqList) if ((new CourseComparator()).compare(C, course) == 0) {
				chosen = true;
				break;
			}
			int bits = 0;
			ArrayList<Course> cArr = new ArrayList<Course>(); cArr.add(course);
			if (chosen) bits += 1;
			if (!chosen && !hasSpace(course, posSchedules)) bits += 4;
			if (course.isClosed()) bits += 2;
			//resultsListBox.addHTML(LIST_HEAD[bits]+course.getListDescription()+LIST_TAIL[bits], course.getDept()+course.getNum());
			CourseResultList.getInstance().add(course, bits);
		}
	}

	@SuppressWarnings("unused")
	private Section combineSections(Section S1, Section S2) {
		Section retVal = new Section();
		for (Period P : S1.getPeriods()) retVal.addPeriod(P);
		for (Period P : S2.getPeriods()) if (!addPeriod(retVal, P)) return null;
		return retVal;
	}
	private boolean addPeriod(Section S, Period P) {
		int time1 = P.getStart().getAbsMinute(), time2 = P.getEnd().getAbsMinute();
		for (Period P2 : S.getPeriods()) if (intersects(P.getDays(), P2.getDays())) {
			int time3 = P2.getStart().getAbsMinute(), time4 = P2.getEnd().getAbsMinute();
			if (time3 <= time2 && time4 >= time1) return false;
		}
		S.addPeriod(P);
		return true;
	}
	private boolean intersects(List<Integer> arr1, List<Integer> arr2) {
		for (Integer i : arr1) for (Integer j : arr2) if (i.equals(j)) return true;
		return false;
	}
	/*
	private boolean containsMatch(ArrayList<Section> list, Section section) {
		for (Section S : list) if (match(S, section)) return true;
	}
	*/
	private boolean hasSpace(Course CS, ArrayList<Schedule> schedules)
	{
		for (Schedule S : schedules) for (Section S2 : CS.getSections()) if (!S.willConflict(S2)) return true;
		return false;
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
