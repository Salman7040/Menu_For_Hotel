
import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

@WebServlet(urlPatterns = {"/MyNewServlet"})
public class MyNewServlet extends HttpServlet {

    Connection c1;
    Statement st;

    @Override
    public void init() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            c1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbemp?zeroDateTimeBehavior="
                    + "CONVERT_TO_NULL", "root", "root");
            st = c1.createStatement();
        } catch (Exception e) {
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        res.setContentType("text/html");
        PrintWriter prt = res.getWriter();

        String i_name, i_Quantity, myf;
        int i_Price = 0, Total = 0;

        myf = req.getParameter("show");
        i_name = req.getParameter("menu");
        i_Quantity = req.getParameter("Quantity");

        if (myf.equals("s1") == true) {
            try {

                ResultSet rs = st.executeQuery("Select * from item_menu ");
                prt.print("<style>th{border:2px solid red;background-color:yellow;}"
                        + "td{border:2px solid green;background-color:#FF95FFB7;}</style>"
                        + "<table><th>Item_Name</th>"
                        + "<th>Item_Quantity</th>"
                        + "<th>Item_Price</th>"
                        + "<th>Total Amount</th>");
                while (rs.next()) {
                    prt.print("<tr><td>" + rs.getString(1) + "</td>"
                                 + "<td>" + rs.getString(2) + "</td>"
                                 + "<td>" + rs.getString(3) + "</td>"
                                 + "<td>" + rs.getString(4) + "</td></tr>");
                }
                prt.print("</table>");

            } catch (SQLException e) {

            }//show data if

        } else if (myf.equals("sub") == true) {

            switch (i_name) {
                case "Tea" ->
                    i_Price = 100;
                case "Cofee" ->
                    i_Price = 200;
                case "Samosa" ->
                    i_Price = 300;
            }
            int x = Integer.parseInt(i_Quantity);
            Total = i_Price * x;

            try {
                boolean b1 = st.execute("insert into item_menu values('" + i_name + "'," + i_Quantity + "," + i_Price + "," + Total + ")  ");
                if (b1 == false) {
                res.sendRedirect("index.html");

                }
            } catch (SQLException e) {
            }

        }//submit else if
        else if(myf.equals("remove") == true){
            try {
                boolean b2=st.execute("delete from item_menu where 1=1");
                if(b2==false){
                res.sendRedirect("myshow.html");
                }
            } catch (Exception e) {
            }
        }

    }

    @Override
    public void destroy() {
        try {
            st.close();
            c1.close();
        } catch (SQLException e) {
        }

    }
}
