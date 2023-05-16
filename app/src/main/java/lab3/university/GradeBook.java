package lab3.university;

import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.toMap;

public class GradeBook {

    private final Map<Student, int[]> gradebook;

    public GradeBook(Set<Student> students, int weeks) {
        gradebook = students
                .stream()
                .collect(toMap(student -> student, student -> new int[weeks]));
    }

    public void setGrade(Student student, int week, int grade) {
        synchronized (gradebook.get(student)) {
            if (gradebook.get(student)[week] == 0) {
                gradebook.get(student)[week] = grade;
                System.out.println("Student " + student + " received: " + grade + " at week: " + week);
            } else {
                System.out.println("Student " + student + " already have a grade at week: " + week);
            }
        }
    }

    public int[] grades(Student student) {
        return gradebook.get(student);
    }

    public Set<Student> students() {
        return gradebook.keySet();
    }
}
