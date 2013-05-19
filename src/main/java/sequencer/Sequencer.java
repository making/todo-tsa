/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sequencer;

import java.util.concurrent.atomic.AtomicLong;
import javax.inject.Singleton;

/**
 *
 * @author maki
 */
@Singleton
public class Sequencer {
    private static final AtomicLong counter = new AtomicLong(0);
    
    public String getNext() {
        return String.valueOf(counter.incrementAndGet());
    }
}
