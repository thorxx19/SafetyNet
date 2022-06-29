package com.safety.safetyNet.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safety.safetyNet.model.ListSafety;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;

@Slf4j
public class JsonUtils {

    public static List<ListSafety> jsonToObjectListSafety(String jsonStrinIn) {

        ObjectMapper mapper = new ObjectMapper();
        List<ListSafety> missions = null;

        try {
            missions = Arrays.asList(mapper.readValue(jsonStrinIn, ListSafety[].class));
        } catch (Exception e) {
            log.error("error :", e);
        }

        return missions;
    }
}
