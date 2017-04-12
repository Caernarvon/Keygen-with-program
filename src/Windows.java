import javax.swing.*;
import java.awt.*;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
/*
 * Program with different encoding level.
 * To take an access to any level you need to write key to activation form.
 * Required key is needed to be formed by certain algorithm depending on preffered encoding access level.
 * Program saves it's status even when closing and re-opening one more time.
*/
public class Windows extends JFrame {
    // initializing static elements to have an access from all places in class
    private static Preferences prefs = Preferences.userRoot().node("prefs");
    private static JButton buttonDef = new JButton("Default encode");
    private static JButton buttonLow = new JButton("Low \n encode");
    private static JButton buttonMid = new JButton("Middle \n encode");
    private static JButton buttonHigh = new JButton("Highend \n encode");
    private static JTextArea keyArea = new JTextArea(7, 10);
    private static JFrame formActivation = new JFrame("Activation");
    private static String key;
    private static JFrame formMain = new JFrame("ZX");
    private static JComboBox combo;

    private Windows() {
        // default form that gives opportunity to choose an encoding level
        formMain.setSize(200, 300);
        final JPanel content = new JPanel(new GridLayout(2, 1, 1, 1));
        final JPanel content2 = new JPanel(new GridLayout(2, 1, 1, 1));
        final JPanel activation = new JPanel(new GridLayout(1, 1, 1, 1));
        JButton buttonKey = new JButton("A C T I V A T E");
        content.add(buttonDef, BorderLayout.WEST);
        content.add(buttonLow, BorderLayout.EAST);
        content2.add(buttonMid, BorderLayout.WEST);
        content2.add(buttonHigh, BorderLayout.EAST);
        activation.add(buttonKey);
        buttonDef.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        buttonLow.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        buttonMid.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        buttonHigh.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        buttonKey.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        formMain.add(content, BorderLayout.WEST);
        formMain.add(content2, BorderLayout.EAST);
        formMain.add(activation, BorderLayout.PAGE_END);
        formMain.setVisible(true);
        formMain.setDefaultCloseOperation(EXIT_ON_CLOSE);
        formMain.setLocationRelativeTo(null);
        formMain.setResizable(false);

        // check serial from Preferences
        setToPreferredAccessByKey(prefs.get("serial", ""));

        // make forms depending on chosen button
        buttonDef.addActionListener((e) -> {
             makeForm(1);
        });
        buttonLow.addActionListener((e) -> {
            makeForm(2);
        });
        buttonMid.addActionListener((e) -> {
            makeForm(3);
        });

        // highest encode form
        // TODO encoding algorithm
        buttonHigh.addActionListener((e) -> {
            // encoding form with password, not just converting one symbol to two etc.
            JFrame formHigh = new JFrame("Highest encode");
            formHigh.setBounds(350, 350, 350, 350);
            formHigh.setLayout(new BorderLayout());
            formHigh.setDefaultCloseOperation(EXIT_ON_CLOSE);
            JTextArea bfrTr = new JTextArea(7, 30);
            JTextArea aftrTr = new JTextArea(8, 30);
            JButton buttonTr = new JButton("Encode");
            JButton buttonDtr = new JButton("Decode");
            JTextArea pass = new JTextArea(2, 2);
            pass.setText("pass here");
            bfrTr.setText("WORK IN PROGRESS");
            pass.setBorder(BorderFactory.createLineBorder(Color.black));
            bfrTr.setBorder(BorderFactory.createLineBorder(Color.black));
            aftrTr.setBorder(BorderFactory.createLineBorder(Color.black));
            formHigh.add(pass, BorderLayout.CENTER);
            Font font = new Font("", Font.PLAIN, 18);
            formHigh.add(pass, BorderLayout.CENTER);
            formHigh.add(bfrTr, BorderLayout.NORTH);
            formHigh.add(buttonTr, BorderLayout.WEST);
            formHigh.add(buttonDtr, BorderLayout.EAST);
            formHigh.add(aftrTr, BorderLayout.SOUTH);
            buttonTr.setFont(font);
            buttonTr.setBackground(Color.WHITE);
            buttonDtr.setFont(font);
            buttonDtr.setBackground(Color.WHITE);
            pass.setFont(font);
            pass.setBackground(Color.WHITE);
            bfrTr.setWrapStyleWord(true);
            aftrTr.setWrapStyleWord(true);
            bfrTr.setLineWrap(true);
            aftrTr.setLineWrap(true);
            formHigh.setVisible(true);
            formMain.setVisible(false);

            buttonTr.addActionListener((a) -> {
                String text = bfrTr.getText();
                int j = 0;
                char[] charText = text.toCharArray();
                if (text.isEmpty()) {
                    aftrTr.setText("Error! Enter something!");
                }

                String passString = pass.getText();
                char[] password = passString.toCharArray();
                int symbol = 0;
                int sum = 0;
                for (int i = 0; i < password.length; i++) {
                    symbol = password[i];
                    sum = sum + symbol;
                }
                sum = sum / password.length;
                //System.out.println(sum);
                if (text.length() > 0) {
                    char[] chars = new char[charText.length * 2 + 2];
                    for (int i = 0; i < chars.length; i++) {
                        chars[i] = ' ';
                    }
                    for (int i = 0; i - 1 < chars.length; i++) {
                        chars[i] = (char) (chars[i] + chars[i]);
                        chars[i+1] = (char) sum;
                    }
                    String stringValueOf = String.valueOf(chars);
                    aftrTr.setText(stringValueOf);
                    bfrTr.setText("");
                }
            });

            buttonDtr.addActionListener((a) -> {
                String text = aftrTr.getText();
                String passString = pass.getText();
                char[] password = passString.toCharArray();
                int symbol = 0;
                int sum = 0;
                for (int i = 0; i < password.length; i++) {
                    symbol = password[i];
                    sum = sum + symbol;

                }
                char[] charText = text.toCharArray();
                int k = 0;
                if (text.isEmpty()) {
                    aftrTr.setText("Error! Nothing to decode!");
                }
                char[] chars = new char[charText.length / 2];
                String stringValueOf = String.valueOf(chars);
                bfrTr.setText(stringValueOf);
                aftrTr.setText("");
            });
        });

        buttonKey.addActionListener((g) -> {
            // activation form
            formActivation.setBounds(400, 400, 170, 170);
            formActivation.setLayout(new BorderLayout());
            formActivation.setDefaultCloseOperation(EXIT_ON_CLOSE);
            keyArea.setText("Key here");
            JButton buttonActivate = new JButton("ACTIVATE");
            formActivation.add(keyArea, BorderLayout.NORTH);
            formActivation.add(buttonActivate, BorderLayout.SOUTH);
            formActivation.setVisible(true);

            buttonActivate.addActionListener((a) -> {
                // get key from area and send to activtor закоммитить снова
                key = keyArea.getText();
                setToPreferredAccessBySerial(key);
            });
        });
    }

