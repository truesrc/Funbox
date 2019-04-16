package ru.funbox.model;

import java.util.ArrayList;
import java.util.List;

public class VisitedDomains {
    public List<String> getDomains() {
        return domains;
    }

    public void setDomains(List<String> domains) {
        this.domains = domains;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private List<String> domains = new ArrayList<>();
    private String status;

    @Override
    public String toString() {
        return "VisitedDomains{" +
                "domains=" + domains +
                ", status='" + status + '\'' +
                '}';
    }

}
