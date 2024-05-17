import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class archivo {

    public static void main(String[] args) {
        String csvFile = "lista_alumnos.csv"; 
        String line;
        String csvSeparator = ",";
        List<Student> students = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue; 
                }
                String[] data = line.split(csvSeparator);
                if (data.length < 5) {
                    System.err.println("Línea inválida (esperados 5 campos, encontrados " + data.length + "): " + line);
                    continue;
                }
                try {
                    String name = data[0];
                    String lastName = data[1]; 
                    String gender = data[2]; 
                    int age = Integer.parseInt(data[3]);
                    double grade = Double.parseDouble(data[4]);
                    students.add(new Student(name + " " + lastName, age, grade));
                } catch (NumberFormatException e) {
                    System.err.println("Error al parsear la línea: " + line);
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (students.isEmpty()) {
            System.out.println("No se encontraron estudiantes en el archivo.");
            return;
        }

        for (Student student : students) {
            System.out.println(student);
        }

        System.out.println("Esta clase tiene " + students.size() + " estudiantes");

        double sumAge = 0;
        for (Student student : students) {
            sumAge += student.getAge();
        }
        int averageAge = (int) Math.round(sumAge / students.size());
        System.out.println("La edad media de los estudiantes es: " + averageAge);

        double sumGrade = 0;
        double maxGrade = Double.MIN_VALUE;
        double minGrade = Double.MAX_VALUE;
        for (Student student : students) {
            sumGrade += student.getGrade();
            maxGrade = Math.max(maxGrade, student.getGrade());
            minGrade = Math.min(minGrade, student.getGrade());
        }
        double averageGrade = sumGrade / students.size();
        System.out.println("La nota media de la clase es: " + String.format("%.2f", averageGrade));
        System.out.println("La nota máxima de la clase es: " + String.format("%.2f", maxGrade));
        System.out.println("La nota mínima de la clase es: " + String.format("%.2f", minGrade));

        String reportFile = "informe-clase.txt";
        try (PrintWriter writer = new PrintWriter(new FileWriter(reportFile))) {
            writer.println("Número de estudiantes: " + students.size());
            writer.println("Edad media: " + averageAge);
            writer.println("Nota media: " + String.format("%.2f", averageGrade));
            writer.println("Nota máxima: " + String.format("%.2f", maxGrade));
            writer.println("Nota mínima: " + String.format("%.2f", minGrade));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class Student {
        private String name;
        private int age;
        private double grade;

        public Student(String name, int age, double grade) {
            this.name = name;
            this.age = age;
            this.grade = grade;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }

        public double getGrade() {
            return grade;
        }

        @Override
        public String toString() {
            return "Student{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    ", grade=" + grade +
                    '}';
        }
    }
}
