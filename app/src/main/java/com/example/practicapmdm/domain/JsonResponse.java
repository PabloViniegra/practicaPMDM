package com.example.practicapmdm.domain;

import com.example.practicapmdm.models.Pool;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class JsonResponse {
    @SerializedName("@graph")
    @Expose
    public final List<Pool> results = null;
}
