public class Student {
    //Constructor = A constructor is a special method to initialize object
    //You can pass an argument to a constructor and set up initial value

    
    String name = "Khalid" ;
    int age ;
    double gpa;
    boolean isEnroll;
    Student(String name, int age, double gpa)
    {
      this.name = name;
      this.age = age;
      this.gpa = gpa;
      this.isEnroll = true;
    }
    void study()
    {
        System.out.println(this.name + " is studying");

    }


}
