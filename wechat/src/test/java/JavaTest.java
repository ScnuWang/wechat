import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.util.List;

/**
 * Created by Jason.
 * Date 2017/4/6 13:53
 */
public class JavaTest {
    @Test
    public void  testEvent(){

        String xml  = "<xml>" +
                "<ToUserName><![CDATA[toUser]]></ToUserName>" +
                "<FromUserName><![CDATA[FromUser]]></FromUserName>" +
                "<CreateTime>123456789</CreateTime>" +
                "<MsgType><![CDATA[event]]></MsgType>" +
                "<Event><![CDATA[CLICK]]></Event>" +
                "<EventKey><![CDATA[EVENTKEY]]></EventKey>" +
                "</xml>";
        SAXReader saxReader = new SAXReader();
        try {
            Document document = saxReader.read((new ByteArrayInputStream(xml.getBytes("UTF-8"))));
            Element root = document.getRootElement();
            List<Element> elementList  = root.elements();
            for (Element e: elementList) {
                System.out.println(e.getName());
                System.out.println(e.getText());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
