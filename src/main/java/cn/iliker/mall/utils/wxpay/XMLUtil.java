package cn.iliker.mall.utils.wxpay;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * xml工具类
 *
 * @author miklchen
 */
public class XMLUtil {

    /**
     * 解析xml,返回第一级元素键值对。如果第一级元素有子节点，则此节点的值是子节点的xml数据。
     *
     * @param strxml
     * @return
     * @throws IOException
     */
    public static SortedMap<String, String> doXMLParse(String strxml) throws IOException, DocumentException {
        SortedMap<String, String> parameters = new TreeMap<>();
        SAXReader reader = new SAXReader();
        ByteArrayInputStream tInputStringStream = new ByteArrayInputStream(strxml.getBytes("utf-8"));
        Document document = reader.read(tInputStringStream);
        Element root = document.getRootElement();
        for (Iterator it = root.elementIterator(); it.hasNext(); ) {
            Element element = (Element) it.next();
            String eName = element.getQualifiedName();
            if ("ScanCodeInfo".equals(eName)) {
                List<Element> list = element.elements();
                for (Element element1 : list) {
                    parameters.put(element1.getQualifiedName(), element1.getText());
                }
            } else {
                parameters.put(eName, element.getText());
            }
        }
        tInputStringStream.close();
        return parameters;
    }

    /**
     * 获取子结点的xml
     *
     * @return String
     */
    public static String getChildrenText(List children) {
        StringBuffer sb = new StringBuffer();
        if (!children.isEmpty()) {
            Iterator it = children.iterator();
            while (it.hasNext()) {
                Element e = (Element) it.next();
                String name = e.getQualifiedName();
                String value = e.getText();
                List list = e.elements();
                sb.append("<" + name + ">");
                if (!list.isEmpty()) {
                    sb.append(XMLUtil.getChildrenText(list));
                }
                sb.append(value);
                sb.append("</" + name + ">");
            }
        }

        return sb.toString();
    }

    public static String toXML(Map<String, Object> params) {
        StringBuilder builder = new StringBuilder();
        builder.append("<xml>");
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            builder.append("<").append(entry.getKey()).append(">").append("<![CDATA[").append(entry.getValue()).append("]]>").append("<").append("/").append(entry.getKey()).append(">");
        }
        builder.append("</xml>");
        return builder.toString();
    }
}
