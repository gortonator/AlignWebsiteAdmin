package alignWebsite.alignadmin;

import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.core.Response;

import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mehaexample.asdDemo.alignWebsite.Admin;
import org.mehaexample.asdDemo.dao.alignadmin.AdminLoginsDao;
import org.mehaexample.asdDemo.dao.alignadmin.AdministratorNotesDao;
import org.mehaexample.asdDemo.dao.alignadmin.AdministratorsDao;
import org.mehaexample.asdDemo.dao.alignadmin.ElectivesAdminDao;
import org.mehaexample.asdDemo.dao.alignadmin.GenderRatioDao;
import org.mehaexample.asdDemo.dao.alignprivate.ElectivesDao;
import org.mehaexample.asdDemo.dao.alignprivate.ExtraExperiencesDao;
import org.mehaexample.asdDemo.dao.alignprivate.PriorEducationsDao;
import org.mehaexample.asdDemo.dao.alignprivate.StudentsDao;
import org.mehaexample.asdDemo.dao.alignprivate.WorkExperiencesDao;
import org.mehaexample.asdDemo.enums.Campus;
import org.mehaexample.asdDemo.enums.DegreeCandidacy;
import org.mehaexample.asdDemo.enums.EnrollmentStatus;
import org.mehaexample.asdDemo.enums.Gender;
import org.mehaexample.asdDemo.enums.Term;
import org.mehaexample.asdDemo.model.alignadmin.AdministratorNotes;
import org.mehaexample.asdDemo.model.alignadmin.GenderRatio;
import org.mehaexample.asdDemo.model.alignadmin.ParamsObject;
import org.mehaexample.asdDemo.model.alignadmin.TopBachelor;
import org.mehaexample.asdDemo.model.alignadmin.TopEmployer;
import org.mehaexample.asdDemo.model.alignprivate.StudentLogins;
//import org.mehaexample.asdDemo.restModels.StudentProfile;
import org.mehaexample.asdDemo.model.alignprivate.ExtraExperiences;
import org.mehaexample.asdDemo.model.alignprivate.Privacies;
import org.mehaexample.asdDemo.model.alignprivate.Projects;
import org.mehaexample.asdDemo.model.alignprivate.Students;
import org.mehaexample.asdDemo.model.alignprivate.WorkExperiences;

import junit.framework.Assert;

public class AdminTest {
	private static String NEUIDTEST = "0000000";

	private static Admin adminFacing;
	private static StudentsDao studentsDao;
	private static ElectivesAdminDao electivesAdminDao;
	private static GenderRatioDao genderRatioDao;
	private static WorkExperiencesDao workExperiencesDao;
	private static PriorEducationsDao priorEducationsDao;
	private static ElectivesDao electivesDao;
	private static AdminLoginsDao adminLoginsDao;
	private static ExtraExperiencesDao extraExperiencesDao;
	private static AdministratorsDao administratorsDao;
	private static AdministratorNotesDao administratorNotesDao;
	private static StudentLogins studentLogins;
	
	@BeforeClass
	public static void init() {
		studentsDao = new StudentsDao();
		electivesAdminDao = new ElectivesAdminDao();
		genderRatioDao = new GenderRatioDao();
		workExperiencesDao = new WorkExperiencesDao();
		priorEducationsDao = new PriorEducationsDao();
		electivesDao = new ElectivesDao();
		adminLoginsDao = new AdminLoginsDao();
		extraExperiencesDao = new ExtraExperiencesDao();
		administratorsDao = new AdministratorsDao();
		administratorNotesDao = new AdministratorNotesDao();
		studentLogins = new StudentLogins();
		adminFacing = new Admin();
	}
	
