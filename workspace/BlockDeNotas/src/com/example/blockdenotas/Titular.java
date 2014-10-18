/**
 * 
 */
package com.example.blockdenotas;

/**
 * @author carlos.gomez
 *
 */
public class Titular {

    /**
     * 
     */
    private String titulo;
    private String subtitulo;
    private Integer prioridad; 
    
    public Titular(String tit, String sub, Integer prio) {
        setTitulo(tit);
        setSubtitulo(sub);
        setPrioridad(prio);
    }
    
    public Integer getPrioridad() {
        return prioridad;
    }
    public void setPrioridad(Integer prioridad) {
        this.prioridad = prioridad;
    }
    public String getSubtitulo() {
        return subtitulo;
    }
    public void setSubtitulo(String subtitulo) {
        this.subtitulo = subtitulo;
    }
    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

}
