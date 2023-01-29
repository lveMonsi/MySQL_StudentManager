package Main;

import com.mysql.cj.jdbc.Driver;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Properties;
import java.util.Scanner;
/**
 * @author lveMonsi
 * @version 1.0
 */
public class Manager {
    public static void main(String[] args) throws SQLException {
        //创建集合对象，用于储存学生数据
        ArrayList<Student> array = new ArrayList<Student>();
        //从数据库中读取学生数据储存到集合中以供使用
        loadStudents(array);
        while (true) {
            //利用死循环实现循环执行系统面板输出
            System.out.println("--------欢迎来到学生管理系统--------");
            System.out.println("1 添加学生");
            System.out.println("2 删除学生");
            System.out.println("3 修改学生");
            System.out.println("4 查看所有学生");
            System.out.println("5 退出");
            System.out.println("请输入你的选择：");

            Scanner sc = new Scanner(System.in);
            String line = sc.nextLine();

            switch (line) {
                case "1":
                    addStudent(array);
                    break;
                case "2":
                    deleteStudent(array);
                    break;
                case "3":
                    updateStudent(array);
                    break;
                case "4":
                    findAllStudent(array);
                    break;
                case "5":
                    System.out.println("谢谢使用");
                    //根据学号排序学生元素
                    sort(array);
                    //结束运行前将已修改的学生数据储存进数据库中
                    saveStudents(array);
                    System.exit(0);
            }
        }
    }
    public static void addStudent(ArrayList<Student> array) {
        Scanner sc = new Scanner(System.in);

        String ID;
        while (true) {
            System.out.println("请输入学生学号(格式：2022001)：");
            ID = sc.nextLine();
            boolean flag = isUseful(array, ID);
            if (flag) {
                break;
            }
        }//判断学号是否可用且格式是否正确
        System.out.println("请输入学生姓名：");
        String name = sc.nextLine();
        System.out.println("请输入学生宿舍号(格式：C11_111)：");
        String dormitory;
        while (true) {
            dormitory = sc.nextLine();
            if (dormitory.charAt(0) == 'C' & dormitory.charAt(3) == '_' & Character.isDigit(dormitory.charAt(1)) & Character.isDigit(dormitory.charAt(2)) & Character.isDigit(dormitory.charAt(4)) & Character.isDigit(dormitory.charAt(5)) & Character.isDigit(dormitory.charAt(6))) {
                break;
            } else
                System.out.println("你输入的宿舍格式有误，请重新输入：");
        }//判断学生宿舍号格式是否正确
        System.out.println("请输入学生班级：");
        String claSS = sc.nextLine();

        Student s = new Student();
        s.setID(ID);
        s.setName(name);
        s.setDormitory(dormitory);
        s.setClaSS(claSS);

        array.add(s);

        System.out.println("添加学生成功！");
    }//向集合添加学生数据
    public static void deleteStudent(ArrayList<Student> array) {
        while(true) {
            Scanner sc = new Scanner(System.in);
            System.out.println("请输入你要删除的学生的学号：");
            String ID = sc.nextLine();
            int index = -1;
            for (int x = 0; x < array.size(); x++) {
                Student s = array.get(x);
                if (s.getID().equals(ID)) {
                    index = x;
                    break;
                }
            }
            if (index == -1) {
                System.out.println("该信息不存在，请重新输入！");
            } else {
                array.remove(index);
                System.out.println("删除学生成功！");
                break;
            }
        }
    }//按学号索引删除对应学生数据
    public static void updateStudent(ArrayList<Student> array) {
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入你要修改的学生的学号：");
        String ID;
        while (true) {
            ID = sc.nextLine();
            if (isNumeric(ID) & ID.length() == 7) {//学号为纯数字且为7位数
                boolean flag = false;
                for (Student s : array) {
                    if (s.getID().equals(ID)) {//学号是否重复
                        flag = true;
                        break;
                    }
                }
                if (flag) {
                    break;
                } else {
                    System.out.println("请输入已储存的学生学号！");
                }
            } else {
                System.out.println("请输入格式正确的学号！");
            }
        }//判断学号是否已被使用，是则修改该位置的学生信息
        System.out.println("请输入新学生姓名：");
        String name = sc.nextLine();
        System.out.println("请输入新学生宿舍号(格式：C11_111)：");
        String dormitory;
        while (true) {
            dormitory = sc.nextLine();
            if (dormitory.charAt(0) == 'C' & dormitory.charAt(3) == '_' & Character.isDigit(dormitory.charAt(1)) & Character.isDigit(dormitory.charAt(2)) & Character.isDigit(dormitory.charAt(4)) & Character.isDigit(dormitory.charAt(5)) & Character.isDigit(dormitory.charAt(6))) {
                break;
            } else
                System.out.println("你输入的宿舍格式有误，请重新输入：");
        }//判断学生宿舍号格式是否正确
        System.out.println("请输入新学生班级：");
        String claSS = sc.nextLine();

        Student s = new Student();
        s.setID(ID);
        s.setName(name);
        s.setDormitory(dormitory);
        s.setClaSS(claSS);


        for (int x = 0; x < array.size(); x++) {
            Student student = array.get(x);
            if (student.getID().equals(ID)) {
                array.set(x, s);
                break;
            }
        }
        System.out.println("修改学生成功！");
    }//修改学生数据
    public static void findAllStudent(ArrayList<Student> array) {
        if (array.size() == 0) {
            System.out.println("无信息，请添加信息后查询");
            return;
        }
        //根据学号排序学生元素
        sort(array);
        System.out.println("ID\t\t\t姓名\t\t\t班级\t\t\t宿舍号");

        for (Student s : array) {
            System.out.println(s);
        }
    }//按格式输出学生数据
    public static boolean isUseful(ArrayList<Student> array, String ID) {
        if (isNumeric(ID) & ID.length() == 7) {//学号为纯数字且为7位数
            for (Student s : array) {
                if (s.getID().equals(ID)) {//学号是否重复
                    System.out.println("该学号已被占用，请重新输入！");
                    return false;
                } else {
                    return true;
                }
            }
        } else {
            System.out.println("请输入格式正确的学号！");
            return false;
        }
        return true;
    }//判断学号是否可用且格式是否正确
    public static boolean isNumeric(String str) {
        for (int i = str.length(); --i >= 0; ) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }//判断字符串是否为纯数字
    public static void loadStudents(ArrayList<Student> array) throws SQLException {
        Driver driver = new Driver();
        String url = "jdbc:mysql://127.0.0.1:3306/students";
        Properties properties = new Properties();
        properties.setProperty("user","root");
        properties.setProperty("password","123456");
        Connection connect = driver.connect(url, properties);
        String sql = "select * from students.Students";
        Statement statement = connect.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while(resultSet.next()){//获取数据填入array集合
            Student s = new Student(resultSet.getString("id"),
                    resultSet.getString("name"),
                    resultSet.getString("class"),
                    resultSet.getString("dorm"));
            array.add(s);
        }
        resultSet.close();
        statement.close();
        connect.close();
    }//连接数据库获取学生数据
    public static void saveStudents(ArrayList<Student> array) throws SQLException {
        Driver driver = new Driver();
        String url = "jdbc:mysql://127.0.0.1:3306/students";
        Properties properties = new Properties();
        properties.setProperty("user","root");
        properties.setProperty("password","123456");
        Connection connect = driver.connect(url, properties);
        String sql = "delete from students.Students";
        Statement statement = connect.createStatement();
        statement.executeUpdate(sql);
        for (Student s : array) {
            //按照格式将学生数据拼接成一个字符串
            //格式：insert into students.Students values (ID,name,class,dormitory)
            StringBuilder sb = new StringBuilder();
            sb.append("insert into students.Students values ");
            sb.append("(").append(s.getID()).append(",'").append(s.getName()).append("','").append(s.getClaSS()).append("','").append(s.getDormitory()).append("'").append(")");
            sql = sb.toString();
            statement.executeUpdate(sql);
        }
        statement.close();
        connect.close();
    }//连接数据库保存学生数据
    public static void sort(ArrayList<Student> array){
        //根据学号排序学生元素
        array.sort(new Comparator<Student>() {
            @Override
            public int compare(Student s1, Student s2) {
                return Integer.parseInt(s1.getID()) - Integer.parseInt(s2.getID());
            }
        });
    }//重新按学号排序
}
