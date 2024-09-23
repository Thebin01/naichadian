package build;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
public class Login {//登录界面
    private Checkbox c1;
    private Checkbox c2;
    private Checkbox c3;
    private JPasswordField myPassword;
    private JTextField yonghuming;
    private int Cmake = 1;
    class Login_Menu extends JFrame//登录界面
    {
        public Login_Menu() {
            setBounds(450, 200, 1000, 720);
            setResizable(false);
            setTitle("奶茶店管理系统");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setVisible(true);
            //setBounds(500, 200, 300, 300);
            //设置参数

            Container contentPane = getContentPane();//布局管理器

            JPanel textPanel = new JPanel();//用户，密码登陆主界面
            textPanel.setBackground(new Color(255,161,48));
            textPanel.setLayout(null);

            JLabel xinxi = new JLabel("欢迎光临");
            xinxi.setFont(new Font("隶书", Font.BOLD, 150));
            xinxi.setBounds(190, 100, 700, 150);
            xinxi.setForeground(new Color(104, 79, 64));
            textPanel.add(xinxi);

            JLabel yonghu = new JLabel("用户名：", JLabel.CENTER);
            yonghu.setFont(new Font("宋体", 0, 50));
            yonghu.setBounds(200, 300, 200, 50);//用户名标签位置
            yonghu.setForeground(new Color(104, 79, 64));

            yonghuming = new JTextField(20);
            yonghuming.setBounds(400, 300, 350, 50);//用户名文本框位置

            textPanel.add(yonghu);
            textPanel.add(yonghuming);

            JLabel inputPassword = new JLabel("密  码：", JLabel.CENTER);
            inputPassword.setFont(new Font("宋体", 0, 50));
            inputPassword.setBounds(200, 360, 200, 50);//密码标签位置
            inputPassword.setForeground(new Color(104, 79, 64));

            myPassword = new JPasswordField(20);
            myPassword.setBounds(400, 360, 350, 50);//密码文本框位置

            textPanel.add(inputPassword);
            textPanel.add(myPassword);

            JTextField checkPassword = new JTextField(20);

            checkPassword.setEditable(false);

            JTextField checkyonghu = new JTextField(20);

            checkyonghu.setEditable(true);

//复选框(学生，教师)

            CheckboxGroup cg = new CheckboxGroup();//限制仅一个复选框打开
            c1 = new Checkbox("Manager", cg, true);//CheckboxGroup cg
            c1.setBounds(800, 300, 100, 50);
            c2 = new Checkbox("Member", cg, false);//这里复选框内容只能显示英文 ？
            c2.setBounds(800, 350, 100, 50);
            c3 = new Checkbox("Visitor", cg, false);//这里复选框内容只能显示英文 ？
            c3.setBounds(800, 400, 100, 50);

            c1.addItemListener(new Login_Menu.checkboxAction());
            c2.addItemListener(new Login_Menu.checkboxAction());
            c3.addItemListener(new Login_Menu.checkboxAction());

            textPanel.add(c1);
            textPanel.add(c2);
            textPanel.add(c3);


//登陆按钮

            JButton loginButton = new JButton("登陆");
            loginButton.setBounds(280, 500, 200, 75);//登录按钮位置大小
            loginButton.setBackground(new Color(245,136,5));
            loginButton.setFont(new Font("微软雅黑", Font.BOLD, 20));
            loginButton.addActionListener(new Login_Menu.LoginAction());

            textPanel.add(loginButton);

//注册按钮

            JButton zhuceButton = new JButton("注册");
            zhuceButton.setBounds(500, 500, 200, 75);//登录按钮位置大小
            zhuceButton.setBackground(new Color(245,136,5));
            zhuceButton.setFont(new Font("微软雅黑", Font.BOLD, 20));
            zhuceButton.addActionListener(new Login_Menu.ZhuceAction());

            textPanel.add(zhuceButton);

            contentPane.add(textPanel);
        }


        public class checkboxAction implements ItemListener {//复选框事件，根据选择赋值Cmake选择不同用户角色
            public void itemStateChanged(ItemEvent et) {
                if (et.getSource() == c1) {//BOSS
                    Cmake = 1;//标志位置1为
                } else if (et.getSource() == c2) {//用户
                    Cmake = 3;
                } else if (et.getSource() == c3){//游客
                    Cmake = 2;
                }
            }
        }



        public class LoginAction implements ActionListener {//登陆事件：根据复选框，调用验证不同身份用户
            public void actionPerformed(ActionEvent e) {
                User user = new User();
                char[] s = myPassword.getPassword();//获取输入的密码
                String gety = yonghuming.getText();//获取输入的用户名
                String getp = new String(s);//转换为String

                dispose();
                user.yan_zheng(gety,getp,Cmake);

            }
        }
        public class ZhuceAction implements ActionListener {//注册事件：不管复选框，直接根据输入信息注册
        public void actionPerformed(ActionEvent e) {
            User user = new User();
            char[] s = myPassword.getPassword();//获取输入的密码
            String gety = yonghuming.getText();//获取输入的用户名
            String getp = new String(s);//转换为String

            boolean make = false;
           // do{
                System.out.println(11111);
                make =user.zhu_ce(gety,getp);
                if (make){
                    dispose();
                    user.yan_zheng(gety,getp,3);
                }


            //}while (!make);//注册成功make为ture则停止循环

            //JOptionPane.showMessageDialog(null, "登陆成功，请等待……");
            //登录成功进入下一界面///////////////////////
            }
        }
    }
}




