package com.sambet.webapp.controller;

import javax.servlet.http.HttpServletRequest;

import com.sambet.Constants;
import com.sambet.dao.SearchException;
import com.sambet.service.UserManager;
import com.sambet.webapp.util.ControllerUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


/**
 * Simple class to retrieve a list of users from the database.
 * <p/>
 * <p>
 * <a href="UserController.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
@Controller
@RequestMapping("/admin/users*")
public class UserController extends BaseFormController {
    private UserManager userManager = null;

    @Autowired
    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    @RequestMapping(method = RequestMethod.GET) 
    public ModelAndView handleRequest(final HttpServletRequest request, @RequestParam(required = false, value = "q") String query) throws Exception {
        Model model = new ExtendedModelMap();
        try {
        	final int PageSizeTable = Constants.PAGE_SIZE_TABLE_20;
        	model.addAttribute(Constants.PAGE_SIZE_TABLE, PageSizeTable);
        	if(query == null || "".equals(query.trim())) {
        		int page = ControllerUtil.DammiPageNumberDisplayTable_by_QueryString(request, PageSizeTable); 
        		model.addAttribute(Constants.USER_LIST, userManager.getUserTableLimit(PageSizeTable, page) );
        		model.addAttribute("resultSize", userManager.getCountUser() );
	        }else{
	        	model.addAttribute(Constants.USER_LIST, userManager.search(query));
	        	model.addAttribute("resultSize", userManager.getCountUser() );
	        }
            
        } catch (SearchException se) {
            model.addAttribute("searchError", se.getMessage());
            model.addAttribute(userManager.getUserTable());
        }
        return new ModelAndView("admin/user-list", model.asMap());
    }
}
