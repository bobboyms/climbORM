package br.com.climb.systemcache;

import br.com.climb.configfile.interfaces.ConfigFile;
import br.com.climb.core.PersistentEntity;
import br.com.climb.core.mapping.Cachable;
import br.com.climb.exception.CacheSystemException;
import br.com.climb.systemcache.model.CommandDTO;
import br.com.climb.utils.ReflectionUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CacheManagerImp implements CacheManager {

    private ConfigFile configFile;

    protected CacheManagerImp(ConfigFile configFile) {
        this.configFile = configFile;
    }

    public static CacheManager build(ConfigFile configFile) {
        return new CacheManagerImp(configFile);
    }

    private HttpURLConnection getConnectionHttpPost() throws IOException {
        URL url = new URL("http://127.0.0.1:7070/api/v1/command/set");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");

        return conn;
    }

    private String generateKey(Object value) {
        var id = ((PersistentEntity)value).getId().toString();
        var tableName =  ReflectionUtil.getTableName(value);
        return String.valueOf((id + tableName).hashCode());
    }

    @Override
    public void addToCache(Object value) throws IOException {

        Cachable Cachable = value.getClass().getAnnotation(Cachable.class);
        if (Cachable == null || !configFile.isCache()) {
            return;
        }

        final var objectMapper = new ObjectMapper();
        final var commandDTO = new CommandDTO();

        commandDTO.setKey(generateKey(value));
        commandDTO.setValue(objectMapper.writeValueAsString(value));

        String message = objectMapper.writeValueAsString(commandDTO);

        final var conn = getConnectionHttpPost();
        final var os = conn.getOutputStream();
        os.write(message.getBytes());
        os.flush();

        if (conn.getResponseCode() != 200) {
            throw new CacheSystemException("Unable to connect to the SPEEDUP cache service error code : "
                    + conn.getResponseCode());
        }

        conn.disconnect();

    }

    @Override
    public Object getValueCache(Class classe, Long id) throws Exception {

        Cachable Cachable = (Cachable) classe.getAnnotation(Cachable.class);
        if (Cachable == null || !configFile.isCache()) {
            return null;
        }

        Object tempObject = classe.getDeclaredConstructor().newInstance();
        ((PersistentEntity)tempObject).setId(id);

        URL url = new URL("http://127.0.0.1:7070/api/v1/command/get/"+generateKey(tempObject));
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");

        if (conn.getResponseCode() != 200) {
            throw new CacheSystemException("Unable to connect to the SPEEDUP cache service error code : "
                    + conn.getResponseCode());
        }

        BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

        String output;
        StringBuilder stringBuilder = new StringBuilder();
        while ((output = br.readLine()) != null) {
            stringBuilder.append(output);
        }

        CommandDTO commandDTO = new ObjectMapper().readValue(stringBuilder.toString(), CommandDTO.class);

        if (commandDTO == null || commandDTO.getValue() == null) {
            return null;
        }

        return new ObjectMapper().readValue(commandDTO.getValue().toString(), classe);
    }

}
