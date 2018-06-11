import com.zhang.busi.HelloWorld;
import com.zhang.rpc.client.RPCClientProxy;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by zhangc on 2018/6/7.
 */
public class ClientTest {
    String h = "";

    @Before
    public void init(){
        System.out.println("before");
        h = "hello";
    }


    //@Test
    public void hello(){
       // String h = "hello";
        assertEquals("hello",h);
    }

    //@Test
    public void test2(){
        assertEquals(1,1);
    }

    @Test(timeout = 10000)
    public void ClientHelloTest(){
        HelloWorld h = new RPCClientProxy().create(HelloWorld.class);
        assertEquals("hello my world.",h.sayHi());
    }


}
