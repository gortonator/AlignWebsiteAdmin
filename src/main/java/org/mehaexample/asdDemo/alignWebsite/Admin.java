package org.mehaexample.asdDemo.alignWebsite;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jose4j.jwe.ContentEncryptionAlgorithmIdentifiers;
import org.jose4j.jwe.JsonWebEncryption;
import org.jose4j.jwe.KeyManagementAlgorithmIdentifiers;
import org.jose4j.keys.AesKey;
import org.json.JSONObject;
import org.json.JSONArray;
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
import org.mehaexample.asdDemo.model.alignadmin.AdminLogins;
import org.mehaexample.asdDemo.model.alignadmin.AdministratorNotes;
import org.mehaexample.asdDemo.model.alignadmin.Administrators;
import org.mehaexample.asdDemo.model.alignadmin.ElectivesAdmin;
import org.mehaexample.asdDemo.model.alignadmin.GenderRatio;
import org.mehaexample.asdDemo.model.alignadmin.LoginObject;
import org.mehaexample.asdDemo.model.alignadmin.SearchObject;
import org.mehaexample.asdDemo.model.alignadmin.StudentBachelorInstitution;
import org.mehaexample.asdDemo.model.alignadmin.TopBachelor;
import org.mehaexample.asdDemo.model.alignadmin.TopElective;
import org.mehaexample.asdDemo.model.alignadmin.TopEmployer;
import org.mehaexample.asdDemo.model.alignadmin.ParamsObject;
import org.mehaexample.asdDemo.model.alignprivate.ExtraExperiences;
import org.mehaexample.asdDemo.model.alignprivate.StudentBasicInfo;
import org.mehaexample.asdDemo.model.alignprivate.StudentCoopList;
import org.mehaexample.asdDemo.model.alignprivate.StudentLogins;
import org.mehaexample.asdDemo.model.alignprivate.Students;
import org.mehaexample.asdDemo.model.alignprivate.WorkExperiences;
import org.mehaexample.asdDemo.restModels.PasswordChangeObject;
import org.mehaexample.asdDemo.restModels.PasswordCreateObject;
import org.mehaexample.asdDemo.restModels.PasswordResetObject;
import org.mehaexample.asdDemo.utils.MailClient;

import com.lambdaworks.crypto.SCryptUtil;


@Path("")
public class Admin{

	//importing DAO methods
	StudentsDao studentDao = new StudentsDao();
	ElectivesAdminDao electiveDao = new ElectivesAdminDao();
	GenderRatioDao genderRatioDao = new GenderRatioDao();
	WorkExperiencesDao workExperiencesDao = new WorkExperiencesDao();
	PriorEducationsDao priorEducationsDao = new PriorEducationsDao();
	ElectivesDao electivesDao = new ElectivesDao();
	AdminLoginsDao adminLoginsDao = new AdminLoginsDao();
	ExtraExperiencesDao extraExperiencesDao = new ExtraExperiencesDao();
	AdministratorsDao administratorsDao = new AdministratorsDao();
	AdministratorNotesDao administratorNotesDao = new AdministratorNotesDao();
	StudentLogins studentLogins = new StudentLogins();

