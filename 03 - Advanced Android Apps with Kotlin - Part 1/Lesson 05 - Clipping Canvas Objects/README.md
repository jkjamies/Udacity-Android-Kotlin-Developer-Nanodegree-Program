# udacity.android.kotlin.developer.nanodegree.clippingexample
ClippingExample Application from Udacity Android Kotlin Developer Nanodegree program.

Highlights:

Clipping\
Overdraw\
\
JvmOverloads annotation (Instructs the Kotlin compiler to generate overloads for this function that substitute default parameter values.)\
\
Dimens for smallest screen width (480) - new dimens res file and select available qualifiers smallest screen width >> enter 480\
\
Move canvas instead of moving objects for simplicity, safety, less error prone, easier to unwind transformations (and efficiency)\
\
1 - canvas.save()\
2 - canvas.translate()\
3 - canvas.clipRect()\
4 - draw...\
5 - canvas.restore\
\
Canvas.clipRect(left, top, right, bottom)\
Paint.align\
Canvas.drawText()\
canvas.save() (think Git stash, saving current state)\
canvas.restore() (think Git stash apply, apply previously saved state)\
\
Steps Used\
1 - save state with canvas.save()\
2 - translate origin to row/column coords with canvas.translate()\
3 - apply transformations to path\
4 - apply clipping with canvas.clipPath(path)\
5 - draw shapes with drawClippedRectangle() or drawText()\
\
quickReject