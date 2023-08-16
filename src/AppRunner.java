//Badams Officiel


import javax.swing.*;

public class AppRunner {
    public static void main(String[] CHAND){
        Calculator calculator = new Calculator(450, 600);
        calculator.setTitle("Badams Calc");
        calculator.getContentPane().setBackground(Helper.LIGHT_BLUE);
        calculator.setLocationRelativeTo(null);
        calculator.setIcon("assets/badamsIcon.png");
        calculator.setVisible(true);
    }
}
