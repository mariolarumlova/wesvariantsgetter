package model;

import tools.PreferencesManager;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Config {

    private Map<String, Object> samples;
    private Map<String, Object> genome;
    private Map<String, Object> programs;
    private Map<String, Object> rules;
    private Map<String, Object> miniconda3;
    private Map<String, Object> env;

    public Config() throws PreferencesManager.IncorrectKeyException, IOException, PreferencesManager.UnsupportedTypeException {
        Map<String, Object> miniconda3 = new HashMap<>();
        miniconda3.put("path", PreferencesManager.getInstance().getPreference("miniconda3", String.class));
        this.miniconda3 = miniconda3;

        Map<String, Object> env = new HashMap<>();
        env.put("name", PreferencesManager.getInstance().getPreference("env_name", String.class));
        this.env = env;
    }

    public Config(Map<String, Object> samples, Map<String, Object> genome, Map<String, Object> programs, Map<String, Object> rules) throws IOException, PreferencesManager.IncorrectKeyException, PreferencesManager.UnsupportedTypeException {
        this();
        this.samples = samples;
        this.genome = genome;
        this.programs = programs;
        this.rules = rules;
    }

    public Map<String, Map<String, Object>> getAsMap() {
        Map<String, Map<String, Object>> map = new HashMap<>();
        map.put("samples", this.getSamples());
        map.put("genome", this.getGenome());
        map.put("programs", this.getPrograms());
        map.put("rules", this.getRules());
        map.put("miniconda3", this.getMiniconda3());
        map.put("env", this.getEnv());
        return map;
    }

    public Map<String, Object> getSamples() {
        return samples;
    }

    public void setSamples(Map<String, Object> samples) {
        this.samples = samples;
    }

    public Map<String, Object> getGenome() {
        return genome;
    }

    public void setGenome(Map<String, Object> genome) {
        this.genome = genome;
    }

    public Map<String, Object> getPrograms() {
        return programs;
    }

    public void setPrograms(Map<String, Object> programs) {
        this.programs = programs;
    }

    public Map<String, Object> getRules() {
        return rules;
    }

    public void setRules(Map<String, Object> rules) {
        this.rules = rules;
    }

    public Map<String, Object> getMiniconda3() {
        return miniconda3;
    }

    public void setMiniconda3(Map<String, Object> miniconda3) {
        this.miniconda3 = miniconda3;
    }

    public Map<String, Object> getEnv() {
        return env;
    }

    public void setEnv(Map<String, Object> env) {
        this.env = env;
    }

    @Override
    public String toString() {
        return "Config{" +
                "samples=" + samples +
                ", genome=" + genome +
                ", programs=" + programs +
                ", rules=" + rules +
                ", miniconda3=" + miniconda3 +
                ", envname='" + env +
                '}';
    }
}