	@After
	public void deleteForDuplicateDatabase() {
		studentsDao.deleteStudent("0000000");
		studentsDao.deleteStudent("1111111");
		studentsDao.deleteStudent("2222222");
	}
	
	
	@Before
	public void setupAddRecords()throws Exception {
		Students newStudent = new Students("0000000", "tomcat@gmail.com", "Tom", "",
				"Cat", Gender.M, "F1", "1111111111",
				"401 Terry Ave", "WA", "Seattle", "98109", Term.FALL, 2015,
				Term.SPRING, 2017,
				EnrollmentStatus.FULL_TIME, Campus.SEATTLE, DegreeCandidacy.MASTERS, null, true);
		Students newStudent2 = new Students("1111111", "jerrymouse@gmail.com", "Jerry", "",
				"Mouse", Gender.F, "F1", "1111111111",
				"225 Terry Ave", "MA", "Seattle", "98109", Term.FALL, 2014,
				Term.SPRING, 2016,
				EnrollmentStatus.PART_TIME, Campus.BOSTON, DegreeCandidacy.MASTERS, null, true);
		Students newStudent3 = new Students("2222222", "tomcat3@gmail.com", "Tom", "",
				"Dog", Gender.M, "F1", "1111111111",
				"401 Terry Ave", "WA", "Seattle", "98109", Term.FALL, 2015,
				Term.FALL, 2017,
				EnrollmentStatus.DROPPED_OUT, Campus.CHARLOTTE, DegreeCandidacy.MASTERS, null, true);

		newStudent.setScholarship(true);
		newStudent.setRace("White");
		newStudent2.setRace("Black");
		newStudent3.setRace("White");

		studentsDao.addStudent(newStudent);
		studentsDao.addStudent(newStudent2);
		studentsDao.addStudent(newStudent3);		
	}	
	
	@Test
	public void getStudentProfileTest(){
		Response studentProfileResponse = adminFacing.getStudentProfile(NEUIDTEST);
		String studentProfile = (String) studentProfileResponse.getEntity();
		JSONObject student = new JSONObject(studentProfile);
		Assert.assertEquals(student.get("neuId"), NEUIDTEST);
	}
	
	@Test
	public void getStudentProfileTest2(){
		Response studentProfileResponse = adminFacing.getStudentProfile("11223344");
		String studentProfile = (String) studentProfileResponse.getEntity();
		Assert.assertEquals(studentProfile, "No Student record exists with given ID");
	}
	
	@Test
	public void getGenderRatioTest(){
		ParamsObject paramsobj = new ParamsObject();
		paramsobj.setCampus("CHARLOTTE");
		paramsobj.setYear("2015");
		Response genderRatioResponse = adminFacing.getGenderRatio(paramsobj);
		List<GenderRatio> ratio = (List<GenderRatio>) genderRatioResponse.getEntity();
		Assert.assertEquals(ratio.get(0).getEntryYear(), 2015);
	}
	
	@Test
	public void getGenderRatioTest2(){
		ParamsObject paramsobj = new ParamsObject();
		paramsobj.setCampus("NEWYORK");
		paramsobj.setYear("2015");
		Response genderRatioResponse = adminFacing.getGenderRatio(paramsobj);
		int status = (int) genderRatioResponse.getStatus();
		Assert.assertEquals(status, 400);
	}
	
	
	@Test
	public void getGenderRatioTest3(){
		ParamsObject paramsobj = new ParamsObject();
		paramsobj.setCampus(null);
		paramsobj.setYear("2015");
		Response genderRatioResponse = adminFacing.getGenderRatio(paramsobj);
		int status = (int) genderRatioResponse.getStatus();
		Assert.assertEquals(status, 400);
	}
	
