/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minor;
/**
 *
 * @author Tripti
 */
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.event.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import javax.swing.*;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.Set;
import java.util.TreeMap;
import javax.swing.table.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class UpdateMenu extends JFrame implements ActionListener, KeyListener ,MouseListener{

    JComboBox cbSearch;
    JTextField txtSearch;
    JButton btnRefresh,btnexport;
    
    Connection con;

    JPanel jp;

    DefaultTableModel model = new DefaultTableModel();
    JTable table;
    JScrollPane jsp;

    
    
    JLabel lblEId,lblname, lblposition, lblph, lbladdress,lblemailid,lbldob,lbldoh,lblnsal,lbltsal;
    JTextField txtEId,txtname, txtposition,txtph,txtaddress,txtemailid,txtdob,txtdoh,txtnsal,txttsal;
    JButton btnSubmit,btnback,btnreset;

    DBconnection db = new DBconnection();
    
    JPopupMenu pm;
    JMenuItem mEdit, mDelete;
    
    
    
    boolean update=false;
 
    public UpdateMenu() {
        
   
         setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("Update Menu");
        
        String search[] = {"", "itemnumber", "itemname", "price"};
        cbSearch = new JComboBox(search);
        add(cbSearch).setBounds(20, 20, 100, 25);

        txtSearch = new JTextField();
        add(txtSearch).setBounds(130, 20, 215, 25);
        txtSearch.addKeyListener(this);

        btnRefresh = new JButton(new ImageIcon("images/refresh32.png"));
        
        add(btnRefresh).setBounds(350, 20, 30, 25);
        btnRefresh.setToolTipText("Refresh");
        btnRefresh.addActionListener(this);
        
        
        //------------------------------------------------------------------------------------
        table = new JTable(model) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false; //To change body of generated methods, choose Tools | Templates.
            }

        };
        
        int v = ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
        int h = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;

        jsp = new JScrollPane(table, v, h);
        add(jsp).setBounds(20, 50, 360, 300);

        btnexport = new JButton("Export");
        add(btnexport);
        btnexport.setBounds(150, 420, 120, 25);
        btnexport.addActionListener(this);
        
        model.addColumn("Item Number");
        model.addColumn("Name");
        model.addColumn("Price");
                

       
                
    table.getColumnModel().getColumn(0).setPreferredWidth(100);
        table.getColumnModel().getColumn(1).setPreferredWidth(150);
            table.getColumnModel().getColumn(2).setPreferredWidth(107);
    table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

      
                
        loadTable();
        table.addMouseListener(this);
       
        
        //------------------------------------------------------------------------------------
        jp = new JPanel(null);
        add(jp).setBounds(450, 20, 650, 650);
        
        btnback= new JButton("Back");
        jp.add(btnback).setBounds(20, 420,100,25);
        btnback.addActionListener(this);
        
        btnreset = new JButton("Reset");
        jp.add(btnreset).setBounds(20, 380, 100, 25);
        btnreset.addActionListener(this);
        
                
        lblEId=new JLabel("Item Number");
        jp.add(lblEId).setBounds(20,20,100,25);
        
        txtEId=new JTextField();
        jp.add(txtEId).setBounds(130,20,120,25);
        

        lblname = new JLabel("Name");
        jp.add(lblname).setBounds(20, 60, 100, 25);

        txtname = new JTextField();
        jp.add(txtname).setBounds(130, 60, 120, 25);

        lblposition = new JLabel("Price");
        jp.add(lblposition).setBounds(20, 100, 100, 25);

        txtposition = new JTextField();
        jp.add(txtposition).setBounds(130, 100, 120, 25);

        
        btnSubmit = new JButton("Submit");
        jp.add(btnSubmit).setBounds(130, 420, 120, 25);
        btnSubmit.addActionListener(this);
 
        //------------------------------------------------------------------------------------
        
        pm=new JPopupMenu();
        mEdit=new JMenuItem("Edit");
        mDelete=new JMenuItem("Delete");
        pm.add(mEdit);
        mEdit.addActionListener(this);
        pm.add(mDelete);
        mDelete.addActionListener(this);
        
        setLayout(null);
        setSize(730, 550);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
    
        private void writetoexcel()
    {
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet ws = wb.createSheet();
        
        //load data to treemap
        
       TreeMap<String,Object[]>data = new TreeMap<>();
       
       //add column headers
       
       data.put("-1", new Object[]{model.getColumnName(0),model.getColumnName(1),model.getColumnName(2)});
       
       //add rows and cells
       for (int i=0;i<model.getRowCount();i++)
       {
           data.put(Integer.toString(i),new Object[]{getcellvalue(i, 0),getcellvalue(i, 1),getcellvalue(i, 2)} );
           
       }
       
       
       //write to excel
       Set<String> ids = data.keySet();
       XSSFRow row;
       int rowID=0;
       for (String key:ids)
       {
           row = ws.createRow(rowID++);
           
           //get data as per key
           
           Object[] values = data.get(key);
           int cellID = 0;
           for (Object O :values)
           {
               XSSFCell cell = row.createCell(cellID++);
               cell.setCellValue(O.toString());
           }
       }
       
       //write to filesystem
       try 
           
       {
           FileOutputStream fos = new FileOutputStream (new File("E:/excel/menurecords.xlsx")); 
           wb.write(fos);
           fos.close();
       }
       catch (Exception ex )
       {
           ex.printStackTrace();
           JOptionPane.showMessageDialog(null, ex);
       }
                
    }
    
     private String getcellvalue (int x ,int y)
     {
         return model.getValueAt(x, y).toString();
     }



    public void loadTable() {
        model.setRowCount(0);
        try {
            Statement stmt = db.cn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from menu");
            while (rs.next()) {
                model.addRow(new Object[]{rs.getString(1), rs.getString(2), rs.getString(3)});
                
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error in loading table"+e);
        }
    }
    
    public void resetForm(){
        txtEId.setText("");
        txtname.setText("");
        txtposition.setText("");
       
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        }
        new UpdateMenu();
    }
    

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()== btnexport)
        {
            writetoexcel();
            JOptionPane.showMessageDialog(null, "Exported");
        }
        if (e.getSource() == btnRefresh) {
            loadTable();
            cbSearch.setSelectedIndex(0);
            txtSearch.setText("");
        }
        
        if(e.getSource()==btnSubmit){
            
        String Eid= txtEId.getText();
        String name=txtname.getText();
        String position=txtposition.getText();
        int ts=Integer.parseInt(position);
        boolean status= false;        
            if(!update){
                
            try {
                Class.forName("com.mysql.jdbc.Driver");
		con=DriverManager.getConnection("jdbc:mysql://localhost:3306/minor","root","");
                PreparedStatement pstmt=con.prepareStatement("insert into menu (itemnumber,itemname,price) values(?,?,?)");
                pstmt.setString(1,Eid);
                pstmt.setString(2,name);
                pstmt.setInt(3,ts);
                               
                int result=pstmt.executeUpdate();
                if(result>0){
                JOptionPane.showMessageDialog(null, "Item added !");
                }
                else{
                 JOptionPane.showMessageDialog(null, "Item not added !");
                }
            } catch (Exception ex) {System.out.println("Error in submit"+ex);  }
            
            }
            else{
             
            try {
                PreparedStatement pstmt=db.cn.prepareStatement("update menu set itemname=?,price=? where itemnumber=?");
                pstmt.setString(3,Eid);
                pstmt.setString(1,name);
                pstmt.setString(2,position);
                
                int result=pstmt.executeUpdate();
                if(result>0){
                JOptionPane.showMessageDialog(null, "Item updated !");
                }
                else{
                 JOptionPane.showMessageDialog(null, "Item not updated !");
                }
                update=false;
            btnSubmit.setText("Submit");
            } catch (SQLException ex) { ex.printStackTrace(); }
            }
            loadTable();
            resetForm();
            }
        
        if(e.getSource()==mEdit){
            txtEId.setEditable(false);
            String id=model.getValueAt(table.getSelectedRow(), 0).toString();
            try {
                PreparedStatement pstmt=db.cn.prepareStatement("select * from menu where itemnumber=?");
                pstmt.setString(1,id);
                ResultSet rs=pstmt.executeQuery();
                if(rs.next()){
                txtEId.setText(rs.getString(1));
                txtname.setText(rs.getString(2));
                txtposition.setText(rs.getString(3));
                

                btnSubmit.setText("Update");
                update=true;
                }
            } catch (Exception ex) {
            }
        }
        
        if(e.getSource()==mDelete){
            int dialogButton = JOptionPane.showConfirmDialog(null,"Are you sure you want to delete?");
               if(dialogButton == JOptionPane.YES_OPTION){ 
            try {
                PreparedStatement pstmt=db.cn.prepareStatement("delete from menu where itemnumber=?");
                pstmt.setString(1, (String) model.getValueAt(table.getSelectedRow(), 0));
                pstmt.executeUpdate();
                JOptionPane.showMessageDialog(null,"Item Deleted !");
                loadTable();
            } catch (Exception exx) 
            {
                System.out.println("Error in deleting"+exx);
            }
        }
        }
        if (e.getSource()== btnback)
        {
            this.dispose();
            new menuu().setVisible(true);
        }
         if (e.getSource() == btnreset)
        {
            this.dispose();
            new UpdateMenu();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getSource()==txtSearch){
            
        String column=cbSearch.getSelectedItem().toString();
        if(!column.equals("")){
            try {
                model.setRowCount(0);
                PreparedStatement pstmt=db.cn.prepareStatement("select * from menu where "+column+" like ?");
                pstmt.setString(1, txtSearch.getText()+"%");
                ResultSet rs=pstmt.executeQuery();
                while (rs.next()) {
                     model.addRow(new Object[]{rs.getString(1), rs.getString(2), rs.getString(3)});
            }
            
            } catch (Exception ex) {
            }
        }
        else{
        JOptionPane.showMessageDialog(null,"Select a Column !");
        txtSearch.setText("");
        }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getSource()==table){
            if(e.getButton()==3){
                int r = table.rowAtPoint(e.getPoint());
                table.setRowSelectionInterval(r,r);
        pm.show(e.getComponent(), e.getX(), e.getY());
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
    
   

}
