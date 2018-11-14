/*
 * Copyright (C) 2018 The Language Applications Grid
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.lappsgrid.discriminator.test.org.lappsgrid.discriminator;

import org.junit.Test;
import org.lappsgrid.discriminator.DiscriminatorRegistry;
import org.lappsgrid.discriminator.Discriminators;

import static org.junit.Assert.*;

/**
 *
 */
public class DiscriminatorRegistryTest
{
	@Test
	public void testPosTagSets() {
		String uri = DiscriminatorRegistry.getUriByReflection("tags-pos");
		assertEquals(Discriminators.Uri.TAGS_POS, uri);

		uri = DiscriminatorRegistry.getUri("tags-pos-penntb");
		assertEquals(Discriminators.Uri.TAGS_POS_PENNTB, uri);
	}
}
