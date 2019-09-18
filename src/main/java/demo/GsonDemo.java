package demo;

import com.google.gson.Gson;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Yangzhe Xie
 * @date 17/9/19
 */
public class GsonDemo {
    
//    public static void main(String[] args) {
////        Gson gson = new Gson();
////
////        /* Convert Java object to JSON string */
////        Student student = new Student(1, "Demo");
////        System.out.println(gson.toJson(student));
////        /* Result: {"id":1,"name":"Demo"} */
////
////        /* Convert JSON string back to Java object */
////        String jsonStr = "{\"id\":2,\"name\":\"New Student\"}";
////        Student newStudent = gson.fromJson(jsonStr, Student.class);
////        System.out.println(newStudent.getName());
////        /* Result: New Student */
//
//    }
public static void main(String args[]) {
    JFrame f = new JFrame("JColorChooser Sample");
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    final JButton button = new JButton("Pick to Change Background");

    ActionListener actionListener = new ActionListener() {
        public void actionPerformed(ActionEvent actionEvent) {
            Color initialBackground = button.getBackground();
            Color background = JColorChooser.showDialog(null, "JColorChooser Sample", initialBackground);
            if (background != null) {
                button.setBackground(background);
            }
        }
    };
    button.addActionListener(actionListener);
    f.add(button, BorderLayout.CENTER);
    f.setSize(300, 200);
    f.setVisible(true);
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
