package build;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Manager extends User{
    //public static void main(String arg[]){
     //   Manager m =new Manager();
     //   m.Manager_Menu();
   // }
    public void Manager_Menu(){
            Manager_Menu m = new Manager_Menu();
        }
    class Manager_Menu extends JFrame//内部类套了很多层
    {
        private String[][] getMenbers(){//从数据库获取会员信息返回
            String[][] menber = new String[100][3];

            Connection conn = null;
            PreparedStatement ps = null;
            ResultSet rs = null;

            try {
                conn = Sql.getConnection();

                conn.setAutoCommit(false);

                ps = conn.prepareStatement("select id,password,birthday from nai_cha_dian.user for update ");

                rs = ps.executeQuery();//获取结果集

                for(int i=0;rs.next();i++) {
                    menber[i][0] = rs.getString("id");
                    menber[i][1] = rs.getString("password");
                    menber[i][2] = rs.getString("birthday");
                }                conn.commit();
                // 提交事务（事务结束）

            } catch (SQLException throwables) {
                // 回滚事务（事务结束）
                try {
                    //assert conn != null;
                    conn.rollback();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return menber;
        }//从数据库获取会员信息返回
        public Manager_Menu(){//管理员界面//二级菜单
            setBounds(450, 200, 1000, 720);
            setResizable(false);
            setTitle("奶茶店管理系统>>管理员");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setVisible(true);
            //设置参数

            Container contentPane = getContentPane();//布局管理器

            JPanel textPanel = new JPanel();//BOSS子界面
            textPanel.setBackground(new Color(255,161,48));
            textPanel.setLayout(null);

            JLabel xinxi = new JLabel("Boss");
            xinxi.setFont(new Font("隶书", Font.BOLD, 150));
            xinxi.setBounds(190, 100, 2800, 150);
            xinxi.setForeground(new Color(104, 79, 64));
            textPanel.add(xinxi);

            JLabel xinxi2 = new JLabel("welcome!");
            xinxi2.setFont(new Font("隶书", Font.BOLD, 150));
            xinxi2.setBounds(190, 250, 2800, 150);
            xinxi2.setForeground(new Color(104, 79, 64));
            textPanel.add(xinxi2);

            //会员管理按钮
            JButton User = new JButton("会员管理");
            User.setBounds(100, 375, 200, 75);//按钮位置大小
            User.setBackground(new Color(245,136,5));
            User.setFont(new Font("微软雅黑", Font.BOLD, 20));
            User.addActionListener(new Manager_Menu.Manage_User());
            textPanel.add(User);

            //菜单管理按钮
            JButton Menus = new JButton("菜单管理");
            Menus.setBounds(300, 375, 200, 75);//按钮位置大小
            Menus.setBackground(new Color(245,136,5));
            Menus.setFont(new Font("微软雅黑", Font.BOLD, 20));
            Menus.addActionListener(new Manager_Menus());
            textPanel.add(Menus);

            //仓库管理按钮
            JButton Cepositories = new JButton("仓库管理");
            Cepositories.setBounds(100, 450, 200, 75);//按钮位置大小
            Cepositories.setBackground(new Color(245,136,5));
            Cepositories.setFont(new Font("微软雅黑", Font.BOLD, 20));
            Cepositories.addActionListener(new Manager_Menu.Manage_Cepositories());
            textPanel.add(Cepositories);

            //收益管理按钮
            JButton Revenue = new JButton("收益管理");
            Revenue.setBounds(300, 450, 200, 75);//按钮位置大小
            Revenue.setBackground(new Color(245,136,5));
            Revenue.setFont(new Font("微软雅黑", Font.BOLD, 20));
            Revenue.addActionListener(new Manager_Menu.Manage_Revenue());
            textPanel.add(Revenue);

            //返回按钮
            JButton back = new JButton("back<<");
            back.setBounds(800, 600, 150, 40);//按钮位置大小
            back.setBackground(new Color(245,136,5));
            back.setFont(new Font("微软雅黑", Font.BOLD, 20));
            back.addActionListener(new Manager_Menu.Manager_Menu_back());
            textPanel.add(back);
            contentPane.add(textPanel);
        }//管理员界面//二级菜单
        public class Manager_Menu_back implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();//关闭管理窗口,再打开登录界面
            new Login_Menu();//重新载入登陆界面
        }
    }




        //////////////////////////三级菜单/////////////////////////////////
        public class Manage_User implements ActionListener {//会员管理//三级菜单
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();//关闭二级菜单
                new Manage_User_Menu();//打开三级菜单
            }
            public class Manage_User_Menu extends JFrame{
                public Manage_User_Menu(){
                setBounds(450, 200, 1000, 720);
                setResizable(false);
                setTitle("奶茶店管理系统>>管理员>>会员管理");
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                setVisible(true);
                //setBounds(500, 200, 300, 300);
                //设置参数

                Container contentPane = getContentPane();//布局管理器

                JPanel textPanel = new JPanel();//会员管理界面
                textPanel.setBackground(new Color(255,161,48));
                textPanel.setLayout(null);

                String[] title={"  用户名  ", "  密码  ", "  生日  "};

                // 具体的各栏行记录 先用空的二维数组占位
                String[][] data=getMenbers();

                // 按用户自定的表头，创建模型
                DefaultTableModel model = new DefaultTableModel(data, title);

                // 创建一个table表
                JTable table = new JTable();

                // 将表格模型装入到table对象中
                table.setModel(model);
                table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // 将表格自动调整的状态给关闭掉

                // 将一个JScrollPane对象
                JScrollPane jscrollpane = new JScrollPane();
                jscrollpane.setPreferredSize(new Dimension(300, 200));
                jscrollpane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);  //设置水平滚动条需要时可见

                // 将table添加到JScrollPane面板中
                jscrollpane.setViewportView(table);

                jscrollpane.setBounds(735, 0, 250, 250);

                textPanel.add(jscrollpane);



                //返回按钮
                JButton back = new JButton("back<<");
                back.setBounds(800, 600, 150, 40);//按钮位置大小
                back.setBackground(new Color(245,136,5));
                back.setFont(new Font("微软雅黑", Font.BOLD, 20));
                back.addActionListener(new Manage_User_Menu.Manage_User_Menu_back());
                textPanel.add(back);

                contentPane.add(textPanel);
            }

            public class Manage_User_Menu_back implements ActionListener {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dispose();//关闭三级菜单
                    new Manager_Menu();//打开二级菜单
                }
            }
        }
    }//会员管理//三级菜单
        public class Manager_Menus implements ActionListener {//三级菜单
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();//关闭二级菜单
                new Manage_Menus_Menu();//打开三级菜单
            }

            public class Manage_Menus_Menu extends JFrame {//菜单管理界面
                public Manage_Menus_Menu(){
                setBounds(450, 200, 1000, 720);
                setResizable(false);
                setTitle("奶茶店管理系统>>管理员>>菜单管理");
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                setVisible(true);
                //setBounds(500, 200, 300, 300);
                //设置参数

                Container contentPane = getContentPane();//布局管理器

                JPanel textPanel = new JPanel();//菜单管理界面
                textPanel.setBackground(new Color(255,161,48));
                textPanel.setLayout(null);

                JLabel xinxi = new JLabel("饮品单");
                xinxi.setFont(new Font("隶书", Font.BOLD, 150));
                xinxi.setBounds(190, 100, 2800, 150);
                xinxi.setForeground(new Color(104, 79, 64));
                textPanel.add(xinxi);

                //增加菜品按钮
                JButton Updata = new JButton("修改饮品");
                Updata.setBounds(100, 375, 200, 75);//按钮位置大小
                Updata.setBackground(new Color(245,136,5));
                Updata.setFont(new Font("微软雅黑", Font.BOLD, 20));
                Updata.addActionListener(new Manage_Menus_Menu.Updata());
                textPanel.add(Updata);
                //////////////////////////////未完成项////////////////////////////////////////////////////////
/**
                //菜品按钮
                JButton Dedata = new JButton("饮品");
                Dedata.setBounds(300, 375, 200, 75);//按钮位置大小
                Dedata.setBackground(new Color(245,136,5));
                Dedata.setFont(new Font("微软雅黑", Font.BOLD, 20));
                //Dedata.addActionListener(new Manage_Menus.Dedata());
                textPanel.add(Dedata);
*/
                //显示菜单按钮
                JButton OutMenus = new JButton("显示菜单");
                OutMenus.setBounds(100, 450, 200, 75);//按钮位置大小
                OutMenus.setBackground(new Color(245,136,5));
                OutMenus.setFont(new Font("微软雅黑", Font.BOLD, 20));
                OutMenus.addActionListener(new Manage_Menus_Menu.OutMenus());
                textPanel.add(OutMenus);

                //////////////////////////////未完成项////////////////////////////////////////////////////////

/**
                //设置优惠按钮
                JButton SetSpecial = new JButton("设置优惠");
                SetSpecial.setBounds(300, 450, 200, 75);//按钮位置大小
                SetSpecial.setBackground(new Color(245,136,5));
                SetSpecial.setFont(new Font("微软雅黑", Font.BOLD, 20));
                //SetSpecial.addActionListener(new Manage_Menus.SetSpecial());
                textPanel.add(SetSpecial);
*/
                //返回按钮
                JButton back = new JButton("back<<");
                back.setBounds(800, 600, 150, 40);//按钮位置大小
                back.setBackground(new Color(245,136,5));
                back.setFont(new Font("微软雅黑", Font.BOLD, 20));
                back.addActionListener(new Manage_Menus_Menu.Manage_Menus_Menu_back());
                textPanel.add(back);

                contentPane.add(textPanel);
            }

            public class Manage_Menus_Menu_back implements ActionListener {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dispose();//关闭三级菜单
                    new Manager_Menu();//打开二级菜单
                }
            }
