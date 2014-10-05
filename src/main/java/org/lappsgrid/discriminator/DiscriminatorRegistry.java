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
package org.lappsgrid.discriminator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.net.URI;
import java.util.*;

import org.lappsgrid.discriminator.core.DiscriminatorImpl;


/**
 * The DiscriminatorRegistry serves as the source of all
 * knowledge for {@link Discriminator} objects.
 * <p>
 * The DiscriminatorRegistry class is used to map between a Discriminator's
 * integer identifier, its short name, and its URI.
 * <p>
 * This class is subject to change as needs evolve.
 *
 * @author Keith Suderman
 */
public class DiscriminatorRegistry
{
   static private long nextId = 0;
   static private Map<String, Discriminator> nameIndex = new HashMap<String, Discriminator>();
   static private Map<String, Discriminator> uriIndex = new HashMap<String, Discriminator>();
   static private Map<Long, Discriminator> typeIndex = new HashMap<Long, Discriminator>();

   // TODO banks should likely be much larger; say 1024 * 1024
   static protected final long BANK_SIZE = 1024;

   static
   {
      try
      {
         initialize();
      }
      catch (IOException e)
      {
         // TODO This exception should be logged.
         e.printStackTrace();
      }
   }

   static public Discriminator getByName(String name)
   {
      return nameIndex.get(name);
   }

   static public Discriminator getByUri(String uri)
   {
      return uriIndex.get(uri);
   }

   static public Discriminator getByType(long type)
   {
      return typeIndex.get(type);
   }

   static public long getType(String key)
   {
      Discriminator d = uriIndex.get(key);
      if (d == null)
      {
         d = nameIndex.get(key);
         if (d == null)
         {
            return -1;
         }
      }
      return d.getId();
   }

   static public String getName(String uri)
   {
      Discriminator d = uriIndex.get(uri);
      if (d == null)
      {
         return null;
      }
      return d.getName();
   }

   static public String getName(long type)
   {
      Discriminator d = typeIndex.get(type);
      if (d == null)
      {
         return null;
      }
      return d.getName();

   }

   static public String getUri(String name)
   {
      Discriminator d = nameIndex.get(name);
      if (d == null)
      {
         return null;
      }
      return d.getUri();
   }

   static public String getUri(long type)
   {
      Discriminator d = typeIndex.get(type);
      if (d == null)
      {
         return null;
      }
      return d.getUri();

   }



   /**
    * The <code>register</code> methods should only be used during
    * testing and development. Calling this <code>register</code>
    * method is <b>not</b> the way to register new discriminators
    * with the registry.
    */
   static private long register(String name, String uri)
   {
      return register(name, (Discriminator) null, uri);
   }

   /**
    * The <code>register</code> methods should only be used during
    * testing and development. Calling this <code>register</code>
    * method is <b>not</b> the way to register new discriminators
    * with the registry.
    */
   static private long register(String name, Discriminator parent, String uri)
   {
      name = name.toLowerCase();
      Discriminator d = nameIndex.get(name);
      if (d == null)
      {
         d = create(name, parent, uri);
         nameIndex.put(name, d);
         typeIndex.put(d.getId(), d);
         uriIndex.put(uri, d);
//            System.out.println("Registered " + name + " with id " + d.getId());
      }
      return d.getId();
   }

   /**
    * The <code>register</code> methods should only be used during
    * testing and development. Calling this <code>register</code>
    * method is <b>not</b> the way to register new discriminators
    * with the registry.
    */
   static private long register(String name, List<Discriminator> parents, String uri)
   {
      Discriminator d = nameIndex.get(name);
      if (d == null)
      {
         d = create(name, parents, uri);
         nameIndex.put(name, d);
         typeIndex.put(d.getId(), d);
         uriIndex.put(uri, d);
//            System.out.println("Registered " + name + " with id " + d.getId());
      }
      return d.getId();
   }

   /**
    * Returns a list (array) of all discriminator values used
    * by the registry.
    *
    */
   static public long[] types()
   {
      int i = 0;
      long[] array = new long[nameIndex.size()];
      for (Discriminator d : nameIndex.values())
      {
         array[i] = d.getId();
         ++i;
      }
      Arrays.sort(array);
      return array;
   }

   static public String[] names()
   {
      List<String> list = new ArrayList<String>(nameIndex.keySet());
      String[] array = new String[list.size()];
      return list.toArray(array);
   }

   static public List<Discriminator> discriminators()
   {
      List<Discriminator> result = new ArrayList<Discriminator>(nameIndex.values());
      Comparator<Discriminator> selector = new Comparator<Discriminator>() {
         @Override
         public int compare(Discriminator d1, Discriminator d2)
         {
            return (int)(d1.getId() - d2.getId());
         }
      };
      Collections.sort(result, selector);
      return result;
   }
   /**
    * Returns a non-negative number corresponding to the
    * discriminator value associated with this <code>name</code>.
    * Returns a negative value is there is no such discriminator.
    *
    * @param name The name of the discriminator
    * @return The id value associated with the discriminator name, or
    * -1 if there is no such discriminator.
    */
   static public long get(String name)
   {
      Discriminator d = nameIndex.get(name.toLowerCase());
      if (d == null)
      {
         d = uriIndex.get(name);
         if (d == null)
         {
            return -1;
         }
      }
      return d.getId();
   }

