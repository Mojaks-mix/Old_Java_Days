package Grading_System.model;

public class Grad {
    private int studentId;
    private int teacherID;
    private int courseId;
    private int firstMark;
    private int secondMark;
    private int finalMark;
    private String symbol;

    public Grad(){
        this(-1, -1, -1, -1, -1, -1, null);
    }

    public Grad(int studentId, int teacherID, int courseId, int firstMark, int secondMark, int finalMark, String symbol) {
        this.studentId = studentId;
        this.teacherID = teacherID;
        this.courseId = courseId;
        this.firstMark = firstMark;
        this.secondMark = secondMark;
        this.finalMark = finalMark;
        this.symbol = symbol;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(int teacherID) {
        this.teacherID = teacherID;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getFirstMark() {
        return firstMark;
    }

    public void setFirstMark(int firstMark) {
        this.firstMark = firstMark;
    }

    public int getSecondMark() {
        return secondMark;
    }

    public void setSecondMark(int secondMark) {
        this.secondMark = secondMark;
    }

    public int getFinalMark() {
        return finalMark;
    }

    public void setFinalMark(int finalMark) {
        this.finalMark = finalMark;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

}
