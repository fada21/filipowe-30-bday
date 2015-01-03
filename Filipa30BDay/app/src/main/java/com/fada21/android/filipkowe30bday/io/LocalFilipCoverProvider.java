package com.fada21.android.filipkowe30bday.io;

import android.content.Context;
import android.util.Log;

import com.fada21.android.filipkowe30bday.model.FilipCover;
import com.fada21.android.filipkowe30bday.model.JSONResources;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class LocalFilipCoverProvider implements IFilipCoverProvider {

    public static final String JSON_FILE = "filip_cover_data.json";

    private final Context ctx;

    public static LocalFilipCoverProvider createProvider(Context context) {
        return new LocalFilipCoverProvider(context);
    }

    private LocalFilipCoverProvider(Context context) {
        ctx = context;
    }

    @Override
    public List<FilipCover> getFilipCoverList() {
        try {
            InputStream inputStream = ctx.getAssets().open(JSON_FILE);
            ObjectMapper mapper = new ObjectMapper();
            JSONResources jsonResources = mapper.readValue(inputStream, JSONResources.class);
            List<FilipCover> resources = jsonResources.getResources();
            return resources;
        } catch (JsonMappingException e) {
            Log.e(getClass().getSimpleName(), "Error mapping json file", e);
        } catch (JsonParseException e) {
            Log.e(getClass().getSimpleName(), "Error parsing json file", e);
        } catch (IOException e) {
            Log.e(getClass().getSimpleName(), "Error reading assets file", e);
        }
        return null;
    }

}