/////////////////////////////////四级菜单//////////////////////////////////////
                public class OutMenus  implements ActionListener {//三级菜单之功能//输出点茶单
                @Override
                public void actionPerformed(ActionEvent e) {
                    dispose();//关闭三级菜单
                    new Out_Menus();//打开四级菜单
                }
                public class  Out_Menus extends JFrame{//四级菜单//输出点茶单
                    public Out_Menus(){
                    String[][] menus = getMenus();

                    setBounds(450, 200, 881, 578);
                    setResizable(false);
                    setTitle("奶茶店管理系统>>管理员>>菜单管理>>显示菜单");
                    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                    Container contentPane = getContentPane();//布局管理器
                    contentPane.setLayout(null);


                     for(int i = 1;menus[i-1][0]!=null;i++)
                    {
                        final int j = i - 1;

                        JLabel xinxi = new JLabel(menus[j][0]+" ........");
                        xinxi.setFont(new Font("幼圆", Font.BOLD, 25));
                        xinxi.setBounds(270,(135+j*45), 350, 25);
                        contentPane.add(xinxi);

                        JButton jiageButton = new JButton(menus[j][1]);
                        jiageButton.setBounds(500,(135+j*45), 70, 25);//按钮位置大小
                        jiageButton.setBackground(new Color(255, 203, 140));
                        jiageButton.setFont(new Font("幼圆", Font.BOLD, 25));
                        jiageButton.setBorderPainted(false);//去掉边框
                        contentPane.add(jiageButton);

                        JButton jiageButton2 = new JButton(String.valueOf(Integer.parseInt(menus[j][1])+3));//大杯的价格为中杯+3

                        jiageButton2.setBounds(595,(135+j*45), 70, 25);//按钮位置大小
                        jiageButton2.setBackground(new Color(255, 203, 140));
                        jiageButton2.setFont(new Font("幼圆", Font.BOLD, 25));
                        jiageButton2.setBorderPainted(false);//去掉边框
                        contentPane.add(jiageButton2);
                        }
                    //返回按钮
                    JButton back = new JButton("back<<");
                    back.setBounds(700, 480, 150, 40);//按钮位置大小
                    back.setBackground(new Color(245,136,5));
                    back.setFont(new Font("微软雅黑", Font.BOLD, 20));
                    back.addActionListener(new Out_Menus.Out_Menus_back());
                    contentPane.add(back);

                    JLabel label_pic = new JLabel(new ImageIcon("C:\\Users\\15275\\IdeaProjects\\yin_pin_dian_guan_li\\images\\nai_cha_dan.jpg"));
                    getLayeredPane().add(label_pic, Integer.MIN_VALUE);
                    label_pic.setBounds(0,0,881, 578);
                    contentPane.add(label_pic, BorderLayout.CENTER);//插入背景图

                    setVisible(true);
                }

                public class Out_Menus_back implements ActionListener {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        dispose();//关闭四级菜单
                        new Manage_Menus_Menu();//打开三级菜单
                    }
                }
            }
            }//四级菜单

                public class Updata implements ActionListener {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        dispose();//关闭三级菜单
                         new Updata_Menu();//打开四级菜单
                    }
                    public class Updata_Menu extends JFrame{
                        JTextField yinpin;
                        JTextField jiage;
                        public Updata_Menu(){
                            setBounds(450, 200, 1000, 720);
                            setResizable(false);
                            setTitle("奶茶店管理系统>>管理员>>菜单管理>>修改菜单");
                            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                            setVisible(true);

                            Container contentPane = getContentPane();//布局管理器

                            JPanel textPanel = new JPanel();//更新饮品界面
                            textPanel.setBackground(new Color(255,161,48));
                            textPanel.setLayout(null);

                            JLabel xinxi3 = new JLabel("修改菜单");
                            xinxi3.setFont(new Font("隶书", Font.BOLD, 150));
                            xinxi3.setBounds(190, 100, 700, 150);
                            xinxi3.setForeground(new Color(104, 79, 64));
                            textPanel.add(xinxi3);

                            JLabel xinxi = new JLabel("饮品名：", JLabel.CENTER);
                            xinxi.setFont(new Font("宋体", 0, 50));
                            xinxi.setBounds(200, 300, 200, 50);//饮品名标签位置
                            xinxi.setForeground(new Color(104, 79, 64));
                            textPanel.add(xinxi);

                            JLabel xinxi2 = new JLabel("价  格：", JLabel.CENTER);
                            xinxi2.setFont(new Font("宋体", 0, 50));
                            xinxi2.setBounds(200, 360, 200, 50);//价格标签位置
                            xinxi2.setForeground(new Color(104, 79, 64));
                            textPanel.add(xinxi2);

                            yinpin = new JTextField(20);
                            yinpin.setBounds(400, 300, 350, 50);
                            textPanel.add(yinpin);

                            jiage = new JTextField(20);
                            jiage.setBounds(400, 360, 350, 50);
                            textPanel.add(jiage);

                            //更新按钮

                            JButton UpdataButton = new JButton("更新");
                            UpdataButton.setBounds(280, 500, 200, 75);//登录按钮位置大小
                            UpdataButton.setBackground(new Color(245,136,5));
                            UpdataButton.setFont(new Font("微软雅黑", Font.BOLD, 20));
                            UpdataButton.addActionListener(new Updata_Menu.Updata_u());

                            textPanel.add(UpdataButton);

                            //删除按钮

                            JButton DedataButton = new JButton("删除");
                            DedataButton.setBounds(500, 500, 200, 75);//登录按钮位置大小
                            DedataButton.setBackground(new Color(245,136,5));
                            DedataButton.setFont(new Font("微软雅黑", Font.BOLD, 20));
                            DedataButton.addActionListener(new Updata_Menu.Updata_d());

                            textPanel.add(DedataButton);

                            //返回按钮
                            JButton back = new JButton("back<<");
                            back.setBounds(800, 600, 150, 40);//按钮位置大小
                            back.setBackground(new Color(245,136,5));
                            back.setFont(new Font("微软雅黑", Font.BOLD, 20));
                            back.addActionListener(new Updata_Menu.Updata_back());
                            contentPane.add(back);

                            contentPane.add(textPanel);


                        }public class Updata_back implements ActionListener {
                            @Override
                            public void actionPerformed(ActionEvent e) {

                                dispose();//关闭四级菜单
                                new Manage_Menus_Menu();
                            }
                        }

                        public class Updata_u implements ActionListener {//更新茶品，品名存在则更新价格，不存在则添加
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                String gety = yinpin.getText();
                                String getj = jiage.getText();

                                boolean zhuceSuccess = false;
                                Connection conn1 = null;
                                PreparedStatement ps1 = null;
                                ResultSet rs1 = null;

                                zhuceSuccess = cha_xun(gety);
                                try {
                                    conn1 = Sql.getConnection();
                                    conn1.setAutoCommit(false);

                                    if(!zhuceSuccess) {
                                        ps1 = conn1.prepareStatement("INSERT INTO nai_cha_dian.cai_dan ( cname,cprice)VALUES(?,?);");
                                        ps1.setString(1, gety);
                                        ps1.setString(2, getj);
                                        }else{
                                        ps1 = conn1.prepareStatement("Update nai_cha_dian.cai_dan set cprice =? where cname = ?");
                                        ps1.setString(1, getj);
                                        ps1.setString(2, gety);
                                        }
                                    int row = ps1.executeUpdate();//返回已经添加了多少条数据//需执行此语句才能正确添加数据？？

                                    //System.out.println(row);
                                    if (row != 0)//添加成功则。。
                                        JOptionPane.showMessageDialog(null, "更新成功，若饮品名存在则已替换价格");

                                    // 提交事务（事务结束）
                                    conn1.commit();
                                } catch (SQLException throwables) {
                                    // 回滚事务（事务结束）
                                    try {
                                        //assert conn1 != null;
                                        conn1.rollback();
                                    } catch (SQLException e1) {
                                        e1.printStackTrace();
                                    }
                                    throwables.printStackTrace();

                                } finally {
                                    Sql.close(conn1, ps1, rs1);
                                }

                            }

                            private boolean cha_xun(String yinpin) {
                                boolean chaxunSuccess = false;
                                Connection conn = null;
                                PreparedStatement ps = null;
                                ResultSet rs = null;
                                try {
                                    conn = Sql.getConnection();

                                    conn.setAutoCommit(false);

                                    ps = conn.prepareStatement("select cname from nai_cha_dian.cai_dan where cname = ?for update ");
                                    ps.setString(1,yinpin);

                                    rs = ps.executeQuery();//获取结果集

                                    while(rs.next()) {
                                        chaxunSuccess = true;//如果查询到了，那么接下来的插入将终止
                                    }
                                    // 提交事务（事务结束）
                                    conn.commit();
                                } catch (SQLException throwables) {
                                    // 回滚事务（事务结束）
                                    try {
                                        //assert conn != null;
                                        conn.rollback();
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    }
                                    throwables.printStackTrace();

                                } finally {
                                    Sql.close(conn,ps,rs);
                                }
                                return chaxunSuccess;
                            }
                        }

                        public class Updata_d implements ActionListener {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                String gety = yinpin.getText();
                                String getj = jiage.getText();

                                Connection conn1 = null;
                                PreparedStatement ps1 = null;
                                ResultSet rs1 = null;

                                try {
                                    conn1 = Sql.getConnection();
                                    conn1.setAutoCommit(false);

                                        ps1 = conn1.prepareStatement("delete from nai_cha_dian.cai_dan where cname = ?");
                                        ps1.setString(1, gety);
                                    int row = ps1.executeUpdate();//返回已经添加了多少条数据//需执行此语句才能正确添加数据？？

                                    //System.out.println(row);
                                    if (row != 0)//添加成功则。。
                                        JOptionPane.showMessageDialog(null, "删除成功");

                                    // 提交事务（事务结束）
                                    conn1.commit();
                                } catch (SQLException throwables) {
                                    // 回滚事务（事务结束）
                                    try {
                                        //assert conn1 != null;
                                        conn1.rollback();
                                    } catch (SQLException e1) {
                                        e1.printStackTrace();
                                    }
                                    throwables.printStackTrace();

                                } finally {
                                    Sql.close(conn1, ps1, rs1);
                                }

                            }
                        }


                    }
                }
            }
    }//菜单管理//三级菜单



//////////////////////////////未完成项////////////////////////////////////////////////////////
//////////////////////////////未完成项////////////////////////////////////////////////////////
//////////////////////////////未完成项////////////////////////////////////////////////////////

        public class Manage_Cepositories implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        }//仓库管理//三级菜单？？？？？


        public class Manage_Revenue implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        }//收益管理//三级菜单？？？？？


    }
}
