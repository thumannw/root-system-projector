# What?

A small JavaFX program (developed sometime in 2017) to visualize irreducible
[Root Systems](https://en.wikipedia.org/wiki/Root_system)
up to rank 8. It does so by projecting the root system onto a 2-dimensional subspace.
The space of all pairs of orthonormal vectors in n-dimensional Euclidean space has 
dimension 2n - 3. Hence, there are as many parameters which you can adjust to choose a 
flat plane onto which the root system gets projected.

# How to use?

Use the arrow keys (up, down, left, right) on the spinner fields to adjust these
parameters. There is also the possibility to play an animation of randomly chosen
projections. It can be run in two different modes "Zig-Zag" and "Straight".
The top right combo box lets you switch between all the irreducible root systems
up to rank 8. The bottom right check boxes determine whether the vectors in the
root system are displayed as lines or points.

# How to run?

You need a JDK 8 to build and run the program. Then, the command `./gradlew run`
should suffice to start the GUI.
