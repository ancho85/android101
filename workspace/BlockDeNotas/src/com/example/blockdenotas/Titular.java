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

    public Integer getPrioridadColor() {
        Integer color;
        switch (prioridad) {
            case 3:
                color = android.R.color.holo_red_light;
                break;
            case 2:
                color = android.R.color.holo_orange_light;
                break;
            case 1:
                color = android.R.color.holo_green_light;
                break;
            default:
                color = android.R.color.holo_blue_bright;
                break;
        }
        return color;

    }

}
