package edu.oakland.uPortalTesterWebapp.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

@Controller
public class Application {

    private static final Log log = LogFactory.getLog(Application.class);

    private Document getDocument(String url){
        try{
            return Jsoup.connect(url).get();
        }catch(Exception e){
            log.error("Exception: getDocument: ", e);
            log.error("Returning new Document to fail gracefully.");
            return new Document("");
        }
    }

    @RequestMapping(value="/", method=RequestMethod.GET)
    public ModelAndView home(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView home = new ModelAndView("home");
        home.addObject("running", getRunning(request));
        return home;
    }

    private boolean getRunning(HttpServletRequest request) {

		if(log.isDebugEnabled()) {
		  String ipAddress = request.getRemoteAddr();
			log.debug("Request generated by: " + ipAddress);
		}

	    Document document = getDocument("http://localhost:8080/uPortal");
      try {
        return (document.getElementById("portal").html() ).length() > 0 ? true : false;
      } catch (Exception e) {
        log.error(e);
        return false;
      }
    }
}
