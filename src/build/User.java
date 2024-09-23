package build;
//import ustc.java.jdbc.DBUtil.DBUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static java.lang.Integer.parseInt;

public class User extends Login{
    /*
    String Name,Sex,Birthday;
    char[] Account;
    int Password;
    */
    public void yan_zheng(String account,String password,int Make){
        boolean loginSuccess = false;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            switch (Make) {
                case 1://店长
                //按输入的Account在数据库里搜索，如果有则核对Passwrod,正确则登录成功
                    if(("".equals(String.valueOf(account)))&&("".equals(password)))//店长账号密码
                        loginSuccess = true;
                    break;
                case 2://游客
                    JOptionPane.showMessageDialog(null, "欢迎光临");
                    loginSuccess = true;
                    //作为游客点击登录可以直接进入（简化冗余信息）
                    //登录成功进入下一界面///////////////////////
                    //User.zhu_ce();
                    break;
                case 3://用户
                    conn = Sql.getConnection();
                // 开启事务
                    conn.setAutoCommit(false);

                    ps = conn.prepareStatement("select id,password from nai_cha_dian.user where id = ? and password = ? for update ");
                    ps.setString(1,account);
                    ps.setString(2, password);

                    rs = ps.executeQuery();//获取结果集

                    while(rs.next()) {
                        System.out.println(rs.getString("id") );
                        loginSuccess = true;
                    }
                    // 提交事务（事务结束）
                    conn.commit();
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Cmake错误");
                    break;
        }
        } catch (SQLException throwables) {
            // 回滚事务（事务结束）
            try {
                conn.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            throwables.printStackTrace();
        } finally {
            Sql.close(conn,ps,rs);
        }

        if(loginSuccess) {
            JOptionPane.showMessageDialog(null, "登陆成功，请等待……");
            //登录成功进入下一界面///////////////////////
            zi_jie_mian(Make);
            }else {//登录失败重新则加载登录界面
            JOptionPane.showMessageDialog(null, account + password + "登录失败");
            back();
        }
    }//验证账号密码

    private void back() {
        Login_Menu frame=new Login_Menu();//重新载入登陆界面
    }

    private void zi_jie_mian(int make) {//子界面导入
            switch (make){
                case 1://BOSS
                    Manager m = new Manager();
                    m.Manager_Menu();
                    break;
                case 2://游客
                    Visitor vv = new Visitor();
                    break;
                case 3://用户
                    Member mm = new Member();

                    break;

            }

    }//子界面导入

    private boolean cha_xun(String yonghu) {//查询用户是否存在，存在则返回ture
        boolean chaxunSuccess = false;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
        conn = Sql.getConnection();

        conn.setAutoCommit(false);

        ps = conn.prepareStatement("select id,password from nai_cha_dian.user where id = ?for update ");
        ps.setString(1,yonghu);

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
    }//查询用户是否存在，存在则返回ture

