package test;

import instrumentation.Instrumentation;

public class InstrumentationTest {
    //Get the only object available
    private static Instrumentation ins = Instrumentation.getInstance();
    public static void main(String[] args) {  
                
        ins.activate(true);
        ins.startTiming("main");

        ins.startTiming("loop");
        for (int i=0; i<5; i++)
        {
            doSomeStuff();
        }
        ins.stopTiming("loop");

        ins.comment("this is an example of a comment!");
        ins.stopTiming("main");
        ins.dump("logs.txt");
    }

    public static void doSomeStuff()
    {
        ins.startTiming("doSomeStuff()");
        System.out.println("Hello there!");
        ins.stopTiming("doSomeStuff()");
    }
}
