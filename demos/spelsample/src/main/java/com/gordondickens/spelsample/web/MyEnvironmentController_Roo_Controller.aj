// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.gordondickens.spelsample.web;

import com.gordondickens.spelsample.entity.MyEnvironment;
import java.io.UnsupportedEncodingException;
import java.lang.Integer;
import java.lang.Long;
import java.lang.String;
import java.util.Collection;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

privileged aspect MyEnvironmentController_Roo_Controller {
    
    @RequestMapping(method = RequestMethod.POST)
    public String MyEnvironmentController.create(@Valid MyEnvironment myEnvironment, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("myEnvironment", myEnvironment);
            return "myenvironments/create";
        }
        uiModel.asMap().clear();
        myEnvironment.persist();
        return "redirect:/myenvironments/" + encodeUrlPathSegment(myEnvironment.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(params = "form", method = RequestMethod.GET)
    public String MyEnvironmentController.createForm(Model uiModel) {
        uiModel.addAttribute("myEnvironment", new MyEnvironment());
        return "myenvironments/create";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String MyEnvironmentController.show(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("myenvironment", MyEnvironment.findMyEnvironment(id));
        uiModel.addAttribute("itemId", id);
        return "myenvironments/show";
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public String MyEnvironmentController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            uiModel.addAttribute("myenvironments", MyEnvironment.findMyEnvironmentEntries(page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));
            float nrOfPages = (float) MyEnvironment.countMyEnvironments() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("myenvironments", MyEnvironment.findAllMyEnvironments());
        }
        return "myenvironments/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT)
    public String MyEnvironmentController.update(@Valid MyEnvironment myEnvironment, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("myEnvironment", myEnvironment);
            return "myenvironments/update";
        }
        uiModel.asMap().clear();
        myEnvironment.merge();
        return "redirect:/myenvironments/" + encodeUrlPathSegment(myEnvironment.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(value = "/{id}", params = "form", method = RequestMethod.GET)
    public String MyEnvironmentController.updateForm(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("myEnvironment", MyEnvironment.findMyEnvironment(id));
        return "myenvironments/update";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String MyEnvironmentController.delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        MyEnvironment.findMyEnvironment(id).remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/myenvironments";
    }
    
    @ModelAttribute("myenvironments")
    public Collection<MyEnvironment> MyEnvironmentController.populateMyEnvironments() {
        return MyEnvironment.findAllMyEnvironments();
    }
    
    String MyEnvironmentController.encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
        String enc = httpServletRequest.getCharacterEncoding();
        if (enc == null) {
            enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
        }
        try {
            pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
        }
        catch (UnsupportedEncodingException uee) {}
        return pathSegment;
    }
    
}