   static public String get(long type)
   {
      Discriminator d = typeIndex.get(type);
      if (d == null)
      {
         return null;
      }
      return d.getName();
   }

   /**
    * Returns true if the discriminator <code>parentName</code>
    * is a super-type of the <code>name</code> discriminator.
    * Returns false otherwise.
    */
   public static boolean isa(String name, String parentName)
   {
      Discriminator d = nameIndex.get(name);
      if (d == null)
      {
         return false;
      }
      Discriminator parent = nameIndex.get(parentName);
      if (parent == null)
      {
         return false;
      }

      return d.isa(parent);
   }

   /**
    * Returns true if the discriminator <code>parentName</code>
    * is a super-type of the <code>type</code> discriminator.
    * Returns false otherwise.
    */
   public static boolean isa(long type, long parentType)
   {
      Discriminator d = getByType(type);
      Discriminator parent = getByType(parentType);
      if ((d == null) || (parent == null))
      {
         return false;
      }
      return d.isa(parent);
   }

   static public long[] getAncestors(long type)
   {
      Discriminator d = typeIndex.get(type);
      if (d == null)
      {
         return new long[0];
      }
      return getAncestors(d);
   }

   static public long[] getAncestors(Discriminator d)
   {
      if (d == null)
      {
         return new long[0];
      }

      Set<Discriminator> ancestors = d.getAncestors();
      long[] result = new long[ancestors.size()];
      int index = 0;
      for (Discriminator ancestor : ancestors)
      {
         result[index] = ancestor.getId();
         ++index;
      }
      return result;
   }


   /**
    * Initializes the DiscriminatorRegistry.
    * <p/>
    * Currently the registry is initialized with values read from a
    * flat text file. In production use the registry should be
    * initialized from a proper type system of some sort.
    */
   static public void initialize() throws IOException
   {
//      ClassLoader loader = Thread.currentThread().getContextClassLoader();
//      if (loader == null)
//      {
        ClassLoader loader = DiscriminatorRegistry.class.getClassLoader();
//      }

      InputStream stream = loader.getResourceAsStream("DataTypes.txt");
      if (stream == null)
      {
			stream = loader.getResourceAsStream("/DataTypes.txt");
			if (stream == null)
			{
				throw new IOException("Could locate DataTypes.txt on the classpath.");
			}
      }
      BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
      String line = reader.readLine();
      while (line != null)
      {
//         System.out.println(line);
         line = trim(line);
         if (line.length() == 0)
         {
            line = reader.readLine();
            continue;
         }
         String[] parts = line.split("\\s+");
         if (parts.length == 1)
         {
            //register(parts[0]);
            throw new IOException("Invalid input line: " + line);
         }
         else if (parts.length > 1)
         {
//            System.out.println("Last part is " + parts[parts.length - 1]);
            int start = 0;
            String first = parts[start];
            if (first.endsWith(":"))
            {
               // If the first part ends with a colon it is the next
               // discriminator id value to be used, either expressed as
               // an integer literal or a "bank" number.
               ++start;
               int end = first.length() - 1;
               String token = first.substring(0, end);
               long value = -1;
               if (token.toLowerCase().startsWith("bank"))
               {
                  value = Integer.parseInt(token.substring(4)) * BANK_SIZE;
               } else
               {
                  value = Integer.parseInt(token);
               }
               if (value < nextId)
               {
                  throw new IOException("Invalid ID value specified in the DataTypes configuration: " + value);
               }
               nextId = value;
            }
            List<Discriminator> parents = new ArrayList<Discriminator>();
            for (int i = start + 1; i < parts.length - 1; ++i)
            {
//					System.out.println("Looking in nameIndex for " + parts[i]);
					Discriminator parent = nameIndex.get(parts[i]);
               if (parent == null)
               {
                  throw new IOException("Invalid parent type " + parts[i]);
               }
               parents.add(parent);
            }
            String uri = parts[parts.length - 1];
            if (uri.startsWith(":"))
            {
               uri = uri.substring(1);
            }
            else if (!uri.startsWith("http"))
            {
               uri = "http://vocab.lappsgrid.org/" + uri;
            }
//            System.out.println("URI is " + uri);
            register(parts[start], parents, uri);
         }
         line = reader.readLine();
      }

   }

   static private String trim(String line)
   {
      if (line.startsWith("#"))
      {
         return "";
      }

//      int index = line.indexOf("//");
//      if (index > 0)
//      {
//         return line.substring(0, index).trim();
//      }
      return line.trim();
   }

   static private Discriminator create(String name, Discriminator parent, String uri)
   {
      return new DiscriminatorImpl(name, parent, nextId++, uri);
   }

   static private Discriminator create(String name, List<Discriminator> parents, String uri)
   {
      return new DiscriminatorImpl(name, parents, nextId++, uri);
   }
}
