package ua.hospital.servletapp.tag;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 * 
 * @author Alexander
 * 
 * Creates custom tag to form list of int values, used to represent numbers of pages with paginating in JSP page.
 * Adds list of values as {@value var} attribute into JSP context using {@link SimpleTagSupport#getJspContext()}
 * Each skipped page is marked with "-1" number.
 *
 */

public class PagesNumeratingTag extends SimpleTagSupport {
	private String var;
	private int allPages;
	private int pageNumber;
	
	private List<Integer> list= new ArrayList<>();
	
	public void setVar(String var) {
		this.var = var;
	}
	
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	
	public void setAllPages(int pageAmount) {
		this.allPages = pageAmount;
	}
	
	@Override
	public void doTag() throws JspException{
		if (allPages > 7) {
			pageNumber += 1;
			
			ArrayList<Integer> head = new ArrayList<>();
			if (pageNumber > 4) {
				head.add(1);
				head.add(-1);
			} else {
				head.add(1);
				head.add(2);
				head.add(3);
			}
			
			ArrayList<Integer> tail = new ArrayList<>();
			if (pageNumber < allPages - 3) {
				tail.add(-1);
				tail.add(allPages);
			} else {
				tail.add(allPages - 2);
				tail.add(allPages - 1);
				tail.add(allPages);
			}
			
			ArrayList<Integer> bodyBefore = new ArrayList<>();
			if (pageNumber > 4 && pageNumber < allPages - 1) {
				bodyBefore.add(pageNumber - 2);
				bodyBefore.add(pageNumber - 1);
			}
			
			ArrayList<Integer> bodyAfter = new ArrayList<>();
			if (pageNumber > 2 && pageNumber < allPages - 3) {
				bodyAfter.add(pageNumber + 1);
				bodyAfter.add(pageNumber + 2);
			}
			
			list.addAll(head);
			list.addAll(bodyBefore);
			
			if (pageNumber > 3 && pageNumber < allPages - 2) {
				list.add(pageNumber);
			}
			
			list.addAll(bodyAfter);
			list.addAll(tail);
			
		} else {
			for (int i=1; i <= allPages; i++) {
				list.add(i);
			}
		}
	
		getJspContext().setAttribute(var, list);
	}
	
}
