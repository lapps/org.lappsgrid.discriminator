---
layout: default
title: org.lappsgrid.discriminator
---

<section>
<h3>Index</h3>
<ol>
<li><a href="site/index.html">Maven site</a></li>
<li><a href="site/apidocs/index.html">Javadocs</a></li>
</ol>
</section>

<section>
<h3>Introduction</h3>
<p>Discriminators are an implementation detail used to abstract away the URI used to
identify objects. This allows code to be written that will not be affected as the 
<a href="http://vocab.lappsgrid.org">LAPPS vocabulary</a> evolves.  For example, the
concept of a <i>Token</i> will never change, but the URI used to represent the concept
of a <i>Token</i> may.</p>

<h3>Discriminators</h3>
<p>A Discriminator object consists of an integer identifier, a short name, and a URI.
Discriminator objects support multiple inheritance and the <tt>isa()</tt> method can
be used to determine if one discriminator is an instance of another.
</p>

<h3>DiscriminatorRegistry</h3>
<p>The DiscriminatorRegistry class is used to obtain instances of Discriminator objects
based on their integer identifier, short name, or URI.  The DiscriminatorRegistry also
contains methods to map between Discriminator's integer identifiers, short names, and URI.
</p>
<pre><code>
Discriminator token = DiscriminatorRegistry.getByName("token");
Discriminator annotation = DiscriminatorRegistry.getByName("annotation");
assertTrue(token.isa(annotation));

long tokenId = DiscriminatorRegistry.get("token");
long annotationId = DiscriminatorRegistry.get("http://vocab.lappsgrid.org/Annotation");
assertTrue(DiscriminatorRegistry.isa(tokenId, annotationId);

</code></pre>

<p>See the <a href="site/apidocs/index.html">Javadocs</a> for more information.

<h3>Maven</h3>

<pre>
&lt;dependency>
	&lt;groupdId>org.lappsgrid&lt;/groupId>
	&lt;artifactId>discriminator&lt;/artifactId>
	&lt;version>2.0.0-SNAPSHOT&lt;/version>
&lt;/dependency>
</pre>
</section>
