package lab3.university;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Set<Student> students = new HashSet<>();
        students.add(new Student("Vlad", Student.Group.IT01));
        students.add(new Student("Artem", Student.Group.IT02));
        students.add(new Student("Alex", Student.Group.IT03));
        students.add(new Student("Anton", Student.Group.IT01));
        students.add(new Student("Nikita", Student.Group.IT02));
        students.add(new Student("Masha", Student.Group.IT03));
        students.add(new Student("Oleg", Student.Group.IT01));
        students.add(new Student("Roma", Student.Group.IT02));
        students.add(new Student("Zhenya", Student.Group.IT03));
        int weeks = 10;

        GradeBook gradeBook = new GradeBook(students, weeks);

        Teacher assistant1 = new Teacher(gradeBook, weeks, Student.Group.IT01);
        Teacher assistant2 = new Teacher(gradeBook, weeks, Student.Group.IT02);
        Teacher assistant3 = new Teacher(gradeBook, weeks, Student.Group.IT03);
        Teacher lector = new Teacher(gradeBook, weeks);

        assistant1.start();
        assistant2.start();
        assistant3.start();
        lector.start();

        assistant1.join();
        assistant2.join();
        assistant3.join();
        lector.join();

        students.forEach(student -> System.out.println(student + " " + Arrays.toString(gradeBook.grades(student))));
    }
}
