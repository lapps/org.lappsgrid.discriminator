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

/**
 * Static final definitions of the most commonly used discriminator types.
 *
 * @deprecated Use the values defined in the {@link org.lappsgrid.discriminator.Constants Constants} class.
 * @author Keith Suderman
 */
public final class Types
{
   public static final long ERROR = get("error");
   public static final long OK = get("ok");
   public static final long META = get("meta");
   public static final long TEXT = get("text");
   public static final long XML = get("xml");
   public static final long INDEX = get("index");
   public static final long QUERY = get("query");
   public static final long STRING_LIST = get("string-list");

   public static final long LIST = get("list");
   public static final long GET = get("get");
   public static final long SIZE = get("size");
   public static final long REGEX = get("regex");
   public static final long COMPOSITE = get("composite");

   public static final long DOCUMENT = get("document");
   public static final long GATE = get("gate");
   public static final long UIMA = get("uima");
   public static final long STANFORD = get("stanford");
   public static final long OPENNLP = get("opennlp");
   public static final long PTB = get("ptb");
   public static final long GRAF = get("graf");
   public static final long JSON = get("json");
   public static final long JSON_LD = get("json-ld");
   public static final long LAPPS = get("lapps");
   public static final long ONE_PER_LINE = get("one-per-line");
   public static final long TSV = get("tsv");
   public static final long CSV = get("csv");

   /*
   public static final long GATE_DOCUMENT = get("gate-document");
   public static final long GATE_SENTENCE_ANNOTAION = get("gate-sentence");
   public static final long GATE_TOKEN_ANNOTATION = get("gate-token");
   public static final long GATE_POS_ANNOTATION = get("gate-pos");
   public static final long GRAF_RESOURCE_HEADER = get("graf-res-hdr");
   public static final long GRAF_DOCUMENT_HEADER = get("graf-doc-hdr");
   public static final long GRAF_STANDOFF_XML = get("graf-standoff");
public static final  A` long QUERY_LUCENE = get("lucene");
   public static final long QUERY_JQUERY = get("jquery");
   public static final long QUERY_REGEX = get("regex");
   */
   public static final long TOKEN = get("token");
   public static final long SENTENCE = get("sentence");
   public static final long POS = get("pos");
   public static final long NAMED_ENTITY = get("ne");
   public static final long PERSON = get("person");
   public static final long DATE = get("date");
   public static final long LOCATION = get("location");
   public static final long ORGANIZATION = get("organization");
   public static final long NOUN_CHUNK = get("nchunk");
   public static final long VERB_CHUNK = get("vchunk");
   public static final long COREF = get("coref");
   public static final long LEMMA = get("lemma");
   public static final long LOOKUP = get("lookup");
   public static final long MATCHES = get("matches");

   // Don't allow instances of this class to be created.
   private Types()
   {
   }

   private static long get(String name)
   {
      Discriminator d = DiscriminatorRegistry.getByName(name);
      return d.getId();
   }
}
