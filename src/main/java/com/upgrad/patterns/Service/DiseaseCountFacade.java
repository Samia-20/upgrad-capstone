package com.upgrad.patterns.Service;

import com.upgrad.patterns.Constants.SourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DiseaseCountFacade {
    private final IndiaDiseaseStatFactory indiaDiseaseStat;
    //create a private object indiaDiseaseStat of type IndiaDiseaseStatFactory

    @Autowired
    public DiseaseCountFacade(IndiaDiseaseStatFactory indiaDiseaseStat)
    {
        this.indiaDiseaseStat = indiaDiseaseStat;
    }
    public Object getDiseaseShCount() {
        try {
            return indiaDiseaseStat.GetInstance(SourceType.DiseaseSh).GetActiveCount();
        } catch (Exception e) {
            return null;
        }
    }

    public Object getJohnHopkinCount() {
        try {
            return indiaDiseaseStat.GetInstance(SourceType.JohnHopkins).GetActiveCount();
        } catch (Exception e) {
            return null;
        }
    }

    //create a public method getDiseaseShCount() that has Object as its return type
    //call the GetInstance method with DiseaseSh as the parameter using the indiaDiseaseStat object created on line 10
    //Based on the strategy returned, call the specific implementation of the GetActiveCount method
    //return the response


    //create a public method getJohnHopkinCount() that has Object as its return type
    //call the GetInstance method with JohnHopkins as the parameter using the indiaDiseaseStat object created on line 10
    //Based on the strategy returned, call the specific implementation of the GetActiveCount method
    //return the response



    public Object getInfectedRatio(String sourceType) throws IllegalArgumentException {
        try {
            Float population = 900000000F;
            SourceType sourceEnum = SourceType.valueOf(sourceType);
            Float active = Float.valueOf(indiaDiseaseStat.GetInstance(sourceEnum).GetActiveCount());
            Float percent = Float.valueOf((active / population));
            return String.format("%.3f", percent * 100);
        }
        catch (IllegalArgumentException e) {
            String message = String.format("Invalid source type specified. Available source types: %s, %s", SourceType.DiseaseSh, SourceType.JohnHopkins);
            throw new IllegalArgumentException(message);
        } catch (Exception e) {
            return null;
        }
    }
}