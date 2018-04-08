
package testHarness;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import customDatatypes.Marks;
import offerings.CourseOffering;
import offerings.ICourseOffering;
import offerings.OfferingFactory;
import registrar.ModelRegister;
import systemUsers.InstructorModel;
import systemUsers.StudentModel;

public class TestStudentModelFactory_1 {

//	public static void main(String[] args) throws IOException{
//		// TODO Auto-generated method stub
//		SystemUserModelFactory factory = new StudentModelFactory();
//		BufferedReader br = new BufferedReader(new FileReader(new File("note_1.txt")));
//		factory.createSystemUserModel(br, null);
//	}

	public static void main(String[] args) throws IOException{
//		Create an instance of an OfferingFactory
		OfferingFactory factory = new OfferingFactory();

//		Use the factory to populate as many instances of courses as many files we've got.
        BufferedReader br = new BufferedReader(new FileReader(new File("note_1.txt")));
        CourseOffering	courseOffering = factory.createCourseOffering(br);
		br.close();

//		Loading 1 file at a time
		br = new BufferedReader(new FileReader(new File("note_2.txt")));
//		here we have only two files
		courseOffering = factory.createCourseOffering(br);
		br.close();

//		code to perform sanity checking of all our code
//		by printing all of the data that we've loaded
		for(CourseOffering course : ModelRegister.getInstance().getAllCourses()){
			System.out.println("ID : " + course.getCourseID() + "\nCourse name : " + course.getCourseName() + "\nSemester : " +
			course.getSemester());
//			System.out.println("Students allowed to enroll\n");
			for(StudentModel student : course.getStudentsAllowedToEnroll()){
//				System.out.println("Student name : " + student.getName() + "\nStudent surname : " + student.getSurname() +
//						"\nStudent ID : " + student.getID() + "\nStudent EvaluationType : " +
//						student.getEvaluationEntities().get(course) + "\n\n");
                if(student.getID().equals("1264")){
                    if(course.getCourseID().equals("CS2212B")){
                        Marks m1 = new Marks();
                        m1.addToEvalStrategy("Final",2.22);
                        Hashtable<ICourseOffering,Marks> markPackage = new Hashtable<>();
                        markPackage.put(course,m1);

                        Marks m2 = new Marks();
                        m2.addToEvalStrategy("Assignment", 88.9);
                        markPackage.put(course,m2);

                        student.setPerCourseMarks(markPackage);
                    }


                    System.out.println("Student " + student.getID() + student.getName());
                    if(student.getPerCourseMarks() != null){
                        System.out.println("Course Marks: " + student.getPerCourseMarks().get(course));
                        Marks m;
                        m = student.getPerCourseMarks().get(course);
                        System.out.println("Course Marks: " + m.getValueWithKey("Assignment"));

                    }
                    else
                        System.out.println("no marks");
                }



            }


//			for(StudentModel student : course.getStudentsAllowedToEnroll()){
//				for(ICourseOffering course2 : student.getCoursesAllowed())
//				System.out.println(student.getName() + "\t\t -> " + course2.getCourseName());
//			}
            System.out.println("--------------");
        }


    }
}
