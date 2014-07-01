package org.lappsgrid.discriminator

import spock.lang.*


/**
 * @author Keith Suderman
 */
class RegistryTest extends Specification {
    String uri(String name) {
        return "http://vocab.lappsgrid.org/${name}"
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
            "error" | 0        | "ns/error"
            "ok"    | 1        | "ns/ok"
    }

    def "test annotation URIs"() {
        given:
            long discriminator = DiscriminatorRegistry.get(name)

        expect:
            DiscriminatorRegistry.get(name) == discriminator
            getUri(name) == uri(expected)
            getUri(discriminator) == uri(expected)

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
            getUri(discriminator) == uri(expected)
            getUri(name) == uri(expected)

        where:
            name     | expected
            "nchunk" | "NounChunk"
            "vchunk" | "VerbChunk"
            "pos"    | "Token#pos"
            "lemma"  | "Token#lemma"
    }

    def "test sizes"() {
        given:
            int ntypes = DiscriminatorRegistry.types().length
            int nnames = DiscriminatorRegistry.names().length
            int ndiscriminators = DiscriminatorRegistry.discriminators().size()

        expect:
            ntypes == nnames
            ntypes == ndiscriminators
    }
    def getUri(long type) {
        Discriminator d = DiscriminatorRegistry.getByType(type)
        if (d == null) {
            return null
        }
        return d.getUri()
    }

    def getUri(String name) {
        Discriminator d = DiscriminatorRegistry.getByName(name)
        if (d == null) {
            return null
        }
        return d.getUri()
    }
}
