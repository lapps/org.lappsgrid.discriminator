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

import java.util.*;

public class DiscriminatorImpl implements Discriminator
{
   private static final long serialVersionUID = 1L;
   
   private long id;
   private String name;
   private int hashCode;
   private Set<Discriminator> parents;
   
   protected DiscriminatorImpl(String name, long id)
   {
      this.id = id;
      this.name = name;
      this.hashCode = name.hashCode();
      this.parents = new HashSet<Discriminator>();
   }
   
   public DiscriminatorImpl(String name, Discriminator parent, long id)   
   {
      this.id = id;
      this.name = name;
      this.hashCode = name.hashCode(); 
      this.parents = new HashSet<Discriminator>();
      if (parent != null)
      {
         this.parents.add(parent);
      }
   }
   
   public DiscriminatorImpl(String name, List<Discriminator> parents, long id)
   {
      this.id = id;
      this.name = name;
      this.hashCode = name.hashCode();
      this.parents = new HashSet<Discriminator>();
      for (Discriminator d : parents)
      {
         this.parents.add(d);
      }
   }
   
   public DiscriminatorImpl(String name, Discriminator[] parents, long id)
   {
      this.id = id;
      this.name = name;
      this.hashCode = name.hashCode();
      this.parents = new HashSet<Discriminator>();
      for (Discriminator d : parents)
      {
         this.parents.add(d);
      }
   }
   public long getId() { return id; }
   
   public boolean isa(Discriminator d)
   {
      if (id  == d.getId())
      {
         return true;
      }      
      if (parents.size() == 0)
      {
         return false;
      }
      for (Discriminator parent : parents)
      {
         if (parent.isa(d))
         {
            return true;
         }
      }
      return false;
   }

   public Set<Discriminator> getAncestors()
   {
      return new HashSet<Discriminator>(parents);
   }

   @Override
   public boolean equals(Object d)
   {
      if (!(d instanceof Discriminator))
      {
         return false;
      }
      return id == ((Discriminator)d).getId();
   }

   @Override
   public int hashCode()
   {
      return hashCode;
   }
}
