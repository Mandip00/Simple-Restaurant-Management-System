/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minor;

import java.awt.event.*;
import java.awt.*;
import static java.awt.Color.*;
import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;

/**
 *
 * @author dell
 */
public class Main extends JFrame implements ActionListener,WindowListener {

    JMenuBar menubar;
    JMenu open, records, menu, daybook, sales, exit;
    JMenuItem takeaway, eatin, showrecords, updaterecords, showmenu, updatemenu, msales, mexit, logout, addpass,daybooks;

    JToolBar toolbar;
    JButton btntakeaway, btneatin, btnrecords, btnmenu,btndaybook, btnsales, btnexit, btnlogout;
    JButton btntakeaway1, btneatin1, btnrecords1, btnmenu1, btnsales1, btnexit1, btnlogout1;

    JPanel jp, jp1,jpi;
    JScrollPane jsp;
    JLabel jl,jlimg;

    public Main() {
        
        
        setIconImage(getIconImage());

        addWindowListener(this);
       // setContentPane(new JLabel(new ImageIcon("images/splash_screen.png")));
        menubar = new JMenuBar();
        open = new JMenu("Open");
        records = new JMenu("Records");
        menu = new JMenu("Menu");
        daybook = new JMenu("Daybook");
        sales = new JMenu("Sales");
        exit = new JMenu("Exit");
        takeaway = new JMenuItem("Bill");
        takeaway.addActionListener(this);
        //eatin = new JMenuItem(new ImageIcon("images/BRANCH.png"));
        showrecords = new JMenuItem("Show Records");
        showrecords.addActionListener(this);
        updaterecords = new JMenuItem("Update Records");
        updaterecords.addActionListener(this);
        addpass = new JMenuItem("Add pass");
        addpass.addActionListener(this);
        showmenu = new JMenuItem("Show Menu");
        showmenu.addActionListener(this);
        updatemenu = new JMenuItem("Update Menu");
        updatemenu.addActionListener(this);
        daybooks=new JMenuItem("Daybook");
        daybooks.addActionListener(this);
        msales = new JMenuItem("Sales");
        msales.addActionListener(this);
        mexit = new JMenuItem("Exit");
        mexit.addActionListener(this);
        logout = new JMenuItem("Logout");
        logout.addActionListener(this);
        btntakeaway = new JButton(new ImageIcon("images/takeaway24.png"));
        //btneatin= new JButton(new ImageIcon("images/BRANCH.png"));
        btnrecords = new JButton(new ImageIcon("images/records21.png"));
        btnmenu = new JButton(new ImageIcon("images/menu24.png"));
        btnsales = new JButton(new ImageIcon("images/s24.png"));
        btndaybook = new JButton(new ImageIcon("images/daybook24.png"));
        btnexit = new JButton(new ImageIcon("images/exit32.png"));
        btnlogout = new JButton(new ImageIcon("images/logout24.png"));
        toolbar = new JToolBar("Toolbar");
       
        btntakeaway1 = new JButton(new ImageIcon("images/note32.png"));
        //btneatin1= new JButton(new ImageIcon("images/BRANCH.png"));
        btnrecords1 = new JButton(new ImageIcon("images/records32.png"));
        btnsales1 = new JButton(new ImageIcon("images/s32.png"));
        btnmenu1 = new JButton(new ImageIcon("images/menu32.png"));
        btnexit1 = new JButton(new ImageIcon("images/exit32.png"));
        btnlogout1 = new JButton(new ImageIcon("images/logout32.png"));

        //int v = ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
        //int h = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;

        //jsp = new JScrollPane(v, h);
        //jpi = new JPanel();
        //jl = new JLabel(new ImageIcon("images/splash_screen.png"));
       
        ////////////////////////////////////////////////////////////////
        setJMenuBar(menubar);

        menubar.add(open);
        open.setMnemonic('O');

        menubar.add(records);
        records.setMnemonic('R');

        menubar.add(menu);
        menu.setMnemonic('M');

        menubar.add(daybook);

        menubar.add(sales);

        menubar.add(exit);

        open.add(takeaway);

        records.add(showrecords);
        showrecords.addActionListener(this);

        records.add(updaterecords);
        updaterecords.addActionListener(this);

        records.add(addpass);
        addpass.addActionListener(this);

        menu.add(showmenu);

        menu.add(updatemenu);
        
        daybook.add(daybooks);

        sales.add(msales);

        exit.add(mexit);
        mexit.setMnemonic('X');
        mexit.addActionListener(this);
        exit.add(logout);
        logout.addActionListener(this);
        logout.setMnemonic('O');

        add(toolbar, BorderLayout.NORTH);

        toolbar.setBounds(0, 25, 150, 1340);

        toolbar.add(btntakeaway);
        btntakeaway.setToolTipText("Take Away");
        btntakeaway.addActionListener(this);

        /*toolbar.add(btneatin);
         btneatin.setToolTipText("Eat in");
         btneatin.addActionListener(this);
         */
        toolbar.add(btnrecords);
        btnrecords.setToolTipText("Records");
        btnrecords.addActionListener(this);

        toolbar.add(btnmenu);
        btnmenu.setToolTipText("Menu");
        btnmenu.addActionListener(this);
        
        toolbar.add(btndaybook);
        btndaybook.setToolTipText("Daybook");
        btndaybook.addActionListener(this);

        toolbar.add(btnsales);
        btnsales.setToolTipText("Sales");
        btnsales.addActionListener(this);

        toolbar.add(btnlogout);
        btnlogout.setToolTipText("Logout");
        btnlogout.addActionListener(this);

        toolbar.add(btnexit);
        btnexit.setToolTipText("Exit");
        btnexit.addActionListener(this);
        ////////////////////////////////////////////////////////////////
        jlimg = new JLabel(new ImageIcon("images/D.jpg"));
        this.add(jlimg);
        //jlimg.setBounds(0, 0, 1500, 1500);
        
        /*add(jpi);
        jpi.setPreferredSize(new Dimension(300,400));
        jpi.add(jl);
        jpi.add(jp);*/
        
        jp = new JPanel();
        add(jp, BorderLayout.SOUTH);
        //jp.setBounds(10, 20, 300, 800);
        jp.setBackground(Color.darkGray);
        jp.setPreferredSize(new Dimension(100,120));
        
        
        
        jp.add(btntakeaway1);//, BorderLayout.CENTER);
        //btntakeaway1.setBounds(20, 350, 100, 50);
        btntakeaway1.addActionListener(this);
        btntakeaway1.setToolTipText("Bill");
        btntakeaway1.setPreferredSize(new Dimension(100,50));

        /* jp.add(btneatin1);
         btneatin1.setBounds(160, 70, 100, 50);
         btneatin1.setToolTipText("Eat in");
         btneatin1.addActionListener(this);
         */
        jp.add(btnrecords1);
       // btnrecords1.setBounds(160, 70, 100, 50);
        btnrecords1.addActionListener(this);
        btnrecords1.setToolTipText("Records");
        btnrecords1.setPreferredSize(new Dimension(100,50));

        jp.add(btnmenu1);
        //btnmenu1.setBounds(300, 70, 100, 50);
        btnmenu1.addActionListener(this);
        btnmenu1.setToolTipText("Menu");
        btnmenu1.setPreferredSize(new Dimension(100,50));
        
        jp.add(btnsales1);
        //btnsales1.setBounds(4400, 70, 100, 50);
        btnsales1.addActionListener(this);
        btnsales1.setToolTipText("Sales");
        btnsales1.setPreferredSize(new Dimension(100,50));
        
        jp.add(btnlogout1);
        //btnlogout1.setBounds(580, 70, 100, 50);
        btnlogout1.setToolTipText("Logout");
        btnlogout1.addActionListener(this);
        btnlogout1.setPreferredSize(new Dimension(100,50));
        
        jp.add(btnexit1);
        //btnexit1.setBounds(720, 70, 100, 50);
        btnexit1.setToolTipText("Exit");
        btnexit1.addActionListener(this);
        btnexit1.setPreferredSize(new Dimension(100,50));
        
        setVisible(true);
        setSize(500,500);
        setExtendedState(MAXIMIZED_BOTH);
        //setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setTitle("Restaurant Mangement System");
        ImageIcon img = new ImageIcon("images/d64.png");
        setIconImage(img.getImage());
        this.getContentPane().setBackground(gray);
        
       
    }

