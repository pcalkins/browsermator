/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package browsermator.com;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import javax.swing.SwingWorker;

/**
 * Wrapper for the GUI listener.
 *
 * <T> return type
 * <S> intermediary type (the "shout out" to listen for)
 */
public abstract class ListenerTask<T, S> extends SwingWorker<T, S> 
        implements PropertyChangeListener {

    private LoudCall<T, S> aMethod;

    public ListenerTask(LoudCall<T, S> aMethod) {
        this.aMethod = aMethod;
    }

    @Override
    protected T doInBackground() throws Exception {
        aMethod.addListener(this);
        return aMethod.call();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("shoutOut".equals(evt.getPropertyName())) {
            publish((S)evt.getNewValue());
        }
    }

    @Override
    protected abstract void process(List<S> chunks);
}