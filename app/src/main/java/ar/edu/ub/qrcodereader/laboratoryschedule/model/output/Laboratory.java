package ar.edu.ub.qrcodereader.laboratoryschedule.model.output;

public enum Laboratory {
    LABORATORY_1("Laboratorio 1 - Multimedios"),
    LABORATORY_2("Laboratorio 2 - Software Libre"),
    LABORATORY_3("Laboratorio 3 - Programación"),
    LABORATORY_4("Laboratorio 4 - Nuevos Medios y Animación"),
    LABORATORY_5("Laboratorio 5 - Leo Pestchanket"),
    LABORATORY_6("Laboratorio 6 - TSOFT"),
    LABORATORY_7("Laboratorio 7 - Gestión Informática");

    private String id;

    Laboratory(String id) {
        this.id = id;
    }

    public String id(){
        return this.id;
    }
}
