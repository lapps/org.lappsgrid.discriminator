package org.lappsgrid.discriminator;

/**
 * @author Keith Suderman
 */
public class Uri
{
   public static final String ERROR = get("error");
   public static final String OK = get("ok");
   public static final String META = get("meta");
   public static final String TEXT = get("text");
   public static final String XML = get("xml");
   public static final String INDEX = get("index");
   public static final String QUERY = get("query");
   public static final String STRING_LIST = get("string-list");

   public static final String LIST = get("list");
   public static final String GET = get("get");
   public static final String REGEX = get("regex");
   public static final String COMPOSITE = get("composite");

   public static final String DOCUMENT = get("document");
   public static final String GATE = get("gate");
   public static final String UIMA = get("uima");
   public static final String STANFORD = get("stanford");
   public static final String OPENNLP = get("opennlp");
   public static final String PTB = get("ptb");
   public static final String GRAF = get("graf");
   public static final String JSON = get("json");
   public static final String JSON_LD = get("json-ld");
   public static final String ONE_PER_LINE = get("one-per-line");
   public static final String LAPPS = get("lapps");
   public static final String TSV = get("tsv");
   public static final String CSV = get("csv");

   public static final String TOKEN = get("token");
   public static final String SENTENCE = get("sentence");
   public static final String POS = get("pos");
   public static final String NAMED_ENTITES = get("ne");
   public static final String PERSON = get("person");
   public static final String DATE = get("date");
   public static final String LOCATION = get("location");
   public static final String ORGANIZATION = get("organization");
   public static final String NOUN_CHUNK = get("nchunk");
   public static final String VERB_CHUNK = get("vchunk");
   public static final String COREF = get("coref");
   public static final String LEMMA = get("lemma");
   public static final String LOOKUP = get("lookup");

   // Don't allow instances of this class to be created.
   private Uri()
   {
   }

   private static String get(String name)
   {
      Discriminator d = DiscriminatorRegistry.getByName(name);
      return d.getUri();
   }
}
