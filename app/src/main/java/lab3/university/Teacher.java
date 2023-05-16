package lab3.university;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public class Teacher extends Thread {

    private final GradeBook gradeBook;
    private final List<Student> students;
    private final Optional<Student.Group> group;
    private final Random random = new Random();
    private final int weeks;

    public Teacher(GradeBook gradeBook, int weeks, Student.Group group) {
        this.gradeBook = gradeBook;
        this.group = Optional.of(group);
        this.weeks = weeks;
        students = gradeBook
                .students()
                .stream()
                .toList();
    }

    public Teacher(GradeBook gradeBook, int weeks) {
        this.gradeBook = gradeBook;
        this.group = Optional.empty();
        this.weeks = weeks;
        students = gradeBook
                .students()
                .stream()
                .toList();
    }

    @Override
    public void run() {
        for (int i = 0; i < weeks; i++) {
            var week = i;
            students.stream()
                    .filter(student -> group.isEmpty() || student.group() == group.get())
                    .forEach(student -> gradeBook.setGrade(student, week, random.nextInt(100)+1));
        }
    }
}
