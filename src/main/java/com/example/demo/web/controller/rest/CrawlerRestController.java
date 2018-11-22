package com.example.demo.web.controller.rest;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.example.demo.util.WebCrawler;
import com.example.demo.util.WebImageCrawler;
import com.example.demo.web.vo.ImageLoaderVo;
import com.example.demo.web.vo.ImageVo;

import freemarker.template.utility.StringUtil;

@Controller
@RequestMapping("/rest/crawler")
public class CrawlerRestController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Value("${path.image}")
    String imagePath;

    @Autowired
    WebCrawler webCrawler;

    @RequestMapping(value="/load/image", method= {RequestMethod.POST})
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody ImageLoaderVo image(HttpServletResponse response, 
    		@RequestParam(value="url", required=false) String url) {
    	ImageLoaderVo data = null;
    	
    	try {
    		data = new ImageLoaderVo();
    		data.setError_code("success");
    		data.setUrl(URLDecoder.decode(url, "UTF-8"));
    		data.setStart_date(new Date());
    		
    		if(StringUtil.emptyToNull(url)!=null) {
    			data.setImageList(webCrawler.getElement(webCrawler.getDocument(data.getUrl()), "img"));
    		}else {
        		data.setError_code("empty_url");
    		}
    		data.setUuid(""+UUID.randomUUID());
    		data.setEnd_date(new Date());
    	}catch(Exception e) {
    		log.error(e.toString());
    	}

        response.setStatus( HttpServletResponse.SC_OK  );
        return data;
    }


    @RequestMapping(value="/save/image", method= {RequestMethod.POST})
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody ImageLoaderVo save(HttpServletResponse response, 
    		@RequestParam(value="image_url", required=false) String image_url,
    		@RequestParam(value="uuid", required=false) String uuid) {
    	ImageLoaderVo data = null;
    	
    	try {
    		data = new ImageLoaderVo();
    		data.setError_code("success");
    		data.setUrl(URLDecoder.decode(image_url, "UTF-8"));
    		data.setStart_date(new Date());
    		
    		if(StringUtil.emptyToNull(image_url)!=null) {
            	try {
            		uuid = uuid!=null && !uuid.trim().equals("")?uuid:UUID.randomUUID()+"";
            		uuid = uuid.replaceAll("/", "").replaceAll("\\.", "");
            		String savePath = imagePath+"/"+uuid;
	            	WebImageCrawler webImageCrawler = new WebImageCrawler();
	            	webImageCrawler.setSavePath(savePath);
	            	webImageCrawler.setImgUrl(data.getUrl());
	            	webImageCrawler.downloadImage();
	            	
	            	ImageVo vo = new ImageVo();
	            	vo.setFileName(webImageCrawler.getImageName());
	            	vo.setThumbName(webImageCrawler.resize(200));
	            	//vo.setSavePath(webImageCrawler.getCanonicalSavePath());
	
	            	List<ImageVo> list = new ArrayList<ImageVo>();
	            	list.add(vo);
	            	data.setImageList(list);
            	}catch(Exception e) {
            		log.error(e.toString());
            	}
    		}else {
        		data.setError_code("empty_url");
    		}
    		data.setEnd_date(new Date());
    	}catch(Exception e) {
    		log.error(e.toString());
    	}

        response.setStatus( HttpServletResponse.SC_OK  );
        return data;
    }
}
