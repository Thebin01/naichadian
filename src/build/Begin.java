package build;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Begin extends Login{
    public static void main(String[] args) {//////程序开始的地方//////////
        Begin bb = new Begin();
        bb.begin();
    }

    class  Huan_ying extends JWindow {//欢迎界面
        public Huan_ying() {
            JLabel label_pic = new JLabel(new ImageIcon("C:\\Users\\15275\\IdeaProjects\\yin_pin_dian_guan_li\\images\\welcme.jpg"));
            add(label_pic, BorderLayout.CENTER);
            setSize(1920, 1080);
            setLocationRelativeTo(null);
            setVisible(true);
        }
    }
    class jia_zai extends  JWindow{
        private JProgressBar jpb;

        private JLabel jl;
        public jia_zai(){
            jl = new JLabel(new ImageIcon("C:\\Users\\15275\\IdeaProjects\\yin_pin_dian_guan_li\\images\\jiazai.jpg"));
            add(jl, BorderLayout.CENTER);
            setSize(800, 200);
            setLocationRelativeTo(null);
            setVisible(true);
        }


    }


    private void begin(){

        jia_zai window2 = new jia_zai();

        Huan_ying window1 = new Huan_ying();//构造欢迎界面

        window1.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {}

            @Override
            public void mousePressed(MouseEvent e) {//鼠标按下

                window1.dispose();
                try {

                    //jia_zai window2 = new jia_zai();
                    Thread.sleep(   1000 );//暂停一秒模拟加载时间
                    window2.dispose();
                    //Login_Menu frame=
                    new Login_Menu();//载入登陆界面
                    //Login login = new Login();//登录
                    //login.menu();
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
        });

    }

}