    public void setToDefaultAccess() {
        // make default access
        buttonLow.setVisible(false);
        buttonMid.setVisible(false);
        buttonHigh.setVisible(false);
    }

    // get access by key from registre/activation form
    public void setToPreferredAccessByKey(String key) {
        // make all forms aren't visible
        setToDefaultAccess();
        if(!key.isEmpty() && key.length() == 17) {
            char[] keyCheck = key.toCharArray();
            int first = keyCheck[9];
            int second = keyCheck[10];
            if(first > 64 && first < 71 && second > 64 && second < 71 ) {
                prefs.put("serial", key);

                buttonLow.setVisible(true);
                formActivation.setVisible(false);
            }
            else if(first > 70 && second < 77 && second > 70 && second < 77 ) {
                prefs.put("serial", key);

                buttonLow.setVisible(true);
                buttonMid.setVisible(true);
                formActivation.setVisible(false);
            }
            else if(first > 76 && second < 83 && second > 76 && second < 83 ) {
                prefs.put("serial", key);

                buttonLow.setVisible(true);
                buttonMid.setVisible(true);
                buttonHigh.setVisible(true);
                formActivation.setVisible(false);
            }
        }
        else {
            keyArea.setText("not activated");
            clearPreferences();
            formActivation.setVisible(false);
        }
    }

    public void setToPreferredAccessBySerial(String serial) {
        setToPreferredAccessByKey(serial);
    }

    // clear preferences when key isn't right, method can be deleted from activation form if's
    // but it allows to set buttons to invisible easely
    public void clearPreferences() {
        try {
            prefs.clear();
        }
        catch (BackingStoreException e) {
            e.printStackTrace();
        }
    }

