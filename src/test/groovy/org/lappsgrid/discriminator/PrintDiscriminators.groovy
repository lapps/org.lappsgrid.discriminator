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
