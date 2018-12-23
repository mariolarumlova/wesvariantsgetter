package model;

import java.util.HashMap;
import java.util.Map;

public class Config {

    private Map<String, Object> samples;
    private Map<String, Object> genome;
    private Map<String, Object> programs;
    private Map<String, Object> rules;

    public Config() {
    }

    public Config(Map<String, Object> samples, Map<String, Object> genome, Map<String, Object> programs, Map<String, Object> rules) {
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

    @Override
    public String toString() {
        return "Config{" +
                "samples=" + samples +
                ", genome=" + genome +
                ", programs=" + programs +
                ", rules=" + rules +
                '}';
    }
}
