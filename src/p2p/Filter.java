package p2p;

import java.util.ArrayList;
import java.util.List;

/**
 * The Filter class provides an option to select specific items from a tsjakkafiles list. It also provides option to modify this filter.
 */
public class Filter {

    private String filter;
    private List<String> extensionList;

    /**
     * Initializes a newly created Filter object.
     */
    public Filter() {
        filter = "";
        extensionList = new ArrayList<String>();
    }
    
    /**
     * Gets the items from the Filter class.
     * 
     * @return the items from the filter as a list of String objects
     */
    public List<String> get() {
        List<String> filterList = new ArrayList<String>();
        if (!extensionList.isEmpty()) {
            return extensionList;
        } else {
            filterList.add(filter);
        }
        return filterList;
    }

    /**
     * Add an item to the filter.
     * 
     * @param filter the String on which you want to filter
     */
    public void add(String filter) {
        if (filter.startsWith(".")) {
            extensionList.add(filter.trim().toLowerCase().substring(1));
        } else {
            clear();
            this.filter = filter.trim().toLowerCase();
        }
    }

    /**
     * Removes all elements from the filter.
     */
    public void clear() {
        extensionList.clear();
        filter = "";
    }
    
    /**
     * Returns true if the filter is empty.
     * 
     * @return true if this filter contains no elements
     */
    public boolean isEmpty() {
        if (extensionList.isEmpty() && filter.trim().equals("")) {
            return true;
        }
        return false;
    }

    /**
     * Filters a list using this filter end returns only the allowed items.
     * 
     * @param fileList the list you want to filter
     * 
     * @return a list containing only the items allowed by the filter
     */
    public List<TsjakkaFile> filter(List<TsjakkaFile> fileList) {
        List<TsjakkaFile> list = new ArrayList<TsjakkaFile>();
        if (!extensionList.isEmpty()) {
            for (String extension : extensionList) {
                for (TsjakkaFile tsjakkaFile : fileList) {
                    if (tsjakkaFile.getExtension().equals(extension.trim().toLowerCase())) {
                        list.add(tsjakkaFile);
                    }
                }
            }
        } else {
            for (TsjakkaFile tsjakkaFile : fileList) {
                if (tsjakkaFile.getFilename().toLowerCase().contains(filter)) {
                    list.add(tsjakkaFile);
                }
            }
        }
        return list;
    }
}
