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
import static org.lappsgrid.discriminator.Discriminators.Uri

/**
 * @author Keith Suderman
 */
@Ignore
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
            assert uri == DiscriminatorRegistry.getUri(type)
            assert uri == DiscriminatorRegistry.getUri(shortName)
        }
        println "Passed."
    }

    @Test
    void byName() {
        Discriminator discriminator = DiscriminatorRegistry.getByName("lif")
        assert null != discriminator.uri
        assert Uri.LIF == discriminator.uri

        discriminator = DiscriminatorRegistry.getByName("gate")
        assert null != discriminator
        assert Uri.GATE == discriminator.uri

        DiscriminatorRegistry.names().each { String name ->
            discriminator = DiscriminatorRegistry.getByName(name)
            assert discriminator != null
            assert name == discriminator.name

            String d2 = DiscriminatorRegistry.getUriByReflection(name)
            if (discriminator.uri != d2) {
                //TODO this should cause the test to fail.
                println "WARNING: duplicate short name values: $name for ${discriminator.uri} and $d2"
            }
        }
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
        Discriminators.Uri.getDeclaredFields().findAll { it.type == String }.each {
            println it.get(null)
        }
    }
}
