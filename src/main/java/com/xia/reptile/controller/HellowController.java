package com.xia.reptile.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HellowController {
	
	/*@RequestMapping(value = "initpage")
	public String getIndex(Map<String,Object> map){
		System.out.println("--------------");
	    map.put("hello","from TemplateController.helloHtml");  
		return "helloHtml";
	}*/
	
	@RequestMapping("/")
    public String index(ModelMap map) {
        map.addAttribute("host", "http://blog.didispace.com");
        return "index";
    }
	
	@RequestMapping("/template")
	public String template(ModelMap map){
		map.addAttribute("host", "http://blog.template.com");
        return "template";
	}
	
}
