# Matrix

Write a program that, given a string representing a matrix of numbers, can return the rows and columns of that matrix.

So given a string with embedded newlines like:

> 9 8 7  
> 5 3 2  
> 6 6 7  

representing this matrix:

```plain
    0  1  2
  |---------
0 | 9  8  7
1 | 5  3  2
2 | 6  6  7
```

your code should be able to spit out:

- A list of the rows, reading each row left-to-right while moving
  top-to-bottom across the rows,
- A list of the columns, reading each column top-to-bottom while moving
  from left-to-right.

The rows for our example matrix:

- 9, 8, 7
- 5, 3, 2
- 6, 6, 7

And its columns:

- 9, 5, 6
- 8, 3, 6
- 7, 2, 7

The Scala exercises assume an SBT project scheme. The exercise solution source
should be placed within the exercise directory/src/main/scala. The exercise
unit tests can be found within the exercise directory/src/test/scala.

To run the tests simply run the command `sbt test` in the exercise directory.

For more detailed info about the Scala track see the [help
page](http://exercism.io/languages/scala).

## Source

Warmup to the `saddle-points` warmup. [http://jumpstartlab.com](http://jumpstartlab.com)

## Submitting Incomplete Problems
It's possible to submit an incomplete solution so you can see how others have completed the exercise.

