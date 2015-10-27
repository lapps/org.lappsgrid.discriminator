/*
 * Copyright 2014 The Language Application Grid
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.lappsgrid.discriminator

import org.junit.*

import static org.junit.Assert.*

/**
 * Ensures discriminator ID values have not changed.
 * 
 * @author Keith Suderman
 */
//@Ignore
class ConsistencyTest {

    void runTest(String filename) {
        ClassLoader loader = ConsistencyTest.class.classLoader;
        String types = loader.getResource(filename).text
        types.eachLine { line ->
            if (!line.startsWith('#')) {
//                println line
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
        println "ConsistencyTest.testOriginals"
        runTest('type-list.txt')
        println "Done"
    }

    @Test
    void test2015_10_21() {
        println "ConsistencyTest.test2015_10_21"
        ClassLoader loader = ConsistencyTest.class.classLoader;
        String types = loader.getResource('./types-2015-10-21.txt')?.text
        assertNotNull types
        types.eachLine { line ->
            String[] parts = line.split("\\s+");
            long id = Long.parseLong(parts[0])
            Discriminator discriminator = DiscriminatorRegistry.getByType(id)
            assertTrue "Expected ${discriminator.name} Found: ${parts[1]}", discriminator.name == parts[1]

            int index = 2
            if (parts.length > 3) {
                List<String> expected = parts[2..-2] as List<String>
                int expectedSize = expected.size()
                int actualSize = discriminator.ancestors.size()
                assertTrue "expected ${expectedSize} found ${actualSize}", expectedSize == actualSize
                List<String> found = discriminator.ancestors.collect { it.name }
                if (!expected.containsAll(found)) {
                    println "Assertion failed on: " + line
                    println "Expected"
                    println expected.join(', ')
                    println "Found"
                    println found.join(', ')
                    fail()
                }
//                assertTrue ancestors.containsAll(expected)
//                assertTrue expected.containsAll(ancestors)
            }
            else {
                assertTrue discriminator.ancestors.isEmpty()
            }
            String message = "${line}\nExpected ${parts[-1]} Found ${discriminator.uri}"
            assertTrue message, normalize(parts[-1]) == normalize(discriminator.uri)
        }
        println "Done"
    }

    @Test
    void test2014_06_11() {
        println "ConsistencyTest.test2014_06_11"
        runTest("types-2014-06-11.txt")
        println "Done"
    }

    @Ignore
    void test2014_10_04() {
        println "ConsistencyTest.test2014_10_04"
        ClassLoader loader = ConsistencyTest.class.classLoader;
        String types = loader.getResource('types-2014-10-04.txt')?.text
        assertNotNull types
        types.eachLine { line ->
            String[] parts = line.split("\\s+");
            long id = Long.parseLong(parts[0])
            Discriminator discriminator = DiscriminatorRegistry.getByType(id)
            assertTrue discriminator.name == parts[1]

            int index = 2
            if (parts.length > 3) {
                List<String> expected = parts[2..-2] as List<String>
                int expectedSize = expected.size()
                int actualSize = discriminator.ancestors.size()
                assertTrue "expected ${expectedSize} found ${actualSize}", expectedSize == actualSize
                List<String> found = discriminator.ancestors.collect { it.name }
                if (!expected.containsAll(found)) {
                    println "Assertion failed on: " + line
                    println "Expected"
                    println expected.join(', ')
                    println "Found"
                    println found.join(', ')
                    fail()
                }
//                assertTrue ancestors.containsAll(expected)
//                assertTrue expected.containsAll(ancestors)
            }
            else {
                assertTrue "${discriminator.name} has ancenstors", discriminator.ancestors.isEmpty()
            }
            String message = "${line}\nExpected ${parts[-1]} Found ${discriminator.uri}"
            assertTrue message, normalize(parts[-1]) == normalize(discriminator.uri)
        }
        println "Done"
    }

    String normalize(String input) {
        return input.replaceAll(' ', '').toLowerCase()
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

    // Used to generate the types-2014-10-04.txt file.
    @Ignore
    void printDiscriminatorList() {
        List<Discriminator> discriminators = DiscriminatorRegistry.discriminators()
        discriminators.each { Discriminator discriminator ->
            discriminator.with {
                print id
                print ' '
                print name
                print ' '
                print ancestors.collect{ it.name }.join(' ')
                print ' '
                println uri
            }
        }
    }

}
