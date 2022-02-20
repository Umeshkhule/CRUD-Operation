package com.app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Test {
	Scanner sc=new Scanner(System.in);
	public static void main(String[] args)throws Exception {
		Scanner sc=new Scanner(System.in);
		int ch;
		Test t=new Test();
		do {
			System.out.println("1.Insert student data into student table:");
			System.out.println("2.Update student data into student table:");
			System.out.println("3.Delete student data into student table:");
			System.out.println("4.Get a list of all students:");
			System.out.println("5.Get one student information:");
			System.out.println("6.Exit");
			System.out.println();
			ch=sc.nextInt();
			switch (ch) {
			case 1:
				t.insertData();
				break;
			case 2:
				t.updateData();
				break;
			case 3:
				t.deleteData();
				break;
			case 4:
				t.getData();
				break;
			case 5:
				t.searchData();
				break;
			default:System.out.println("Thank You For Visiting!!!!");
			break;
			}
		} while (ch!=6);
		sc.close();
	}
	public Connection getConnect() {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","scott","tiger");
			return con;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public void insertData()throws Exception{
		while(true) {
			System.out.print("Enter student NO:");
			int sno=sc.nextInt();
			System.out.print("Enter student NAME:");
			String sname=sc.next();
			System.out.print("Enter student DOB:");
			String dob=sc.next();
			System.out.print("Enter student DOJ");
			String doj=sc.next();

			java.sql.Date sqdob=java.sql.Date.valueOf(dob);
			java.sql.Date sqdoj=java.sql.Date.valueOf(doj);

			try {
				Connection con=getConnect();
				PreparedStatement pst=con.prepareStatement("insert into Student values(?,?,?,?)");
				pst.setInt(1,sno);
				pst.setString(2,sname);
				pst.setDate(3,sqdob);
				pst.setDate(4,sqdoj);

				int rowCount=pst.executeUpdate();
				pst.close();
				con.close();
				if(rowCount>0) {
					System.out.println("Record inserted successfully");
					System.out.println("One more student[Yes/No]");
					String option=sc.next();
					if(option.equalsIgnoreCase("No"))
					{
						break;
					}
					System.out.println();
				}
				else {
					System.out.println("Record not inserted");
					System.out.println();
					break;
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	public void updateData() {
		try {
			Connection con=getConnect();
			System.out.print("Enter student NO.:");
			int sno=sc.nextInt();
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery("select * from student");
			if (rs.next()) {
				if(sno==rs.getInt(1)) {
					PreparedStatement pst=con.prepareStatement("update Student set sname=?,dob=?,doj=? where sno=?");
					System.out.print("Enter student NAME:");
					String sname=sc.next();
					System.out.print("Enter student DOB:");
					String dob=sc.next();
					System.out.print("Enter student DOJ");
					String doj=sc.next();

					java.sql.Date sqdob=java.sql.Date.valueOf(dob);
					java.sql.Date sqdoj=java.sql.Date.valueOf(doj);

					pst.setString(1,sname);
					pst.setDate(2,sqdob);
					pst.setDate(3,sqdoj);
					pst.setInt(4,sno);

					int rowCount=pst.executeUpdate();
					pst.close();
					con.close();
					if(rowCount>0) {
						System.out.println("Record updated successfully");
						System.out.println();
					}
					else {
						System.out.println("Record not updated");
						System.out.println();
					}
				}
				else {
					System.out.println("Record not found");
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void deleteData() {
		System.out.print("Enter sno.");
		int sno=sc.nextInt();
		try {
			Connection con=getConnect();
			PreparedStatement pst=con.prepareStatement("delete from Student where sno=?");
			pst.setInt(1,sno);

			int rowCount=pst.executeUpdate();
			con.close();
			pst.close();
			if (rowCount>0) {
				System.out.println("Record deleted successfully");
				System.out.println();
			} else {
				System.out.println("Record not found");
				System.out.println();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void getData() {
		try {
			Connection con=getConnect();
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery("select * from Student");
			System.out.println("SNO\tSNAME\tDOB\t\tDOJ");
			System.out.println("=========================================");
			while(rs.next()) {
				System.out.println(rs.getInt(1)+"\t"+rs.getString(2)+"\t"+rs.getDate(3)+"\t"+rs.getDate(4));
			}
			System.out.println();
			con.close();
			st.close();
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void searchData() {
		System.out.print("Enter sno:");
		int sno=sc.nextInt();
		try {
			Connection con=getConnect();
			PreparedStatement pst=con.prepareStatement("select * from Student where sno=?");
			pst.setInt(1,sno);
			ResultSet rs=pst.executeQuery();
			if(rs.next()) {
				System.out.println("SNO\tSNAME\tDOB\t\tDOJ");
				System.out.println("=========================================");
				System.out.println(rs.getInt(1)+"\t"+rs.getString(2)+"\t"+rs.getDate(3)+"\t"+rs.getDate(4));
			}
			else {
				System.out.println("Record not fount");
			}
			System.out.println();	
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}