package com.artapp.podstreleny.palo.artist.network.api_responses.genes;
import com.artapp.podstreleny.palo.artist.db.entity.Gene;

import java.util.List;

public class GeneData {
    private List<Gene> genes;

    public List<Gene> getGenes() {
        return genes;
    }

    public void setGenes(List<Gene> genes) {
        this.genes = genes;
    }
}
