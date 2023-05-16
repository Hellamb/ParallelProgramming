package lab3.university;

import com.google.common.base.Objects;

public class Student {
    private final String name;
    private final Group group;

    public Student(String name, Group group) {
        this.name = name;
        this.group = group;
    }

    public String name() {
        return name;
    }

    public Group group() {
        return group;
    }

    public enum Group {
        IT01, IT02, IT03
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equal(name, student.name) && group == student.group;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name, group);
    }

    @Override
    public String toString() {
        return name + " from " + group;
    }
}
