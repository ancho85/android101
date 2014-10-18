/**
 * 
 */
package com.example.blockdenotas;

import android.graphics.Color;

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

    public Integer getPrioridadColor() {
        Integer color;
        switch (prioridad) {
            case 3:
                color = Color.RED;
                break;
            case 2:
                color = Color.YELLOW;
                break;
            case 1:
                color = Color.GREEN;
                break;
            default:
                color = Color.LTGRAY;
                break;
        }
        return color;

    }

}
