/*-
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
package org.lappsgrid.discriminator.core;


import java.util.Set;

/**
 * Discriminator objects are used to provide type information about
 * Data objects returned by a DataSource.
 * <p>
 * Discriminator objects are never exposed directly to users. Rather
 * the user interacts with a DiscriminatorRegistry to
 * obtain discriminator id values and to map between discriminator
 * names and discriminator values.
 * 
 * @author Keith Suderman
 *
 */
public interface Discriminator extends java.io.Serializable
{   
   long getId();
   boolean isa(Discriminator discriminator);
   Set<Discriminator> getAncestors();
}