	/**
	 * Request 1
	 * This is the function to search for students
	 *	
	 *	http://localhost:8080/webapi/students
	 * @param firstname
	 * @return the list of student profiles matching the search fields. 200 OK else 500
	 */
	@POST
	@Path("students")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchStudent(SearchObject input){
		Map<String,List<String>> map = new HashMap<String,List<String>>();
		ArrayList<Students> studentRecords;
		JSONArray resultArray = new JSONArray();
		JSONObject finalResult = new JSONObject();
		int total = -1;
		int begin = 1;
		int end = 20;
		if (input.getFirstname()!=null){
			ArrayList<String> firstnameList = new ArrayList<String>();
			firstnameList.add(input.getFirstname());
			map.put("firstName",firstnameList);
		}
		if (input.getLastname()!=null){
			ArrayList<String> lastnameList = new ArrayList<String>();
			lastnameList.add(input.getLastname());
			map.put("lastName",lastnameList);
		}
		if (input.getEmail()!=null){
			ArrayList<String> emailList = new ArrayList<String>();
			emailList.add(input.getEmail());
			map.put("email",emailList);
		}
		if (input.getDegreeyear()!=null){
			ArrayList<String> degreeyearList = new ArrayList<String>();
			degreeyearList.add(input.getDegreeyear());
			map.put("expectedLastYear",degreeyearList);
		}
		if (input.getEnrollmentstatus()!=null){
			if(input.getEnrollmentstatus().size() > 0)
				map.put("enrollmentStatus",input.getEnrollmentstatus());
		}
		if (input.getCampus()!=null){
			if(input.getCampus().size() > 0)
				map.put("campus",input.getCampus());
		}
		if (input.getCompany()!=null){
			ArrayList<String> companyList = new ArrayList<String>();
			companyList.add(input.getCompany());
			map.put("companyName",companyList);
		}
		if (input.getNeuid()!=null){
			ArrayList<String> neuIdList = new ArrayList<String>();
			neuIdList.add(input.getNeuid());
			map.put("neuId",neuIdList);
		}
		if (input.getUndergradmajor()!=null){
			ArrayList<String> undergradmajor = new ArrayList<String>();
			undergradmajor.add(input.getUndergradmajor());
			map.put("majorName",undergradmajor);
		}
		if (input.getNuundergrad()!=null){
			ArrayList<String> nuundergrad = new ArrayList<String>();
			ArrayList<String> degree = new ArrayList<String>();
			if(input.getNuundergrad().equalsIgnoreCase("true")){
				nuundergrad.add("Northeastern University");
				degree.add("BACHELORS");
				map.put("institutionName",nuundergrad);
				map.put("degreeCandidacy",degree);
			}
		}
		if (input.getCoop()!=null){
			ArrayList<String> coop = new ArrayList<String>();
			coop.add(input.getCoop());
			map.put("companyName",coop);
		}
		if (input.getGender()!=null){
			ArrayList<String> gender = new ArrayList<String>();
			if(input.getGender().equalsIgnoreCase("male")){
				gender.add("M");
			}else if(input.getGender().equalsIgnoreCase("female")){
				gender.add("F");
			}else {
				gender.add(input.getGender());
			}
			map.put("gender",gender);
		}
		if (input.getRace()!=null){
			ArrayList<String> race = new ArrayList<String>();
			race.add(input.getRace());
			map.put("race",race);
		}
		if (input.getBeginindex()!=null){
			begin = Integer.valueOf(input.getBeginindex());
		}
		if (input.getEndindex()!=null){
			end = Integer.valueOf(input.getEndindex());
		}
		try{
		studentRecords = (ArrayList<Students>) studentDao.getAdminFilteredStudents(map, begin, end);
		total = studentDao.getAdminFilteredStudentsCount(map);
		
		for(Students st : studentRecords) {
			JSONObject studentJson = new JSONObject();
			JSONObject eachStudentJson = new JSONObject(st);
			java.util.Set<String> keys = eachStudentJson.keySet();
			for(int i=0;i<keys.toArray().length; i++){
				studentJson.put(((String) keys.toArray()[i]).toLowerCase(), eachStudentJson.get((String) keys.toArray()[i]));
			}
			studentJson.put("notes",administratorNotesDao.getAdministratorNoteRecordByNeuId(studentJson.get("neuid").toString()));
			resultArray.put(studentJson);
		}
		finalResult.put("students", resultArray);
		finalResult.put("beginindex", begin);
		finalResult.put("endindex", end);
		finalResult.put("totalcount", total);
		
		}
		catch(Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
		}
		return Response.status(Response.Status.OK).entity(finalResult.toString()).build();
	}

	/**
	 * Request 2
	 * This is the function to retrieve a student details based on nuid.
	 *	
	 *	http://localhost:8080/webapi/students/090
	 * @param nuid
	 * @return the student details matching the nuid. 200 OK else 404
	 */
	@GET
	@Path("students/{nuid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getStudentProfile(@PathParam("nuid") String nuid){
		if(!studentDao.ifNuidExists(nuid)){
			return Response.status(Response.Status.NOT_FOUND).entity("No Student record exists with given ID").build();
		} else {
			Students studentRecord = studentDao.getStudentRecord(nuid);
			JSONObject jsonObj = new JSONObject(studentRecord);
			ArrayList<WorkExperiences> workEx = (ArrayList<WorkExperiences>) workExperiencesDao.getWorkExperiencesByNeuId(nuid);
			jsonObj.put("company", workEx);
			ArrayList<ElectivesAdmin> electives = (ArrayList<ElectivesAdmin>) electiveDao.getElectivesByNeuId(nuid);
			jsonObj.put("courses", electives);
			List<ExtraExperiences> coop = extraExperiencesDao.getExtraExperiencesByNeuId(nuid);
			jsonObj.put("coopexperience", coop);
			jsonObj.put("notes",administratorNotesDao.getAdministratorNoteRecordByNeuId(nuid));
			return Response.status(Response.Status.OK).entity(jsonObj.toString()).build();
		}
	}

