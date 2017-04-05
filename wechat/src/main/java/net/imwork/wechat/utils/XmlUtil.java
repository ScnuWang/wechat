package net.imwork.wechat.utils;

import com.thoughtworks.xstream.XStream;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Jason.
 * Date 2017/4/1 17:42
 */
public class XmlUtil {
    /**
     * 功能描述：Java对象转换成XML字符串
     * @param object 被转换成xml的源对象
     * @return
     */
    public static String objectToXml(Object object){
        XStream xStream = new XStream();
        xStream.alias("object",Object.class);
        return xStream.toXML(object);
    }

    /**
     *  功能描述：XML字符串转换成Java对象
     * @param xml   被解析的xml字符串
     * @param object    解析的目标对象
     * @return
     */
    public static Object xmlToObject(String xml,Object object){
        XStream xStream = new XStream();
        xStream.alias("object",object.getClass());
        return xStream.fromXML(xml);
    }

    public static Map xmlToMap(String xml){
        if (StringUtils.isNotEmpty(xml)){
            return null;
        }
        SAXReader saxReader = new SAXReader();
        Map map = new HashMap();
        try {
            Document document = saxReader.read((new ByteArrayInputStream(xml.getBytes("UTF-8"))));
            Element root = document.getRootElement();
            List<Element> elementList  = root.elements();
            for (Element e: elementList) {
                map.put(e.getName(),e.getTextTrim());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}
