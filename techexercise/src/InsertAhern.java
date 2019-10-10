/**
 * @file InsertAhern.java
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/InsertAhern")
public class InsertAhern extends HttpServlet
{
   private static final long serialVersionUID = 1L;

   public InsertAhern()
   {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
   {
      String dueDate = request.getParameter("dueDate");
      String dueTime = request.getParameter("dueTime");
      String taskName = request.getParameter("taskName");
      String description = request.getParameter("description");
      String length = request.getParameter("length");
      String constraints = request.getParameter("constraints");

      Connection connection = null;
      String insertSql = " INSERT INTO myTableFinal (id, DUEDATE, DUETIME, TASKNAME, DESCRIPTION, LENGTH, CONSTRAINTS) values (default, ?, ?, ?, ?, ?, ?)";

      try
      {
         DBConnectionAhern.getDBConnection();
         connection = DBConnectionAhern.connection;
         PreparedStatement preparedStmt = connection.prepareStatement(insertSql);
         preparedStmt.setString(1, dueDate);
         preparedStmt.setString(2, dueTime);
         preparedStmt.setString(3, taskName);
         preparedStmt.setString(4, description);
         preparedStmt.setString(5, length);
         preparedStmt.setString(6, constraints);
         preparedStmt.execute();
         connection.close();
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }

      // Set response content type
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String title = "Task Added Successfully!";
      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
      out.println(
    		docType + //
            "<html>\n" + //
            "<head><title>" + title + "</title></head>\n" + //
            "<body bgcolor=\"#85b6c5\">\n" + //
            "<h2 align=\"center\">" + title + "</h2>\n" + //
            "<ul>\n" + //

            "  <li><b>Due date</b>: " + dueDate + "\n" + //
            "  <li><b>Due time</b>: " + dueTime + "\n" + //
            "  <li><b>Task name</b>: " + taskName + "\n" + //
            "  <li><b>Description</b>: " + description + "\n" + //
            "  <li><b>Length in hours</b>: " + length + "\n" + //
            "  <li><b>Constraints</b>: " + constraints + "\n" + //
            "</ul>\n");

      out.println("<a href=/techexercise/InsertAhern.html>Add another task</a> <br>");
      out.println("<a href=/techexercise/SearchAhern.html>Search the list</a> <br>");
      out.println("</body></html>");
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
   {
      doGet(request, response);
   }

}
