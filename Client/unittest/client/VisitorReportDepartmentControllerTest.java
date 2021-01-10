package client;

import static org.junit.jupiter.api.Assertions.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import util.HourAmount;
import util.ViewReports;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;

import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;
import org.junit.Rule;

import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import clientTry.VisitorReportDepartmentController;

import static org.mockito.Mockito.when;

class VisitorReportDepartmentControllerTest {
	
	ArrayList<HourAmount> group= new ArrayList<>();
	ArrayList<HourAmount> member= new ArrayList<>();
	ArrayList<HourAmount> user= new ArrayList<>();
	VisitorReportDepartmentController controller;
	@Rule
	MockitoRule rule =MockitoJUnit.rule();
	@BeforeEach
	void setUp()
	{
		controller=mock(VisitorReportDepartmentController.class);
		member.add(new HourAmount("14", 5));
		member.add(new HourAmount("11", 3));
		group.add(new HourAmount("14", 5));
		group.add(new HourAmount("10", 5));
		user.add(new HourAmount("14", 5));
		user.add(new HourAmount("11", 5));

	}
	@Test
	void test() {
		fail("Not yet implemented");
	}

}
