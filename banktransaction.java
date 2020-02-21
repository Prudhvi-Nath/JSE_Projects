import java.util.Timer;
import java.util.TimerTask;
import java.util.Scanner;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.PooledConnection;
import java.sql.Statement;
import oracle.jdbc.pool.OracleConnectionPoolDataSource;
import static java.lang.System.out;
import static java.lang.System.*;
import oracle.jdbc.rowset.OracleWebRowSet;
import java.io.File;
import java.io.FileWriter;
import java.io.Console;
interface Security{
 void deposit();	
void balenquiry();
void withanddept();
						
}
						
public  class banktransaction extends bankacccreate implements Security,Runnable{
	static Connection con=null;
	static int withdrwalamt=0;
	static int depositamount=0;
	static int choice=0;
	static int id1=0;
	static int balance=0;
	static Scanner scan=null;
	static banktransaction bt=null;
	static int accno=0;
	static String pass="";
	static ResultSet rs=null;
	static Statement st=null;
	static Thread t=null;

	static  TimerTask task = new TimerTask(){

	        public void run(){
            		if( accno==0){
	                System.out.println( "you input nothing. Session Timed exit..." );
               	 System.exit( 0 );
            			}
           		else{
		System.out.println("you entered something");
		System.exit(0);
		}
   	           }    
    	   };
	static  TimerTask task1 = new TimerTask(){

	        public void run(){
            		if( pass.equals("")){
	                System.out.println( "you input nothing. Session Timed exit..." );
               	 System.exit( 0 );
            			}
           		else{
		System.out.println("you entered something");
		System.exit(0);
		}
   	           }    
    	   };
	static  TimerTask task2 = new TimerTask(){

	        public void run(){
            		if(choice==0){
	                System.out.println( "you input nothing. Session Timed exit..." );
               	 System.exit( 0 );
            			}
           		else{
		System.out.println("you entered something");
		System.exit(0);
		}
   	           }    
    	   };
	static  TimerTask task3 = new TimerTask(){

	        public void run(){
            		if(withdrwalamt==0){
	                System.out.println( "you input nothing. Session Timed exit..." );
               	 System.exit( 0 );
            			}
           		else{
		System.out.println("you entered something");
		System.exit(0);
		}
   	           }    
    	   };
static  TimerTask task4 = new TimerTask(){

	        public void run(){
            		if(depositamount==0){
	                System.out.println( "you input nothing. Session Timed exit..." );
               	 System.exit( 0 );
            			}
           		else{
		System.out.println("you entered something");
		System.exit(0);
		}
   	           }    
    	   };


	 Connection connection()throws SQLException{
OracleConnectionPoolDataSource ocp=new OracleConnectionPoolDataSource();
		ocp.setURL("jdbc:oracle:thin:@localhost:1521:xe");
		ocp.setUser("ora5pm");
		ocp.setPassword("oracle");//it is set up with java appplication
		PooledConnection pc=ocp.getPooledConnection();	//and that connected to pooled connection manager
		//PooledConnection is an interface
		 con=pc.getConnection();//then by that creating connection to database
		return con;
		}

	public synchronized void balenquiry(){
			System.out.println("Your Balance in your account:"+balance);
			}
		