    // method makes form depending on level, it's exists because code of forms activated by buttons is nearly similar
    // and here is no to write different code for anyone form
    public void makeForm(int lvl){
        JFrame formDef = new JFrame("Encode");
        formDef.setBounds(300, 300, 300, 300);
        formDef.setLayout(new BorderLayout());
        formDef.setDefaultCloseOperation(EXIT_ON_CLOSE);
        JTextArea bfrTr = new JTextArea(7, 30);
        JTextArea aftrTr = new JTextArea(8, 30);
        JButton buttonTr = new JButton("Encode");
        JButton buttonDtr = new JButton("Decode");
        Font font = new Font("", Font.PLAIN, 18);
        formDef.add(bfrTr, BorderLayout.NORTH);
        formDef.add(buttonTr, BorderLayout.WEST);
        formDef.add(buttonDtr, BorderLayout.EAST);
        formDef.add(aftrTr, BorderLayout.SOUTH);
        buttonTr.setFont(font);
        buttonTr.setBackground(Color.WHITE);
        buttonDtr.setFont(font);
        buttonDtr.setBackground(Color.WHITE);
        bfrTr.setWrapStyleWord(true);
        aftrTr.setWrapStyleWord(true);
        bfrTr.setLineWrap(true);
        aftrTr.setLineWrap(true);
        formDef.setVisible(true);
        formMain.setVisible(false);
        // that's the only one different GUI element in all forms
        // encoding access level depends on chosen level
        if(lvl == 1) {
            String[] elements = new String[]{"1"};
            combo = new JComboBox(elements);
            combo.setSelectedIndex(0);
            combo.setSize(400, 400);
            combo.setFont(font);
            combo.setBackground(Color.WHITE);
            formDef.add(combo, BorderLayout.CENTER);
        }
        if(lvl == 2) {
            String[] elements = new String[]{"1", "2"};
            combo = new JComboBox(elements);
            combo.setSelectedIndex(0);
            combo.setSize(400, 400);
            combo.setFont(font);
            combo.setBackground(Color.WHITE);
            formDef.add(combo, BorderLayout.CENTER);
        }
        if(lvl == 3) {
            String[] elements = new String[]{"1", "2", "3"};
            combo = new JComboBox(elements);
            combo.setSelectedIndex(0);
            combo.setSize(400, 400);
            combo.setFont(font);
            combo.setBackground(Color.WHITE);
            combo = new JComboBox(elements);
            formDef.add(combo, BorderLayout.CENTER);
        }

        // encoding algorithm for def, low, mid forms
        buttonTr.addActionListener((a) -> {
            int level = combo.getSelectedIndex() + 1;
            String text = bfrTr.getText();
            int j = 0;
            char[] charText = text.toCharArray();
            if (text.isEmpty()) {
                aftrTr.setText("Error! Enter something!");
            }
            if (text.length() > 0) {
                char[] chars = new char[charText.length * level];
                for (int count = 0; count < chars.length; count++) {
                    chars[count] = ' ';
                }
                for (int i = 0; i < chars.length; ) {
                    int n = charText[j];
                    if (level == 1) {
                        int k = n * 2;
                        char ch2 = (char) k;
                        chars[i] = ch2;
                        j++;
                        i++;
                    }
                    if (level == 2) {
                        int g1 = n * 2;
                        int g2 = n + charText.length;
                        char ch3 = (char) g1;
                        char gh3 = (char) g2;
                        chars[i] = ch3;
                        chars[i + 1] = gh3;
                        j++;
                        i = i + 2;
                    }
                    if (level == 3) {
                        int h1 = n * 2;
                        int h2 = n + charText.length;
                        int h3 = 'a' + charText.length;
                        char hh4 = (char) h1;
                        char jh4 = (char) h2;
                        char kh4 = (char) h3;
                        chars[i] = hh4;
                        chars[i + 1] = jh4;
                        chars[i + 2] = kh4;
                        j++;
                        i = i + 3;
                    }
                }
                String stringValueOf = String.valueOf(chars);
                aftrTr.setText(stringValueOf);
                bfrTr.setText("");
            }
        });

        // decoding algorithm for def, low, mid forms
        buttonDtr.addActionListener((a) -> {
            int level = combo.getSelectedIndex() + 1;
            String text = aftrTr.getText();
            char[] charText = text.toCharArray();
            int k = 0;
            if (text.isEmpty()) {
                aftrTr.setText("Error! Nothing to decode!");
            }
            char[] chars = new char[charText.length / level];
            if (charText.length > 0) {
                for (int i = 0; i < chars.length * level; ) {
                    if (level == 1) {
                        char ch2 = charText[i];
                        int symbol1 = (char) ch2;
                        symbol1 = symbol1 / 2;
                        char symbolOne = (char) symbol1;
                        chars[i] = symbolOne;
                        k++;
                        i++;
                    }
                    if (level == 2) {
                        char ch2 = charText[i];
                        int symbol1 = (char) ch2;
                        symbol1 = symbol1 / 2;
                        char symbolOne = (char) symbol1;
                        chars[k] = symbolOne;
                        k++;
                        i = i + 2;
                    }
                    if (level == 3) {
                        char ch2 = charText[i];
                        int symbol1 = (char) ch2;
                        symbol1 = symbol1 / 2;
                        char symbolOne = (char) symbol1;
                        chars[k] = symbolOne;
                        k++;
                        i = i + 3;
                    }
                }
                String stringValueOf = String.valueOf(chars);
                bfrTr.setText(stringValueOf);
                aftrTr.setText("");
            }
        });
    }

    // can be used to make MD5 from serial key, it's can be used only if key isn't dynamic and it could be compared
    // with other MD5 from other serial key, but it isn't used in my situation
    private String encode(String serial) {
        String result = "";
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(serial.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
            }
            result = sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
            // catch exception
        }
        return result;
    }

    public static void main(String[] args) {
        // initializing form
        new Windows();
    }
}
