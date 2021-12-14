# udacity.android.kotlin.developer.nanodegree.customfancontroller
CustomFanController Application from Udacity Android Kotlin Developer Nanodegree program.

Highlights:

Custom Views (subclass existing view and override methods)\
\
Drawing custom views\
1 - override onSizeChanged() to calculate views size when first appearing and each time size changes\
2 - override onDraw() to draw custom view using Canvas object styled by a Paint object (allocate space and initialize outside of onDraw() if at all possible)\
3 - call invalidate() when responding to user click changing how view is drawn forcing onDraw() to redraw view\
\
Draw primitive shapes using drawRect(), drawOval(), and drawArc()\
Change whether shapes are filled, outlined, or both by calling setStyle()\
Draw bitmaps by calling drawBitmap()\
\
Perform clicks on custom views by setting isClickable to true, implementing performClick(), call invalidate() to redraw the view\
\
Adding attributes to custom view