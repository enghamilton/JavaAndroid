Repository for Java sdk Android

# Review needed for avoiding Memory Leaks
## Hamilton Kamiya
###### The Author Hamiton skipped WeakReference concern on this repository for practical examples purposes
###### On a Professional those Java contents in this Repository must be reviewed for WeakReference 
###### and Garbage Collector issues to avoid Memory Leaks

**Java done regardless _OF MEMORY LEAKES concern_ please review must be DONE**

```
import java.lang.ref.*;

class MainActivity extends Activity {

private static TextView textView;

...

@Override
public void onDestruct(){
	super.onDestruct();
	textView = null;
	System.gc();
}

}
```


```
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
```
