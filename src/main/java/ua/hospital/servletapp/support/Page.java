package ua.hospital.servletapp.support;

import java.util.List;

/**
 * This is generic class purposed for handling paginated entity list transfer operations.
 *  
 * Field {@link Page#allPages} stores information about amount of pages with the similar size to the one, stored in particular
 * {@link Page} object, that could be retrieved from relevant data source at the moment,
 * when particular {@link Page} object was created.
 * 
 * Field {@link Page#pageNumber} contains an int value for the position of current entity {@link List} {@link Page#list}
 * among the rest that where in the data source at the moment of the creation of current Page object.
 * 
 * Field {@link Page#sort} - the parameter of T objects ordering;
 * 
 * @author Alexander-PC
 *
 * @param <T> - stored entity
 */

public class Page<T> {
	private int pageNumber;
	private int allPages;
	private String sort;
	private List<T> list;
	
	public Page(List<T> list, String sort, int pageNumber, int allPages){
		this.list = list;
		this.sort = sort;
		this.pageNumber = pageNumber;
		this.allPages = allPages;
	}
	
	public int getPageSize() {
		return list.size();
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public int getAllPages() {
		return allPages;
	}

	public String getSort() {
		return sort;
	}

	public List<T> getList() {
		return list;
	}

	@Override
	public String toString() {
		return new StringBuilder("pageNumber: ")
				.append(pageNumber)
				.append("allPages: ")
				.append(allPages)
				.append("sort: ")
				.append(sort)
				.append("\nlist: ")
				.append(list)
				.toString();
	}
	
	

}
