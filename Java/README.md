Repository for Java sdk Android

import java.lang.ref.*;

class Example {
    public Example() {
        
    }
}

class SubExample extends Example {
    
    public SubExample(){}
    
}

public class Main
{
	public static void main(String[] args) {
		System.out.println("Hello World");
		
		SubExample instance = new SubExample();
		WeakReference<SubExample> someWeakInstance = new WeakReference<>(instance);
		
		instance = null;
		System.gc();
	}
}
