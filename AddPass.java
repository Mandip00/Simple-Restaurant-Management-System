/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minor;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import static minor.PassEnc.passen;

/**
 *
 * @author dell
 */
public class AddPass extends JFrame implements ActionListener
{
    JLabel empid,password;
    JComboBox cbeid;
    JPasswordField txtpass;
    JButton btnadd;
    
    DBconnection db = new DBconnection();
    PreparedStatement ps;
    ResultSet rs;
    Statement stmt;
    public AddPass()
    {
        empid = new JLabel("Empoyee ID");
        password = new JLabel("Password");
       // txtempid = new JTextField(10);
        cbeid = new JComboBox();
        txtpass = new JPasswordField(25);
        btnadd = new JButton("Add");
        
        setSize(350, 300);
        setVisible(true);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        add(empid);
        empid.setBounds(20, 30, 100, 25);
        add(cbeid).setBounds(150, 30, 120, 25);
        add(password).setBounds(30,70 ,100  ,25);
        add(txtpass).setBounds(150,70,120,25);
        add(btnadd).setBounds(200,150,100,50);
        btnadd.addActionListener(this);
        
         try
        {
            stmt=db.cn.createStatement();
            rs=stmt.executeQuery("select * from employees");
            while (rs.next())
            {
                cbeid.addItem(rs.getString(1));
                
               
            }
        }
    catch (Exception er)
            {
                System.out.println("error"+er);
            }
          ImageIcon img = new ImageIcon("images/pass64.png");
        setIconImage(img.getImage());
        setTitle("Add Password");
    }
    public void actionPerformed (ActionEvent ae)
    {
        if (ae.getSource()== btnadd)
        {
            String empid = cbeid.getSelectedItem().toString();
            String pass = txtpass.getText();
            try
            {
                PreparedStatement pstm = db.cn.prepareStatement("delete from login where empid = ?");
                pstm.setString(1, cbeid.getSelectedItem().toString());
                pstm.executeUpdate();
                
                ps = db.cn.prepareStatement("insert into login (empid,Password) values(?,?)");
                ps.setString(1, empid);
                ps.setString(2, passen(pass));
                 int result=ps.executeUpdate();
                if(result>0){
                JOptionPane.showMessageDialog(null, "Record added !");
                }
                else{
                 JOptionPane.showMessageDialog(null, "Record not added !");
                }
            }
             
        
            catch (Exception ex)
            {
                ex.getStackTrace();
                JOptionPane.showMessageDialog(null, "Error in adding"+ex);
            }
            this.dispose();
            new AddPass().setVisible(true);

         }
    }
    public static void main(String[] args) {
        new AddPass().setVisible(true);
    }
    
    
}
