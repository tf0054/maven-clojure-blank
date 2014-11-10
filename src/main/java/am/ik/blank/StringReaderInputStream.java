package am.ik.blank;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

import java.util.UUID;
//import java.lang.InterruptedException;

public class StringReaderInputStream extends InputStream
{
    private String tmpStr;
    private StringReader reader;
    
    public StringReaderInputStream(StringReader reader)
    {
        super();
        this.reader = reader;
    }
    
    public StringReaderInputStream(String value)
    {
        this(new StringReader(value));
    }

    public int read() throws IOException
    {
	int tmpInt = reader.read();
	if(tmpInt == -1){
/*
try {
	Thread.sleep(1000);
} catch(InterruptedException e) {
	System.out.println(e);
}
*/
		reader = new StringReader(UUID.randomUUID()+"\n");
		tmpInt = reader.read();
	}
	return tmpInt;
    }
}
