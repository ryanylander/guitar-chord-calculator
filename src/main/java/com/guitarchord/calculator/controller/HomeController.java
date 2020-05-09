package com.guitarchord.calculator.controller;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Controller
public class HomeController {

    @Autowired
    public RequestMappingHandlerMapping requestMappingHandlerMapping;

    @RequestMapping(value = "/")
    public String index() {
        return "index";
    }

    @RequestMapping("/endpoints")
    public @ResponseBody
    Object showEndpointsAction() throws SQLException
    {
            return requestMappingHandlerMapping.getHandlerMethods().keySet().stream().map(t ->
                (t.getMethodsCondition().getMethods().size() == 0 ? "GET" : t.getMethodsCondition().getMethods().toArray()[0]) + " " +                    
                t.getPatternsCondition().getPatterns().toArray()[0]
            ).toArray();
 }

}