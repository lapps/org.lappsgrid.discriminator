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

package org.lappsgrid.discriminator.test;

import org.junit.Ignore;
import org.junit.Test;
import org.lappsgrid.discriminator.DiscriminatorRegistry;

import java.util.Arrays;

/**
 * @author Keith Suderman
 */
public class ListAll
{
   public ListAll()
   {

   }

   @Ignore
   public void listAll()
   {
      long[] types = DiscriminatorRegistry.types();
      Arrays.sort(types);
//      System.out.println("| Discriminator | Name |");
//      System.out.println("|:--------------|:-----|");
      for (long type : types)
      {
         String name = DiscriminatorRegistry.get(type);
         System.out.println(type + "\t" + name);
      }
   }
}
