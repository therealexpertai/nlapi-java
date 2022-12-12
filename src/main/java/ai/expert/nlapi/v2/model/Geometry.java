package ai.expert.nlapi.v2.model;

import lombok.Data;

import java.util.List;

@Data
public class Geometry {
    
    private Integer page, pageWidth, pageHeight;
    private List<Integer> box;
    
}
