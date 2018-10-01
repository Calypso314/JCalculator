//##################################################--The Simple Calculator--#####################################################//

/**
 * First, some initial guide on how the code works:
 *
 * Program is written by extending the JFrame class, rather than creating an instance of it. I believe that for an app small like this,
 * there will be no any maintenance issues.
 *
 * Layout is constructed by using the GroupLayout layout manager.
 *
 * By pressing any of the numeric buttons, the value assigned to button is written on the screen. Exception is 0, where pressing
 * there can only be one zero on the screen, if there is nothing else but it on the screen (you cannot write 0000000000...), unless there is a
 * decimal separator after it.
 *
 * The operator button for changing the value to negative and back adds or removes the minus sign on the screen. See "negativeNum" action
 * listener for details on how this is done. When there is operator button pressed, this button also parses the first variable and clears
 * the screen.
 *
 *
 * The calculator is programmed to be capable of the following operations, division, multiplication, addition and subtraction.
 * And it is capable of what every calculator shall be capable of: chaining of operations. This means that user does not need to press
 * "equals" after the every operation, but instead can write the number, press the operator, then write the number, then press the operator,
 * and so on as long as user desires.
 *
 * Now, detailed explanation on how the process goes:
 *
 * The user enters the number. Then, they press on the operator button (-, +, *, /). Now, what happens after the every press on such button:
 *
 * If the screen is empty pressing on the operator button, assigns the value to the toggle variable ( for example, if the plus is pressed, then
 * the value of toggle is "+". The purpose of the toggle button is to notify the other buttons that any of the operator buttons have been pressed.
 * Then the appropriate action is taken, if the screen is empty, and the toggle value is empty, the number will be simply written on the
 * screen. But, if the toggle is not empty, the value will be parsed from the screen, assigned to the firstDouble variable, the screen will
 * be cleared, and the toggle button will also be cleared, to make it available for the further use, and to make code behave the way it is intended.
 *
 * First click on the operator button only sets the toggle and operation variable. Every other click also clicks the equals button, therefore
 * showing the result of the previous calculation, which now can be used as first variable (firstDouble). This makes the chaining of operations
 * possible.
 *
 * Every numeric button writes the value on the screen. Unless the operator button has been pressed, then the numeric button parses the
 * value from the screen prior to pressing the arithmetic operator button. That value is assigned to firstDouble.
 *
 * For detailed operation, see the comments written on the line 107 and below.
 *
 * Pressing on the equal button reads the value from the firstDouble and secondDouble, and then performs appropriate action, based on
 * "operator" variable, which gets its value from toggle variable. See the "equals" one the line 160 for detailed description.
 *
 * Zero is different from the other operators, because there are exceptions for it, for an example, there cannot be more than 0 zero on the
 * screen, unless they are separated by decimal point, therefore it requires different logic from oter buttons. See line 340 for details.
 *
 * So, without much further ado...
 */

