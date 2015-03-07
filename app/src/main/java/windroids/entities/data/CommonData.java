package windroids.entities.data;

import java.io.Serializable;
import java.util.Arrays;
import java.util.ArrayList;

public class CommonData extends Data implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final ArrayList<String> recommendedCommonData =
            new ArrayList<>(Arrays.asList(
                    "Weight",
                    "Height",
                    "Blood type",
                    "Chronic disease",
                    "Allergy",
                    "Symptoms",
                    "Medical Diagnosis",
                    "Regimen"));

    public CommonData(String name, String description) {
        super(Type.Common);
        this.name = name;
        this.description = description;
    }

    private String name;
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
