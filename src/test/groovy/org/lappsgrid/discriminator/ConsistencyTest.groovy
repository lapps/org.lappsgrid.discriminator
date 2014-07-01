package org.lappsgrid.discriminator

import org.junit.*
import static org.junit.Assert.*

/**
 * Ensures discriminator ID values have not changed.
 * 
 * @author Keith Suderman
 */
class ConsistencyTest {

    void runTest(String filename) {
        ClassLoader loader = ConsistencyTest.class.classLoader;
        String types = loader.getResource(filename).text
        types.eachLine { line ->
            if (!line.startsWith('#')) {
                String[] parts = line.split("\t");
                assertTrue parts.length == 2
                long expected = Long.parseLong(parts[0])
                String name = parts[1]
                long actual = DiscriminatorRegistry.get(name);
                String message = "Type id for ${name} has changed. Expected: ${expected} Found: ${actual}"
                assertTrue message, expected == DiscriminatorRegistry.get(name)
            }
        }
    }

    @Test
    void testOriginals() {
        runTest('type-list.txt')
    }

    @Test
    void test2014_06_11() {
        runTest("types-2014-06-11.txt")
    }

    @Ignore
    void printAll() {
        long[] types = DiscriminatorRegistry.types()
        Arrays.sort(types)
        types.each { type ->
            String name = DiscriminatorRegistry.get(type)
            println "$type\t$name"
        }
    }

}
