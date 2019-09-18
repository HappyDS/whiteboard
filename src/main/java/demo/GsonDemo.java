package demo;

import com.google.gson.Gson;

/**
 * @author Yangzhe Xie
 * @date 17/9/19
 */
public class GsonDemo {
    
    public static void main(String[] args) {
        Gson gson = new Gson();

        /* Convert Java object to JSON string */
        Student student = new Student(1, "Demo");
        System.out.println(gson.toJson(student));
        /* Result: {"id":1,"name":"Demo"} */

        /* Convert JSON string back to Java object */
        String jsonStr = "{\"id\":2,\"name\":\"New Student\"}";
        Student newStudent = gson.fromJson(jsonStr, Student.class);
        System.out.println(newStudent.getName());
        /* Result: New Student */
    }
    
    private static class Student {
        private int id;
        private String name;

        public Student(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
