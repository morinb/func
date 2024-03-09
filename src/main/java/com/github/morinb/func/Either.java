package com.github.morinb.func;


import java.util.NoSuchElementException;
import java.util.Objects;

public sealed interface Either<L, R> permits Either.Left, Either.Right
{
    static <L, R> Right<L, R> right(R value)
    {
        return new Right<>(value);
    }

    static <L, R> Left<L, R> left(L value)
    {
        return new Left<>(value);
    }

    boolean isLeft();

    boolean isRight();

    R get();

    L getLeft();

    static <R, A, B, C, D, E, F, G, H, I, J, Z> Either<NonEmptyList<R>, Z> zipOrAccumulate(
            Either<R, A> a,
            Either<R, B> b,
            Either<R, C> c,
            Either<R, D> d,
            Either<R, E> e,
            Either<R, F> f,
            Either<R, G> g,
            Either<R, H> h,
            Either<R, I> i,
            Either<R, J> j,
            Function10<A, B, C, D, E, F, G, H, I, J, Z> transform
    )
    {
        var eithers = List.of(a, b, c, d, e, f, g, h, i, j);

        var errors = eithers
                .filter(Either::isLeft)
                .map(Either::getLeft);

        if (errors.isEmpty())
        {
            return right(transform.apply(a.get(), b.get(), c.get(), d.get(), e.get(), f.get(), g.get(), h.get(), i.get(), j.get()));
        }
        else
        {
            return left(NonEmptyList.of(errors.toJavaList()));
        }
    }

    default <T> Either<L, T> map(Function1<? super R, ? extends T> mapper)
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

    // implement default mapLeft
    default <T> Either<T, R> mapLeft(Function1<? super L, ? extends T> mapper)
    {
        if (isLeft())
        {
            return left(mapper.apply(getLeft()));
        }
        else
        {
            return (Either<T, R>) this;
        }
    }

    // implements bimap
    default <T, U> Either<T, U> bimap(Function1<? super L, ? extends T> leftMapper, Function1<? super R, ? extends U> rightMapper)
    {
        if (isLeft())
        {
            return left(leftMapper.apply(getLeft()));
        }
        else
        {
            return right(rightMapper.apply(get()));
        }
    }

    default <U> U fold(Function1<? super L, ? extends U> leftMapper, Function1<? super R, ? extends U> rightMapper)
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
        private final L value;

        public Left(L value)
        {
            this.value = value;
        }

        @Override
        public boolean isLeft()
        {
            return true;
        }

        @Override
        public boolean isRight()
        {
            return false;
        }

        @Override
        public R get()
        {
            throw new NoSuchElementException("Calling get on a Left");
        }

        @Override
        public L getLeft()
        {
            return value;
        }
    }

    final class Right<L, R> implements Either<L, R>
    {
        private final R value;

        public Right(R value)
        {
            this.value = value;
        }

        @Override
        public boolean isLeft()
        {
            return false;
        }

        @Override
        public boolean isRight()
        {
            return true;
        }

        @Override
        public R get()
        {
            return value;
        }

        @Override
        public L getLeft()
        {
            throw new NoSuchElementException("Calling getLeft on a Right");
        }
    }
}