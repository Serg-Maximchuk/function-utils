# Java functional utils

https://mvnrepository.com/artifact/io.github.serg-maximchuk/function-utils

```xml
<dependency>
  <groupId>io.github.serg-maximchuk</groupId>
  <artifactId>function-utils</artifactId>
  <version>1.0.3</version>
</dependency>
```
```groovy
compile group: 'io.github.serg-maximchuk', name: 'function-utils', version: '1.0.3'
```

Dependency-free utility library born to reduce the code size and increase it's readability
when dealing with lambdas, and some more! It will help you to write java code in more
functional/imperative style.

Throwing lambdas as a bonus!

Feel free to create a PR.

# Value::with
The main function, it will help you to write java code in more functional/imperative style.

How many times you've been writing code like these pieces:

```
HttpHeaders headers = new HttpHeaders();
headers.add(AUTHORIZATION, "Basic ...");
return headers;
```


```
User user = new User();
user.setEmail(email);
return repository.save(user);
```

```
User user = new User();
users.add(user);
return user;
```

The point is to shorten such cases, when you want to get/create something, made an action with
it, and return it back.


Whole idea is to transform next common-used structure:
```
T t = new T();
t.f();
return t;
```
Or:
```
T t = new T();
$outer.f(t);
return t;
```

Into this:
```
return with(new T(), t -> t.f());
return with(new T(), t -> $outer.f(t));

// which also can be simplified
return with(new T(), T::f);
return with(new T(), $outer::f);
```

Meet the `Value` class and it's function `with`!

Main actors:
   - `T value`, an operand of generic type, which will be returned in the end;
   - void `Consumer<T> action` whose the only argument would be the `value`.

Take a look how it can reduce the code (`with` method is statically imported):

```
return with(new HttpHeaders(), headers -> headers.add(AUTHORIZATION, "Basic ...");
```

```
return repository.save(with(new User(), user -> user.setEmail(email)));
```

```
return with(new User(), users::add);
```

You may not be good enough in lambdas, so you may not understand these pieces of code,
don't panic! Try this approach few times, and you will start seeing potential places where
you can use this awesome function. You will start hating such code constructions:
```
Some some = new Some();
some.doSomething();
return some;
```

**Note**, the `action` is a consumer - void function. The `Value::with` thing is **not** for
such case:
```
T t = new T();
return $outer.f(t);
```
Which may be something like this in real life:
```
User user = new User();
return repo.save(user);
```
**BUT** the `Value::with` can help you in next case:
```
User user = new User();
user.setActive();
return repo.save(user);
```
Just don't forget to call `repo::save` method **outside** of a `Value::with`,
I believe you understand why.

# Bonus: Throwing lambdas
Not only in the `Value::with` context, sometimes action in a lambda may throw some checked
exceptions. Throwing lambdas to the rescue:

```
ThrowingBiConsumer
ThrowingBiFunction
ThrowingBinaryOperator
ThrowingBiPredicate
ThrowingBooleanSupplier
ThrowingConsumer
ThrowingFunction
ThrowingPredicate
ThrowingRunnable
ThrowingSupplier
ThrowingUnaryOperator
```
You can't compile such a thing without wrapping it into `try/catch` or using
`sneakyThrow` approach:
```
Value.with(t, t -> $outer.thisMethodThrows(t));
```
But you may use built-in method with throwing lambda:
```
Value.withThrowing(t, t -> $outer.thisMethodThrows(t));
```

Throwing lambdas may be used as a stand-alone function whenever you want.

#

### Coming soon:

In throwing lambdas context: comparison why this library is better than others
```
https://www.javadoc.io/doc/io.vavr/vavr/1.0.0-alpha-2
https://github.com/pivovarit/throwing-function
https://github.com/SeregaLBN/StreamUnthrower
https://github.com/fge/throwing-lambdas
https://github.com/mscharhag/ET
https://github.com/jOOQ/jOOL
```


### Coming soon:

```
ThrowingIntBinaryOperator
ThrowingIntConsumer
ThrowingIntFunction
ThrowingIntPredicate
ThrowingIntSupplier
ThrowingIntToDoubleFunction
ThrowingIntToLongFunction
ThrowingIntUnaryOperator
ThrowingLongBinaryOperator
ThrowingLongConsumer
ThrowingLongFunction
ThrowingLongPredicate
ThrowingLongSupplier
ThrowingLongToDoubleFunction
ThrowingLongToIntFunction
ThrowingLongUnaryOperator
ThrowingObjDoubleConsumer
ThrowingObjIntConsumer
ThrowingObjLongConsumer
ThrowingToDoubleBiFunction
ThrowingToDoubleFunction
ThrowingToIntBiFunction
ThrowingToIntFunction
ThrowingToLongBiFunction
ThrowingToLongFunction
```
