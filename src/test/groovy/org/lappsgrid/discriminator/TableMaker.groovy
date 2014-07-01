package org.lappsgrid.discriminator

import groovy.xml.MarkupBuilder

/**
 * @author Keith Suderman
 */
class TableMaker {

    public static String getAncestors(Discriminator discriminator) {
        return discriminator.ancestors.collect { it.name }.join(", ")
    }

    public static void makeHtmlTable() {
        StringWriter writer = new StringWriter()
        MarkupBuilder html = new MarkupBuilder(writer)
        html.html {
            head {
                title "LAPPS Grid Discriminators"
            }
            body {
                p "These are the discriminator values currently registered."
                table {
                    thead {
                        th "ID"
                        th "Short Name"
                        th "URI"
                        th "Ancestors"
                    }
                    DiscriminatorRegistry.types().each { id ->
                        Discriminator discriminator = DiscriminatorRegistry.getByType(id)
                        tr {
                            td id
                            td discriminator.name
                            td discriminator.uri
                            td getAncestors(discriminator)
                        }
                    }
                }
            }
        }
        new File("target/Discriminators.html").text = writer.toString();
    }

    public static void makeMarkdownTable() {
        StringWriter writer = new StringWriter()
        writer.println "| ID | Short Name | URI | Ancestors |"
        println "|:---|:-----------|:----|:----------|"
        DiscriminatorRegistry.types(). each { id ->
            Discriminator discriminator = DiscriminatorRegistry.getByType(id)
            writer.println "| ${id} | ${discriminator.name} | ${discriminator.uri} | ${getAncestors(discriminator)} |"
        }
        println writer.toString()
    }

    public static void main(args) {
        makeMarkdownTable()
    }
}
