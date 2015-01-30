org.lappsgrid.discriminator
===========================

## Build Status

master[![Master Status](https://travis-ci.org/lapps/org.lappsgrid.discriminator.svg?branch=master)](https://travis-ci.org/lapps/org.lappsgrid.discriminator)
develop[![Develop Status](https://travis-ci.org/lapps/org.lappsgrid.discriminator.svg?branch=develop)](https://travis-ci.org/lapps/org.lappsgrid.discriminator)
feature[![Feature Status](https://travis-ci.org/lapps/org.lappsgrid.discriminator.svg?branch=feature%2Fv2.0.0)](https://travis-ci.org/lapps/org.lappsgrid.discriminator)

The `org.lappsgrid.discriminator` package provides an inventory of the URI used by
LAPPS services.

## Maven
```xml
<dependency>
	<groupId>org.lappsgrid</groupId>
	<artifactId>discriminator</artifactId>
	<version>2.0.0-SNAPSHOT</version>
</dependency>
```

## Discriminators

The concept of discriminators was introduced to allow code development while decisions
about the exact URI to use were still being made.  The `org.lappsgrid.discriminator.Discriminator`
class maps amongst an integer identifier, a short name, and a URI.  This allows programs
to refer to a discriminator by its short name and be immune to changes as the URI evolve.

### DiscriminatorRegistry

The `org.lappsgrid.discriminator.DiscriminatorRegistry` class is used to retrieve
discriminator instances based on the integer identifier, the short name, or the URI.

```java
Discriminator token = DiscriminatorRegistry.getByName("token");
Discriminator sentence = DiscriminatorRegistry.getByUri("http://vocab.lappsgrid.org/Sentence");
```

Discriminators also support multiple inheritance, that is, a discriminator may have one 
or more parent discriminators.  Both the `Discriminator` and `DiscriminatorRegistry` 
classes contain `isa()` methods than can be used to test if one discriminator is an
instance of another.

```java
long annotation = DiscriminatorRegistry.get("annotation");
long token = DiscriminatorRegistry.get("token");
assertTrue(DiscriminatorRegistry.isa(token, annotation));
```
or
```java
Discriminator annotation = DiscriminatorRegistry.getByName("annotation");
Discriminator token = DiscriminatorRegistry.getByName("token");
assertTrue(token.isa(annotation));
```