	/**
	 * Request 3
	 * This is the function to get the gender ratio counts per year.
	 *	
	 *	http://localhost:8080/webapi/analytics/gender-ratio
	 * @param 
	 * @return the gender ratio is returned as a list of years with counts 200 OK else 400
	 * @throws SQLException 
	 */
	@POST
	@Path("analytics/gender-ratio")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getGenderRatio(ParamsObject input){
		List<GenderRatio> ratio;
		JSONArray result = new JSONArray();
		if (input.getCampus()!=null){
			try{
				ratio = genderRatioDao.getYearlyGenderRatio(Campus.valueOf(input.getCampus().toUpperCase()));
				
				for(int i=0; i<ratio.size();i++){
					JSONObject jsonObj = new JSONObject();
					jsonObj.put("male", ratio.get(i).getMale());
					jsonObj.put("female", ratio.get(i).getFemale());
					jsonObj.put("year", ratio.get(i).getEntryYear());
					result.put(jsonObj);
	    		}
			} catch(Exception e){
				return Response.status(Response.Status.BAD_REQUEST).entity(e).build();
			}
		} else {
			return Response.status(Response.Status.BAD_REQUEST).entity("campus field cannot be null").build();
		}
		return Response.status(Response.Status.OK).entity(result.toString()).build();
	}


	/**
	 * Request 4
	 * This is the function to get the list of top 10 bachelor degrees.
	 *	
	 *	http://localhost:8080/webapi/analytics/top-bachelor-degrees
	 * @param 
	 * @return the list of top 10 bachelor degrees and number of students 200 OK else 400
	 * @throws SQLException 
	 * 
	 */
	@POST
	@Path("analytics/top-bachelor-degrees")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTopBachelorDegree(ParamsObject input) throws SQLException{
		List<TopBachelor> degrees = new ArrayList<TopBachelor>();
		JSONArray result = new JSONArray();
		if (input.getCampus()!=null && input.getYear()!=null){
			try{
				degrees = priorEducationsDao.getTopTenBachelors(Campus.valueOf(input.getCampus().toUpperCase()),Integer.valueOf(input.getYear()));
			} catch(Exception e){
				return Response.status(Response.Status.BAD_REQUEST).entity(e).build();
			}
		} else if (input.getCampus()!=null && input.getYear()==null){
			try{
				degrees = priorEducationsDao.getTopTenBachelors(Campus.valueOf(input.getCampus().toUpperCase()),null);
			} catch(Exception e){
				return Response.status(Response.Status.BAD_REQUEST).entity(e).build();
			}
		} else if (input.getCampus()==null && input.getYear()!=null){
			try{
				degrees = priorEducationsDao.getTopTenBachelors(null,Integer.valueOf(input.getYear()));
			} catch(Exception e){
				return Response.status(Response.Status.BAD_REQUEST).entity(e).build();
			}
		} else if (input.getCampus()==null && input.getYear()==null){
			try{
				degrees = priorEducationsDao.getTopTenBachelors(null,null);
			} catch(Exception e){
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
			}
		}
		
		for(int i=0; i<degrees.size();i++){
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("degree", degrees.get(i).getDegree());
			jsonObj.put("students", degrees.get(i).getTotalStudents());
			result.put(jsonObj);
		}
		
		return Response.status(Response.Status.OK).entity(result.toString()).build();
	}

	/**
	 * Request 5
	 * This is the function to get a list of the top 10 employers.
	 *	
	 *	http://localhost:8080/webapi/analytics/top-employers
	 * @param 
	 * @return the list of top 10 employers and number of students 200 OK else 400
	 * @throws SQLException 
	 * 
	 */
	@POST
	@Path("analytics/top-employers")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTopEmployers(ParamsObject input){
		List<TopEmployer> employers = new ArrayList<TopEmployer>();
		JSONArray result = new JSONArray();
		if (input.getCampus()!=null && input.getYear()!=null){
			try{
				employers = workExperiencesDao.getTopTenEmployers(Campus.valueOf(input.getCampus().toUpperCase()),Integer.valueOf(input.getYear()));
			} catch(Exception e){
				return Response.status(Response.Status.BAD_REQUEST).entity(e).build();
			}
		} else if (input.getCampus()!=null && input.getYear()==null){
			try{
				employers = workExperiencesDao.getTopTenEmployers(Campus.valueOf(input.getCampus().toUpperCase()),null);
			} catch(Exception e){
				return Response.status(Response.Status.BAD_REQUEST).entity(e).build();
			}
		}else if (input.getCampus()==null && input.getYear()!=null){
			try{
				employers = workExperiencesDao.getTopTenEmployers(null,Integer.valueOf(input.getYear()));
			} catch(Exception e){
				return Response.status(Response.Status.BAD_REQUEST).entity(e).build();
			}
		} else if (input.getCampus()==null && input.getYear()==null){
			try{
				employers = workExperiencesDao.getTopTenEmployers(null,null);
			} catch(Exception e){
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
			}
		} 
		
		for(int i=0; i<employers.size();i++){
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("name", employers.get(i).getCompanyName());
			jsonObj.put("students", employers.get(i).getTotalStudents());
			result.put(jsonObj);
		}
		
		return Response.status(Response.Status.OK).entity(result.toString()).build();
	}

