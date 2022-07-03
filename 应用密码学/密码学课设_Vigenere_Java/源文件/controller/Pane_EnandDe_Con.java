package SecretDesign.controller;

import SecretDesign.Launch.MainApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.IOException;
import static javafx.application.Platform.exit;

public class Pane_EnandDe_Con {
    @FXML
    private TextField Secret;
    @FXML
    private TextField plainText;
    @FXML
    private TextField cipherText;
    @FXML
    private TextArea Explain;
    @FXML
    private Label Chart_Con;
    @FXML
    private ImageView show;
    @FXML
    MenuBar menubar;
    private Image Show_1=new Image("SecretDesign/icon/Show_1.png");
    private Image Show_2=new Image("SecretDesign/icon/Show_2.png");
    private String a,b,c;
    Pane pane;

    public void clickAdd() throws IOException {//点击加密按钮
        a=Secret.getText().toLowerCase();//密钥降为大写字符
        b=plainText.getText();//明文
        c="";
        boolean result_a = a.matches("[a-zA-Z]+");
        boolean result_b = b.matches("[a-zA-Z]+");
        if(result_a&&result_b){//密钥与明文均不含有非法字符
            Explain.setText("加/解密日志打印栏：\n密钥为："+a+"\n明文为："+b+"\n");
            cipherText.setText(Vigenere_Add());
            show.setImage(Show_1);
            Chart_Con.setVisible(false);
            show.setVisible(true);
        }
        else{//密钥或明文含有非法字符
            Explain.setText("加/解密日志打印栏：\n密钥为："+a+"\n明文为:"+b+"\n含有非法字符，请输入全英文字符串！\n");
        }
    }

    public void clickDecrease() throws IOException{//点击解密按钮
        a=Secret.getText().toLowerCase();//密文转为小写
        c=cipherText.getText();
        b="";
        boolean result_a = a.matches("[a-zA-Z]+");
        boolean result_c = c.matches("[a-zA-Z]+");
        if(result_a&&result_c){
            Explain.setText("加/解密日志打印栏：\n密钥为："+a+"\n密文为："+c+"\n");
            plainText.setText(Vigenere_Decrease());
            Chart_Con.setVisible(false);
            show.setImage(Show_2);
        }
        else{
            Explain.setText("加/解密日志打印栏：\n密钥为："+a+"\n密文为:"+c+"\n含有非法字符，请输入全英文字符串！\n");
        }
    }

    public void clickClear(){//点击清空按钮
        plainText.setText("");
        Secret.setText("");
        cipherText.setText("");
        Explain.setText("加/解密说明栏：请先加密/解密后再查看此栏。");
        show.setVisible(false);
        Chart_Con.setVisible(true);
    }

    public String Vigenere_Add() {//加密算法
        int  num=a.length();//num是密钥的长度
        for (int i = 0; i < b.length() ; i++) {//b.length是明文的长度
                int Temp=a.charAt(i%num)-97;//Temp现在是密钥的对应值
                Temp=b.charAt(i)+Temp%26;
                if(65<=b.charAt(i)&&b.charAt(i)<=90) {
                   if (Temp > 90) Temp -= 26;
                }
                else{
                    if(Temp>122) Temp-=26;
                }
                Explain.setText(Explain.getText()+(i+1)+"：密钥第"+((a.charAt(i%num)))+"行，明文第"+(b.charAt(i))+"列，得出密文为"+(char)Temp+"\n");
                c = c.concat(Character.toString((char)Temp));
        }
        return c;
    }

    public String Vigenere_Decrease(){//解密算法
        int num=a.length();
        for(int i=0;i<c.length();i++){
            int Temp=a.charAt(i%num)-97;//Temp现在是密钥的对应值
            Temp=c.charAt(i)-Temp%26;
            if(65<=c.charAt(i)&&c.charAt(i)<=90) {
                if (Temp <65) Temp += 26;
            }
            else{
                if(Temp<97) Temp+=26;
            }
            Explain.setText(Explain.getText() + (i + 1) + "：密文第" + ((c.charAt(i))) + "行，密钥第" + (a.charAt(i%num)) + "列，得出明文为" + (char) Temp + "\n");
            b = b.concat(Character.toString((char)Temp));
        }
        return b;
    }

    public void getStage(String name, ActionEvent event) throws IOException {
        Stage stage=(Stage) menubar.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getClassLoader().getResource(name));
        pane = loader.load();
        Scene scene = new Scene(pane);
        stage.setScene(scene);
    }

    public void clickPrevious(ActionEvent event) throws IOException {
        getStage("SecretDesign/view/Pane_CrackingMethod.fxml",event);

    }

    public void clickNext(ActionEvent event) throws IOException{
        getStage("SecretDesign/view/Pane_FurtherStudy.fxml",event);
    }

    public void clickExit() {
        exit();
    }

    public void click_main(ActionEvent event) throws IOException {
        Stage stage=(Stage) menubar.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getClassLoader().getResource("SecretDesign/view/Pane_main.fxml"));
        SplitPane splitPane= loader.load();
        Scene scene = new Scene(splitPane);
        stage.setScene(scene);
    }

    public void click_History(ActionEvent event) throws IOException {
        getStage("SecretDesign/view/Pane_History.fxml",event);
    }

    public void click_Describe(ActionEvent event) throws IOException {
        getStage("SecretDesign/view/Pane_Describe.fxml",event);
    }

    public void click_CrackingMethod(ActionEvent event) throws IOException {
        getStage("SecretDesign/view/Pane_CrackingMethod.fxml",event);
    }

    public void click_EnandDe(ActionEvent event) throws IOException{
        getStage("SecretDesign/view/Pane_EnandDe.fxml",event);
    }

    public void click_Instruction(ActionEvent event) throws IOException {
        getStage("SecretDesign/view/Pane_Instruction.fxml",event);
    }

    public void click_FurtherStudy(ActionEvent event) throws IOException {
        getStage("SecretDesign/view/Pane_FurtherStudy.fxml",event);
    }
}