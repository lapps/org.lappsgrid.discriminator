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
