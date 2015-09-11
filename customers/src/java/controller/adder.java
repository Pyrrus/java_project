/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Iterator;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author Adam
 */
@WebServlet(name = "adder", urlPatterns = {"/adder"})
public class adder extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        JSONParser parser = new JSONParser();
        try {
            String myDriver = "org.gjt.mm.mysql.Driver";
            String myUrl = "jdbc:mysql://localhost:3306/customers";
            Class.forName(myDriver);
            Connection conn = DriverManager.getConnection(myUrl, "root", "");

            Statement st = conn.createStatement();

            ResultSet rs;
            rs = conn.createStatement().executeQuery("SELECT * FROM list");

            response.setContentType("text/html");
            PrintWriter out = response.getWriter();

            //get data from request into String
            InputStreamReader isr = new InputStreamReader(request.getInputStream());
            BufferedReader in = new BufferedReader(isr);
            String jsonText = in.readLine();
            System.out.println(jsonText);

            Object obj = parser.parse(jsonText);
            JSONObject jsonObject = (JSONObject) obj;

            JSONArray slideContent = (JSONArray) jsonObject.get("list");
            Iterator i = slideContent.iterator();

            while (i.hasNext()) {
                JSONObject slide = (JSONObject) i.next();
                String firstName = (String) slide.get("firstName");
                String lastName = (String) slide.get("lastName");
                long idholder = (long) slide.get("id");
                String email = (String) slide.get("email");
                String phone = (String) slide.get("phone");
                String street = (String) slide.get("street");
                String state = (String) slide.get("state");
                String zip = (String) slide.get("zip");
                String city = (String) slide.get("city");

                int id = (int) idholder;

                String query = "SELECT * FROM list WHERE id = " + id;

                rs = conn.createStatement().executeQuery(query);

                if (rs.next()) {
                    query = "UPDATE list SET id = " + id + ", firstName = '" + firstName + "', lastName = '" + lastName + "', email = '" + email + "', phone = '" + phone + "', street = '" + street + "', city = '" + city + "', state = '" + state + "', zip = '" + zip + "' WHERE id =" + id;
                    conn.createStatement().executeUpdate(query);
                    response.getWriter().println("Update");
                } else {
                    query = "INSERT INTO list (id, firstName, lastName, email, phone, street, city, state, zip) VALUES (" + id + ", '" + firstName + "', '" + lastName + "', '" + email + "', '" + phone + "', '" + street + "', '" + city + "', '" + state + "', '" + zip + "')";
                    conn.createStatement().executeUpdate(query);
                    response.getWriter().println("add");

                }

            }

            st.close();

        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
