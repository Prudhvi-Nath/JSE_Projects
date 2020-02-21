import java.io.IOException;
import java.io.File;
import java.util.Date;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.util.Scanner;
import java.sql.SQLException;
import oracle.jdbc.pool.OracleConnectionPoolDataSource;
import javax.sql.PooledConnection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import oracle.jdbc.rowset.OracleWebRowSet;
class bankacccreate extends Thread{

	Connection con=null;
	Scanner scan=null;
	int count=0;
	static int id=0;
	static String name=null;
	static String pass=null;
	static Double balance=0.0;
	static String typeofacc=null;
               static bankacccreate bac;
OracleConnectionPoolDataSource ocp;
	 synchronized void insertion(){
	            try{
		scan=new Scanner(System.in);
		System.out.println("enter the id for account and id:");
		id=scan.nextInt();
		System.out.println("enter the name for account holder:");
		name=scan.next();
		System.out.println("enter the password for account:");
		pass=scan.next();
		System.out.println("enter the minimum balance deposit while account creation:");
		balance=scan.nextDouble();
		System.out.println("enter the type of account is it savings or current:");
		typeofacc=scan.next();
		}
		catch(Exception e){
		  System.out.println("\n Error in entering details appropiate please check");
			}
		}
	synchronized Connection connection()throws SQLException{
		try{
		 ocp=new OracleConnectionPoolDataSource();
		ocp.setURL("jdbc:oracle:thin:@localhost:1521:xe");
		ocp.setUser("ora5pm");
		ocp.setPassword("oracle");//it is set up with java appplication
		PooledConnection pc=ocp.getPooledConnection();	//and that connected to pooled connection manager
		//PooledConnection is an interface
		con=pc.getConnection();//then by that creating connection to database
		//getConnection() is a abstract method with the return type Connection
		if(typeofacc.equalsIgnoreCase("savings")&&balance>=5000){
		PreparedStatement ps=con.prepareStatement("insert into bankaccholder values(?,?,?,?,?)");
		ps.setInt(1,bac.id);
		ps.setString(2,bac.name);
		ps.setString(3,bac.pass);
		ps.setDouble(4,bac.balance);
		ps.setString(5,bac.typeofacc);
		count=ps.executeUpdate();
		   }//if closed		

		else if(typeofacc.equalsIgnoreCase("current")&&balance>=10000){
		PreparedStatement ps=con.prepareStatement("insert into bankaccholder values(?,?,?,?,?)");
		ps.setDouble(1,bac.id);
		ps.setString(2,bac.name);
		ps.setString(3,bac.pass);
		ps.setDouble(4,bac.balance);
		ps.setString(5,bac.typeofacc);
		count=ps.executeUpdate();
		}//else if closed
		else{
		    System.out.println("Invalid account type or balance was not meet the requirement");	
		   }//else closed
		
	      
		
		if(count==1){	
		System.out.println(bac.typeofacc+ " Account Created "+bac.id+" for account holder:"+bac.name);
		OracleWebRowSet wrs=new OracleWebRowSet();
		//WebRowSet wrs=RowSetProvider.newFactory().createWebRowSet();
		wrs.setUrl("jdbc:oracle:thin:@localhost:1521:xe");
		wrs.setUsername("ora5pm");
		wrs.setPassword("oracle");
		wrs.setCommand("select * from bankaccholder");
		wrs.execute();
		File f=new File("bankaccdetails.xml");
		FileWriter fw=new FileWriter(f);
		wrs.writeXml(fw);
							
		}//if closed
			
                     	  }//try closed
		
		catch(Exception e1){
			System.out.println("Something went wrong in insertion values it does not satisfy requirement:Account not created");
				}//catch closed
		
			
		
		   		
			
		return con;
		}//method closed
	public static void main(String[] args)throws SQLException{
	 bac=new bankacccreate();
	   new Thread(){

		  
		  public void run(){
			try{
			bac.insertion();
			   }
			catch(Exception e3){
				System.out.println("problem in insertion method");
				}
			}
		
		 }.start();
	   new Thread(){

		  
		  public void run(){
			try{
			bac.connection();
			   }
			catch(Exception e3){
				System.out.println("Connection method failure");
				}
			}
		
		 }.start();
			
				}
}  