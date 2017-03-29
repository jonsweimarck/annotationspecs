package net.scatteredbits;


import java.io.Serializable;
import java.util.List;

public class SpecificationWithExamples implements Serializable {

    private final String specName;
    private final String specDesc;
    private final List<String> keyExampleDescriptions;

    public SpecificationWithExamples(String specName, String specDesc, List<String> keyExampleDescriptions) {

        this.specName = specName;
        this.specDesc = specDesc;
        this.keyExampleDescriptions = keyExampleDescriptions;
    }

    public String getSpecName() {
        return specName;
    }

    public String getSpecDesc() {
        return specDesc;
    }

    public List<String> getKeyExampleDescriptions() {
        return keyExampleDescriptions;
    }
}
