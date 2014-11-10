package am.ik.blank;

import clojure.lang.RT;
import clojure.lang.Var;
import clojure.lang.Symbol;
import clojure.lang.Compiler;
import java.io.StringReader;

import java.io.ByteArrayInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

import am.ik.blank.hello;
import am.ik.blank.KafkaReadingInputStream;

public class Hallo {
    public static String hello(String name) {
        return "Hello " + name + "!";
    }
    
    public static void main(String[] args) throws IOException {
        System.out.println(hello("Clojure"));
        hello.foo(1);
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

    //
    // KafkaStream
    //
    BufferedReader br = new BufferedReader(new InputStreamReader(
		    new KafkaReadingInputStream("xxxx\nyyyy\nzzzz\n")));
    int intCount = 0;
    for (String line = br.readLine(); line != null; line = br.readLine()) {
       System.out.println((intCount++)+": "+line);
       if(intCount > 99) break;
    }

    br.close();
    }
}
