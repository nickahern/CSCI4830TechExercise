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

@WebServlet("/SearchAhern")
public class SearchAhern extends HttpServlet
{
   private static final long serialVersionUID = 1L;

   public SearchAhern()
   {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
   {
      String keyword = request.getParameter("keyword");
      search(keyword, response);
   }

   void search(String keyword, HttpServletResponse response) throws IOException
   {
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String title = "Search Results";
      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 transitional//en\">\n"; //
      out.println(
    		docType + //
            "<html>\n" + //
            "<head><title>" + title + "</title></head>\n" + //
            "<body bgcolor=\"#f2e4b6\">\n" + //
            "<h1 align=\"center\">" + title + "</h1>\n");

      Connection connection = null;
      PreparedStatement preparedStatement = null;
      try
      {
         DBConnectionAhern.getDBConnection();
         connection = DBConnectionAhern.connection;

         if (keyword.isEmpty())
         {
            String selectSQL = "SELECT * FROM myTableFinal";
            preparedStatement = connection.prepareStatement(selectSQL);
         }
         else
         {
            String selectSQL = "SELECT * FROM myTableFinal WHERE DUEDATE LIKE ?";
            String theUserName = keyword + "%";
            preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setString(1, theUserName);
         }
         ResultSet rs = preparedStatement.executeQuery();

         out.println("<ul>\n");
         while (rs.next())
         {
             String dueDate1 = rs.getString("DUEDATE");
             String dueTime1 = rs.getString("DUETIME");
             String taskName1 = rs.getString("TASKNAME");
             String description1 = rs.getString("DESCRIPTION");
             String length1 = rs.getString("LENGTH");
             String constraints1 = rs.getString("CONSTRAINTS");

            if (keyword.isEmpty() || dueDate1.contains(keyword))
            {
                out.append("<li><b>Due date</b>: " + dueDate1 + "; ");
                out.append("<b>Due time</b>: " + dueTime1 + "; ");
                out.append("<b>Task name</b>: " + taskName1 + "; ");
                out.append("<b>Description</b>: " + description1 + "; ");
                out.append("<b>Length in hours</b>: " + length1 + "; ");
                out.append("<b>Constraints</b>: " + constraints1 + "</li> \n");
            }
         }
         out.println("</ul>\n");
         out.println("<a href=/techexercise/SearchAhern.html>Search again</a> <br>");
         out.println("<a href=/techexercise/InsertAhern.html>Add a task</a> <br>");
         out.println("</body></html>");
         rs.close();
         preparedStatement.close();
         connection.close();
      }
      catch (SQLException se)
      {
         se.printStackTrace();
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }
      finally
      {
         try
         {
            if (preparedStatement != null)
               preparedStatement.close();
         }
         catch (SQLException se2)
         {
         }
         try
         {
            if (connection != null)
               connection.close();
         }
         catch (SQLException se)
         {
            se.printStackTrace();
         }
      }
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
   {
      doGet(request, response);
   }

}
