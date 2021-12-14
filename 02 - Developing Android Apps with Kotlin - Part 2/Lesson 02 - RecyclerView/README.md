# udacity.android.kotlin.developer.nanodegree.trackmysleepqualityrecyclerview
TrackMySleepQualityRecyclerView Application from Udacity Android Kotlin Developer Nanodegree program.

Highlights:

RecyclerViews\
\
RecyclerView Adapters\
\
getItemCount, onBindViewHolder, onCreateViewHolder, notifyDataSetChanged()\
\
Custom XML Layout for RecyclerView and ViewHolder\
\
Highlight code segment, right click, refactor, function (in adapter, then alt+enter on holder, convert parameter to receiver, copy function into ViewHolder class, remove res variable from onBindViewHolder and parameter and add res variable in copied function in ViewHolder class).
You can also use this for the onCreateViewHolder content by placing it in a function in a companion object inside the ViewHolder class with a parent ViewGroup parameter. Then changing the ViewHolder class to private constructor, as there is no need to call it from outside of the class anymore.\
\
Improving Data Refresh with DiffUtil (or notify item moved/updated/removed/changed/added)\
\
DataBinding to Adapter (alt+enter on root layout in xml and convert to binding layout as a tip, update companion object function inflate layout, update ViewHolder class parameter/type and RecyclerView.ViewHolder(binding.root)).
Adding data to the xml views for DataBinding requires adding a BindingUtils file with annotated @BindingAdapter functions to convert to proper formatted resources, then in xml to set to [app:functionNameCreated="@{xmlVariable}"]\
\
GridLayout\
\
OnClickListeners for RecyclerView Items in ViewHolder -> ViewModel\
\
Navigate during on Click\
\
RecyclerView Headers (change definition of SleepNightAdapter.ViewHolder to RecyclerView.ViewHolder, and ListAdapter to hold type DataItem instead of SleepNight)\
\
Customize spans in RecyclerView (GridLayout)