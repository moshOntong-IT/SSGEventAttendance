package org.codx.Model;

public class Student extends UserCredentials { // subclass  and superclass

    private long studentID;
    private String fName;
    private String mName;
    private String lName;
    private int age;
    private String gender;
    private String email;
    private String phoneNumber;
    private String section;
    private Department department;
    private String schoolName;
    private String schoolYear;


    public  Student (){

    }
    public Student(long userID, String password, long studentID,
                   String fName, String mName, String lName,
                   int age, String gender, String email, String phoneNumber,
                   String section, Department department, String schoolName, String schoolYear) {
        super(userID,password);
        this.studentID = studentID;
        this.fName = fName;
        this.mName = mName;
        this.lName = lName;
        this.age = age;
        this.gender = gender;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.section = section;
        this.department = department;
        this.schoolName = schoolName;
        this.schoolYear = schoolYear;
    }

    public long getStudentID() {
        return studentID;
    }

    public void setStudentID(long studentID) {
        this.studentID = studentID;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(String schoolYear) {
        this.schoolYear = schoolYear;
    }
}
