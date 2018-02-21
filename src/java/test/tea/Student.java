package test.tea;

import java.io.Serializable;
import java.util.Date;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;


public class Student implements Serializable
{
    private Integer studentid;
    @NotBlank
    private String username;
    @Size(min = 3, message = "Password length must be at least 3")
    private String password;
    @NotBlank
    private String fullname;
    @NotBlank
    private String sex;
    @Pattern(regexp="^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$",
        message="Invalid Email Address")
    private String email;
    @Past @NotNull
    private Date dayofbirth;
    @NotNull
    private Integer classid;
    
    private String classname;

    /**
     * @return the studentid
     */
    public Integer getStudentid()
    {
        return studentid;
    }

    /**
     * @param studentid the studentid to set
     */
    public void setStudentid(Integer studentid)
    {
        this.studentid = studentid;
    }

    /**
     * @return the username
     */
    public String getUsername()
    {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username)
    {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword()
    {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password)
    {
        this.password = password;
    }

    /**
     * @return the fullname
     */
    public String getFullname()
    {
        return fullname;
    }

    /**
     * @param fullname the fullname to set
     */
    public void setFullname(String fullname)
    {
        this.fullname = fullname;
    }

    /**
     * @return the sex
     */
    public String getSex()
    {
        return sex;
    }

    /**
     * @param sex the sex to set
     */
    public void setSex(String sex)
    {
        this.sex = sex;
    }
    
    public boolean isMale()
    {
        return sex.equals("nam");
    }

    /**
     * @return the email
     */
    public String getEmail()
    {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email)
    {
        this.email = email;
    }

    /**
     * @return the dayofbirth
     */
    public Date getDayofbirth()
    {
        return dayofbirth;
    }

    /**
     * @param dayofbirth the dayofbirth to set
     */
    public void setDayofbirth(Date dayofbirth)
    {
        this.dayofbirth = dayofbirth;
    }

    /**
     * @return the classid
     */
    public Integer getClassid()
    {
        return classid;
    }

    /**
     * @param classid the classid to set
     */
    public void setClassid(Integer classid)
    {
        this.classid = classid;
    }
    
    public String getClassname()
    { 
        return classname;
    }
    
    public void setClassname(String newValue)
    {
        classname = newValue;
    }
    
}
