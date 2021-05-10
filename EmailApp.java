package EmailApp;

import java.sql.*;
import java.util.Scanner;

public class EmailApp {
    private static final String selectQuery = "select email,password from email";
    private static final String insertQuery = "insert into email values (?,?,?,?,?)";
    private static final String updatePasswordQuery = "update email set password = ? where email = ?";
    private static final String updateEmailQuery = "update email set email = ? where lastname = ?";
    private static final String deleteQuery = "delete from email where email = ?";
    private static final String classForName = "oracle.jdbc.driver.OracleDriver";
    private static final String driverUrl = "jdbc:oracle:thin:@localhost:1521:XE";
    private static final String username = "********";
    private static final String password = "********";
    public static void main(String[] args){
              int ch;
              boolean flag = true;
              Scanner sc =  new Scanner(System.in);

              select();
              while(flag){

                  System.out.println("========================================================");
                  System.out.println("THESE ARE THE OPTIONS AVAILABLE: ");
                  System.out.println("1.INSERT \n2.UPDATE PASSWORD \n3.UPDATE EMAIL \n4.DISPLAY \n5.DELETE \n6.EXIT");
                  System.out.println("ENTER YOUR CHOICE: ");
                  ch = sc.nextInt();
                  switch(ch){
                      case 1: insert();
                                break;
                      case 2: updatePassword();
                                break;
                      case 3: updateEmail();
                                break;
                      case 4: select();
                                break;
                      case 5: delete();
                                break;
                      case 6: flag = false;
                  }
              }
    }

    public static void insert(){
          try{
              Scanner sc = new Scanner(System.in);
              String firstName;
              String lastName;
              String companyName;
              String col5;
              System.out.println("ENTER YOUR FIRST NAME: (CAPITALS ONLY)");
              firstName = sc.nextLine();
              System.out.println("ENTER YOUR LAST NAME: (CAPITALS ONLY)");
              lastName = sc.nextLine();
              col5 = setDepartment();

              Class.forName(classForName);

              Connection con = DriverManager.getConnection(driverUrl,username,password);

              System.out.println("ENTER YOUR EMAIL: ");
              String col3 = sc.nextLine();
              SendEmail email = new SendEmail(col3);

              String col1 = firstName;
              String col2 = lastName;
              String col4 = email.getUpdatePassword();

              PreparedStatement psmt = con.prepareStatement(insertQuery);
              psmt.setString(1,col1);
              psmt.setString(2,col2);
              psmt.setString(3,col3);
              psmt.setString(4,col4);
              psmt.setString(5,col5);
              psmt.execute();

              psmt.close();
              con.close();
          }
          catch(Exception e){
              e.printStackTrace();
          }
    }

    public static void select(){
        try{
            Class.forName(classForName);

            Connection con = DriverManager.getConnection(driverUrl,username,password);

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery(selectQuery);
            System.out.println("========================================================");
            System.out.println("EMAIL \t \t \t \t \t \t \t \tPASSWORD");
            System.out.println("========================================================");
            while(rs.next()) {
                String dbEmail = rs.getString("EMAIL");
                String dbPassword = rs.getString("PASSWORD");
                System.out.println(dbEmail+" \t \t "+dbPassword);
            }
            stmt.close();
            con.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void updatePassword(){
        try{
            Scanner scanner = new Scanner(System.in);
            System.out.println("ENTER EMAIL TO UPDATE PASSWORD: ");
            String updatePasswordByEmail = scanner.nextLine();

            SendEmail passwordEmail = new SendEmail(updatePasswordByEmail);

            String col1 = passwordEmail.getUpdatePassword();
            System.out.println("THE NEW PASSWORD FOR EMAIL: " + updatePasswordByEmail + " IS: " + col1);
            System.out.println(passwordEmail.getUpdatePassword());
            Class.forName(classForName);
            Connection con = DriverManager.getConnection(driverUrl,username,password);

            PreparedStatement psmt = con.prepareStatement(updatePasswordQuery);
            psmt.setString(1,col1);
            psmt.setString(2, updatePasswordByEmail);
            psmt.execute();

            psmt.close();
            con.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void updateEmail(){
        try{
            Scanner scanner = new Scanner(System.in);
            String firstName;
            String lastName;
            String companyName;
            String department;
            System.out.println("ENTER THE FIRST NAME: (CAPITALS ONLY)");
            firstName = scanner.nextLine();
            System.out.println("ENTER THE LAST NAME: (CAPITALS ONLY)");
            lastName = scanner.nextLine();
            System.out.println("ENTER THE EMAIL");
            String col1 = scanner.nextLine();
            String col2 = lastName;

            System.out.println("THE NEW EMAIL FOR " + col2 + " IS: " + col1);
            Class.forName(classForName);
            Connection con = DriverManager.getConnection(driverUrl,username,password);

            PreparedStatement psmt = con.prepareStatement(updateEmailQuery);
            psmt.setString(1,col1);
            psmt.setString(2,col2);
            psmt.execute();

            psmt.close();
            con.close();

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static String setDepartment(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("THESE ARE THE CODES: \n1.SALES \n2.DEVELOPMENT \n3.ACCOUNTS \n4.TESTING \nENTER YOUR DEPARTMENT:");
        int choice = scanner.nextInt();
        if(choice == 1) { return "SALES";}
        else if(choice == 2) { return "DEVELOPMENT";}
        else if(choice == 3) { return "ACCOUNTS";}
        else if(choice == 4) {return "TESTING";}
        else return " ";
    }

    public static void delete(){
        try{
            Scanner scanner = new Scanner(System.in);
            String emailToDelete;

            System.out.println("ENTER THE EMAIL TO DELETE THE RECORD: ");
            emailToDelete = scanner.nextLine();
            Class.forName(classForName);
            Connection con = DriverManager.getConnection(driverUrl,username,password);

            PreparedStatement psmt = con.prepareStatement(deleteQuery);
            psmt.setString(1,emailToDelete);
            psmt.execute();

            System.out.println("RECORD DELETED SUCCESSFULLY.");

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}