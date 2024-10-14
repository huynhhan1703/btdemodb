/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package myservlet;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author mygam
 */
public class ViewServlet extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            java.sql.Connection conn = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
            String Data = "";
            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                conn = DriverManager.getConnection("jdbc:sqlserver://May04:1433;databaseName=demodb", "sa", "123");
                ps = conn.prepareStatement("select * from users");
                rs = ps.executeQuery();
                Data += "<table border=1>";
                Data += "<tr><th>Id</th><th>Name</th><th>Password</th><th>Email</th><th>Country</th><th>Edit</th><th>Delete</th></tr>";
                while (rs.next()) {
                    Data += "<tr>";
                    Data += "<td>" + rs.getInt(1) + "</td>\n";
                    Data += "<td>" + rs.getString(2) + "</td>\n";
                    Data += "<td>" + rs.getString(3) + "</td>\n";
                    Data += "<td>" + rs.getString(4) + "</td>\n";
                    Data += "<td>" + rs.getString(5) + "</td>\n";
                    Data += "<td><a href=EditServlet?id=" + rs.getInt(1) + ">Edit</a></td>\n";
                    Data += "<td><a href=DeleteServlet?id=" + rs.getInt(1) + " onclick=\"return confirm('Are you sure ?')\">Delete</a></td>\n";
                    Data += "</tr>";
                }
                Data += "</table>";
                conn.close();
            } catch (Exception e) {
                System.out.println("Loi: " + e.toString());
                out.println("<h2>Thêm user thất bại</h2>");
            }
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ViewServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<a href=index.html>Add New user</a>");
            out.println("<h1>Users List</h1>");
            out.println(Data);
            out.println("</body>");
            out.println("</html>");
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
