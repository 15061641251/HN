package com.sbq.tools;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.util.NodeList;

import java.io.UnsupportedEncodingException;

/**
 * html工具类
 * Created by zhangyuan on 2017/3/24.
 */
public class HTMLUtil {

    /**
     * 返回html内容纯文本
     *
     * @param inputHtml
     * @return
     */
    public static String extractText(String inputHtml) {

        StringBuffer text = new StringBuffer();
        try {
            Parser parser = Parser.createParser(new String(inputHtml.getBytes("UTF-8"), "UTF-8"), "UTF-8");
            // 遍历所有的节点
            NodeList nodes = parser.extractAllNodesThatMatch(new NodeFilter() {
                public boolean accept(Node node) {
                    return true;
                }
            });

            for (int i = 0; i < nodes.size(); i++) {
                Node nodet = nodes.elementAt(i);
                //System.out.println(nodet.getText());
                text.append(new String(nodet.toPlainTextString().getBytes("UTF-8"), "UTF-8"));
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return text.toString().replace("&nbsp;", "");
    }
}
