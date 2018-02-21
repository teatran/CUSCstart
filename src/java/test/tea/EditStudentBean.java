
package test.tea;

import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import test.tea.db.DbOp;

@ManagedBean
@SessionScoped
public class EditStudentBean
{
    @ManagedProperty(value = "#{editStudent}")
    private Student student;
    
    private static List<SelectItem> classList = null;
    
    public EditStudentBean() { }
    
    public Student getStudent()
    {
        return student;
    }
    
    public void setStudent(Student newValue) 
    { 
        student = newValue; 
    }
    
    public List<SelectItem> getClassList()
    {
        if (classList == null) {
            classList = DbOp.selectAllClasses();
        }
        return classList;
    }
    
    public String saveEditStudent()
    {
        int nUpdate = DbOp.updateStudent(student);
        if (nUpdate > 0)
            return "/showlist.xhtml?faces-redirect=true";
        else {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("Can not edit this student"));
            return "edit";
        }
    }
}