	/**
	 * Request 6
	 * This is the function to get a list of the top 10 electives.
	 *	
	 *	http://localhost:8080/webapi/aanalytics/top-electives
	 * @param 
	 * @return the list of top 10 electives and number of students 200 OK else 400
	 * @throws SQLException 
	 * 
	 */
	@POST
	@Path("analytics/top-electives")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTopElectives(ParamsObject input){
		List<TopElective> electives = new ArrayList<TopElective>();
		JSONArray result = new JSONArray();
		if (input.getCampus()!=null && input.getYear()!=null){
			try{
				electives = electivesDao.getTopTenElectives(Campus.valueOf(input.getCampus().toUpperCase()),Integer.valueOf(input.getYear()));
			} catch(Exception e){
				return Response.status(Response.Status.BAD_REQUEST).entity(e).build();
			}
		} else if (input.getCampus()!=null && input.getYear()==null){
			try{
				electives = electivesDao.getTopTenElectives(Campus.valueOf(input.getCampus().toUpperCase()),null);
			} catch(Exception e){
				return Response.status(Response.Status.BAD_REQUEST).entity(e).build();
			}
		} else if (input.getCampus()==null && input.getYear()!=null){
			try{
				electives = electivesDao.getTopTenElectives(null,Integer.valueOf(input.getYear()));
			} catch(Exception e){
				return Response.status(Response.Status.BAD_REQUEST).entity(e).build();
			}
		} else if (input.getCampus()==null && input.getYear()==null){
			try{
				electives = electivesDao.getTopTenElectives(null,null);
			} catch(Exception e){
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
			}
		}
		
		for(int i=0; i<electives.size();i++){
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("elective", electives.get(i).getCourseName());
			jsonObj.put("students", electives.get(i).getTotalStudents());
			result.put(jsonObj);
		}
		
		return Response.status(Response.Status.OK).entity(result.toString()).build();
	}

	/**
	 * Request 7
	 * This is the function to get the list of students,companies worked for as coop.
	 *	
	 *	http://localhost:8080/webapi/analytics/coop-students
	 * @param 
	 * @return the list student details , companies they worked for as coop 200 OK else 400
	 * @throws SQLException 
	 * 
	 */
	@POST
	@Path("analytics/coop-students")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCoopStudents(ParamsObject input){
		List<StudentCoopList> coopStudentsList = new ArrayList<StudentCoopList>();
		JSONArray result = new JSONArray();
		if (input.getCampus()!=null && input.getYear()!=null){
			try{
				coopStudentsList = workExperiencesDao.getStudentCoopCompanies(Campus.valueOf(input.getCampus().toUpperCase()),Integer.valueOf(input.getYear()));
			} catch(Exception e){
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
			}
		} else if (input.getCampus()!=null && input.getYear()==null){
			try{
				coopStudentsList = workExperiencesDao.getStudentCoopCompanies(Campus.valueOf(input.getCampus().toUpperCase()),null);
			} catch(Exception e){
				return Response.status(Response.Status.BAD_REQUEST).entity(e).build();
			}
		} else if (input.getCampus()==null){
			return Response.status(Response.Status.BAD_REQUEST).entity("Campus cannot be null.").build();
		}

		for(int i=0; i<coopStudentsList.size();i++){
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("nuid", coopStudentsList.get(i).getNeuId());
			jsonObj.put("companies", coopStudentsList.get(i).getCompanies());
			jsonObj.put("name", coopStudentsList.get(i).getFirstName()+" "+coopStudentsList.get(i).getLastName());
			result.put(jsonObj);
		}
		
		return Response.status(Response.Status.OK).entity(result.toString()).build();
	}

