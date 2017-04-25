/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cegepgim;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
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
     * @param useremail
     * @param number
     * @param pass
     * @return an instance of java.lang.String
     */
  @Path("signup&{Uname}&{Ulastn}&{Uid}&{Uemail}&{Unumber}&{Upass}")
    @GET
    @Produces("application/json")
    public String signup(@PathParam("Uname") String name,@PathParam("Ulastn") String lastn,@PathParam("Uid") String id,@PathParam("Uemail") String useremail,@PathParam("Unumber") String number,@PathParam("Upass") String pass) throws SQLException, IOException {
        try {
            create_connection.getConnection();
            String sql1="INSERT INTO PROJ3.USERINFO (FIRSTNAME, LASTNAME, USER_ID, EMAIL, PHONENUMBER, PASSWORD) VALUES ('"+name+"', '"+lastn+"', '"+id+"', '"+useremail+"', '"+number+"', '"+pass+"')";
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
                  //Date DOB=rs.getDate("DOB");
                    JSONObject ordersobj = new JSONObject();

                    ordersobj.accumulate("USER_ID", USER_ID);
                    ordersobj.accumulate("FIRSTNAME", FIRSTNAME);

                    ordersobj.accumulate("LASTNAME", LASTNAME);
                    ordersobj.accumulate("EMAIL", EMAIL);
                    ordersobj.accumulate("PHONENUMBER", PHONENUMBER);
                    //ordersobj.accumulate("DOB", DOB);
                   

                    ary.add(ordersobj);
                    ordersobj.clear();
                }
                obj.accumulate("Orders", ary);

            } else {
                status = "wrong";

                obj.accumulate("Status", status);
                Long timenow = curenttime();
                obj.accumulate("Timestmap", timenow);
                obj.accumulate("Message", "no column or row found");
             
            }
          
        } catch (Exception e) {
            obj.accumulate("Status", "ERROR");
            obj.accumulate("TimeStamp", curenttime());
            obj.accumulate("Message", e.getLocalizedMessage());
   
        }
        System.out.println("Goodbye!");
 
        return obj.toString();
    }

    public static Long curenttime() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return timestamp.getTime();
    }}