	@Override
	protected void finalize(){
		  System.out.println("memory Cleaned");
			}
	public synchronized void withdrawl(){
		try{
		Timer timer3 = new Timer();
                                             timer3.schedule( task3, 10*1000 );//timer.schedule(reference vairable,time)10*1000=10 seconds

		  System.out.println("please enter the withdrawl amount:");
				            withdrwalamt=scan.nextInt();
				  timer3.cancel();
		if(withdrwalamt<=balance ){
				if(withdrwalamt%10 == 0){
					if(withdrwalamt<=20000 && withdrwalamt>=500){
				         balance=balance-withdrwalamt;
				        System.out.println("Remaining amount is:"+balance);
				      PreparedStatement ps1=con.prepareStatement("update bankaccholder set accbalance=? where id=?");
				      ps1.setInt(1,balance);
				      ps1.setInt(2,id1);
				      int count1=ps1.executeUpdate();
				   System.out.println("updated balance amount in account:"+count1);
					       	}
					 else{
					   System.out.println("withdrawl amount should be greater than 500 and less than 20000");	
				                      }
					}
				  else{
					System.out.println("please enter only even digit number should be end with zero");
				        }
			}
	             else{
	                     System.out.println("amount not more than balance amount or do not enter the number not ends with zero or");
					
		      }
			}
		catch(SQLException s){
			
			}
				
			}
		public synchronized void deposit(){
				try{
				Timer timer4 = new Timer();
                                             timer4.schedule( task4, 10*1000 );//timer.schedule(reference vairable,time)10*1000=10 seconds

				System.out.println("enter the amount you want to deposit:");
			               depositamount=scan.nextInt();
				timer4.cancel();
				balance=balance+depositamount;
				System.out.println("Total account balance is:"+balance);
				 PreparedStatement ps2=con.prepareStatement("update bankaccholder set accbalance=? where id=?");
				      ps2.setInt(1,balance);
				      ps2.setInt(2,id1);
				      int count2=ps2.executeUpdate();
				   System.out.println("updated balance amount in account debited:"+count2);
					}
				catch(SQLException s1){
				}
			
		                 	}
	  public synchronized void withanddept(){
		try{
		 st=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
		String query="select * from bankaccholder where id="+accno; 
		boolean b=st.execute(query);
		 rs=st.getResultSet();
		if(b){
		int i=0;   
		 while(rs.next()){
			i++;
			}
		System.out.println("User Id:"+accno);
		if(i>=1){
	
		while(rs.previous()){
		             id1=rs.getInt(1);
		             balance=rs.getInt(4);
		   try{
		   if(accno==id1 && pass.equals(rs.getString(3))){
			System.out.println("Please Enter the options:");
			Timer timer2 = new Timer();
                                             timer2.schedule( task2, 10*1000 );//timer.schedule(reference vairable,time)10*1000=10 seconds

			System.out.println("1.Withdrawl 2.deposit 3.balance enquiry 4.cancel");
			 choice=scan.nextInt();
			timer2.cancel();
			switch(choice){
		                      case 1:
				  new Thread()
				{	public void run(){
						try{
						 bt.withdrawl();

						   }
						catch(Exception t1){
						}
						  bt.balenquiry();
					
					}
				}.start();
		                     
				break;
			     case 2:
			               new Thread()
				{
					public void run(){
					          try{
					            bt.deposit();
						
						 }
						catch(Exception t2){
						}
						bt.balenquiry();
						
						
						}
				}.start();
			     break;
			       
			     case 3:
				new Thread()
				{
				public void run(){
					bt.balenquiry();
					      }
				}.start();	
			
				break;
			      case 4:
				System.out.println("Transaction Cancelled:");
				System.gc();
				bt=null;
				Connection con=null;
				scan.close();

				break;	
			      default:
				System.out.println("Invalid option entered:");
				 break;
				}//switch closed
			   }//nested if closed
			
 		               else{
			
			System.out.println("Invalid Password");
				
			      }//else closed
		
			}
		  
	   
		catch(Exception e){
			System.out.println(e.toString());
			}
				
		}//while closed
		}//if closed
		else if(i==0){
		System.out.println("Invalid User Id:"+accno);

			
			}
		}//if true
		}//try block
		catch(SQLException s2){
			}
		
			}//method closed
	public static void main(String[] args)throws SQLException{
		
		scan=new Scanner(System.in);
		 Timer timer = new Timer();
       		 timer.schedule( task, 10*1000 );//timer.schedule(reference vairble,time)10*1000=10 seconds
         		//we assigning time to execute it based on setting time
       
		System.out.println("enter your account number:");
		accno=scan.nextInt();
		timer.cancel();
	      Timer timer1=new Timer();
                     timer1.schedule( task1, 10*1000 );//timer.schedule(reference vairable,time)10*1000=10 seconds
         //we assigning time to execute it based on setting time

		Console console=System.console();       
		System.out.println("enter your account password:");
		char c[]=console.readPassword();

		 pass=new String(c);
		timer1.cancel();
		System.out.println("password is:"+pass.toString());
		
		 bt=new banktransaction();
		  t=new Thread(bt);
                              con=bt.connection();
		System.out.println("connection established:"+con);
		bt.withanddept();
		System.gc();
			}
	}