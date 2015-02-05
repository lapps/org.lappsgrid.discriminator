package org.lappsgrid.discriminator

/**
 * Used to generate the .txt file used in the consistency tests.
 * @author Keith Suderman
 */
class PrintDiscriminators {
    static void main(args) {
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
