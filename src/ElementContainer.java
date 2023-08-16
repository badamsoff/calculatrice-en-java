//Badams Officiel


import javax.swing.*;
import java.awt.*;
import java.time.Year;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

class ElementContainer extends JFrame {
    Map<String, ButtonHolder> buttonHolderMap;
    private JPanel keyPad = new JPanel();
    private JPanel footer = new JPanel();

    JTextField outputDisplay = new JTextField();
    JTextField inputDisplay = new JTextField();

    ElementContainer(int width, int height) {
        this.setSize(width, height);
        this.setBackground(Color.cyan);
        this.setLayout(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.buttonHolderMap = ButtonHolder.getAll();

        this.setupDisplay();

        this.setupKeyPad();

        this.setupFooter();
    }

    private void setupDisplay() {
        System.out.println(new Date()+"[ElementContainer][setupDisplay] Setting up display screens...");
        JPanel screen = new JPanel();
        screen.setLayout(null);
        int screenHeight = (int) (this.getHeight() * Helper.SCREEN_RATIO);
        this.add(screen).setBounds(0, 0, this.getWidth(), screenHeight);
        //Input display
        screen.add(inputDisplay);
        int inputDisplayHeight = (int) (screen.getHeight() * Helper.INPUT_DISPLAY_RATIO);
        this.inputDisplay.setBounds(0, 0, screen.getWidth(), inputDisplayHeight);
        this.inputDisplay.setHorizontalAlignment(JTextField.LEFT);
        this.inputDisplay.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        this.inputDisplay.setText("0");
        this.inputDisplay.setEditable(false);
        this.inputDisplay.setBackground(Color.pink);
        this.inputDisplay.setForeground(Color.BLACK);

        //Output display
        screen.add(this.outputDisplay);
        int outputDisplayHeight = (int) (screen.getHeight() * Helper.OUTPUT_DISPLAY_RATIO);
        this.outputDisplay.setBounds(0, inputDisplayHeight, screen.getWidth(), outputDisplayHeight);
        this.outputDisplay.setHorizontalAlignment(JTextField.RIGHT);
        this.outputDisplay.setFont(Helper.SCREEN_FONT);
        this.outputDisplay.setText("0");
        this.outputDisplay.setEditable(false);
        this.outputDisplay.setBackground(Helper.GREEN);
        this.outputDisplay.setForeground(Color.BLACK);
        System.out.println(new Date()+"[ElementContainer][setupDisplay] Done!");
    }

    private void setupKeyPad() {
        System.out.println(new Date()+"[ElementContainer][setupKeyPad] Setting up keypad...");
        //------------setting font, color and style of buttons-------
        ArrayList<String> mapKeys = ButtonHolder.getMapKeysByType(this.buttonHolderMap, "numeric");
        this.prepareButtons(mapKeys, Helper.KEY_FONT_MD, Color.cyan);

        mapKeys = ButtonHolder.getMapKeysByType(this.buttonHolderMap, "operator");
        this.prepareButtons(mapKeys, Helper.KEY_FONT_MD, Helper.WHITE);

        mapKeys = ButtonHolder.getMapKeysByType(this.buttonHolderMap, "number_modifier");
        this.prepareButtons(mapKeys, Helper.KEY_FONT_MD, Helper.GREY);

        mapKeys = ButtonHolder.getMapKeysByType(this.buttonHolderMap, "answer");
        this.prepareButtons(mapKeys, Helper.KEY_FONT_MD, Helper.GREEN);

        this.prepareButtonByKey("exit", Helper.KEY_FONT_MD, Helper.RED);
        this.prepareButtonByKey("clear", Helper.KEY_FONT_MD, Color.ORANGE);
        this.prepareButtonByKey("delete", Helper.KEY_FONT_MD, Color.YELLOW);

        mapKeys = ButtonHolder.getMapKeysByType(this.buttonHolderMap, "single_operator");
        this.prepareButtons(mapKeys, Helper.KEY_FONT_MD, Helper.WHITE);

        this.prepareButtonByKey("percent", Helper.KEY_FONT_MD, Helper.WHITE);

        this.keyPad = new JPanel();
        int keyPadY = (int) (this.getHeight() * Helper.SCREEN_RATIO);
        int keyPadHeight = (int) (this.getHeight() * Helper.KEYPAD_RATIO);
        this.add(this.keyPad).setBounds(0, keyPadY, this.getWidth(), keyPadHeight);
        this.keyPad.setLayout(null);
        this.keyPad.setBackground(Helper.darkGrey);

        this.fixButtonsPlacement();

        System.out.println(new Date()+"[ElementContainer][setupKeyPad] Done!");
    }

    private void setupFooter(){
        System.out.println(new Date()+"[ElementContainer][setupFooter] Setting up footer...");
        int footerY = (int) (this.getHeight() * (Helper.SCREEN_RATIO + Helper.KEYPAD_RATIO));
        int footerHeight = (int)(this.getHeight() * Helper.FOOTER_RATIO);
        this.add(footer).setBounds(0, footerY, this.getWidth(), footerHeight);

        JLabel copyrightTag = new JLabel("\u00A9 "+ Year.now().getValue()+"  CI- Technology");
        copyrightTag.setForeground(Helper.BLUE);
        this.footer.add(copyrightTag).setBounds(0, 0, this.footer.getWidth(), this.footer.getHeight());
        System.out.println(new Date()+"[ElementContainer][setupFooter] Done!");
    }

    private void prepareButtons(ArrayList<String> buttonHolderMapKeys, Font font, Color bgColor) {
        for (String key : buttonHolderMapKeys) {
            this.prepareButtonByKey(key, font, bgColor);
        }
    }

    private void prepareButtonByKey(String key, Font font, Color bgColor) {
        this.buttonHolderMap.get(key).setFont(font);
        this.buttonHolderMap.get(key).setBackground(bgColor);
        this.buttonHolderMap.get(key).setFocusable(false);
    }

    private void fixButtonsPlacement() {
        System.out.println(new Date()+"[ElementContainer][fixButtonsPlacement] Placing buttons...");

        int btnWidth = this.keyPad.getWidth() / Helper.BUTTON_PER_LINE;
        this.keyPad.add(this.buttonHolderMap.get("clear")).setBounds(0, 0, btnWidth, Helper.BUTTON_HEIGHT);
        this.keyPad.add(this.buttonHolderMap.get("delete")).setBounds(btnWidth, 0, btnWidth, Helper.BUTTON_HEIGHT);

        String[] commonButtons = {"1", "2", "3", "add", "sub", "4", "5", "6", "mul", "div", "7", "8", "9", "mod", "0", "point", "percent", "equal"};
        this.setButtonByKeyList(commonButtons, Helper.BUTTON_HEIGHT);

        String[] scientificButtons = {"sin", "cos", "tan", "ln", "log", "one_by_n", "square", "cube", "sqrt", "exit"};
        this.setButtonByKeyList(scientificButtons, 6*Helper.BUTTON_HEIGHT);
        System.out.println(new Date()+"[ElementContainer][fixButtonsPlacement] Done!");
    }

    private void setButtonByKeyList(String[] buttonsKeys, int startY){
        int btnPerLine = Helper.BUTTON_PER_LINE;
        int btnWidth = this.keyPad.getWidth() / btnPerLine;
        int btnHeight = Helper.BUTTON_HEIGHT;
        int extraBlock = this.totalExtraBlock(buttonsKeys.length, btnPerLine);

        int rowCounter = 0, colCounter = 0, counter = 0;
        for(String buttonKey: buttonsKeys){
            int x = colCounter * btnWidth, y = rowCounter * btnHeight + startY;


            if(buttonKey.equalsIgnoreCase("mod") || buttonKey.equalsIgnoreCase("equal")){
                this.keyPad.add(this.buttonHolderMap.get(buttonKey)).setBounds(x, y, btnWidth*2, btnHeight);
                counter +=2;
            }else{
                this.keyPad.add(this.buttonHolderMap.get(buttonKey)).setBounds(x, y, btnWidth, btnHeight);
                counter++;
            }
            rowCounter = counter % btnPerLine == 0 ? rowCounter + 1 : rowCounter;
            colCounter = counter % btnPerLine == 0 ? 0 : colCounter + 1;
        }
    }

    private int totalExtraBlock(int numberOfBtn, int btnPerLine){
        return numberOfBtn - (numberOfBtn % btnPerLine);
    }

    void setIcon(String iconPath){
        ImageIcon img = new ImageIcon(iconPath);
        this.setIconImage(img.getImage());
    }
}
