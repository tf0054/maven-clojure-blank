package am.ik.blank;


import clojure.lang.RT;
import clojure.lang.Var;
import clojure.lang.Symbol;
import clojure.lang.Compiler;
import java.io.StringReader;

import am.ik.blank.hello;

public class Hallo {

    public static String static_java(String name) {
        return "Hello " + name + " from java!";
    }

    public static void main(String[] args) {
        System.out.println(static_java("Clojure"));

        // clojure static func call
        hello.foo(12);

        // clojure non-static func call
        hello h = new hello();
        System.out.println(h.echo("Dr.Bones"));

        //
        // http://stackoverflow.com/a/2182608
        //
        // Load the Clojure script -- as a side effect this initializes the runtime.
        String str = "(ns user) (defn foo [a b]   (str a \" \" b))";

        //RT.loadResourceScript("foo.clj");
        Compiler.load(new StringReader(str));

        // Get a reference to the foo function.
        Var foo = RT.var("user", "foo");

        // Call it!
        Object result = foo.invoke("Hi", "there");
        System.out.println(result);
    }
}
