public class StudentInfo {
    public static void main(String[] args)
    {
      Student student1 = new Student("Khalid", 21, 3.236);
      Student student2 = new Student("Shimanto", 21, 3.563);
      Student student3 = new Student("Raju", 21, 3.53);
        System.out.println(student1.name);
        System.out.println(student1.age);
        System.out.println(student1.gpa);
        System.out.println(student1.isEnroll);

        System.out.println(student2.name);
        System.out.println(student2.age);
        System.out.println(student2.name);
        System.out.println(student2.isEnroll);

        System.out.println(student3.name);
        System.out.println(student3.age);
        System.out.println(student3.name);
        System.out.println(student3.isEnroll);

        student1.study();
        student2.study();
        student3.study();


    }
}
