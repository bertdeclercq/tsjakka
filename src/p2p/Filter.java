package p2p;

import java.util.ArrayList;
import java.util.List;

public class Filter {

    private String filter = "";
    private List<String> extensionList;

    public Filter() {
        extensionList = new ArrayList<String>();
    }
    
    public List<String> get() {
        List<String> filterList = new ArrayList<String>();
        if (!extensionList.isEmpty()) {
            return extensionList;
        } else {
            filterList.add(filter);
        }
        return filterList;
    }

    public void add(String filter) {
        if (filter.startsWith(".")) {
            extensionList.add(filter.trim().toLowerCase().substring(1));
        } else {
            clear();
            this.filter = filter.trim().toLowerCase();
        }
    }

    public void clear() {
        extensionList.clear();
        filter = "";
    }
    
    public boolean isEmpty() {
        if (extensionList.isEmpty() && filter.trim().equals("")) {
            return true;
        }
        return false;
    }

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
