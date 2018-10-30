package ar.edu.ub.qrcodereader.laboratoryschedule.model.output;

public enum Status {
    OCCUPIED("Ocupado"),
    UNOCCUPIED("Libre");

    private String id;

    Status(String id) {
        this.id = id;
    }

    public String id(){
        return this.id;
    }
}
