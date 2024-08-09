package com.jdbc.student;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;

import org.w3c.dom.UserDataHandler;

import com.mysql.cj.protocol.Resultset;
	
public class StudentDatabase {

	private static Connection connection = null;
	private static Scanner scanner = new Scanner(System.in);
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		
		StudentDatabase studentDatabase = new StudentDatabase();
		Class.forName("com.mysql.cj.jdbc.Driver");
		String dbURL = "jdbc:mysql://localhost:3306/JDBC_DB";
		String username = "root";
		String password = "manager";
		connection=DriverManager.getConnection(dbURL,username,password);
		
		System.out.println("Enter Choice :: ");
		System.out.println("1. Insert Record");
		System.out.println("2. Select Record");
		System.out.println("3. Update Record");
		System.out.println("4. Delete Record");
		System.out.println("5. transactions ");
		System.out.println("6. Batch Proceesing");
		
		

		int choice = scanner.nextInt();
		
		switch (choice) {
		case 1:
			insertrecod();
			break;
		case 2:
			selectrecord();
			break;
		case 3:
			updateRecords();
			break;
		case 4:
			deleteRecord();
			break;
		case 5:
			trasanction();
			break;
		case 6:
			batchProcessing();
			break;
		}

	}
	
	
	private static void insertrecod() throws SQLException {
		// TODO Auto-generated method stub
		System.out.println("Insert Record ");
		
		String sql = "insert into student(name,percentage,adress) values(?,?,?)";
		
//		String sql = "insert into student(name,percentage,adress) values("Raj",82.2,"Mumbai")"; Hardcoded values  
		
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		/*
		 * you can hardcode value like this 
		 * preparedStatement.setString(1, "Omkar");
		 * preparedStatement.setDouble(2,88.50); preparedStatement.setString(3,"Pune");
		 */
		
		System.out.println("Enter Name ::");
		preparedStatement.setString(1, scanner.next());
		
		System.out.println("Enter Percentage ::");
		preparedStatement.setDouble(2, scanner.nextDouble());
		
		System.out.println("Enter Address ::");
		preparedStatement.setString(3, scanner.next());
		
		int rows = preparedStatement.executeUpdate();
		
		if(rows>0) {
			System.out.println("Record Insrted Succesfully");
		}
	}
	
	private static void selectrecord() throws SQLException {
		// TODO Auto-generated method stub
		System.out.println("Enter the roll number you result you want ");
		int number = scanner.nextInt();
		String sql = "select * from student where roll_number = "+number;
		Statement statement= connection.createStatement();
		ResultSet resultset =statement.executeQuery(sql);
		 
		 if(resultset.next()) {
			 int roll_no = resultset.getInt("roll_number");
			 String name = resultset.getString("name");
			 Double percentage = resultset.getDouble("percentage");
			 String address= resultset.getString("adress");
			 
 			 System.out.println(roll_no+" "+name+" "+percentage+" "+address);
		 } 
		 
			/* use while loop when the result set returns more that one value
			 * 
			 * while(resultset.next()) {   
			 * 
			 * }
			 */
	}
	
	private static void updateRecords() throws SQLException {
		// TODO Auto-generated method stub
		
		 System.out.println("Enter the roll number you want to update ");
		 int roll =(scanner.nextInt());
		 
		 Statement statement=connection.createStatement();
		 String sqlString ="select * from student where roll_number ="+roll;
		 ResultSet resultset = statement.executeQuery(sqlString);
		 
		 if(resultset.next()) {
			 int roll_number = resultset.getInt("roll_number");
			 String  name = resultset.getString("name");
			 Double percentage = resultset.getDouble("percentage");
			 String address = resultset.getString("adress");
			 
			System.out.println(" Roll_Number -> "+roll_number+" Name -> "+name+" Percentage -> "+percentage+" Address->"
					+ " "+address );
		
		 System.out.println("Whcich Data You want to update ");
		 System.out.println("1.Name");
		 System.out.println("2.Percentage ");
		 System.out.println("3.Address ");
		 
		 int choice = scanner.nextInt();
		 
		 String sql = "update student set";
		 
		 switch (choice) {
		case 1:
			System.out.println("Enter the new name ");
			String new_name= scanner.next();
			
			sql = sql +" name= ? where roll_number ="+roll;	
			PreparedStatement preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setString(1, new_name);
			
			if(preparedStatement.executeUpdate()>0) {
				System.out.println("record updated sucessfully ");
			}
			break;
		case 2:
			System.out.println("enter the new percetnage to update ");
			double percentage1 = scanner.nextDouble();
			
			String sqlUpdate = "update student set ";
			sqlUpdate = sqlUpdate +"percentage = ? where roll_number="+roll;
			PreparedStatement preparedStatement2=connection.prepareStatement(sqlUpdate);
			preparedStatement2.setDouble(1, percentage1);
			if(preparedStatement2.executeUpdate()>0) {
				System.out.println("Percentage Updated Sucessfully ");
			}
			
			break;
		case 3:
			System.out.println("enter the adress to update ");
			String new_address = scanner.next();
			
			String sqlAdressUpdate = "update student set ";
			sqlAdressUpdate += " adress = ? where roll_number = "+roll;
			
			PreparedStatement preparedStatement3=connection.prepareStatement(sqlAdressUpdate);
			preparedStatement3.setString(1, new_address);
			
			if(preparedStatement3.executeUpdate()>0) {
				System.out.println("Percentage updated successfully ");
			}
			break;
		default:
			break;
		}	
	}
		 else {
			System.out.println("Records not found !!!");
		}
		 
	}
	
	private static void deleteRecord() throws SQLException {
		// TODO Auto-generated method stub
		
		System.out.println("Enter roll Number you want to delete");
		int roll_number = scanner.nextInt();
		
		String sql = "delete from student where roll_number = "+roll_number;
		
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		
		if(preparedStatement.executeUpdate()>0) {
			System.out.println("Record is deleted successfully ");
		}
	}
	
	private static void trasanction() throws SQLException {
		// TODO Auto-generated method stub
		connection.setAutoCommit(false);

		String sqlString ="insert into student(name,percentage,adress) values('Shreyas',69.4,'Banglore')";
		String sqlString1 ="insert into student(name,percentage,adress) values('Rahul',79.2,'Chennai')";

		PreparedStatement preparedStatement = connection.prepareStatement(sqlString);
		int row1 = preparedStatement.executeUpdate();
		
		PreparedStatement preparedStatement2 = connection.prepareStatement(sqlString1);
		 int row2=preparedStatement.executeUpdate(sqlString1);
		 
		 if(row1 > 0 && row2 >0) {
			 connection.commit();
		 }else {
			connection.rollback();
		}
	}
	
	private static void batchProcessing() throws SQLException {
		// TODO Auto-generated method stub
		
		connection.setAutoCommit(false);

		String sqlString ="insert into student(name,percentage,adress) values('Surya',83.2,'Gujrat')";
		String sqlString1 ="insert into student(name,percentage,adress) values('Axar',89.2,'Ahamdabad')";
		
	Statement statement =	connection.createStatement();
	statement.addBatch(sqlString);
	statement.addBatch(sqlString1);
	
	int [] rows = statement.executeBatch();
	
	for(int i:rows) {
		 if(i>0) {
			 continue;
		 }else {
			connection.rollback();
		}	 
	}
	connection.commit();
	
	
	

	}
	

}
