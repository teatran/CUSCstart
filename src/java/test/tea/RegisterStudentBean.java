/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.tea;

import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

import test.tea.db.DbOp;

@ManagedBean
@SessionScoped
public class RegisterStudentBean implements Serializable
{

    private Student student;
    private static List<SelectItem> classList = null;

    public RegisterStudentBean()
    {
        student = new Student();
    }

    public Student getStudent()
    {
        return student;
    }

    public List<SelectItem> getClassList()
    {
        if (classList == null) {
            classList = DbOp.selectAllClasses();
        }
        return classList;
    }

    public String insertDb()
    {
        int nInsert = DbOp.insertStudent(student);
        if (nInsert > 0) {
            // reset all fields to register new student
            student.setUsername(null);
            student.setPassword(null);
            student.setFullname(null);
            student.setSex(null);
            student.setEmail(null);
            student.setClassid(null);
            student.setEmail(null);
            // redirect to login page
            return "/login";
        }
        return "register";
    }

}
