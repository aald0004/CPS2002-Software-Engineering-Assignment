package cps2002.task1;

import cps2002.task1.Message;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class MessageTest {

    private Message m;

    @Before
    public void setup(){
        m = new Message();
    }


    @Test
    public void testSimpleMessage() {

        // Exercise
        String message = m.getMessage();

        // Verify
        assertEquals("Message", message);


    }

    @Test
    public void testHelloWorld(){

        String hw = m.helloWorld();
        assertEquals("Hello World", hw);
    }

    @Test
    public void testBlankMessage(){
        String bm = m.blankMessage();
        assertEquals("",bm);
    }


    @After
    public void teardown(){
        m = null;
    }

}

