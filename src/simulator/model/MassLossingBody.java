
package simulator.model;

import simulator.misc.Vector2D;

public class MassLossingBody extends Body {
    private double lossFactor, lossFrequency, c;

    public MassLossingBody(String id, Vector2D velocidad, Vector2D posicion, Double masa, Double LFac, Double LFreq) {

        super(id, velocidad, posicion, masa);
        lossFactor=LFac;
        lossFrequency=LFreq;
        c=0.0;
    }

    void move(double t) {
        super.move(t);
        c+=t;

        if(c>=lossFrequency) {
            c= 0.0;
            m = m * (1- lossFactor);
        }

    }

}