import javax.swing.*;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class Calculator extends javax.swing.JFrame {  //With application small as this, extending JFrame is not gonna hurt.
    private double firstDouble = 0;
    private double secondDouble = 0;
    private double doubleResult = 0;
    private String toggle = "";//remembers the operation from the button (temporary)
    private String operation = "";//gets the operator from toggle, which is used for calculation (see "equals" down below).
    private Color screenGreen = new Color(220,252,200);
    private Color panelColor = new Color(43, 50, 56);

    private Calculator() {

        initComponents();
    }

    private void initComponents() {
//object creation and variable declaration
        JTextField screen = new javax.swing.JTextField("0");
        JButton negativeNum = new javax.swing.JButton("+/-");
        JButton division = new javax.swing.JButton("/");
        JButton multiply = new javax.swing.JButton("x");
        JButton equals = new javax.swing.JButton("=");
        JButton seven = new javax.swing.JButton("7");
        JButton eight = new javax.swing.JButton("8");
        JButton nine = new javax.swing.JButton("9");
        JButton minus = new javax.swing.JButton("—");
        JButton four = new javax.swing.JButton("4");
        JButton five = new javax.swing.JButton("5");
        JButton six = new javax.swing.JButton("6");
        JButton plus = new javax.swing.JButton("+");
        JButton three = new javax.swing.JButton("3");
        JButton one = new javax.swing.JButton("1");
        JButton two = new javax.swing.JButton("2");
        JButton zero = new javax.swing.JButton("0");
        JButton decSeparator = new javax.swing.JButton(".");
        JButton clear = new javax.swing.JButton("Clear");

        JButton[] buttons = {zero, one, two, three, four, five, six, seven, eight, nine, equals, negativeNum, division, multiply, plus, minus, decSeparator};
        String[] operands = {"/", "*", "+", "-"};
        JButton[] numButtons = {one, two, three, four, five, six, seven, eight, nine};
        JButton[] opButtons = {division, multiply, plus, minus};

        for (JButton button: buttons) {
            button.setFont(new java.awt.Font("Trebuchet", Font.BOLD, 18));
            button.setFocusable(false);
        }

        for (JButton a: numButtons) {  // adding the action listener to all numeric buttons, except zero. Zero has unique ActionListener assigned. See line: 341.
            a.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!toggle.equals("")) {
                        firstDouble = Double.parseDouble(screen.getText());
                        toggle = "";
                        screen.setText("");
                        screen.setText(screen.getText() + Integer.toString(java.util.Arrays.asList(numButtons).indexOf(a) +1)); // + 1 is necessary, because as we need to write 1 on the screen, the array starts
                    }                                                                                                             // at index 0, therefore 0 would be written on the screen when you press 1.
                    else if (screen.getText().equals("0")){                                                                       // incrementing everything by 1 fixes that.
                        screen.setText("");
                        screen.setText(screen.getText() + Integer.toString(java.util.Arrays.asList(numButtons).indexOf(a) + 1));
                    }
                    else {
                        screen.setText(screen.getText() + Integer.toString(java.util.Arrays.asList(numButtons).indexOf(a) + 1));
                    } 
                  } // INCREMENTED INDEX OF ELEMENTS OF numButtons ARRAY IS USED AS VALUE OF THE BUTTON CLICKED
            });

        }
        for (JButton a: opButtons) {
            a.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {   // ActionListener for plus, minus, multiply and divide buttons.
                    if (!operation.equals("") && firstDouble != 0) {
                        equals.doClick();
                        toggle = operands[java.util.Arrays.asList(opButtons).indexOf(a)];
                        operation = operands[java.util.Arrays.asList(opButtons).indexOf(a)];
                    } else {
                        toggle = operands[java.util.Arrays.asList(opButtons).indexOf(a)];
                        operation = operands[java.util.Arrays.asList(opButtons).indexOf(a)];
                    }
                }    // FOR EVERY OPERATION BUTTON FROM opButton ARRAY, THERE IS STRING VALUE IN operands ARRAY
            });     //  INDEX OF EVERY ITEM FROM FROM opButtons CORRESPONDS TO THE INDEX OF operands ITEM
        }

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Calculator");
        setResizable(false);
        getContentPane().setBackground(panelColor);

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        /* Equals button performs the calculations based from value of firstDouble, secondDouble and "operation" variable
*//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        equals.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "Equals");
        equals.getActionMap().put("Equals", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                equals.doClick();
            }
        });
        equals.addActionListener(e -> {
            if (!toggle.equals("")) {
                firstDouble = Double.parseDouble(screen.getText());
            }
            else {
                secondDouble = Double.parseDouble(screen.getText());
            }

            switch (operation) {

                case "+":
                    doubleResult = firstDouble + secondDouble;
                    operation = "";
                    toggle = "";
                    screen.setText("" + doubleResult);
                    firstDouble = 0;
                    break;
                case "-":
                    doubleResult = firstDouble - secondDouble;
                    operation = "";
                    toggle = "";
                    screen.setText("" + doubleResult);
                    firstDouble = 0;
                    break;
                case "*":
                    doubleResult = firstDouble * secondDouble;
                    operation = "";
                    toggle = "";
                    screen.setText("" + doubleResult);
                    firstDouble = 0;
                    break;
                case "/":
                    doubleResult = firstDouble / secondDouble;
                    operation = "";
                    toggle = "";
                    screen.setText("" + doubleResult);
                    firstDouble = 0;
                    break;

                default:
                    screen.setText(screen.getText() + "");
            }
////////////////////////////////////////////////////In addition////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            if (doubleResult % 1 == 0 && doubleResult != 0 ) {
                screen.setText("" + Math.round(doubleResult)); // Why display the result with decimal separator if there isn't truly anything behind it?
            }                                                 // This takes care of that! 1 looks better than 1.0. Second condition serves to not break the default switch statement.

            else if (screen.getText().equals("0.0") && doubleResult == 0) { //condition above only works with number greater or lower than 0, hence this.
                screen.setText("0");
            }
            if (screen.getText().equals("Infinity") || screen.getText().equals("NaN")) {  // sets pop-up warning dialogue, to notify user that division by zero is not allowed.
                screen.setText("0");
                JOptionPane.showMessageDialog(Calculator.this, "Division by zero is not allowed!", "Error", JOptionPane.ERROR_MESSAGE);

            }
        });
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        screen.setFont(new java.awt.Font("DS-Digital", Font.ITALIC, 34));
        screen.setBackground(screenGreen);
        screen.setEditable(false);
        screen.setCaretColor(screenGreen); // makes cursor/caret "invisible"
        screen.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) { //ignores writing from keyboard to JTextField.
                    e.consume();
            }
        });
        screen.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("BACK_SPACE"), "Clear");
        screen.getActionMap().put("Clear", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clear.doClick();
            }
        });

        //sets the value negative or reverts it back to positive.
        negativeNum.addActionListener(e -> {
            if (screen.getText().contains("-")) {
                screen.setText(screen.getText().replace("-", "")); // If there is a "-" sign on screen, it removes it.
            } else if (screen.getText().equals("0")) { // if there is only 0 on the screen, nothing is added nor removed
                screen.setText("0");
            } else if (!screen.getText().equals("")) {
                screen.setText("-" + screen.getText());  //If there isn't one, it is added by the press on this button.
            }
        });

        division.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_SLASH, 0), "Divide");
        division.getActionMap().put("Divide", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                division.doClick();
            }
        });
        multiply.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ASTERISK, 0), "Multiply");
        multiply.getActionMap().put("Multiply", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                multiply.doClick();
            }
        });
        seven.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD7, 0), "Seven");
        seven.getActionMap().put("Seven", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                seven.doClick();
            }
        });
        eight.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD8, 0), "Eight");
        eight.getActionMap().put("Eight", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eight.doClick();
            }
        });
        nine.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD9, 0), "Nine");
        nine.getActionMap().put("Nine", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nine.doClick();
            }
        });
        minus.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, 0), "Minus");
        minus.getActionMap().put("Minus", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                minus.doClick();
            }
        });
        four.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD4, 0), "Four");
        four.getActionMap().put("Four", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                four.doClick();
            }
        });
        five.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD5, 0), "Five");
        five.getActionMap().put("Five", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                five.doClick();
            }
        });
        six.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD6, 0), "Six");
        six.getActionMap().put("Six", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                six.doClick();
            }
        });
        plus.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_PLUS, 0), "Plus");
        plus.getActionMap().put("Plus", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                plus.doClick();
            }
        });
        three.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD3, 0), "Three");
        three.getActionMap().put("Three", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                three.doClick();
            }
        });
        one.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD1, 0), "One");
        one.getActionMap().put("One", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                one.doClick();
            }
        });
        two.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD2, 0), "Two");
        two.getActionMap().put("Two", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                two.doClick();
            }
        });
        zero.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD0, 0), "Null");
        zero.getActionMap().put("Null", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                zero.doClick();
            }
        });


        zero.addActionListener(e -> {

            if (!toggle.equals("")) {  //assigns the operation from "toggle" to the operator value
                firstDouble = Double.parseDouble(screen.getText());
                toggle = "";  //resets the operator button
                screen.setText("0");
            }
            else if (screen.getText().contains(".")) {
                screen.setText(screen.getText() + 0);
            }
            else if (!screen.getText().equals("0")) {
                screen.setText(screen.getText() + "0");
            }
            else  {
                screen.setText("");
                screen.setText("0");  //Only one zero can be on the screen if the zero is the first number, without decimal separator.
            }
        });

        decSeparator.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_PERIOD, 0), "Decimal");
        decSeparator.getActionMap().put("Decimal", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                decSeparator.doClick();
            }
        });
        decSeparator.addActionListener(e -> {
            if (!screen.getText().contains(".") && !screen.getText().equals("")) {
                screen.setText(screen.getText() + "."); // checks if there isn't decimal separator in the screen and if the screen isn't empty.
            }                                           // in case both conditions are true, the dec. separator is set on the screen
        });

        //sets 0 to the screen, sets the toggle and operation variable to empty.
        clear.setFocusable(false);
        clear.addActionListener(e -> {
            screen.setText("0");
            operation = "";
            toggle = "";
        });


        //creating app's layout, horizontally and vertically
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                        .addGroup(layout.createSequentialGroup()
                                                                                .addComponent(four, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                .addComponent(five, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                        .addGroup(layout.createSequentialGroup()
                                                                                .addComponent(one, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                                .addComponent(two, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                        .addComponent(zero, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                                        .addComponent(six, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(three, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(decSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(seven, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(eight, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(nine, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(plus, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(minus, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addComponent(screen)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(negativeNum, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(division, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(multiply, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(clear, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                                        .addComponent(equals, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(screen, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(9, 9, 9)
                                .addComponent(clear)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(negativeNum, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(division, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(multiply, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(equals, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(seven, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(eight, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(nine, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(four, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(five, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(six, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addComponent(minus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(one, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(three, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(two, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(decSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(zero, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addComponent(plus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap(8, Short.MAX_VALUE))
        );

        pack();
    }


    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Calculator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Calculator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Calculator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Calculator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }


        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {

                    new Calculator().setVisible(true);
                }
            });
    }
}
