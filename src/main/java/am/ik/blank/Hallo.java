package am.ik.blank;

//import clojure.lang.RT;
//import clojure.lang.Symbol;
//import clojure.lang.Var;

import am.ik.blank.hello;

public class Hallo {
    public static String hello(String name) {
        return "Hello " + name + "!";
    }
    
    public static void main(String[] args) {
        System.out.println(hello("Clojure"));
        hello.foo(1);
//        IFn plus = Clojure.var("clojure.core", "+");
//        System.out.println(plus.invoke(1, 2));
    }
}
