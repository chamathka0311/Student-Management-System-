package cw;

public class Student {
    private String stdID;
    private String stdName;

    public Student (String stdID, String stdName) {
        this.stdID = stdID;
        this.stdName = stdName;
    }

    public String getStdID() {
        return stdID;
    }

    public void setStdID(String stdID) {
        this.stdID = stdID;
    }

    public String getStdName() {
        return stdName;
    }

    public void setStdName(String stdName) {
        this.stdName = stdName;
    }


}
