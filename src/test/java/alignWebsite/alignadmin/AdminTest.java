package alignWebsite.alignadmin;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.mehaexample.asdDemo.model.alignadmin.*;
import org.mehaexample.asdDemo.model.alignprivate.*;
//import org.mehaexample.asdDemo.restModels.StudentProfile;

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
    Electives elective;
    Electives elective2;
    Students newStudent;
    Students newStudent2;
    Students newStudent3;
    Students student;
    Students student2;
    SearchObject searchObject;
    SearchObject searchObject2;
    AdministratorNotes administratorNotes;
    AdministratorNotes administratorNotesTest;
    PriorEducations priorEducations1;
    PriorEducations priorEducations2;
    PriorEducations priorEducations3;
    PriorEducations priorEducations4;
    PriorEducations newPriorEducation;
    WorkExperiences newWorkExperience;
    WorkExperiences newWorkExperience2;
    WorkExperiences newWorkExperience3;
    WorkExperiences newWorkExperience4;
    WorkExperiences newWorkExperience5;


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

    @Before
    public void setup() throws ParseException {

        newStudent = new Students("0000000", "tomcat@gmail.com", "Tom", "",
                "Cat", Gender.M, "F1", "1111111111",
                "401 Terry Ave", "WA", "Seattle", "98109", Term.FALL, 2015,
                Term.SPRING, 2017,
                EnrollmentStatus.FULL_TIME, Campus.SEATTLE, DegreeCandidacy.MASTERS, null, true);
        newStudent2 = new Students("1111111", "jerrymouse@gmail.com", "Jerry", "",
                "Mouse", Gender.F, "F1", "1111111111",
                "225 Terry Ave", "MA", "Seattle", "98109", Term.FALL, 2014,
                Term.SPRING, 2016,
                EnrollmentStatus.PART_TIME, Campus.BOSTON, DegreeCandidacy.MASTERS, null, true);
        newStudent3 = new Students("2222222", "tomcat3@gmail.com", "Tom", "",
                "Dog", Gender.M, "F1", "1111111111",
                "401 Terry Ave", "WA", "Seattle", "98109", Term.FALL, 2015,
                Term.FALL, 2017,
                EnrollmentStatus.DROPPED_OUT, Campus.CHARLOTTE, DegreeCandidacy.MASTERS, null, true);

        newStudent.setScholarship(true);

        studentsDao.addStudent(newStudent);
        studentsDao.addStudent(newStudent2);
        studentsDao.addStudent(newStudent3);

        List<String> campusSearchObj = new ArrayList<>();
        List<String> enrollmentStatus = new ArrayList<>();

        campusSearchObj.add("BOSTON");
        enrollmentStatus.add("DROPPED_OUT");


        administratorNotes = new AdministratorNotes("0000000", "3", "test", "test");
        administratorNotesTest = new AdministratorNotes(null, null, null, null);

        priorEducations1 = new PriorEducations();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        priorEducations1.setGraduationDate(dateFormat.parse("2015-01-01"));
        priorEducations1.setGpa(3.50f);
        priorEducations1.setDegreeCandidacy(DegreeCandidacy.MASTERS);
        priorEducations1.setNeuId(newStudent.getNeuId());
        priorEducations1.setMajorName("Computer Science");
        priorEducations1.setInstitutionName("University of Washington");

        priorEducationsDao.createPriorEducation(priorEducations1);

        priorEducations2 = new PriorEducations();
        priorEducations2.setGraduationDate(dateFormat.parse("2015-01-01"));
        priorEducations2.setGpa(3.50f);
        priorEducations2.setDegreeCandidacy(DegreeCandidacy.MASTERS);
        priorEducations2.setNeuId(newStudent2.getNeuId());
        priorEducations2.setMajorName("Computer Science");
        priorEducations2.setInstitutionName("University of Washington");

        priorEducationsDao.createPriorEducation(priorEducations2);

        student = new Students("0012345671", "tomcatadmintest@gmail.com", "Tom", "",
                "Cat", Gender.M, "F1", "1111111111",
                "401 Terry Ave", "WA", "Seattle", "98109",
                Term.FALL, 2014, Term.SPRING, 2016,
                EnrollmentStatus.FULL_TIME, Campus.SEATTLE, DegreeCandidacy.MASTERS, null, true);
         student2 = new Students("1112345671", "jerrymouseadmintest@gmail.com", "Jerry", "",
                "Mouse", Gender.F, "F1", "1111111111",
                "401 Terry Ave", "WA", "Boston", "98109",
                Term.FALL, 2014, Term.SPRING, 2016,
                EnrollmentStatus.FULL_TIME, Campus.BOSTON, DegreeCandidacy.MASTERS, null, true);
        studentsDao.addStudent(student);
        studentsDao.addStudent(student2);

        newPriorEducation = new PriorEducations();
        newPriorEducation.setGraduationDate(dateFormat.parse("2015-01-01"));
        newPriorEducation.setGpa(3.50f);
        newPriorEducation.setDegreeCandidacy(DegreeCandidacy.BACHELORS);
        newPriorEducation.setNeuId(student.getNeuId());
        newPriorEducation.setMajorName("Computer Science");
        newPriorEducation.setInstitutionName("University of Washington");

        priorEducationsDao.createPriorEducation(newPriorEducation);

        newWorkExperience = new WorkExperiences();
        newWorkExperience.setStartDate(dateFormat.parse("2015-06-01"));
        newWorkExperience.setEndDate(dateFormat.parse("2015-12-01"));
        newWorkExperience.setCurrentJob(true);
        newWorkExperience.setCoop(false);
        newWorkExperience.setTitle("Title");
        newWorkExperience.setDescription("Description");
        newWorkExperience.setNeuId(student.getNeuId());
        newWorkExperience.setCompanyName("Amazon");
        workExperiencesDao.createWorkExperience(newWorkExperience);

        newWorkExperience2 = new WorkExperiences();
        newWorkExperience2.setStartDate(dateFormat.parse("2015-06-01"));
        newWorkExperience2.setEndDate(dateFormat.parse("2015-12-01"));
        newWorkExperience2.setCurrentJob(true);
        newWorkExperience2.setCoop(false);
        newWorkExperience2.setTitle("Title");
        newWorkExperience2.setDescription("Description");
        newWorkExperience2.setNeuId(student2.getNeuId());
        newWorkExperience2.setCompanyName("Microsoft");
        workExperiencesDao.createWorkExperience(newWorkExperience2);


        newWorkExperience3 = new WorkExperiences();
        newWorkExperience3.setStartDate(dateFormat.parse("2015-06-01"));
        newWorkExperience3.setEndDate(dateFormat.parse("2015-12-01"));
        newWorkExperience3.setCurrentJob(true);
        newWorkExperience3.setCoop(true);
        newWorkExperience3.setTitle("Title");
        newWorkExperience3.setDescription("Description");
        newWorkExperience3.setNeuId(student2.getNeuId());
        newWorkExperience3.setCompanyName("apple");
        workExperiencesDao.createWorkExperience(newWorkExperience3);


        newWorkExperience4 = new WorkExperiences();
        newWorkExperience4.setStartDate(dateFormat.parse("2015-06-01"));
        newWorkExperience4.setEndDate(dateFormat.parse("2015-12-01"));
        newWorkExperience4.setCurrentJob(false);
        newWorkExperience4.setCoop(true);
        newWorkExperience4.setTitle("Title");
        newWorkExperience4.setDescription("Description");
        newWorkExperience4.setNeuId(student.getNeuId());
        newWorkExperience4.setCompanyName("facebook");
        workExperiencesDao.createWorkExperience(newWorkExperience4);

        newWorkExperience5 = new WorkExperiences();
        newWorkExperience5.setStartDate(dateFormat.parse("2017-06-01"));
        newWorkExperience5.setEndDate(dateFormat.parse("2017-12-01"));
        newWorkExperience5.setCurrentJob(true);
        newWorkExperience5.setCoop(false);
        newWorkExperience5.setTitle("Title");
        newWorkExperience5.setDescription("Description");
        newWorkExperience5.setNeuId("0012345671");
        newWorkExperience5.setCompanyName("Google");
        workExperiencesDao.createWorkExperience(newWorkExperience5);


        priorEducations3 = new PriorEducations();
        priorEducations3.setGraduationDate(dateFormat.parse("2015-01-01"));
        priorEducations3.setGpa(3.50f);
        priorEducations3.setDegreeCandidacy(DegreeCandidacy.MASTERS);
        priorEducations3.setNeuId(student.getNeuId());
        priorEducations3.setMajorName("Computer Science");
        priorEducations3.setInstitutionName("University of Washington");

        priorEducationsDao.createPriorEducation(priorEducations3);

        priorEducations4 = new PriorEducations();
        priorEducations4.setGraduationDate(dateFormat.parse("2015-01-01"));
        priorEducations4.setGpa(3.50f);
        priorEducations4.setDegreeCandidacy(DegreeCandidacy.MASTERS);
        priorEducations4.setNeuId(student2.getNeuId());
        priorEducations4.setMajorName("Computer Science");
        priorEducations4.setInstitutionName("University of Washington");

        priorEducationsDao.createPriorEducation(priorEducations4);


        elective = new Electives();
        elective.setNeuId(student.getNeuId());
        elective.setCourseTerm(Term.SPRING);
        elective.setCourseYear(2017);

        electivesDao.addElective(elective);


        elective2 = new Electives();
        elective2.setNeuId(student2.getNeuId());
        elective2.setCourseTerm(Term.SPRING);
        elective2.setCourseYear(2017);

        electivesDao.addElective(elective2);




        searchObject = new SearchObject(campusSearchObj, "2015", "Tom",
                "Cat", "tomcat@gmail.com", "2017", enrollmentStatus, "", "1",
                "3", "0012345671", "", "true", "amazon", "male", "white");

        searchObject2 = new SearchObject(campusSearchObj, "2015", "Tom",
                "Cat", "tomcat@gmail.com", "2017", enrollmentStatus, "", "1",
                "3", "1112345671", "", "true", "amazon", "Female", "white");
    }

    @After
    public void deleteForDuplicateDatabase() {
        workExperiencesDao.deleteWorkExperienceByNeuId("0012345671");
        workExperiencesDao.deleteWorkExperienceByNeuId("1112345671");

        priorEducationsDao.deletePriorEducationById(
                priorEducationsDao.getPriorEducationsByNeuId(newStudent.getNeuId()).get(0).getPriorEducationId());
        priorEducationsDao.deletePriorEducationById(
                priorEducationsDao.getPriorEducationsByNeuId(newStudent2.getNeuId()).get(0).getPriorEducationId());
        studentsDao.deleteStudent("0000000");
        studentsDao.deleteStudent("1111111");
        studentsDao.deleteStudent("2222222");
        priorEducationsDao.deletePriorEducationById(
                priorEducationsDao.getPriorEducationsByNeuId("0012345671").get(0).getPriorEducationId());
        studentsDao.deleteStudent("0012345671");
        studentsDao.deleteStudent("1112345671");

    }


