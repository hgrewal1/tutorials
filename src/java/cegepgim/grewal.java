/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cegepgim;

import java.io.IOException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import static javax.ws.rs.core.Response.status;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import utilities.create_connection;

/**
 * REST Web Service
 *
 * @author 1691544
 */

@Path("tutorials")
public class grewal {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of grewal
     */
    public static String status = null;
    JSONObject obj = new JSONObject();

    public grewal() {
    }

    /**
     * Retrieves representation of an instance of cegepgim.grewal
     * @param name
     * @param lastn
     * @param id
     * @param useremail
     * @param number
     * @param pass
     * @param birthdate
     * @return an instance of java.lang.String
     * @throws java.sql.SQLException
     * @throws java.io.IOException
     */
  @Path("signup&{Uname}&{Ulastn}&{Uid}&{Uemail}&{Unumber}&{Upass}&{datebirth}")
    @GET
    @Produces("application/json")
    public String signup (@PathParam("Uname") String name,@PathParam("Ulastn") String lastn,@PathParam("Uid") String id,@PathParam("Uemail") String useremail,@PathParam("Unumber") String number,@PathParam("Upass") String pass,@PathParam("datebirth") String birthdate) throws SQLException, IOException {
        try {
            create_connection.getConnection();
            String sql1="INSERT INTO PROJ3.USERINFO (FIRSTNAME, LASTNAME, USER_ID, EMAIL, PHONENUMBER, PASSWORD,dob) VALUES ('"+name+"', '"+lastn+"', '"+id+"', '"+useremail+"', '"+number+"', '"+pass+"', '"+birthdate+"')";
             ResultSet rs1 = create_connection.grewal(sql1);
             rs1.close();
            String sql = "select * from USERINFO";
            ResultSet rs = create_connection.grewal(sql);
            if (rs.next()) {
                status = "ok";
                obj.accumulate("Status", status);
                Long timenow = curenttime();
                obj.accumulate("Timestmap", timenow);
                
                rs.beforeFirst();
                JSONArray ary = new JSONArray();
                
                while (rs.next()) {
                    
                    String FIRSTNAME = rs.getString("FIRSTNAME");
                   String LASTNAME = rs.getString("LASTNAME");
                   String USER_ID = rs.getString("USER_ID");
                   String EMAIL = rs.getString("EMAIL");
                    String PHONENUMBER = rs.getString("PHONENUMBER");
                  Date DOB=rs.getDate("DOB");
                String PASSWORD = rs.getString("PASSWORD");
                
                 String newDate = dateFormatter(DOB);
                    JSONObject newobj = new JSONObject();

                    newobj.accumulate("UserId", USER_ID);
                    newobj.accumulate("FirstName", FIRSTNAME);

                    newobj.accumulate("LastName", LASTNAME);
                    newobj.accumulate("Email", EMAIL);
                    newobj.accumulate("PhoneNumber", PHONENUMBER);
                    newobj.accumulate("Dob", newDate);
                    newobj.accumulate("Password", PASSWORD);
                   

                    ary.add(newobj);
                    newobj.clear();
                }
                obj.accumulate("Users", ary);

            } else {
                status = "wrong";

                obj.accumulate("Status", status);
                Long timenow = curenttime();
                obj.accumulate("Timestmap", timenow);
                obj.accumulate("UserId", id);
                obj.accumulate("Message", "no column or row found");
             
            }
          
        } catch (Exception e) {
            obj.accumulate("Status", "ERROR");
            obj.accumulate("TimeStamp", curenttime());
             obj.accumulate("UserId", id);
            obj.accumulate("Message", e.getLocalizedMessage());
   
        }
        System.out.println("Goodbye!");
 
        return obj.toString();
    }
 @Path("addcomplaints&{description}&{Uid}&{Tutid}")
    @GET
    @Produces("application/json")
    public String addcomplaints (@PathParam("description") String Cdescription,@PathParam("Tutid") String Tut_id,@PathParam("Uid") String Userid) throws SQLException, IOException {
        try {
            create_connection.getConnection();
            String current_time=timenow();
            String sql1="INSERT INTO PROJ3.COMPLAINTS (DESCRIPTIONS, TUT_ID, USER_ID,COM_DATE) VALUES ('"+Cdescription+"', '"+Tut_id+"', '"+Userid+"',TO_TIMESTAMP('"+current_time+"','yyyy-MM-dd hh24:mi:ss'))";
             ResultSet rs1 = create_connection.grewal(sql1);
             rs1.close();
            String sql = "select * from COMPLAINTS where DESCRIPTIONS='"+Cdescription+"'";
            ResultSet rs = create_connection.grewal(sql);
            if (rs.next()) {
                status = "ok";
                obj.accumulate("Status", status);
                Long timenow = curenttime();
                obj.accumulate("Timestmap", timenow);
                
                    String DESCRIPTIONS = rs.getString("DESCRIPTIONS");
                    String TutorialId = rs.getString("TUT_ID");
                    String UserID = rs.getString("USER_ID");
                    obj.accumulate("Complaint", DESCRIPTIONS);
                    obj.accumulate("TutorialId", TutorialId);
                    obj.accumulate("UserID", UserID);
                    obj.accumulate("ComplaintDate", timenow());
                
            } else {
                status = "wrong";

                obj.accumulate("Status", status);
                Long timenow = curenttime();
                obj.accumulate("Timestmap", timenow);
                obj.accumulate("TutorialId", Tut_id);
                    obj.accumulate("UserID", Userid);
                obj.accumulate("Message", "no column or row found");
             
            }
          
        } catch (Exception e) {
            obj.accumulate("Status", "ERROR");
            obj.accumulate("TimeStamp", curenttime());
            obj.accumulate("TutorialId", Tut_id);
            obj.accumulate("UserID", Userid);
            obj.accumulate("Message", e.getLocalizedMessage());
   
        }
        System.out.println("Goodbye!");
 
        return obj.toString();
    }
@Path("addsuggestions&{SUGGESTIONS}&{Uid}&{Tutid}")
    @GET
    @Produces("application/json")
    public String addsuggestions (@PathParam("SUGGESTIONS") String suggestion,@PathParam("Tutid") String Tut_id,@PathParam("Uid") String Userid) throws SQLException, IOException {
        try {
            create_connection.getConnection();
            String current_time=timenow();
            String sql1="INSERT INTO PROJ3.SUGGESTIONS (REVIEWS, TUT_ID, USER_ID,SUG_DATE) VALUES ('"+suggestion+"', '"+Tut_id+"', '"+Userid+"',TO_TIMESTAMP('"+current_time+"','yyyy-MM-dd hh24:mi:ss'))";
             ResultSet rs1 = create_connection.grewal(sql1);
             rs1.close();
            String sql = "select * from SUGGESTIONS where REVIEWS='"+suggestion+"'";
            ResultSet rs = create_connection.grewal(sql);
            if (rs.next()) {
                status = "ok";
                obj.accumulate("Status", status);
                Long timenow = curenttime();
                obj.accumulate("Timestmap", timenow);
                
                    String REVIEWS = rs.getString("REVIEWS");
                    String TutorialId = rs.getString("TUT_ID");
                    String UserID = rs.getString("USER_ID");
                    obj.accumulate("Reviews", REVIEWS);
                    obj.accumulate("TutorialId", TutorialId);
                    obj.accumulate("UserID", UserID);
                    obj.accumulate("SuggestionDate", timenow());
                
            } else {
                status = "wrong";

                obj.accumulate("Status", status);
                Long timenow = curenttime();
                obj.accumulate("Timestmap", timenow);
                obj.accumulate("TutorialId", Tut_id);
                    obj.accumulate("UserID", Userid);
                obj.accumulate("Message", "no column or row found");
             
            }
          
        } catch (Exception e) {
            obj.accumulate("Status", "ERROR");
            obj.accumulate("TimeStamp", curenttime());
            obj.accumulate("TutorialId", Tut_id);
            obj.accumulate("UserID", Userid);
            obj.accumulate("Message", e.getLocalizedMessage());
   
        }
        System.out.println("Goodbye!");
 
        return obj.toString();
    }
@Path("addtutorials&{tut_category}&{Tutid}&{userid}&{DESCRIPTION}")
    @GET
    @Produces("application/json")
    public String addtutorials (@PathParam("DESCRIPTION") String DESCRIPTION,@PathParam("userid") String userid,@PathParam("tut_category") String tut_cat,@PathParam("Tutid") String Tut_id) throws SQLException, IOException {
        try {
            create_connection.getConnection();
            String current_time=timenow();
            String sql1="INSERT INTO PROJ3.TUTORIALS (DESCRIPTION, USER_ID,TUT_ID, ADDED_DATE) VALUES ('"+DESCRIPTION+"', '"+userid+"', '"+Tut_id+"',TO_TIMESTAMP('"+current_time+"','yyyy-MM-dd hh24:mi:ss'))";
             ResultSet rs1 = create_connection.grewal(sql1);
             rs1.close();
             String sql2="INSERT INTO PROJ3.TUTORIAL_CATEGORY (TUT_ID, CAT_ID) VALUES ('"+Tut_id+"', '"+tut_cat+"')";
             ResultSet rs2 = create_connection.grewal(sql2);
             rs2.close();
            String sql = "select TUTORIALS.DESCRIPTION,CATEGORIES.CAT_NAME,TUTORIALS.ADDED_DATE,TUTORIALS.TUT_ID,TUTORIALS.USER_ID,TUTORIAL_CATEGORY.CAT_ID from TUTORIALS left join TUTORIAL_CATEGORY on TUTORIAL_CATEGORY.TUT_ID=TUTORIALS.TUT_ID left join CATEGORIES on CATEGORIES.CAT_ID =TUTORIAL_CATEGORY.CAT_ID where TUTORIALS.TUT_ID='"+Tut_id+"'";
            ResultSet rs = create_connection.grewal(sql);
            if (rs.next()) {
                status = "ok";
                obj.accumulate("Status", status);
                Long timenow = curenttime();
                obj.accumulate("Timestmap", timenow);
                
                    String CAT_NAME = rs.getString("CAT_NAME");
                  
                    
                    obj.accumulate("TutorialName", DESCRIPTION);
                    obj.accumulate("AddedDate", timenow());
                    obj.accumulate("TutorialId", Tut_id);
                    obj.accumulate("UserId", userid);
                    obj.accumulate("CategoryName", CAT_NAME);
                    obj.accumulate("CategoryId", tut_cat);
                
            } else {
                status = "wrong";

                obj.accumulate("Status", status);
                Long timenow = curenttime();
                obj.accumulate("Timestmap", timenow);
                obj.accumulate("TutorialId", Tut_id);
                    obj.accumulate("UserID", userid);
                obj.accumulate("Message", "no column or row found");
             
            }
          
        } catch (Exception e) {
            obj.accumulate("Status", "ERROR");
            obj.accumulate("TimeStamp", curenttime());
            obj.accumulate("TutorialId", Tut_id);
            obj.accumulate("UserID", userid);
            obj.accumulate("Message", e.getLocalizedMessage());
   
        }
        System.out.println("Goodbye!");
 
        return obj.toString();
    }
    public static Long curenttime() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return timestamp.getTime();
    }
    public static String dateFormatter(Date d ) {
        DateFormat df = new SimpleDateFormat("MM-dd-yyyy", Locale.ENGLISH);
        String dat = df.format(d);
        return dat;
    }
    public static String timenow(){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.ENGLISH);
        String timeStamp =df.format(new Timestamp(System.currentTimeMillis()));
    return timeStamp;}
}