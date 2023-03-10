import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Test {

    public static void main(String[] args) throws FileNotFoundException{

        Catalog catalog = Catalog.getCatalog();
        ScoreVisitor visitor = new ScoreVisitor();

        JSONParser jsonParser = new JSONParser();

        Vector<String> pathnames = new Vector<>();
        pathnames.add("catalog.json");

        try {

            Object obj = jsonParser.parse(new FileReader(pathnames.get(0)));
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray coursesArray = (JSONArray) jsonObject.get("courses");
            Iterator coursesIterator = coursesArray.iterator();

            // CITIM CURSURILE

            while (coursesIterator.hasNext()) {
                JSONObject course = (JSONObject) coursesIterator.next();

                TreeSet<Assistant> assistantTreeSet = new TreeSet<>();
                HashMap<String, Group> groupHashMap = new HashMap<>();

                int credits;
                Number number = (Number) course.get("credits");
                credits = number.intValue();

                String courseType = (String) course.get("type");
                String strategyName = (String) course.get("strategy");
                String name = (String) course.get("name");

                JSONObject teacher = (JSONObject) course.get("teacher");

                String teacherFirstName = (String) teacher.get("firstName");
                String teacherLastName = (String) teacher.get("lastName");

                Teacher T = (Teacher) UserFactory.getUser("Teacher", teacherFirstName, teacherLastName);

                Object assistantsArrayObject = course.get("assistants");
                JSONArray assistantsArray = (JSONArray) assistantsArrayObject;

                Iterator assistantsIterator = assistantsArray.iterator();


                while (assistantsIterator.hasNext()) {

                    JSONObject assistant = (JSONObject) assistantsIterator.next();
                    String assistantFirstName = (String) assistant.get("firstName");
                    String assistantLastName = (String) assistant.get("lastName");
                    Assistant A = (Assistant) UserFactory.getUser("Assistant", assistantFirstName, assistantLastName);
                    assistantTreeSet.add(A);

                }

                JSONArray groupsArray = (JSONArray) course.get("groups");
                Iterator groupsIterator = groupsArray.iterator();

                while (groupsIterator.hasNext()) {

                    JSONObject group = (JSONObject) groupsIterator.next();
                    String ID = (String) group.get("ID");

                    JSONObject assistant = (JSONObject) group.get("assistant");
                    String assistantFirstName = (String) assistant.get("firstName");
                    String assistantLastName = (String) assistant.get("lastName");

                    // PASTRAM O INSTANTA UNICA A ASISTENTILOR
                    Assistant A = null;

                    for (Assistant assistant1 : assistantTreeSet) {
                        if (assistant1.getFirstName().equals(assistantFirstName) &&
                                assistant1.getLastName().equals(assistantLastName))
                            A = assistant1;
                    }

                    if (A == null) {
                        A = (Assistant) UserFactory.getUser("Assistant", assistantFirstName, assistantLastName);
                    }
                    Group G = new Group(ID, A);

                    JSONArray studentsArray = (JSONArray) group.get("students");
                    Iterator studentIterator = studentsArray.iterator();

                    while (studentIterator.hasNext()) {

                        // CREAM STUDENTUL SI IL ADAUGAM IN GRUPA

                        JSONObject student = (JSONObject) studentIterator.next();

                        String studentFirstName = (String) student.get("firstName");
                        String studentLastName = (String) student.get("lastName");

                        // DORIM SA ADAUGAM ACEEASI INSTANTA A ELEVULUI
                        // DACA A FOST DEJA CREAT

                        Student S;
                        if (catalog.findStudent(studentFirstName, studentLastName) != null)
                            S = catalog.findStudent(studentFirstName, studentLastName);
                        else
                            S = (Student) UserFactory.getUser("Student", studentFirstName, studentLastName);

                        if (S.getMother() == null && S.getFather() == null) {
                            JSONObject mother = (JSONObject) student.get("mother");
                            JSONObject father = (JSONObject) student.get("father");

                            String motherFirstName, motherLastName;
                            String fatherFirstName, fartherLastName;

                            if (mother != null) {
                                motherFirstName = (String) mother.get("firstName");
                                motherLastName = (String) mother.get("lastName");
                            } else {
                                motherFirstName = "NU EXISTA DATE";
                                motherLastName = "NU EXISTA DATE";
                            }
                            if (father != null) {
                                fatherFirstName = (String) father.get("firstName");
                                fartherLastName = (String) father.get("lastName");
                            } else {
                                fartherLastName = "NU EXISTA DATE";
                                fatherFirstName = "NU EXISTA DATE";
                            }

                            // CREAM PARINTII SI II ABONAM LA CATALOG

                            Parent M = (Parent) UserFactory.getUser("Parent", motherFirstName, motherLastName);
                            Parent F = (Parent) UserFactory.getUser("Parent", fatherFirstName, fartherLastName);

                            S.setFather(F);
                            S.setMother(M);

                            catalog.addObserver(F);
                            catalog.addObserver(M);
                        }
                        G.add(S);
                    }

                    groupHashMap.put(ID, G);

                }

                // CREAM STRATEGIA

                Strategy strategy;
                if (strategyName.equals("BestPartialScore"))
                    strategy = new BestPartialScore();
                else if (strategyName.equals("BestExamScore"))
                    strategy = new BestExamScore();
                else strategy = new BestTotalScore();

                Course C;

                if (courseType.equals("FullCourse")) {
                    C = (FullCourse) new FullCourse.FullCourseBuilder().name(name).groups(groupHashMap).assistants(assistantTreeSet).build();
                } else {
                    C = (PartialCourse) new PartialCourse.PartialCourseBuilder().name(name).groups(groupHashMap).assistants(assistantTreeSet).build();
                }
                // NU SE FOLOSESC TOATE CAMPURILE BUILDER PENTRU O MAI BUNA LIZIBILITATE

                C.setTitular(T);
                C.setCredits(credits);
                C.setStrategy(strategy);

                catalog.addCourse(C);

            }

            // ADAUGAM NOTELE DIN EXAMEN

            JSONArray examScoresArray = (JSONArray) jsonObject.get("examScores");
            Iterator examScoresIterator = examScoresArray.iterator();

            while (examScoresIterator.hasNext()) {

                JSONObject examScore = (JSONObject) examScoresIterator.next();

                JSONObject student = (JSONObject) examScore.get("student");
                String studentFirstName = (String) student.get("firstName");
                String studentLastName = (String) student.get("lastName");

                String courseName = (String) examScore.get("course");
                Double score = ((Number) examScore.get("grade")).doubleValue();

                Teacher T = catalog.findTeacher(courseName);
                Student S = catalog.findStudent(studentFirstName, studentLastName, courseName);

                visitor.addGrade(score, T, S, courseName);

            }

            // ADAUGAM NOTELE DE PE PARCURS

            JSONArray partialScoresArray = (JSONArray) jsonObject.get("partialScores");
            Iterator partialScoresIterator = partialScoresArray.iterator();

            while (partialScoresIterator.hasNext()) {

                JSONObject partialScore = (JSONObject) partialScoresIterator.next();

                JSONObject assistant = (JSONObject) partialScore.get("assistant");
                if (assistant == null)
                    return;

                String assistantFirstName = (String) assistant.get("firstName");
                String assistantLastName = (String) assistant.get("lastName");

                JSONObject student = (JSONObject) partialScore.get("student");
                String studentFirstName = (String) student.get("firstName");
                String studentLastName = (String) student.get("lastName");

                String courseName = (String) partialScore.get("course");
                Double score = ((Number) partialScore.get("grade")).doubleValue();

                Assistant A = catalog.findAssistant(assistantFirstName, assistantLastName, courseName);
                Student S = catalog.findStudent(studentFirstName, studentLastName, courseName);
                visitor.addGrade(score, A, S, courseName);

            }

        } catch (IOException | ParseException e ){
            throw new RuntimeException(e);
        }
        // TESTARE

        // I INTERFATA GRAFICA

        String courseName = "Programare Orientata pe Obiecte";


        Student S1 = catalog.findStudent("Sebastian", "Moisescu",courseName);
        Teacher T1 = catalog.findTeacher(courseName);
        Assistant A1 = S1.getAssistant(courseName);
        Parent P1 = S1.getFather();

        new StudentPage("STUDENT PAGE", S1);
        new TeacherAssistantPage("TEACHER PAGE", T1, visitor);
        new TeacherAssistantPage("ASSISTANT PAGE", A1, visitor);
        new ParentPage("PARENT PAGE", P1);

        /*
       // Pentru vizualizarea mai multor pagini
        String courseName2 = "Paradigme de programare";
        Teacher T2 = catalog.findTeacher(courseName2);
        Assistant A2 = S1.getAssistant(courseName2);
        Parent P2 = S1.getMother();

        new TeacherAssistantPage("TEACHER PAGE", T2, visitor);
        new TeacherAssistantPage("ASSISTANT PAGE", A2, visitor);
        new ParentPage("PARENT PAGE", P2);
        */

        // II STRATEGY DESIGN PATTERN

        String courseName3 = "Sisteme de operare";
        Teacher T3 = catalog.findTeacher(courseName3);

        // VALIDAM NOTELE

        T3.accept(visitor);
        for(Assistant assistant : catalog.getCourse(courseName3).getAssistants())
            assistant.accept(visitor);

        Student bestStudent = catalog.getCourse(courseName3).getBestStudent();

        System.out.println("CEL MAI BUN STUDENT DE LA CURSUL " + courseName3 + " este: " + bestStudent);


        // III MEMENTO DESIGN PATTERN

        // VOM LUCRA TOT CU NOTELE VALIDATE ANTERIOR
        // LA CURSUL SISTEM DE OPERARE, SE FACE BACK-UP
        // SE ADAUGA UN STUDENT NOU, SE FACE UNDO PENTRU
        // A RESTAURA CATALOGUL LA SO

        Course SO = catalog.getCourse(courseName3);

        SO.makeBackup();
        Student newStudent = new Student("Cerasela", "Soare");
        SO.addStudent("313CC",newStudent);

        SO.addGrade(new Grade(5d,5d, newStudent, "Sisteme de operare"));

        System.out.println("CATALOG ACTUAL: \n" + SO.getGrades());

        SO.undo();


       System.out.println("\n\n\nCATALOGUL INAINTE DE TRANSFERAREA UNUI STUDENT\n" + SO.getGrades());
    }
}