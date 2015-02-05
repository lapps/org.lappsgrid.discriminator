package org.lappsgrid.discriminator

import groovy.xml.MarkupBuilder

/**
 * @author Keith Suderman
 */
class TableMaker {

    public static final File PATH = new File("/Users/suderman/Documents/LAPPS/Design")

    public static String getAncestors(Discriminator discriminator) {
        return discriminator.ancestors.collect { it.name }.join(", ")
    }

    public static void makeLatexTable(Writer writer, Closure selector) {
        makeLatexTable(writer, DiscriminatorRegistry.discriminators().findAll(selector))
    }
    public static void makeLatexTable(Writer writer) {
        makeLatexTable(writer, DiscriminatorRegistry.discriminators())
    }
    public static void makeLatexTable(Writer writer, List<Discriminator> discriminators) {
//        StringWriter writer = new StringWriter()
        writer.println """\\begin{longtable}{| r | l | l | p{3cm} | }
\\hline \\multicolumn{1}{|r|}{\\textbf{ID}} & \\multicolumn{1}{l|}{\\textbf{Name}} & \\multicolumn{1}{l|}{\\textbf{URI or media-type}} & \\multicolumn{1}{l|}{\\textbf{Ancestors}} \\\\ \\hline
\\endhead
"""
        discriminators.each { discriminator ->
//            Discriminator discriminator = DiscriminatorRegistry.getByType(id)
            String uri = discriminator.uri.replace("#", "\\#").replace("~", "\\texttildelow{}")
            writer.println "${discriminator.id} & ${discriminator.name} & ${uri} & ${getAncestors(discriminator)} \\\\ \\hline"
        }
        writer.println "\\end{longtable}"
//        new File(LATEX_PATH).text = writer.toString()
//        println writer.toString()
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

    public static void make(String filename) {
        StringWriter writer = new StringWriter()
        writer.println "% WARNING: This file is machine generated."
        writer.println "% Any changes made to this file are likely to be lost."
        makeLatexTable(writer)
        new File(PATH, filename).text = writer.toString()
        println "Wrote $filename"
    }
    public static void make(String filename, Closure selector) {
        StringWriter writer = new StringWriter()
        writer.println "% WARNING: This file is machine generated."
        writer.println "% Any changes made to this file are likely to be lost."
        makeLatexTable(writer, selector)
        new File(PATH, filename).text = writer.toString()
        println "Wrote $filename"
    }
    public static void main(args) {
        //makeMarkdownTable()
        make("discriminators.tex")
        make("media-types.tex") {
            !it.uri.startsWith("http")
        }
        Discriminator annotation = DiscriminatorRegistry.getByName("annotation")
        Discriminator license = DiscriminatorRegistry.getByName("license")
        Discriminator usage = DiscriminatorRegistry.getByName("usage");
        make("license-types.tex") { it.isa(license) }
        make("usage-types.tex") { it.isa(usage) }
        make("annotation-types.tex") { it.isa(annotation) }
    }

}