	/**
	 * Request 8 
	 * This is a function for retrieving the students working in a given company
	 * 
	 * http://localhost:8080/webapi/analytics/company
	 * @param params
	 * @return the list student details working for a company 200 OK else 400
	 */
	@POST
	@Path("/analytics/company")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getStudentsWorkingForACompany(ParamsObject input){
		List<StudentBasicInfo> studentsList = new ArrayList<StudentBasicInfo>();
		JSONArray result = new JSONArray();
		if (input.getCampus()!=null && input.getCompany()!=null && input.getYear()!=null){
			try{
				studentsList = workExperiencesDao.getStudentsWorkingInACompany(Campus.valueOf(input.getCampus().toUpperCase()),Integer.valueOf(input.getYear()),input.getCompany());
			} catch(Exception e){
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
			}
		} else if (input.getCampus()!=null && input.getCompany()!=null && input.getYear()==null){
			try{
				studentsList = workExperiencesDao.getStudentsWorkingInACompany(Campus.valueOf(input.getCampus().toUpperCase()),null,input.getCompany());
			} catch(Exception e){
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
			}
		} else if (input.getCampus()==null || input.getCompany()==null){
			return Response.status(Response.Status.BAD_REQUEST).entity("Campus and Company cannot be null.").build();
		}

		for(int i=0; i<studentsList.size();i++){
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("nuid", studentsList.get(i).getNeuId());
			jsonObj.put("name", studentsList.get(i).getFirstName()+" "+studentsList.get(i).getLastName());
			result.put(jsonObj);
		}
		
		return Response.status(Response.Status.OK).entity(result.toString()).build(); 
	}

	/**
	 * Request 9
	 * This is a function for retrieving the students working as full time
	 * 
	 * http://localhost:8080/webapi/analytics/working
	 * @param params
	 * @return the list student details and company they are working for. 200 OK else 400
	 */
	@POST
	@Path("/analytics/working")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getStudentWorkingFullTime(ParamsObject input){
		List<StudentCoopList> studentsList = new ArrayList<StudentCoopList>();
		JSONArray result = new JSONArray();
		if (input.getCampus()!=null && input.getYear()!=null){
			try{
				studentsList = workExperiencesDao.
						getStudentCurrentCompanies(Campus.valueOf(input.getCampus().toUpperCase()),Integer.valueOf(input.getYear()));
			} catch(Exception e){
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
			}
		} else if (input.getCampus()!=null && input.getYear()==null){
			try{
				studentsList = workExperiencesDao.
						getStudentCurrentCompanies(Campus.valueOf(input.getCampus().toUpperCase()),null);
			} catch(Exception e){
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
			}
		} else if (input.getCampus()==null){
			return Response.status(Response.Status.BAD_REQUEST).entity("Campus cannot be null.").build();
		}

		for(int i=0; i<studentsList.size();i++){
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("nuid", studentsList.get(i).getNeuId());
			jsonObj.put("company", studentsList.get(i).getCompanies().get(0));
			jsonObj.put("name", studentsList.get(i).getFirstName()+" "+studentsList.get(i).getLastName());
			result.put(jsonObj);
		}
		
		return Response.status(Response.Status.OK).entity(result.toString()).build();   
	}
	
