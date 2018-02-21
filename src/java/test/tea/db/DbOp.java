package test.tea.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.faces.model.SelectItem;
import test.tea.LoginBean;
import test.tea.Student;

public class DbOp
{

    public static Connection getConnection()
    {
        try {
            Class.forName("com.mysql.jdbc.Driver");  // loads connector class
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/loginman",
                "tea", "anhthe939**");
            return conn;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean validate(String username, String password)
    {
        Connection conn;
        PreparedStatement ps;
        try {
            conn = DbOp.getConnection();
            ps = conn.prepareStatement("SELECT username, password FROM student WHERE username=? AND password=?");
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet result = ps.executeQuery();
            if (result.next()) {
                // result found, means valid login
                conn.close();
                System.out.println("Query OK");
                return true;
            }
        } catch (SQLException se) {
            System.out.println(se.getMessage());
        }
        return false;
    }

    public static List<SelectItem> selectAllClasses()
    {
        List<SelectItem> allClasses = new ArrayList<>();
        try (Connection conn = getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT classid, classname"
                + " from class");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                allClasses.add(new SelectItem(rs.getString(1), rs.getString(2)));
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return allClasses;
    }

    public static List<Student> selectStudentsSameClass(String username)
    {
        List<Student> allStudents = new ArrayList<>();
        try (Connection conn = getConnection()) {
            PreparedStatement ps = conn.prepareStatement(
                "SELECT studentid, username, fullname, sex, email, dayofbirth, classname"
                + " FROM student JOIN class ON (student.classid=class.classid)"
                + " WHERE student.classid=(SELECT classid FROM student"
                                        + " WHERE username=?)");
            ps.setString(1, username);
            System.out.println("*** " + ps);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Student student = new Student();
                student.setStudentid(rs.getInt("studentid"));
                student.setUsername(rs.getString("username"));
                student.setFullname(rs.getString("fullname"));
                student.setSex(rs.getString("sex"));
                student.setEmail(rs.getString("email"));
                // since resultSet.getDate returns a java.sql.Date object
                // we have to use getTimestamp
                Timestamp timestamp = rs.getTimestamp("dayofbirth");
                student.setDayofbirth(new java.util.Date(timestamp.getTime()));
                System.out.println("*** " + student.getDayofbirth());   // ugly test
                
                student.setClassname(rs.getString("classname"));
                
                allStudents.add(student);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return allStudents;
    }

    public static int insertStudent(Student student)
    {
        int updateCount = 0;
        try (Connection conn = getConnection()) {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO student"
                + "(username, password, fullname, sex, email, dayofbirth, classid) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, student.getUsername());
            ps.setString(2, student.getPassword());
            ps.setString(3, student.getFullname());
            ps.setString(4, student.getSex());
            ps.setString(5, student.getEmail());

            // convert java.util.Date object to MySql Date string
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateString = sdf.format(student.getDayofbirth());
            System.out.println("*** " + dateString);   // ugly test
            ps.setString(6, dateString);

            ps.setInt(7, student.getClassid());

            updateCount = ps.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return updateCount;
    }
    
    public static void deleteStudent(int studentId)
    {
        try (Connection conn = getConnection()) {
            PreparedStatement ps = conn.prepareStatement(
                "DELETE FROM student WHERE studentid=?");
            ps.setInt(1, studentId);
            ps.executeUpdate();
        } 
        catch (SQLException se) {
            se.printStackTrace();
        }
    }
    
    public static Student getEditStudent(int studentId)
    {
        Student editStudent = new Student();
        try (Connection conn = getConnection()) {
            PreparedStatement ps = conn.prepareStatement(
                "SELECT studentid, username, password, fullname, sex, email, dayofbirth, classid" +
                " FROM student WHERE studentid=?");
            ps.setInt(1, studentId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                editStudent.setStudentid(rs.getInt(1));
                editStudent.setUsername(rs.getString(2));
                editStudent.setPassword(rs.getString(3));
                editStudent.setFullname(rs.getString(4));
                editStudent.setSex(rs.getString(5));
                editStudent.setEmail(rs.getString(6));
                editStudent.setDayofbirth(rs.getDate(7));
                editStudent.setClassid(rs.getInt(8));
            }
        }
        catch (SQLException se) {
            se.printStackTrace();
        }
        return editStudent;
    }
    
    public static int updateStudent(Student student)
    {
        int updateCount = 0;
        try (Connection conn = getConnection()) {
            PreparedStatement ps = conn.prepareStatement("UPDATE student "
                + "SET username=?, password=?, fullname=?, sex=?, email=?, dayofbirth=?, classid=? "
                + "WHERE studentid=?");
            ps.setString(1, student.getUsername());
            ps.setString(2, student.getPassword());
            ps.setString(3, student.getFullname());
            ps.setString(4, student.getSex());
            ps.setString(5, student.getEmail());

            // convert java.util.Date object to MySql Date string
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateString = sdf.format(student.getDayofbirth());
            System.out.println("*** " + dateString);   // ugly test
            ps.setString(6, dateString);

            ps.setInt(7, student.getClassid());
            ps.setInt(8, student.getStudentid());
            System.out.println("*** " + ps);   // ugly test
            updateCount = ps.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return updateCount;
    }

    // we run it as a unit test
    public static void main(String[] args)
    {
        if (validate("test", "test")) {
            System.out.println("Query OK");
        }
    }
}
