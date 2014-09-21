package org.lappsgrid.discriminator

import spock.lang.*


/**
 * @author Keith Suderman
 */
class RegistryTest extends Specification {
    def "test one-per-line ancestors"() {
        given:
            Discriminator d = DiscriminatorRegistry.getByName('one-per-line')

        expect:
            d != null
            d.ancestors != null
            d.ancestors.size() > 0
    }

    def "test tsv ancestors"() {
        given:
            Discriminator d = DiscriminatorRegistry.getByName('tsv')

        expect:
            d != null
            d.ancestors != null
            d.ancestors.size() > 0
    }

    def "error code tests"() {
        given:
            long discriminator = DiscriminatorRegistry.get(name)

        expect:
            discriminator == expected
            getUri(discriminator) == uri(value)
            getUri(name) == uri(value)
            DiscriminatorRegistry.get(discriminator) == name

        where:
            name    | expected | value
            "error" | 0        | "error"
            "ok"    | 1        | "ok"
    }

    def "test annotation URIs"() {
        given:
            long discriminator = DiscriminatorRegistry.get(name)

        expect:
            getUri(name) == vocab(expected)
            getUri(discriminator) == vocab(expected)

        where:
            name          | expected
            "annotation"  | "Annotation"
            "token"       | "Token"
            "sentence"    | "Sentence"
            "ne"          | "NamedEntity"
            "person"      | "Person"
            "date"        | "Date"
            "organization"| "Organization"
            "location"    | "Location"
            "lookup"      | "Lookup"

    }

    def "test chunks and features"() {
        given:
            long discriminator = DiscriminatorRegistry.get(name)

        expect:
            DiscriminatorRegistry.get(discriminator) == name
            getUri(discriminator) == vocab(expected)
            getUri(name) == vocab(expected)

        where:
            name     | expected
            "nchunk" | "NounChunk"
            "vchunk" | "VerbChunk"
            "pos"    | "Token#pos"
            "lemma"  | "Token#lemma"
    }

    def "test sizes"() {
        given:
            int types = DiscriminatorRegistry.types().length
            int names = DiscriminatorRegistry.names().length
            int discriminators = DiscriminatorRegistry.discriminators().size()

        expect:
            types == names
            types == discriminators
    }

    def uri(String name) {
        return "http://ns.lappsgrid.org/1.0/${name}"
    }
    def vocab(String name) {
        return "http://vocab.lappsgrid.org/${name}"
    }

    def getUri(long type) {
        Discriminator d = DiscriminatorRegistry.getByType(type)
        return d?.getUri()
    }

    def getUri(String name) {
        Discriminator d = DiscriminatorRegistry.getByName(name)
        return d?.getUri()
    }
}
