/*
 * Copyright 2012-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.boot.test.assertj;

import org.assertj.core.api.Condition;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;

import org.springframework.util.Assert;

/**
 * Adapter class allowing a Hamcrest {@link Matcher} to be used as an AssertJ
 * {@link Condition}.
 * <p>
 * Usually used with the {@code is} method of {@code assertThat}, for example:
 *
 * <pre class="code">
 * assertThat("1234").is(Matched.when(startsWith("12")));
 * </pre>
 *
 * @param <T> The type of object that the condition accepts
 * @author Phillip Webb
 * @since 1.4
 */
public final class Matched<T> extends Condition<T> {

	private final Matcher<? extends T> matcher;

	private Matched(Matcher<? extends T> matcher) {
		Assert.notNull(matcher, "Matcher must not be null");
		this.matcher = matcher;
	}

	@Override
	public boolean matches(final T value) {
		if (this.matcher.matches(value)) {
			return true;
		}
		StringDescription description = new StringDescription();
		this.matcher.describeTo(description);
		describedAs(description.toString());
		return false;
	}

	public static <T> Condition<T> when(Matcher<? extends T> matcher) {
		return by(matcher);
	}

	public static <T> Condition<T> by(Matcher<? extends T> matcher) {
		return new Matched<T>(matcher);
	}

}
