import com.qs.iChain.CtSphChain;
import com.qs.iChain.Entry;
import com.qs.iChain.exception.ChainException;

/**
 * test
 * @author TsingSungHu
 */
public class MainTest {

    public static void main(String[] args) {
        Entry entry = null;
        try {
            entry = new CtSphChain().entry("demo");
        } catch (ChainException e) {
            System.out.println("异常："+e.getMessage());
        } finally {
            if (entry != null) {
                entry.exit();
            }
        }
    }
}
