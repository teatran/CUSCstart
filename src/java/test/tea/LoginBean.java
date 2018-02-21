package test.tea;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import test.tea.db.DbOp;

@ManagedBean
@SessionScoped
public class LoginBean implements Serializable
{

    private String username;
    private String password;
    private String message;
    private List<Student> studentList = null;
    private List<Student> filterStudents;

    public LoginBean()
    {
    }
    
    public List<Student> getFilterStudents()
    {
        return filterStudents;
    }
    
    public void setFilterStudents(List<Student> newValue)
    {
        filterStudents = newValue;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String newValue)
    {
        username = newValue;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String newValue)
    {
        password = newValue;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String newValue)
    {
        message = newValue;
    }

    public String validateLogin()
    {
        boolean valid = DbOp.validate(username, password);
        if (valid) {
            // save username into session
            HttpSession session = SessionUtils.getSession();
            session.setAttribute("username", username);
            return "/showlist.xhtml?faces-redirect=true";
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("Invalid login. Please enter correct username and password"));
            return "/login.xhtml";
        }
    }

    public List<Student> getStudentList()
    {
        if (studentList == null) {
            studentList = DbOp.selectStudentsSameClass(username);
        }
        return studentList;
    }

    public String deleteStudent(String id)
    {
        studentList = null;   // reset student list since it will be updated
        int studentId = Integer.parseInt(id);
        DbOp.deleteStudent(studentId);
        return "/showlist.xhtml?faces-redirect=true";
    }

    public String showEditStudent(String id)
    {
        studentList = null;   // reset student list since it will be updated
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance()
            .getExternalContext().getSessionMap();
        int studentId = Integer.parseInt(id);
        Student editStudent = DbOp.getEditStudent(studentId);
        sessionMap.put("editStudent", editStudent);
        return "edit";
    }

    public String logout()
    {
        HttpSession session = SessionUtils.getSession();
        session.invalidate();
        return "/login.xhtml";
    }
}
