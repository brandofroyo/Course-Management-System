import customDatatypes.Marks;
import customDatatypes.NotificationTypes;
import offerings.CourseOffering;
import offerings.OfferingFactory;
import registrar.ModelRegister;
import systemUsers.InstructorModel;
import systemUsers.StudentModel;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Operations {
    private ModelRegister register;
    private List<CourseOffering> coursesOffered;


    public void init(){
        this.register = ModelRegister.getInstance();
        this.coursesOffered = this.register.getAllCourses();
    }

    public void createCourses() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
//            System.out.print("\n\tGive filename: ");
//            String line = reader.readLine();
//            String[] lineSplit = line.split(" ");

//            for (int i = 0; i <= lineSplit.length; i++) {
                buildCourseOffering("note_1.txt");
                buildCourseOffering("note_2.txt");
//            }
        }
        catch (IOException e) {
            e.printStackTrace();
            System.out.println(e + "exception thrown at createCourses");
        }

    }

    /**
     * helper function that builds the courses
     * @param file filename given by user
     * @throws IOException
     */
    private void buildCourseOffering(String file) throws IOException {
        OfferingFactory factory = new OfferingFactory();
        BufferedReader br = new BufferedReader(new FileReader(new File(file)));
        CourseOffering courseOffering = factory.createCourseOffering(br);
        br.close();
    }

    public void calcFinalGrade(String id){

    }

    /**
     * Print class record.
     * @param courseName name of course user wants
     * @param id user's ID
     */
    public void printClassRecord(String courseName, String id) {
        System.out.println();
        for (CourseOffering course : ModelRegister.getInstance().getAllCourses()) {
            //if course is requested;
            if(course.getCourseID().equals(courseName)) {
                for(InstructorModel instructor : course.getInstructor()){
                    //print only logged in profs class
                    if(instructor.getID().equals(id))
                        System.out.println("Course ID: " + course.getCourseID() +
                                "\nCourse name: " + course.getCourseName() +
                                "\nSemester: " + course.getSemester() +
                                "\nInstructor: " + course.getInstructor());

                    for(InstructorModel teach : course.getInstructor()) {
                        System.out.println("Instructor: " + teach.getName() + " " + teach.getSurname() +
                                "\nInstructor ID: " + teach.getID());
                    }


                    System.out.println("\nStudents allowed to enroll:");
                    for (StudentModel student : course.getStudentsAllowedToEnroll()) {
                        System.out.println("Student name: " + student.getName() + " " + student.getSurname() +
                                "\nStudent ID: " + student.getID() +
                                "\nStudent EvaluationType: " + student.getEvaluationEntities().get(course) + "\n\n");
                    }

                }
            }

        }

    }

    /**Add notification preferences.
     *
     * @param courseName get the course id
     * @param id user's id
     */
    public void setNotification(String courseName,String id){
        Scanner input = new Scanner(System.in);
        System.out.print("\n\tGive Notification Type (\"EMAIL\", \"PHONE\", \"MAIL\": ");
        String line = input.next();

        CourseOffering course = this.register.getRegisteredCourse(courseName);
        for (StudentModel student : course.getStudentsAllowedToEnroll()) {
            if(student.getID().equals(id)){
                switch (line.toUpperCase()){
                    case "EMAIL": student.setNotificationType(NotificationTypes.EMAIL);
                        System.out.println("You should receive emails from now on, unless it gets lost.");
                        break;
                    case "PHONE": student.setNotificationType(NotificationTypes.CELLPHONE);
                        System.out.println("Ring ring.");
                        break;
                    case "PIGEON": student.setNotificationType(NotificationTypes.PIGEON_POST);
                        System.out.println("1957 called. Pigeon's are no longer used.");
                        break;
                    case "MAIL": student.setNotificationType(NotificationTypes.PIGEON_POST);
                        System.out.println("We are licking the stamps. Expect slobbery mail.");
                        break;
                    default:
                        System.out.println("Invalid selection. Process aborted and returning to main.");

                }
            }
        }
    }

    /**
     * Print course record
     * @param courseName get the course id
     * @param sID user's id
     */
    public void printStudentCourse(String courseName, String sID){

        CourseOffering course = this.register.getRegisteredCourse(courseName);
        for (StudentModel student : course.getStudentsAllowedToEnroll()) {
            if(student.getID().equals(sID))
                System.out.println("\nCourse ID: " + course.getCourseID() +
                        "\nCourse Name: " + course.getCourseName() + course.getSemester() +
                        "\nCourse Instructor: " + course.getInstructor() +
                        "\nStudent Name: " + student.getName() + " " + student.getSurname() +
                        "\nStudent ID: " + student.getID() +
                        "\nStudent EvaluationType: " + student.getEvaluationEntities().get(course) );
            //+ "\nCourse Marks: " + student.getPerCourseMarks().get(course));
        }
    }

}


