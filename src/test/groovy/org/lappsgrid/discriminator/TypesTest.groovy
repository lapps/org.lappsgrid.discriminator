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

import java.lang.reflect.Field

import static org.junit.Assert.*
import static java.lang.reflect.Modifier.*

/**
 * @author Keith Suderman
 */
class TypesTest {

    /** The bit mask for public static final fields. */
    public static final int PSF = PUBLIC | STATIC | FINAL

    // Returns true if the field is public static final and of the
    // required type.
    def test = { Class<?> type, Field field ->
        return (PSF == (field.modifiers & PSF)) && field.type == type
    }

    @Test
    void testURI() {
        println "TypesTest.testURI"
        def uriTest = test.curry(String.class)
//        def fields = Uri.getDeclaredFields().findAll {uriTest it}
        def fields = Discriminators.Uri.getDeclaredFields().findAll { uriTest it }

        fields.each { uriField ->
            String uri = uriField.get(null)
            // Get the corresponding fields from the Types class
//            Field typesField = Types.getDeclaredField(uriField.name)
            Field typesField = Discriminators.Values.getDeclaredField(uriField.name)
            long type = typesField.get(null);
            String shortName = DiscriminatorRegistry.get(type)
            assertTrue uri == DiscriminatorRegistry.getUri(type)
            assertTrue uri == DiscriminatorRegistry.getUri(shortName)
        }
        println "Passed."
    }

    @Test
    void testAncestors() {
        Discriminator type = DiscriminatorRegistry.getByName('one-per-line')
        assertNotNull type
        assertTrue type.ancestors.size() == 1
        println type.ancestors[0].name
    }

    @Ignore
    void printAllURI() {
        Uri.getDeclaredFields().findAll { it.type == String }.each {
            println it.get(null)
        }
    }
}
