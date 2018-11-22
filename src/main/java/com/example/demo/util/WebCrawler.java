package com.example.demo.util;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import com.example.demo.web.vo.ImageVo;

@Component
public class WebCrawler {
	
    public Document getDocument(String url) {
    	Document doc = null;
        try {
            doc = Jsoup
                    .connect(url)
                    .userAgent("Mozilla/5.0")
                    .timeout(10 * 1000)
                    .get();
        } catch (IOException e) {
            System.err.println("For '" + url + "': " + e.getMessage());
        }
        return doc;
    }
    
    public List<ImageVo> getElement(Document doc, String selector) throws Exception {
    	List<ImageVo> list = null;
    	if(doc!=null) {
	    	Elements els = doc.select(selector);
	    	if(els!=null) {
	    		list = new ArrayList<ImageVo>();
	    		for(Element el : els) {
	    			ImageVo vo = new ImageVo();
	    			vo.setSrc(el.attr("abs:src"));
	    			vo.setAlt(el.attr("alt"));
	    			if(el.attr("src")==null || el.attr("src").equals("")) {
		    			vo.setSrc(el.attr("abs:data-src"));
	    			}

	    			try {
		    	        URL url = new URL(vo.getSrc());
		    	        vo.setSize(url.openConnection().getContentLength());
	    			}catch(Exception e) {
	    			}

	    			list.add(vo);
	    		}
	    	}
    	}
    	return list;
    }
}