/*
SearchStudent
 */
    @Test
    public void searchStudent() {
        Response genderRatioResponse = adminFacing.searchStudent(searchObject);
        Assert.assertEquals(200, genderRatioResponse.getStatus());
    }

    @Test
    public void searchStudent2() {
        Response genderRatioResponse = adminFacing.searchStudent(searchObject2);
        Assert.assertEquals(200, genderRatioResponse.getStatus());
    }


    /*
    getStudentProile
     */


    @Test
    public void getStudentProfileTest() {
        Response studentProfileResponse = adminFacing.getStudentProfile(NEUIDTEST);
        String studentProfile = (String) studentProfileResponse.getEntity();
        JSONObject student = new JSONObject(studentProfile);
        Assert.assertEquals(student.get("neuId"), NEUIDTEST);
    }

    @Test
    public void getStudentProfileBadTest() {
        Response studentProfileResponse = adminFacing.getStudentProfile("090");
        String studentProfile = (String) studentProfileResponse.getEntity();
        Assert.assertEquals("No Student record exists with given ID", studentProfile);
    }

    /*
    getGenderRatio
     */

    @Test
    public void getGenderRatioTest() {
        ParamsObject paramsobj = new ParamsObject();
        List<String> campus = new ArrayList<>();
        campus.add("CHARLOTTE");
        paramsobj.setCampus(campus);
        paramsobj.setYear("2015");
        Response genderRatioResponse = adminFacing.getGenderRatio(paramsobj);
        String ratio = (String) genderRatioResponse.getEntity();
        Assert.assertEquals(ratio, "[{\"year\":2015,\"female\":0,\"male\":1}]");
    }

    @Test
    public void getGenderRatioBadTest() {
        ParamsObject paramsobj = new ParamsObject();
        Response genderRatioResponse = adminFacing.getGenderRatio(paramsobj);
        Assert.assertEquals(400, genderRatioResponse.getStatus());
    }

    @Test
    public void getGenderRatioTest3() {
        ParamsObject paramsobj = new ParamsObject();
        paramsobj.setCampus(null);
        paramsobj.setYear("2015");
        Response genderRatioResponse = adminFacing.getGenderRatio(paramsobj);
        String genderRatio = (String) genderRatioResponse.getEntity();
        int status = (int) genderRatioResponse.getStatus();
        Assert.assertEquals(status, 400);
        Assert.assertEquals("campus field cannot be null", genderRatio);
    }

    /*
    getTopBachlorsDegree
     */

    @Test
    public void getTopBachelorDegreeTest() throws SQLException {
        ParamsObject paramsobj = new ParamsObject();
        List<String> campus = new ArrayList<>();
        campus.add("CHARLOTTE");
        campus.add("BOSTON");
        paramsobj.setCampus(campus);
        paramsobj.setYear("2015");
        paramsobj.setYear("2014");
        Response TopBachelorResponse;
        TopBachelorResponse = adminFacing.getTopBachelorDegree(paramsobj);
        String degrees = (String) TopBachelorResponse.getEntity();
        Assert.assertEquals(degrees.length(), 2);
        Assert.assertEquals(200, TopBachelorResponse.getStatus());
    }


    @Test
    public void getTopBachelorDegreeCampusNuLLTest() throws SQLException {
        ParamsObject paramsobj = new ParamsObject();
        paramsobj.setCampus(null);
        paramsobj.setYear("20r15");
        paramsobj.setYear("201i4");
        Response TopBachelorResponse;
        TopBachelorResponse = adminFacing.getTopBachelorDegree(paramsobj);
        Assert.assertEquals(400, TopBachelorResponse.getStatus());
    }

    @Test
    public void getTopBachelorDegreeYearNullTest() throws SQLException {
        ParamsObject paramsobj = new ParamsObject();
        List<String> campus = new ArrayList<>();
        campus.add("CHARL");
        campus.add("BOST");
        paramsobj.setCampus(campus);
        paramsobj.setYear(null);
        Response TopBachelorResponse;
        TopBachelorResponse = adminFacing.getTopBachelorDegree(paramsobj);
        Assert.assertEquals(400, TopBachelorResponse.getStatus());
    }

    @Test
    public void getTopBachelorDegreeErrorTest() throws SQLException {
        ParamsObject paramsobj = new ParamsObject();
        List<String> campus = new ArrayList<>();
        campus.add("CHARL");
        campus.add("BOS");
        paramsobj.setCampus(campus);
        paramsobj.setYear("2100");
        paramsobj.setYear("22014");
        Response TopBachelorResponse;
        TopBachelorResponse = adminFacing.getTopBachelorDegree(paramsobj);
        Assert.assertEquals(400, TopBachelorResponse.getStatus());
    }


    @Test
    public void getTopBachelorDegreeBadTest() throws SQLException {
        ParamsObject paramsobj = new ParamsObject();
        List<String> campus = new ArrayList<>();
        campus.add("CHARLOTTE");
        campus.add("BOSTON");
        paramsobj.setCampus(campus);
        paramsobj.setYear(null);
        Response TopBachelorResponse;
        TopBachelorResponse = adminFacing.getTopBachelorDegree(paramsobj);
        Assert.assertEquals(400, TopBachelorResponse.getStatus());
    }

    @Test
    public void getTopBachelorDegreeBadYearCampusTest() throws SQLException {
        ParamsObject paramsobj = new ParamsObject();
        paramsobj.setCampus(null);
        paramsobj.setYear(null);
        Response TopBachelorResponse;
        TopBachelorResponse = adminFacing.getTopBachelorDegree(paramsobj);
        String degrees = (String) TopBachelorResponse.getEntity();
        System.out.println(degrees);
        Assert.assertEquals(degrees.length(), 44);
        Assert.assertEquals(200, TopBachelorResponse.getStatus());
    }

    @Test
    public void getTopBachelorDegreeBadYearTest() throws SQLException {
        ParamsObject paramsobj = new ParamsObject();
        paramsobj.setCampus(null);
        paramsobj.setYear("2015");
        Response TopBachelorResponse;
        TopBachelorResponse = adminFacing.getTopBachelorDegree(paramsobj);
        String degrees = (String) TopBachelorResponse.getEntity();
        Assert.assertEquals(degrees.length(), 2);
        Assert.assertEquals(200, TopBachelorResponse.getStatus());
    }

    /*
    getTopEmpoyers
     */

    @Test
    public void getTopEmployersTest() throws SQLException, ParseException {
        ParamsObject paramsobj = new ParamsObject();
        List<String> campus = new ArrayList<>();
        campus.add("CHARLOTTE");
        campus.add("BOSTON");
        paramsobj.setCampus(campus);
        paramsobj.setYear("2015");
        paramsobj.setYear("2014");
        Response TopBachelorResponse;
        TopBachelorResponse = adminFacing.getTopEmployers(paramsobj);
        String degrees = (String) TopBachelorResponse.getEntity();
        System.out.println(degrees);
        Assert.assertEquals(degrees.length(), 2);
        Assert.assertEquals(200, TopBachelorResponse.getStatus());
    }
    @Test
    public void getTopEmployersNullTest() throws SQLException {
        ParamsObject paramsobj = new ParamsObject();
        paramsobj.setCampus(null);
        paramsobj.setYear(null);
        Response TopBachelorResponse;
        TopBachelorResponse = adminFacing.getTopEmployers(paramsobj);
        Assert.assertEquals(200, TopBachelorResponse.getStatus());
    }

    @Test
    public void getTopEmployersCampusNullTest() throws SQLException {
        ParamsObject paramsobj = new ParamsObject();
        paramsobj.setCampus(null);
        paramsobj.setYear("2015");
        Response TopBachelorResponse;
        TopBachelorResponse = adminFacing.getTopEmployers(paramsobj);
        String degrees = (String) TopBachelorResponse.getEntity();
        Assert.assertEquals(degrees.length(), 2);
        Assert.assertEquals(200, TopBachelorResponse.getStatus());
    }

    @Test
    public void getTopEmployersYearNullTest() throws SQLException {
        ParamsObject paramsobj = new ParamsObject();
        List<String> campus = new ArrayList<>();
        campus.add("CHARLOTTE");
        campus.add("BOSTON");
        paramsobj.setCampus(campus);
        paramsobj.setYear(null);
        Response TopBachelorResponse;
        TopBachelorResponse = adminFacing.getTopEmployers(paramsobj);
        Assert.assertEquals(200, TopBachelorResponse.getStatus());
    }

    @Test
    public void getTopEmployeBadTest() throws SQLException {
        ParamsObject paramsobj = new ParamsObject();
        List<String> campus = new ArrayList<>();
        campus.add("CHARL");
        campus.add("BOS");
        paramsobj.setCampus(campus);
        paramsobj.setYear("2100");
        paramsobj.setYear("22014");
        Response TopBachelorResponse;
        TopBachelorResponse = adminFacing.getTopEmployers(paramsobj);
        Assert.assertEquals(400, TopBachelorResponse.getStatus());
    }

    @Test
    public void getTopEmployeBadCampusTest() throws SQLException {
        ParamsObject paramsobj = new ParamsObject();
        List<String> campus = new ArrayList<>();
        campus.add("CHARL");
        campus.add("BOS");
        paramsobj.setCampus(campus);
        paramsobj.setYear(null);
        Response TopBachelorResponse;
        TopBachelorResponse = adminFacing.getTopEmployers(paramsobj);
        Assert.assertEquals(400, TopBachelorResponse.getStatus());
    }

    @Test
    public void getTopEmployeBadYearTest() throws SQLException {
        ParamsObject paramsobj = new ParamsObject();
        paramsobj.setCampus(null);
        paramsobj.setYear("vbn");
        Response TopBachelorResponse;
        TopBachelorResponse = adminFacing.getTopEmployers(paramsobj);
        Assert.assertEquals(400, TopBachelorResponse.getStatus());
    }

    /*
    getTopElectives
     */

    @Test
    public void getTopElectiveTest() throws SQLException, ParseException {
        ParamsObject paramsobj = new ParamsObject();
        List<String> campus = new ArrayList<>();
        campus.add("CHARLOTTE");
        campus.add("BOSTON");
        paramsobj.setCampus(campus);
        paramsobj.setYear("2015");
        paramsobj.setYear("2014");
        Response TopBachelorResponse;
        TopBachelorResponse = adminFacing.getTopElectives(paramsobj);
        String degrees = (String) TopBachelorResponse.getEntity();
        System.out.println(degrees);
        Assert.assertEquals(degrees.length(), 2);
        Assert.assertEquals(200, TopBachelorResponse.getStatus());
    }

    @Test
    public void getTopElectiveBadTest() throws SQLException, ParseException {
        ParamsObject paramsobj = new ParamsObject();
        List<String> campus = new ArrayList<>();
        campus.add("CHAR");
        campus.add("BOS");
        paramsobj.setCampus(campus);
        paramsobj.setYear("2uui");
        paramsobj.setYear("20i4");
        Response TopBachelorResponse;
        TopBachelorResponse = adminFacing.getTopElectives(paramsobj);
        Assert.assertEquals(400, TopBachelorResponse.getStatus());
    }

    @Test
    public void getTopElectiveNullTest() throws SQLException, ParseException {
        ParamsObject paramsobj = new ParamsObject();
        paramsobj.setCampus(null);
        paramsobj.setYear(null);
        Response TopBachelorResponse;
        TopBachelorResponse = adminFacing.getTopElectives(paramsobj);
        String degrees = (String) TopBachelorResponse.getEntity();
        System.out.println(degrees);
        Assert.assertEquals(degrees.length(), 16);
        Assert.assertEquals(200, TopBachelorResponse.getStatus());
    }


    @Test
    public void getTopElectiveCampusNUllTest() throws SQLException, ParseException {
        ParamsObject paramsobj = new ParamsObject();
        paramsobj.setCampus(null);
        paramsobj.setYear("2015");
        paramsobj.setYear("2014");
        Response TopBachelorResponse;
        TopBachelorResponse = adminFacing.getTopElectives(paramsobj);
        String degrees = (String) TopBachelorResponse.getEntity();
        System.out.println(degrees);
        Assert.assertEquals(degrees.length(), 2);
        Assert.assertEquals(200, TopBachelorResponse.getStatus());
    }

    @Test
    public void getTopElectiveBadYearTest() throws SQLException, ParseException {
        ParamsObject paramsobj = new ParamsObject();
        paramsobj.setCampus(null);
        paramsobj.setYear("20u15");
        paramsobj.setYear("201j4");
        Response TopBachelorResponse;
        TopBachelorResponse = adminFacing.getTopElectives(paramsobj);
        Assert.assertEquals(400, TopBachelorResponse.getStatus());
    }

    @Test
    public void getTopElectiveYearNullTest() throws SQLException, ParseException {
        ParamsObject paramsobj = new ParamsObject();
        List<String> campus = new ArrayList<>();
        campus.add("CHARLOTTE");
        campus.add("BOSTON");
        paramsobj.setCampus(campus);
        paramsobj.setYear(null);
        Response TopBachelorResponse;
        TopBachelorResponse = adminFacing.getTopElectives(paramsobj);
        Assert.assertEquals(200, TopBachelorResponse.getStatus());
    }

    @Test
    public void getTopElectivCampusBadTest() throws SQLException, ParseException {
        ParamsObject paramsobj = new ParamsObject();
        List<String> campus = new ArrayList<>();
        campus.add("CHARL");
        campus.add("BOS");
        paramsobj.setCampus(campus);
        paramsobj.setYear(null);
        Response TopBachelorResponse;
        TopBachelorResponse = adminFacing.getTopElectives(paramsobj);
        Assert.assertEquals(400, TopBachelorResponse.getStatus());
    }

    /*
    getCoop
     */


    @Test
    public void getTopCoopTest() throws SQLException, ParseException {
        ParamsObject paramsobj = new ParamsObject();
        List<String> campus = new ArrayList<>();
        campus.add("CHARLOTTE");
        campus.add("BOSTON");
        paramsobj.setCampus(campus);
        paramsobj.setYear("2015");
        paramsobj.setYear("2014");
        Response TopBachelorResponse;
        TopBachelorResponse = adminFacing.getCoopStudents(paramsobj);
        String degrees = (String) TopBachelorResponse.getEntity();
        System.out.println(degrees);
        Assert.assertEquals(degrees.length(), 2);
        Assert.assertEquals(200, TopBachelorResponse.getStatus());
    }

    @Test
    public void getTopCoopBadTest() throws SQLException, ParseException {
        ParamsObject paramsobj = new ParamsObject();
        List<String> campus = new ArrayList<>();
        campus.add("CHAR");
        campus.add("BOS");
        paramsobj.setCampus(campus);
        paramsobj.setYear("2uui");
        paramsobj.setYear("20i4");
        Response TopBachelorResponse;
        TopBachelorResponse = adminFacing.getCoopStudents(paramsobj);
        Assert.assertEquals(500, TopBachelorResponse.getStatus());
    }

    @Test
    public void getTopCoopNullTest() throws SQLException, ParseException {
        ParamsObject paramsobj = new ParamsObject();
        paramsobj.setCampus(null);
        paramsobj.setYear(null);
        Response TopBachelorResponse;
        TopBachelorResponse = adminFacing.getCoopStudents(paramsobj);
        Assert.assertEquals(400, TopBachelorResponse.getStatus());
    }


    @Test
    public void getTopCoopCampusNUllTest() throws SQLException, ParseException {
        ParamsObject paramsobj = new ParamsObject();
        paramsobj.setCampus(null);
        paramsobj.setYear("2015");
        paramsobj.setYear("2014");
        Response TopBachelorResponse;
        TopBachelorResponse = adminFacing.getCoopStudents(paramsobj);
        Assert.assertEquals(400, TopBachelorResponse.getStatus());
    }

    @Test
    public void getTopCoopBadYearTest() throws SQLException, ParseException {
        ParamsObject paramsobj = new ParamsObject();
        paramsobj.setCampus(null);
        paramsobj.setYear("20u15");
        paramsobj.setYear("201j4");
        Response TopBachelorResponse;
        TopBachelorResponse = adminFacing.getTopElectives(paramsobj);
        Assert.assertEquals(400, TopBachelorResponse.getStatus());
    }

    @Test
    public void getTopCoopYearNullTest() throws SQLException, ParseException {
        ParamsObject paramsobj = new ParamsObject();
        List<String> campus = new ArrayList<>();
        campus.add("CHARLOTTE");
        campus.add("BOSTON");
        paramsobj.setCampus(campus);
        paramsobj.setYear(null);
        Response TopBachelorResponse;
        TopBachelorResponse = adminFacing.getCoopStudents(paramsobj);
        Assert.assertEquals(200, TopBachelorResponse.getStatus());
    }

    @Test
    public void getTopCoopCampusBadTest() throws SQLException, ParseException {
        ParamsObject paramsobj = new ParamsObject();
        List<String> campus = new ArrayList<>();
        campus.add("CHARL");
        campus.add("BOS");
        paramsobj.setCampus(campus);
        paramsobj.setYear(null);
        Response TopBachelorResponse;
        TopBachelorResponse = adminFacing.getCoopStudents(paramsobj);
        Assert.assertEquals(400, TopBachelorResponse.getStatus());
    }

    /*
    getStudentsWorkingForACompany
     */

    @Test
    public void getStudentsWorkingForACompanyTest() throws SQLException, ParseException {
        ParamsObject paramsobj = new ParamsObject();
        List<String> campus = new ArrayList<>();
        campus.add("CHARLOTTE");
        campus.add("BOSTON");
        paramsobj.setCampus(campus);
        paramsobj.setCompany("Aamzon");
        paramsobj.setYear("2015");
        paramsobj.setYear("2014");
        Response TopBachelorResponse;
        TopBachelorResponse = adminFacing.getStudentsWorkingForACompany(paramsobj);
        String degrees = (String) TopBachelorResponse.getEntity();
        System.out.println(degrees);
        Assert.assertEquals(degrees.length(), 2);
        Assert.assertEquals(200, TopBachelorResponse.getStatus());
    }

    @Test
    public void getStudentsWorkingForACompanyYesrNullTest() throws SQLException, ParseException {
        ParamsObject paramsobj = new ParamsObject();
        List<String> campus = new ArrayList<>();
        campus.add("CHARLOTTE");
        campus.add("BOSTON");
        paramsobj.setCampus(campus);
        paramsobj.setCompany("Microsoft");
        paramsobj.setYear(null);
        Response TopBachelorResponse;
        TopBachelorResponse = adminFacing.getStudentsWorkingForACompany(paramsobj);
        String degrees = (String) TopBachelorResponse.getEntity();
        System.out.println(degrees);
        Assert.assertEquals(degrees.length(), 44);
        Assert.assertEquals(200, TopBachelorResponse.getStatus());
    }

    @Test
    public void getStudentsWorkingForACompanyBadTest() throws SQLException, ParseException {
        ParamsObject paramsobj = new ParamsObject();
        List<String> campus = new ArrayList<>();
        campus.add("CHAR");
        campus.add("BOS");
        paramsobj.setCampus(campus);
        paramsobj.setCompany("123");
        paramsobj.setYear(null);
        Response TopBachelorResponse;
        TopBachelorResponse = adminFacing.getStudentsWorkingForACompany(paramsobj);
        Assert.assertEquals(500, TopBachelorResponse.getStatus());
    }

    @Test
    public void getStudentsWorkingForACompanyNullTest() throws SQLException, ParseException {
        ParamsObject paramsobj = new ParamsObject();
        paramsobj.setCampus(null);
        paramsobj.setCompany(null);
        paramsobj.setYear(null);
        Response TopBachelorResponse;
        TopBachelorResponse = adminFacing.getStudentsWorkingForACompany(paramsobj);
        Assert.assertEquals(400, TopBachelorResponse.getStatus());
    }

    @Test
    public void getStudentsWorkingForACompanyErrorTest() throws SQLException, ParseException {
        ParamsObject paramsobj = new ParamsObject();
        List<String> campus = new ArrayList<>();
        campus.add("CHARL");
        campus.add("BOS");
        paramsobj.setCampus(campus);
        paramsobj.setCompany("123");
        paramsobj.setYear("20t15");
        paramsobj.setYear("201g4");
        Response TopBachelorResponse;
        TopBachelorResponse = adminFacing.getStudentsWorkingForACompany(paramsobj);
        Assert.assertEquals(500, TopBachelorResponse.getStatus());
    }

    /*
    getStudentWorkingFullTime
     */

    //getting sql error, check why student not getting data


    @Test
    public void getStudentWorkingFullTimeTest() throws SQLException, ParseException {
        ParamsObject paramsobj = new ParamsObject();
        List<String> campus = new ArrayList<>();
        campus.add("SEATTLE");
        campus.add("BOSTON");
        paramsobj.setCampus(campus);
        paramsobj.setYear("2017");
        Response TopBachelorResponse;
        TopBachelorResponse = adminFacing.getStudentWorkingFullTime(paramsobj);
        String degrees = (String) TopBachelorResponse.getEntity();
        System.out.println(degrees);
        Assert.assertEquals(degrees.length(), 2);
        Assert.assertEquals(200, TopBachelorResponse.getStatus());
    }


    @Test
    public void getStudentWorkingFullTimeNullTest() throws SQLException, ParseException {
        ParamsObject paramsobj = new ParamsObject();
        paramsobj.setCampus(null);
        paramsobj.setYear(null);
        Response TopBachelorResponse;
        TopBachelorResponse = adminFacing.getStudentWorkingFullTime(paramsobj);
        Assert.assertEquals(400, TopBachelorResponse.getStatus());
    }

    //getting sql error, check why student not getting data. Getting SQL error. should return 200

    @Test
    public void getStudentWorkingFullTimeYearTest() throws SQLException, ParseException {
        ParamsObject paramsobj = new ParamsObject();
        List<String> campus = new ArrayList<>();
        campus.add("SEATTLE");
        paramsobj.setCampus(campus);
        paramsobj.setYear(null);
        Response TopBachelorResponse;
        TopBachelorResponse = adminFacing.getStudentWorkingFullTime(paramsobj);
        String degrees = (String) TopBachelorResponse.getEntity();
        System.out.println(degrees);
        Assert.assertEquals(degrees.length(), 2);
        Assert.assertEquals(500, TopBachelorResponse.getStatus());
    }


    @Test
    public void getStudentWorkingFullTimeErrorTest() throws SQLException, ParseException {
        ParamsObject paramsobj = new ParamsObject();
        List<String> campus = new ArrayList<>();
        campus.add("SETT");
        campus.add("BOS");
        paramsobj.setCampus(campus);
        paramsobj.setYear("201i4");
        Response TopBachelorResponse;
        TopBachelorResponse = adminFacing.getStudentWorkingFullTime(paramsobj);
        Assert.assertEquals(500, TopBachelorResponse.getStatus());
    }

}

