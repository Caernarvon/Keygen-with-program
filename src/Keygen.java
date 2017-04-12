import javax.swing.*;
import java.awt.*;
import java.util.prefs.Preferences;
/*
 * Keygen generates key depending on chosen radiobutton and entered name.
 * Anyone new generated key is always not similar with previous key.
 * Several letters are generated in a certain range, they're used later in program activation form.
*/
public class Keygen extends JFrame{
    Keygen(){
        // initializing form and components
        JFrame formActivation = new JFrame("KEYGEN");
        formActivation.setBounds(170, 250, 170, 250);
        formActivation.setLayout(new BorderLayout());
        formActivation.setDefaultCloseOperation(EXIT_ON_CLOSE);
        JTextArea keyArea = new JTextArea(3, 10);
        keyArea.setText("Enter your name here");
        JRadioButton radioButton = new JRadioButton("Low");
        JRadioButton radioButton1 = new JRadioButton("Mid");
        JRadioButton radioButton2 = new JRadioButton("High");
        JButton buttonActivate = new JButton("GENERATE");
        formActivation.add(keyArea, BorderLayout.NORTH);
        formActivation.add(radioButton, BorderLayout.CENTER);
        formActivation.add(buttonActivate, BorderLayout.AFTER_LINE_ENDS);
        final JPanel content = new JPanel(new GridLayout(3, 3, 3, 3));
        content.add(radioButton, BorderLayout.PAGE_START);
        content.add(radioButton1, BorderLayout.CENTER);
        content.add(radioButton2, BorderLayout.PAGE_END);
        formActivation.add(content, BorderLayout.CENTER);
        formActivation.setVisible(true);
        radioButton.setSelected(true);

        // Key generator algorithm with different range to different radiobutton
        buttonActivate.addActionListener((a) -> {
            //9'th and 10'th figures are responsible for access levels
            if (radioButton.isSelected()) {
                char[] nameArray = keyArea.getText().toCharArray();
                char figure = (char) ((int)nameArray[nameArray.length - 1] - 32);
                char[] key = new char[17];
                for(int i = 0; i < key.length; i++) {
                    key[i] = (char) ((Math.random() * (90 - 65)) + 65);
                    if(i == 4) {
                        key[i] = figure;
                    }
                    if(i == 5 || i == 11) {
                        key[i] = '-';
                    }
                    if(i == 9 || i == 10) {
                        key[i] = (char) ((Math.random() * (70 - 65)) + 65);
                    }
                }
                String newKey = String.valueOf(key);
                keyArea.setText(newKey);
            }

            if (radioButton1.isSelected()) {
                char[] nameArray = keyArea.getText().toCharArray();
                char figure = (char) ((int)nameArray[nameArray.length - 1] - 32);
                char[] key = new char[17];
                for(int i = 0; i < key.length; i++) {
                    key[i] = (char) ((Math.random() * (90 - 65)) + 65);
                    if(i == 4) {
                        key[i] = figure;
                    }
                    if(i == 5 || i == 11) {
                        key[i] = '-';
                    }
                    if(i == 9 || i == 10) {
                        key[i] = (char) ((Math.random() * (76 - 71)) + 71);
                    }
                }
                String newKey = String.valueOf(key);
                keyArea.setText(newKey);
            }

            if (radioButton2.isSelected()) {
                char[] nameArray = keyArea.getText().toCharArray();
                char figure = (char) ((int)nameArray[nameArray.length - 1] - 32);
                char[] key = new char[17];
                for(int i = 0; i < key.length; i++) {
                    key[i] = (char) ((Math.random() * (90 - 65)) + 65);
                    if(i == 4) {
                        key[i] = figure;
                    }
                    if(i == 5 || i == 11) {
                        key[i] = '-';
                    }
                    if(i == 9 || i == 10) {
                        key[i] = (char) ((Math.random() * (82 - 77)) + 77);
                    }
                }
                String newKey = String.valueOf(key);
                // print generated key
                keyArea.setText(newKey);
            }
        });
    }

    public static void main(String[] args) {
        new Keygen();
    }
}