	/**
	 * Request 10
	 * This is a function for retrieving the undergrad institutions of all the students
	 * 
	 * http://localhost:8080/webapi/analytics/undergrad-institutions
	 * @param params
	 * @return the list of undergrad institution and count 200 OK else 400
	 */
	@POST
	@Path("/analytics/undergrad-institutions")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getStudentundergradInstitutuins(ParamsObject input){
		List<StudentBachelorInstitution> instList = new ArrayList<StudentBachelorInstitution>();
		JSONArray result = new JSONArray();
		if (input.getCampus()!=null && input.getYear()!=null){
			try{
				instList = priorEducationsDao.
						getListOfBachelorInstitutions(Campus.valueOf(input.getCampus().toUpperCase()),Integer.valueOf(input.getYear()));
			} catch(Exception e){
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
			}
		} else if (input.getCampus()!=null && input.getYear()==null){
			try{
				instList = priorEducationsDao.
						getListOfBachelorInstitutions(Campus.valueOf(input.getCampus().toUpperCase()),null);
			} catch(Exception e){
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
			}
		} else if (input.getCampus()==null){
			return Response.status(Response.Status.BAD_REQUEST).entity("Campus cannot be null.").build();
		}

		for(int i=0; i<instList.size();i++){
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("count", instList.get(i).getTotalStudents());
			jsonObj.put("name", instList.get(i).getInstitutionName());
			result.put(jsonObj);
		}
		
		return Response.status(Response.Status.OK).entity(result.toString()).build();   
	}
	
	
	/**
	 * Request 11
	 * This is a function to update an existing student notes
	 * 
	 * http://localhost:8080/webapi/notes/{noteid}
	 * @param AdministratorNotes
	 * @return 200 if notes updated successfully else return 404, 500
	 */
	@PUT
	@Path("/notes/{noteid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateNote(AdministratorNotes input,@PathParam("noteid") String noteid){
		try{
			
			AdministratorNotes existingNote = administratorNotesDao.getAdministratorNoteById(Integer.parseInt(noteid));
			if(input.getAdministratorNeuId() == null){
				input.setAdministratorNeuId(existingNote.getAdministratorNeuId());
			}
			if(input.getNeuId() == null){
				input.setNeuId(existingNote.getNeuId());
			}
			if(input.getTitle() == null){
				input.setTitle(existingNote.getTitle());
			}
			if(input.getDesc() == null){
				input.setDesc(existingNote.getDesc());
			}
			input.setAdministratorNoteId(Integer.parseInt(noteid));
			if(administratorNotesDao.updateAdministratorNote(input)){
				return Response.status(Response.Status.OK).
						entity("note updated successfully").build();
			}
			} catch (Exception e){
				return Response.status(Response.Status.NOT_FOUND).entity("Please check the note id").build();
			}
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).
				entity("note updation failed").build();
	}
	
	/**
	 * Request 12
	 * This is a function to create a student notes
	 * 
	 * http://localhost:8080/webapi/{adminneuid}/notes
	 * @param AdministratorNotes
	 * @return 200 if notes created successfully else return 400, 404
	 */
	@POST
	@Path("/{adminneuid}/notes")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createNote(AdministratorNotes input,@PathParam("adminneuid") String adminneuid){
		try{
			Administrators admin = administratorsDao.getAdministratorRecord(adminneuid);
			if(admin == null){
				return Response.status(Response.Status.NOT_FOUND).
						entity("administrator NEUID cannot be null").build();
			}
			input.setAdministratorNeuId(adminneuid);
			AdministratorNotes note = administratorNotesDao.addAdministratorNoteRecord(input);
			return Response.status(Response.Status.OK).
						entity(note).build();
			} catch (Exception e){
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
			}
	}
	
	/**
	 * Request 13
	 * This is a function to DELETE a student notes
	 * 
	 * http://localhost:8080/webapi/notes/{adminnoteid}
	 * @param 
	 * @return 200 if notes deleted successfully else return 400, 406
	 */
	@DELETE
	@Path("/notes/{adminnoteid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteNote(@PathParam("adminnoteid") String adminnoteid){
		try{
			if(administratorNotesDao.deleteAdministratorNoteRecord(Integer.valueOf(adminnoteid))){
				return Response.status(Response.Status.OK).
						entity("note deleted successfully").build();
			}
			} catch (Exception e){
				return Response.status(Response.Status.NOT_FOUND).entity("Please check the note ID.").build();
			}
		return Response.status(Response.Status.BAD_REQUEST).entity("Please check the request").build();
	}
	
	
	/**
	 * Request 14
	 * This function creates the password for admin when they reset their password
	 * 
	 * @param passwordCreateObject
	 * @return 200 if password changed successfully else return 400,500
	 */
	@POST
	@Path("/password-create")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createPassword(PasswordCreateObject passwordCreateObject){
		String email = passwordCreateObject.getEmail();
		String password = passwordCreateObject.getPassword();
		String registrationKey = passwordCreateObject.getRegistrationKey();
		
		AdminLogins adminLoginsExisting = adminLoginsDao.findAdminLoginsByEmail(email); 
		if(adminLoginsExisting == null) {
			return Response.status(Response.Status.BAD_REQUEST).
					entity("Admin does not exist." ).build();
		}

		String databaseRegistrationKey = adminLoginsExisting.getRegistrationKey();
		Timestamp databaseTimestamp = adminLoginsExisting.getKeyExpiration();

		if((databaseRegistrationKey.equals(registrationKey))){
			Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());

			if(databaseTimestamp.after(currentTimestamp)){
	    		String saltnewStr = email.substring(0, email.length()/2);
	    		String setPassword = password+saltnewStr;
	            String hashedPassword = SCryptUtil.scrypt(setPassword, 16, 16, 16);
				adminLoginsExisting.setAdminPassword(hashedPassword);
				adminLoginsExisting.setConfirmed(true);
				boolean adminLoginUpdatedWithPassword = adminLoginsDao.updateAdminLogin(adminLoginsExisting);
				if(adminLoginUpdatedWithPassword) {
					
					return Response.status(Response.Status.OK).
							entity("Congratulations! Password Reset successfully for Admin!").build();
				} else {
					return Response.status(Response.Status.INTERNAL_SERVER_ERROR).
							entity("Database exception thrown" ).build();
				}
			} else {
				return Response.status(Response.Status.OK).
						entity(" Registration key expired!" ).build();
			}
		} else {
			return Response.status(Response.Status.BAD_REQUEST).
					entity("Invalid registration key" ).build();
		}
	}
	
	/**
	 * Request 15
	 * This is a function to login using admin email and password
	 * 
	 * http://localhost:8080/webapi/login
	 * @param passwordChangeObject
	 * @return the token if logged in successfully 200 OK else 404,401,400,500
	 */
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response loginUser(@Context HttpServletRequest request,LoginObject loginInput){
		AdminLogins adminLogins = adminLoginsDao.findAdminLoginsByEmail(loginInput.getUsername());
		if(adminLogins == null){
			return Response.status(Response.Status.NOT_FOUND).
					entity("User doesn't exist: " + loginInput.getUsername()).build();
		}

        boolean matched = false;
        try{
        	String reqPass = loginInput.getPassword();
    		String saltStr = loginInput.getUsername().substring(0, loginInput.getUsername().length()/2);
    		String originalPassword = reqPass+saltStr;
        	matched = SCryptUtil.check(originalPassword,adminLogins.getAdminPassword());
        } catch (Exception e){
        	return Response.status(Response.Status.UNAUTHORIZED).
					entity("Incorrect Password").build();
        }

		if(matched){
			try {
				JSONObject jsonObj = new JSONObject();
				Timestamp keyGeneration = new Timestamp(System.currentTimeMillis());
				Timestamp keyExpiration = new Timestamp(System.currentTimeMillis()+15*60*1000);
				adminLogins.setLoginTime(keyGeneration);
				adminLogins.setKeyExpiration(keyExpiration);
				adminLoginsDao.updateAdminLogin(adminLogins);
				String ip = request.getRemoteAddr();
				JsonWebEncryption senderJwe = new JsonWebEncryption();
				senderJwe.setPlaintext(adminLogins.getEmail()+"*#*"+ip+"*#*"+keyGeneration.toString());
				senderJwe.setAlgorithmHeaderValue(KeyManagementAlgorithmIdentifiers.DIRECT);
				senderJwe.setEncryptionMethodHeaderParameter(ContentEncryptionAlgorithmIdentifiers.AES_128_CBC_HMAC_SHA_256);
				
				String secretKey = ip+"sEcR3t_nsA-K3y";
				byte[] key = secretKey.getBytes();
				key = Arrays.copyOf(key, 32);
				AesKey keyMain = new AesKey(key);
				senderJwe.setKey(keyMain);
				String compactSerialization = senderJwe.getCompactSerialization();
				jsonObj.put("token", compactSerialization);
				Administrators admin = administratorsDao.findAdministratorByEmail(loginInput.getUsername());
				jsonObj.put("id", admin.getAdministratorNeuId());
				
				return Response.status(Response.Status.OK).
						entity(jsonObj.toString()).build();
			} catch (Exception e) {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).
						entity(e).build();
			}
		}else{
			return Response.status(Response.Status.UNAUTHORIZED).
					entity("Incorrect Password").build();
		}
	}
	
	/**
	 * Request 16
	 * This is a function to logout
	 * 
	 * http://localhost:8080/webapi/logout
	 * @param 
	 * @return 200 OK else 404,500
	 */
	@POST
	@Path("/logout")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response logoutUser(@Context HttpServletRequest request,LoginObject loginInput){
		AdminLogins adminLogins = adminLoginsDao.findAdminLoginsByEmail(loginInput.getUsername());
		if(adminLogins == null){
			return Response.status(Response.Status.NOT_FOUND).
					entity("User doesn't exist: " + loginInput.getUsername()).build();
		}
		try{
			Timestamp keyExpiration = new Timestamp(System.currentTimeMillis());
			adminLogins.setKeyExpiration(keyExpiration);
			adminLoginsDao.updateAdminLogin(adminLogins);
		}
		catch (Exception e){
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).
					entity(e).build();	
		}
		return Response.status(Response.Status.OK).
				entity("Logged Out Successfully").build();
	}

	/**
	 * Request 17
	 * This is a function to change an existing admin's password
	 * 
	 * http://localhost:8080/webapi/password-change
	 * @param passwordChangeObject
	 * @return 200 if password changed successfully else return 404,400,401
	 */
	@POST
	@Path("/password-change")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response changeUserPassword(PasswordChangeObject passwordChangeObject){
		AdminLogins adminLogins = adminLoginsDao.findAdminLoginsByEmail(passwordChangeObject.getEmail());

		if(adminLogins == null){
			return Response.status(Response.Status.NOT_FOUND).
					entity("Email doesn't exist: " + passwordChangeObject.getEmail()).build();
		}

        boolean matched = false;
        try{
        	String reqPass = passwordChangeObject.getOldPassword();
    		String saltStr = passwordChangeObject.getEmail().substring(0, passwordChangeObject.getEmail().length()/2);
    		String originalPassword = reqPass+saltStr;
        	matched = SCryptUtil.check(originalPassword,adminLogins.getAdminPassword());
        } catch (Exception e){
        	return Response.status(Response.Status.UNAUTHORIZED).
					entity("Incorrect Password").build();
        }
        
		if(matched){
			String newPass = passwordChangeObject.getNewPassword();
    		String saltnewStr = passwordChangeObject.getEmail().substring(0, passwordChangeObject.getEmail().length()/2);
    		String updatePassword = newPass+saltnewStr;
            String generatedSecuredPasswordHash = SCryptUtil.scrypt(updatePassword, 16, 16, 16);
            adminLogins.setAdminPassword(generatedSecuredPasswordHash);
			adminLoginsDao.updateAdminLogin(adminLogins);

			return Response.status(Response.Status.OK).
					entity("Password Changed Succesfully!" ).build();
		}else{
			return Response.status(Response.Status.BAD_REQUEST).
					entity("Incorrect Password").build();
		}
	}


	/**
	 * Request 18
	 * This function sends email to admin’s northeastern ID to reset the password.
	 * 
	 * @param adminEmail
	 * @return 200 if password reset successfully else return 404,400,500
	 */
	@POST
	@Path("/password-reset")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response sendEmailForPasswordResetAdmin(PasswordResetObject passwordResetObject){
		String adminEmail = passwordResetObject.getEmail();
		if (adminEmail == null){
				return Response.status(Response.Status.BAD_REQUEST).
					entity("Email Id can't be null").build();
		}else{
			AdminLogins adminLogins = adminLoginsDao.findAdminLoginsByEmail(adminEmail);
			if(adminLogins == null){
				return Response.status(Response.Status.NOT_FOUND).
						entity("Email doesn't exist: " + adminEmail).build();
			}
			if(adminLogins.isConfirmed() == false){
				return Response.status(Response.Status.NOT_FOUND).
						entity("Password can't be reset....Please create password and register: " + adminEmail).build();
			}
			String registrationKey = createRegistrationKey(); 
			Timestamp keyExpirationTime = new Timestamp(System.currentTimeMillis()+ 15*60*1000);
			AdminLogins adminLoginsNew = new AdminLogins();
			adminLoginsNew.setEmail(adminEmail);
			adminLoginsNew.setAdminPassword(adminLogins.getAdminPassword()); 
			adminLoginsNew.setLoginTime(adminLogins.getLoginTime()); 
			adminLoginsNew.setRegistrationKey(registrationKey);
			adminLoginsNew.setKeyExpiration(keyExpirationTime);
			adminLoginsNew.setConfirmed(true);

			boolean adminLoginUpdated = adminLoginsDao.updateAdminLogin(adminLoginsNew);
			if(adminLoginUpdated) {
				MailClient.sendPasswordResetEmail(adminEmail, registrationKey);
				return Response.status(Response.Status.OK).
						entity("Password Reset link sent succesfully!" ).build(); 
			}
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).
					entity("Something Went Wrong" + adminEmail).build();
		}
	}

	private String createRegistrationKey() {
		return UUID.randomUUID().toString();
	}	
	
	
	    /**
     * Request 19
     * This is the function to get top undergraduate degrees.
     * The body should be in the JSON format like below:
     * <p>
     * http://localhost:8080/alignWebsite/webapi/public-facing/top-undergraddegrees
     *
     * @return List of n top undergraduate degrees
     */
    @POST
    @Path("autofill-search")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAutoFillSearch(String input) {
    	List<Students> students;
        JSONObject result = new JSONObject();
        JSONArray studentsArray = new JSONArray();
        String firstName = input;
        String middleName = input;
        String lastName = input;
        String neuId = input;
        String email = input;

        try {
        	
        	String[] inputSplit = input.split(" ");
        	if(inputSplit.length>2){
        		firstName = inputSplit[0];
        		middleName = inputSplit[1];
        		lastName = inputSplit[2];
        	}else if(inputSplit.length>1){
        		firstName = inputSplit[0];
        		lastName = inputSplit[1];
        	}else{
        		firstName = inputSplit[0];
        		middleName = inputSplit[0];
        		lastName = inputSplit[0];
        		neuId = inputSplit[0];
        		email = inputSplit[0];
        	}
        	
            students = studentDao.getAdminAutoFillSearch(firstName,middleName,lastName,neuId,email);
            for (Students student : students) {
                JSONObject studentJson = new JSONObject(student);
                result.put(neuId,studentJson);
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
        }

        return Response.status(Response.Status.OK).entity(result.toString()).build();
    }
	
	
}
