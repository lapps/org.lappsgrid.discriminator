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

import spock.lang.*

/**
 * @author Keith Suderman
 */
class UriTests extends Specification {

    def "test URI short names"() {
        expect:
            expected == DiscriminatorRegistry.getName(uri)

        where:
            uri          | expected
            Uri.ERROR    | "error"
            Uri.OK       | "ok"
            Uri.TOKEN    | "token"
            Uri.SENTENCE | "sentence"
    }

    def "test URI id values"() {
        expect:
            expected == DiscriminatorRegistry.getTypeForUri(uri)

        where:
            uri       | expected
            Uri.ERROR | Types.ERROR
            Uri.OK    | Types.OK
            Uri.TOKEN | Types.TOKEN
            Uri.SENTENCE | Types.SENTENCE
            Uri.POS   | Types.POS
            Uri.LEMMA | Types.LEMMA
            Uri.LIST  | Types.LIST
            Uri.INDEX | Types.INDEX
    }
}