    public void actionPerformed(ActionEvent ae) {
        try {
            if (ae.getSource() == takeaway) {
                new Bill().setVisible(true);
            }
            if (ae.getSource() == daybooks)
            {
                new dbook().setVisible(true);
            }

            if (ae.getSource() == showrecords) {
                new ShowRecords().setVisible(true);
            }

            if (ae.getSource() == updaterecords) {
                new SLogin().setVisible(true);
            }
            if (ae.getSource() == addpass) {
                
                new AddPass().setVisible(true);
            }
            if (ae.getSource() == logout) {
                UnloadWindow1();
            }
            if (ae.getSource() == showmenu) {
                new ShowMenu().setVisible(true);
            }
            if (ae.getSource() == updatemenu) {
                new SLogin1().setVisible(true);
            }
            if (ae.getSource() == msales)
            {
                new graph().setVisible(true);
            }

            if (ae.getSource() == mexit) {
                UnloadWindow();
            }
            if (ae.getSource() == btntakeaway) {
                new Bill().setVisible(true);
            }

            if (ae.getSource() == btnrecords) {
                new Manage().setVisible(true);
            }
            if (ae.getSource() == btnmenu) {
                new menuu().setVisible(true);
            }
            if (ae.getSource() == btndaybook)
            {
                new dbook().setVisible(true);
            }
            if (ae.getSource() == btnsales) {
                new graph().setVisible(true);

            }
            if (ae.getSource() == btnlogout) {
               UnloadWindow1();
            }
            if (ae.getSource() == btnexit) {
                UnloadWindow();
            }
            if (ae.getSource() == btntakeaway1) {
                new Bill().setVisible(true);
            }

            if (ae.getSource() == btnrecords1) {
                new Manage().setVisible(true);
            }
            if (ae.getSource() == btnmenu1) {
                new menuu().setVisible(true);
            }
            if (ae.getSource() == btnsales1) {
                new graph().setVisible(true);

            }
            if (ae.getSource() == btnlogout1) {
                UnloadWindow1();

            }
            if (ae.getSource() == btnexit1) {
                UnloadWindow();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error");
        }
    }
    
        public void windowOpened(WindowEvent e){
	}
	public void windowClosing(WindowEvent e){
		UnloadWindow();
	}
	public void windowClosed(WindowEvent e){
	}
	public void windowIconified(WindowEvent e){
	}
	public void windowDeiconified(WindowEvent e){
	}
	public void windowActivated(WindowEvent e){
	}
	public void windowDeactivated(WindowEvent e){}
    
      
        
    protected void UnloadWindow() {
        String ObjButtons[] = {"Yes", "No"};
        int PromptResult = JOptionPane.showOptionDialog(null, "Are you sure you want to exit?", "", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, ObjButtons, ObjButtons[1]);
        if (PromptResult == 0) {
            System.exit(0);
        }
    }
     
     protected void UnloadWindow1() {
        String ObjButtons[] = {"Yes", "No"};
        int PromptResult = JOptionPane.showOptionDialog(null, "Are you sure you want to logout?", "", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, ObjButtons, ObjButtons[1]);
        if (PromptResult == 0) {
            this.dispose();
            new LoginGUI().setVisible(true);
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        }
        new Main();

    }
}
