package org.example.Serialization;

import lombok.extern.java.Log;

import java.io.*;
import java.util.logging.Level;


@Log
public class CloningUtility {

    /**
     * Created deep copy of provided object using serialization.
     *
     * @param object object to be cloned
     * @param <T>    type of the object
     * @return deep copy of the object
     * @throws IllegalStateException on any I/O or class cast error, should not happen
     */
    @SuppressWarnings("unchecked")
    public static <T extends Serializable> T clone(T object) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();//Closing this stream has no effect
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            return (T) ois.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            log.log(Level.SEVERE, ex.getMessage(), ex);
            throw new IllegalStateException(ex);
        }

    }
}

