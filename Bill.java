/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minor;

import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.swing.table.*;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Set;
import java.util.TreeMap;
import javafx.scene.control.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hsqldb.Tokens;

/**
 *
 * @author dell
 */
public class Bill extends JFrame implements ActionListener, MouseListener, KeyListener {

    JLabel tablenumber, chooseitem, chooseitemend, item, quantity, total, discount, charge, grandtotal;
    JTextField txttablenumber, txtquantity, txttotal, txtgrandtotal;
    JComboBox cbitem, cbdiscount, cbcharge;
    JButton btnaddtocart, btnnew, btnsave, btnprint;
    JScrollPane jsp;
    JPopupMenu jp;
    JMenuItem mdelete;
    DefaultTableModel model = new DefaultTableModel();
    JTable table = new JTable(model);
    DBconnection db = new DBconnection();
    ResultSet rs;
    PreparedStatement ps;
    Statement stmt;
    Connection con;
    JPopupMenu pm;
    JMenuItem mDelete;
    private double totalAmount, gtotalamount;

    public Bill() {
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/minor", "root", "");
        } catch (Exception e) {
        }

        setVisible(true);
        setSize(850, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(null);
        setTitle("Billing");
        ImageIcon img = new ImageIcon("images/pass48.png");
        setIconImage(img.getImage());
        

        pm = new JPopupMenu();
        mDelete = new JMenuItem("Delete");
        pm.add(mDelete);
        mDelete.addActionListener(this);

        tablenumber = new JLabel("Table Number");
        add(tablenumber);
        tablenumber.setBounds(20, 30, 100, 25);

        /*   orderid = new JLabel("Order id");
         add(orderid);
         orderid.setBounds(20, 40, 100, 25);

         txtorderid = new JTextField(20);
         add(txtorderid);
         txtorderid.setBounds(130, 40, 120, 25);
        
         currentid = new JLabel("Current id");
         add(currentid);
         currentid.setBounds(550, 40, 100, 25);
        
         txtcurrentid = new JTextField(20);
         add(txtcurrentid);
         txtcurrentid.setBounds(610, 40, 120, 25);
         txtcurrentid.setEditable(false);
         */
        txttablenumber = new JTextField(10);
        add(txttablenumber);
        txttablenumber.setBounds(130, 30, 120, 25);
        txttablenumber.addKeyListener(this);

        chooseitem = new JLabel("----------------------Choose Item--------------------------");
        add(chooseitem);
        chooseitem.setBounds(50, 70, 800, 25);

        item = new JLabel("Items");
        add(item);
        item.setBounds(20, 110, 100, 25);

        String search[] = {""};
        cbitem = new JComboBox(search);
        add(cbitem).setBounds(130, 110, 120, 25);

        quantity = new JLabel("Quantity");
        add(quantity).setBounds(20, 150, 100, 25);

        txtquantity = new JTextField(10);
        add(txtquantity).setBounds(130, 150, 120, 25);

        btnaddtocart = new JButton("Add to Table", new ImageIcon("images/addtable24.png"));
        add(btnaddtocart).setBounds(500, 180, 150, 25);
        btnaddtocart.addActionListener(this);

        chooseitemend = new JLabel("--------------------------------------------------X-------------------------------------------");
        add(chooseitemend).setBounds(50, 200, 800, 25);

        int v = ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
        int h = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;

        jsp = new JScrollPane(table, v, h);
        table.addMouseListener(this);
        add(jsp).setBounds(20, 220, 710, 200);
        model.addColumn("Item Number");
        model.addColumn("Item");
        model.addColumn("Price");
        model.addColumn("Quantity");
        model.addColumn("Amount");
        table.getColumnModel().getColumn(0).setPreferredWidth(100);
        table.getColumnModel().getColumn(1).setPreferredWidth(200);
        table.getColumnModel().getColumn(2).setPreferredWidth(150);
        table.getColumnModel().getColumn(3).setPreferredWidth(100);
        table.getColumnModel().getColumn(4).setPreferredWidth(160);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        total = new JLabel("Total");
        add(total).setBounds(20, 440, 100, 25);

        txttotal = new JTextField(20);
        add(txttotal).setBounds(130, 440, 120, 25);
        txttotal.setEditable(false);

        charge = new JLabel("VAT and SC (%)");
        add(charge).setBounds(20, 480, 100, 25);

        String charges[] = {"0", "2", "5", "10", "12", "13", "15", "20", "22", "23", "25", "30", "35"};
        cbcharge = new JComboBox(charges);
        add(cbcharge).setBounds(130, 480, 120, 25);
        cbcharge.addActionListener(this);

        discount = new JLabel("Discount  (%)");
        add(discount).setBounds(20, 520, 100, 25);

        String discounts[] = {"0", "1", "2", "5", "10", "12", "13", "15", "18", "20", "25"};
        cbdiscount = new JComboBox(discounts);
        add(cbdiscount).setBounds(130, 520, 120, 25);
        cbdiscount.addActionListener(this);

        grandtotal = new JLabel("Grand Total");
        add(grandtotal).setBounds(20, 560, 100, 25);

        txtgrandtotal = new JTextField(20);
        add(txtgrandtotal).setBounds(130, 560, 120, 25);
        txtgrandtotal.setEditable(false);

        btnnew = new JButton("New", new ImageIcon("images/NEW.png"));
        add(btnnew).setBounds(460, 600, 100, 50);
        btnnew.addActionListener(this);

        btnsave = new JButton("Save", new ImageIcon("images/Ok.png"));
        add(btnsave).setBounds(575, 600, 100, 50);
        btnsave.addActionListener(this);

        btnprint = new JButton("Export", new ImageIcon("images/Print.png"));
        add(btnprint).setBounds(700, 600, 100, 50);
        btnprint.addActionListener(this);

        try {
            stmt = db.cn.createStatement();
            rs = stmt.executeQuery("select * from menu");
            while (rs.next()) {
                cbitem.addItem(rs.getString(2));

            }
        } catch (Exception er) {
            System.out.println("error" + er);
        }
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
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
            JOptionPane.showMessageDialog(null, "Error in loading table" + e);
        }
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == btnnew) {
            this.dispose();
            new Bill().setVisible(true);
        }
        if (ae.getSource() == btnprint) {
            writetoexcel();
            try {
                for (int i = 0; i < model.getRowCount(); i++) {
                    PreparedStatement pstm = db.cn.prepareStatement("insert into billgraph (Itemnumber,Name,Price,Quantity,Amount,Tablenumber,Date) values (?,?,?,?,?,?,CURRENT_TIMESTAMP())");

                    pstm.setString(1, model.getValueAt(i, 0).toString());
                    pstm.setString(2, model.getValueAt(i, 1).toString());
                    pstm.setString(3, model.getValueAt(i, 2).toString());
                    pstm.setString(4, model.getValueAt(i, 3).toString());
                    pstm.setString(5, model.getValueAt(i, 4).toString());
                    pstm.setString(6, txttablenumber.getText());
                    pstm.executeUpdate();
                }
                PreparedStatement ps = db.cn.prepareStatement("delete from bill where Tablenumber=? ");
                ps.setString(1, txttablenumber.getText());
                ps.executeUpdate();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex);

            }
            JOptionPane.showMessageDialog(null, "Successful");
            this.dispose();
            new Bill();
        }

        if (ae.getSource() == btnsave) {
            String x = txttablenumber.getText();
            if (x.equals(null)) {
                JOptionPane.showMessageDialog(null, "Enter table nummber");
            } else {
                try {
                    PreparedStatement ps = db.cn.prepareStatement("delete  from bill where Tablenumber=?");
                    ps.setInt(1, Integer.parseInt(txttablenumber.getText()));
                    ps.executeUpdate();

                    for (int i = 0; i < model.getRowCount(); i++) {
                        PreparedStatement pstm = db.cn.prepareStatement("insert into bill (Itemnumber,Name,Price,Quantity,Amount,Tablenumber,Date) values (?,?,?,?,?,?,CURRENT_TIMESTAMP())");

                        pstm.setString(1, model.getValueAt(i, 0).toString());
                        pstm.setString(2, model.getValueAt(i, 1).toString());
                        pstm.setString(3, model.getValueAt(i, 2).toString());
                        pstm.setString(4, model.getValueAt(i, 3).toString());
                        pstm.setString(5, model.getValueAt(i, 4).toString());
                        pstm.setString(6, txttablenumber.getText().toString());
                        pstm.executeUpdate();

                    }
                    JOptionPane.showMessageDialog(null, "Data recorded");
                    this.dispose();
                    new Bill();

                    // DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    //Date date = new Date();
                    //pstm.setString(8, dateFormat.format(date));
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }

            }

        }

        if (ae.getSource() == btnaddtocart) {
            model.setRowCount(0);
            try {
                PreparedStatement pstm = db.cn.prepareStatement("Select * from bill where Tablenumber=?");
                pstm.setInt(1, Integer.parseInt(txttablenumber.getText()));
                ResultSet rs = pstm.executeQuery();
                while (rs.next()) {
                    //System.out.println(rs.getString(1));
                    model.addRow(new Object[]{rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)});
                }

            } catch (SQLException e) {

            }

            try {
                PreparedStatement ps = db.cn.prepareStatement("Select * from menu where itemname=?");
                ps.setString(1, cbitem.getSelectedItem().toString());
                ResultSet rs1 = ps.executeQuery();
                while (rs1.next()) {
                    int price = Integer.parseInt(rs1.getString(3));
                    int amount = (Integer.parseInt(txtquantity.getText())) * price;
                    model.addRow(new Object[]{rs1.getString(1), cbitem.getSelectedItem().toString(), price, txtquantity.getText(), amount});

                    PreparedStatement ps1 = db.cn.prepareStatement("insert into bill (Itemnumber,Name,Price,Quantity,Amount,Tablenumber,Date) values (?,?,?,?,?,?,CURRENT_TIMESTAMP())");
                    int i = model.getRowCount() - 1;
                    ps1.setString(1, model.getValueAt(i, 0).toString());
                    ps1.setString(2, model.getValueAt(i, 1).toString());
                    ps1.setString(3, model.getValueAt(i, 2).toString());
                    ps1.setString(4, model.getValueAt(i, 3).toString());
                    ps1.setString(5, model.getValueAt(i, 4).toString());
                    ps1.setInt(6, Integer.parseInt(txttablenumber.getText()));
                    ps1.executeUpdate();

                }
              int fprice = 0;
            for (int x = 0; x < model.getRowCount(); x++) {
                fprice += Integer.parseInt(model.getValueAt(x, 4).toString());
            }
            txttotal.setText(fprice + "");
            int total=Integer.parseInt(txttotal.getText());
            int  charge = Integer.parseInt(cbcharge.getSelectedItem().toString());
            double tempTotal=total+(charge*0.01*total);
            //System.out.println(tempTotal);
            int  discount = Integer.parseInt(cbdiscount.getSelectedItem().toString());
            double gtotal=tempTotal-(discount*0.01*tempTotal);
            txtgrandtotal.setText(gtotal + "");
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, ex);
            }

        } else if (ae.getSource() == mDelete) {
            int dialogButton = JOptionPane.showConfirmDialog(null,"Are you sure you want to delete?");
               if(dialogButton == JOptionPane.YES_OPTION){ 

            try {
                model.removeRow(table.getSelectedRow());
            } catch (Exception exx) {
                System.out.println("Error in deleting" + exx);
            }
                }
        
        } else if (ae.getSource() == cbcharge || ae.getSource() == cbdiscount) {
          
            int total=Integer.parseInt(txttotal.getText());
            int  charge = Integer.parseInt(cbcharge.getSelectedItem().toString());
            double tempTotal=total+(charge*0.01*total);
            //System.out.println(tempTotal);
            int  discount = Integer.parseInt(cbdiscount.getSelectedItem().toString());
            double gtotal=tempTotal-(discount*0.01*tempTotal);
            txtgrandtotal.setText(gtotal + "");
        
        }
        
        

    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
    }

    @SuppressWarnings("empty-statement")
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == txttablenumber) {

            try {
                model.setRowCount(0);
                PreparedStatement pstmt = db.cn.prepareStatement("select * from bill where Tablenumber=?");
                pstmt.setString(1, txttablenumber.getText());
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    model.addRow(new Object[]{rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)});
                }

            } catch (Exception ex) {
            }
            int fprice = 0;
            for (int x = 0; x < model.getRowCount(); x++) {
                fprice += Integer.parseInt(model.getValueAt(x, 4).toString());
            }
            txttotal.setText(fprice + "");
            int total=Integer.parseInt(txttotal.getText());
            int  charge = Integer.parseInt(cbcharge.getSelectedItem().toString());
            double tempTotal=total+(charge*0.01*total);
            //System.out.println(tempTotal);
            int  discount = Integer.parseInt(cbdiscount.getSelectedItem().toString());
            double gtotal=tempTotal-(discount*0.01*tempTotal);
            txtgrandtotal.setText(gtotal + "");
        }
    }

    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == table) {
            if (e.getButton() == 3) {
                int r = table.rowAtPoint(e.getPoint());
                table.setRowSelectionInterval(r, r);
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
     private void writetoexcel()
    {
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet ws = wb.createSheet();
        
        //load data to treemap
        
       TreeMap<String,Object[]>data = new TreeMap<>();
       
       //add column headers
       
       data.put("-1", new Object[]{model.getColumnName(0),model.getColumnName(1),model.getColumnName(2),model.getColumnName(3),model.getColumnName(4)});
       
       //add rows and cells
       for (int i=0;i<model.getRowCount();i++)
       {
           data.put(Integer.toString(i),new Object[]{getcellvalue(i, 0),getcellvalue(i, 1),getcellvalue(i, 2),getcellvalue(i, 3),getcellvalue(i, 4)} );
           
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
           FileOutputStream fos = new FileOutputStream (new File("E:/excel/bill.xlsx")); 
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

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        }
        new Bill();
        //setDefaultLookAndFeelDecorated(true);

    }

}
