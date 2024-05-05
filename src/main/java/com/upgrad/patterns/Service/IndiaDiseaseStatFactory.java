package com.upgrad.patterns.Service;

import com.upgrad.patterns.Interfaces.IndianDiseaseStat;
import com.upgrad.patterns.Constants.SourceType;
import com.upgrad.patterns.Strategies.DiseaseShStrategy;
import com.upgrad.patterns.Strategies.JohnHopkinsStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IndiaDiseaseStatFactory {
    private IndianDiseaseStat diseaseShStrategy;
    private IndianDiseaseStat johnHopkinsStrategy;

    @Autowired
    public IndiaDiseaseStatFactory(DiseaseShStrategy diseaseShStrategy, JohnHopkinsStrategy johnHopkinsStrategy)
    {
        this.diseaseShStrategy = diseaseShStrategy;
        this.johnHopkinsStrategy = johnHopkinsStrategy;
    }


    public IndianDiseaseStat GetInstance(SourceType sourceType) {
        switch (sourceType) {
            case JohnHopkins:
                return johnHopkinsStrategy;
            case DiseaseSh:
                return diseaseShStrategy;
            default:
                String message = String.format("Invalid disease strategy/sourceType: %s", sourceType);
                throw new IllegalArgumentException(message);
        }
    }
    
    
}
