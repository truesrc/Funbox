package ru.funbox.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author truesrc
 * @since 16.04.2019
 */

public class VisitedLinks implements Serializable {

    private List<String> links;

    public List<String> getLinks() {
        return links;
    }

    public void setLinks(List<String> links) {
        this.links = links;
    }
}
