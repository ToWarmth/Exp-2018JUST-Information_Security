package Work.main3;

//Calculator.java
//实现计算器
//优势：同时实现键盘监听与Button点击监听
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;

public class Calculator implements ActionListener {
    private JFrame jf=new JFrame();
    private String[] temp = {"%","CE","C","Back","1⁄x","X²","√x","÷","7","8","9","*","4","5","6","-","1","2","3","+","+/-","0",".","="};
    private JButton buttons[] = new JButton[temp.length];  //创建Button按钮
    private JTextField resultText = new JTextField("0");  //显示计算结果文本框，初始状态为0

    private boolean firstDigit = true;  // 标志用户按的是否是整个表达式的第一个数字,或者是运算符后的第一个数字
    private double resultNum = 0.0000;   // 计算的中间结果
    private String operator = "=";   // 当前运算的运算符（按键"C"时需要将其还原为"="）
    private boolean operateValidFlag = true;   // 判断操作是否合法

    Calculator(){//控制生成计算器
        init();
    }

    public void init(){//初始化计算器界面
        jf.setSize(500,700);
        jf.setTitle("Calculaor designed by CSG");
        jf.setLocation(300,100);
        jf.setMinimumSize(new Dimension(500, 700));
        Image icon=Toolkit.getDefaultToolkit().getImage("C://Users/Sce_dio/IdeaProjects/Java_Course/src/Work/main3/1.jpg");
        jf.setIconImage(icon);
        jf.setResizable(true);
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //设置文本框格式，并将其加入jp_text面板中
        resultText.setFont(new Font("宋体",Font.BOLD,43));  //设置文本框中文字的字体以及大小，加粗
        resultText.setHorizontalAlignment(JTextField.RIGHT);  //文本框中的内容采用右对齐方式
        resultText.setEditable(true);  //不能修改结果文本框
        resultText.setBorder(null);  //删除文本框的边框
        Color color1 = new Color(195, 238, 242);    //功能键颜色
        resultText.setBackground(color1);
        JPanel jp_text=new JPanel();
        jp_text.setLayout(new BorderLayout());
        jp_text.add(resultText);

        //设置各按键格式，并将其加入jp_key面板中
        JPanel jp_key = new JPanel();
        jp_key.setLayout(new GridLayout(6, 4, 2, 2));
        Color color3 = new Color(232, 232, 232);  //背景颜色
        for(int i = 0; i < 8; i++) {//初始化功能按钮
            buttons[i] = new JButton(temp[i]);
            buttons[i].setBackground(color3);
            buttons[i].setForeground(Color.black);
            buttons[i].setFont(new Font(Font.SERIF,Font.PLAIN,18));
            buttons[i].setBorderPainted(false);  //去除按钮的边框
            jp_key.add(buttons[i]);
        }
        for(int i = 8; i < temp.length; i++) {//初始化运算符及数字键按钮
            buttons[i] = new JButton(temp[i]);
            jp_key.add(buttons[i]);
            if((i+1)%4==0) buttons[i].setBackground(color3);
            else buttons[i].setBackground(Color.white);
            buttons[i].setForeground(Color.black);
            buttons[i].setFont(new Font(Font.SERIF,Font.PLAIN,18));
            buttons[i].setBorderPainted(false);  //去除按钮的边框
        }
        Color color2 = new Color(126, 192, 238);  //等于号专属颜色
        buttons[23].setBackground(color2);  // '='符键用特殊颜色
        jp_key.setBackground(color1);

        // 为各按钮添加事件监听器，都使用同一个事件监听器。
        for (int i = 0; i < temp.length; i++) {
            buttons[i].addActionListener(this);
        }

        //将面板JPanel加入到JFrame中，jp_text在上，jp_key在下
        jf.getContentPane().add("North", jp_text);
        jf.getContentPane().add("Center",jp_key);

        //设置两个面板的边框，尽量还原win10计算器
        jp_text.setBorder(BorderFactory.createMatteBorder(25,3,1,3,color1));
        jp_key.setBorder(BorderFactory.createMatteBorder(6,3,3,3,color1));

        resultText.addKeyListener(new KeyAdapter() {//向文本域添加监听器，以实现监听键盘输入
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyTyped(e);
                char string=e.getKeyChar();
                String key=String.valueOf(string);
                System.out.println(key);
                if ("0123456789.".indexOf(key) >= 0) {// 用户按了数字键或者小数点键
                    doNumber(key,false);
                } else if(key.equals(temp[7])||key.equals(temp[11])||key.equals(temp[15])||key.equals(temp[19])){
                    doOperator2(key,true);
                }
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {//对button按钮添加监听器
        String key = e.getActionCommand();
        if (key.equals(temp[3])) {//Back键
            doBackspace();
        } else if (key.equals(temp[1])) {//CE键
            resultText.setText("0");
        } else if (key.equals(temp[2])) {//C键
            do_Clear();
        } else if ("0123456789.".indexOf(key) >= 0) {// 用户按了数字键或者小数点键
            doNumber(key,true);
        } else if(key.equals(temp[0]) || key.equals(temp[4]) || key.equals(temp[5]) ||
                key.equals(temp[6]) || key.equals(temp[20])) {// 用户按了只需一个数的运算键（求倒数，%，开方，平方，取正负数）
            doOperator1(key,false);
        } else {
            doOperator2(key,false);
        }
    }

    private void doBackspace() {
        String text = resultText.getText();
        int i = text.length();
        if (i > 0) {
            text = text.substring(0, i - 1);  // 退格
            if (text.length() == 0) {//初始化
                resultText.setText("0");
                firstDigit = true;
                operator = "=";
            } else {//text已经减少一个字符，赋值，看起来就像退格
                resultText.setText(text);
            }
        }
    }

    private void do_Clear() {
        resultText.setText("0");
        firstDigit = true;
        operator = "=";
    }

    private void doNumber(String key,boolean control) {
        //firstDigit用于判断是否是第一个数，是的话覆盖，不是的话连接
        if (firstDigit) {// 输入的为第一个数，直接设置文本，覆盖0
            resultText.setText(key);
        } else if ((key.equals(".")) && (resultText.getText().indexOf(".") < 0)) {
            // 输入的是小数点，并且之前没有小数点，则将小数点附在结果文本框的后面
            resultText.setText(resultText.getText() + ".");
        } else if (!key.equals(".")&&control) {
            // 如果输入的不是小数点，则将数字附在结果文本框的后面。键盘输入的就不用继续附了。
            resultText.setText(resultText.getText() + key);
        }
        firstDigit = false;
    }

    private void doOperator1(String command,boolean control) {
        //只有一个操作数类型的操作符
        if(control){//如果是键盘输入的，先把运算符覆盖再说。
            resultText.setText(resultText.getText());
        }
        operator = command;  // 运算符为用户按的按钮
        if (operator.equals("1⁄x")) {   // 倒数运算
            if (resultNum == 0) {
                operateValidFlag = false;
                resultText.setText("零没有倒数");
            } else {
                resultNum = 1 / getNumberFromText();
            }
        } else if (operator.equals("√x")) {// 平方根运算
            if (resultNum < 0) {
                operateValidFlag = false;
                resultText.setText("根号内不能为负");
            } else {
                resultNum = Math.sqrt(getNumberFromText());
            }
        } else if (operator.equals("X²")) {// 平方运算
            resultNum = getNumberFromText()*getNumberFromText();
        } else if (operator.equals("%")) {// 百分号运算，除以100
            resultNum = getNumberFromText() / 100;
        } else if (operator.equals("+/-")) {// 正数负数运算
            resultNum = getNumberFromText() * (-1);
            if (operateValidFlag) {// 操作合法的情况下，才正常输出数值到屏幕上
                long t1;
                double t2;
                t1 = (long) resultNum;
                t2 = resultNum - t1;
                if (t2 == 0) {
                    resultText.setText(String.valueOf(t1));
                } else {
                    resultText.setText(String.valueOf(new DecimalFormat("0.00").format(resultNum)));
                }
            }
            firstDigit = true;
            operateValidFlag = true;
        }
    }

    private void doOperator2(String command,boolean control) {
        ////有两个操作数类型的操作符
        if(control) doBackspace();
        if (operator.equals("÷")) { // 除法运算
            // 如果当前结果文本框中的值等于0
            if (getNumberFromText() == 0.0) {
                operateValidFlag = false;  //操作不合法
                resultText.setText("除数不能为零");
            } else {
                resultNum /= getNumberFromText();
            }
        } else if (operator.equals("+")) {
            // 加法运算
            resultNum += getNumberFromText();
        } else if (operator.equals("-")) {
            // 减法运算
            resultNum -= getNumberFromText();
        } else if (operator.equals("*")) {
            // 乘法运算
            resultNum *= getNumberFromText();
        } else if (operator.equals("=")) {
            // 赋值运算
            resultNum = getNumberFromText();
        }
        if (operateValidFlag) {
            // 操作合法的情况下，结果为小数保留小数点后4位，整数正常输出
            long t1;
            double t2;
            t1 = (long) resultNum;
            //判断resultNum是否小数部分为0，若小数部分为0，当整数输出；若小数部分不为0，那就按8位小数输出resultNum
            t2 = resultNum - t1;
            if (t2 == 0) {
                resultText.setText(String.valueOf(t1));
            } else {
                resultText.setText(String.valueOf(new DecimalFormat("0.00000000").format(resultNum)));
            }
        }
        operator = command;  //运算符为用户按的按钮
        firstDigit = true;
        operateValidFlag = true;
    }

    private double getNumberFromText() {
        double result = 0;
        try {
            result = Double.valueOf(resultText.getText()).doubleValue();//textField本来是字符串，将其转为double数值
        } catch (NumberFormatException e) {
        }
        return result;
    }
    
    public static void main(String[] args){
        new Calculator();
    }
}
