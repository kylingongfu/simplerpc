import com.zhang.busi.HelloWorld;
import com.zhang.rpc.client.RPCClientProxy;

/**
 * Created by zhangc on 2018/5/28.
 */
public class BusiTest {
    public static void main(String[] args) {
        HelloWorld h = new RPCClientProxy().create(HelloWorld.class);
        System.out.println(h.sayHi());
    }
}
