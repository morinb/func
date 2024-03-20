/*
 * Copyright 2024 Baptiste MORIN
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.morinb.func;


import java.io.Serializable;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * An interface representing either a left or a right value.
 *
 * @param <L> The type of the left value
 * @param <R> The type of the right value
 */
public sealed interface Either<L, R>
        extends Value<R>, Serializable
        permits Either.Left, Either.Right
{


    /**
     * Returns a new instance of Right with the specified value.
     *
     * @param <L>   the type of the left value
     * @param <R>   the type of the right value
     * @param value the value for the right side of the instance
     * @return a new instance of Right with the specified value
     */
    static <L, R> Right<L, R> right(final R value)
    {
        return new Right<>(value);
    }

    /**
     * Returns a new instance of Left with the specified value.
     *
     * @param <L>   the type of the left value
     * @param <R>   the type of the right value
     * @param value the value for the left side of the instance
     * @return a new instance of Left with the specified value
     */
    static <L, R> Left<L, R> left(final L value)
    {
        return new Left<>(value);
    }

    static <L, R> Either<L, R> noop()
    {
        return new Right<>(null);
    }

    static <L, R> Either<L, R> empty()
    {
        return new Left<>(null);
    }

    /**
     * Zips or accumulates the values from multiple Either instances. If all input Either instances are Right,
     * the transform function is applied to the values and the result is wrapped in a Right. If any of the input
     * Either instances are Left, the Left values are accumulated into a NonEmptyList and returned.
     *
     * @param <R>       the type of the Left value
     * @param <A>       the type of the Right value for Either a
     * @param <B>       the type of the Right value for Either b
     * @param <Z>       the type of the result value
     * @param a         the Either for value a
     * @param b         the Either for value b
     * @param transform the function to apply to the values if all Either instances are Right
     * @return an Either containing the accumulated Left values or the result of applying the transform function
     */
    static <R, A, B, Z> Either<NonEmptyList<R>, Z> zipOrAccumulate(
            final Either<R, A> a,
            final Either<R, B> b,
            final Function2<A, B, Z> transform
    )
    {
        return zipOrAccumulate(
                a, b, Either.noop(), Either.noop(), Either.noop(), Either.noop(), Either.noop(), Either.noop(), Either.noop(), Either.noop(),
                (aa, bb, cc, dd, ee, ff, gg, hh, ii, jj) -> transform.apply(aa, bb));
    }

    /**
     * Zips or accumulates the values from multiple Either instances. If all input Either instances are Right,
     * the transform function is applied to the values and the result is wrapped in a Right. If any of the input
     * Either instances are Left, the Left values are accumulated into a NonEmptyList and returned.
     *
     * @param <R>       the type of the Left value
     * @param <A>       the type of the Right value for Either a
     * @param <B>       the type of the Right value for Either b
     * @param <C>       the type of the Right value for Either c
     * @param <Z>       the type of the result value
     * @param a         the Either for value a
     * @param b         the Either for value b
     * @param c         the Either for value c
     * @param transform the function to apply to the values if all Either instances are Right
     * @return an Either containing the accumulated Left values or the result of applying the transform function
     */
    static <R, A, B, C, Z> Either<NonEmptyList<R>, Z> zipOrAccumulate(
            final Either<R, A> a,
            final Either<R, B> b,
            final Either<R, C> c,
            final Function3<A, B, C, Z> transform
    )
    {
        return zipOrAccumulate(
                a, b, c, Either.noop(), Either.noop(), Either.noop(), Either.noop(), Either.noop(), Either.noop(), Either.noop(),
                (aa, bb, cc, dd, ee, ff, gg, hh, ii, jj) -> transform.apply(aa, bb, cc));
    }

    /**
     * Zips or accumulates the values from multiple Either instances. If all input Either instances are Right,
     * the transform function is applied to the values and the result is wrapped in a Right. If any of the input
     * Either instances are Left, the Left values are accumulated into a NonEmptyList and returned.
     *
     * @param <R>       the type of the Left value
     * @param <A>       the type of the Right value for Either a
     * @param <B>       the type of the Right value for Either b
     * @param <C>       the type of the Right value for Either c
     * @param <D>       the type of the Right value for Either d
     * @param <Z>       the type of the result value
     * @param a         the Either for value a
     * @param b         the Either for value b
     * @param c         the Either for value c
     * @param d         the Either for value d
     * @param transform the function to apply to the values if all Either instances are Right
     * @return an Either containing the accumulated Left values or the result of applying the transform function
     */
    static <R, A, B, C, D, Z> Either<NonEmptyList<R>, Z> zipOrAccumulate(
            final Either<R, A> a,
            final Either<R, B> b,
            final Either<R, C> c,
            final Either<R, D> d,
            final Function4<A, B, C, D, Z> transform
    )
    {
        return zipOrAccumulate(
                a, b, c, d, Either.noop(), Either.noop(), Either.noop(), Either.noop(), Either.noop(), Either.noop(),
                (aa, bb, cc, dd, ee, ff, gg, hh, ii, jj) -> transform.apply(aa, bb, cc, dd));
    }

    /**
     * Zips or accumulates the values from multiple Either instances. If all input Either instances are Right,
     * the transform function is applied to the values and the result is wrapped in a Right. If any of the input
     * Either instances are Left, the Left values are accumulated into a NonEmptyList and returned.
     *
     * @param <R>       the type of the Left value
     * @param <A>       the type of the Right value for Either a
     * @param <B>       the type of the Right value for Either b
     * @param <C>       the type of the Right value for Either c
     * @param <D>       the type of the Right value for Either d
     * @param <E>       the type of the Right value for Either e
     * @param <Z>       the type of the result value
     * @param a         the Either for value a
     * @param b         the Either for value b
     * @param c         the Either for value c
     * @param d         the Either for value d
     * @param e         the Either for value e
     * @param transform the function to apply to the values if all Either instances are Right
     * @return an Either containing the accumulated Left values or the result of applying the transform function
     */
    static <R, A, B, C, D, E, Z> Either<NonEmptyList<R>, Z> zipOrAccumulate(
            final Either<R, A> a,
            final Either<R, B> b,
            final Either<R, C> c,
            final Either<R, D> d,
            final Either<R, E> e,
            final Function5<A, B, C, D, E, Z> transform
    )
    {
        return zipOrAccumulate(
                a, b, c, d, e, Either.noop(), Either.noop(), Either.noop(), Either.noop(), Either.noop(),
                (aa, bb, cc, dd, ee, ff, gg, hh, ii, jj) -> transform.apply(aa, bb, cc, dd, ee));
    }

    /**
     * Zips or accumulates the values from multiple Either instances. If all input Either instances are Right,
     * the transform function is applied to the values and the result is wrapped in a Right. If any of the input
     * Either instances are Left, the Left values are accumulated into a NonEmptyList and returned.
     *
     * @param <R>       the type of the Left value
     * @param <A>       the type of the Right value for Either a
     * @param <B>       the type of the Right value for Either b
     * @param <C>       the type of the Right value for Either c
     * @param <D>       the type of the Right value for Either d
     * @param <E>       the type of the Right value for Either e
     * @param <F>       the type of the Right value for Either f
     * @param <Z>       the type of the result value
     * @param a         the Either for value a
     * @param b         the Either for value b
     * @param c         the Either for value c
     * @param d         the Either for value d
     * @param e         the Either for value e
     * @param f         the Either for value f
     * @param transform the function to apply to the values if all Either instances are Right
     * @return an Either containing the accumulated Left values or the result of applying the transform function
     */
    static <R, A, B, C, D, E, F, Z> Either<NonEmptyList<R>, Z> zipOrAccumulate(
            final Either<R, A> a,
            final Either<R, B> b,
            final Either<R, C> c,
            final Either<R, D> d,
            final Either<R, E> e,
            final Either<R, F> f,
            final Function6<A, B, C, D, E, F, Z> transform
    )
    {
        return zipOrAccumulate(
                a, b, c, d, e, f, Either.noop(), Either.noop(), Either.noop(), Either.noop(),
                (aa, bb, cc, dd, ee, ff, gg, hh, ii, jj) -> transform.apply(aa, bb, cc, dd, ee, ff));
    }

    /**
     * Zips or accumulates the values from multiple Either instances. If all input Either instances are Right,
     * the transform function is applied to the values and the result is wrapped in a Right. If any of the input
     * Either instances are Left, the Left values are accumulated into a NonEmptyList and returned.
     *
     * @param <R>       the type of the Left value
     * @param <A>       the type of the Right value for Either a
     * @param <B>       the type of the Right value for Either b
     * @param <C>       the type of the Right value for Either c
     * @param <D>       the type of the Right value for Either d
     * @param <E>       the type of the Right value for Either e
     * @param <F>       the type of the Right value for Either f
     * @param <G>       the type of the Right value for Either g
     * @param <Z>       the type of the result value
     * @param a         the Either for value a
     * @param b         the Either for value b
     * @param c         the Either for value c
     * @param d         the Either for value d
     * @param e         the Either for value e
     * @param f         the Either for value f
     * @param g         the Either for value g
     * @param transform the function to apply to the values if all Either instances are Right
     * @return an Either containing the accumulated Left values or the result of applying the transform function
     */
    @SuppressWarnings("squid:S107")
    static <R, A, B, C, D, E, F, G, Z> Either<NonEmptyList<R>, Z> zipOrAccumulate(
            final Either<R, A> a,
            final Either<R, B> b,
            final Either<R, C> c,
            final Either<R, D> d,
            final Either<R, E> e,
            final Either<R, F> f,
            final Either<R, G> g,
            final Function7<A, B, C, D, E, F, G, Z> transform
    )
    {
        return zipOrAccumulate(
                a, b, c, d, e, f, g, Either.noop(), Either.noop(), Either.noop(),
                (aa, bb, cc, dd, ee, ff, gg, hh, ii, jj) -> transform.apply(aa, bb, cc, dd, ee, ff, gg));
    }

    /**
     * Zips or accumulates the values from multiple Either instances. If all input Either instances are Right,
     * the transform function is applied to the values and the result is wrapped in a Right. If any of the input
     * Either instances are Left, the Left values are accumulated into a NonEmptyList and returned.
     *
     * @param <R>       the type of the Left value
     * @param <A>       the type of the Right value for Either a
     * @param <B>       the type of the Right value for Either b
     * @param <C>       the type of the Right value for Either c
     * @param <D>       the type of the Right value for Either d
     * @param <E>       the type of the Right value for Either e
     * @param <F>       the type of the Right value for Either f
     * @param <G>       the type of the Right value for Either g
     * @param <H>       the type of the Right value for Either h
     * @param <Z>       the type of the result value
     * @param a         the Either for value a
     * @param b         the Either for value b
     * @param c         the Either for value c
     * @param d         the Either for value d
     * @param e         the Either for value e
     * @param f         the Either for value f
     * @param g         the Either for value g
     * @param h         the Either for value h
     * @param transform the function to apply to the values if all Either instances are Right
     * @return an Either containing the accumulated Left values or the result of applying the transform function
     */
    @SuppressWarnings("squid:S107")
    static <R, A, B, C, D, E, F, G, H, Z> Either<NonEmptyList<R>, Z> zipOrAccumulate(
            final Either<R, A> a,
            final Either<R, B> b,
            final Either<R, C> c,
            final Either<R, D> d,
            final Either<R, E> e,
            final Either<R, F> f,
            final Either<R, G> g,
            final Either<R, H> h,
            final Function8<A, B, C, D, E, F, G, H, Z> transform
    )
    {
        return zipOrAccumulate(
                a, b, c, d, e, f, g, h, Either.noop(), Either.noop(),
                (aa, bb, cc, dd, ee, ff, gg, hh, ii, jj) -> transform.apply(aa, bb, cc, dd, ee, ff, gg, hh));
    }

    /**
     * Zips or accumulates the values from multiple Either instances. If all input Either instances are Right,
     * the transform function is applied to the values and the result is wrapped in a Right. If any of the input
     * Either instances are Left, the Left values are accumulated into a NonEmptyList and returned.
     *
     * @param <R>       the type of the Left value
     * @param <A>       the type of the Right value for Either a
     * @param <B>       the type of the Right value for Either b
     * @param <C>       the type of the Right value for Either c
     * @param <D>       the type of the Right value for Either d
     * @param <E>       the type of the Right value for Either e
     * @param <F>       the type of the Right value for Either f
     * @param <G>       the type of the Right value for Either g
     * @param <H>       the type of the Right value for Either h
     * @param <I>       the type of the Right value for Either i
     * @param <Z>       the type of the result value
     * @param a         the Either for value a
     * @param b         the Either for value b
     * @param c         the Either for value c
     * @param d         the Either for value d
     * @param e         the Either for value e
     * @param f         the Either for value f
     * @param g         the Either for value g
     * @param h         the Either for value h
     * @param i         the Either for value i
     * @param transform the function to apply to the values if all Either instances are Right
     * @return an Either containing the accumulated Left values or the result of applying the transform function
     */
    @SuppressWarnings("squid:S107")
    static <R, A, B, C, D, E, F, G, H, I, Z> Either<NonEmptyList<R>, Z> zipOrAccumulate(
            final Either<R, A> a,
            final Either<R, B> b,
            final Either<R, C> c,
            final Either<R, D> d,
            final Either<R, E> e,
            final Either<R, F> f,
            final Either<R, G> g,
            final Either<R, H> h,
            final Either<R, I> i,
            final Function9<A, B, C, D, E, F, G, H, I, Z> transform
    )
    {
        return zipOrAccumulate(
                a, b, c, d, e, f, g, h, i, Either.noop(),
                (aa, bb, cc, dd, ee, ff, gg, hh, ii, jj) -> transform.apply(aa, bb, cc, dd, ee, ff, gg, hh, ii));
    }

    /**
     * Zips or accumulates the values from multiple Either instances. If all input Either instances are Right,
     * the transform function is applied to the values and the result is wrapped in a Right. If any of the input
     * Either instances are Left, the Left values are accumulated into a NonEmptyList and returned.
     *
     * @param <R>       the type of the Left value
     * @param <A>       the type of the Right value for Either a
     * @param <B>       the type of the Right value for Either b
     * @param <C>       the type of the Right value for Either c
     * @param <D>       the type of the Right value for Either d
     * @param <E>       the type of the Right value for Either e
     * @param <F>       the type of the Right value for Either f
     * @param <G>       the type of the Right value for Either g
     * @param <H>       the type of the Right value for Either h
     * @param <I>       the type of the Right value for Either i
     * @param <J>       the type of the Right value for Either j
     * @param <Z>       the type of the result value
     * @param a         the Either for value a
     * @param b         the Either for value b
     * @param c         the Either for value c
     * @param d         the Either for value d
     * @param e         the Either for value e
     * @param f         the Either for value f
     * @param g         the Either for value g
     * @param h         the Either for value h
     * @param i         the Either for value i
     * @param j         the Either for value j
     * @param transform the function to apply to the values if all Either instances are Right
     * @return an Either containing the accumulated Left values or the result of applying the transform function
     */
    @SuppressWarnings({"squid:S107"})
    static <R, A, B, C, D, E, F, G, H, I, J, Z> Either<NonEmptyList<R>, Z> zipOrAccumulate(
            final Either<R, A> a,
            final Either<R, B> b,
            final Either<R, C> c,
            final Either<R, D> d,
            final Either<R, E> e,
            final Either<R, F> f,
            final Either<R, G> g,
            final Either<R, H> h,
            final Either<R, I> i,
            final Either<R, J> j,
            final Function10<A, B, C, D, E, F, G, H, I, J, Z> transform
    )
    {
        final var eithers = FList.of(a, b, c, d, e, f, g, h, i, j);

        final var errors = eithers
                .filter(Either::isLeft)
                .map(Either::getLeft);

        if (errors.isEmpty())
        {
            return right(transform.apply(a.get(), b.get(), c.get(), d.get(), e.get(), f.get(), g.get(), h.get(), i.get(), j.get()));
        }
        else
        {
            return left(NonEmptyList.of(errors));
        }
    }

    /**
     * Combines or accumulates multiple Either into a single Either.
     * If all the input Either are Right, then
     * the transform function is applied to their Right values and the result is returned as a Right. If any of the
     * input Either are Left, then the Left values are accumulated and returned as a NonEmptyList of errors.
     *
     * @param <R>       The type of the error value in the input Either and the resulting NonEmptyList of errors.
     * @param <A>       The type of the Right value in the first input Either.
     * @param <B>       The type of the Right value in the second input Either.
     * @param <Z>       The type of the result when transforming the Right values with the given transform function.
     * @param a         The first input Either.
     * @param b         The second input Either.
     * @param transform The function to transform the Right values of the input Either into a result of type Z.
     * @return Either<NonEmptyList < R>, Z> - Either containing the accumulated errors as a NonEmptyList or the transformed right value as a Right.
     */

    static <R, A, B, Z> Either<NonEmptyList<R>, Z> zipOrAccumulateNel(
            final Either<NonEmptyList<R>, A> a,
            final Either<NonEmptyList<R>, B> b,
            final Function2<A, B, Z> transform
    )
    {
        return zipOrAccumulateNel(
                a, b, Either.noop(), Either.noop(), Either.noop(), Either.noop(), Either.noop(), Either.noop(), Either.noop(), Either.noop(),
                (aa, bb, cc, dd, ee, ff, gg, hh, ii, jj) -> transform.apply(aa, bb));
    }

    /**
     * Combines or accumulates multiple Either into a single Either.
     * If all the input Either are Right, then
     * the transform function is applied to their Right values and the result is returned as a Right. If any of the
     * input Either are Left, then the Left values are accumulated and returned as a NonEmptyList of errors.
     *
     * @param <R>       The type of the error value in the input Either and the resulting NonEmptyList of errors.
     * @param <A>       The type of the Right value in the first input Either.
     * @param <B>       The type of the Right value in the second input Either.
     * @param <C>       The type of the Right value in the third input Either.
     * @param <Z>       The type of the result when transforming the Right values with the given transform function.
     * @param a         The first input Either.
     * @param b         The second input Either.
     * @param c         The third input Either.
     * @param transform The function to transform the Right values of the input Either into a result of type Z.
     * @return Either<NonEmptyList < R>, Z> - Either containing the accumulated errors as a NonEmptyList or the transformed right value as a Right.
     */

    static <R, A, B, C, Z> Either<NonEmptyList<R>, Z> zipOrAccumulateNel(
            final Either<NonEmptyList<R>, A> a,
            final Either<NonEmptyList<R>, B> b,
            final Either<NonEmptyList<R>, C> c,
            final Function3<A, B, C, Z> transform
    )
    {
        return zipOrAccumulateNel(
                a, b, c, Either.noop(), Either.noop(), Either.noop(), Either.noop(), Either.noop(), Either.noop(), Either.noop(),
                (aa, bb, cc, dd, ee, ff, gg, hh, ii, jj) -> transform.apply(aa, bb, cc));
    }

    /**
     * Combines or accumulates multiple Either into a single Either.
     * If all the input Either are Right, then
     * the transform function is applied to their Right values and the result is returned as a Right. If any of the
     * input Either are Left, then the Left values are accumulated and returned as a NonEmptyList of errors.
     *
     * @param <R>       The type of the error value in the input Either and the resulting NonEmptyList of errors.
     * @param <A>       The type of the Right value in the first input Either.
     * @param <B>       The type of the Right value in the second input Either.
     * @param <C>       The type of the Right value in the third input Either.
     * @param <D>       The type of the Right value in the fourth input Either.
     * @param <Z>       The type of the result when transforming the Right values with the given transform function.
     * @param a         The first input Either.
     * @param b         The second input Either.
     * @param c         The third input Either.
     * @param d         The fourth input Either.
     * @param transform The function to transform the Right values of the input Either into a result of type Z.
     * @return Either<NonEmptyList < R>, Z> - Either containing the accumulated errors as a NonEmptyList or the transformed right value as a Right.
     */


    static <R, A, B, C, D, Z> Either<NonEmptyList<R>, Z> zipOrAccumulateNel(
            final Either<NonEmptyList<R>, A> a,
            final Either<NonEmptyList<R>, B> b,
            final Either<NonEmptyList<R>, C> c,
            final Either<NonEmptyList<R>, D> d,
            final Function4<A, B, C, D, Z> transform
    )
    {
        return zipOrAccumulateNel(
                a, b, c, d, Either.noop(), Either.noop(), Either.noop(), Either.noop(), Either.noop(), Either.noop(),
                (aa, bb, cc, dd, ee, ff, gg, hh, ii, jj) -> transform.apply(aa, bb, cc, dd));
    }

    /**
     * Combines or accumulates multiple Either into a single Either.
     * If all the input Either are Right, then
     * the transform function is applied to their Right values and the result is returned as a Right. If any of the
     * input Either are Left, then the Left values are accumulated and returned as a NonEmptyList of errors.
     *
     * @param <R>       The type of the error value in the input Either and the resulting NonEmptyList of errors.
     * @param <A>       The type of the Right value in the first input Either.
     * @param <B>       The type of the Right value in the second input Either.
     * @param <C>       The type of the Right value in the third input Either.
     * @param <D>       The type of the Right value in the fourth input Either.
     * @param <E>       The type of the Right value in the fifth input Either.
     * @param <Z>       The type of the result when transforming the Right values with the given transform function.
     * @param a         The first input Either.
     * @param b         The second input Either.
     * @param c         The third input Either.
     * @param d         The fourth input Either.
     * @param e         The fifth input Either.
     * @param transform The function to transform the Right values of the input Either into a result of type Z.
     * @return Either<NonEmptyList < R>, Z> - Either containing the accumulated errors as a NonEmptyList or the transformed right value as a Right.
     */

    static <R, A, B, C, D, E, Z> Either<NonEmptyList<R>, Z> zipOrAccumulateNel(
            final Either<NonEmptyList<R>, A> a,
            final Either<NonEmptyList<R>, B> b,
            final Either<NonEmptyList<R>, C> c,
            final Either<NonEmptyList<R>, D> d,
            final Either<NonEmptyList<R>, E> e,
            final Function5<A, B, C, D, E, Z> transform
    )
    {
        return zipOrAccumulateNel(
                a, b, c, d, e, Either.noop(), Either.noop(), Either.noop(), Either.noop(), Either.noop(),
                (aa, bb, cc, dd, ee, ff, gg, hh, ii, jj) -> transform.apply(aa, bb, cc, dd, ee));
    }

    /**
     * Combines or accumulates multiple Either into a single Either.
     * If all the input Either are Right, then
     * the transform function is applied to their Right values and the result is returned as a Right. If any of the
     * input Either are Left, then the Left values are accumulated and returned as a NonEmptyList of errors.
     *
     * @param <R>       The type of the error value in the input Either and the resulting NonEmptyList of errors.
     * @param <A>       The type of the Right value in the first input Either.
     * @param <B>       The type of the Right value in the second input Either.
     * @param <C>       The type of the Right value in the third input Either.
     * @param <D>       The type of the Right value in the fourth input Either.
     * @param <E>       The type of the Right value in the fifth input Either.
     * @param <F>       The type of the Right value in the sixth input Either.
     * @param <Z>       The type of the result when transforming the Right values with the given transform function.
     * @param a         The first input Either.
     * @param b         The second input Either.
     * @param c         The third input Either.
     * @param d         The fourth input Either.
     * @param e         The fifth input Either.
     * @param f         The sixth input Either.
     * @param transform The function to transform the Right values of the input Either into a result of type Z.
     * @return Either<NonEmptyList < R>, Z> - Either containing the accumulated errors as a NonEmptyList or the transformed right value as a Right.
     */

    static <R, A, B, C, D, E, F, Z> Either<NonEmptyList<R>, Z> zipOrAccumulateNel(
            final Either<NonEmptyList<R>, A> a,
            final Either<NonEmptyList<R>, B> b,
            final Either<NonEmptyList<R>, C> c,
            final Either<NonEmptyList<R>, D> d,
            final Either<NonEmptyList<R>, E> e,
            final Either<NonEmptyList<R>, F> f,
            final Function6<A, B, C, D, E, F, Z> transform
    )
    {
        return zipOrAccumulateNel(
                a, b, c, d, e, f, Either.noop(), Either.noop(), Either.noop(), Either.noop(),
                (aa, bb, cc, dd, ee, ff, gg, hh, ii, jj) -> transform.apply(aa, bb, cc, dd, ee, ff));
    }

    /**
     * Combines or accumulates multiple Either into a single Either.
     * If all the input Either are Right, then
     * the transform function is applied to their Right values and the result is returned as a Right. If any of the
     * input Either are Left, then the Left values are accumulated and returned as a NonEmptyList of errors.
     *
     * @param <R>       The type of the error value in the input Either and the resulting NonEmptyList of errors.
     * @param <A>       The type of the Right value in the first input Either.
     * @param <B>       The type of the Right value in the second input Either.
     * @param <C>       The type of the Right value in the third input Either.
     * @param <D>       The type of the Right value in the fourth input Either.
     * @param <E>       The type of the Right value in the fifth input Either.
     * @param <F>       The type of the Right value in the sixth input Either.
     * @param <G>       The type of the Right value in the seventh input Either.
     * @param <Z>       The type of the result when transforming the Right values with the given transform function.
     * @param a         The first input Either.
     * @param b         The second input Either.
     * @param c         The third input Either.
     * @param d         The fourth input Either.
     * @param e         The fifth input Either.
     * @param f         The sixth input Either.
     * @param g         The seventh input Either.
     * @param transform The function to transform the Right values of the input Either into a result of type Z.
     * @return Either<NonEmptyList < R>, Z> - Either containing the accumulated errors as a NonEmptyList or the transformed right value as a Right.
     */

    @SuppressWarnings("squid:S107")
    static <R, A, B, C, D, E, F, G, Z> Either<NonEmptyList<R>, Z> zipOrAccumulateNel(
            final Either<NonEmptyList<R>, A> a,
            final Either<NonEmptyList<R>, B> b,
            final Either<NonEmptyList<R>, C> c,
            final Either<NonEmptyList<R>, D> d,
            final Either<NonEmptyList<R>, E> e,
            final Either<NonEmptyList<R>, F> f,
            final Either<NonEmptyList<R>, G> g,
            final Function7<A, B, C, D, E, F, G, Z> transform
    )
    {
        return zipOrAccumulateNel(
                a, b, c, d, e, f, g, Either.noop(), Either.noop(), Either.noop(),
                (aa, bb, cc, dd, ee, ff, gg, hh, ii, jj) -> transform.apply(aa, bb, cc, dd, ee, ff, gg));
    }

    /**
     * Combines or accumulates multiple Either into a single Either.
     * If all the input Either are Right, then
     * the transform function is applied to their Right values and the result is returned as a Right. If any of the
     * input Either are Left, then the Left values are accumulated and returned as a NonEmptyList of errors.
     *
     * @param <R>       The type of the error value in the input Either and the resulting NonEmptyList of errors.
     * @param <A>       The type of the Right value in the first input Either.
     * @param <B>       The type of the Right value in the second input Either.
     * @param <C>       The type of the Right value in the third input Either.
     * @param <D>       The type of the Right value in the fourth input Either.
     * @param <E>       The type of the Right value in the fifth input Either.
     * @param <F>       The type of the Right value in the sixth input Either.
     * @param <G>       The type of the Right value in the seventh input Either.
     * @param <H>       The type of the Right value in the eighth input Either.
     * @param <Z>       The type of the result when transforming the Right values with the given transform function.
     * @param a         The first input Either.
     * @param b         The second input Either.
     * @param c         The third input Either.
     * @param d         The fourth input Either.
     * @param e         The fifth input Either.
     * @param f         The sixth input Either.
     * @param g         The seventh input Either.
     * @param h         The eighth input Either.
     * @param transform The function to transform the Right values of the input Either into a result of type Z.
     * @return Either<NonEmptyList < R>, Z> - Either containing the accumulated errors as a NonEmptyList or the transformed right value as a Right.
     */

    @SuppressWarnings("squid:S107")
    static <R, A, B, C, D, E, F, G, H, Z> Either<NonEmptyList<R>, Z> zipOrAccumulateNel(
            final Either<NonEmptyList<R>, A> a,
            final Either<NonEmptyList<R>, B> b,
            final Either<NonEmptyList<R>, C> c,
            final Either<NonEmptyList<R>, D> d,
            final Either<NonEmptyList<R>, E> e,
            final Either<NonEmptyList<R>, F> f,
            final Either<NonEmptyList<R>, G> g,
            final Either<NonEmptyList<R>, H> h,
            final Function8<A, B, C, D, E, F, G, H, Z> transform
    )
    {
        return zipOrAccumulateNel(
                a, b, c, d, e, f, g, h, Either.noop(), Either.noop(),
                (aa, bb, cc, dd, ee, ff, gg, hh, ii, jj) -> transform.apply(aa, bb, cc, dd, ee, ff, gg, hh));
    }

    /**
     * Combines or accumulates multiple Either into a single Either.
     * If all the input Either are Right, then
     * the transform function is applied to their Right values and the result is returned as a Right. If any of the
     * input Either are Left, then the Left values are accumulated and returned as a NonEmptyList of errors.
     *
     * @param <R>       The type of the error value in the input Either and the resulting NonEmptyList of errors.
     * @param <A>       The type of the Right value in the first input Either.
     * @param <B>       The type of the Right value in the second input Either.
     * @param <C>       The type of the Right value in the third input Either.
     * @param <D>       The type of the Right value in the fourth input Either.
     * @param <E>       The type of the Right value in the fifth input Either.
     * @param <F>       The type of the Right value in the sixth input Either.
     * @param <G>       The type of the Right value in the seventh input Either.
     * @param <H>       The type of the Right value in the eighth input Either.
     * @param <I>       The type of the Right value in the ninth input Either.
     * @param <Z>       The type of the result when transforming the Right values with the given transform function.
     * @param a         The first input Either.
     * @param b         The second input Either.
     * @param c         The third input Either.
     * @param d         The fourth input Either.
     * @param e         The fifth input Either.
     * @param f         The sixth input Either.
     * @param g         The seventh input Either.
     * @param h         The eighth input Either.
     * @param i         The ninth input Either.
     * @param transform The function to transform the Right values of the input Either into a result of type Z.
     * @return Either<NonEmptyList < R>, Z> - Either containing the accumulated errors as a NonEmptyList or the transformed right value as a Right.
     */
    @SuppressWarnings("squid:S107")
    static <R, A, B, C, D, E, F, G, H, I, Z> Either<NonEmptyList<R>, Z> zipOrAccumulateNel(
            final Either<NonEmptyList<R>, A> a,
            final Either<NonEmptyList<R>, B> b,
            final Either<NonEmptyList<R>, C> c,
            final Either<NonEmptyList<R>, D> d,
            final Either<NonEmptyList<R>, E> e,
            final Either<NonEmptyList<R>, F> f,
            final Either<NonEmptyList<R>, G> g,
            final Either<NonEmptyList<R>, H> h,
            final Either<NonEmptyList<R>, I> i,
            final Function9<A, B, C, D, E, F, G, H, I, Z> transform
    )
    {
        return zipOrAccumulateNel(
                a, b, c, d, e, f, g, h, i, Either.noop(),
                (aa, bb, cc, dd, ee, ff, gg, hh, ii, jj) -> transform.apply(aa, bb, cc, dd, ee, ff, gg, hh, ii));
    }

    /**
     * Combines or accumulates multiple Either into a single Either.
     * If all the input Either are Right, then
     * the transform function is applied to their Right values and the result is returned as a Right. If any of the
     * input Either are Left, then the Left values are accumulated and returned as a NonEmptyList of errors.
     *
     * @param <R>       The type of the error value in the input Either and the resulting NonEmptyList of errors.
     * @param <A>       The type of the Right value in the first input Either.
     * @param <B>       The type of the Right value in the second input Either.
     * @param <C>       The type of the Right value in the third input Either.
     * @param <D>       The type of the Right value in the fourth input Either.
     * @param <E>       The type of the Right value in the fifth input Either.
     * @param <F>       The type of the Right value in the sixth input Either.
     * @param <G>       The type of the Right value in the seventh input Either.
     * @param <H>       The type of the Right value in the eighth input Either.
     * @param <I>       The type of the Right value in the ninth input Either.
     * @param <J>       The type of the Right value in the tenth input Either.
     * @param <Z>       The type of the result when transforming the Right values with the given transform function.
     * @param a         The first input Either.
     * @param b         The second input Either.
     * @param c         The third input Either.
     * @param d         The fourth input Either.
     * @param e         The fifth input Either.
     * @param f         The sixth input Either.
     * @param g         The seventh input Either.
     * @param h         The eighth input Either.
     * @param i         The ninth input Either.
     * @param j         The tenth input Either.
     * @param transform The function to transform the Right values of the input Either into a result of type Z.
     * @return Either<NonEmptyList < R>, Z> - Either containing the accumulated errors as a NonEmptyList or the transformed right value as a Right.
     */
    @SuppressWarnings("squid:S107")
    static <R, A, B, C, D, E, F, G, H, I, J, Z> Either<NonEmptyList<R>, Z> zipOrAccumulateNel(
            final Either<NonEmptyList<R>, A> a,
            final Either<NonEmptyList<R>, B> b,
            final Either<NonEmptyList<R>, C> c,
            final Either<NonEmptyList<R>, D> d,
            final Either<NonEmptyList<R>, E> e,
            final Either<NonEmptyList<R>, F> f,
            final Either<NonEmptyList<R>, G> g,
            final Either<NonEmptyList<R>, H> h,
            final Either<NonEmptyList<R>, I> i,
            final Either<NonEmptyList<R>, J> j,
            final Function10<A, B, C, D, E, F, G, H, I, J, Z> transform
    )
    {
        final var eithers = FList.of(a, b, c, d, e, f, g, h, i, j);

        final var errors = eithers
                .filter(Either::isLeft)
                .map(Either::getLeft)
                .flatMap(NonEmptyList::toFList);

        if (errors.isEmpty())
        {
            return right(transform.apply(a.get(), b.get(), c.get(), d.get(), e.get(), f.get(), g.get(), h.get(), i.get(), j.get()));
        }
        else
        {
            return left(NonEmptyList.of(errors));
        }
    }

    /**
     * Checks if the instance is of the Left type.
     *
     * @return true if the instance is of the Left type, false otherwise.
     */
    boolean isLeft();

    /**
     * Checks if the instance is of the Right type.
     *
     * @return true if the instance is of the Right type, false otherwise.
     */
    boolean isRight();

    /**
     * Retrieves the value of the left side of the Either instance.
     *
     * @return The value of the left side.
     */
    L getLeft();

    default <X extends Throwable> R getOrElseThrow(Function1<? super L, X> exceptionMapper) throws X
    {
        Objects.requireNonNull(exceptionMapper, "exceptionMapper is null");
        if (isEmpty())
        {
            throw exceptionMapper.apply(getLeft());
        }
        return get();
    }

    @Override
    default boolean isEmpty()
    {
        return isLeft();
    }

    @Override
    default Iterator<R> iterator()
    {
        if (isRight())
        {
            return new Iterator<>()
            {
                boolean hasNext = true;

                @Override
                public boolean hasNext()
                {
                    return hasNext;
                }

                @Override
                public R next()
                {
                    if (!hasNext)
                    {
                        throw new NoSuchElementException();
                    }
                    hasNext = false;
                    return get();
                }
            };
        }
        else
        {
            return new Iterator<>()
            {
                @Override
                public boolean hasNext()
                {
                    return false;
                }

                @Override
                public R next()
                {
                    throw new NoSuchElementException();
                }

            };
        }

    }

    default Either<L, R> peek(Consumer<R> action)
    {
        Objects.requireNonNull(action, "action is null");
        if (isRight())
        {
            action.accept(get());
        }
        return this;
    }

    default Either<L, R> peekLeft(Consumer<L> action)
    {
        Objects.requireNonNull(action, "action is null");
        if (isLeft())
        {
            action.accept(getLeft());
        }
        return this;
    }

    /**
     * Maps the value of the Either if it is a right value, using the provided mapper function.
     *
     * @param <T>    The type of the mapped value.
     * @param mapper The function to apply to the value if it is a right value.
     * @return A new Either containing the mapped value, or the same Either if it is a left value.
     */
    @SuppressWarnings("unchecked")
    default <T> Either<L, T> map(final Function1<? super R, ? extends T> mapper)
    {
        if (isRight())
        {
            return right(mapper.apply(get()));
        }
        else
        {
            return (Either<L, T>) this;
        }
    }

    /**
     * This method applies the given mapper function to the left value of this Either object and returns a new Either object with the result.
     *
     * @param mapper the function to apply to the left value
     * @param <T>    the type of the result of the function
     * @return a new Either object with the result of applying the mapper function to the left value
     */
    @SuppressWarnings("unchecked")
    default <T> Either<T, R> mapLeft(final Function1<? super L, ? extends T> mapper)
    {
        Objects.requireNonNull(mapper, "mapper is null");
        if (isLeft())
        {
            return left(mapper.apply(getLeft()));
        }
        else
        {
            return (Either<T, R>) this;
        }
    }

    /**
     * Applies the specified mappers to the values of the Either,
     * returning a new Either instance with the transformed values.
     *
     * @param leftMapper  the mapper function to apply to the left value
     * @param rightMapper the mapper function to apply to the right value
     * @param <T>         the type of the left value of the Either
     * @param <U>         the type of the right value of the Either
     * @return a new Either instance with the transformed values
     */
    default <T, U> Either<T, U> bimap(final Function1<? super L, ? extends T> leftMapper, final Function1<? super R, ? extends U> rightMapper)
    {
        Objects.requireNonNull(leftMapper, "leftMapper is null");
        Objects.requireNonNull(rightMapper, "rightMapper is null");
        if (isLeft())
        {
            return left(leftMapper.apply(getLeft()));
        }
        else
        {
            return right(rightMapper.apply(get()));
        }
    }

    /**
     * Applies the given leftMapper function on the left value if this either is in the left state,
     * or applies the given rightMapper function on the right value if this either is in the right state.
     * Returns the result of the function application.
     *
     * @param leftMapper  the function to be applied on the left value if this either is in the left state
     * @param rightMapper the function to be applied on the right value if this either is in the right state
     * @param <U>         the type of the result of the function application
     * @return the result of the function application
     * @throws NullPointerException if leftMapper is null or rightMapper is null
     */
    default <U> U fold(final Function1<? super L, ? extends U> leftMapper, final Function1<? super R, ? extends U> rightMapper)
    {
        Objects.requireNonNull(leftMapper, "leftMapper is null");
        Objects.requireNonNull(rightMapper, "rightMapper is null");
        if (isRight())
        {
            return rightMapper.apply(get());
        }
        else
        {
            return leftMapper.apply(getLeft());
        }
    }

    final class Left<L, R> implements Either<L, R>
    {

        /**
         * Represents the value stored in an instance of Left.
         * <p>
         * This value is final and cannot be modified once initialized.
         */
        private final L value;

        /**
         * Constructs a new instance of Left with the specified value.
         *
         * @param value the value for the left side of the instance
         */
        public Left(final L value)
        {
            this.value = value;
        }

        /**
         * Determines if the object is from the left side.
         *
         * @return {@code true} if the object is from the left side,
         * {@code false} otherwise.
         */
        @Override
        public boolean isLeft()
        {
            return true;
        }

        /**
         * Checks if the current object represents the right side of an Either object.
         *
         * @return {@code false} since this object represents the left side of an Either object
         */
        @Override
        public boolean isRight()
        {
            return false;
        }

        /**
         * Retrieves the value contained in an instance of Left.
         *
         * @return the value contained in the Left instance.
         * @throws NoSuchElementException if called on an instance of Left.
         */
        @Override
        public R get()
        {
            throw new NoSuchElementException("Calling get on a Left");
        }

        /**
         * Retrieves the left value of this object.
         *
         * @return the left value
         */
        @Override
        public L getLeft()
        {
            return value;
        }

        @Override
        public boolean equals(Object obj)
        {
            return (obj == this) || (obj instanceof Either.Left<?, ?> && Objects.equals(value, ((Left<?, ?>) obj).value));
        }

        @Override
        public int hashCode()
        {
            return Objects.hashCode(value);
        }

        @Override
        public String toString()
        {
            if (getLeft() == null)
            {
                return "Left(null)";
            }
            return "Left(" + getLeft() + ")";
        }
    }

    /**
     * Represents a Right value in the Either type.
     *
     * @param <L> the type of the Left value
     * @param <R> the type of the Right value
     */
    final class Right<L, R> implements Either<L, R>
    {
        /**
         * Value stored in an instance of the Right class.
         */
        private final R value;

        /**
         * Represents a Right value in an Either object.
         */
        public Right(final R value)
        {
            this.value = value;
        }

        /**
         * Determines if the given object is positioned at the left.
         *
         * @return {@code true} if the object is at the left, {@code false} otherwise.
         */
        @Override
        public boolean isLeft()
        {
            return false;
        }

        /**
         * Returns a boolean value indicating whether this instance represents a right value.
         *
         * @return true if this instance represents a right value, false otherwise.
         */
        @Override
        public boolean isRight()
        {
            return true;
        }

        /**
         * Returns the value of the Right type object.
         *
         * @return the value of the Right type object
         */
        @Override
        public R get()
        {
            return value;
        }

        /**
         * Retrieves the left value of this Either instance.
         *
         * @return the left value of this Either instance.
         * @throws NoSuchElementException if this is a Right instance.
         */
        @Override
        public L getLeft()
        {
            throw new NoSuchElementException("Calling getLeft on a Right");
        }

        @Override
        public boolean equals(Object obj)
        {
            return (obj == this) || (obj instanceof Right && Objects.equals(value, ((Right<?, ?>) obj).value));
        }

        @Override
        public int hashCode()
        {
            return Objects.hashCode(value);
        }

        @Override
        public String toString()
        {
            if (get() == null)
            {
                return "Right(null)";
            }
            return "Right(" + get() + ")";
        }
    }
}