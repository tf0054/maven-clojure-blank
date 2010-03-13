package am.ik.blank;

public class Hello {
    public static String hello(String name) {
        return "Hello " + name + "!";
    }
    
    public static void main(String[] args) {
        System.out.println(hello("Clojure"));
    }
}