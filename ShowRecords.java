package minor;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.*;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import static javax.swing.JFrame.setDefaultLookAndFeelDecorated;
import javax.swing.event.*; //for documentlistener
import javax.swing.text.*;
import javax.swing.table.*;

public class ShowRecords extends JFrame  implements ActionListener
{
        Connection con;
	Statement stmt;
	PreparedStatement pstmt;
	ResultSet rs,rs1;
	
	JMenuBar menubar;
	
	JTextField searchfield,txtsearch;
        
	JButton back;
	//JComboBox cb;
        JPanel jp;
	
	DefaultTableModel model = new DefaultTableModel();
	JTable table = new JTable(model);
		
	public ShowRecords() 
	{
	
           
               
                back=new JButton("Back");		
		menubar = new JMenuBar();
                searchfield = new JTextField(30);
                jp = new JPanel();
		
                add(menubar,BorderLayout.NORTH);
                
		
                menubar.add(searchfield);
                menubar.setBounds(0, 0, 700, 25);
                
                add(jp).setBounds(0,26, 790, 25);
                jp.setVisible(true);
                jp.add(back);
                back.setBounds(60, 30, 10, 25);
               
                
               Dimension x = getMaximumSize();
              // back.setBounds(600,0,100,25);
               back.addActionListener(this);
                
		searchfield.getDocument().addDocumentListener(new MyDocumentListener());
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
                        con=DriverManager.getConnection("jdbc:mysql://localhost:3306/minor","root","");
			stmt=con.createStatement();
			rs=stmt.executeQuery("select empid,name,phonenumber from employees order by name");
						
			table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			model.addColumn("Employee ID");
			model.addColumn("Name");
			model.addColumn("Phone Number");
                        
			while(rs.next())
				{
					model.addRow(new Object[] { rs.getString(1),rs.getString(2),rs.getString(3)});
				}
			
			int v = ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
			int h = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
			JScrollPane scrollPane = new JScrollPane(table,v,h);
                        add(scrollPane, BorderLayout.SOUTH);
			table.updateUI();
		
		}	
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null,"No Record Found.","Display",JOptionPane.INFORMATION_MESSAGE);
		}
		
		setVisible(true);
		setSize(800,600);
		setTitle("Record Display");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                setLocationRelativeTo(null);
	}
	    public void actionPerformed (ActionEvent ae)
        {
            try
            {
            if (ae.getSource()==back)
            {
                this.dispose();
                new Manage().setVisible(true);
                
            }
        }
            catch (Exception e)
            {
                JOptionPane.showMessageDialog(null,"Error:"+e);
            }
        }
       
		
	
	
	
	
	
	class MyDocumentListener implements DocumentListener
		{
		
			
			public void update(DocumentEvent de) {
			Document doc = (Document)de.getDocument();
			int length=doc.getLength();
			String str=null;
			
			try
				{
					str = doc.getText(0,length);
				}

			catch (BadLocationException ex)
				{
                                    System.out.println("error"+ex);
				}
				
			try
				{
					Class.forName("com.mysql.jdbc.Driver");
					con=DriverManager.getConnection("jdbc:mysql://localhost:3306/minor","root","");
					pstmt=con.prepareStatement("select empid,name,phonenumber from employees where name LIKE '"+str+"%' order by name");
					rs1=pstmt.executeQuery();
					
					
					
					model.setRowCount(0);
					
					while(rs1.next())
						{
							model.addRow(new Object[] { rs1.getString(1),rs1.getString(2),rs1.getString(3)});
						}
										
					pstmt.close();
					con.close();
				}
			
			catch(SQLException e)
				{
					JOptionPane.showMessageDialog(null,"Error in Database : "+e,"Display",JOptionPane.INFORMATION_MESSAGE);
				}
			
			catch(Exception e)
				{
					JOptionPane.showMessageDialog(null,"Unidentified Error : "+e,"Display",JOptionPane.INFORMATION_MESSAGE);
				}
			
			}
			
			public void changedUpdate(DocumentEvent de) {
			// text was changed
			update(de);
			}
			
			public void removeUpdate(DocumentEvent de) {
			// text was deleted
			update(de);
			}
			
			public void insertUpdate(DocumentEvent de) {
			// text was inserted
			update(de);
			}
		
		}
     
	
           
        
        public static void main(String args[])
	{
            try{
		setDefaultLookAndFeelDecorated(true);
		
		new ShowRecords().setVisible(true);
	       }
            catch(Exception ex)
            {
                System.out.println("error in main"+ex);
                ex.printStackTrace();
            }

}
}

