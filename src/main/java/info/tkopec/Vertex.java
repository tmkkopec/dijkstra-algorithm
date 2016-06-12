package info.tkopec;

/**
 * Created by tkopec on 04/05/16.
 */
class Vertex {
    String id;
    double attr1;
    double attr2;
    double attr3;
    double attr4;


    Vertex(String id) {
        this.id = id;
    }

    Vertex(String id, double attr1, double attr2, double attr3, double attr4) {
        this.id = id;
        this.attr1 = attr1;
        this.attr2 = attr2;
        this.attr3 = attr3;
        this.attr4 = attr4;
    }

    @Override
    public String toString() {
        return "Vertex{" +
                "id='" + id + '\'' +
                ", attr1=" + attr1 +
                ", attr2=" + attr2 +
                ", attr3=" + attr3 +
                ", attr4=" + attr4 +
                '}';
    }
}
