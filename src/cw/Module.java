package cw;

public class Module {
    private double mark;

    public Module(double mark) {
        this.mark = mark;
    }

    public double getMark() {
        return mark;
    }

    public void setMark(double mark) {
        this.mark = mark;
    }

    public static double calcAvg(double[] moduleMarks) {
        double total = 0;
        for (double mark : moduleMarks) {
            total += mark;
        }
        return (total/3);
    }

    public static String moduleGrade(double average) {
        String grade;
        if(average >= 80) {
            grade = "Distinction";
        }
        else if(average >= 70) {
            grade = "Merit";
        }
        else if(average >= 40) {
            grade = "Pass";
        }
        else {
            grade = "Fail";
        }
        return grade;
    }

}
