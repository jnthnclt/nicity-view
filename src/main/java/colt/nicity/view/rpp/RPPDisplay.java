package colt.nicity.view.rpp;

import colt.nicity.core.collection.CArray;
import colt.nicity.core.collection.CSet;
import colt.nicity.core.lang.ASetObject;
import colt.nicity.core.lang.ICallback;
import colt.nicity.core.lang.UBase64;
import colt.nicity.core.memory.struct.XYWH_I;
import colt.nicity.view.canvas.FilerCanvas;
import colt.nicity.view.core.ADisplay;
import colt.nicity.view.interfaces.ICanvas;
import colt.nicity.view.interfaces.IView;

/**
 * 
 * @author jonathan
 */
public class RPPDisplay extends ADisplay {    
    /**
     * 
     * @param _view
     */
    public RPPDisplay(IView _view) {
        super(_view);
    }
    @Override
    public ICanvas display(long _who,float _w, float _h) {
        ICanvas c = new FilerCanvas(_who,new RPPFiler(),new ICallback() {
            @Override
            public Object callback(Object _value) {
                //System.out.println(_value);
                return _value;
            }
        });
        return c;
    }

    private long lastMillis = Long.MIN_VALUE;
    @Override
    public void displayable(ICanvas _g, XYWH_I _region) {
        _g.dispose();

        // keep from painting more that 30fps
        long millis = System.currentTimeMillis();
        if (lastMillis != Long.MIN_VALUE) {
            long d = millis - lastMillis;
            if ((d*2) < 1000/30) { // * 2 assuming next repaint will take similair amount of time
                long sleep = (1000/30)-(d*2);
                try { Thread.sleep(sleep); } catch(Exception x) {}
            }
        }
        lastMillis = millis;
        

        FilerCanvas c = (FilerCanvas)_g;
        try {
            RPPFiler f = (RPPFiler)c.filer();
            byte[] data = f.getBytes();
            RPP s = new RPP();
            s.region = _region;
            s.version = System.identityHashCode(data);
            s.rppBase64 = new String(UBase64.encode(data));
            Object[] _updateable = null;
            synchronized(updatesFor) {
                _updateable = updateable;// get stack copy
            }
            for(Object u:_updateable) ((PaintUpdates)u).add(s);
            for(Object u:_updateable) ((PaintUpdates)u).updated();

        } catch(Exception x) {
            
        }
    }

    final CSet<PaintUpdates> updatesFor = new CSet<PaintUpdates>();
    Object[] updateable = new Object[0];
    /**
     * 
     * @param _id
     * @return
     */
    public PaintUpdates updateFor(long _id) {
        synchronized(updatesFor) {
            PaintUpdates u = (PaintUpdates)updatesFor.get(_id);
            if (u == null) {
                u = new PaintUpdates(_id);
                updatesFor.add(u);
                updateable = updatesFor.getAll(PaintUpdates.class);
                displaying().paint();// repaint when someone new joins
            }
            return u;
        }
    }

    /**
     * 
     */
    public class PaintUpdates extends ASetObject {
        long who;
        CArray<RPP> update = new CArray<RPP>(RPP.class);
        final Object lock = new Object();
        int waiting = 0;
        PaintUpdates(long _who) {
            who = _who;
        }
        @Override
        public Object hashObject() {
            return who;
        }
        /**
         * 
         */
        public void updated() {
            synchronized(lock) {
                lock.notifyAll();
            }
        }
        /**
         * 
         * @param _update
         */
        public void add(RPP _update) {
            synchronized(lock) {
                update.insertLast(_update);
            }
        }
        /**
         * 
         * @param _wait
         * @return
         */
        public RPP[] remove(boolean _wait) {
            synchronized(lock) {
                if (update.getCount() > 0) return (RPP[])update.removeAll();
                else {
                    if (!_wait) return new RPP[0];
                    try {
                        lock.wait(300000); //!!
                    } catch (InterruptedException ex) {}
                    return (RPP[])update.removeAll();
                }
            }
        }
    }
}
