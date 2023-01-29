package Main;
/**
 * @author lveMonsi
 * @version 1.0
 */
public class Student {
    private String ID;
    private String name;
    private String claSS;
    private String dormitory;

    public Student() {
    }

    public Student(String ID, String name, String claSS, String dormitory) {
        this.ID = ID;
        this.name = name;
        this.claSS = claSS;
        this.dormitory = dormitory;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClaSS() {
        return claSS;
    }

    public void setClaSS(String claSS) {
        this.claSS = claSS;
    }

    public String getDormitory() {
        return dormitory;
    }

    public void setDormitory(String dormitory) {
        this.dormitory = dormitory;
    }

    @Override
    public String toString() {
        return
                ID +
                        "\t   \t" + name +
                        "\t   \t\t" + claSS +
                        "\t   \t" + dormitory ;
    }
}