	@Test
	public void getTopBachelorDegreeTest(){
		ParamsObject paramsobj = new ParamsObject();
		paramsobj.setCampus("CHARLOTTE");
		paramsobj.setYear("2015");
		Response TopBachelorResponse;
		try {
			TopBachelorResponse = adminFacing.getTopBachelorDegree(paramsobj);
			List<TopBachelor> degrees = (List<TopBachelor>) TopBachelorResponse.getEntity();
			Assert.assertEquals(degrees.size(), 0);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void getTopBachelorDegreeTest2(){
		ParamsObject paramsobj = new ParamsObject();
		paramsobj.setCampus("CHARLOTTE");
		paramsobj.setYear("2015");
		Response TopBachelorResponse;
		try {
			TopBachelorResponse = adminFacing.getTopBachelorDegree(paramsobj);
			int status = (int) TopBachelorResponse.getStatus();
			Assert.assertEquals(status, 200);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void getTopBachelorDegreeTest21(){
		ParamsObject paramsobj = new ParamsObject();
		paramsobj.setCampus("CHARLO");
		paramsobj.setYear("2015");
		Response TopBachelorResponse;
		try {
			TopBachelorResponse = adminFacing.getTopBachelorDegree(paramsobj);
			int status = (int) TopBachelorResponse.getStatus();
			Assert.assertEquals(status, 400);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void getTopBachelorDegreeTest3(){
		ParamsObject paramsobj = new ParamsObject();
		paramsobj.setCampus(null);
		paramsobj.setYear("2015");
		Response TopBachelorResponse;
		try {
			TopBachelorResponse = adminFacing.getTopBachelorDegree(paramsobj);
			int status = (int) TopBachelorResponse.getStatus();
			Assert.assertEquals(status, 200);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void getTopBachelorDegreeTest32(){
		ParamsObject paramsobj = new ParamsObject();
		paramsobj.setCampus(null);
		paramsobj.setYear("20a5");
		Response TopBachelorResponse;
		try {
			TopBachelorResponse = adminFacing.getTopBachelorDegree(paramsobj);
			int status = (int) TopBachelorResponse.getStatus();
			Assert.assertEquals(status, 400);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void getTopBachelorDegreeTest4(){
		ParamsObject paramsobj = new ParamsObject();
		paramsobj.setCampus("CHARLOTTE");
		paramsobj.setYear(null);
		Response TopBachelorResponse;
		try {
			TopBachelorResponse = adminFacing.getTopBachelorDegree(paramsobj);
			int status = (int) TopBachelorResponse.getStatus();
			Assert.assertEquals(status, 200);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void getTopBachelorDegreeTest42(){
		ParamsObject paramsobj = new ParamsObject();
		paramsobj.setCampus("CHARLO");
		paramsobj.setYear(null);
		Response TopBachelorResponse;
		try {
			TopBachelorResponse = adminFacing.getTopBachelorDegree(paramsobj);
			int status = (int) TopBachelorResponse.getStatus();
			Assert.assertEquals(status, 400);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void getTopBachelorDegreeTest5(){
		ParamsObject paramsobj = new ParamsObject();
		paramsobj.setCampus(null);
		paramsobj.setYear(null);
		Response TopBachelorResponse;
		try {
			TopBachelorResponse = adminFacing.getTopBachelorDegree(paramsobj);
			int status = (int) TopBachelorResponse.getStatus();
			Assert.assertEquals(status, 200);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void getTopEmployersTest(){
		ParamsObject paramsobj = new ParamsObject();
		paramsobj.setCampus("CHARLOTTE");
		paramsobj.setYear("2015");
		Response TopEmployers;
		TopEmployers = adminFacing.getTopEmployers(paramsobj);
		List<TopEmployer> degrees = (List<TopEmployer>) TopEmployers.getEntity();
		Assert.assertEquals(degrees.size(), 0);
	}
	
	@Test
	public void getTopEmployersTest2(){
		ParamsObject paramsobj = new ParamsObject();
		paramsobj.setCampus(null);
		paramsobj.setYear("2015");
		Response TopEmployers;
		TopEmployers = adminFacing.getTopEmployers(paramsobj);
		int status = (int) TopEmployers.getStatus();
		Assert.assertEquals(status, 200);
	}
	
	
	@Test
	public void getTopEmployersTest21(){
		ParamsObject paramsobj = new ParamsObject();
		paramsobj.setCampus(null);
		paramsobj.setYear("20a5");
		Response TopEmployers;
		TopEmployers = adminFacing.getTopEmployers(paramsobj);
		int status = (int) TopEmployers.getStatus();
		Assert.assertEquals(status, 400);
	}
	
	
	@Test
	public void getTopEmployersTest3(){
		ParamsObject paramsobj = new ParamsObject();
		paramsobj.setCampus("CHARLOTTE");
		paramsobj.setYear(null);
		Response TopEmployers;
		TopEmployers = adminFacing.getTopEmployers(paramsobj);
		int status = (int) TopEmployers.getStatus();
		Assert.assertEquals(status, 200);
	}
	
	
	@Test
	public void getTopEmployersTest4(){
		ParamsObject paramsobj = new ParamsObject();
		paramsobj.setCampus("CHARLE");
		paramsobj.setYear(null);
		Response TopEmployers;
		TopEmployers = adminFacing.getTopEmployers(paramsobj);
		int status = (int) TopEmployers.getStatus();
		Assert.assertEquals(status, 400);
	}
	
	@Test
	public void getTopEmployersTest41(){
		ParamsObject paramsobj = new ParamsObject();
		paramsobj.setCampus(null);
		paramsobj.setYear(null);
		Response TopEmployers;
		TopEmployers = adminFacing.getTopEmployers(paramsobj);
		int status = (int) TopEmployers.getStatus();
		Assert.assertEquals(status, 200);
	}
	
	@Test
	public void getTopElectivesTest(){
		ParamsObject paramsobj = new ParamsObject();
		paramsobj.setCampus("CHARLOTTE");
		paramsobj.setYear("2015");
		Response TopElectives;
		TopElectives = adminFacing.getTopElectives(paramsobj);
		int status = (int) TopElectives.getStatus();
		Assert.assertEquals(status, 200);
	}
	
	@Test
	public void getTopElectivesTest2(){
		ParamsObject paramsobj = new ParamsObject();
		paramsobj.setCampus(null);
		paramsobj.setYear("2015");
		Response TopElectives;
		TopElectives = adminFacing.getTopElectives(paramsobj);
		int status = (int) TopElectives.getStatus();
		Assert.assertEquals(status, 200);
	}
	
	@Test
	public void getTopElectivesTest21(){
		ParamsObject paramsobj = new ParamsObject();
		paramsobj.setCampus(null);
		paramsobj.setYear("20a5");
		Response TopElectives;
		TopElectives = adminFacing.getTopElectives(paramsobj);
		int status = (int) TopElectives.getStatus();
		Assert.assertEquals(status, 400);
	}
	
	@Test
	public void getTopElectivesTest3(){
		ParamsObject paramsobj = new ParamsObject();
		paramsobj.setCampus("CHARLOTTE");
		paramsobj.setYear(null);
		Response TopElectives;
		TopElectives = adminFacing.getTopElectives(paramsobj);
		int status = (int) TopElectives.getStatus();
		Assert.assertEquals(status, 200);
	}
	
	@Test
	public void getTopElectivesTest31(){
		ParamsObject paramsobj = new ParamsObject();
		paramsobj.setCampus("CHARLTE");
		paramsobj.setYear(null);
		Response TopElectives;
		TopElectives = adminFacing.getTopElectives(paramsobj);
		int status = (int) TopElectives.getStatus();
		Assert.assertEquals(status, 400);
	}

	@Test
	public void getTopElectivesTest4(){
		ParamsObject paramsobj = new ParamsObject();
		paramsobj.setCampus(null);
		paramsobj.setYear(null);
		Response TopElectives;
		TopElectives = adminFacing.getTopElectives(paramsobj);
		int status = (int) TopElectives.getStatus();
		Assert.assertEquals(status, 200);
	}

	@Test
	public void getCoopStudentsTest(){
		ParamsObject paramsobj = new ParamsObject();
		paramsobj.setCampus("CHARLOTTE");
		paramsobj.setYear("2015");
		Response StudentCoopLists;
		StudentCoopLists = adminFacing.getCoopStudents(paramsobj);
		int status = (int) StudentCoopLists.getStatus();
		Assert.assertEquals(status, 200);
	}
	
	@Test
	public void getCoopStudentsTest1(){
		ParamsObject paramsobj = new ParamsObject();
		paramsobj.setCampus("CHARLTTE");
		paramsobj.setYear("2015");
		Response StudentCoopLists;
		StudentCoopLists = adminFacing.getCoopStudents(paramsobj);
		int status = (int) StudentCoopLists.getStatus();
		Assert.assertEquals(status, 500);
	}

	@Test
	public void getCoopStudentsTest2(){
		ParamsObject paramsobj = new ParamsObject();
		paramsobj.setCampus("CHARLOTTE");
		paramsobj.setYear(null);
		Response StudentCoopLists;
		StudentCoopLists = adminFacing.getCoopStudents(paramsobj);
		int status = (int) StudentCoopLists.getStatus();
		Assert.assertEquals(status, 200);
	}
	
	@Test
	public void getCoopStudentsTest21(){
		ParamsObject paramsobj = new ParamsObject();
		paramsobj.setCampus("CHARLTTE");
		paramsobj.setYear(null);
		Response StudentCoopLists;
		StudentCoopLists = adminFacing.getCoopStudents(paramsobj);
		int status = (int) StudentCoopLists.getStatus();
		Assert.assertEquals(status, 400);
	}

	@Test
	public void getCoopStudentsTest3(){
		ParamsObject paramsobj = new ParamsObject();
		paramsobj.setCampus(null);
		paramsobj.setYear("2015");
		Response StudentCoopLists;
		StudentCoopLists = adminFacing.getCoopStudents(paramsobj);
		int status = (int) StudentCoopLists.getStatus();
		Assert.assertEquals(status, 400);
	}
	
	@Test
	public void getCoopStudentsTest31(){
		ParamsObject paramsobj = new ParamsObject();
		paramsobj.setCampus(null);
		paramsobj.setYear("20a5");
		Response StudentCoopLists;
		StudentCoopLists = adminFacing.getCoopStudents(paramsobj);
		int status = (int) StudentCoopLists.getStatus();
		Assert.assertEquals(status, 400);
	}

	@Test
	public void getStudentWorkingFullTimeTest(){
		ParamsObject paramsobj = new ParamsObject();
		paramsobj.setCampus("CHARLOTTE");
		paramsobj.setYear("2015");
		Response StudentCoopLists;
		StudentCoopLists = adminFacing.getStudentsWorkingForACompany(paramsobj);
		int status = (int) StudentCoopLists.getStatus();
		Assert.assertEquals(status, 400);
	}
	
	@Test
	public void getStudentWorkingFullTimeTest1(){
		ParamsObject paramsobj = new ParamsObject();
		paramsobj.setCampus("CHARLTTE");
		paramsobj.setYear("2015");
		Response StudentCoopLists;
		StudentCoopLists = adminFacing.getStudentsWorkingForACompany(paramsobj);
		int status = (int) StudentCoopLists.getStatus();
		Assert.assertEquals(status, 400);
	}
	
	@Test
	public void getStudentWorkingFullTimeTest2(){
		ParamsObject paramsobj = new ParamsObject();
		paramsobj.setCampus("CHARLOTTE");
		paramsobj.setYear(null);
		Response StudentCoopLists;
		StudentCoopLists = adminFacing.getStudentsWorkingForACompany(paramsobj);
		int status = (int) StudentCoopLists.getStatus();
		Assert.assertEquals(status, 400);
	}
	
	@Test
	public void getStudentWorkingFullTimeTest21(){
		ParamsObject paramsobj = new ParamsObject();
		paramsobj.setCampus("CHALOTE");
		paramsobj.setYear(null);
		Response StudentCoopLists;
		StudentCoopLists = adminFacing.getStudentsWorkingForACompany(paramsobj);
		int status = (int) StudentCoopLists.getStatus();
		Assert.assertEquals(status, 400);
	}
	
	@Test
	public void getStudentWorkingFullTimeTest3(){
		ParamsObject paramsobj = new ParamsObject();
		paramsobj.setCampus(null);
		paramsobj.setYear("2015");
		Response StudentCoopLists;
		StudentCoopLists = adminFacing.getStudentsWorkingForACompany(paramsobj);
		int status = (int) StudentCoopLists.getStatus();
		Assert.assertEquals(status, 400);
	}
	
	@Test
	public void getStudentundergradInstitutuinsTest(){
		ParamsObject paramsobj = new ParamsObject();
		paramsobj.setCampus("CHARLOTTE");
		paramsobj.setYear("2015");
		Response StudentBachelorInstitutions;
		StudentBachelorInstitutions = adminFacing.getStudentundergradInstitutuins(paramsobj);
		int status = (int) StudentBachelorInstitutions.getStatus();
		Assert.assertEquals(status, 200);
	}
	
	@Test
	public void getStudentundergradInstitutuinsTest1(){
		ParamsObject paramsobj = new ParamsObject();
		paramsobj.setCampus("CHARLOTTE");
		paramsobj.setYear(null);
		Response StudentBachelorInstitutions;
		StudentBachelorInstitutions = adminFacing.getStudentundergradInstitutuins(paramsobj);
		int status = (int) StudentBachelorInstitutions.getStatus();
		Assert.assertEquals(status, 200);
	}
	
	@Test
	public void getStudentundergradInstitutuinsTest2(){
		ParamsObject paramsobj = new ParamsObject();
		paramsobj.setCampus("CHARLTTE");
		paramsobj.setYear(null);
		Response StudentBachelorInstitutions;
		StudentBachelorInstitutions = adminFacing.getStudentundergradInstitutuins(paramsobj);
		int status = (int) StudentBachelorInstitutions.getStatus();
		Assert.assertEquals(status, 500);
	}
	
	@Test
	public void getStudentundergradInstitutuinsTest3(){
		ParamsObject paramsobj = new ParamsObject();
		paramsobj.setCampus(null);
		paramsobj.setYear("2015");
		Response StudentBachelorInstitutions;
		StudentBachelorInstitutions = adminFacing.getStudentundergradInstitutuins(paramsobj);
		int status = (int) StudentBachelorInstitutions.getStatus();
		Assert.assertEquals(status, 400);
	}
	

	@Test
	public void getStudentsWorkingForACompanyTest(){
		ParamsObject paramsobj = new ParamsObject();
		paramsobj.setCampus("CHARLOTTE");
		paramsobj.setYear("2015");
		paramsobj.setCompany("amazon");
		Response StudentBasicInfos;
		StudentBasicInfos = adminFacing.getStudentsWorkingForACompany(paramsobj);
		int status = (int) StudentBasicInfos.getStatus();
		Assert.assertEquals(status, 200);
	}
	
	@Test
	public void getStudentsWorkingForACompanyTest1(){
		ParamsObject paramsobj = new ParamsObject();
		paramsobj.setCampus("CHAROTTE");
		paramsobj.setYear("2015");
		paramsobj.setCompany("amazon");
		Response StudentBasicInfos;
		StudentBasicInfos = adminFacing.getStudentsWorkingForACompany(paramsobj);
		int status = (int) StudentBasicInfos.getStatus();
		Assert.assertEquals(status, 500);
	}

	@Test
	public void getStudentsWorkingForACompanyTest2(){
		ParamsObject paramsobj = new ParamsObject();
		paramsobj.setCampus("CHARLOTTE");
		paramsobj.setYear(null);
		paramsobj.setCompany("amazon");
		Response StudentBasicInfos;
		StudentBasicInfos = adminFacing.getStudentsWorkingForACompany(paramsobj);
		int status = (int) StudentBasicInfos.getStatus();
		Assert.assertEquals(status, 200);
	}

	@Test
	public void getStudentsWorkingForACompanyTest21(){
		ParamsObject paramsobj = new ParamsObject();
		paramsobj.setCampus("CHARLTTE");
		paramsobj.setYear(null);
		paramsobj.setCompany("amazon");
		Response StudentBasicInfos;
		StudentBasicInfos = adminFacing.getStudentsWorkingForACompany(paramsobj);
		int status = (int) StudentBasicInfos.getStatus();
		Assert.assertEquals(status, 500);
	}

	@Test
	public void getStudentsWorkingForACompanyTest3(){
		ParamsObject paramsobj = new ParamsObject();
		paramsobj.setCampus("CHARLOTTE");
		paramsobj.setYear("2015");
		paramsobj.setCompany("amazon");
		Response StudentBasicInfos;
		StudentBasicInfos = adminFacing.getStudentsWorkingForACompany(paramsobj);
		int status = (int) StudentBasicInfos.getStatus();
		Assert.assertEquals(status, 200);
	}
	

	@Test
	public void getStudentsWorkingForACompanyTest31(){
		ParamsObject paramsobj = new ParamsObject();
		paramsobj.setCampus(null);
		paramsobj.setYear("2015");
		paramsobj.setCompany("amazon");
		Response StudentBasicInfos;
		StudentBasicInfos = adminFacing.getStudentsWorkingForACompany(paramsobj);
		int status = (int) StudentBasicInfos.getStatus();
		Assert.assertEquals(status, 400);
	}


	@Test
	public void createNoteTest(){
		AdministratorNotes input = new AdministratorNotes();
		input.setAdministratorNeuId("001379999");
		input.setAdministratorNoteId(100);
		input.setDesc("001379999");
		input.setTitle("001379999");
		input.setNeuId(NEUIDTEST);
		Response StudentBasicInfos;
		StudentBasicInfos = adminFacing.createNote(input,"001379999");
		int status = (int) StudentBasicInfos.getStatus();
		Assert.assertEquals(status, 200);
	}
	

	@Test
	public void createNoteTest2(){
		AdministratorNotes input = new AdministratorNotes();
		input.setAdministratorNeuId("00137999");
		input.setAdministratorNoteId(100);
		input.setDesc("001379999");
		input.setTitle("001379999");
		input.setNeuId(NEUIDTEST);
		Response StudentBasicInfos;
		StudentBasicInfos = adminFacing.createNote(input,"001379999");
		int status = (int) StudentBasicInfos.getStatus();
		Assert.assertEquals(status, 500);
	}
	

	@Test
	public void createNoteTest3(){
		AdministratorNotes input = new AdministratorNotes();
		input.setAdministratorNeuId("0019999");
		input.setAdministratorNoteId(100);
		input.setDesc("001379999");
		input.setTitle("001379999");
		input.setNeuId("786");
		Response StudentBasicInfos;
		StudentBasicInfos = adminFacing.createNote(input,"001379999");
		int status = (int) StudentBasicInfos.getStatus();
		Assert.assertEquals(status, 500);
	}

	

	@Test
	public void searchTest(){
		ParamsObject paramsobj = new ParamsObject();
		paramsobj.setCampus("CHARLOTTE");
		paramsobj.setBeginindex("1");
		paramsobj.setEndindex("20");
		Response StudentBasicInfos;
		StudentBasicInfos = adminFacing.searchStudent(paramsobj);
		int status = (int) StudentBasicInfos.getStatus();
		Assert.assertEquals(status, 200);
	}
	
	
	
	
	
	
	
	
	
	
	
}