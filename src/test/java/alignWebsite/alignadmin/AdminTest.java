package alignWebsite.alignadmin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Context;
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
import org.mehaexample.asdDemo.restModels.PasswordChangeObject;
import org.mehaexample.asdDemo.restModels.PasswordCreateObject;
import org.mehaexample.asdDemo.restModels.PasswordResetObject;


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
    SearchObject searchObject3;
    SearchObject searchObject4;
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
    AdministratorNotes administratorNotes;
    Administrators administrators;
    AdministratorNotes administratorNotes2;
    Administrators administrators2;
    Administrators administrators3;
    Administrators administratorsAdmin;
    AdminLogins adminLogins;
    PasswordCreateObject passwordCreateObject;


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

        List<String> campusSearchObjEmpty = new ArrayList<>();
        List<String> enrollmentStatusEmpty = new ArrayList<>();


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
                EnrollmentStatus.FULL_TIME, Campus.BOSTON, DegreeCandidacy.BACHELORS, null, true);
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
        newWorkExperience5.setStartDate(dateFormat.parse("2016-06-01"));
        newWorkExperience5.setEndDate(dateFormat.parse("2016-12-01"));
        newWorkExperience5.setCurrentJob(true);
        newWorkExperience5.setCoop(false);
        newWorkExperience5.setTitle("Title");
        newWorkExperience5.setDescription("Description");
        newWorkExperience5.setNeuId("0012345671");
        newWorkExperience5.setCompanyName("Google");
        workExperiencesDao.createWorkExperience(newWorkExperience5);


        priorEducations3 = new PriorEducations();
        priorEducations3.setGraduationDate(dateFormat.parse("2016-01-01"));
        priorEducations3.setGpa(3.50f);
        priorEducations3.setDegreeCandidacy(DegreeCandidacy.BACHELORS);
        priorEducations3.setNeuId(student.getNeuId());
        priorEducations3.setMajorName("IT");
        priorEducations3.setInstitutionName("NYU");

        priorEducationsDao.createPriorEducation(priorEducations3);

        priorEducations4 = new PriorEducations();
        priorEducations4.setGraduationDate(dateFormat.parse("2016-01-01"));
        priorEducations4.setGpa(3.50f);
        priorEducations4.setDegreeCandidacy(DegreeCandidacy.BACHELORS);
        priorEducations4.setNeuId("1112345671");
        priorEducations4.setMajorName("CD");
        priorEducations4.setInstitutionName("NEU");


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
                "3", "0012345671", "", "true", "amazon", "Male", "white");

        searchObject2 = new SearchObject(campusSearchObj, "2015", "Tom",
                "Cat", "tomcat@gmail.com", "2017", enrollmentStatus, "", "1",
                "3", "1112345671", "", "true", "amazon", "FemaLe", "white");

        searchObject3 = new SearchObject(null, null, null,
                null, null, null, null, null, null,
                null, null, null, null, null, null, null);

        searchObject4 = new SearchObject(campusSearchObjEmpty, null, null,
                null, null, null, enrollmentStatusEmpty, null, null,
                null, null, null, null, null, null, null);

        administrators = new Administrators("3","admintest@gmail.com",
                "fadmin","madmin","ladmin");

        administratorsDao.addAdministrator(administrators);

        administratorNotes = new AdministratorNotes("0000000", "3", "test", "test");
        administratorNotes.setAdministratorNoteId(030);
        administratorNotesDao.addAdministratorNoteRecord(administratorNotes);


       administratorsAdmin = new Administrators("135","krishnakaranam3732@gmail.com",
                "fadmin3","madmin3","ladmi3");
        administratorsDao.addAdministrator(administratorsAdmin);

        adminLogins = new AdminLogins("krishnakaranam3732@gmail.com",
                "$s0$41010$cwF4TDlHcEf5+zxUKgsA3w==$vlMxt0lC641Vdavp9nclzELFgS3YgkuG9GBTgeUKfwQ=",
                "key",
                Timestamp.valueOf("2017-09-23 10:10:10.0"),
                Timestamp.valueOf("2019-09-23 10:10:10.0"),
                true);

        adminLoginsDao.createAdminLogin(adminLogins);


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

        administratorsDao.deleteAdministrator(administrators.getAdministratorNeuId());
        administratorsDao.deleteAdministrator(administratorsAdmin.getAdministratorNeuId());

        administratorNotesDao.deleteAdministratorNoteRecord(administratorNotes.getAdministratorNoteId());


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

    @Test
    public void searchStudent3() {
        Response genderRatioResponse = adminFacing.searchStudent(searchObject3);
        Assert.assertEquals(200, genderRatioResponse.getStatus());
    }

    @Test
    public void searchStudent4() {
        Response genderRatioResponse = adminFacing.searchStudent(searchObject4);
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
    public void getTopBachelorDegreeYearNullTest() throws SQLException {
        ParamsObject paramsobj = new ParamsObject();
        List<String> campus = new ArrayList<>();
        campus.add("CHARLOTTE");
        campus.add("BOSTON");
        paramsobj.setCampus(campus);
        paramsobj.setYear(null);
        Response TopBachelorResponse;
        TopBachelorResponse = adminFacing.getTopBachelorDegree(paramsobj);
        String degrees = (String) TopBachelorResponse.getEntity();
        //Assert.assertEquals(degrees.length(), 30);
        Assert.assertEquals(200, TopBachelorResponse.getStatus());
    }

    @Test
    public void getTopBachelorDegreeCampusNullTest() throws SQLException {
        ParamsObject paramsobj = new ParamsObject();
        paramsobj.setCampus(null);
        paramsobj.setYear("2015");
        paramsobj.setYear("2014");
        paramsobj.setYear("2016");
        Response TopBachelorResponse;
        TopBachelorResponse = adminFacing.getTopBachelorDegree(paramsobj);
        Assert.assertEquals(200, TopBachelorResponse.getStatus());
    }

    @Test
    public void getTopBachelorDegreeNullTest() throws SQLException {
        ParamsObject paramsobj = new ParamsObject();
        paramsobj.setCampus(null);
        paramsobj.setYear(null);
        Response TopBachelorResponse;
        TopBachelorResponse = adminFacing.getTopBachelorDegree(paramsobj);
        System.out.println(TopBachelorResponse.getEntity());
        Assert.assertEquals(200, TopBachelorResponse.getStatus());
    }


//
//
//    @Test
//    public void getTopBachelorDegreeCampusNuLLTest() throws SQLException {
//        ParamsObject paramsobj = new ParamsObject();
//        paramsobj.setCampus(null);
//        paramsobj.setYear("20r15");
//        paramsobj.setYear("201i4");
//        Response TopBachelorResponse;
//        TopBachelorResponse = adminFacing.getTopBachelorDegree(paramsobj);
//        Assert.assertEquals(400, TopBachelorResponse.getStatus());
//    }
//
//    @Test
//    public void getTopBachelorDegreeYearNullTest() throws SQLException {
//        ParamsObject paramsobj = new ParamsObject();
//        List<String> campus = new ArrayList<>();
//        campus.add("CHARL");
//        campus.add("BOST");
//        paramsobj.setCampus(campus);
//        paramsobj.setYear(null);
//        Response TopBachelorResponse;
//        TopBachelorResponse = adminFacing.getTopBachelorDegree(paramsobj);
//        Assert.assertEquals(400, TopBachelorResponse.getStatus());
//    }
//
//    @Test
//    public void getTopBachelorDegreeErrorTest() throws SQLException {
//        ParamsObject paramsobj = new ParamsObject();
//        List<String> campus = new ArrayList<>();
//        campus.add("CHARL");
//        campus.add("BOS");
//        paramsobj.setCampus(campus);
//        paramsobj.setYear("21i00");
//        paramsobj.setYear("220i14");
//        Response TopBachelorResponse;
//        TopBachelorResponse = adminFacing.getTopBachelorDegree(paramsobj);
//        Assert.assertEquals(400, TopBachelorResponse.getStatus());
//    }
//
//
//    @Test
//    public void getTopBachelorDegreeBadTest() throws SQLException, ParseException {
//
//
//       Students student3 = new Students("990", "jerry@gmail.com", "Jerry", "",
//                "Mouse", Gender.F, "F1", "1111111111",
//                "401 Terry Ave", "WA", "Boston", "98109",
//                Term.FALL, 2014, Term.SPRING, 2016,
//                EnrollmentStatus.FULL_TIME, Campus.BOSTON, DegreeCandidacy.BACHELORS, null, true);
//        studentsDao.addStudent(student3);
//
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        PriorEducations newPriorEducation = new PriorEducations();
//        newPriorEducation.setGraduationDate(dateFormat.parse("2015-01-01"));
//        newPriorEducation.setGpa(3.50f);
//        newPriorEducation.setDegreeCandidacy(DegreeCandidacy.BACHELORS);
//        newPriorEducation.setNeuId(student3.getNeuId());
//        newPriorEducation.setMajorName("Computer Science");
//        newPriorEducation.setInstitutionName("University of Washington");
//
//        ParamsObject paramsobj = new ParamsObject();
//        List<String> campus = new ArrayList<>();
//        System.out.println("before"+campus.size());
//        campus.add("BOSTON");
//        System.out.println(campus.size());
//        paramsobj.setCampus(campus);
//        System.out.println("params"+paramsobj.getCampus().size());
//        Response TopBachelorResponse;
//        TopBachelorResponse = adminFacing.getTopBachelorDegree(paramsobj);
//        Assert.assertEquals(200, TopBachelorResponse.getStatus());
//
//        studentsDao.deleteStudent(student3.getNeuId());
//    }
//
//    @Test
//    public void getTopBachelorDegreeBadCampusTest() throws SQLException {
//        ParamsObject paramsobj = new ParamsObject();
//        List<String> campus = new ArrayList<>();
//        paramsobj.setCampus(campus);
//        paramsobj.setYear(null);
//        Response TopBachelorResponse;
//        TopBachelorResponse = adminFacing.getTopBachelorDegree(paramsobj);
//        Assert.assertEquals(400, TopBachelorResponse.getStatus());
//    }
//
//    @Test
//    public void getTopBachelorDegreeBadCampusYearTest() throws SQLException {
//        ParamsObject paramsobj = new ParamsObject();
//        List<String> campus = new ArrayList<>();
//        paramsobj.setCampus(campus);
//        Response TopBachelorResponse;
//        TopBachelorResponse = adminFacing.getTopBachelorDegree(paramsobj);
//        Assert.assertEquals(400, TopBachelorResponse.getStatus());
//    }
//
//    @Test
//    public void getTopBachelorDegreeEmptyInputTest() throws SQLException {
//        ParamsObject paramsobj = new ParamsObject();
//        Response TopBachelorResponse;
//        TopBachelorResponse = adminFacing.getTopBachelorDegree(paramsobj);
//        Assert.assertEquals(400, TopBachelorResponse.getStatus());
//    }
//
//    @Test
//    public void getTopBachelorDegreeBadYearCampusTest() throws SQLException {
//        ParamsObject paramsobj = new ParamsObject();
//        paramsobj.setCampus(null);
//        paramsobj.setYear(null);
//        Response TopBachelorResponse;
//        TopBachelorResponse = adminFacing.getTopBachelorDegree(paramsobj);
////        String degrees = (String) TopBachelorResponse.getEntity();
////        System.out.println(degrees);
////        Assert.assertEquals(degrees.length(), 102);
//        Assert.assertEquals(400, TopBachelorResponse.getStatus());
//    }
//
//    @Test
//    public void getTopBachelorDegreeBadYearTest() throws SQLException {
//        ParamsObject paramsobj = new ParamsObject();
//        paramsobj.setCampus(null);
//        paramsobj.setYear("2015");
//        Response TopBachelorResponse;
//        TopBachelorResponse = adminFacing.getTopBachelorDegree(paramsobj);
////        String degrees = (String) TopBachelorResponse.getEntity();
////        Assert.assertEquals(degrees.length(), 2);
//        Assert.assertEquals(400, TopBachelorResponse.getStatus());
//    }

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
    public void getTopBachelorDegreeBadEmptyTest() throws SQLException {
        ParamsObject paramsobj = new ParamsObject();
        List<String> campus = new ArrayList<>();
        Response TopBachelorResponse;
        TopBachelorResponse = adminFacing.getTopEmployers(paramsobj);
        Assert.assertEquals(200, TopBachelorResponse.getStatus());
    }

    @Test
    public void getTopEmployeerEmptyInputTest() throws SQLException {
        ParamsObject paramsobj = new ParamsObject();
        Response TopBachelorResponse;
        TopBachelorResponse = adminFacing.getTopEmployers(paramsobj);
        Assert.assertEquals(200, TopBachelorResponse.getStatus());
    }

    @Test
    public void getTopBachelorDegreeEmptyTest() throws SQLException {
        ParamsObject paramsobj = new ParamsObject();
        List<String> campus = new ArrayList<>();
        paramsobj.setCampus(campus);
        Response TopBachelorResponse;
        TopBachelorResponse = adminFacing.getTopEmployers(paramsobj);
        Assert.assertEquals(200, TopBachelorResponse.getStatus());
    }

    @Test
    public void getTopEmployersCampusNullTest() throws SQLException {
        ParamsObject paramsobj = new ParamsObject();
        paramsobj.setCampus(null);
        paramsobj.setYear("2015");
        paramsobj.setYear("2014");
        paramsobj.setYear("2016");
        paramsobj.setYear("2017");
        paramsobj.setYear("2018");
        Response TopBachelorResponse;
        TopBachelorResponse = adminFacing.getTopEmployers(paramsobj);
//        String degrees = (String) TopBachelorResponse.getEntity();
//        Assert.assertEquals(degrees.length(), 2);
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
        System.out.println(TopBachelorResponse.getEntity());
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
//        String degrees = (String) TopBachelorResponse.getEntity();
//        System.out.println(degrees);
//        Assert.assertEquals(degrees.length(), 2);
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
    public void getTopElectiveBadYearEmptyTest() throws SQLException, ParseException {
        ParamsObject paramsobj = new ParamsObject();
        Response TopBachelorResponse;
        TopBachelorResponse = adminFacing.getTopElectives(paramsobj);
        Assert.assertEquals(200, TopBachelorResponse.getStatus());
    }

    @Test
    public void getTopElectiveEmptyInputTest() throws SQLException, ParseException {
        ParamsObject paramsobj = new ParamsObject();
        List<String> campus = new ArrayList<>();
        paramsobj.setCampus(campus);
        Response TopBachelorResponse;
        TopBachelorResponse = adminFacing.getTopElectives(paramsobj);
        Assert.assertEquals(200, TopBachelorResponse.getStatus());
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
        Assert.assertEquals(400, TopBachelorResponse.getStatus());
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
        paramsobj.setYear("2017");
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
        Assert.assertEquals(400, TopBachelorResponse.getStatus());
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
        Assert.assertEquals(400, TopBachelorResponse.getStatus());
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
        paramsobj.setYear("2016");
        Response TopBachelorResponse;
        TopBachelorResponse = adminFacing.getStudentWorkingFullTime(paramsobj);
        String degrees = (String) TopBachelorResponse.getEntity();
        System.out.println(degrees);
        Assert.assertEquals(degrees.length(), 124);
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

//    @Test
//    public void getStudentWorkingFullTimeCampusTest() throws SQLException, ParseException {
//        ParamsObject paramsobj = new ParamsObject();
//        paramsobj.setCampus(null);
//        paramsobj.setYear(null);
//        Response TopBachelorResponse;
//        TopBachelorResponse = adminFacing.getStudentWorkingFullTime(paramsobj);
//        Assert.assertEquals("Campus cannot be null.", TopBachelorResponse.getEntity());
//    }

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
//        String degrees = (String) TopBachelorResponse.getEntity();
//        System.out.println(degrees);
//        Assert.assertEquals(degrees.length(), 2);
        Assert.assertEquals(200, TopBachelorResponse.getStatus());
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
        Assert.assertEquals(400, TopBachelorResponse.getStatus());
    }

    /*
    getStudentundergradInstitutuins
     */

    @Test
    public void getStudentundergradInstitutuinsTest() throws SQLException, ParseException {
        ParamsObject paramsobj = new ParamsObject();
        List<String> campus = new ArrayList<>();
        campus.add("SEATTLE");
        campus.add("BOSTON");
        paramsobj.setCampus(campus);
        paramsobj.setYear("2016");
        Response TopBachelorResponse;
        TopBachelorResponse = adminFacing.getStudentundergradInstitutuins(paramsobj);
        String degrees = (String) TopBachelorResponse.getEntity();
        System.out.println(degrees);
        Assert.assertEquals(degrees.length(), 97);
        Assert.assertEquals(200, TopBachelorResponse.getStatus());
    }


    @Test
    public void getundergradInstitutuinsCampusTest() throws SQLException, ParseException {
        ParamsObject paramsobj = new ParamsObject();
        List<String> campus = new ArrayList<>();
        campus.add("SEATTLE");
        campus.add("BOSTON");
        paramsobj.setCampus(campus);
        paramsobj.setYear(null);
        Response TopBachelorResponse;
        TopBachelorResponse = adminFacing.getStudentundergradInstitutuins(paramsobj);
        String degrees = (String) TopBachelorResponse.getEntity();
        System.out.println(degrees);
        //Assert.assertEquals(degrees.length(), 97);
        Assert.assertEquals(200, TopBachelorResponse.getStatus());
    }


    @Test
    public void getundergradInstitutuinsCampusNullTest() throws SQLException, ParseException {
        ParamsObject paramsobj = new ParamsObject();
        paramsobj.setCampus(null);
        paramsobj.setYear(null);
        Response TopBachelorResponse;
        TopBachelorResponse = adminFacing.getStudentundergradInstitutuins(paramsobj);
        Assert.assertEquals(400, TopBachelorResponse.getStatus());
    }


    @Test
    public void getStudentundergradInstitutuinsBadTest() throws SQLException, ParseException {
        ParamsObject paramsobj = new ParamsObject();
        List<String> campus = new ArrayList<>();
        campus.add("SEATT");
        campus.add("BOS");
        paramsobj.setCampus(campus);
        paramsobj.setYear("20i16");
        Response TopBachelorResponse;
        TopBachelorResponse = adminFacing.getStudentundergradInstitutuins(paramsobj);
        Assert.assertEquals(400, TopBachelorResponse.getStatus());
    }

//    @Test
//    public void getundergradInstitutuinsCampusStringTest() throws SQLException, ParseException {
//        ParamsObject paramsobj = new ParamsObject();
//        paramsobj.setCampus(null);
//        paramsobj.setYear(null);
//        Response TopBachelorResponse;
//        TopBachelorResponse = adminFacing.getStudentundergradInstitutuins(paramsobj);
//        Assert.assertEquals("Campus cannot be null.", TopBachelorResponse.getEntity());
//    }

//    @Test
//    public void getundergradInstitutuinsErrorTest() throws SQLException, ParseException {
//        ParamsObject paramsobj = new ParamsObject();
//        paramsobj.setCampus(null);
//        Response TopBachelorResponse;
//        TopBachelorResponse = adminFacing.getStudentundergradInstitutuins(paramsobj);
//        Assert.assertEquals("Campus cannot be null.", TopBachelorResponse.getEntity());
//    }


    /*
    UpdateNote
     */

    @Test
    public void UpdateNoteTest() throws SQLException, ParseException {
        AdministratorNotes input = new AdministratorNotes();
        input.setTitle("test");
        input.setDesc("test");
        input.setNeuId("0012345671");
        input.setAdministratorNeuId("3");
        Response TopBachelorResponse;
        TopBachelorResponse = adminFacing.updateNote(input,Integer.toString(administratorNotes.getAdministratorNoteId()));
        Assert.assertEquals(200, TopBachelorResponse.getStatus());
    }

    @Test
    public void UpdateNoteErrorTest() throws SQLException, ParseException {
        AdministratorNotes input = new AdministratorNotes();
        input.setTitle("test");
        input.setDesc("test");
        input.setNeuId("0012345671");
        input.setAdministratorNeuId("3");
        Response TopBachelorResponse;
        TopBachelorResponse = adminFacing.updateNote(input,"ui");
        Assert.assertEquals(404, TopBachelorResponse.getStatus());
    }

    @Test
    public void UpdateNoteNullTest() throws SQLException, ParseException {
        AdministratorNotes input = new AdministratorNotes();
        input.setTitle(null);
        input.setDesc(null);
        input.setNeuId(null);
        input.setAdministratorNeuId(null);
        Response TopBachelorResponse;
        TopBachelorResponse = adminFacing.updateNote(input,Integer.toString(administratorNotes.getAdministratorNoteId()));
        Assert.assertEquals(200, TopBachelorResponse.getStatus());
    }

    /*
    create note
     */

    @Test
    public void CreateNoteTest() throws SQLException, ParseException {
        AdministratorNotes input = new AdministratorNotes();
        input.setTitle("test");
        input.setDesc("test");
        input.setNeuId("0012345671");
        input.setAdministratorNeuId(administrators.getAdministratorNeuId());
        Response TopBachelorResponse;
        TopBachelorResponse = adminFacing.createNote(input,administrators.getAdministratorNeuId());
        Assert.assertEquals(200, TopBachelorResponse.getStatus());
    }

    @Test
    public void CreateNoteNotFoundTest() throws SQLException, ParseException {
        AdministratorNotes input = new AdministratorNotes();
        input.setTitle("test");
        input.setDesc("test");
        input.setNeuId("0012345671");
        input.setAdministratorNeuId(administrators.getAdministratorNeuId());
        Response TopBachelorResponse;
        TopBachelorResponse = adminFacing.createNote(input,"none");
        Assert.assertEquals(404, TopBachelorResponse.getStatus());
    }

    @Test
    public void DeleteNoteTest() throws SQLException, ParseException {

        administrators2 = new Administrators("12","admintest12@gmail.com",
                "fadmin2","madmin2","ladmin2");

        administratorsDao.addAdministrator(administrators2);

        administratorNotes2 = new AdministratorNotes("0", "12", "test2", "test2");
        administratorNotes2.setAdministratorNoteId(7);
        administratorNotesDao.addAdministratorNoteRecord(administratorNotes2);


        Response TopBachelorResponse;
        TopBachelorResponse = adminFacing.deleteNote(Integer.toString(administratorNotes2.getAdministratorNoteId()));
        Assert.assertEquals(200, TopBachelorResponse.getStatus());

        administratorsDao.deleteAdministrator(administrators2.getAdministratorNeuId());
    }

    @Test
    public void DeleteNoteErrorTest() throws SQLException, ParseException {
        Response TopBachelorResponse;
        TopBachelorResponse = adminFacing.deleteNote("none");
        Assert.assertEquals(404, TopBachelorResponse.getStatus());
    }

    /*
    createPassword
     */

    @Test
    public void CreatePasswordTest() throws SQLException, ParseException {


        administrators3 = new Administrators("19","adminlogintest13@gmail.com",
                "fadmin3","madmin3","ladmi3");
        administratorsDao.addAdministrator(administrators3);

        adminLogins = new AdminLogins("adminlogintest13@gmail.com",
                "password",
                "key",
                Timestamp.valueOf("2017-09-23 10:10:10.0"),
                Timestamp.valueOf("2019-09-23 10:10:10.0"),
                false);
        adminLoginsDao.createAdminLogin(adminLogins);

        passwordCreateObject = new PasswordCreateObject("adminlogintest13@gmail.com",
                "passwordTest","key");

        Response TopBachelorResponse;
        TopBachelorResponse = adminFacing.createPassword(passwordCreateObject);
        Assert.assertEquals(200, TopBachelorResponse.getStatus());

        adminLoginsDao.deleteAdminLogin("adminlogintest13@gmail.com");

        administratorsDao.deleteAdministrator(administrators3.getAdministratorNeuId());


    }

    @Test
    public void CreatePasswordRegitrationExpTest() throws SQLException, ParseException {

        Administrators administrators6 = new Administrators("45","adminloginRegtest12@gmail.com",
                "fadmin3","madmin3","ladmi3");
        administratorsDao.addAdministrator(administrators6);

        adminLogins = new AdminLogins("adminloginRegtest12@gmail.com",
                "password",
                "key",
                Timestamp.valueOf("2017-09-23 10:10:10.0"),
                Timestamp.valueOf("2017-09-23 10:10:10.0"),
                false);
        adminLoginsDao.createAdminLogin(adminLogins);

        passwordCreateObject = new PasswordCreateObject("adminloginRegtest12@gmail.com",
                "passwordTest","key");

        Response TopBachelorResponse;
        TopBachelorResponse = adminFacing.createPassword(passwordCreateObject);
        Assert.assertEquals(200, TopBachelorResponse.getStatus());

        adminLoginsDao.deleteAdminLogin("adminloginRegtest12@gmail.com");

        administratorsDao.deleteAdministrator(administrators6.getAdministratorNeuId());
    }

    @Test
    public void CreatePasswordNoEmailExpTest() throws SQLException, ParseException {

        passwordCreateObject = new PasswordCreateObject("test@gmail.com",
                "passwordTest","key");

        Response TopBachelorResponse;
        TopBachelorResponse = adminFacing.createPassword(passwordCreateObject);
        Assert.assertEquals(400, TopBachelorResponse.getStatus());

    }

    @Test
    public void CreatePasswordErrorTest() throws SQLException, ParseException {

        Administrators administrators7 = new Administrators("10","admin2@gmail.com",
                "fadmin3","madmin3","ladmi3");
        administratorsDao.addAdministrator(administrators7);

        adminLogins = new AdminLogins("admin2@gmail.com",
                "password",
                "key",
                Timestamp.valueOf("2017-09-23 10:10:10.0"),
                Timestamp.valueOf("2017-09-23 10:10:10.0"),
                false);
        adminLoginsDao.createAdminLogin(adminLogins);

        passwordCreateObject = new PasswordCreateObject("admin2@gmail.com",
                "passwordTest","key1");

        Response TopBachelorResponse;
        TopBachelorResponse = adminFacing.createPassword(passwordCreateObject);
        Assert.assertEquals(400, TopBachelorResponse.getStatus());

        adminLoginsDao.deleteAdminLogin("admin2@gmail.com");

        administratorsDao.deleteAdministrator(administrators7.getAdministratorNeuId());
    }

    @Test
    public void CreatePassworddataErrorTest() throws SQLException, ParseException {
        passwordCreateObject = new PasswordCreateObject();
        Response TopBachelorResponse;
        TopBachelorResponse = adminFacing.createPassword(passwordCreateObject);
        Assert.assertEquals(400, TopBachelorResponse.getStatus());

    }

    /*
    AdminLogin
     */


    @Test
    public void AdminLoginTest() throws SQLException, ParseException {

        HttpServletRequest request = new HttpServletRequest() {
            @Override
            public String getAuthType() {
                return null;
            }

            @Override
            public Cookie[] getCookies() {
                return new Cookie[0];
            }

            @Override
            public long getDateHeader(String s) {
                return 0;
            }

            @Override
            public String getHeader(String s) {
                return null;
            }

            @Override
            public Enumeration getHeaders(String s) {
                return null;
            }

            @Override
            public Enumeration getHeaderNames() {
                return null;
            }

            @Override
            public int getIntHeader(String s) {
                return 0;
            }

            @Override
            public String getMethod() {
                return null;
            }

            @Override
            public String getPathInfo() {
                return null;
            }

            @Override
            public String getPathTranslated() {
                return null;
            }

            @Override
            public String getContextPath() {
                return null;
            }

            @Override
            public String getQueryString() {
                return null;
            }

            @Override
            public String getRemoteUser() {
                return null;
            }

            @Override
            public boolean isUserInRole(String s) {
                return false;
            }

            @Override
            public Principal getUserPrincipal() {
                return null;
            }

            @Override
            public String getRequestedSessionId() {
                return null;
            }

            @Override
            public String getRequestURI() {
                return null;
            }

            @Override
            public StringBuffer getRequestURL() {
                return null;
            }

            @Override
            public String getServletPath() {
                return null;
            }

            @Override
            public HttpSession getSession(boolean b) {
                return null;
            }

            @Override
            public HttpSession getSession() {
                return null;
            }

            @Override
            public boolean isRequestedSessionIdValid() {
                return false;
            }

            @Override
            public boolean isRequestedSessionIdFromCookie() {
                return false;
            }

            @Override
            public boolean isRequestedSessionIdFromURL() {
                return false;
            }

            @Override
            public boolean isRequestedSessionIdFromUrl() {
                return false;
            }

            @Override
            public Object getAttribute(String s) {
                return null;
            }

            @Override
            public Enumeration getAttributeNames() {
                return null;
            }

            @Override
            public String getCharacterEncoding() {
                return null;
            }

            @Override
            public void setCharacterEncoding(String s) throws UnsupportedEncodingException {

            }

            @Override
            public int getContentLength() {
                return 0;
            }

            @Override
            public String getContentType() {
                return null;
            }

            @Override
            public ServletInputStream getInputStream() throws IOException {
                return null;
            }

            @Override
            public String getParameter(String s) {
                return null;
            }

            @Override
            public Enumeration getParameterNames() {
                return null;
            }

            @Override
            public String[] getParameterValues(String s) {
                return new String[0];
            }

            @Override
            public Map getParameterMap() {
                return null;
            }

            @Override
            public String getProtocol() {
                return null;
            }

            @Override
            public String getScheme() {
                return null;
            }

            @Override
            public String getServerName() {
                return null;
            }

            @Override
            public int getServerPort() {
                return 0;
            }

            @Override
            public BufferedReader getReader() throws IOException {
                return null;
            }

            @Override
            public String getRemoteAddr() {
                return null;
            }

            @Override
            public String getRemoteHost() {
                return null;
            }

            @Override
            public void setAttribute(String s, Object o) {

            }

            @Override
            public void removeAttribute(String s) {

            }

            @Override
            public Locale getLocale() {
                return null;
            }

            @Override
            public Enumeration getLocales() {
                return null;
            }

            @Override
            public boolean isSecure() {
                return false;
            }

            @Override
            public RequestDispatcher getRequestDispatcher(String s) {
                return null;
            }

            @Override
            public String getRealPath(String s) {
                return null;
            }

            @Override
            public int getRemotePort() {
                return 0;
            }

            @Override
            public String getLocalName() {
                return null;
            }

            @Override
            public String getLocalAddr() {
                return null;
            }

            @Override
            public int getLocalPort() {
                return 0;
            }
        };

        LoginObject loginObject = new LoginObject("krishnakaranam3732@gmail.com","$s0$41010$cwF4TDlHcEf5+zxUKgsA3w==$vlMxt0lC641Vdavp9nclzELFgS3YgkuG9GBTgeUKfwQ=");

        Response TopBachelorResponse;
        TopBachelorResponse = adminFacing.loginUser(request ,loginObject);
        Assert.assertEquals(401, TopBachelorResponse.getStatus());

    }

    @Test
    public void AdminLoginIncoreectTest() throws SQLException, ParseException {


        HttpServletRequest request = new HttpServletRequest() {
            @Override
            public String getAuthType() {
                return null;
            }

            @Override
            public Cookie[] getCookies() {
                return new Cookie[0];
            }

            @Override
            public long getDateHeader(String s) {
                return 0;
            }

            @Override
            public String getHeader(String s) {
                return null;
            }

            @Override
            public Enumeration getHeaders(String s) {
                return null;
            }

            @Override
            public Enumeration getHeaderNames() {
                return null;
            }

            @Override
            public int getIntHeader(String s) {
                return 0;
            }

            @Override
            public String getMethod() {
                return null;
            }

            @Override
            public String getPathInfo() {
                return null;
            }

            @Override
            public String getPathTranslated() {
                return null;
            }

            @Override
            public String getContextPath() {
                return null;
            }

            @Override
            public String getQueryString() {
                return null;
            }

            @Override
            public String getRemoteUser() {
                return null;
            }

            @Override
            public boolean isUserInRole(String s) {
                return false;
            }

            @Override
            public Principal getUserPrincipal() {
                return null;
            }

            @Override
            public String getRequestedSessionId() {
                return null;
            }

            @Override
            public String getRequestURI() {
                return null;
            }

            @Override
            public StringBuffer getRequestURL() {
                return null;
            }

            @Override
            public String getServletPath() {
                return null;
            }

            @Override
            public HttpSession getSession(boolean b) {
                return null;
            }

            @Override
            public HttpSession getSession() {
                return null;
            }

            @Override
            public boolean isRequestedSessionIdValid() {
                return false;
            }

            @Override
            public boolean isRequestedSessionIdFromCookie() {
                return false;
            }

            @Override
            public boolean isRequestedSessionIdFromURL() {
                return false;
            }

            @Override
            public boolean isRequestedSessionIdFromUrl() {
                return false;
            }

            @Override
            public Object getAttribute(String s) {
                return null;
            }

            @Override
            public Enumeration getAttributeNames() {
                return null;
            }

            @Override
            public String getCharacterEncoding() {
                return null;
            }

            @Override
            public void setCharacterEncoding(String s) throws UnsupportedEncodingException {

            }

            @Override
            public int getContentLength() {
                return 0;
            }

            @Override
            public String getContentType() {
                return null;
            }

            @Override
            public ServletInputStream getInputStream() throws IOException {
                return null;
            }

            @Override
            public String getParameter(String s) {
                return null;
            }

            @Override
            public Enumeration getParameterNames() {
                return null;
            }

            @Override
            public String[] getParameterValues(String s) {
                return new String[0];
            }

            @Override
            public Map getParameterMap() {
                return null;
            }

            @Override
            public String getProtocol() {
                return null;
            }

            @Override
            public String getScheme() {
                return null;
            }

            @Override
            public String getServerName() {
                return null;
            }

            @Override
            public int getServerPort() {
                return 0;
            }

            @Override
            public BufferedReader getReader() throws IOException {
                return null;
            }

            @Override
            public String getRemoteAddr() {
                return null;
            }

            @Override
            public String getRemoteHost() {
                return null;
            }

            @Override
            public void setAttribute(String s, Object o) {

            }

            @Override
            public void removeAttribute(String s) {

            }

            @Override
            public Locale getLocale() {
                return null;
            }

            @Override
            public Enumeration getLocales() {
                return null;
            }

            @Override
            public boolean isSecure() {
                return false;
            }

            @Override
            public RequestDispatcher getRequestDispatcher(String s) {
                return null;
            }

            @Override
            public String getRealPath(String s) {
                return null;
            }

            @Override
            public int getRemotePort() {
                return 0;
            }

            @Override
            public String getLocalName() {
                return null;
            }

            @Override
            public String getLocalAddr() {
                return null;
            }

            @Override
            public int getLocalPort() {
                return 0;
            }
        };

        LoginObject loginObject = new LoginObject("krishnakaranam3732@gmail.com","password");

        Response TopBachelorResponse;
        TopBachelorResponse = adminFacing.loginUser(request ,loginObject);
        Assert.assertEquals(200, TopBachelorResponse.getStatus());

    }

    @Test
    public void AdminLoginNullTest() throws SQLException, ParseException {


        HttpServletRequest request = new HttpServletRequest() {
            @Override
            public String getAuthType() {
                return null;
            }

            @Override
            public Cookie[] getCookies() {
                return new Cookie[0];
            }

            @Override
            public long getDateHeader(String s) {
                return 0;
            }

            @Override
            public String getHeader(String s) {
                return null;
            }

            @Override
            public Enumeration getHeaders(String s) {
                return null;
            }

            @Override
            public Enumeration getHeaderNames() {
                return null;
            }

            @Override
            public int getIntHeader(String s) {
                return 0;
            }

            @Override
            public String getMethod() {
                return null;
            }

            @Override
            public String getPathInfo() {
                return null;
            }

            @Override
            public String getPathTranslated() {
                return null;
            }

            @Override
            public String getContextPath() {
                return null;
            }

            @Override
            public String getQueryString() {
                return null;
            }

            @Override
            public String getRemoteUser() {
                return null;
            }

            @Override
            public boolean isUserInRole(String s) {
                return false;
            }

            @Override
            public Principal getUserPrincipal() {
                return null;
            }

            @Override
            public String getRequestedSessionId() {
                return null;
            }

            @Override
            public String getRequestURI() {
                return null;
            }

            @Override
            public StringBuffer getRequestURL() {
                return null;
            }

            @Override
            public String getServletPath() {
                return null;
            }

            @Override
            public HttpSession getSession(boolean b) {
                return null;
            }

            @Override
            public HttpSession getSession() {
                return null;
            }

            @Override
            public boolean isRequestedSessionIdValid() {
                return false;
            }

            @Override
            public boolean isRequestedSessionIdFromCookie() {
                return false;
            }

            @Override
            public boolean isRequestedSessionIdFromURL() {
                return false;
            }

            @Override
            public boolean isRequestedSessionIdFromUrl() {
                return false;
            }

            @Override
            public Object getAttribute(String s) {
                return null;
            }

            @Override
            public Enumeration getAttributeNames() {
                return null;
            }

            @Override
            public String getCharacterEncoding() {
                return null;
            }

            @Override
            public void setCharacterEncoding(String s) throws UnsupportedEncodingException {

            }

            @Override
            public int getContentLength() {
                return 0;
            }

            @Override
            public String getContentType() {
                return null;
            }

            @Override
            public ServletInputStream getInputStream() throws IOException {
                return null;
            }

            @Override
            public String getParameter(String s) {
                return null;
            }

            @Override
            public Enumeration getParameterNames() {
                return null;
            }

            @Override
            public String[] getParameterValues(String s) {
                return new String[0];
            }

            @Override
            public Map getParameterMap() {
                return null;
            }

            @Override
            public String getProtocol() {
                return null;
            }

            @Override
            public String getScheme() {
                return null;
            }

            @Override
            public String getServerName() {
                return null;
            }

            @Override
            public int getServerPort() {
                return 0;
            }

            @Override
            public BufferedReader getReader() throws IOException {
                return null;
            }

            @Override
            public String getRemoteAddr() {
                return null;
            }

            @Override
            public String getRemoteHost() {
                return null;
            }

            @Override
            public void setAttribute(String s, Object o) {

            }

            @Override
            public void removeAttribute(String s) {

            }

            @Override
            public Locale getLocale() {
                return null;
            }

            @Override
            public Enumeration getLocales() {
                return null;
            }

            @Override
            public boolean isSecure() {
                return false;
            }

            @Override
            public RequestDispatcher getRequestDispatcher(String s) {
                return null;
            }

            @Override
            public String getRealPath(String s) {
                return null;
            }

            @Override
            public int getRemotePort() {
                return 0;
            }

            @Override
            public String getLocalName() {
                return null;
            }

            @Override
            public String getLocalAddr() {
                return null;
            }

            @Override
            public int getLocalPort() {
                return 0;
            }
        };

        LoginObject loginObject = new LoginObject("null@gmail.com","password");

        Response TopBachelorResponse;
        TopBachelorResponse = adminFacing.loginUser(request ,loginObject);
        Assert.assertEquals(404, TopBachelorResponse.getStatus());

    }

    @Test
    public void AdminLoginNull2Test() throws SQLException, ParseException {

        Administrators administrators10 = new Administrators("290","null2@gmail.com",
                "fadmin3","madmin3","ladmi3");
        administratorsDao.addAdministrator(administrators10);

        adminLogins = new AdminLogins("null2@gmail.com",
                "pass",
                "key",
                Timestamp.valueOf("2017-09-23 10:10:10.0"),
                Timestamp.valueOf("2017-09-23 10:10:10.0"),
                false);
        adminLoginsDao.createAdminLogin(adminLogins);

        HttpServletRequest request = new HttpServletRequest() {
            @Override
            public String getAuthType() {
                return null;
            }

            @Override
            public Cookie[] getCookies() {
                return new Cookie[0];
            }

            @Override
            public long getDateHeader(String s) {
                return 0;
            }

            @Override
            public String getHeader(String s) {
                return null;
            }

            @Override
            public Enumeration getHeaders(String s) {
                return null;
            }

            @Override
            public Enumeration getHeaderNames() {
                return null;
            }

            @Override
            public int getIntHeader(String s) {
                return 0;
            }

            @Override
            public String getMethod() {
                return null;
            }

            @Override
            public String getPathInfo() {
                return null;
            }

            @Override
            public String getPathTranslated() {
                return null;
            }

            @Override
            public String getContextPath() {
                return null;
            }

            @Override
            public String getQueryString() {
                return null;
            }

            @Override
            public String getRemoteUser() {
                return null;
            }

            @Override
            public boolean isUserInRole(String s) {
                return false;
            }

            @Override
            public Principal getUserPrincipal() {
                return null;
            }

            @Override
            public String getRequestedSessionId() {
                return null;
            }

            @Override
            public String getRequestURI() {
                return null;
            }

            @Override
            public StringBuffer getRequestURL() {
                return null;
            }

            @Override
            public String getServletPath() {
                return null;
            }

            @Override
            public HttpSession getSession(boolean b) {
                return null;
            }

            @Override
            public HttpSession getSession() {
                return null;
            }

            @Override
            public boolean isRequestedSessionIdValid() {
                return false;
            }

            @Override
            public boolean isRequestedSessionIdFromCookie() {
                return false;
            }

            @Override
            public boolean isRequestedSessionIdFromURL() {
                return false;
            }

            @Override
            public boolean isRequestedSessionIdFromUrl() {
                return false;
            }

            @Override
            public Object getAttribute(String s) {
                return null;
            }

            @Override
            public Enumeration getAttributeNames() {
                return null;
            }

            @Override
            public String getCharacterEncoding() {
                return null;
            }

            @Override
            public void setCharacterEncoding(String s) throws UnsupportedEncodingException {

            }

            @Override
            public int getContentLength() {
                return 0;
            }

            @Override
            public String getContentType() {
                return null;
            }

            @Override
            public ServletInputStream getInputStream() throws IOException {
                return null;
            }

            @Override
            public String getParameter(String s) {
                return null;
            }

            @Override
            public Enumeration getParameterNames() {
                return null;
            }

            @Override
            public String[] getParameterValues(String s) {
                return new String[0];
            }

            @Override
            public Map getParameterMap() {
                return null;
            }

            @Override
            public String getProtocol() {
                return null;
            }

            @Override
            public String getScheme() {
                return null;
            }

            @Override
            public String getServerName() {
                return null;
            }

            @Override
            public int getServerPort() {
                return 0;
            }

            @Override
            public BufferedReader getReader() throws IOException {
                return null;
            }

            @Override
            public String getRemoteAddr() {
                return null;
            }

            @Override
            public String getRemoteHost() {
                return null;
            }

            @Override
            public void setAttribute(String s, Object o) {

            }

            @Override
            public void removeAttribute(String s) {

            }

            @Override
            public Locale getLocale() {
                return null;
            }

            @Override
            public Enumeration getLocales() {
                return null;
            }

            @Override
            public boolean isSecure() {
                return false;
            }

            @Override
            public RequestDispatcher getRequestDispatcher(String s) {
                return null;
            }

            @Override
            public String getRealPath(String s) {
                return null;
            }

            @Override
            public int getRemotePort() {
                return 0;
            }

            @Override
            public String getLocalName() {
                return null;
            }

            @Override
            public String getLocalAddr() {
                return null;
            }

            @Override
            public int getLocalPort() {
                return 0;
            }
        };

        LoginObject loginObject = new LoginObject("null2@gmail.com","pass");

        Response TopBachelorResponse;
        TopBachelorResponse = adminFacing.loginUser(request ,loginObject);
        Assert.assertEquals(401, TopBachelorResponse.getStatus());

        adminLoginsDao.deleteAdminLogin("null2@gmail.com");

        administratorsDao.deleteAdministrator(administrators10.getAdministratorNeuId());

    }

    /*
    AdminLogout
     */

    @Test
    public void AdminLogoutUnAuthorizeTest() throws SQLException, ParseException {

        Administrators administrators8 = new Administrators("198","notadminLogout@gmail.com",
                "fadmin3","madmin3","ladmi3");
        administratorsDao.addAdministrator(administrators8);

        adminLogins = new AdminLogins("notadminLogout@gmail.com",
                "password",
                "key",
                Timestamp.valueOf("2017-09-23 10:10:10.0"),
                Timestamp.valueOf("2017-09-23 10:10:10.0"),
                false);
        adminLoginsDao.createAdminLogin(adminLogins);

        HttpServletRequest request = new HttpServletRequest() {
            @Override
            public String getAuthType() {
                return null;
            }

            @Override
            public Cookie[] getCookies() {
                return new Cookie[0];
            }

            @Override
            public long getDateHeader(String s) {
                return 0;
            }

            @Override
            public String getHeader(String s) {
                return null;
            }

            @Override
            public Enumeration getHeaders(String s) {
                return null;
            }

            @Override
            public Enumeration getHeaderNames() {
                return null;
            }

            @Override
            public int getIntHeader(String s) {
                return 0;
            }

            @Override
            public String getMethod() {
                return null;
            }

            @Override
            public String getPathInfo() {
                return null;
            }

            @Override
            public String getPathTranslated() {
                return null;
            }

            @Override
            public String getContextPath() {
                return null;
            }

            @Override
            public String getQueryString() {
                return null;
            }

            @Override
            public String getRemoteUser() {
                return null;
            }

            @Override
            public boolean isUserInRole(String s) {
                return false;
            }

            @Override
            public Principal getUserPrincipal() {
                return null;
            }

            @Override
            public String getRequestedSessionId() {
                return null;
            }

            @Override
            public String getRequestURI() {
                return null;
            }

            @Override
            public StringBuffer getRequestURL() {
                return null;
            }

            @Override
            public String getServletPath() {
                return null;
            }

            @Override
            public HttpSession getSession(boolean b) {
                return null;
            }

            @Override
            public HttpSession getSession() {
                return null;
            }

            @Override
            public boolean isRequestedSessionIdValid() {
                return false;
            }

            @Override
            public boolean isRequestedSessionIdFromCookie() {
                return false;
            }

            @Override
            public boolean isRequestedSessionIdFromURL() {
                return false;
            }

            @Override
            public boolean isRequestedSessionIdFromUrl() {
                return false;
            }

            @Override
            public Object getAttribute(String s) {
                return null;
            }

            @Override
            public Enumeration getAttributeNames() {
                return null;
            }

            @Override
            public String getCharacterEncoding() {
                return null;
            }

            @Override
            public void setCharacterEncoding(String s) throws UnsupportedEncodingException {

            }

            @Override
            public int getContentLength() {
                return 0;
            }

            @Override
            public String getContentType() {
                return null;
            }

            @Override
            public ServletInputStream getInputStream() throws IOException {
                return null;
            }

            @Override
            public String getParameter(String s) {
                return null;
            }

            @Override
            public Enumeration getParameterNames() {
                return null;
            }

            @Override
            public String[] getParameterValues(String s) {
                return new String[0];
            }

            @Override
            public Map getParameterMap() {
                return null;
            }

            @Override
            public String getProtocol() {
                return null;
            }

            @Override
            public String getScheme() {
                return null;
            }

            @Override
            public String getServerName() {
                return null;
            }

            @Override
            public int getServerPort() {
                return 0;
            }

            @Override
            public BufferedReader getReader() throws IOException {
                return null;
            }

            @Override
            public String getRemoteAddr() {
                return null;
            }

            @Override
            public String getRemoteHost() {
                return null;
            }

            @Override
            public void setAttribute(String s, Object o) {

            }

            @Override
            public void removeAttribute(String s) {

            }

            @Override
            public Locale getLocale() {
                return null;
            }

            @Override
            public Enumeration getLocales() {
                return null;
            }

            @Override
            public boolean isSecure() {
                return false;
            }

            @Override
            public RequestDispatcher getRequestDispatcher(String s) {
                return null;
            }

            @Override
            public String getRealPath(String s) {
                return null;
            }

            @Override
            public int getRemotePort() {
                return 0;
            }

            @Override
            public String getLocalName() {
                return null;
            }

            @Override
            public String getLocalAddr() {
                return null;
            }

            @Override
            public int getLocalPort() {
                return 0;
            }
        };


        LoginObject loginObject = new LoginObject("notadminLogout@gmail.com","Unpassword");

        Response TopBachelorResponse;
        TopBachelorResponse = adminFacing.logoutUser(request ,loginObject);
        Assert.assertEquals(200, TopBachelorResponse.getStatus());

        adminLoginsDao.deleteAdminLogin("notadminLogout@gmail.com");

        administratorsDao.deleteAdministrator(administrators8.getAdministratorNeuId());
    }


    @Test
    public void AdminLogoutTest() throws SQLException, ParseException {

        Administrators administrators8 = new Administrators("190","adminLogout@gmail.com",
                "fadmin3","madmin3","ladmi3");
        administratorsDao.addAdministrator(administrators8);

        adminLogins = new AdminLogins("adminLogout@gmail.com",
                "password",
                "key",
                Timestamp.valueOf("2017-09-23 10:10:10.0"),
                Timestamp.valueOf("2019-09-23 10:10:10.0"),
                false);
        adminLoginsDao.createAdminLogin(adminLogins);

        HttpServletRequest request = new HttpServletRequest() {
            @Override
            public String getAuthType() {
                return null;
            }

            @Override
            public Cookie[] getCookies() {
                return new Cookie[0];
            }

            @Override
            public long getDateHeader(String s) {
                return 0;
            }

            @Override
            public String getHeader(String s) {
                return null;
            }

            @Override
            public Enumeration getHeaders(String s) {
                return null;
            }

            @Override
            public Enumeration getHeaderNames() {
                return null;
            }

            @Override
            public int getIntHeader(String s) {
                return 0;
            }

            @Override
            public String getMethod() {
                return null;
            }

            @Override
            public String getPathInfo() {
                return null;
            }

            @Override
            public String getPathTranslated() {
                return null;
            }

            @Override
            public String getContextPath() {
                return null;
            }

            @Override
            public String getQueryString() {
                return null;
            }

            @Override
            public String getRemoteUser() {
                return null;
            }

            @Override
            public boolean isUserInRole(String s) {
                return false;
            }

            @Override
            public Principal getUserPrincipal() {
                return null;
            }

            @Override
            public String getRequestedSessionId() {
                return null;
            }

            @Override
            public String getRequestURI() {
                return null;
            }

            @Override
            public StringBuffer getRequestURL() {
                return null;
            }

            @Override
            public String getServletPath() {
                return null;
            }

            @Override
            public HttpSession getSession(boolean b) {
                return null;
            }

            @Override
            public HttpSession getSession() {
                return null;
            }

            @Override
            public boolean isRequestedSessionIdValid() {
                return false;
            }

            @Override
            public boolean isRequestedSessionIdFromCookie() {
                return false;
            }

            @Override
            public boolean isRequestedSessionIdFromURL() {
                return false;
            }

            @Override
            public boolean isRequestedSessionIdFromUrl() {
                return false;
            }

            @Override
            public Object getAttribute(String s) {
                return null;
            }

            @Override
            public Enumeration getAttributeNames() {
                return null;
            }

            @Override
            public String getCharacterEncoding() {
                return null;
            }

            @Override
            public void setCharacterEncoding(String s) throws UnsupportedEncodingException {

            }

            @Override
            public int getContentLength() {
                return 0;
            }

            @Override
            public String getContentType() {
                return null;
            }

            @Override
            public ServletInputStream getInputStream() throws IOException {
                return null;
            }

            @Override
            public String getParameter(String s) {
                return null;
            }

            @Override
            public Enumeration getParameterNames() {
                return null;
            }

            @Override
            public String[] getParameterValues(String s) {
                return new String[0];
            }

            @Override
            public Map getParameterMap() {
                return null;
            }

            @Override
            public String getProtocol() {
                return null;
            }

            @Override
            public String getScheme() {
                return null;
            }

            @Override
            public String getServerName() {
                return null;
            }

            @Override
            public int getServerPort() {
                return 0;
            }

            @Override
            public BufferedReader getReader() throws IOException {
                return null;
            }

            @Override
            public String getRemoteAddr() {
                return null;
            }

            @Override
            public String getRemoteHost() {
                return null;
            }

            @Override
            public void setAttribute(String s, Object o) {

            }

            @Override
            public void removeAttribute(String s) {

            }

            @Override
            public Locale getLocale() {
                return null;
            }

            @Override
            public Enumeration getLocales() {
                return null;
            }

            @Override
            public boolean isSecure() {
                return false;
            }

            @Override
            public RequestDispatcher getRequestDispatcher(String s) {
                return null;
            }

            @Override
            public String getRealPath(String s) {
                return null;
            }

            @Override
            public int getRemotePort() {
                return 0;
            }

            @Override
            public String getLocalName() {
                return null;
            }

            @Override
            public String getLocalAddr() {
                return null;
            }

            @Override
            public int getLocalPort() {
                return 0;
            }
        };


        LoginObject loginObject = new LoginObject("adminLogout@gmail.com","password");

        Response TopBachelorResponse;
        TopBachelorResponse = adminFacing.logoutUser(request ,loginObject);
        Assert.assertEquals(200, TopBachelorResponse.getStatus());

        adminLoginsDao.deleteAdminLogin("adminLogout@gmail.com");

        administratorsDao.deleteAdministrator(administrators8.getAdministratorNeuId());
    }


    /*
    changePassword
     */


    @Test
    public void ChangePasswordTest() throws SQLException, ParseException {

        PasswordChangeObject passwordChangeObject = new PasswordChangeObject("krishnakaranam3732@gmail.com",
                "password","newpassword");

        Response TopBachelorResponse;
        TopBachelorResponse = adminFacing.changeUserPassword(passwordChangeObject);
        Assert.assertEquals(200, TopBachelorResponse.getStatus());

    }


    @Test
    public void ChangePasswordBadTest() throws SQLException, ParseException {

        PasswordChangeObject passwordChangeObject = new PasswordChangeObject("krishnakaranam3732@gmail.com",
                "$s0$41010$cwF4TDlHcEf5+zxUKgsA3w==$vlMxt0lC641Vdavp9nclzELFgS3YgkuG9GBTgeUKfwQ=","newpassword");

        Response TopBachelorResponse;
        TopBachelorResponse = adminFacing.changeUserPassword(passwordChangeObject);
        Assert.assertEquals(400, TopBachelorResponse.getStatus());

    }

    /*
    sendEmail
     */

//    @Test
//    public void emailForPasswordResetTest() throws SQLException, ParseException {
//
//        PasswordResetObject passwordResetObject = new PasswordResetObject("krishnakaranam3732@gmail.com");
//
//        Response TopBachelorResponse;
//        TopBachelorResponse = adminFacing.sendEmailForPasswordResetAdmin(passwordResetObject);
//        Assert.assertEquals(200, TopBachelorResponse.getStatus());
//
//    }

    @Test
    public void emailForPasswordResetNullTest() throws SQLException, ParseException {

        PasswordResetObject passwordResetObject = new PasswordResetObject(null);

        Response TopBachelorResponse;
        TopBachelorResponse = adminFacing.sendEmailForPasswordResetAdmin(passwordResetObject);
        Assert.assertEquals(400, TopBachelorResponse.getStatus());

    }

    @Test
    public void emailForPasswordResetNotExistTest() throws SQLException, ParseException {

        PasswordResetObject passwordResetObject = new PasswordResetObject("doesnotexist.com");

        Response TopBachelorResponse;
        TopBachelorResponse = adminFacing.sendEmailForPasswordResetAdmin(passwordResetObject);
        Assert.assertEquals(404, TopBachelorResponse.getStatus());

    }

    @Test
    public void emailForPasswordResetFalseTest() throws SQLException, ParseException {

        Administrators administratorsAdmin = new Administrators("130","t@gmail.com",
                "fadmin3","madmin3","ladmi3");
        administratorsDao.addAdministrator(administratorsAdmin);

        adminLogins = new AdminLogins("t@gmail.com",
                "123",
                "key",
                Timestamp.valueOf("2017-09-23 10:10:10.0"),
                Timestamp.valueOf("2019-09-23 10:10:10.0"),
                false);
        adminLoginsDao.createAdminLogin(adminLogins);

        PasswordResetObject passwordResetObject = new PasswordResetObject("t@gmail.com");

        Response TopBachelorResponse;
        TopBachelorResponse = adminFacing.sendEmailForPasswordResetAdmin(passwordResetObject);
        Assert.assertEquals(404, TopBachelorResponse.getStatus());

        adminLoginsDao.deleteAdminLogin("t@gmail.com");

        administratorsDao.deleteAdministrator(administratorsAdmin.getAdministratorNeuId());
    }

    /*
    AutoFill
     */

    @Test
    public void AutoFillTest() throws SQLException, ParseException {
        Response TopBachelorResponse;
        TopBachelorResponse = adminFacing.getAutoFillSearch("Tom Cat 0012345671 tomcat@gmail.com");
        Assert.assertEquals(200, TopBachelorResponse.getStatus());
    }

    @Test
    public void AutoFillTest3() throws SQLException, ParseException {
        Response TopBachelorResponse;
        TopBachelorResponse = adminFacing.getAutoFillSearch("Tom Cat");
        Assert.assertEquals(200, TopBachelorResponse.getStatus());
    }

    @Test
    public void AutoFillTest2() throws SQLException, ParseException {

        Students TestStudent = new Students("020", "test@gmail.com", "test", "test",
                "test", Gender.M, "F1", "1111111111",
                "401 Terry Ave", "WA", "Seattle", "98109", Term.FALL, 2015,
                Term.SPRING, 2017,
                EnrollmentStatus.FULL_TIME, Campus.SEATTLE, DegreeCandidacy.MASTERS, null, true);
        studentsDao.addStudent(TestStudent);
        Response TopBachelorResponse;
        TopBachelorResponse = adminFacing.getAutoFillSearch("test test test 020 test@gmail.com");
        Assert.assertEquals(200, TopBachelorResponse.getStatus());

        studentsDao.deleteStudent(TestStudent.getNeuId());
    }



}