    public boolean zhu_ce(String yonghu, String password) {//注册会员
        Icon icon = null;
        String birthday;
        while (true) {
            birthday = (String) JOptionPane.showInputDialog(null, "请输入你的生日：\n", "注册", JOptionPane.PLAIN_MESSAGE, icon, null, "XXXX-XX-XX");
        if((birthday.compareTo("1000-00-00")<0)||(birthday.compareTo("2023-00-00")>0))
            JOptionPane.showMessageDialog(null, "输入错误，请重新输入");
        else
            break;}

        boolean zhuceSuccess = false;
        Connection conn1 = null;
        PreparedStatement ps1 = null;
        ResultSet rs1 = null;
// 查询数据
        zhuceSuccess = cha_xun(yonghu);
// 插入数据
            if(!zhuceSuccess)
                try {
                    conn1 = Sql.getConnection();
                    conn1.setAutoCommit(false);

                    ps1 = conn1.prepareStatement("INSERT INTO nai_cha_dian.user ( id,password,birthday)VALUES(?,?,?);");
                    ps1.setString(1, yonghu);
                    ps1.setString(2, password);
                    ps1.setString(3, birthday);
                    int row = ps1.executeUpdate();//返回已经添加了多少条数据//需执行此语句才能正确添加数据？？

                    //System.out.println(row);
                    if(row!=0)//添加成功则。。
                    zhuceSuccess = true;

                    // 提交事务（事务结束）
                    conn1.commit();
                    JOptionPane.showMessageDialog(null, "注册成功，正在登录...");
                } catch (SQLException throwables) {
                    // 回滚事务（事务结束）
                    try {
                        //assert conn1 != null;
                        conn1.rollback();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    throwables.printStackTrace();

                } finally {
                    Sql.close(conn1, ps1, rs1);
                }
            else{//注册失败，因为存在相同的用户
                JOptionPane.showMessageDialog(null, "注册失败，用户名或密码已存在，请重新输入");
                zhuceSuccess = false;
            }

            return zhuceSuccess;
    }//注册会员


    public String[][] getMenus() {//获取点茶单
        String[][] getMenus = new String[100][2];
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = Sql.getConnection();

            conn.setAutoCommit(false);

            ps = conn.prepareStatement("select cname,cprice from nai_cha_dian.cai_dan for update ");

            rs = ps.executeQuery();//获取结果集

            for(int i=0;rs.next();i++) {
                getMenus[i][0] = rs.getString("cname");
                getMenus[i][1] = rs.getString("cprice");
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
        return getMenus;
    }//获取点茶单


    public class Dianchadan {

        int[] yi_dian = new int[100], jiage = new int[100];
        String[][] menus = getMenus();
        String[][] diandan = new String[menus.length*2][2];
        int sum = 0,n = 0;
        int diandanlen;
        String Name;
        Dianchadan(String name){
            Name = name;
            new OutMenus();
        }
        public void getdiandan(){
            for (int i = 0 ,j = 0;i<menus.length;i++,j++)
                if(menus[i][1]!=null)//parseInt()不能为null
                {
                    diandan[j][0] = menus[i][0] + "中杯";
                    diandan[j][1] = menus[i][1];

                    diandan[++j][0] = menus[i][0] + "大杯";
                    diandan[j][1] = String.valueOf(Integer.parseInt(menus[i][1]) + 3);
                }//设置点单名单

            int i = 0;
            do{
                i++;
            }while (diandan[i][0]!=null);
            diandanlen = i;
        }
        class OutMenus extends JFrame {

            public OutMenus() {

                setBounds(450, 200, 881, 578);
                setResizable(false);
                setTitle("奶茶店管理系统>>"+Name);
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                Container contentPane = getContentPane();//布局管理器
                contentPane.setLayout(null);


                for (int i = 1; menus[i - 1][0] != null; i++) {
                    final int j = i - 1;

                    JLabel xinxi = new JLabel(menus[j][0] + " ........");
                    xinxi.setFont(new Font("幼圆", Font.BOLD, 25));
                    xinxi.setBounds(270, (135 + j * 45), 350, 25);
                    contentPane.add(xinxi);

                    JButton jiageButton = new JButton(menus[j][1]);
                    jiageButton.setBounds(500, (135 + j * 45), 70, 25);//按钮位置大小
                    jiageButton.setBackground(new Color(255, 203, 140));
                    jiageButton.setFont(new Font("幼圆", Font.BOLD, 25));
                    jiageButton.setBorderPainted(false);//去掉边框
                    jiageButton.addActionListener(new Dianchadan.Sum(parseInt(menus[j][1])));//实现点击一次则将这次对应的价格加起来
                    contentPane.add(jiageButton);

                    JButton jiageButton2 = new JButton(String.valueOf(parseInt(menus[j][1]) + 3));//大杯的价格为中杯+3

                    jiageButton2.setBounds(595, (135 + j * 45), 70, 25);//按钮位置大小
                    jiageButton2.setBackground(new Color(255, 203, 140));
                    jiageButton2.setFont(new Font("幼圆", Font.BOLD, 25));
                    jiageButton2.setBorderPainted(false);//去掉边框
                    jiageButton2.addActionListener(new Dianchadan.Sum(parseInt(menus[j][1]) + 3));
                    contentPane.add(jiageButton2);
                }
                //结算按钮
                JButton jiesuan = new JButton("结算");
                jiesuan.setBounds(700, 440, 150, 40);//按钮位置大小
                jiesuan.setBackground(new Color(245, 136, 5));
                jiesuan.setFont(new Font("微软雅黑", Font.BOLD, 20));
                jiesuan.addActionListener(new Dianchadan.OutMenus.Member_OutMenus_jiesuan());
                contentPane.add(jiesuan);

                //返回按钮
                JButton back = new JButton("back<<");
                back.setBounds(700, 480, 150, 40);//按钮位置大小
                back.setBackground(new Color(245, 136, 5));
                back.setFont(new Font("微软雅黑", Font.BOLD, 20));
                back.addActionListener(new Dianchadan.OutMenus.Member_OutMenus_back());
                contentPane.add(back);

                JLabel label_pic = new JLabel(new ImageIcon("C:\\Users\\15275\\IdeaProjects\\yin_pin_dian_guan_li\\images\\nai_cha_dan.jpg"));
                getLayeredPane().add(label_pic, Integer.MIN_VALUE);
                label_pic.setBounds(0, 0, 881, 578);
                contentPane.add(label_pic, BorderLayout.CENTER);//插入背景图

                setVisible(true);
            }
            public class Member_OutMenus_back implements ActionListener {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dispose();
                    new Login_Menu();
                }
            }

            public class Member_OutMenus_jiesuan implements ActionListener {
                @Override
                public void actionPerformed(ActionEvent e) {
                    for(int i = 0; i< yi_dian.length; i++)
                        if(yi_dian[i]==1)
                            sum += jiage[i];//结算时将yi_dian数组完全遍历，把yi_dian数组中置1的位置在jiage数组中找到相应价格，然后相加
                    //结算
                    dispose();
                    new Jiesuan_Menus();
                }


            }
        }

        class Jiesuan_Menus extends JFrame{

            public Jiesuan_Menus()
            {
                setBounds(450, 100, 578, 881);
                setResizable(false);
                setTitle("奶茶店管理系统>>"+Name+">>结算");
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                setVisible(true);
                //设置参数

                Container contentPane = getContentPane();//布局管理器

                JPanel textPanel = new JPanel();//结算界面
                textPanel.setBackground(new Color(255,161,48));
                textPanel.setLayout(null);

                String[] title={"  茶名   " , "  价格  "};

                getdiandan();

                String[][]data = new String[yi_dian.length][2];
                for (int i = 0,j = 0; i < yi_dian.length ; i++)
                {
                    if(yi_dian[i] == 1) {
                        if(diandan[i][0]==null) {
                            int k = i % diandanlen;//如果按back返回再重复选择并且保存上次记录，则需将yi_dian中找到的1的位置对diandan的真实长度取余，即可得到正确的相对应位置
                            data[j][0] = diandan[k][0];
                            data[j++][1] = diandan[k][1];
                        }else {
                            data[j][0] = diandan[i][0];
                            data[j++][1] = diandan[i][1];
                        }
                    }
                }

                //for (int i=0;i<menus.length;i++)
                //    System.out.println(menus[i][0]+menus[i][1]);
                for (int i=0;i<10;i++)
                System.out.println(yi_dian[i]);
                for (int i=0;i<diandan.length;i++)
                    if(diandan[i][0]!=null)
                    System.out.println(diandan[i][0]+diandan[i][1]);
                for (int i=0;i<data.length;i++)
                    if(data[i][0]!=null)
                    System.out.println(data[i][0]+data[i][1]);//测试

                //System.out.println();


                DefaultTableModel model = new DefaultTableModel(data, title);

                // 创建一个table表
                JTable table = new JTable();

                table.setRowHeight(30);// 设置表格行宽

                table.setAutoResizeMode(125);// 以下设置表格列宽

                JTableHeader head = table.getTableHeader(); // 创建表格标题对象
                head.setPreferredSize(new Dimension(125, 40));// 设置表头大小
                head.setFont(new Font("楷体", Font.PLAIN, 18));// 设置表格字体
                head.setBackground(new Color(255, 203, 140));


                // 将表格模型装入到table对象中
                table.setModel(model);
                table.setBackground(new Color(255, 203, 140));
                //table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // 将表格自动调整的状态给关闭掉

                // 将一个JScrollPane对象
                JScrollPane jscrollpane = new JScrollPane();
                jscrollpane.setPreferredSize(new Dimension(250, 250));
                jscrollpane.setBackground(new Color(255, 203, 140));
                jscrollpane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);  //设置水平滚动条需要时可见

                // 将table添加到JScrollPane面板中
                jscrollpane.setViewportView(table);

                jscrollpane.setBounds(50, 50, 250, 500);

                textPanel.add(jscrollpane);


                JLabel xinxi = new JLabel("总价："+sum);
                xinxi.setFont(new Font("隶书", Font.BOLD, 50));
                xinxi.setBounds(300, 600, 300, 50);
                xinxi.setForeground(new Color(104, 79, 64));
                textPanel.add(xinxi);

                JButton xiadan = new JButton("下单");
                xiadan.setBounds(300, 700, 100, 30);//按钮位置大小
                xiadan.setBackground(new Color(245,136,5));
                xiadan.setFont(new Font("微软雅黑", Font.BOLD, 20));
                xiadan.addActionListener(new Xiadan());
                textPanel.add(xiadan);
//返回按钮
                JButton back = new JButton("back<<");
                back.setBounds(300, 750, 100, 30);//按钮位置大小
                back.setBackground(new Color(245,136,5));
                back.setFont(new Font("微软雅黑", Font.BOLD, 20));
                back.addActionListener(new Dianchadan.Jiesuan_Menus.Jiesuan_back());
                textPanel.add(back);
                contentPane.add(textPanel);
            }

            public class Jiesuan_back implements ActionListener {
                @Override
                public void actionPerformed(ActionEvent e) {
                    sum = 0;
                    dispose();
                    new OutMenus();
                }
            }

            public class Xiadan implements ActionListener {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JOptionPane.showMessageDialog(null, "下单成功，请稍等");
                }
            }
        }

        public class Sum implements ActionListener {
            int i = 0;
            public Sum(int ss){
                i = n++;//在编译时每个按钮对应的发生事件相同，不同的是每个按钮构造时的i不同
                jiage[i] = ss;//构造时传入对应价格放在相应位置上
            }
            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println(i);
                yi_dian[i] = 1;//事件触发时，因为每个按钮对应的i不同，则可以对相对应的位置置1，表示已点
                JOptionPane.showMessageDialog(null, "点单成功");
            }
        }
    }


}
