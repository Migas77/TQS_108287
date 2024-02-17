/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tqs.sets;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tqs.sets.BoundedSetOfNaturals;

import java.nio.file.FileAlreadyExistsException;

/**
 * @author ico0
 */
class BoundedSetOfNaturalsTest {
    private BoundedSetOfNaturals setA;
    private BoundedSetOfNaturals setB;
    private BoundedSetOfNaturals setC;

    @BeforeEach
    public void setUp() {
        setA = new BoundedSetOfNaturals(1);
        setB = BoundedSetOfNaturals.fromArray(new int[]{10, 20, 30, 40, 50, 60});
        setC = new BoundedSetOfNaturals(3);
    }

    @AfterEach
    public void tearDown() {
        setA = setB = setC = null;
    }

    @Test
    public void testAddElement() {

        setA.add(99);
        assertTrue(setA.contains(99), "add: added element not found in set.");
        assertEquals(1, setA.size());

        // must fail with exception (reached max size)
        assertThrows(IllegalArgumentException.class, () -> setB.add(1));

        // must fail with exception (trying to insert existent value)
        setC.add(1);
        assertThrows(IllegalArgumentException.class, () -> setC.add(1));
    }

    @Test
    public void testAddFromBadArray() {
        int[] elems = new int[]{10, -20, -30};

        // must fail with exception (-20 and -30 are not valid for neither numbers nor stars)
        assertThrows(IllegalArgumentException.class, () -> setC.add(elems));
    }


}
