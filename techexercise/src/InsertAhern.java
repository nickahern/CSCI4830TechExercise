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
public class InsertAhern extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public InsertAhern() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String dueDate = request.getParameter("dueDate");
      String dueTime = request.getParameter("dueTime");
      String taskName = request.getParameter("taskName");
      String description = request.getParameter("description");
      String length = request.getParameter("length");
      String constraints = request.getParameter("constraints");

      Connection connection = null;
      String insertSql = " INSERT INTO myTableTest (id, DUEDATE, DUETIME, TASKNAME, DESCRIPTION, LENGTH, CONSTRAINTS) values (default, ?, ?, ?, ?, ?, ?)";

      try {
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
      } catch (Exception e) {
         e.printStackTrace();
      }

      // Set response content type
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String title = "Task Added Successfully!";
      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
      out.println(docType + //
            "<html>\n" + //
            "<head><title>" + title + "</title></head>\n" + //
            "<body bgcolor=\"#f0f0f0\">\n" + //
            "<h2 align=\"center\">" + title + "</h2>\n" + //
            "<ul>\n" + //

            "  <li><b>Due Date</b>: " + dueDate + "\n" + //
            "  <li><b>Due Time</b>: " + dueTime + "\n" + //
            "  <li><b>Task Name</b>: " + taskName + "\n" + //
            "  <li><b>Description</b>: " + description + "\n" + //
            "  <li><b>Length</b>: " + length + "\n" + //
            "  <li><b>Constraints</b>: " + constraints + "\n" + //
            "</ul>\n");

      out.println("<a href=/techexercise/TechExerciseAhern.html>Add another task</a> <br>");
      out.println("</body></html>");
      
      try
      {
          String selectSQL = "SELECT * FROM myTableTest";
          out.println(selectSQL + "<br>");
          out.println("------------------------------------------<br>");
          PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
          ResultSet rs = preparedStatement.executeQuery();
          while (rs.next())
          {
             String dueDate1 = rs.getString("DUEDATE");
             String dueTime1 = rs.getString("DUETIME");
             String taskName1 = rs.getString("TASKNAME");
             String description1 = rs.getString("DESCRIPTION");
             String length1 = rs.getString("LENGTH");
             String constraints1 = rs.getString("CONSTRAINTS");
             response.getWriter().append("DUE DATE: " + dueDate1 + ", ");
             response.getWriter().append("DUE TIME: " + dueTime1 + ", ");
             response.getWriter().append("DUE DATE: " + taskName1 + ", ");
             response.getWriter().append("DUE TIME: " + description1 + ", ");
             response.getWriter().append("DUE DATE: " + length1 + ", ");
             response.getWriter().append("USER PHONE: " + constraints1 + "<br>");
          }
          connection.close();
       }
      catch (SQLException e)
      {
          e.printStackTrace();
       }
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }

}
